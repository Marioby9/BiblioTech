package elementos;

public class Juego {

	private int ID_Juego;
	private int ID_Usuario;
	private String titulo;
	private String genero;
	private String plataforma;
	private int hJugadas;
	private int lanzamiento;
	private String terminado;
	private String portada;
	private String resumen;
	private String empresa;
	
	public Juego(int iD_Juego, int iD_Usuario, String titulo, String genero, String plataforma,
			int hJugadas, int lanzamiento, String terminado, String portada, String resumen, String empresa) {
		
		ID_Juego = iD_Juego;
		ID_Usuario = iD_Usuario;
		this.titulo = titulo;
		this.genero = genero;
		this.plataforma = plataforma;
		this.hJugadas = hJugadas;
		this.lanzamiento = lanzamiento;
		this.terminado = terminado;
		this.portada = portada;
		this.resumen = resumen;
		this.empresa = empresa;
	}

	public String getEmpresa() {
		return empresa;
	}

	public void setEmpresa(String empresa) {
		this.empresa = empresa;
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

	public int gethJugadas() {
		return hJugadas;
	}

	public void sethJugadas(int hJugadas) {
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


	
	
	
	
	
}
