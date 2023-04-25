package elementos;

public class Libro {


	private int ID_Libro;
	private int ID_Usuario;
	private String Titulo;
	private String Genero;
	private String Autor;
	private int nPaginas;
	private int Lanzamiento;
	private String terminado;
	private String portada;
	private String resumen;
	
	public Libro(int iD_Libro, int iD_Usuario, String titulo, String genero, String autor, int nPaginas,
			int lanzamiento, String terminado, String portada, String resumen) {
		
		ID_Libro = iD_Libro;
		ID_Usuario = iD_Usuario;
		this.Titulo = titulo;
		this.Genero = genero;
		this.Autor = autor;
		this.nPaginas = nPaginas;
		this.Lanzamiento = lanzamiento;
		this.terminado = terminado;
		this.portada = portada;
		this.resumen = resumen;
	}
	

	public int getID_Libro() {
		return ID_Libro;
	}

	public void setID_Libro(int iD_Libro) {
		ID_Libro = iD_Libro;
	}

	public int getID_Usuario() {
		return ID_Usuario;
	}

	public void setID_Usuario(int iD_Usuario) {
		ID_Usuario = iD_Usuario;
	}

	public String getTitulo() {
		return Titulo;
	}

	public void setTitulo(String titulo) {
		this.Titulo = titulo;
	}

	public String getGenero() {
		return Genero;
	}

	public void setGenero(String genero) {
		this.Genero = genero;
	}

	public String getAutor() {
		return Autor;
	}

	public void setAutor(String autor) {
		this.Autor = autor;
	}

	public int getnPaginas() {
		return nPaginas;
	}

	public void setnPaginas(int nPaginas) {
		this.nPaginas = nPaginas;
	}

	public int getLanzamiento() {
		return Lanzamiento;
	}

	public void setLanzamiento(int lanzamiento) {
		this.Lanzamiento = lanzamiento;
	}

	public String getTerminado() {
		return terminado;
	}

	public void setTerminado(String terminado) {
		this.terminado = terminado;
	}

	public String getPortada() {
		return portada;
	}

	public void setPortada(String portada) {
		this.portada = portada;
	}

	public String getResumen() {
		return resumen;
	}

	public void setResumen(String resumen) {
		this.resumen = resumen;
	}


	@Override
	public String toString() {
		return "Libro [ID_Libro=" + ID_Libro + ", ID_Usuario=" + ID_Usuario + ", titulo=" + Titulo + ", genero="
				+ Genero + ", autor=" + Autor + ", nPaginas=" + nPaginas + ", lanzamiento=" + Lanzamiento
				+ ", terminado=" + terminado + ", portada=" + portada + ", resumen=" + resumen + "]\n";
	}
	
	
	
	
	
	
}