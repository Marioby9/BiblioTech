package utilidades;

public class Cancion {

	int ID_Cancion;
	int ID_Usuario;
	String nombre;
	String genero;
	String ruta;
	double duracion;
	
	
	public Cancion(int iD_Cancion, int iD_Usuario, String nombre, String genero, String ruta, double duracion) {
		ID_Cancion = iD_Cancion;
		ID_Usuario = iD_Usuario;
		this.nombre = nombre;
		this.genero = genero;
		this.ruta = ruta;
		this.duracion = duracion;
	}
	
	
	
	
	
}
