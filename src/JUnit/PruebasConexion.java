package JUnit;

import static org.junit.Assert.*;
import org.junit.Test;
import utilidades.Conexion;
import utilidades.Usuario;

public class PruebasConexion {

	
	/**********************************************CONECTAR***************************************/
	
	@Test
	public void testConectar() {
		boolean conectado = Conexion.conectar();
		assertEquals(true, conectado);
	}
	
	
	/********************************************  BOOLEANS  *************************************/
	
	@Test
	public void testCompruebaLogin() {
		try {
			Conexion.conectar();
			
			boolean accede = Conexion.compruebaLogIn("ADMIN","ADMIN");
			assertEquals(true, accede);
			
			Conexion.cerrar();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testRegistraUsuario() {
		try {
			Conexion.conectar();
			Usuario u = new Usuario("JUNIT", "JUNIT", "junit@mail.com");
			boolean registrado = Conexion.registraUsuario(u);
			assertEquals(true, registrado);
			
			Conexion.cerrar();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	

	/********************************************  INTEGERS  *************************************/

	
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
	public void testTotalElementos() {
		try {
			Conexion.conectar();
			
			int  total = Conexion.totalElementos();
			assertEquals(31, total);
			
			Conexion.cerrar();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testTotalAutores() {
		try {
			Conexion.conectar();
			
			int  total = Conexion.autoresTotales();
			assertEquals(31, total);
			
			Conexion.cerrar();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testUsuBorrados() {
		try {
			Conexion.conectar();
			
			int  total = Conexion.usuBorrados();
			assertEquals(1, total);
			
			Conexion.cerrar();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	
	
	/********************************************  STRINGS  *************************************/

	@Test
	public void testConsultaStr() {
		try {
			Conexion.conectar();
			
			String password = Conexion.consultaStr("USUARIO","PASSWORD", "NICKNAME = ADMIN");
			assertEquals("ADMIN", password);
			
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
