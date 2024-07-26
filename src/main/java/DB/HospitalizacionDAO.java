package DB;
	
	import model.Hospitalizacion;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;
	import java.util.ArrayList;
	import java.util.Date;
	import java.util.List;
	import java.sql.Timestamp;
	
	public class HospitalizacionDAO {
	    
	    private Conexion conexion;
	
	    public HospitalizacionDAO() {
	        this.conexion = new Conexion(); // Asumiendo que tienes una clase que maneja la conexión
	    }
	
	    public boolean insertarHospitalizacion(Hospitalizacion hospitalizacion, int idVeterinario) {
	        String sql = "INSERT INTO hospitalizaciones (id_mascota, fecha_ingreso, fecha_salida, motivo, tratamiento, estado, notas, id_veterinario) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
	        
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setInt(1, hospitalizacion.getIdMascota());
	            pstmt.setTimestamp(2, Timestamp.valueOf(hospitalizacion.getFechaIngreso()));
	            if (hospitalizacion.getFechaSalida() != null) {
	                pstmt.setTimestamp(3, Timestamp.valueOf(hospitalizacion.getFechaSalida()));
	            } else {
	                pstmt.setTimestamp(3, null);
	            }
	            pstmt.setString(4, hospitalizacion.getMotivo());
	            pstmt.setString(5, hospitalizacion.getTratamiento());
	            pstmt.setString(6, hospitalizacion.getEstado());
	            pstmt.setString(7, hospitalizacion.getNotas());
	            pstmt.setInt(8, idVeterinario);
	            
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	
	
	
	    public boolean actualizarFechaSalidaHospitalizacion(int idMascota, Date fechaSalida) {
	        String sql = "UPDATE hospitalizaciones SET fecha_salida = ? WHERE id_mascota = ? AND fecha_salida IS NULL";
	        
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setDate(1, new java.sql.Date(fechaSalida.getTime()));
	            pstmt.setInt(2, idMascota);
	            
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public List<Hospitalizacion> obtenerHospitalizacionesPorIdMascota(int idMascota) {
	        List<Hospitalizacion> hospitalizaciones = new ArrayList<>();
	        String sql = "SELECT * FROM hospitalizaciones WHERE id_mascota = ? ORDER BY fecha_ingreso DESC";
	
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setInt(1, idMascota);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                while (rs.next()) {
	                    Hospitalizacion hospitalizacion = new Hospitalizacion(
	                        rs.getInt("id"),
	                        rs.getInt("id_mascota"),
	                        rs.getTimestamp("fecha_ingreso").toLocalDateTime(),
	                        rs.getTimestamp("fecha_salida") != null ? rs.getTimestamp("fecha_salida").toLocalDateTime() : null,
	                        rs.getString("motivo"),
	                        rs.getString("tratamiento"),
	                        rs.getString("estado"),
	                        rs.getString("notas")
	                    );
	                    hospitalizaciones.add(hospitalizacion);
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return hospitalizaciones;
	    }
	    
	    public List<Hospitalizacion> recuperarHospitalizacionesSinFechaSalida() {
	        List<Hospitalizacion> hospitalizaciones = new ArrayList<>();
	        String sql = "SELECT * FROM hospitalizaciones WHERE fecha_salida IS NULL";
	
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {
	            
	            while (rs.next()) {
	                Hospitalizacion hospitalizacion = new Hospitalizacion(
	                    rs.getInt("id"),
	                    rs.getInt("id_mascota"),
	                    rs.getTimestamp("fecha_ingreso").toLocalDateTime(), // Convertido a LocalDateTime
	                    rs.getTimestamp("fecha_salida") != null ? rs.getTimestamp("fecha_salida").toLocalDateTime() : null, // Manejo de null
	                    rs.getString("motivo"),
	                    rs.getString("tratamiento"),
	                    rs.getString("estado"),
	                    rs.getString("notas")
	                );
	                hospitalizaciones.add(hospitalizacion);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return hospitalizaciones;
	    }
	    
	    public List<Hospitalizacion> recuperarTodasLasHospitalizaciones() {
	        List<Hospitalizacion> hospitalizaciones = new ArrayList<>();
	        String sql = "SELECT h.id, h.fecha_ingreso, h.fecha_salida, h.motivo, h.tratamiento, h.estado, h.notas, "
	                     + "m.nombre AS nombre_mascota, v.nombre AS nombre_veterinario "
	                     + "FROM hospitalizaciones h "
	                     + "JOIN mascotas m ON m.id = h.id_mascota "
	                     + "LEFT JOIN veterinarios v ON v.id = h.id_veterinario "
	                     + "ORDER BY h.fecha_ingreso DESC, h.fecha_salida DESC";
	
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql);
	             ResultSet rs = pstmt.executeQuery()) {
	
	            while (rs.next()) {
	                Hospitalizacion hospitalizacion = new Hospitalizacion();
	                hospitalizacion.setId(rs.getInt("id"));
	                hospitalizacion.setFechaIngreso(rs.getTimestamp("fecha_ingreso").toLocalDateTime());
	                hospitalizacion.setFechaSalida(rs.getTimestamp("fecha_salida") != null ? rs.getTimestamp("fecha_salida").toLocalDateTime() : null);
	                hospitalizacion.setMotivo(rs.getString("motivo"));
	                hospitalizacion.setTratamiento(rs.getString("tratamiento"));
	                hospitalizacion.setEstado(rs.getString("estado"));
	                hospitalizacion.setNotas(rs.getString("notas"));
	                hospitalizacion.setNombreMascota(rs.getString("nombre_mascota"));
	                hospitalizacion.setNombreVeterinario(rs.getString("nombre_veterinario")); // Asumiendo que has añadido este campo a tu clase Hospitalizacion
	
	                hospitalizaciones.add(hospitalizacion);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return hospitalizaciones;
	    }
	
	    
	    public int obtenerIdHospitalizacionActual(int idMascota) {
	        String sql = "SELECT id FROM hospitalizaciones WHERE id_mascota = ? AND fecha_salida IS NULL ORDER BY fecha_ingreso DESC LIMIT 1";
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setInt(1, idMascota);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    return rs.getInt("id");
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return -1; // Indica que no se encontró una hospitalización actual
	    }
	    
	    public boolean actualizarHospitalizacion(Hospitalizacion hospitalizacion) {
	        String sql = "UPDATE hospitalizaciones SET fecha_salida = ?, estado = ?, notas = ?, tratamiento = ? WHERE id = ?";
	        
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setTimestamp(1, hospitalizacion.getFechaSalida() == null ? null : Timestamp.valueOf(hospitalizacion.getFechaSalida()));
	            pstmt.setString(2, hospitalizacion.getEstado());
	            pstmt.setString(3, hospitalizacion.getNotas());
	            pstmt.setString(4, hospitalizacion.getTratamiento());
	            pstmt.setInt(5, hospitalizacion.getId());
	            
	            int affectedRows = pstmt.executeUpdate();
	            return affectedRows > 0;
	        } catch (SQLException e) {
	            e.printStackTrace();
	            return false;
	        }
	    }
	    
	    public Hospitalizacion obtenerHospitalizacionPorId(int id) {
	        String sql = "SELECT h.id, h.fecha_ingreso, h.fecha_salida, h.motivo, h.tratamiento, h.estado, h.notas, " +
	                     "m.nombre AS nombre_mascota, v.nombre AS nombre_veterinario " +
	                     "FROM hospitalizaciones h " +
	                     "JOIN mascotas m ON h.id_mascota = m.id " +
	                     "LEFT JOIN veterinarios v ON h.id_veterinario = v.id " +
	                     "WHERE h.id = ?";
	
	        try (Connection conn = conexion.getConexion();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            
	            pstmt.setInt(1, id);
	            try (ResultSet rs = pstmt.executeQuery()) {
	                if (rs.next()) {
	                    Hospitalizacion hospitalizacion = new Hospitalizacion();
	                    hospitalizacion.setId(rs.getInt("id"));
	                    hospitalizacion.setFechaIngreso(rs.getTimestamp("fecha_ingreso").toLocalDateTime());
	                    hospitalizacion.setFechaSalida(rs.getTimestamp("fecha_salida") != null ? rs.getTimestamp("fecha_salida").toLocalDateTime() : null);
	                    hospitalizacion.setMotivo(rs.getString("motivo"));
	                    hospitalizacion.setTratamiento(rs.getString("tratamiento"));
	                    hospitalizacion.setEstado(rs.getString("estado"));
	                    hospitalizacion.setNotas(rs.getString("notas"));
	                    hospitalizacion.setNombreMascota(rs.getString("nombre_mascota"));
	                    hospitalizacion.setNombreVeterinario(rs.getString("nombre_veterinario"));
	                    return hospitalizacion;
	                }
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        
	        
	        return null;
	        
	      
	    }
	
	    
	    
	    
	    
	
	
	
	
	
	
	
	}
