package elementos;

public class Juego {

	private int ID_Juego;
	private int ID_Usuario;
	private int nPanel;
	private String titulo;
	private String genero;
	private String plataforma;
	private double hJugadas;
	private int lanzamiento;
	private String terminado;
	
	
	public Juego(int iD_Juego, int nPanel,int iD_Usuario, String titulo, String genero, String plataforma, double hJugadas, int lanzamiento, String terminado) {
		this.ID_Juego = iD_Juego;
		this.ID_Usuario = iD_Usuario;
		this.titulo = titulo;
		this.genero = genero;
		this.plataforma = plataforma;
		this.hJugadas = hJugadas;
		this.lanzamiento = lanzamiento;
		this.terminado = terminado;
		
	}


	public int getID_Juego() {
		return ID_Juego;
	}


	public void setID_Juego(int iD_Juego) {
		ID_Juego = iD_Juego;
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


	public String getPlataforma() {
		return plataforma;
	}


	public void setPlataforma(String plataforma) {
		this.plataforma = plataforma;
	}


	public double gethJugadas() {
		return hJugadas;
	}


	public void sethJugadas(double hJugadas) {
		this.hJugadas = hJugadas;
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
