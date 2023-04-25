package elementos;

public class Cancion {

	private int ID_Cancion;
	private int ID_Usuario;
	private String nombre;
	private String genero;
	private String ruta;
	private double duracion;
	
	
	public Cancion(int iD_Cancion, int iD_Usuario, String nombre, String genero, String ruta, double duracion) {
		ID_Cancion = iD_Cancion;
		ID_Usuario = iD_Usuario;
		this.nombre = nombre;
		this.genero = genero;
		this.ruta = ruta;
		this.duracion = duracion;
	}


	public int getID_Cancion() {
		return ID_Cancion;
	}


	public void setID_Cancion(int iD_Cancion) {
		ID_Cancion = iD_Cancion;
	}


	public int getID_Usuario() {
		return ID_Usuario;
	}


	public void setID_Usuario(int iD_Usuario) {
		ID_Usuario = iD_Usuario;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public String getRuta() {
		return ruta;
	}


	public void setRuta(String ruta) {
		this.ruta = ruta;
	}


	public double getDuracion() {
		return duracion;
	}


	public void setDuracion(double duracion) {
		this.duracion = duracion;
	}
	
	
	
	
	
}