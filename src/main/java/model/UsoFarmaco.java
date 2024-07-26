package model;

import java.time.LocalDateTime;

public class UsoFarmaco {
    private int idFarmaco;
    private int idHospitalizacion;
    private int idVeterinario;
    private int cantidadUsada;
    private LocalDateTime fechaHoraUso;
    private String nombreFarmaco;
    private String dosis;
    private String frecuencia;
    private String codigoFarmaco;
    private String nombreMascota;

    // Constructor completo
    public UsoFarmaco(int idFarmaco, int idHospitalizacion, int idVeterinario, int cantidadUsada, String nombreFarmaco, String dosis, String frecuencia, String codigoFarmaco) {
        this.idFarmaco = idFarmaco;
        this.idHospitalizacion = idHospitalizacion;
        this.idVeterinario = idVeterinario;
        this.cantidadUsada = cantidadUsada;
        this.nombreFarmaco = nombreFarmaco;
        this.dosis = dosis;
        this.frecuencia = frecuencia;
        this.codigoFarmaco = codigoFarmaco;
    }
    public UsoFarmaco() {
    	
    }
    
    public LocalDateTime getFechaHoraUso() { return fechaHoraUso; }
    public void setFechaHoraUso(LocalDateTime fechaHoraUso) { this.fechaHoraUso = fechaHoraUso; }

    // Getters y Setters
    public int getIdFarmaco() { return idFarmaco; }
    public void setIdFarmaco(int idFarmaco) { this.idFarmaco = idFarmaco; }

    public int getIdHospitalizacion() { return idHospitalizacion; }
    public void setIdHospitalizacion(int idHospitalizacion) { this.idHospitalizacion = idHospitalizacion; }

    public int getIdVeterinario() { return idVeterinario; }
    public void setIdVeterinario(int idVeterinario) { this.idVeterinario = idVeterinario; }

    public int getCantidadUsada() { return cantidadUsada; }
    public void setCantidadUsada(int cantidadUsada) { this.cantidadUsada = cantidadUsada; }

    public String getNombreFarmaco() { return nombreFarmaco; }
    public void setNombreFarmaco(String nombreFarmaco) { this.nombreFarmaco = nombreFarmaco; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public String getCodigoFarmaco() { return codigoFarmaco; }
    public void setCodigoFarmaco(String codigoFarmaco) { this.codigoFarmaco = codigoFarmaco; }
    
    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }
}
