package AdminApp;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AdminUsuariosController {
	
	@FXML private AnchorPane pRootUsuarios;
	@FXML private Pane pUsuarios;

	@FXML private ImageView ImgAdminUsuario;

	@FXML private ImageView ImgAdminUsuarioVerde;

	@FXML private ImageView ImgAdminCorreo;

	@FXML private ImageView ImgAdminCorreoVerde;
	@FXML private ImageView ImgAdminContraseña;

	@FXML private ImageView ImgAdminContraseñaVerde;

	@FXML private ImageView ImgAdminContraseñaGU;

	@FXML private ImageView ImgAdminContraseñaVerdeGU;

	@FXML private ImageView ImgAdminCorreoGU;

	@FXML private ImageView ImgAdminCorreoVerdeGU;

	@FXML private ImageView ImgAdminUsuarioGU;

	@FXML private ImageView ImgAdminUsuarioVerdeGU;

	
	protected AdminMenuController menu;
	
	//GESTION USUARIO ADMIN
	@FXML
	void clickAdminUsuarioGestionUsuarioVolver(MouseEvent event) {
		ImgAdminUsuario.setVisible(true);
		ImgAdminUsuarioVerde.setVisible(false);
		ImgAdminCorreo.setVisible(true);
		ImgAdminCorreoVerde.setVisible(false);
		ImgAdminContraseñaGU.setVisible(true);
		ImgAdminContraseñaVerdeGU.setVisible(false);
	}


	@FXML
	void clickAdminUsuarioGestionUsuario(MouseEvent event) {
		ImgAdminUsuario.setVisible(false);
		ImgAdminUsuarioVerde.setVisible(true);
		ImgAdminCorreo.setVisible(true);
		ImgAdminCorreoVerde.setVisible(false);
		ImgAdminContraseñaGU.setVisible(true);
		ImgAdminContraseñaVerdeGU.setVisible(false);
	}

	@FXML
	void clickAdminCorreoGestionUsuarioVolver(MouseEvent event) {
		ImgAdminCorreo.setVisible(true);
		ImgAdminCorreoVerde.setVisible(false);
		ImgAdminUsuario.setVisible(true);
		ImgAdminUsuarioVerde.setVisible(false);
		ImgAdminContraseñaGU.setVisible(true);
		ImgAdminContraseñaVerdeGU.setVisible(false);
	}

	@FXML
	void clickAdminCorreoGestionUsuario(MouseEvent event) {
		ImgAdminCorreo.setVisible(false);
		ImgAdminCorreoVerde.setVisible(true);
		ImgAdminUsuario.setVisible(true);
		ImgAdminUsuarioVerde.setVisible(false);
		ImgAdminContraseñaGU.setVisible(true);
		ImgAdminContraseñaVerdeGU.setVisible(false);
	}

	@FXML
	void clickAdminContraseñaGestionUsuarioVolver(MouseEvent event) {
		ImgAdminContraseñaGU.setVisible(true);
		ImgAdminContraseñaVerde.setVisible(false);
		ImgAdminCorreo.setVisible(true);
		ImgAdminUsuario.setVisible(true);
		ImgAdminUsuarioVerde.setVisible(false);
		ImgAdminContraseñaVerdeGU.setVisible(false);
	}

	@FXML
	void clickAdminContraseñaGestionUsuario(MouseEvent event) {
		ImgAdminContraseñaVerdeGU.setVisible(true);
		ImgAdminContraseñaGU.setVisible(false);
		ImgAdminCorreo.setVisible(true);
		ImgAdminUsuario.setVisible(true);
		ImgAdminUsuarioVerde.setVisible(false);
		ImgAdminCorreoVerde.setVisible(false);
	}


	public Pane getPane() {
		return pUsuarios;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}
	
	
}
