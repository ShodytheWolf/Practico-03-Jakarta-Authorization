package api.aplicacion;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import api.dominio.Empleado;
import api.dominio.Tarea;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class empleadoServiceImpl implements empleadoService {
	
	ArrayList<Empleado> empleados;
	ArrayList<Tarea> tareas;

	@PostConstruct
	public void inicializarEmpleado() {
		empleados = new ArrayList<Empleado>();
		System.out.println("Empleados inicializados");
		tareas = new ArrayList<Tarea>();
		System.out.println("Tareas inicializadas");
	}


	//Cabe destacar, que hace falta el control de IDs, pues al borrar un empleado con ID 2 de una lista de 3 empleados, no se actualizan los IDs de los empleados
	//que estén por delante del empleado borrado, causando así un sobrelapamiento de IDs, se arreglaria con un for que itere la lista de empleados que estén por delante
	//del empleado borrado, y reajustando sus correspondientes IDs
	@Override
	public Response borrarEmpleado(int empleadoID) throws Exception {
		
		try {
			
			Empleado emp = findEmployeeByID(empleadoID);
			
			if (emp.getTareasAsignadas().isEmpty()) {
				
				empleados.remove(emp);
				System.out.println("Se borró exitosamente el empleado: " + emp.getNombre() + " con ID: " + emp.getId());
				
				//Aquí debería haber un FOR que re valorizé los IDs de los empleados por delante del empleado borrado
				
				return Response.ok("Se borró exitosamente el empleado: " + emp.getNombre() + " con ID: " + emp.getId()).build();
			}else {
				return Response.notModified("No se pueden borrar empleados con tareas asignadas").build();
			}	
		} 
		catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}	
	}

	
	
	@Override
	public List<Empleado> listarEmpleados() {
		return empleados;
	}

	
	@Override
	public Empleado checkearEmpleado(int id) throws Exception {
		
		try {
			Empleado response = findEmployeeByID(id);
			return response;
		} 
		catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}

	
	
	@Override
	public void asignarTarea(int empleadoID, String tarea) throws Exception {
		
		try {
			Empleado emp = findEmployeeByID(empleadoID);
			tareas = emp.getTareasAsignadas();
			
			Tarea tareaAAsignar = new Tarea(tareas.size(), tarea, LocalDate.now(), LocalDate.now());
			tareas.add(tareaAAsignar);
			emp.setTareasAsignadas(tareas);
			
			System.out.println("Se asignó la tarea: " + tareaAAsignar.getDescripcion() + "con ID: " + tareaAAsignar.getId() + " al empleado: " + emp.getNombre() + " con ID: " + emp.getId());
			
			tareas = new ArrayList<Tarea>();
		}
		catch (Exception e) {
			throw new InternalServerErrorException(e.getMessage());
		}
	}

	
	@Override
	public List<Tarea> checkearTareasEmpleado(int empleadoID) throws Exception {
		
		try {
			Empleado emp = findEmployeeByID(empleadoID);
			ArrayList<Tarea> tareasAsignadas = emp.getTareasAsignadas();
			return tareasAsignadas;
		} catch (Exception e) {
			throw new NotFoundException(e.getMessage());
		}
	}


	
	@Override
	public Response agregarEmpleado(Empleado empleado) {
		
		empleado.setId(empleados.size());
		empleado.setTareasAsignadas(new ArrayList<Tarea>());
		empleados.add(empleado);
		System.out.println("Se añadió el empleado: " + empleado.getNombre() + " con ID: " + empleado.getId());
		return Response.ok("Se añadió el empleado: " + empleado.getNombre() + " con ID: " + empleado.getId()).build();
		
	}
	
	
	
	//función helper para evitar escribir siempre la misma concatenación de ordenes
	// básicamente busca dentro de nuestra lista de empleados, el empleado con el ID que se pasa por parametro
	//si no se lo encuentra, levanta una excepción del tipo correspondiente
	public Empleado findEmployeeByID(int id) throws Exception {
		
		if(!empleados.isEmpty()) {
			
			for( Empleado emp : empleados) {

				if(emp.getId() == id) {
					System.out.println("Se encontró el empleado: " + emp.getNombre() + " con ID: " + emp.getId());
					return emp;
				}
			}
			throw new NotFoundException("No se encontro un empleado con ID: "+ id);
		}
		throw new InternalServerErrorException("No hay ningún empleado registrado todavía");
	}
	
	
}
