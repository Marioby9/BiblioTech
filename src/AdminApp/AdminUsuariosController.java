package AdminApp;


import java.sql.SQLException;

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

public class AdminUsuariosController {

	@FXML private AnchorPane pRootUsuarios;
	@FXML private Pane pUsuarios, pFiltros;
	@FXML protected Pane pFondoAvisoUsu, pAvisoUsu;

	@FXML private ImageView imgNickname;
	@FXML private ImageView imgNicknameVerde;

	@FXML private ImageView imgCorreo;
	@FXML private ImageView imgCorreoVerde;

	@FXML private ImageView imgPassword;
	@FXML private ImageView imgPasswordVerde;


	@FXML private TextField txtFiltro;
	@FXML private TextField txtUserNickname;
	@FXML private TextField txtUserCorreo;
	@FXML private TextField txtUserPassword;


	/* -------------------- TABLE VIEW -----------------*/
	@FXML private TableColumn<Usuario, Integer> idUsu;

	@FXML private TableColumn<Usuario, String>  nicknameUsu;

	@FXML private TableColumn<Usuario, String>  passwordUsu;

	@FXML private TableColumn<Usuario, String> correoUsu;

	@FXML private TableView<Usuario> tablaUsuarios;

	private String filtro, paramFil;
	private Usuario usuSelec;
	protected AdminMenuController menu;

	//GESTION USUARIO ADMIN
	@FXML
	void clickAdminUsuarioGestionUsuarioVolver(MouseEvent event) {
		imgNickname.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgCorreo.setVisible(true);
		imgCorreoVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}


	@FXML
	void clickAdminUsuarioGestionUsuario(MouseEvent event) {
		imgNickname.setVisible(false);
		imgNicknameVerde.setVisible(true);
		imgCorreo.setVisible(true);
		imgCorreoVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}

	@FXML
	void clickAdminCorreoGestionUsuarioVolver(MouseEvent event) {
		imgCorreo.setVisible(true);
		imgCorreoVerde.setVisible(false);
		imgNickname.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}

	@FXML
	void clickAdminCorreoGestionUsuario(MouseEvent event) {
		imgCorreo.setVisible(false);
		imgCorreoVerde.setVisible(true);
		imgNickname.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgPassword.setVisible(true);
		imgPasswordVerde.setVisible(false);
	}

	@FXML
	void clickAdminContraseñaGestionUsuarioVolver(MouseEvent event) {
		imgPassword.setVisible(true);
		imgCorreo.setVisible(true);
		imgNickname.setVisible(true);
		imgPasswordVerde.setVisible(false);
		imgNicknameVerde.setVisible(false);
		imgCorreoVerde.setVisible(false);
	}

	@FXML
	void clickAdminContraseñaGestionUsuario(MouseEvent event) {
		imgPasswordVerde.setVisible(true);
		imgNicknameVerde.setVisible(false);
		imgCorreoVerde.setVisible(false);
		imgPassword.setVisible(false);
		imgCorreo.setVisible(true);
		imgNickname.setVisible(true);

	}

	@FXML void refreshTablaUsu (MouseEvent event){
		vaciaTextos();
		quitarFiltros();
		rellenaTablaUsu();
	}

	@FXML void clickEligeUsu(MouseEvent event){ //CUANDO PULSAMOS UN USUARIO DE LA TABLA, COGEMOS SUS DATOS
		usuSelec = tablaUsuarios.getSelectionModel().getSelectedItem();
		txtUserNickname.setText(usuSelec.getNickname());
		txtUserCorreo.setText(usuSelec.getCorreo());
		txtUserPassword.setText(usuSelec.getContrasena());

	}

	@FXML void clickBuscar (MouseEvent event){
		vaciaTextos();
		usuSelec = null;

		filtrar();
		rellenaTablaUsu();

	}

	@FXML void clickEliminaUsu(MouseEvent event){ //CUANDO PULSAMOS UN USUARIO DE LA TABLA, COGEMOS SUS DATOS
		if(usuSelec != null) {
			pFondoAvisoUsu.setVisible(true);
			pAvisoUsu.setVisible(true);
		}
		else {
			System.out.println("No se puede eliminar el usuario");
		}

	}

	@FXML void clickAceptaAvisoUsu(MouseEvent event) {
		try {
			Conexion.eliminarUsuario(usuSelec); //BORRAMOS
			rellenaTablaUsu(); //ACTUALIZAMOS TABLA
			pFondoAvisoUsu.setVisible(false);
			pAvisoUsu.setVisible(false);

			vaciaTextos();
		} catch (SQLException e) {
			e.printStackTrace();
		}


	}
	@FXML void clickRechazaAvisoUsu(MouseEvent event) {
		pFondoAvisoUsu.setVisible(false);
		pAvisoUsu.setVisible(false);
	}


	@FXML void clickCancelaUsu (MouseEvent event){
		vaciaTextos();
		usuSelec = null;
	}

	@FXML void clickAplicaUsu (MouseEvent event){
		String nickname = txtUserNickname.getText();
		String password = txtUserPassword.getText();
		String correo = txtUserCorreo.getText();

		if(usuSelec != null && !nickname.equals("") && !password.equals("") && !correo.equals("")){
			try {
				usuSelec.setNickname(nickname);
				usuSelec.setContrasena(password);
				usuSelec.setCorreo(correo);
				Conexion.updateUsuario(usuSelec);
			}catch (Exception e) {
				e.printStackTrace();
			}

			vaciaTextos();
			usuSelec = null;

		}else {
			System.out.println("No se puede actualizar el usuario");
		}

	}

	/* ------------------- FUNCIONES VARIAS ---------------*/

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


	private void vaciaTextos() {

		txtUserNickname.setText("");
		txtUserPassword.setText("");
		txtUserCorreo.setText("");
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
		return pUsuarios;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}


}
