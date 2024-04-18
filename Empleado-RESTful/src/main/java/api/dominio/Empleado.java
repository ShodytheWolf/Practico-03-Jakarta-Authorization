package api.dominio;

import java.util.ArrayList;

public class Empleado {
	private int id;
	private String nombre;
	private int cedula;
	private ArrayList<Tarea> tareasAsignadas;
	
//	@PostConstruct
//	public void inicializarTareas() {
//		
//	}
	
	public Empleado() {
	}
	
	public Empleado(int id, String nombre, int cedula, ArrayList<Tarea> tareas) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.cedula = cedula;
		
		if(tareas == null || tareas.isEmpty()) {
			tareasAsignadas = new ArrayList<Tarea>();
		}
		else {
			this.tareasAsignadas = tareas;
		}

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

	public int getCedula() {
		return cedula;
	}

	public void setCedula(int cedula) {
		this.cedula = cedula;
	}

	public ArrayList<Tarea> getTareasAsignadas() {
		return tareasAsignadas;
	}

	public void setTareasAsignadas(ArrayList<Tarea> tareasAsignadas) {
		this.tareasAsignadas = tareasAsignadas;
	}
}
