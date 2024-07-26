package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
	
	private int id;
    private String titulo;
    private LocalDate fecha;
    private LocalTime hora;
    private String notas;
    private int clienteId; // ID del cliente
    private int mascotaId; // ID de la mascota
    private String nombreCliente; // Opcional: Para mostrar en la UI
    private String nombreMascota;
    private String tipo;
    private int veterinarioId;
    
    
    public int getVeterinarioId() {
        return veterinarioId;
    }

    public void setVeterinarioId(int veterinarioId) {
        this.veterinarioId = veterinarioId;
    }
    
    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public LocalDate getFecha() {
		return fecha;
	}
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}
	public LocalTime getHora() {
		return hora;
	}
	public void setHora(LocalTime hora) {
		this.hora = hora;
	}
	public String getNotas() {
		return notas;
	}
	public void setNotas(String notas) {
		this.notas = notas;
	}
	public int getClienteId() {
		return clienteId;
	}
	public void setClienteId(int clienteId) {
		this.clienteId = clienteId;
	}
	public int getMascotaId() {
		return mascotaId;
	}
	public void setMascotaId(int mascotaId) {
		this.mascotaId = mascotaId;
	}
	
	public Cita(int id, String titulo, LocalDate fecha, LocalTime hora, String notas, int clienteId, int mascotaId) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        this.notas = notas;
        this.clienteId = clienteId;
        this.mascotaId = mascotaId;
    }
	public Cita(int id, String titulo, LocalDate fecha, LocalTime hora, String notas, int clienteId, int mascotaId, String nombreCliente, String nombreMascota) {
        this(id, titulo, fecha, hora, notas, clienteId, mascotaId); // Llama al constructor anterior
        this.nombreCliente = nombreCliente;
        this.nombreMascota = nombreMascota;
    }
    
 // Getters y setters para los nuevos campos
	public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }
    
    // Constructor, getters y setters...
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public Cita() {
        // Constructor sin argumentos
    }
    
    // Constructor que incluye veterinarioId
    public Cita(int id, String titulo, LocalDate fecha, LocalTime hora, String notas, int clienteId, int mascotaId, int veterinarioId) {
        this.id = id;
        this.titulo = titulo;
        this.fecha = fecha;
        this.hora = hora;
        this.notas = notas;
        this.clienteId = clienteId;
        this.mascotaId = mascotaId;
        this.veterinarioId = veterinarioId; // Asigna el ID del veterinario
    }

    
    
    
    
    

}



