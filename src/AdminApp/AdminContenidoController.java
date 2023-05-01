package AdminApp;

import elementos.Juego;
import elementos.Libro;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utilidades.Conexion;
import utilidades.Usuario;

public class AdminContenidoController {

	@FXML private AnchorPane pRootContenido;
	@FXML private Pane pContenido, pFiltros;

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

	private String filtro, paramFil;
	private Usuario usuSelec;    
	protected AdminMenuController menu;

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
		quitarFiltros();
		rellenaTablaUsu();
		tablaJuegos.setItems(null); //VACIAMOS TABLAS DE ELEMENTOS
		tablaLibros.setItems(null);
	}

	
	@FXML void clickEligeUsu(MouseEvent event){ //CUANDO PULSAMOS UN USUARIO DE LA TABLA, COGEMOS SUS DATOS
		usuSelec = tablaUsuarios.getSelectionModel().getSelectedItem();
		if(usuSelec!=null) {
			rellenaTablaLibros();
			rellenaTablaJuegos();
		}


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










	public Pane getPane() {
		return pContenido;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}

}
