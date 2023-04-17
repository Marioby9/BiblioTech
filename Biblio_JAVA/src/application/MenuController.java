package application;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import utilidades.Conexion;
import utilidades.Correo;
import utilidades.Ficheros;
import utilidades.Usuario;
import elementos.Juego;
import elementos.Libro;
import elementos.Cancion;

public class MenuController {
	//PANELES
	@FXML private AnchorPane pFondoMenu;
	@FXML private ImageView imgFondoMenu;


	//PANEL PERFIL
	@FXML private Pane pPerfil;
	@FXML private ImageView bEditarFoto, bEliminarFoto;		
	@FXML private Label lblNomUsuario, lblBienvenidoPerfil;
	@FXML private Pane pProfilePics;
	@FXML private ImageView fotoPerfil, fotoPerfil1;
	Image fPerfil, fPerfil1;
	int numFPerfil;
	@FXML private ImageView foto1, foto2, foto3, foto4, foto5, foto6, foto7,foto8, foto9;
	@FXML private TextField txtCambioCorreo, txtCambioPassword, txtCambioUsuario;


	//PANEL JUEGOS
	@FXML private Pane pJuegos, pJuegoIndiv, pListaJuegos, bGuardaEditaJuego, bGuardaAgregaJuego;
	//BOTONES PJUEGOS
	@FXML private ImageView bJueAccion, bJueDeportes, bJueFavoritos, bJueShooter, bJueTerror, btnBackPaneJuegos, bFavJue, addGame, portadaListaJue, portadaJuego;
	@FXML private TableView<Juego> tablaJuegos;
	@FXML private TableColumn<Juego, String> tituloJue;
	@FXML private TableColumn<Juego, String> plataformaJue;
	@FXML private TableColumn<Juego, Integer> fechaJue;
	@FXML private TableColumn<Juego, Integer> horasJue;
	Juego jueActual = null;
	String categJue = "";
	String accJuego = "";
	String rutaPortadaJue = "";
	boolean cambiaPortadaJue = false; //CUANDO QUIERAS SUBIR UN ARCHIVO, SE PONDRA TRUE


	//PANELES LISTA JUEGOS
	@FXML private Label lblTituloJuegoInd, lblNHorasJuegoInd, lblPlataformaJuegoInd, lblGeneroJuegoInd, lblYearJuegoInd, lblResumenJuegoInd, lblCompaniaJuegoInd, lblTituloListaJue, lblErrorJue;
	@FXML private TextArea txtAreaResumenJuego;
	@FXML private TextField txtFieldTitJuego, txtFieldPlataformaJuego, txtFieldGeneroJuego, txtFieldFechaJuego, txtFieldHorJuego, txtFieldCompaniaJuegoInd;



	//PANEL LIBROS 
	@FXML private Pane pLibros, pLibroIndiv, pListaLibros, bGuardaEditaLibro, bGuardaAgregaLibro;
	//BOTONES PLIBROS
	@FXML private ImageView bLibAmor, bLibAventuras, bLibComedia, bLibFavoritos, bLibTerror, btnBackPaneLibros, bFavLib, addBook, portadaListaLib, portadaLibro;
	@FXML private TableView<Libro> tablaLibros;
	@FXML private TableColumn<Libro, String> tituloLib;
	@FXML private TableColumn<Libro, String> autorLib;
	@FXML private TableColumn<Libro, Integer> fechaLib;
	@FXML private TableColumn<Libro, Integer> nPagsLib;
	Libro libActual = null;
	String categLib = "";
	String accLibro = "";
	String rutaPortadaLib = "";
	boolean cambiaPortadaLib = false; //CUANDO QUIERAS SUBIR UN ARCHIVO, SE PONDRA TRUE


	//PANELES LISTA LIBROS
	@FXML private Label lblTituloLibroInd, lblAutorLibroInd, lblNPagsLibro, lblGeneroLibroInd, lblYearLibroInd, lblResumenLibroInd, lblTituloListaLib, lblErrorLib;
	@FXML private TextArea txtAreaResumenLibro;
	@FXML private TextField txtFieldTitLibro, txtFieldAutorLibro, txtFieldFechaLibro, txtFieldPagLibro;



	//MUSICA
	@FXML private Pane pMusica, cabeceraMus, pListaMus;
	@FXML private Label lblTitListaMus, lblTiempoCanc, lblNumCanciones, lblTitReproductor ;
	@FXML private ProgressBar barraMusica;
	@FXML private ImageView bPlayMusica;
	Image imgPlayMus, imgPauseMus;
	Timeline timeline;
	Duration duracionTotal;
	//BOTONES PMUSICA
	@FXML private ImageView bMusElectronica, bMusFlamenco, bMusPop, bMusReggaeton, bMusRock;
	@FXML private TableView<Cancion> tablaMusica;
	@FXML private TableColumn<Cancion, String> tituloCanc;
	@FXML private TableColumn<Cancion, String> artistaCanc;
	@FXML private TableColumn<Cancion, String> generoCanc;
	MediaPlayer reproductor;
	Cancion cancActual = null;
	String categMus = "";

	//PANELES LISTAS MUSICA + CANCIONES
	boolean activo = false;
	String carpCanciones = "";
	String estiloCabMus = "-fx-border-width: 4px; -fx-border-color: BLACK; -fx-border-radius: 20px; -fx-background-radius: 25px;";
	double vol;

	int contPrueba = 1000;


	//CONTROLADOR
	LogInController controlador;
	//MENU IZQUIERDA OPCIONES

	@FXML private ImageView bVolver;


	//PANEL AJUSTES:
	@FXML private Pane pAjustes;
	@FXML private Label lblColorTema;
	@FXML private ImageView bEditarColor, bWeb, bGithub, bTwitter, bInstagram, bYoutube;
	@FXML private Pane pPanelColores;		boolean abierto;  int color;
	@FXML private Slider barraVolAjustes, barraVolReproductor;
	@FXML private Label lblVolAjustes;

	@FXML private Label lblColorAzul, lblColorMorado, lblColorNaranja, lblColorRojo, lblColorVerde;


	//BBDD
	Conexion c1;
	Usuario u1;

	public void initialize() {
		c1 = new Conexion(); //CONECTA BBDD	
		u1 = Usuario.getUsuario(); //RECIBE EL USUARIO QUE ACABA DE INICIAR SESION

		initComponents(); //INICIA TODOS LOS COMPONENTES

	}

	public void initComponents() {
		//VISIBLES O NO
		pFondoMenu.setVisible(true);
		pPerfil.setVisible(true);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);

		pLibroIndiv.setVisible(false);
		pJuegoIndiv.setVisible(false);


		pProfilePics.setVisible(false);
		pAjustes.setVisible(false);
		pPanelColores.setVisible(false); abierto = false;  

