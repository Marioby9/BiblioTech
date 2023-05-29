package AdminApp;

import java.io.File;

import elementos.Cancion;
import elementos.Juego;
import elementos.Libro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import utilidades.Conexion;
import utilidades.Usuario;

public class AdminContenidoController {

	@FXML private AnchorPane pRootContenido;
	@FXML private Pane pContenido, pFiltros;
	@FXML protected Pane pLibIndiv, pJueIndiv, pMusIndiv;

	@FXML private ImageView imgNickname;
	@FXML private ImageView imgNicknameVerde;

	@FXML private ImageView imgCorreo;
	@FXML private ImageView imgCorreoVerde;

	@FXML private ImageView imgPassword;
	@FXML private ImageView imgPasswordVerde;


	@FXML private TextField txtFiltro;

	/*TABLE VIEWS*/
	/* -------------------- TABLE VIEW -----------------*/
	@FXML private TableView<Usuario> tablaUsuarios;
	@FXML private TableColumn<Usuario, Integer> idUsu;
	@FXML private TableColumn<Usuario, String>  nicknameUsu;
	@FXML private TableColumn<Usuario, String>  passwordUsu;
	@FXML private TableColumn<Usuario, String> correoUsu;

	@FXML private TableView<Libro> tablaLibros;
	@FXML private TableColumn<Libro, Integer> idLibro;
	@FXML private TableColumn<Libro, String>  nombreLibro;

	@FXML private TableView<Juego> tablaJuegos;
	@FXML private TableColumn<Juego, Integer> idJuego;
	@FXML private TableColumn<Juego, String>  nombreJuego;
	
	@FXML private TableView<Cancion> tablaMusica;
	@FXML private TableColumn<Cancion, Integer> idCancion;
	@FXML private TableColumn<Cancion, String>  nombreCancion;


	/*TEXTFIELDS*/
	@FXML private TextField tituloLibro, autorLibro, fechaLibro, pagsLibro, generoLibro, portadaLibro;
	@FXML private TextArea resumenLibro;

	@FXML private TextField tituloJuego, empresaJuego, plataformaJuego, fechaJuego, horasJuego, generoJuego, portadaJuego;
	@FXML private TextArea resumenJuego;
	
	@FXML private TextField tituloCancion, artistaCancion, generoCancion, rutaCancion;

	private String filtro, paramFil;

	private Usuario usuSelec;   
	private Libro libSelec;
	private Juego jueSelec;
	private Cancion canSelec;

	protected AdminMenuController menu;
	
	protected void initComponents() {
		refreshTablas(null);
		ocultaPaneles();
		vaciaTextos();
	}

	//GESTION CONTENIDO ADMIN
	@FXML void clickAdminUsuarioGestionContenido(MouseEvent event) {

		imgNickname.setVisible(false);
		imgNicknameVerde.setVisible(true);
		imgCorreo.setVisible(true);
		imgCorreoVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}

	@FXML void clickAdminUsuarioGestionContenidoVolver(MouseEvent event) {
		imgNickname.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgCorreo.setVisible(true);
		imgCorreoVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}

	@FXML void clickAdminCorreoGestionContenido(MouseEvent event) {
		imgCorreo.setVisible(false);
		imgCorreoVerde.setVisible(true);
		imgNickname.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}

	@FXML void clickAdminCorreoGestionContenidoVolver(MouseEvent event) {
		imgCorreo.setVisible(true);
		imgCorreoVerde.setVisible(false);
		imgNickname.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}

	@FXML void clickAdminContraseñaGestionContenido(MouseEvent event) {
		imgPasswordVerde.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgCorreoVerde.setVisible(false);
		imgPassword.setVisible(false);
		imgCorreo.setVisible(true);
		imgNickname.setVisible(true);
	}

	@FXML void clickAdminContraseñaGestionContenidoVolver(MouseEvent event) {
		imgPassword.setVisible(true);
		imgCorreo.setVisible(true);
		imgNickname.setVisible(true);
		imgPasswordVerde.setVisible(false);
		imgNicknameVerde.setVisible(false);
		imgCorreoVerde.setVisible(false);
	}

	@FXML protected void refreshTablas (MouseEvent event){
		ocultaPaneles();
		quitarFiltros();
		vaciaTextos();
		rellenaTablaUsu();
		tablaJuegos.setItems(null); //VACIAMOS TABLAS DE ELEMENTOS
		tablaLibros.setItems(null);
		tablaMusica.setItems(null);
	}


	@FXML void clickEligeUsu(MouseEvent event){ //CUANDO PULSAMOS UN USUARIO DE LA TABLA, COGEMOS SUS DATOS
		usuSelec = tablaUsuarios.getSelectionModel().getSelectedItem();
		if(usuSelec!=null) {
			ocultaPaneles();
			rellenaTablaLibros();
			rellenaTablaJuegos();
			rellenaTablaMusica();
		}

		libSelec = null; jueSelec = null;
	}

