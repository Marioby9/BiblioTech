package utilidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;
import java.util.stream.Stream;
import elementos.Cancion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Ficheros {

	public static void main(String[] args) {
	}
	
	//TEMAS CARPETAS MUSICA
	
	public static ObservableList<Cancion> leeCarpetaMus(String carpeta, String lista) throws Exception {
		ObservableList<Cancion> listaCanciones = FXCollections.observableArrayList();
		String titulo, nomArch, artista, ruta, extension;
		Path directory = Paths.get(carpeta+"\\"+lista);
		Stream<Path> archivos = Files.list(directory);
		Iterator it = archivos.iterator();
		
		while(it.hasNext()) {
			File file = ((Path)it.next()).toFile();
			nomArch = file.getName().toString();
			extension = nomArch.substring(nomArch.length()-4, nomArch.length());

			if(extension.equalsIgnoreCase(".mp3")) {
				String [] separados = nomArch.replace(".mp3", "").split("-");
				artista = separados[0].replace("_", " ");
				titulo = separados[1].replace("_", " ");
				ruta = carpeta+"\\"+lista+"\\"+nomArch;
				listaCanciones.add(new Cancion(0, 0, titulo, artista, lista, ruta, 0));
			}
			
		}
		return listaCanciones;
	}
	
	
	public static void creaCarpetasMus(String carpeta) throws IOException { //CREA LAS CARPETAS DE LAS LISTAS DE REPRODUCCION DE MUSICA
		Path reggaeton = Paths.get(carpeta+"\\Reggaeton");
		Path pop = Paths.get(carpeta+"\\Pop");
		Path rock = Paths.get(carpeta+"\\Rock");
		Path electronica = Paths.get(carpeta+"\\Electronica");
		Path flamenco = Paths.get(carpeta+"\\Flamenco");
		Path papelera = Paths.get(carpeta+"\\Papelera");

		Files.createDirectories(reggaeton); Files.createDirectories(pop); Files.createDirectories(rock);
		Files.createDirectories(electronica); Files.createDirectories(flamenco); Files.createDirectories(papelera);

	}

	public static boolean mueveCancion(File f, String carpeta) throws IOException {
		boolean movido = false;
		String rutaPap = carpeta+"\\Papelera";
		File papelera = new File(rutaPap);
		if(f.exists() && papelera.exists() && papelera.isDirectory()) {
			Files.move(Paths.get(f.getAbsolutePath()), Paths.get(rutaPap+"\\"+f.getName()), StandardCopyOption.REPLACE_EXISTING);
			movido = true;
		}
		else {
			System.out.println("No se puede mover el archivo");
			movido = false;
		}


		return movido;
	}
	

	//LEER RESUMENES Y FICHEROS

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
