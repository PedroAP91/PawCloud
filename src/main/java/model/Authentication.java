package model;

import DB.Conexion;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {

    public boolean authenticateUser(String username, String password) {
        String sql = "SELECT PasswordHash FROM usuarios WHERE Username = ?";
        
        try (Connection conn = new Conexion().getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
             
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPasswordHash = rs.getString("PasswordHash");
                return BCrypt.checkpw(password, storedPasswordHash);
            }
            
        } catch (SQLException e) {
        	 System.err.println("Error al autenticar al usuario: " + e.getMessage());
        }
        
        return false;
    }
}
