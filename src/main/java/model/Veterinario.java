package model;

import java.sql.Date;

public class Veterinario {
    private int id;
    private String nombre;
    private String apellidos;
    private String licencia;
    private String telefono;
    private String email;
    private String especialidades;
    private String horarioTrabajo;
    private Date fechaContratacion; 
    
    
    
    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getLicencia() {
		return licencia;
	}

	public void setLicencia(String licencia) {
		this.licencia = licencia;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEspecialidades() {
		return especialidades;
	}

	public void setEspecialidades(String especialidades) {
		this.especialidades = especialidades;
	}

	public String getHorarioTrabajo() {
		return horarioTrabajo;
	}

	public void setHorarioTrabajo(String horarioTrabajo) {
		this.horarioTrabajo = horarioTrabajo;
	}

	public Date getFechaContratacion() {
		return fechaContratacion;
	}

	public void setFechaContratacion(Date fechaContratacion) {
		this.fechaContratacion = fechaContratacion;
	}

    // Constructor sin parámetros
    public Veterinario() {}

    // Constructor completo
    public Veterinario(int id, String nombre, String apellidos, String licencia, String telefono, String email, String especialidades, String horarioTrabajo, Date fechaContratacion) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.licencia = licencia;
        this.telefono = telefono;
        this.email = email;
        this.especialidades = especialidades;
        this.horarioTrabajo = horarioTrabajo;
        this.fechaContratacion = fechaContratacion;
    }
    
    @Override
    public String toString() {
        return nombre + " " + apellidos; // Asegúrate de tener los campos nombre y apellidos.
    }

    
    

    
}
