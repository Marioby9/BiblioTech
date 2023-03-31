package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;


public class Ficheros {

	//HAY 2 FORMAS PARA GUARDAR LAS RUTAS (ABSOLUTA Y RELATIVA) Y PARA CADA UNA HAY QUE DECLARAR EL FILE DE FORMA DISTINTA
	public static String leeResumen(String ruta) throws Exception {
		String resumen = "";
		String linea;
		URL url;
		File file = new File(ruta);


		if(!file.isAbsolute()) {
			url = Ficheros.class.getResource(ruta);
			file = new File(url.toURI());
		}

		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);

		while((linea = br.readLine()) != null) {
			resumen+=linea;
		}
		br.close();
		fr.close();

		return resumen;
	}

	public static void escribeResumen(File file, String texto) throws Exception{

		FileWriter fw = new FileWriter(file);
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(texto);

		bw.close();
		fw.close();

	}

	public static void borraResumen(String ruta) throws Exception {
		File file = new File(ruta);
		

		if(file.delete()) {
			System.out.println("Resumen borrado: "+ ruta);
		}
		else {
			System.out.println("Error al borrar el resumen");
		}
	}

}
