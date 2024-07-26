package model;

import DB.Conexion;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserModel {

    public boolean registerUser(String username, String email, String password) {
        // Hash de la contraseña
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        try (Connection conn = new Conexion().getConexion()) {
            String sql = "INSERT INTO usuarios (Username, Email, PasswordHash) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, username);
                pstmt.setString(2, email);
                pstmt.setString(3, hashedPassword);

                int affectedRows = pstmt.executeUpdate();

                // Retorna true si el usuario se registró correctamente
                return affectedRows > 0;
            } catch (SQLException ex) {
                System.err.println("Error al registrar el usuario: " + ex.getMessage());
                return false;
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener la conexión: " + ex.getMessage());
            return false;
        }
    }
}