		try { //PONE EL COLOR DE PANEL QUE QUIERA EL USUARIO
			color=c1.consultaNum("AJUSTES", "CFONDO", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			cambiaColorFondo();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		fPerfil = new Image(fotoPerfil.getImage().getUrl());
		fPerfil1 = new Image(fotoPerfil1.getImage().getUrl());
		try {//PONE FPERFIL QUE TENGA EL USUARIO EN SU BBDD
			numFPerfil = c1.consultaNum("AJUSTES", "FPERFIL", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			cambiaFPerfil();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}


		//INICIAR COMPONENTES PARA CADA USUARIO
		lblBienvenidoPerfil.setText("Bienvenido, "+ u1.getNickname());
		lblNomUsuario.setText(u1.getNickname());
		initPanelesIndiv();

		//QUITAR BARRA HORIZONTAL DE SCROLLPANES
		pListaMus.setVisible(false);


		//MUSICA:

		try {
			vol = c1.consultaNum("AJUSTES", "VOLUMEN", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			carpCanciones = c1.consultaStr("AJUSTES", "CARP_MUSICA", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		barraVolAjustes.setValue(vol);
		barraMusica.setProgress(0);
		lblVolAjustes.setText(Integer.toString((int)(Math.floor(barraVolAjustes.getValue()))));

		vol /= 100; //DIVIDIMOS EL VOLUMEN ENTRE 100 PORQUE LA FUNCION .setVolume(vol) RECIBE UN DOUBLE

		imgPlayMus = new Image(getClass().getResourceAsStream("/icons/playCancion.png"));
		imgPauseMus = new Image(getClass().getResourceAsStream("/icons/pausa.png"));
	}

	public void closeWindows() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("LogInView.fxml"));
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
			if(activo) {
				reproductor.stop();
			}
			venMenu.close();

		} catch(IOException ex) {
			Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE,null,ex);
		}
	}




	@FXML void clickBVolver(MouseEvent event) throws IOException {
		closeWindows();
		controlador.pInicio.setVisible(false);
		controlador.pRegistro.setVisible(false);


	}

	@FXML void clickBPerfil(MouseEvent event) {
		txtCambioUsuario.setText("");
		txtCambioCorreo.setText("");
		txtCambioPassword.setText("");

		pPerfil.setVisible(true);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);
		pProfilePics.setVisible(false);



	}

