package api.aplicacion;

import java.util.List;

import api.dominio.Empleado;
import api.dominio.Tarea;
import jakarta.ws.rs.core.Response;

public interface empleadoService {
	
	/*
	 * Agrega un empleado a la lista.
	 * @param empleado objeto empleado que se serializa desde nuestra API
	 */
	public Response agregarEmpleado(Empleado empleado);
	
	/*
	 * Borra un empleado siempre cuando este no cuente con tareas asignadas
	 * @param empleadoID la ID del empleado a borrar
	 * @throws Exception tira excepción cuando no se logra encontrar al empleado con ID: empleadoID
	 */
	public Response borrarEmpleado(int empleadoID) throws Exception;
	
	/*
	 * Devuelve la lista de empleados
	 */
	public List<Empleado> listarEmpleados();
	
	/*
	 * Devuelve todos los datos de un empleado dado.
	 * @param id la ID del empleado a devolver
	 * @throws Exception tira excepción cuando no se logra encontrar al empleado con ID: id
	 */
	public Empleado checkearEmpleado(int id) throws Exception;
	
	/*
	 * Crea y asigna una tarea a un empleado
	 * @param empleadoID el ID del empleado a ser asignada una tarea
	 * @param tarea la descripción de la tarea a ser creada y asignada
	 * @throws Exception tira excepción cuando no se logra encontrar al empleado con ID: empleadoID
	 */
	public void asignarTarea( int empleadoID, String tarea) throws Exception;
	
	/*
	 * Devuelve una lista de las tareas asignadas a un empleado dado.
	 * @param empleadoID el ID del empleado cuyas tareas se devolveran
	 * @throws Exception tira excpeción cuando no se logra encontrar al empleado con empleadoID
	 */
	public List<Tarea> checkearTareasEmpleado(int empleadoID) throws Exception;
}
