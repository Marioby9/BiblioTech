package application;



import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

public class MenuController {

	//PANELES MENU
	@FXML private AnchorPane pFondoMenu;
	@FXML private Pane pContenido;
	@FXML public ImageView imgFondoMenu;
	@FXML public Label lblNomUsuario;
	@FXML public ImageView fotoPerfil1 ,bVolver;

	//PERFIL:
	private PerfilController perfilController;
	Pane pPerfil;

	//LIBROS
	private LibrosController librosController;
	Pane pLibros;

	//JUEGOS
	private JuegosController juegosController;
	Pane pJuegos;

	//MUSICA
	private MusicaController musicaController;
	Pane pMusica;

	//AJUSTES
	private AjustesController ajustesController;
	Pane pAjustes;



	//PANELES LISTAS MUSICA + CANCIONES
	public String carpCanciones = "";
	
	public double vol;

	


	//CONTROLADOR
	LogInController controlador;



	private Usuario u1;

	public void initialize() {	
		u1 = Usuario.getUsuario(); //RECIBE EL USUARIO QUE ACABA DE INICIAR SESION

		initComponents(); //INICIA TODOS LOS COMPONENTES

	}

	public void initComponents() {

		try { //CONSULTAMOS EL VALOR DEL VOLUMEN POR DEFECTO DEL USUARIO
			vol = Conexion.consultaNum("AJUSTES", "VOLUMEN", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			carpCanciones = Conexion.consultaStr("AJUSTES", "CARP_MUSICA", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		try { //INSTANCIAMOS LOS CONTROLADORES
			//PERFIL:
			FXMLLoader loaderPerfil = new FXMLLoader(getClass().getResource("/FXML/PerfilView.fxml"));
			loaderPerfil.load();
			perfilController = loaderPerfil.getController();
			perfilController.setMenuController(this);
			pPerfil = perfilController.getPane();
			pContenido.getChildren().add(pPerfil);
			pPerfil.setLayoutX(0); pPerfil.setLayoutY(0);
			pPerfil.setVisible(true);
			perfilController.cambiaFPerfil();

			//LIBROS
			FXMLLoader loaderLibros = new FXMLLoader(getClass().getResource("/FXML/LibrosView.fxml"));
			loaderLibros.load();
			librosController = loaderLibros.getController();
			librosController.setMenuController(this);
			pLibros = librosController.getPane();
			pContenido.getChildren().add(pLibros);
			pLibros.setLayoutX(0); pLibros.setLayoutY(0);

			//JUEGOS
			FXMLLoader loaderJuegos = new FXMLLoader(getClass().getResource("/FXML/JuegosView.fxml"));
			loaderJuegos.load();
			juegosController = loaderJuegos.getController();
			juegosController.setMenuController(this);
			pJuegos = juegosController.getPane();
			pContenido.getChildren().add(pJuegos);
			pJuegos.setLayoutX(0); pJuegos.setLayoutY(0);
			
			//MUSICA
			FXMLLoader loaderMusica = new FXMLLoader(getClass().getResource("/FXML/MusicaView.fxml"));
			loaderMusica.load();
			musicaController = loaderMusica.getController();
			musicaController.setMenuController(this);
			pMusica = musicaController.getPane();
			pContenido.getChildren().add(pMusica);
			pMusica.setLayoutX(0); pMusica.setLayoutY(0);
			musicaController.barraMusica.setProgress(0);
			musicaController.imgPlayMus = new Image(getClass().getResourceAsStream("/icons/playCancion.png"));
			musicaController.imgPauseMus = new Image(getClass().getResourceAsStream("/icons/pausa.png"));
			musicaController.imgBucleBlanco = new Image(getClass().getResourceAsStream("/icons/repetir.png"));
			musicaController.imgBucleVerde = new Image(getClass().getResourceAsStream("/icons/repetirVerde.png"));
			
			

			//AJUSTES
			FXMLLoader loaderAjustes = new FXMLLoader(getClass().getResource("/FXML/AjustesView.fxml"));
			loaderAjustes.load();
			ajustesController = loaderAjustes.getController();
			ajustesController.setMenuController(this);
			pAjustes = ajustesController.getPane();
			pContenido.getChildren().add(pAjustes);
			pAjustes.setLayoutX(0); pAjustes.setLayoutY(0);
			
			ajustesController.cambiaColorFondo();
			ajustesController.barraVolAjustes.setValue(vol);
			ajustesController.setMusicaController(musicaController);
			ajustesController.lblVolAjustes.setText(Integer.toString((int)(Math.floor(ajustesController.barraVolAjustes.getValue()))));
			vol /= 100; //DIVIDIMOS EL VOLUMEN ENTRE 100 PORQUE LA FUNCION .setVolume(vol) RECIBE UN DOUBLE


		} catch (IOException e2) {
			e2.printStackTrace();
		}

		//VISIBLES O NO

		pFondoMenu.setVisible(true);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
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
			if(musicaController.activo) {
				musicaController.reproductor.stop();
			}
			venMenu.close();

			Conexion.terminaTiempo(u1);
			
		} catch(Exception ex) {
			Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE,null,ex);
		}
	}


	@FXML public void clickBVolver(MouseEvent event) throws IOException {
		closeWindows();
		controlador.pInicio.setVisible(false);
		controlador.pRegistro.setVisible(false);


	}

	@FXML void clickBPerfil(MouseEvent event) {


		pPerfil.setVisible(true);
		perfilController.pProfilePics.setVisible(false);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);




	}

	@FXML void clickBLibros(MouseEvent event) {
		pPerfil.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);
		librosController.pListaLibros.setVisible(false);
		librosController.pLibroIndiv.setVisible(false);
		pLibros.setVisible(true);


	}

	@FXML void clickBJuegos(MouseEvent event) {
		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);
		juegosController.pListaJuegos.setVisible(false);
		juegosController.pJuegoIndiv.setVisible(false);
		pJuegos.setVisible(true);

	}

	@FXML void clickBMusica(MouseEvent event) {
		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pAjustes.setVisible(false);
		pMusica.setVisible(true);
		musicaController.pListaMus.setVisible(false);
		musicaController.pFondoAvisoMus.setVisible(false);
		musicaController.pAvisoMus.setVisible(false);

	}

	@FXML void clickBAjustes(MouseEvent event) {
		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(true);
		ajustesController.pPanelColores.setVisible(false);
		ajustesController.pFondoAviso.setVisible(false);
		ajustesController.pAvisoMus.setVisible(false);
		ajustesController.pAvisoCuenta.setVisible(false);
	}


}
