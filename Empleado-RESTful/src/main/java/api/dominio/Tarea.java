package api.dominio;

import java.time.LocalDate;

public class Tarea {
	private int id;
	private String descripcion;
	private LocalDate fechaIni;
	private LocalDate fechaFin;
	
	
	public Tarea() {
	}
	
	public Tarea(int id, String desc, LocalDate fechaIni, LocalDate fechaFin) {
		super();
		this.setId(id);
		this.setDescripcion(desc);
		this.setFechaIni(fechaIni);
		this.setFechaFin(fechaFin);
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public LocalDate getFechaIni() {
		return fechaIni;
	}


	public void setFechaIni(LocalDate fechaIni) {
		this.fechaIni = fechaIni;
	}


	public LocalDate getFechaFin() {
		return fechaFin;
	}


	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}
	


}
