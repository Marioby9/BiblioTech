package utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Scanner;


/**
 * ESTA CLASE CONSISTE EN PASAR A HASH LA CONTRASENA QUE INTRODUCE EL USUARIO AL REGISTRARSE
 * 
 */

public class ProtegePassword {

	private static final String algoritmo = "SHA-224"; //PARA CIFRADO HASH

	/**
	 * //RECIBE LA CONTRASENA ESCRITA Y LA CONVIERTE A HASH. DESPUES LA PASA A STRING PARA PODER INSERTAR EL HASH EN LA BBDD
	 * @param password
	 * @return EL HASH EN FORMATO STRING
	 * @throws NoSuchAlgorithmException
	 */
	public static String cifradoHash(String password) throws NoSuchAlgorithmException{ 
		MessageDigest md = MessageDigest.getInstance(algoritmo);
		md.update(password.getBytes());
		byte[] hash = md.digest();
		return Arrays.toString(hash); 
	}

	/**
	 * //RECIBE LA CONTRASENA ESCRITA AL LOGEAR Y LA CONVIERTE A HASH. DESPUES COMPRUEBA SI SU HASH ESTA EN LA BBDD
	 * @param password(DEL login), hash(DE LA BBDD)
	 * @return BOOLEAN QUE VERIFICA
	 * @throws NoSuchAlgorithmException
	 */

	public static boolean compruebaHash(String password, String hash) throws NoSuchAlgorithmException {
		String hashedPassword = cifradoHash(password);  
		return hashedPassword.equals(hash);  
	}


	//CIFRADO ASCII
	public static String cifradoASCII(String password){ 
		char[] pass = password.toCharArray();
		int [] valores = new int [pass.length];
		for(int i=0; i<valores.length; i++) {
			valores[i] = pass[i];
		}

		return Arrays.toString(valores);

	}

	public static boolean compruebaASCII(String password, String ascii){
		String asciiPassword = cifradoASCII(password); 
		return asciiPassword.equals(ascii); 
	}


	//CIFRADO PROPIO
	public static String cifradoBibliotech(String password) {
		String passFinal = "";
		

		return passFinal;
	}

	public static boolean compruebaBiblio(String password, String cifrado){
		String biblioPassword = cifradoBibliotech(password); 
		return biblioPassword.equals(cifrado); 
	}


	//PRUEBAS PARA VER LA EFICIENCIA DE CADA METODO DE CIFRADO
	public static void main(String[] args) { 
		Scanner sc = new Scanner(System.in);

		try {
			//METODO HASH
			System.out.print("Introduce contraseña: "); String pass = sc.next();
			long t0 = System.currentTimeMillis();
			System.out.println("Cifrado HASH: "+cifradoHash(pass));
			long th = System.currentTimeMillis() - t0;
			System.out.print("Introduce contraseña: ");  String pass1 = sc.next();
			System.out.println("Comprueba Hash: "+compruebaHash(pass, cifradoHash(pass1)));


			//METODO ASCII
			t0 = System.currentTimeMillis();
			System.out.println("\nCifrado ASCII: "+cifradoASCII(pass));
			long ta = System.currentTimeMillis() - t0;
			System.out.println("Comprueba ASCII: "+compruebaASCII(pass, cifradoASCII(pass1)));


			//METODO BIBLIOTECH
			t0 = System.currentTimeMillis();
			System.out.println("\nCifrado Biblio: "+cifradoBibliotech(pass));
			long tb = System.currentTimeMillis() - t0;
			System.out.println(cifradoBibliotech(pass).length());
			System.out.println("Comprueba Biblio: "+compruebaBiblio(pass, cifradoBibliotech(pass1)));


			//TIEMPOS
			System.out.println("\nEl hash tarda en cifrar: "+th+" ms."); //15-20 ms de media
			System.out.println("\nEl ASCII tarda en cifrar: "+ta+" ms."); //0 ms de media
			System.out.println("\nEl Biblio tarda en cifrar: "+tb+" ms."); 


		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			sc.close();
		}
	}
}
