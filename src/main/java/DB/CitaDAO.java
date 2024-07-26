package DB;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import model.Cita;

public class CitaDAO {

	public void insertarCita(Cita cita) {
	    String sql = "INSERT INTO citas (titulo, fecha, hora, notas, id_cliente, id_mascota, tipo) VALUES (?, ?, ?, ?, ?, ?, ?)";
	    
	    try (Connection conn = Conexion.getConexion();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        pstmt.setString(1, cita.getTitulo());
	        pstmt.setDate(2, Date.valueOf(cita.getFecha()));
	        pstmt.setTime(3, Time.valueOf(cita.getHora()));
	        pstmt.setString(4, cita.getNotas());
	        pstmt.setInt(5, cita.getClienteId());
	        pstmt.setInt(6, cita.getMascotaId());
	        pstmt.setString(7, cita.getTipo()); // Añadir el tipo de cita
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    
    public List<Cita> recuperarCitas() {
        List<Cita> citas = new ArrayList<>();
        // Ajuste en la consulta SQL para incluir JOIN con las tablas clientes y mascotas
        String sql = "SELECT c.id, c.titulo, c.fecha, c.hora, c.notas, c.id_cliente, c.id_mascota, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON cl.id = c.id_cliente " + // Asegúrate de que el nombre del campo id en clientes sea correcto
                     "JOIN mascotas m ON m.id = c.id_mascota " + // Asegúrate de que el nombre del campo id en mascotas sea correcto
                     "ORDER BY c.fecha DESC, c.hora DESC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("notas"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_mascota"),
                    rs.getString("nombre_cliente"), // Recuperamos el nombre del cliente
                    rs.getString("nombre_mascota") // Recuperamos el nombre de la mascota
                );
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }



    
    public List<Cita> recuperarCitasHome() {
        List<Cita> citas = new ArrayList<>();
        // Modifica la consulta SQL para incluir JOIN con las tablas clientes y mascotas
        String sql = "SELECT c.id, c.titulo, c.fecha, c.hora, c.notas, c.id_cliente, c.id_mascota, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id " +
                     "JOIN mascotas m ON c.id_mascota = m.id " +
                     "ORDER BY c.fecha ASC, c.hora ASC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                		
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("notas"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_mascota"),
                    rs.getString("nombre_cliente"),
                    rs.getString("nombre_mascota")
                );
                cita.setFecha(rs.getDate("fecha") != null ? rs.getDate("fecha").toLocalDate() : null);
                cita.setHora(rs.getTime("hora") != null ? rs.getTime("hora").toLocalTime() : null);
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }
    
    
    public List<Cita> recuperarCitasFuturas() {
        List<Cita> citas = new ArrayList<>();
        // Asume que tienes una columna 'fecha' y una columna 'hora' en tu tabla 'citas'
        String sql = "SELECT c.*, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id " +
                     "JOIN mascotas m ON c.id_mascota = m.id " +
                     "WHERE (c.fecha > CURRENT_DATE) OR (c.fecha = CURRENT_DATE AND c.hora >= CURRENT_TIME) " +
                     "ORDER BY c.fecha ASC, c.hora ASC";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita(
                    rs.getInt("id"),
                    rs.getString("titulo"),
                    rs.getDate("fecha").toLocalDate(),
                    rs.getTime("hora").toLocalTime(),
                    rs.getString("notas"),
                    rs.getInt("id_cliente"),
                    rs.getInt("id_mascota"),
                    rs.getString("nombre_cliente"),
                    rs.getString("nombre_mascota")
                );
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }
    
    public List<Cita> recuperarCitasParaCalendario() {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.id, c.titulo, c.fecha, c.hora, c.tipo " + // Selecciona solo los campos necesarios
                     "FROM citas c " +
                     "ORDER BY c.fecha ASC, c.hora ASC";

        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setTitulo(rs.getString("titulo"));
                cita.setFecha(rs.getDate("fecha").toLocalDate());
                cita.setHora(rs.getTime("hora").toLocalTime());
                cita.setTipo(rs.getString("tipo")); // Asegúrate de incluir el tipo
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }
    
    public static List<Cita> recuperarCitasPorSemana(LocalDate fechaInicio, LocalDate fechaFin) {
        List<Cita> citas = new ArrayList<>();
        String sql = "SELECT c.id, c.titulo, c.fecha, c.hora, c.tipo, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id " +
                     "JOIN mascotas m ON c.id_mascota = m.id " +
                     "WHERE c.fecha >= ? AND c.fecha <= ? " +
                     "ORDER BY c.fecha ASC, c.hora ASC";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(fechaInicio));
            stmt.setDate(2, Date.valueOf(fechaFin));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	Cita cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setTitulo(rs.getString("titulo"));
                cita.setFecha(rs.getDate("fecha").toLocalDate());
                cita.setHora(rs.getTime("hora").toLocalTime());
                cita.setTipo(rs.getString("tipo")); // Asegúrate de incluir el tipo
                citas.add(cita);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return citas;
    }
    public void actualizarCita(Cita cita) throws SQLException {
        String sql = "UPDATE citas SET titulo = ?, fecha = ?, hora = ?, notas = ?, id_cliente = ?, id_mascota = ?, tipo = ? WHERE id = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, cita.getTitulo());
            pstmt.setDate(2, Date.valueOf(cita.getFecha()));
            pstmt.setTime(3, Time.valueOf(cita.getHora()));
            pstmt.setString(4, cita.getNotas());
            pstmt.setInt(5, cita.getClienteId());
            pstmt.setInt(6, cita.getMascotaId());
            pstmt.setString(7, cita.getTipo());
            pstmt.setInt(8, cita.getId());
            
            pstmt.executeUpdate();
        }
    }
    public void eliminarCita(int idCita) throws SQLException {
        String sql = "DELETE FROM citas WHERE id = ?";
        
        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCita);
            
            pstmt.executeUpdate();
        }
    }
    
    public Cita obtenerCitaPorId(int idCita) {
        String sql = "SELECT c.id, c.titulo, c.fecha, c.hora, c.notas, c.id_cliente, c.id_mascota, c.tipo, cl.nombre AS nombre_cliente, m.nombre AS nombre_mascota " +
                     "FROM citas c " +
                     "JOIN clientes cl ON c.id_cliente = cl.id " +
                     "JOIN mascotas m ON c.id_mascota = m.id " +
                     "WHERE c.id = ?";
        Cita cita = null;

        try (Connection conn = Conexion.getConexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, idCita);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cita = new Cita();
                cita.setId(rs.getInt("id"));
                cita.setTitulo(rs.getString("titulo"));
                cita.setFecha(rs.getDate("fecha").toLocalDate());
                cita.setHora(rs.getTime("hora").toLocalTime());
                cita.setNotas(rs.getString("notas"));
                cita.setClienteId(rs.getInt("id_cliente"));
                cita.setMascotaId(rs.getInt("id_mascota"));
                cita.setTipo(rs.getString("tipo"));
                // Los campos nombre_cliente y nombre_mascota pueden ser usados si necesitas mostrar esta información
                // Pero si solo necesitas la información de la cita, puede que no necesites asignar estos campos aquí.
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cita;
    }



}

