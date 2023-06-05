package AdminApp;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import application.LogInController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilidades.Usuario;

public class AdminMenuController {

	@FXML private AnchorPane pFondoMenu;
	@FXML private Pane pContenidoMenu;
	@FXML public ImageView imgFondoMenu;
	@FXML public Label lblNomUsuario;
	@FXML public ImageView fotoPerfil1 ,bVolver;


	//PERFIL:
	private AdminPerfilController adminPerfilController;
	Pane pPerfil;

	//CONTENIDO
	private AdminContenidoController adminContenidoController;
	Pane pContenido;

	//USUARIOS
	private AdminUsuariosController adminUsuariosController;
	Pane pUsuarios;

	//ESTADISTICAS
	private AdminEstadisticasController adminEstadisticasController;
	Pane pEstadisticas;

	//AJUSTES
	private AdminAjustesController adminAjustesController;
	Pane pAjustes;

	LogInController controlador;
	private Usuario u1;
	
	public void initialize() {	
		u1 = Usuario.getUsuario(); //RECIBE EL USUARIO QUE ACABA DE INICIAR SESION

		initComponents(); //INICIA TODOS LOS COMPONENTES

	}
	
	public void initComponents() {

		

		try { //INSTANCIAMOS LOS CONTROLADORES
			//PERFIL:
			FXMLLoader loaderPerfil = new FXMLLoader(getClass().getResource("/AdminFXML/AdminPerfilView.fxml"));
			loaderPerfil.load();
			adminPerfilController = loaderPerfil.getController();
			adminPerfilController.setMenuController(this);
			pPerfil = adminPerfilController.getPane();
			pContenidoMenu.getChildren().add(pPerfil);
			pPerfil.setLayoutX(0); pPerfil.setLayoutY(0);
			pPerfil.setVisible(true);
			adminPerfilController.cambiaFPerfil();
			


			//CONTENIDO
			FXMLLoader loaderContenido = new FXMLLoader(getClass().getResource("/AdminFXML/AdminContenidoView.fxml"));
			loaderContenido.load();
			adminContenidoController = loaderContenido.getController();
			adminContenidoController.setMenuController(this);
			pContenido = adminContenidoController.getPane();
			pContenidoMenu.getChildren().add(pContenido);
			pContenido.setLayoutX(0); pContenido.setLayoutY(0);
			
			
			//USUARIOS
			FXMLLoader loaderUsuarios = new FXMLLoader(getClass().getResource("/AdminFXML/AdminUsuariosView.fxml"));
			loaderUsuarios.load();
			adminUsuariosController = loaderUsuarios.getController();
			adminUsuariosController.setMenuController(this);
			pUsuarios = adminUsuariosController.getPane();
			pContenidoMenu.getChildren().add(pUsuarios);
			pUsuarios.setLayoutX(0); pUsuarios.setLayoutY(0);
			
			//ESTADISTICAS
			FXMLLoader loaderEstadisticas = new FXMLLoader(getClass().getResource("/AdminFXML/AdminEstadisticasView.fxml"));
			loaderEstadisticas.load();
			adminEstadisticasController = loaderEstadisticas.getController();
			adminEstadisticasController.setMenuController(this);
			pEstadisticas = adminEstadisticasController.getPane();
			pContenidoMenu.getChildren().add(pEstadisticas);
			pEstadisticas.setLayoutX(0); pEstadisticas.setLayoutY(0);
	
			
			//AJUSTES
			FXMLLoader loaderAjustes = new FXMLLoader(getClass().getResource("/AdminFXML/AdminAjustesView.fxml"));
			loaderAjustes.load();
			adminAjustesController = loaderAjustes.getController();
			adminAjustesController.setMenuController(this);
			pAjustes = adminAjustesController.getPane();
			pContenidoMenu.getChildren().add(pAjustes);
			pAjustes.setLayoutX(0); pAjustes.setLayoutY(0);
			
			adminAjustesController.cambiaColorFondo();
		
			


		} catch (IOException e2) {
			e2.printStackTrace();
		}

		//VISIBLES O NO

		pFondoMenu.setVisible(true);
		pPerfil.setVisible(true);
		pContenido.setVisible(false);
		pUsuarios.setVisible(false);
		pEstadisticas.setVisible(false);
		pAjustes.setVisible(false);
		lblNomUsuario.setText(u1.getNickname());

		
	}

	public void closeWindows() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/UserFXML/LogInView.fxml"));
			Parent root = loader.load();
			controlador = loader.getController();

			Scene scene = new Scene(root);
			Stage ventana = new Stage();
			ventana.setScene(scene);
			ventana.setTitle("BiblioTech");

			//File file = new File("src/icons/logoNegroCF.png");		//FORMA ANTIGUA CAMBIAR ICONO. NO FUNCIONABA EN EJECUTABLE POR RUTA RELATIVA
			//ventana.getIcons().add(new Image(file.toURI().toString()));  //FUNCIONABA EN ECLIPSE

			Image icon = new Image(getClass().getResourceAsStream("/icons/logoNegroCF.png"));//CAMBIAR ICONO DEL PROGRAMA
			ventana.getIcons().add(icon);//FUNCIONA PERFECTAMENTE

			ventana.setResizable(false);
			
			//ventana.initStyle(StageStyle.UNDECORATED);//QUITAR BARRA
			
			ventana.show();


			Stage venMenu = (Stage) this.pFondoMenu.getScene().getWindow();
			venMenu.close();

		} catch(IOException ex) {
			Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE,null,ex);
		}
	}

	
	@FXML void clickBPerfil(MouseEvent event) {
		pContenido.setVisible(false);
		pUsuarios.setVisible(false);
		pEstadisticas.setVisible(false);
		pAjustes.setVisible(false);
		pPerfil.setVisible(true);
		adminPerfilController.pProfilePics.setVisible(false);
	}

	@FXML void clickBContenido(MouseEvent event) {
		pContenido.setVisible(true);
		pPerfil.setVisible(false);
		pUsuarios.setVisible(false);
		pEstadisticas.setVisible(false);
		pAjustes.setVisible(false);
		adminContenidoController.initComponents();
	}

	@FXML void clickBUsuarios(MouseEvent event) {
		pUsuarios.setVisible(true);
		pPerfil.setVisible(false);
		pContenido.setVisible(false);
		pEstadisticas.setVisible(false);
		pAjustes.setVisible(false);
		adminUsuariosController.initComponents();
	}

	@FXML void clickBEstadisticas(MouseEvent event) {
		adminEstadisticasController.actualizaEstadisticas();
		pEstadisticas.setVisible(true);
		pPerfil.setVisible(false);
		pContenido.setVisible(false);
		pUsuarios.setVisible(false);
		pAjustes.setVisible(false);
	}

	@FXML void clickBAjustes(MouseEvent event) {
		pPerfil.setVisible(false);
		pContenido.setVisible(false);
		pUsuarios.setVisible(false);
		pEstadisticas.setVisible(false);
		pAjustes.setVisible(true);
		adminAjustesController.pPanelColores.setVisible(false);
	}

	
	@FXML public void clickBVolver(MouseEvent event) throws IOException {
		closeWindows();
		controlador.pInicio.setVisible(false);
		controlador.pRegistro.setVisible(false);
	}
	

}
