package model;

import java.time.LocalDate;
import java.util.Objects;

public class Mascota {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private int idCliente; // Clave foránea que apunta al ID del cliente
    private String microchip;
    private LocalDate fechaNacimiento;
    private String caracter;
    private String color;
    private String tipoPelo;
    private Sexo sexo; // Cambio de String a enum
    private boolean esterilizado;

    public enum Sexo {
        MACHO, HEMBRA
    }

    // Constructor modificado para aceptar Sexo como enum
    public Mascota(int id, String nombre, String especie, String raza, int edad, int idCliente, 
                   String microchip, LocalDate fechaNacimiento, String caracter, String color, 
                   String tipoPelo, Sexo sexo, boolean esterilizado) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.idCliente = idCliente;
        this.microchip = microchip;
        this.fechaNacimiento = fechaNacimiento;
        this.caracter = caracter;
        this.color = color;
        this.tipoPelo = tipoPelo;
        this.sexo = sexo; // Asignar directamente el enum
        this.esterilizado = esterilizado;
    }

    public Mascota() {
        // Constructor vacío
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo;
    }

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

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public String getMicrochip() {
        return microchip;
    }

    public void setMicrochip(String microchip) {
        this.microchip = microchip;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getCaracter() {
        return caracter;
    }

    public void setCaracter(String caracter) {
        this.caracter = caracter;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getTipoPelo() {
        return tipoPelo;
    }

    public void setTipoPelo(String tipoPelo) {
        this.tipoPelo = tipoPelo;
    }

    // Getters y Setters para el campo `sexo`, utilizando el enum Sexo
    public Sexo getSexo() {
        return sexo;
    }

   
    public boolean isEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(boolean esterilizado) {
        this.esterilizado = esterilizado;
    }
    
    public static class MascotaContenedor {
        private Mascota mascota;

        public MascotaContenedor(Mascota mascota) {
            this.mascota = mascota;
        }

        public Mascota getMascota() {
            return mascota;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            MascotaContenedor that = (MascotaContenedor) o;
            return Objects.equals(mascota.getId(), that.mascota.getId());
        }

        @Override
        public int hashCode() {
            return Objects.hash(mascota.getId());
        }

        @Override
        public String toString() {
            return mascota.getNombre(); // Suponiendo que quieres mostrar solo el nombre en el comboBox
        }
    }


    @Override
    public String toString() {
        return "Mascota{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", edad=" + edad +
                ", idCliente=" + idCliente +
                ", microchip='" + microchip + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", caracter='" + caracter + '\'' +
                ", color='" + color + '\'' +
                ", tipoPelo='" + tipoPelo + '\'' +
                ", sexo='" + (sexo != null ? sexo.name() : "null") + '\'' +
                ", esterilizado=" + esterilizado +
                '}';
    }
}