package DB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.mindrot.jbcrypt.BCrypt;

public class BaseDatosServicio {

    public static boolean guardarToken(String correo, String token) {
        Conexion db = new Conexion();
        try (Connection conn = db.getConexion()) {
            String sql = "INSERT INTO reset_tokens (token, email, expiry) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, token);
                pstmt.setString(2, correo);
                // Establecer la expiración del token a 1 hora desde ahora
                LocalDateTime expiry = LocalDateTime.now().plusHours(1);
                pstmt.setString(3, expiry.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            }
        } catch (SQLException e) {
            System.err.println("Error al guardar el token: " + e.getMessage());
            return false;
        }
    }

    public static boolean verificarToken(String email, String token) {
        String sql = "SELECT COUNT(*) FROM reset_tokens WHERE email = ? AND token = ? AND expiry > NOW()";
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, email);
            pstmt.setString(2, token);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
        	System.err.println("Error al verificar el token: " + e.getMessage());
        }
        return false;
    }

    public static boolean actualizarContraseña(String email, String nuevaContraseña) {
        // Hash de la nueva contraseña
        String hashedPassword = BCrypt.hashpw(nuevaContraseña, BCrypt.gensalt());

        try (Connection conn = new Conexion().getConexion()) {
            String sql = "UPDATE usuarios SET PasswordHash = ? WHERE Email = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, hashedPassword);
                pstmt.setString(2, email);
                int affectedRows = pstmt.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException e) {
                System.err.println("Error al actualizar la contraseña: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
        }
        return false;
    }
}
