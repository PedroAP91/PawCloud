package DB;

import model.Veterinario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {

    private Connection connection;

    public VeterinarioDAO() {
        try {
            // Usamos la clase Conexion para obtener la conexión
            this.connection = Conexion.getConexion();
        } catch (SQLException e) {
            System.err.println("Error al obtener la conexión: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean insertarVeterinario(Veterinario veterinario) {
        String sql = "INSERT INTO veterinarios (nombre, apellidos, licencia, telefono, email, especialidades, horario_trabajo, fecha_contratacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, veterinario.getNombre());
            statement.setString(2, veterinario.getApellidos());
            statement.setString(3, veterinario.getLicencia());
            statement.setString(4, veterinario.getTelefono());
            statement.setString(5, veterinario.getEmail());
            statement.setString(6, veterinario.getEspecialidades());
            statement.setString(7, veterinario.getHorarioTrabajo());
            statement.setDate(8, new java.sql.Date(veterinario.getFechaContratacion().getTime()));

            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println("Error al insertar el veterinario: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public List<Veterinario> obtenerTodosLosVeterinarios() {
        List<Veterinario> veterinarios = new ArrayList<>();
        String sql = "SELECT * FROM veterinarios";
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Veterinario veterinario = new Veterinario(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellidos"),
                        resultSet.getString("licencia"),
                        resultSet.getString("telefono"),
                        resultSet.getString("email"),
                        resultSet.getString("especialidades"),
                        resultSet.getString("horario_trabajo"),
                        resultSet.getDate("fecha_contratacion")
                );
                veterinarios.add(veterinario);
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener veterinarios: " + e.getMessage());
            e.printStackTrace();
        }
        return veterinarios;
    }
    
    public int obtenerIdVeterinarioPorNombre(String nombreCompletoVeterinario) {
        int idVeterinario = -1; // Un valor que indica que el id no fue encontrado
        String sql = "SELECT id FROM veterinarios WHERE CONCAT(nombre, ' ', apellidos) = ?";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nombreCompletoVeterinario);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                idVeterinario = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.err.println("Error al obtener el ID del veterinario: " + e.getMessage());
            e.printStackTrace();
        }

        return idVeterinario;
    }

}