	protected void rellenaTablaUsu() {
		try {
			ObservableList<Usuario> listaUsuarios = Conexion.rellenaTablaUsu(filtro, paramFil);
			tablaUsuarios.setItems(listaUsuarios);

			idUsu.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID_Usuario()).asObject());
			nicknameUsu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNickname()));
			passwordUsu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContrasena()));
			correoUsu.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCorreo()));

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void rellenaTablaLibros() {
		try {
			int id = usuSelec.getID_Usuario();
			ObservableList<Libro> listaLibros = Conexion.adminListBooks(id);
			tablaLibros.setItems(listaLibros);

			idLibro.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID_Libro()).asObject());
			nombreLibro.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
			vaciaTextos();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void rellenaTablaJuegos() {
		try {
			int id = usuSelec.getID_Usuario();
			ObservableList<Juego> listaJuegos = Conexion.adminListGames(id);
			tablaJuegos.setItems(listaJuegos);

			idJuego.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID_Juego()).asObject());
			nombreJuego.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));

		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	protected void rellenaTablaMusica() {
		try {
			int id = usuSelec.getID_Usuario();
			ObservableList<Cancion> listaCanciones = Conexion.adminListSongs(id);
			tablaMusica.setItems(listaCanciones);

			idCancion.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getID_Cancion()).asObject());
			nombreCancion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML void clickBuscar (MouseEvent event){
		filtrar();
		rellenaTablaUsu();
		tablaJuegos.setItems(null); //VACIAMOS TABLAS DE ELEMENTOS
		tablaLibros.setItems(null);
	}

	private void filtrar() {
		paramFil = txtFiltro.getText();

		if(imgNicknameVerde.isVisible()) {
			filtro = "NICKNAME";
		}
		else if(imgCorreoVerde.isVisible()) {
			filtro = "CORREO";
		}
		else if(imgPasswordVerde.isVisible()) {
			filtro = "CONTRASEÑA";
		}
		else {
			filtro = null;
		}
	}

	protected void quitarFiltros() {
		txtFiltro.setText("");
		imgNicknameVerde.setVisible(false);
		imgCorreoVerde.setVisible(false);
		imgPasswordVerde.setVisible(false);

		imgNickname.setVisible(true);
		imgCorreo.setVisible(true);
		imgPassword.setVisible(true);

		filtro = null;
		paramFil = null;
	}



	/*PULSAR EN UN ELEMENTO INDIVIDUAL*/

	@FXML void clickLibro(MouseEvent event) {
		libSelec = tablaLibros.getSelectionModel().getSelectedItem();
		if(libSelec!=null) { 
			pLibIndiv.setVisible(true);

			tituloLibro.setText(libSelec.getTitulo());
			autorLibro.setText(libSelec.getAutor());
			fechaLibro.setText(Integer.toString(libSelec.getLanzamiento()));
			pagsLibro.setText(Integer.toString(libSelec.getnPaginas()));
			generoLibro.setText(libSelec.getGenero());
			portadaLibro.setText(libSelec.getPortada());
			resumenLibro.setText(libSelec.getResumen());

		}

	}
	@FXML void clickJuego(MouseEvent event) {
		jueSelec = tablaJuegos.getSelectionModel().getSelectedItem();
		if(jueSelec!=null) {
			pJueIndiv.setVisible(true);

			tituloJuego.setText(jueSelec.getTitulo());
			empresaJuego.setText(jueSelec.getEmpresa());
			fechaJuego.setText(Integer.toString(jueSelec.getLanzamiento()));
			horasJuego.setText(Integer.toString(jueSelec.gethJugadas()));
			generoJuego.setText(jueSelec.getGenero());
			plataformaJuego.setText(jueSelec.getPlataforma());
			portadaJuego.setText(jueSelec.getPortada());
			resumenJuego.setText(jueSelec.getResumen());
		}
		

	}
	@FXML void clickCancion(MouseEvent event) {
		
		canSelec = tablaMusica.getSelectionModel().getSelectedItem();
		if(canSelec!=null) {
			pMusIndiv.setVisible(true);

			tituloCancion.setText(canSelec.getNombre());
			artistaCancion.setText(canSelec.getArtista());
			generoCancion.setText(canSelec.getGenero());
			rutaCancion.setText(canSelec.getRuta());
		}
	}


	/*BOTONES LIBRO*/
	@FXML void cancelaLibro(MouseEvent event) {
		vaciaTextos();
		pLibIndiv.setVisible(false);
	}
	@FXML void eliminaLibro(MouseEvent event) {
		try {
			Conexion.eliminaLibro(libSelec);
			pLibIndiv.setVisible(false);
			rellenaTablaLibros();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML void aplicaLibro(MouseEvent event) {
		try {
			String titulo, autor, genero, portada, resumen;
			int fecha, pags;
			boolean actPortada = false;
			
			if(!tituloLibro.getText().equalsIgnoreCase("") && !autorLibro.getText().equalsIgnoreCase("") && !generoLibro.getText().equalsIgnoreCase("") && !pagsLibro.getText().equalsIgnoreCase("") && !fechaLibro.getText().equalsIgnoreCase("")) {
				titulo = tituloLibro.getText();
				autor = autorLibro.getText();
				genero = generoLibro.getText();
				pags = Integer.parseInt(pagsLibro.getText());
				fecha = Integer.parseInt(fechaLibro.getText());
				portada = portadaLibro.getText();
				resumen = resumenLibro.getText();
				Libro lNuevo = new Libro(libSelec.getID_Libro(), libSelec.getID_Usuario(), titulo, genero, autor, pags, fecha, libSelec.getTerminado(), portada, resumen);
				Conexion.updateLibro(lNuevo);
				
				if(portadaLibro.getText().equals("")) {
					actPortada = false;
				}
				else {
					actPortada = true;
				}
				Conexion.actualizaPortadaLib(lNuevo, actPortada);
				vaciaTextos();
				rellenaTablaLibros();
				pLibIndiv.setVisible(false);
				System.out.println("Libro actualizado");
			}
			else {
				System.out.println("No se pueden dejar campos vacios");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al actualizar el libro");
		}
	}

	/*BOTONES JUEGO*/
	@FXML void cancelaJuego(MouseEvent event) {
		vaciaTextos();
		pJueIndiv.setVisible(false);
	}
	@FXML void eliminaJuego(MouseEvent event) {
		try {
			Conexion.eliminaJuego(jueSelec);
			pJueIndiv.setVisible(false);
			rellenaTablaJuegos();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML void aplicaJuego(MouseEvent event) {
		try {
			String titulo, empresa, genero, plataforma, portada, resumen;
			int fecha, horas;
			boolean actPortada = false;
			
			if(!tituloJuego.getText().equalsIgnoreCase("") && !generoJuego.getText().equalsIgnoreCase("") && !plataformaJuego.getText().equalsIgnoreCase("") && !horasJuego.getText().equalsIgnoreCase("") && !fechaJuego.getText().equalsIgnoreCase("")) {
				titulo = tituloJuego.getText();
				empresa = empresaJuego.getText();
				genero = generoJuego.getText();
				plataforma = plataformaJuego.getText();
				horas = Integer.parseInt(horasJuego.getText());
				fecha = Integer.parseInt(fechaJuego.getText());
				portada = portadaJuego.getText();
				resumen = resumenJuego.getText();
				Juego JNuevo = new Juego(jueSelec.getID_Juego(), jueSelec.getID_Usuario(), titulo, genero, plataforma, horas, fecha, jueSelec.getTerminado(), portada, resumen, empresa);
				Conexion.updateJuego(JNuevo);
				
				if(portadaJuego.getText().equals("")) {
					actPortada = false;
				}
				else {
					actPortada = true;
				}
				Conexion.actualizaPortadaJue(JNuevo, actPortada);
				vaciaTextos();
				rellenaTablaJuegos();
				pJueIndiv.setVisible(false);
				System.out.println("Juego actualizado");
			}
			else {
				System.out.println("No se pueden dejar campos vacios");
			}
			
			
		}catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error al actualizar el libro");
		}
	}

	
	/*BOTONES MUSICA*/
	@FXML void cancelaCancion(MouseEvent event) {
		vaciaTextos();
		pMusIndiv.setVisible(false);
	}
	@FXML void eliminaCancion(MouseEvent event) {
		try {
			Conexion.eliminaCancion(canSelec);
			pMusIndiv.setVisible(false);
			rellenaTablaMusica();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	@FXML void aplicaCancion(MouseEvent event) {
		
	}







	protected void ocultaPaneles() {
		pLibIndiv.setVisible(false);
		pJueIndiv.setVisible(false);
		pMusIndiv.setVisible(false);
		libSelec = null; jueSelec = null;
	}

	protected void vaciaTextos() {
		tituloLibro.setText("");
		autorLibro.setText("");
		fechaLibro.setText("");
		pagsLibro.setText("");
		generoLibro.setText("");
		portadaLibro.setText("");
		resumenLibro.setText("");

		tituloJuego.setText("");
		empresaJuego.setText("");
		fechaJuego.setText("");
		horasJuego.setText("");
		generoJuego.setText("");
		plataformaJuego.setText("");
		portadaJuego.setText("");
		resumenJuego.setText("");
		
		tituloCancion.setText("");
		artistaCancion.setText("");
		generoCancion.setText("");
		rutaCancion.setText("");
		
	}

	@FXML void cambiaPortadaLibro(MouseEvent event) {
		FileChooser fChooser = new FileChooser();
		fChooser.setTitle("Selecciona una imagen");
		fChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
				);
		File selectedFile = fChooser.showOpenDialog(null);
		if (selectedFile != null) {
			portadaLibro.setText(selectedFile.toURI().toString());;
		}
	}
	
	@FXML void cambiaPortadaJuego(MouseEvent event) {
		FileChooser fChooser = new FileChooser();
		fChooser.setTitle("Selecciona una imagen");
		fChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
				);
		File selectedFile = fChooser.showOpenDialog(null);
		if (selectedFile != null) {
			portadaJuego.setText(selectedFile.toURI().toString());;
		}
	}


	public Pane getPane() {
		return pContenido;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}

}
