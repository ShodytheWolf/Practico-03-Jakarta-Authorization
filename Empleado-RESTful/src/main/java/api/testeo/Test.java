package api.testeo;

import java.util.ArrayList;
import java.util.List;

import api.aplicacion.empleadoService;
import api.dominio.Empleado;
import api.dominio.Tarea;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
//@ApplicationPath("/api")
@Path("/")
public class Test extends Application{
	
	@Inject
	private empleadoService empleado;
	
	/**
	 * Ejemplo de API RESTful
	 * 
	 * http://<hostname>:<port>/<context-root>/<REST-config>/<resource-config>

		context-root 		--> (nombre del war por defecto, se puede cambiar agregando archivo conf. )
		REST-config 		--> se establece en web.xml (tambien existe una anotación para poder establecerlo) @ApplicationPath
		resource-config		--> se establece con la anotación  @Path
		
		http://localhost:8080/Empleado-RESTful/api/empleado
	 */
	
	
	/*
	 * curl -X POST -H "Content-Type: application/json" -d '{"cedula":"5334","nombre":"jose"}' http://localhost:8080/Empleado-RESTful/api/empleado/anadir
	 * curl --cacert certificadoSSL.pem --user SAPO-PEPE:pepe -X POST -H "Content-Type: application/json" -d '{"cedula":"5334","nombre":"jose"}' https://localhost:8443/Empleado-RESTful/api/empleado/anadir
	 */
	
	@Path("empleado/anadir")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces({MediaType.APPLICATION_JSON})
	public Response testAgregarEmpleado(Empleado empleadoAAA) {
		
		System.out.println("Estoy apunto de agregar un empleado");
		return empleado.agregarEmpleado(empleadoAAA); //acá estoy devolviendo un response desde el lado de la implementación, no sé hasta que punto quién tiene la responsabilidad de
													   //formular los responses, si la API o la implementación.
	}
	
	/**
	 * curl -v http://localhost:8080/Empleado-RESTful/api/empleado/listar
	 * @return
	 */
	@Path("empleado/listar")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public List<Empleado> testListarEmpleados(){
		System.out.println("Listando todos los empleados!");
		return empleado.listarEmpleados();
	}
	
	
	
	/**
	 * curl -X GET -v http://localhost:8080/Empleado-RESTful/api/empleado/checkear/1
	 * @return
	 * @throws Exception 
	 */
	@Path("empleado/checkear/{id}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response testCheckearEmpleado(@PathParam("id") Integer id ) throws Exception{
		
		try {
			System.out.println("Checkeando el empleado con ID: " + id);
			
			Empleado empleadoADevolver = empleado.checkearEmpleado(id);
			return Response.ok(empleadoADevolver).build();
			
		} catch (Exception e) {
			
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	
	/*
	 * curl -X PUT http://localhost:8080/Empleado-RESTful/api/tarea/asignar/0/EstoEsUnaTarea
	 */
	
	@PUT
	@Path("tarea/asignar/{empleadoID}/{tarea}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response testAsignarTarea(@PathParam("tarea") String tarea, @PathParam("empleadoID") Integer empleadoID) throws Exception {
	    try {
	    	
	    	System.out.println("Estoy apunto de asignar la tarea: " + tarea + " al empleado con ID: " + empleadoID);
	        empleado.asignarTarea(empleadoID, tarea);
	        return Response.ok("Tarea asignada correctamente").build();
	        
	    } catch (NotFoundException e) { //código provisto por chatGPT, básicamente estámos generando una respuesta para el cliente en base a la excepción que nos tiré el servidor
	    	
	        return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build(); //acá se catchea la excepción, se le asigna un Status de HTTP, y se lo arrojamos al usuario
	        																				  //con .build();
	    } catch (InternalServerErrorException e) {
	        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build(); //acá lo mismo pero con otro STATUS de HTTP
	    }
	}
	
	
	
	
	/**
	 * curl -X DELETE -v http://localhost:8080/Empleado-RESTful/api/empleado/borrar/1
	 * curl --cacert certificadoSSL.pem --user SAPO-PEPE:pepe -X DELETE -v https://localhost:8443/Empleado-RESTful/api/empleado/borrar/1
	 * @return
	 * @throws Exception 
	 */
	
	@Path("empleado/borrar/{empleadoID}")
	@DELETE
	@Produces({MediaType.APPLICATION_JSON})
	public Response testBorrarEmpleado(@PathParam("empleadoID") Integer empleadoID) {
		
		try {
			System.out.println("Intentando borrar el empleado con ID: " + empleadoID);
			return empleado.borrarEmpleado(empleadoID);
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
	
	
	/**
	 * curl -X GET -v http://localhost:8080/Empleado-RESTful/api/tarea/listar/1
	 * curl --cacert certificadoSSL.pem  --user VACA-LOLA:vacaloka -X GET -v https://localhost:8443/Empleado-RESTful/api/tarea/listar/1
	 * @return
	 */
	
	@Path("tarea/listar/{empleadoID}")
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response testListarTareas(@PathParam("empleadoID") Integer empleadoID){
		try {
			
			System.out.println("Listando todas las tareas!");
			ArrayList<Tarea> tareasDelEmpleado = (ArrayList<Tarea>) empleado.checkearTareasEmpleado(empleadoID);
			return Response.ok(tareasDelEmpleado).build();
			
		} catch (Exception e) {
			return Response.status(Response.Status.NOT_FOUND).entity(e.getMessage()).build();
		}
	}
}
