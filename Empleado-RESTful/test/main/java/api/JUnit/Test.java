package api.JUnit;


import api.aplicacion.empleadoService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Test {
	@Inject
	private empleadoService empleado;
	

	@Test
	public void testAgregarEmpleado() {
		empleado.agregarEmpleado("José", 53349029);
	}
}
