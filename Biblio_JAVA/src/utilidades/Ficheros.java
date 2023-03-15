package utilidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;


public class Ficheros {

	
	public static String leeResumen(String ruta) throws Exception {
		URL url = Ficheros.class.getResource(ruta);
		File file = new File(url.toURI());
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		String resumen = "";
		String linea;
		while((linea = br.readLine()) != null) {
			resumen+=linea;
		}
		
		br.close();
		fr.close();
		
		return resumen;
	}
	
	
	
}