	@FXML void clickBLibros(MouseEvent event) {
		pLibros.setVisible(true);
		pListaLibros.setVisible(false);
		pLibroIndiv.setVisible(false);

		pPerfil.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);
		pLibroIndiv.setVisible(false);

	}

	@FXML void clickBJuegos(MouseEvent event) {
		pJuegos.setVisible(true);
		pListaJuegos.setVisible(false);
		pJuegoIndiv.setVisible(false);

		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);

	}

	@FXML void clickBMusica(MouseEvent event) {
		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pAjustes.setVisible(false);
		pMusica.setVisible(true);

		pListaMus.setVisible(false);

	}

	@FXML void clickBAjustes(MouseEvent event) {
		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(true);

		pPanelColores.setVisible(false);
	}


	@FXML void clickBEditarFoto(MouseEvent event) {
		if(pProfilePics.isVisible()==false) {
			pProfilePics.setVisible(true);
		}
		else {
			pProfilePics.setVisible(false);
		}


	}

	@FXML void clickBEliminarFoto(MouseEvent event) { //PONE LA FOTO PERFIL POR DEFAULT
		//FORMA ANTIGUA:
		//		File file = new File("src/icons/logAzulSF.png");
		//		Image image = new Image(file.toURI().toString()); 
		//		fotoPerfil.setImage(image);
		//		fotoPerfil1.setImage(image);

		//FORMA NUEVA:
		numFPerfil = 0;
		cambiaFPerfil();

		try {
			c1.updateTabla("AJUSTES", "FPERFIL", numFPerfil, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			cambiaFPerfil();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	@FXML void clickBGuardarCambios(MouseEvent event) { //CAMBIA FOTO PERFIL, NOMBRE, CORREO, CONTRASEÑA

		fotoPerfil1.setImage(fotoPerfil.getImage());

		if(!txtCambioUsuario.getText().equals("")) { //CAMBIA NICKNAME
			String nuevoNom = txtCambioUsuario.getText();
			try {
				c1.updateTabla("USUARIO", "NICKNAME", nuevoNom, "ID = "+Integer.toString(u1.getID_Usuario()));
				u1.setNickname(nuevoNom);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			lblNomUsuario.setText(nuevoNom);
			lblBienvenidoPerfil.setText("Bienvenido, "+nuevoNom);
		}

		if(!txtCambioPassword.getText().equals("")) { //CAMBIA CONTRASEÑA
			String nuevaCon = txtCambioPassword.getText();
			try {
				c1.updateTabla("USUARIO", "CONTRASEÑA", nuevaCon, "ID = "+Integer.toString(u1.getID_Usuario()));
				u1.setContrasena(nuevaCon);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if(!txtCambioCorreo.getText().equals("")) { //CAMBIA CORREO
			String nuevoCorreo = txtCambioCorreo.getText();
			try {
				c1.updateTabla("USUARIO", "CORREO", nuevoCorreo, "ID = "+Integer.toString(u1.getID_Usuario()));
				u1.setCorreo(nuevoCorreo);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		//CAMBIA FPERFIL
		try {
			c1.updateTabla("AJUSTES", "FPERFIL", numFPerfil, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			cambiaFPerfil();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	//CAMBIAR FOTO PERFIL
	@FXML void clickFoto(MouseEvent event) {
		if(event.getSource()==foto1) {
			fotoPerfil.setImage(foto1.getImage());
			numFPerfil = 1;
		}
		else if(event.getSource()==foto2) {
			fotoPerfil.setImage(foto2.getImage());
			numFPerfil = 2;
		}
		else if(event.getSource()==foto3) {
			fotoPerfil.setImage(foto3.getImage());
			numFPerfil = 3;
		}
		else if(event.getSource()==foto4) {
			fotoPerfil.setImage(foto4.getImage());
			numFPerfil = 4;
		}
		else if(event.getSource()==foto5) {
			fotoPerfil.setImage(foto5.getImage());
			numFPerfil = 5;
		}
		else if(event.getSource()==foto6) {
			fotoPerfil.setImage(foto6.getImage());
			numFPerfil = 6;
		}
		else if(event.getSource()==foto7) {
			fotoPerfil.setImage(foto7.getImage());
			numFPerfil = 7;
		}
		else if(event.getSource()==foto8) {
			fotoPerfil.setImage(foto8.getImage());
			numFPerfil = 8;
		}
		else if(event.getSource()==foto9) {
			fotoPerfil.setImage(foto9.getImage());
			numFPerfil = 9;
		}


	}


	//LIBROS:

	@FXML void clickListaLibros(MouseEvent e) { //RELLENA LA TABLA DE CADA UNA DE LAS LISTAS. JUGANDO CON EL VALOR DE CATEGORIA (GENERO)

		pListaLibros.setVisible(true);
		addBook.setVisible(true);
		if(e.getSource()==bLibAventuras) {
			categLib = "AVENTURAS";
		}
		else if(e.getSource()==bLibAmor) {
			categLib = "AMOR";
		}
		else if(e.getSource()==bLibTerror) {
			categLib = "TERROR";
		}
		else if(e.getSource()==bLibComedia) {
			categLib = "COMEDIA";
		}
		else if(e.getSource()==bLibFavoritos) {
			categLib = "FAVORITOS";
			addBook.setVisible(false);
		}


		rellenaTablaLibros(categLib);
	}

	@FXML void showSinglePaneLib(){


		if(libActual!=null) {
			accLibro = "";
			pLibroIndiv.setVisible(true);
			bGuardaEditaLibro.setVisible(false);
			bGuardaAgregaLibro.setVisible(false);
			lblErrorLib.setVisible(false);
			cambiaPortadaLib = false;
			initPanelesIndiv(); //HACE VISIBLES LOS LABELS E INVISIBLES LOS TEXTFIELD

			lblTituloLibroInd.setText(libActual.getTitulo());
			lblAutorLibroInd.setText(libActual.getAutor());
			lblGeneroLibroInd.setText(libActual.getGenero());
			lblNPagsLibro.setText(Integer.toString(libActual.getnPaginas()));
			lblYearLibroInd.setText(Integer.toString(libActual.getLanzamiento()));

			String portada = libActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
					image = new Image(getClass().getResource(portada).toString());
					portadaLibro.setImage(image);
				} catch (Exception e) { //SI SE LEVANTA LA EXCEPCION, LA RUTA ES RELATIVA
					//portadaLibro.setImage(null);
					image = new Image(portada);
					portadaLibro.setImage(image);
				}
			}
			else {
				portadaLibro.setImage(null);
			}


			//RESUMEN
			if(libActual.getResumen()!=null) {
				lblResumenLibroInd.setText(libActual.getResumen());
			}
			else {
				lblResumenLibroInd.setText("Este libro no tiene resumen");
			}

		}


	}

	@FXML void editSinglePaneLib(){


		if(libActual!=null && libActual.getID_Usuario()>0) { //NO PUEDES EDITAR LOS POR DEFECTO
			accLibro = "EDITAR";
			pLibroIndiv.setVisible(true);
			bGuardaEditaLibro.setVisible(true);
			bGuardaAgregaLibro.setVisible(false);
			lblErrorLib.setVisible(false);
			cambiaPortadaLib = false;

			//LABELS INVISIBLES
			lblTituloLibroInd.setVisible(false); lblAutorLibroInd.setVisible(false); lblNPagsLibro.setVisible(false); lblYearLibroInd.setVisible(false); lblResumenLibroInd.setVisible(false); 

			//TEXTFIELDS VISIBLES
			txtFieldTitLibro.setVisible(true); txtFieldAutorLibro.setVisible(true); txtFieldPagLibro.setVisible(true); txtFieldFechaLibro.setVisible(true); txtAreaResumenLibro.setVisible(true);

			//PONER LOS DATOS QUE TIENEN ACTUALMENTE Y EDITAR A PARTIR DE AHI
			txtFieldTitLibro.setText(libActual.getTitulo()); 						txtFieldAutorLibro.setText(libActual.getAutor()); 
			txtFieldPagLibro.setText(Integer.toString(libActual.getnPaginas())); 	txtFieldFechaLibro.setText(Integer.toString(libActual.getLanzamiento())); 
			lblGeneroLibroInd.setText(libActual.getGenero());


			String portada = libActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				image = new Image(portada);
				portadaLibro.setImage(image);
			}
			else {
				portadaLibro.setImage(null);
			}


			//RESUMEN
			if(libActual.getResumen()!=null) {
				txtAreaResumenLibro.setText(libActual.getResumen());
			}
			else {
				txtAreaResumenLibro.setText(null);
			}

		}

	}


	@FXML void agregaSinglePaneLib(){

		accLibro = "AGREGAR";  //PARA QUE AL PULSAR EN LA PORTADA, SOLO SALGA EL FILECHOOSER CUANDO ESTEMOS EDITANDO O AGREGANDO UN LIBRO
		pLibroIndiv.setVisible(true);
		bGuardaAgregaLibro.setVisible(true);
		bGuardaEditaLibro.setVisible(false);
		lblErrorLib.setVisible(false);
		cambiaPortadaLib = false;



		//LABELS INVISIBLES
		lblTituloLibroInd.setVisible(false); lblAutorLibroInd.setVisible(false); lblNPagsLibro.setVisible(false); lblYearLibroInd.setVisible(false); lblResumenLibroInd.setVisible(false); portadaLibro.setImage(null);

		//TEXTFIELDS VISIBLES
		txtFieldTitLibro.setVisible(true); txtFieldAutorLibro.setVisible(true); txtFieldPagLibro.setVisible(true); txtFieldFechaLibro.setVisible(true); txtAreaResumenLibro.setVisible(true);

		//VACIAR TEXTFIELDS PARA QUE EL USUARIO INTRODUZCA DATOS DE 0
		txtFieldTitLibro.setText(""); 	txtAreaResumenLibro.setText(""); 	txtFieldAutorLibro.setText(""); 
		txtFieldPagLibro.setText(""); 	txtFieldFechaLibro.setText(""); 
		lblGeneroLibroInd.setText(libActual.getGenero());

	}


	@FXML void clickGuardaLibroEditado(MouseEvent event) { 
		try {
			String titulo = txtFieldTitLibro.getText();
			String autor = txtFieldAutorLibro.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaLibro.getText());
			int pags = Integer.parseInt(txtFieldPagLibro.getText());
			String resumen = txtAreaResumenLibro.getText();

			if(!titulo.equals("") && !autor.equals("") && lanzamiento >= 0 && pags >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN) Y NO SER NEGATIVOS

				//UPDATE DATOS LIBRO
				libActual.setTitulo(titulo); libActual.setAutor(autor); libActual.setLanzamiento(lanzamiento); libActual.setnPaginas(pags);
				libActual.setResumen(resumen);
				c1.updateLibro(libActual);
				lblErrorLib.setVisible(false);

				//UPDATE PORTADA
				if(cambiaPortadaLib) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					libActual.setPortada(rutaPortadaLib);
					c1.actualizaPortadaLib(libActual, true);
				}



			}
			else {
				lblErrorLib.setText("ERROR: Campos incompletos");
				lblErrorLib.setVisible(true);

			}

		}catch (NumberFormatException e) {
			lblErrorLib.setText("ERROR: No se pudo editar");
			lblErrorLib.setVisible(true);


		}catch (Exception e) {
			lblErrorLib.setText("ERROR: No se pudo editar");
			lblErrorLib.setVisible(true);


		}
	}

	@FXML void clickGuardaLibroAgregado(MouseEvent event) {
		try {
			String titulo = txtFieldTitLibro.getText();
			String autor = txtFieldAutorLibro.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaLibro.getText());
			int pags = Integer.parseInt(txtFieldPagLibro.getText());
			String resumen = txtAreaResumenLibro.getText();
			int id_libro = c1.consultaNum("LIBROS", "MAX(ID_LIBRO)", null) +1;

			if(!titulo.equals("") && !autor.equals("") && lanzamiento >= 0 && pags >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN) Y NO SER NEGATIVOS
				libActual.setTitulo(titulo); libActual.setAutor(autor); libActual.setLanzamiento(lanzamiento); libActual.setnPaginas(pags); libActual.setID_Libro(id_libro);
				libActual.setID_Usuario(u1.getID_Usuario()); libActual.setResumen(resumen);

				libActual.setTerminado("NO"); //MODIFICAR Y PONER UNA OPCION PARA RELLENAR POR EL USUARIO

				if(cambiaPortadaLib) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					libActual.setPortada(rutaPortadaLib);
				}

				c1.agregaLibro(libActual, cambiaPortadaLib);
				lblErrorLib.setVisible(false);
			}
			else {
				lblErrorLib.setText("ERROR: Campos incompletos");
				lblErrorLib.setVisible(true);
			}

		}catch (NumberFormatException e) {
			lblErrorLib.setText("ERROR: No se pudo añadir");
			lblErrorLib.setVisible(true);


		}catch (Exception e) {
			lblErrorLib.setText("ERROR: No se pudo añadir");
			lblErrorLib.setVisible(true);
		}

	}

	@FXML void clickBEliminaLibro(MouseEvent event){
		if(libActual != null) {
			try {
				if(c1.eliminaLibro(libActual)) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	@FXML void refreshTablaLib(MouseEvent event) { //CLICK ACTUALIZAR TABLA LIBROS
		rellenaTablaLibros(categLib);
	}




	//JUEGOS:
	@FXML void clickListaJuegos(MouseEvent event) {

		pListaJuegos.setVisible(true);
		addGame.setVisible(true);

		if(event.getSource()==bJueFavoritos) {
			categJue = "FAVORITOS";
			addGame.setVisible(false);
		}
		else if(event.getSource()==bJueAccion) {
			categJue = "ACCION";
		}
		else if(event.getSource()==bJueDeportes) {
			categJue = "DEPORTES";
		}
		else if(event.getSource()==bJueTerror) {
			categJue = "TERROR";
		}
		else if(event.getSource()==bJueShooter) {
			categJue = "SHOOTER";
		}

		rellenaTablaJuegos(categJue);
	}

	@FXML void showSinglePaneJue(){
		if(jueActual!=null) {
			accJuego = "";
			pJuegoIndiv.setVisible(true);
			bGuardaAgregaJuego.setVisible(false);
			bGuardaEditaJuego.setVisible(false);
			lblErrorJue.setVisible(false);
			cambiaPortadaJue = false;
			initPanelesIndiv(); //HACE VISIBLES LOS LABELS E INVISIBLES LOS TEXTFIELD

			lblTituloJuegoInd.setText(jueActual.getTitulo());
			lblCompaniaJuegoInd.setText(jueActual.getEmpresa());
			lblGeneroJuegoInd.setText(jueActual.getGenero());
			lblPlataformaJuegoInd.setText(jueActual.getPlataforma());
			lblNHorasJuegoInd.setText(Integer.toString(jueActual.gethJugadas()));
			lblYearJuegoInd.setText(Integer.toString(jueActual.getLanzamiento()));

			String portada = jueActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
					image = new Image(getClass().getResource(portada).toString());
					portadaJuego.setImage(image);
				} catch (Exception e) { //SI SE LEVANTA LA EXCEPCION, LA RUTA ES RELATIVA
					//portadaLibro.setImage(null);
					image = new Image(portada);
					portadaJuego.setImage(image);
				}
			}
			else {
				portadaJuego.setImage(null);
			}

			//RESUMEN
			if(jueActual.getResumen()!=null) {
				lblResumenJuegoInd.setText(jueActual.getResumen());
			}
			else {
				lblResumenJuegoInd.setText("Este juego no tiene resumen");
			}

		}
	}

	@FXML void editSinglePaneJue(){

		if(jueActual!=null && jueActual.getID_Usuario()>0) { //NO PUEDES EDITAR LOS POR DEFECTO
			accJuego = "EDITAR";
			pJuegoIndiv.setVisible(true);
			bGuardaEditaJuego.setVisible(true);
			bGuardaAgregaJuego.setVisible(false);
			lblErrorJue.setVisible(false);
			cambiaPortadaJue = false;

			//LABELS INVISIBLES
			lblTituloJuegoInd.setVisible(false); lblPlataformaJuegoInd.setVisible(false); lblNHorasJuegoInd.setVisible(false); lblYearJuegoInd.setVisible(false); 
			lblCompaniaJuegoInd.setVisible(false); lblResumenJuegoInd.setVisible(false); 

			//TEXTFIELDS VISIBLES
			txtFieldTitJuego.setVisible(true); txtFieldCompaniaJuegoInd.setVisible(true); txtFieldHorJuego.setVisible(true); txtFieldFechaJuego.setVisible(true); txtAreaResumenJuego.setVisible(true);
			txtFieldPlataformaJuego.setVisible(true); 

			//PONER LOS DATOS QUE TIENEN ACTUALMENTE Y EDITAR A PARTIR DE AHI
			txtFieldTitJuego.setText(jueActual.getTitulo()); 						txtFieldCompaniaJuegoInd.setText(jueActual.getEmpresa()); 
			txtFieldHorJuego.setText(Integer.toString(jueActual.gethJugadas())); 	txtFieldFechaJuego.setText(Integer.toString(jueActual.getLanzamiento())); 
			lblGeneroJuegoInd.setText(jueActual.getGenero()); txtFieldPlataformaJuego.setText(jueActual.getPlataforma());


			String portada = jueActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				image = new Image(portada);
				portadaJuego.setImage(image);
			}
			else {
				portadaJuego.setImage(null);
			}


			//RESUMEN
			if(jueActual.getResumen()!=null) {
				txtAreaResumenJuego.setText(jueActual.getResumen());
			}
			else {
				txtAreaResumenJuego.setText(null);
			}

		}



	}


	@FXML void agregaSinglePaneJue(){
		accJuego = "AGREGAR";  //PARA QUE AL PULSAR EN LA PORTADA, SOLO SALGA EL FILECHOOSER CUANDO ESTEMOS EDITANDO O AGREGANDO UN LIBRO
		pJuegoIndiv.setVisible(true);
		bGuardaAgregaJuego.setVisible(true);
		bGuardaEditaJuego.setVisible(false);
		lblErrorJue.setVisible(false);
		cambiaPortadaJue = false;



		//LABELS INVISIBLES
		lblTituloJuegoInd.setVisible(false); lblPlataformaJuegoInd.setVisible(false); lblNHorasJuegoInd.setVisible(false); lblYearJuegoInd.setVisible(false); 
		lblCompaniaJuegoInd.setVisible(false); lblResumenJuegoInd.setVisible(false); portadaJuego.setImage(null);

		//TEXTFIELDS VISIBLES
		txtFieldTitJuego.setVisible(true); txtFieldCompaniaJuegoInd.setVisible(true); txtFieldHorJuego.setVisible(true); txtFieldFechaJuego.setVisible(true); txtAreaResumenJuego.setVisible(true);
		txtFieldPlataformaJuego.setVisible(true);

		//VACIAR TEXTFIELDS PARA QUE EL USUARIO INTRODUZCA DATOS DE 0
		txtFieldTitJuego.setText(""); 	txtAreaResumenJuego.setText(""); 	txtFieldCompaniaJuegoInd.setText(""); 
		txtFieldHorJuego.setText(""); 	txtFieldFechaJuego.setText(""); 	txtFieldPlataformaJuego.setText(""); 
		lblGeneroJuegoInd.setText(jueActual.getGenero());
	}

	@FXML void clickGuardaJuegoEditado(MouseEvent event) { 
		try {

			String titulo = txtFieldTitJuego.getText();
			String empresa = txtFieldCompaniaJuegoInd.getText();
			String plataforma = txtFieldPlataformaJuego.getText();
			String resumen = txtAreaResumenJuego.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaJuego.getText());
			int horas = Integer.parseInt(txtFieldHorJuego.getText());


			if(!titulo.equals("") && !plataforma.equals("") && lanzamiento >= 0 && horas >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN Y EMPRESA)

				//UPDATE DATOS LIBRO
				jueActual.setTitulo(titulo); jueActual.setPlataforma(plataforma); jueActual.setLanzamiento(lanzamiento); jueActual.sethJugadas(horas);;
				jueActual.setResumen(resumen); jueActual.setEmpresa(empresa);
				c1.updateJuego(jueActual);
				lblErrorJue.setVisible(false);

				//UPDATE PORTADA
				if(cambiaPortadaJue) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					jueActual.setPortada(rutaPortadaJue);
					c1.actualizaPortadaJue(jueActual, true);
				}



			}
			else {
				lblErrorJue.setText("ERROR: Campos incompletos");
				lblErrorJue.setVisible(true);

			}

		}catch (NumberFormatException e) {
			lblErrorJue.setText("ERROR: No se pudo editar");
			lblErrorJue.setVisible(true);


		}catch (Exception e) {
			lblErrorJue.setText("ERROR: No se pudo editar");
			lblErrorJue.setVisible(true);


		}
	}

	@FXML void clickGuardaJuegoAgregado(MouseEvent event) {
		try {

			String titulo = txtFieldTitJuego.getText();
			String empresa = txtFieldCompaniaJuegoInd.getText();
			String plataforma = txtFieldPlataformaJuego.getText();
			String resumen = txtAreaResumenJuego.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaJuego.getText());
			int horas = Integer.parseInt(txtFieldHorJuego.getText());
			int id_juego = c1.consultaNum("JUEGOS", "MAX(ID_JUEGO)", null) +1;

			if(!titulo.equals("") && !plataforma.equals("") && lanzamiento >= 0 && horas >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN) Y NO SER NEGATIVOS
				jueActual.setTitulo(titulo); jueActual.setPlataforma(plataforma); jueActual.setLanzamiento(lanzamiento); jueActual.sethJugadas(horas);;
				jueActual.setResumen(resumen); jueActual.setEmpresa(empresa); jueActual.setID_Juego(id_juego); jueActual.setID_Usuario(u1.getID_Usuario());

				jueActual.setTerminado("NO"); //MODIFICAR Y PONER UNA OPCION PARA RELLENAR POR EL USUARIO

				if(cambiaPortadaJue) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					jueActual.setPortada(rutaPortadaJue);
				}

				c1.agregaJuego(jueActual, cambiaPortadaJue);
				lblErrorJue.setVisible(false);
			}
			else {
				lblErrorJue.setText("ERROR: Campos incompletos");
				lblErrorJue.setVisible(true);
			}

		}catch (NumberFormatException e) {
			lblErrorJue.setText("ERROR: No se pudo añadir");
			lblErrorJue.setVisible(true);


		}catch (Exception e) {
			lblErrorJue.setText("ERROR: No se pudo añadir");
			lblErrorLib.setVisible(true);
			e.printStackTrace();

		}


	}


	@FXML void clickBEliminaJuego(MouseEvent event){
		if(jueActual!=null) {
			try {
				if(c1.eliminaJuego(jueActual)) {
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}


	@FXML void refreshTablaJue(MouseEvent event) { //CLICK ACTUALIZAR TABLA LIBROS
		rellenaTablaJuegos(categJue);
	}



	@FXML void backSinglePane(MouseEvent event) {

		if(event.getSource() == btnBackPaneLibros) {
			pLibroIndiv.setVisible(false);

		}
		else if(event.getSource() == btnBackPaneJuegos) {
			pJuegoIndiv.setVisible(false);

		}
	}


	//MUSICA:
	@FXML void clickListaMusica(MouseEvent event) {
		if(event.getSource()==bMusReggaeton) {
			categMus = "REGGAETON";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #ff748d, #ff7daf);"+estiloCabMus);
		}
		else if(event.getSource()==bMusPop) {
			categMus = "POP";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #00c0fa, #015eea);"+estiloCabMus);
		}
		else if(event.getSource()==bMusElectronica) {
			categMus = "ELECTRONICA";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #facc22, #f83600);"+estiloCabMus);
		}
		else if(event.getSource()==bMusFlamenco) {
			categMus = "FLAMENCO";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #e1afcc, #7530e3);"+estiloCabMus);
		}
		else if(event.getSource()==bMusRock) {
			categMus = "ROCK";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom,#fba981, #5c2c15);"+estiloCabMus);
		}

		lblTitListaMus.setText(categMus);
		tablaMusica.setVisible(true);
		rellenaTablaMus(categJue);
		pListaMus.setVisible(true);


	}


	@FXML void clickBackMusica(MouseEvent event) {
		pListaMus.setVisible(false);
		pMusica.setVisible(true);
	}   



	//MEDIAPLAYER (PRUEBA)
	@FXML
	void clickBPlayMusica(MouseEvent event) {

		if(!activo) {

			activo = true;
			bPlayMusica.setImage(imgPauseMus);
			reproductor.play();
			actualizaBarraMusica(duracionTotal);

		}
		else {
			activo = false;
			bPlayMusica.setImage(imgPlayMus);
			reproductor.pause();
		}

	} 


	//PANEL AJUSTES:

	@FXML void clickBEditarColor(MouseEvent event) { //CAMBIAR EL COLOR PANEL DEL MENU DE LA IZQUIERDA
		if(!abierto) {
			pPanelColores.setVisible(true);
			abierto=true;
		}
		else {
			pPanelColores.setVisible(false);
			abierto=false;
		}
	}

	//CAMBIAR COLORES TEMA	
	@FXML void clickColorFondo (MouseEvent event) {
		if(event.getSource()==lblColorAzul) {
			lblColorTema.setStyle("-fx-background-color: #08426c; -fx-border-color: white; -fx-border-width: 3px;");
			color = 0;
		}
		else if(event.getSource()==lblColorNaranja) {
			lblColorTema.setStyle("-fx-background-color: #fb7b14; -fx-border-color: white; -fx-border-width: 3px;");
			color = 1;
		}
		else if(event.getSource()==lblColorVerde) {
			lblColorTema.setStyle("-fx-background-color:  #9fb65a; -fx-border-color: white; -fx-border-width: 3px;");
			color = 2;
		}
		else if(event.getSource()==lblColorRojo) {
			lblColorTema.setStyle("-fx-background-color:  #c0060e; -fx-border-color: white; -fx-border-width: 3px;");
			color = 3;
		}
		else if(event.getSource()==lblColorMorado) {
			lblColorTema.setStyle("-fx-background-color:  #954c7b; -fx-border-color: white; -fx-border-width: 3px;");
			color = 4;
		}
	}


	@FXML void clickBGuardarAjustes(MouseEvent event) {
		try {
			cambiaColorFondo();
			c1.updateTabla("AJUSTES", "CFONDO", color, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			c1.updateTabla("AJUSTES", "VOLUMEN", Math.floor(barraVolAjustes.getValue()), "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));

			vol = Math.floor(barraVolAjustes.getValue());
			reproductor.setVolume(vol);

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML void clickBEliminarCuenta(MouseEvent event) {

		try {
			if(c1.eliminarUsuario(Usuario.getUsuario())) {
				try {
					clickBVolver(event);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	@FXML void arrastraVolAjustes(MouseEvent event) {

		lblVolAjustes.setText(Integer.toString((int)(Math.floor(barraVolAjustes.getValue()))));
	}


	@FXML void clickBInformacion(MouseEvent event) { //PULSA ENLACE WEB O REPOSITORIO
		String enlace;
		if(event.getSource() == bWeb) {
			enlace = "https://byruby12.github.io/Bibliotech/";
		}
		else if (event.getSource() == bInstagram) {
			enlace = "https://www.instagram.com/bibliotech2023/";
		}
		else if (event.getSource() == bTwitter ) {
			enlace = "https://twitter.com/Bibliotech2023";
		}
		else if (event.getSource() == bYoutube) {
			enlace = "https://www.youtube.com/@bibliotech2023";
		}
		else {
			enlace = "https://github.com/Marioby9/BiblioTech";
		}
		try {
			Correo.abrirURL(enlace);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

	}


	@FXML void clickCarpetaCanciones(MouseEvent event) {
		DirectoryChooser dChooser = new DirectoryChooser();
		dChooser.setTitle("Selecciona una carpeta");

		File selectedFile = dChooser.showDialog(null);
		if (selectedFile != null) {
			carpCanciones = selectedFile.getAbsolutePath();
			try {
				Ficheros.creaCarpetasMus(carpCanciones);
				System.out.println("Carpetas creadas en: "+carpCanciones);
				c1.updateTabla("AJUSTES", "CARP_MUSICA", carpCanciones, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}


	//FUNCIONES VARIAS 


	private void cambiaColorFondo(){
		if(color==0) { imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu.png"))); 				lblColorTema.setStyle("-fx-background-color: #08426c; -fx-border-color: white; -fx-border-width: 3px;"); } 	
		else if(color==1) {  imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu1.png")));  		lblColorTema.setStyle("-fx-background-color: #fb7b14; -fx-border-color: white; -fx-border-width: 3px;");}
		else if(color==2) { imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu3.png"))); 		lblColorTema.setStyle("-fx-background-color: #9fb65a; -fx-border-color: white; -fx-border-width: 3px;");	}
		else if(color==3) {  imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu4.png"))); 		lblColorTema.setStyle("-fx-background-color: #c0060e; -fx-border-color: white; -fx-border-width: 3px;"); }
		else if(color==4) {  imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu2.png"))); 		lblColorTema.setStyle("-fx-background-color: #954c7b; -fx-border-color: white; -fx-border-width: 3px;"); }

	}



	private void cambiaFPerfil(){

		if(numFPerfil==0) { 			fotoPerfil.setImage(fPerfil);	  } 	
		else if(numFPerfil==1) {  fotoPerfil.setImage(foto1.getImage()); }
		else if(numFPerfil==2) {  fotoPerfil.setImage(foto2.getImage()); }
		else if(numFPerfil==3) {  fotoPerfil.setImage(foto3.getImage()); }
		else if(numFPerfil==4) {  fotoPerfil.setImage(foto4.getImage()); }
		else if(numFPerfil==5) {  fotoPerfil.setImage(foto5.getImage()); }
		else if(numFPerfil==6) {  fotoPerfil.setImage(foto6.getImage()); }
		else if(numFPerfil==7) {  fotoPerfil.setImage(foto7.getImage()); }
		else if(numFPerfil==8) {  fotoPerfil.setImage(foto8.getImage()); }
		else if(numFPerfil==9) {  fotoPerfil.setImage(foto9.getImage()); }

		fotoPerfil1.setImage(fotoPerfil.getImage());

	}


	private void initPanelesIndiv() {
		lblAutorLibroInd.setVisible(true);
		lblGeneroLibroInd.setVisible(true);
		lblNPagsLibro.setVisible(true);
		lblTituloLibroInd.setVisible(true);
		lblYearLibroInd.setVisible(true);
		lblResumenLibroInd.setVisible(true);

		lblTituloJuegoInd.setVisible(true);
		lblGeneroJuegoInd.setVisible(true);
		lblPlataformaJuegoInd.setVisible(true);
		lblYearJuegoInd.setVisible(true);
		lblCompaniaJuegoInd.setVisible(true);
		lblNHorasJuegoInd.setVisible(true);
		lblResumenJuegoInd.setVisible(true);

		txtAreaResumenLibro.setVisible(false);
		txtFieldAutorLibro.setVisible(false);
		txtFieldFechaLibro.setVisible(false);
		txtFieldTitLibro.setVisible(false);
		txtFieldPagLibro.setVisible(false);


		txtAreaResumenJuego.setVisible(false);
		txtFieldTitJuego.setVisible(false);
		txtFieldGeneroJuego.setVisible(false);
		txtFieldCompaniaJuegoInd.setVisible(false);
		txtFieldFechaJuego.setVisible(false);
		txtFieldHorJuego.setVisible(false);
		txtFieldPlataformaJuego.setVisible(false);

	}

	//LIBROS, JUEGOS, CANCIONES FUNCIONES EQUIVALENTES
	private void rellenaTablaLibros(String categoria) {

		try {
			ObservableList<Libro> listaLibros;
			if(categoria.equalsIgnoreCase("FAVORITOS")) {
				listaLibros = c1.fillFavBooks(u1.getID_Usuario());
			}
			else {
				listaLibros = c1.fillListBooks(categoria, u1.getID_Usuario()); //NOS TRAEMOS LA LISTA DE LA CONSULTA
			}

			//CUANDO CAMBIAMOS DE CATEGORIA, PONEMOS EL TITULO Y LA PORTADA DEL PRIMER LIBRO DE LA LISTA
			if(listaLibros.size()!=0) { 
				bFavLib.setVisible(true);
				libActual = listaLibros.get(0);
				lblTituloListaLib.setText(libActual.getTitulo()); 

				String portada = libActual.getPortada(); 
				Image image; 

				if(portada!=null) {
					try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
						image = new Image(getClass().getResource(portada).toString());
						portadaListaLib.setImage(image);
					} catch (Exception e) {
						//portadaListaLib.setImage(null);
						image = new Image(portada);
						portadaListaLib.setImage(image);
					}
				}

				Image imgFav;
				if(libActual.getFavorito().equalsIgnoreCase("NO")) {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				}
				else {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				}

				bFavLib.setImage(imgFav);

			}
			else { //SI NO HAY ELEMENTOS EN LA LISTA:
				portadaListaLib.setImage(null);
				lblTituloListaLib.setText("No hay libros");
				bFavLib.setVisible(false);
				libActual = null;
			}

			tablaLibros.setItems(listaLibros); //RELLENAR TABLA CON LOS LIBROS DE CADA CATEGORIA

			tituloLib.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
			autorLib.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));
			fechaLib.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLanzamiento()).asObject()); //SE LE PONE AS OBJECT() PORQUE ESPERA UN INTEGER Y ESTAMOS PASANDO UN INT
			nPagsLib.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getnPaginas()).asObject());


		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}


	private void rellenaTablaJuegos(String categoria) {

		try {
			ObservableList<Juego> listaJuegos; 
			if(categoria.equalsIgnoreCase("FAVORITOS")) {
				listaJuegos = c1.fillFavGames(u1.getID_Usuario());
			}
			else {
				listaJuegos = c1.fillListGames(categoria, u1.getID_Usuario()); //NOS TRAEMOS LA LISTA DE LA CONSULTA
			}

			//CUANDO CAMBIAMOS DE CATEGORIA, PONEMOS EL TITULO Y LA PORTADA DEL PRIMER LIBRO DE LA LISTA
			if(listaJuegos.size()!=0) { 
				bFavJue.setVisible(true);
				jueActual = listaJuegos.get(0);
				lblTituloListaJue.setText(jueActual.getTitulo()); 

				String portada = jueActual.getPortada(); 
				Image image; 

				if(portada!=null) {
					try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
						image = new Image(getClass().getResource(portada).toString());
						portadaListaJue.setImage(image);
					} catch (Exception e) {
						//portadaListaLib.setImage(null);
						image = new Image(portada);
						portadaListaJue.setImage(image);
					}
				}

				Image imgFav;
				if(jueActual.getFavorito().equalsIgnoreCase("NO")) {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				}
				else {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				}

				bFavJue.setImage(imgFav);

			}
			else { //SI NO HAY ELEMENTOS EN LA LISTA:
				portadaListaJue.setImage(null);
				bFavJue.setVisible(false);
				lblTituloListaJue.setText("No hay Juegos");
				jueActual = null;
			}

			tablaJuegos.setItems(listaJuegos); //RELLENAR TABLA CON LOS LIBROS DE CADA CATEGORIA

			tituloJue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
			plataformaJue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlataforma()));
			fechaJue.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLanzamiento()).asObject()); //SE LE PONE AS OBJECT() PORQUE ESPERA UN INTEGER Y ESTAMOS PASANDO UN INT
			horasJue.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().gethJugadas()).asObject());



		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	private void rellenaTablaMus(String categoria) {

		try {
			ObservableList<Cancion> listaMusica;
			listaMusica = Ficheros.leeCarpetaMus(carpCanciones, categMus);
			lblNumCanciones.setText(listaMusica.size()+" Canciones");

			//CUANDO CAMBIAMOS DE CATEGORIA, PONEMOS EL TITULO Y LA PORTADA DEL PRIMER LIBRO DE LA LISTA
			if(listaMusica.size()!=0 && !activo) { 
				bFavLib.setVisible(true);
				cancActual = listaMusica.get(0);
				barraMusica.setProgress(0);
				lblTitReproductor.setText(cancActual.getArtista()+ "  |  "+cancActual.getNombre());
				
				String rutaArchivo = cancActual.getRuta();
				File archivo = new File(rutaArchivo);
				Media media = new Media(archivo.toURI().toString());
				reproductor = new MediaPlayer(media);
				reproductor.setOnReady(() -> {
					duracionTotal = reproductor.getTotalDuration();
				});

			}
			else if(listaMusica.size()==0 && !activo) { //SI NO HAY ELEMENTOS EN LA LISTA:
				tablaMusica.setVisible(false);
				cancActual = null;
				lblTitReproductor.setText("ARTISTA  |  TITULO CANCION  ");
				barraMusica.setProgress(0);
			}
			else if(listaMusica.size()==0 && activo) { //SI NO HAY ELEMENTOS EN LA LISTA:
				tablaMusica.setVisible(false);
			}


			tablaMusica.setItems(listaMusica); //RELLENAR TABLA CON LOS LIBROS DE CADA CATEGORIA

			tituloCanc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
			artistaCanc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtista()));
			generoCanc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@FXML void clickEligeLibro(MouseEvent event){ //CUANDO PULSAMOS UN OBJETO DE LA TABLA, COGEMOS SUS DATOS
		libActual = tablaLibros.getSelectionModel().getSelectedItem();

		if(libActual!=null) {
			String portada = libActual.getPortada(); 
			if(portada!=null) {
				try {
					Image image = new Image(getClass().getResource(portada).toString()); //RUTA RELATIVA
					portadaListaLib.setImage(image);
				} catch (Exception e) {
					Image image = new Image(portada); //RUTA ABSOLUTA
					portadaListaLib.setImage(image);

				}
			}
			else {
				portadaListaLib.setImage(null);
			}

			lblTituloListaLib.setText(libActual.getTitulo());


			Image imgFav;
			if(libActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
			}

			bFavLib.setImage(imgFav);

		}
	}

	@FXML void clickEligeCancion(MouseEvent event){ //CUANDO PULSAMOS UN OBJETO DE LA TABLA, COGEMOS SUS DATOS
		cancActual = tablaMusica.getSelectionModel().getSelectedItem();
		if(cancActual != null) {
			lblTitReproductor.setText(cancActual.getArtista()+ "  |  "+cancActual.getNombre());
			reproductor.stop();
	
			String rutaArchivo = cancActual.getRuta();
			File archivo = new File(rutaArchivo);
			Media media = new Media(archivo.toURI().toString());
			reproductor = new MediaPlayer(media);
	
			bPlayMusica.setImage(imgPlayMus);
			reproductor.setVolume(vol);
	
			if(timeline!=null) {
				timeline.stop();
			}
			barraMusica.setProgress(0);
	
			reproductor.setOnReady(() -> {
				duracionTotal = reproductor.getTotalDuration();
				//MOSTRAR DURACION DE LA CANCION 
				int minutos = (int) duracionTotal.toMinutes();
				int segundos = (int) (duracionTotal.toSeconds() % 60);
				String duracionString = String.format("%02d:%02d", minutos, segundos);
				lblTiempoCanc.setText(duracionString);
			});
		}

	}

	void actualizaBarraMusica(Duration duracion) { //MEJORAR!!!

		
		if (timeline != null) {
	        timeline.stop();
	    }
		
		timeline = new Timeline(
				new KeyFrame(Duration.ZERO, e -> {
					double progress = reproductor.getCurrentTime().toMillis() / duracion.toMillis();

					if(activo) {
						duracionTotal = duracionTotal.subtract(Duration.seconds(1));
					}

					// PASAR DURACION A MINUTOS Y SEGUNDOS
					int minutos = (int) duracionTotal.toMinutes();
					int segundos = (int) (duracionTotal.toSeconds() % 60);
					String duracionString = String.format("%02d:%02d", minutos, segundos);
					//MOSTRAR DURACION DE LA CANCION 

					lblTiempoCanc.setText(duracionString);				
					barraMusica.setProgress(progress);

				}),
				new KeyFrame(Duration.seconds(1))
				);
		timeline.setCycleCount(Animation.INDEFINITE);
		if(activo) {
			timeline.play();
		}
		else {
			timeline.stop();
			barraMusica.setProgress(0);
		}

	}

	@FXML void clickEligeJuego(MouseEvent event){ //CUANDO PULSAMOS UN OBJETO DE LA TABLA, COGEMOS SUS DATOS
		jueActual = tablaJuegos.getSelectionModel().getSelectedItem();

		if(jueActual!=null) {
			String portada = jueActual.getPortada(); 
			if(portada!=null) {
				try {
					Image image = new Image(getClass().getResource(portada).toString()); //RUTA RELATIVA
					portadaListaJue.setImage(image);
				} catch (Exception e) {
					Image image = new Image(portada); //RUTA ABSOLUTA
					portadaListaJue.setImage(image);


				}
			}
			else {
				portadaListaJue.setImage(null);
			}

			lblTituloListaJue.setText(jueActual.getTitulo());

			Image imgFav;
			if(jueActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
			}

			bFavJue.setImage(imgFav);
		}
	}


	@FXML void cambiaPortadaLibro(MouseEvent event) { //SOLO DISPONIBLE CUANDO EDITAS O AGREGAS UN LIBRO

		if(accLibro.equalsIgnoreCase("EDITAR") || accLibro.equalsIgnoreCase("AGREGAR") ) { 

			FileChooser fChooser = new FileChooser();
			fChooser.setTitle("Selecciona una imagen");
			fChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
					);
			File selectedFile = fChooser.showOpenDialog(null);
			if (selectedFile != null) {
				rutaPortadaLib = selectedFile.toURI().toString();
				Image image = new Image(rutaPortadaLib);
				portadaLibro.setImage(image);
				cambiaPortadaLib = true;
			}
		}

	}

	@FXML void cambiaPortadaJuego(MouseEvent event) { //SOLO DISPONIBLE CUANDO EDITAS O AGREGAS UN JUEGO

		if(accJuego.equalsIgnoreCase("EDITAR") || accJuego.equalsIgnoreCase("AGREGAR") ) { 

			FileChooser fChooser = new FileChooser();
			fChooser.setTitle("Selecciona una imagen");
			fChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
					);
			File selectedFile = fChooser.showOpenDialog(null);
			if (selectedFile != null) {
				rutaPortadaJue = selectedFile.toURI().toString();
				Image image = new Image(rutaPortadaJue);
				portadaJuego.setImage(image);
				cambiaPortadaJue = true;
			}
		}

	}

	@FXML void clickBFavLib(MouseEvent event) { //CLICK PARA AGREGAR A FAVORITOS
		try {

			Image imgFav;
			if(libActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				libActual.setFavorito("SI");
				c1.updateTabla("LIBROS", "FAVORITO", "SI", "ID_LIBRO = "+libActual.getID_Libro());
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				libActual.setFavorito("NO");
				c1.updateTabla("LIBROS", "FAVORITO", "NO", "ID_LIBRO = "+libActual.getID_Libro());
			}

			bFavLib.setImage(imgFav);
		}
		catch (Exception e) {
			System.out.println("ERROR AL AÑADIR FAVORITO");
			e.printStackTrace();
		}
	}

	@FXML void clickBFavJue(MouseEvent event) { //CLICK PARA AGREGAR A FAVORITOS
		try {

			Image imgFav;
			if(jueActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				jueActual.setFavorito("SI");
				c1.updateTabla("JUEGOS", "FAVORITO", "SI", "ID_JUEGO = "+jueActual.getID_Juego());
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				jueActual.setFavorito("NO");
				c1.updateTabla("JUEGOS", "FAVORITO", "NO", "ID_JUEGO = "+jueActual.getID_Juego());
			}

			bFavJue.setImage(imgFav);
		}
		catch (Exception e) {
			System.out.println("ERROR AL AÑADIR FAVORITO");
			e.printStackTrace();
		}
	}


}
