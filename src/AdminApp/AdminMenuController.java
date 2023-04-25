package AdminApp;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.AjustesController;
import application.JuegosController;
import application.LibrosController;
import application.LogInController;
import application.MusicaController;
import application.PerfilController;
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
import utilidades.Conexion;
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
	Pane pJuegos;

	//ESTADISTICAS
	private AdminEstadisticasController adminEstadisticasController;
	Pane pMusica;

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
		pAjustes.setVisible(false);
		lblNomUsuario.setText(u1.getNickname());

		
	}

	public void closeWindows() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/LogInView.fxml"));
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
		pPerfil.setVisible(true);
		pAjustes.setVisible(false);

	}

	@FXML void clickBContenido(MouseEvent event) {
		pPerfil.setVisible(false);
		pAjustes.setVisible(false);

	}

	@FXML void clickBUsuarios(MouseEvent event) {
		pPerfil.setVisible(false);
		pAjustes.setVisible(false);
	}

	@FXML void clickBEstadisticas(MouseEvent event) {
		pPerfil.setVisible(false);
		pAjustes.setVisible(false);

	}

	@FXML void clickBAjustes(MouseEvent event) {
		pPerfil.setVisible(false);
		pAjustes.setVisible(true);
		adminAjustesController.pPanelColores.setVisible(false);
		adminAjustesController.pFondoAviso.setVisible(false);
		adminAjustesController.pAvisoCuenta.setVisible(false);
	}

	
	@FXML public void clickBVolver(MouseEvent event) throws IOException {
		closeWindows();
		controlador.pInicio.setVisible(false);
		controlador.pRegistro.setVisible(false);


	}
	

}