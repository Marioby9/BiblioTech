package elementos;

public class Cancion {

	private int ID_Cancion;
	private int ID_Usuario;
	private String nombre;
	private String genero;
	private String artista;
	private String ruta;

	
	
	public Cancion(int iD_Cancion, int iD_Usuario, String nombre, String artista, String genero, String ruta) {
		ID_Cancion = iD_Cancion;
		ID_Usuario = iD_Usuario;
		this.nombre = nombre;
		this.artista = artista;
		this.genero = genero;
		this.ruta = ruta;
		
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


	public String getArtista() {
		return artista;
	}


	public void setArtista(String artista) {
		this.artista = artista;
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


	
	
	
	
}
