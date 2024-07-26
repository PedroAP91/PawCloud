package model;

import java.math.BigDecimal;
import java.sql.Date;

public class Farmaco {
   
	private int id;
	private String codigo;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private String dosisRecomendada;
    private String unidadMedida;
    private Date fechaCaducidad;
    private BigDecimal precio;

    public int getId() {
 		return id;
 	}
 	public void setId(int id) {
 		this.id = id;
 	}
 	public String getCodigo() {
 		return codigo;
 	}
 	public void setCodigo(String codigo) {
 		this.codigo = codigo;
 	}
 	public String getNombre() {
 		return nombre;
 	}
 	public void setNombre(String nombre) {
 		this.nombre = nombre;
 	}
 	public String getDescripcion() {
 		return descripcion;
 	}
 	public void setDescripcion(String descripcion) {
 		this.descripcion = descripcion;
 	}
 	public int getCantidad() {
 		return cantidad;
 	}
 	public void setCantidad(int cantidad) {
 		this.cantidad = cantidad;
 	}
 	public String getDosisRecomendada() {
 		return dosisRecomendada;
 	}
 	public void setDosisRecomendada(String dosisRecomendada) {
 		this.dosisRecomendada = dosisRecomendada;
 	}
 	public String getUnidadMedida() {
 		return unidadMedida;
 	}
 	public void setUnidadMedida(String unidadMedida) {
 		this.unidadMedida = unidadMedida;
 	}
 	public Date getFechaCaducidad() {
 		return fechaCaducidad;
 	}
 	public void setFechaCaducidad(Date fechaCaducidad) {
 		this.fechaCaducidad = fechaCaducidad;
 	}
 	public BigDecimal getPrecio() {
 		return precio;
 	}
 	public void setPrecio(BigDecimal precio) {
 		this.precio = precio;
 	}
 	
 	public Farmaco() {
    }

    // Constructor con todos los parámetros
    public Farmaco(int id, String codigo, String nombre, String descripcion, int cantidad,
                   String dosisRecomendada, String unidadMedida, Date fechaCaducidad, BigDecimal precio) {
        this.id = id;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.dosisRecomendada = dosisRecomendada;
        this.unidadMedida = unidadMedida;
        this.fechaCaducidad = fechaCaducidad;
        this.precio = precio;
    }
    
    @Override
    public String toString() {
        // Asumiendo que tienes un atributo 'nombre' en tu clase Farmaco
        return this.nombre;
    }

}
