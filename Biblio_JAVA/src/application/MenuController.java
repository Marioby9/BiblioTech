package application;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import utilidades.Conexion;
import utilidades.Correo;
import utilidades.Usuario;

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
	@FXML private Pane pJuegos;
	//BOTONES PJUEGOS
	@FXML private ImageView bJueAccion, bJueDeportes, bJueFavoritos, bJueShooter, bJueTerror, btnBackPaneJuegos;


	//PANELES LISTA JUEGOS
	@FXML private Pane pJueAccion, pJueDeportes, pJueFavoritos, pJueShooter, pJueTerror, pJuegoIndiv;


	//PANEL LIBROS 
	@FXML private Pane pLibros;
	//BOTONES PLIBROS
	@FXML private ImageView bLibAmor, bLibAventuras, bLibComedia, bLibFavoritos, bLibTerror, btnBackPaneLibros;


	//PANELES LISTA LIBROS
	@FXML private Pane pLibFavoritos, pLibAventuras, pLibAmor, pLibTerror, pLibComedia, pLibroIndiv;

	
	//MUSICA
	@FXML private Pane pMusica;
	//BOTONES PMUSICA
	@FXML private ImageView bMusElectronica, bMusFlamenco, bMusPop, bMusReggaeton, bMusRock;

	//PANELES LISTAS MUSICA + SCROLLPANES CANCIONES
	@FXML private Pane pReggaeton, pPop, pElectronica, pFlamenco, pRock;
	@FXML private ScrollPane pListaReggaeton, pListaPop, pListaElectronica, pListaFlamenco, pListaRock;


	//CONTROLADOR
	LogInController controlador;
	//MENU IZQUIERDA OPCIONES

	@FXML private ImageView bVolver;


	//PANEL AJUSTES:
	@FXML private Pane pAjustes;
	@FXML private Label lblColorTema;
	@FXML private ImageView bEditarColor, bWeb, bGithub;
	@FXML private Pane pPanelColores;		boolean abierto;  int color;
	@FXML private Slider barraVolAjustes;
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
		pReggaeton.setVisible(false);
		pPop.setVisible(false);
		pElectronica.setVisible(false);
		pFlamenco.setVisible(false);
		pRock.setVisible(false);
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


		//QUITAR BARRA HORIZONTAL DE SCROLLPANES
		pListaReggaeton.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		pListaPop.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		pListaElectronica.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		pListaFlamenco.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
		pListaRock.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);


		//VOLUMEN:
		int vol = 0;
		try {
			vol = c1.consultaNum("AJUSTES", "VOLUMEN", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()) );
		} catch (SQLException e) {
			e.printStackTrace();
		}

		barraVolAjustes.setValue(vol);
		lblVolAjustes.setText(Integer.toString((int)(Math.floor(barraVolAjustes.getValue()))));





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
		pPerfil.setVisible(true);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);
		pProfilePics.setVisible(false);
		pPanelColores.setVisible(false);
		pJuegoIndiv.setVisible(false);
		pLibroIndiv.setVisible(false);

	}

	@FXML void clickBLibros(MouseEvent event) {
		pLibros.setVisible(true);
		pLibFavoritos.setVisible(false);
		pLibAventuras.setVisible(false);
		pLibAmor.setVisible(false);
		pLibTerror.setVisible(false);
		pLibComedia.setVisible(false);

		pPerfil.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);
		pProfilePics.setVisible(false);
		pPanelColores.setVisible(false);
		pJuegoIndiv.setVisible(false);
		pLibroIndiv.setVisible(false);

	}

	@FXML void clickBJuegos(MouseEvent event) {
		pJuegos.setVisible(true);
		pJueFavoritos.setVisible(false);
		pJueAccion.setVisible(false);
		pJueDeportes.setVisible(false);
		pJueTerror.setVisible(false);
		pJueShooter.setVisible(false);

		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(false);
		pProfilePics.setVisible(false);
		pPanelColores.setVisible(false);
		pJuegoIndiv.setVisible(false);
		pLibroIndiv.setVisible(false);

	}

	@FXML void clickBMusica(MouseEvent event) {
		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(true);
		pAjustes.setVisible(false);
		pProfilePics.setVisible(false);
		pPanelColores.setVisible(false);

		pReggaeton.setVisible(false);
		pPop.setVisible(false);
		pElectronica.setVisible(false);
		pFlamenco.setVisible(false);
		pRock.setVisible(false);
		pJuegoIndiv.setVisible(false);
		pLibroIndiv.setVisible(false);

	}

	@FXML void clickBAjustes(MouseEvent event) {
		pPerfil.setVisible(false);
		pLibros.setVisible(false);
		pJuegos.setVisible(false);
		pMusica.setVisible(false);
		pAjustes.setVisible(true);
		pProfilePics.setVisible(false);
		pPanelColores.setVisible(false);
		pJuegoIndiv.setVisible(false);
		pLibroIndiv.setVisible(false);
	}


	@FXML void clickBEditarFoto(MouseEvent event) {
		if(pProfilePics.isVisible()==false) {
			pProfilePics.setVisible(true);
		}
		else {
			pProfilePics.setVisible(false);
		}


	}

	@FXML void clickBEliminarFoto(MouseEvent event) { //PONE LA FOTO POR DEFAULT
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

	@FXML void clickListaLibros(MouseEvent event) {
		if(event.getSource()==bLibAventuras) {
			pLibAventuras.setVisible(true);
		}
		else if(event.getSource()==bLibAmor) {
			pLibAmor.setVisible(true);
		}
		else if(event.getSource()==bLibTerror) {
			pLibTerror.setVisible(true);
		}
		else if(event.getSource()==bLibComedia) {
			pLibComedia.setVisible(true);
		}
		else if(event.getSource()==bLibFavoritos) {
			pLibFavoritos.setVisible(true);
		}


	}

	//JUEGOS
	//JUEGOS:
	@FXML void clickListaJuegos(MouseEvent event) {
		if(event.getSource()==bJueFavoritos) {
			pJueFavoritos.setVisible(true);
		}
		else if(event.getSource()==bJueAccion) {
			pJueAccion.setVisible(true);
		}
		else if(event.getSource()==bJueDeportes) {
			pJueDeportes.setVisible(true);

		}
		else if(event.getSource()==bJueTerror) {
			pJueTerror.setVisible(true);
		}
		else if(event.getSource()==bJueShooter) {
			pJueShooter.setVisible(true);
		}


	}

	//PANEL INDIVIDUAL JUEGOS LIBROS
	@FXML void openSingleBookPane(MouseEvent event) { //ABRE EL PANEL INDIVIDUAL AL PULSAR EN UNO DE LOS LIBROS
		pLibroIndiv.setVisible(true);

	}

	@FXML void openSingleGamePane(MouseEvent event) {
		pJuegoIndiv.setVisible(true);
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
			pReggaeton.setVisible(true);
			listaReggaeton();
		}
		else if(event.getSource()==bMusPop) {
			pPop.setVisible(true);
		}
		else if(event.getSource()==bMusElectronica) {
			pElectronica.setVisible(true);
		}
		else if(event.getSource()==bMusFlamenco) {
			pFlamenco.setVisible(true);
		}
		else if(event.getSource()==bMusRock) {
			pRock.setVisible(true);
		}
	}


	@FXML void clickBackMusica(MouseEvent event) {
		pMusica.setVisible(true);
		pReggaeton.setVisible(false);
		pPop.setVisible(false);
		pElectronica.setVisible(false);
		pFlamenco.setVisible(false);
		pRock.setVisible(false);

	}   

	// PANEL REGGAETON  
	void listaReggaeton(){

	}   



	//MEDIAPLAYER (PRUEBA)
	@FXML
	void clickBPlayMusica(ActionEvent event) {


		//Media media = new Media(getClass().getResource("/canciones/cancion1.mp3").toExternalForm()); //FUNCIONA EN ECLIPSE Y NO EN EJECUTABLE
		Media media = new Media(getClass().getResource("/canciones/shakira-bzrp.mp3").toString()); 	   //FUNCIONA EN ECLIPSE Y NO EN EJECUTABLE

		MediaPlayer reproductor = new MediaPlayer(media);
		boolean activo = false;
		double volumen;
		try {
			volumen = c1.consultaNum("Ajustes", "Volumen", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
		} catch (SQLException e) {
			e.printStackTrace();
			volumen=0;
		}

		reproductor.setVolume(volumen/100);
		if(activo==false) {
			reproductor.play();
			activo=true;
		}
		else {
			reproductor.pause();
			activo=false;
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
		try {//FPERFIL
			cambiaColorFondo();
			c1.updateTabla("AJUSTES", "CFONDO", color, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));

		} catch (SQLException e) {
			e.printStackTrace();
		}

		try {//VOLUMEN
			c1.updateTabla("AJUSTES", "VOLUMEN", Math.floor(barraVolAjustes.getValue()), "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
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
			enlace = "https://probibliotech.000webhostapp.com/";
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







}
