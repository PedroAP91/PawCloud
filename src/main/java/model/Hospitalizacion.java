package model;

import java.time.LocalDateTime;

public class Hospitalizacion {
    private int id;
    private int idMascota;
    private int idVeterinario; 
    private LocalDateTime fechaIngreso;
    private LocalDateTime fechaSalida;
    private String motivo;
    private String tratamiento;
    private String estado;
    private String notas;
    private String nombreMascota;
    private String nombreVeterinario;

    // Constructor vacío
    public Hospitalizacion() {
    }

    // Constructor para inserción/actualización (sin nombreVeterinario)
    public Hospitalizacion(int id, int idMascota, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, String motivo, String tratamiento, String estado, String notas) {
        this.id = id;
        this.idMascota = idMascota;
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.motivo = motivo;
        this.tratamiento = tratamiento;
        this.estado = estado;
        this.notas = notas;
    }

    // Constructor completo (incluye nombres)
    public Hospitalizacion(int id, int idMascota, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, String motivo, String tratamiento, String estado, String notas, String nombreMascota, String nombreVeterinario) {
        this(id, idMascota, fechaIngreso, fechaSalida, motivo, tratamiento, estado, notas);
        this.nombreMascota = nombreMascota;
        this.nombreVeterinario = nombreVeterinario;
    }
    
    
 // Constructor completo actualizado para incluir idVeterinario
    public Hospitalizacion(int id, int idMascota, int idVeterinario, LocalDateTime fechaIngreso, LocalDateTime fechaSalida, String motivo, String tratamiento, String estado, String notas, String nombreMascota, String nombreVeterinario) {
        this.id = id;
        this.idMascota = idMascota;
        this.idVeterinario = idVeterinario; // Agregar este parámetro
        this.fechaIngreso = fechaIngreso;
        this.fechaSalida = fechaSalida;
        this.motivo = motivo;
        this.tratamiento = tratamiento;
        this.estado = estado;
        this.notas = notas;
        this.nombreMascota = nombreMascota;
        this.nombreVeterinario = nombreVeterinario;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(int idMascota) {
        this.idMascota = idMascota;
    }

    public LocalDateTime getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDateTime fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public LocalDateTime getFechaSalida() {
        return fechaSalida;
    }

    public void setFechaSalida(LocalDateTime fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getTratamiento() {
        return tratamiento;
    }

    public void setTratamiento(String tratamiento) {
        this.tratamiento = tratamiento;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getNombreVeterinario() {
        return nombreVeterinario;
    }

    public void setNombreVeterinario(String nombreVeterinario) {
        this.nombreVeterinario = nombreVeterinario;
    }
    
    public int getIdVeterinario() {
        return idVeterinario;
    }

    public void setIdVeterinario(int idVeterinario) {
        this.idVeterinario = idVeterinario;
    }

    // Método toString
    @Override
    public String toString() {
        return "Hospitalizacion{" +
                "id=" + id +
                ", idMascota=" + idMascota +
                ", idVeterinario=" + idVeterinario + // Mostrar idVeterinario en toString
                ", fechaIngreso=" + fechaIngreso +
                ", fechaSalida=" + fechaSalida +
                ", motivo='" + motivo + '\'' +
                ", tratamiento='" + tratamiento + '\'' +
                ", estado='" + estado + '\'' +
                ", notas='" + notas + '\'' +
                ", nombreMascota='" + nombreMascota + '\'' +
                ", nombreVeterinario='" + nombreVeterinario + '\'' +
                '}';
    }
}
