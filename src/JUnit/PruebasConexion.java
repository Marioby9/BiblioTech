package JUnit;

import static org.junit.Assert.*;
import org.junit.Test;
import utilidades.Conexion;

public class PruebasConexion {

	@Test
	public void testConectar() {
		boolean conectado = Conexion.conectar();
		assertEquals(true, conectado);
	}

	@Test
	public void testUsuTotales() {
		try {
			Conexion.conectar();
			
			int numUsu = Conexion.usuTotales();
			System.out.println(numUsu);
			assertEquals(15, numUsu);
			
			Conexion.cerrar();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}






	@Test
	public void testCerrar() {
		try {
			boolean cerrado = Conexion.cerrar();
			assertEquals(true, cerrado);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
