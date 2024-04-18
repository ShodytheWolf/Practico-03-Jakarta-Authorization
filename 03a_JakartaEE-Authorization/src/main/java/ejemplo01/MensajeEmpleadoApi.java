package ejemplo01;

import ejemplo00.aplicacion.MensajeServicios;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/empleado")
public class MensajeEmpleadoApi {
	
	@Inject
	private MensajeServicios servicio;
	
	
	//curl -v http://localhost:8080/03a_JakartaEE-Authorization/mensajes/empleado/enviarMensajeSeguro?mensaje=HolaMundo
	//curl --user usr1:papa -v http://localhost:8080/03a_JakartaEE-Authorization/mensajes/empleado/enviarMensajeSeguro?mensaje=HolaMundo
	//curl --cacert certificadoPrueba.pem --user usr1:papa -v https://localhost:8443/03a_JakartaEE-Authorization/mensajes/empleado/enviarMensajeSeguro?mensaje=HolaMundo
	@GET
	@Path("/enviarMensajeSeguro")
	@Produces({ MediaType.APPLICATION_JSON })
	public String enviarMensaje(@QueryParam("mensaje") String mensaje) {
		System.out.println("Estoy enviando un mensaje con empleado");
		return servicio.enviarMensajeComoEmpleado(mensaje);
	}
	
}
