package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.mindrot.jbcrypt.BCrypt;

import DB.Conexion;

public class ServicioRestablecimiento {
  

    public boolean verificarToken(String token) {
        // Verificar si el token es válido y no ha expirado
        String sql = "SELECT UserID FROM reset_tokens WHERE Token = ? AND Expiry > NOW()";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {
               ps.setString(1, token);
               try (ResultSet rs = ps.executeQuery()) {
                   return rs.next();
               }
        } catch (Exception e) {
        	System.err.println("Error al verificar el token: " + e.getMessage());
            return false;
        }
    }

    public boolean actualizarContraseña(String token, String nuevaContraseña) {
        // Primero, verifica el token
        if (!verificarToken(token)) {
            return false;
        }
        // Aquí deberías hashear la nueva contraseña antes de almacenarla
        String hashedPassword = hashPassword(nuevaContraseña); // Implementa esta función según tu lógica de hashing

        // Actualizar la contraseña del usuario
        String sql = "UPDATE usuarios SET PasswordHash = ? WHERE UserID = (SELECT UserID FROM reset_tokens WHERE Token = ?)";
        try (Connection conexion = Conexion.getConexion();
                PreparedStatement ps = conexion.prepareStatement(sql)) {
               ps.setString(1, hashedPassword);
            ps.setString(2, token);
            int affectedRows = ps.executeUpdate();
            return affectedRows > 0; // Devuelve true si la contraseña se actualizó correctamente
        } catch (Exception e) {
        	 System.err.println("Error al actualizar la contraseña: " + e.getMessage());
            return false;
        }
    }

    // Implementa esta función según tu lógica de hashing
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}

