package elementos;

public class Libro {


	private int ID_Libro;
	private int ID_Usuario;
	private int nPanel;
	private String titulo;
	private String genero;
	private String autor;
	private double nPaginas;
	private int lanzamiento;
	private String terminado;
	
	
	public Libro(int iD_Libro, int nPanel,int iD_Usuario, String titulo, String genero, String autor, double nPaginas, int lanzamiento, String terminado) {
		this.ID_Libro = iD_Libro;
		this.ID_Usuario = iD_Usuario;
		this.titulo = titulo;
		this.genero = genero;
		this.autor = autor;
		this.nPaginas = nPaginas;
		this.lanzamiento = lanzamiento;
		this.terminado = terminado;
		
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


	public int getnPanel() {
		return nPanel;
	}


	public void setnPanel(int nPanel) {
		this.nPanel = nPanel;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getGenero() {
		return genero;
	}


	public void setGenero(String genero) {
		this.genero = genero;
	}


	public String getAutor() {
		return autor;
	}


	public void setAutor(String autor) {
		this.autor = autor;
	}


	public double getnPaginas() {
		return nPaginas;
	}


	public void setnPaginas(double nPaginas) {
		this.nPaginas = nPaginas;
	}


	public int getLanzamiento() {
		return lanzamiento;
	}


	public void setLanzamiento(int lanzamiento) {
		this.lanzamiento = lanzamiento;
	}


	public String getTerminado() {
		return terminado;
	}


	public void setTerminado(String terminado) {
		this.terminado = terminado;
	}
}
