package AdminApp;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AdminContenidoController {

	@FXML private AnchorPane pRootContenido;
	@FXML private Pane pContenido;
	
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
	
	//GESTION CONTENIDO ADMIN
    @FXML
    void clickAdminUsuarioGestionContenido(MouseEvent event) {
    	
    	ImgAdminUsuario.setVisible(false);
    	ImgAdminUsuarioVerde.setVisible(true);
    	ImgAdminCorreo.setVisible(true);
    	ImgAdminCorreoVerde.setVisible(false);
    	ImgAdminContraseña.setVisible(true);
    	ImgAdminContraseñaVerde.setVisible(false);
    }
    
    @FXML
    void clickAdminUsuarioGestionContenidoVolver(MouseEvent event) {
    	ImgAdminUsuario.setVisible(true);
    	ImgAdminUsuarioVerde.setVisible(false);
    	ImgAdminCorreo.setVisible(true);
    	ImgAdminCorreoVerde.setVisible(false);
    	ImgAdminContraseña.setVisible(true);
    	ImgAdminContraseñaVerde.setVisible(false);
    }

    @FXML
    void clickAdminCorreoGestionContenido(MouseEvent event) {
    	ImgAdminCorreo.setVisible(false);
    	ImgAdminCorreoVerde.setVisible(true);
    	ImgAdminUsuario.setVisible(true);
    	ImgAdminUsuarioVerde.setVisible(false);
    	ImgAdminContraseña.setVisible(true);
    	ImgAdminContraseñaVerde.setVisible(false);
    }

    @FXML
    void clickAdminCorreoGestionContenidoVolver(MouseEvent event) {
    	ImgAdminCorreo.setVisible(true);
    	ImgAdminCorreoVerde.setVisible(false);
    	ImgAdminUsuario.setVisible(true);
    	ImgAdminUsuarioVerde.setVisible(false);
    	ImgAdminContraseña.setVisible(true);
    	ImgAdminContraseñaVerde.setVisible(false);
    }
    
    @FXML
    void clickAdminContraseñaGestionContenido(MouseEvent event) {
    	ImgAdminContraseñaVerde.setVisible(true);
    	ImgAdminContraseña.setVisible(false);
    	ImgAdminCorreo.setVisible(true);
    	ImgAdminUsuario.setVisible(true);
    	ImgAdminUsuarioVerde.setVisible(false);
    	ImgAdminCorreoVerde.setVisible(false);
    }

    @FXML
    void clickAdminContraseñaGestionContenidoVolver(MouseEvent event) {
    	ImgAdminContraseña.setVisible(true);
    	ImgAdminContraseñaVerde.setVisible(false);
    	ImgAdminCorreo.setVisible(true);
    	ImgAdminUsuario.setVisible(true);
    	ImgAdminUsuarioVerde.setVisible(false);
    	ImgAdminCorreoVerde.setVisible(false);
    }
    
    
    public Pane getPane() {
		return pContenido;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}
	
}
