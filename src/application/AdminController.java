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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilidades.Conexion;
import utilidades.Usuario;

public class AdminController {

    @FXML
    private ImageView bEditarFoto;

    @FXML
    private ImageView bEliminarFoto;

    @FXML
    private ImageView bVolver;

    @FXML
    private ImageView foto1;

    @FXML
    private ImageView foto2;

    @FXML
    private ImageView foto3;

    @FXML
    private ImageView foto4;

    @FXML
    private ImageView foto5;

    @FXML
    private ImageView foto6;

    @FXML
    private ImageView foto7;

    @FXML
    private ImageView foto8;

    @FXML
    private ImageView foto9;

    @FXML
    private ImageView fotoPerfil;

    @FXML
    private ImageView fotoPerfil1;

    @FXML
    private ImageView imgFondoMenu;
    
    @FXML
    private ImageView ImgAdminUsuario;

    @FXML
    private ImageView ImgAdminUsuarioVerde;

    @FXML
    private ImageView ImgAdminCorreo;
    
    @FXML
    private ImageView ImgAdminCorreoVerde;

    @FXML
    private ImageView ImgAdminContraseña;

    @FXML
    private ImageView ImgAdminContraseñaVerde;

    @FXML
    private ImageView ImgAdminContraseñaGU;
    
    @FXML
    private ImageView ImgAdminContraseñaVerdeGU;

    @FXML
    private ImageView ImgAdminCorreoGU;

    @FXML
    private ImageView ImgAdminCorreoVerdeGU;
    
    @FXML
    private ImageView ImgAdminUsuarioGU;
    
    @FXML
    private ImageView ImgAdminUsuarioVerdeGU;
    
    
    @FXML
    private Label lblBienvenidoPerfil;

    @FXML
    private Label lblNomUsuario;

    @FXML
    private AnchorPane pFondoMenu;

    @FXML
    private Pane pPerfil;

    @FXML
    private Pane pPerfil1;

    @FXML
    private Pane pProfilePics;

    @FXML
    private TextField txtCambioCorreo;

    @FXML
    private TextField txtCambioPassword;

    @FXML
    private TextField txtCambioUsuario;

    @FXML private Pane pPanelColores;		boolean abierto;  int color;
    
	@FXML private Label lblColorTema;
	
    @FXML
    private Pane pUsuarios;
    
    @FXML
    private Pane pContenido;
	
	int numFPerfil;
	
    LogInController controlador;
    Image fPerfil, fPerfil1;
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
		pUsuarios.setVisible(false);
		pContenido.setVisible(false);
		pProfilePics.setVisible(false);
		//pAjustes.setVisible(false);
		//pPanelColores.setVisible(false); abierto = false;  

		try { //PONE EL COLOR DE PANEL QUE QUIERA EL USUARIO
			color=c1.consultaNum("AJUSTES", "CFONDO", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			//cambiaColorFondo();
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
		//initPanelesIndiv();
		
		
		//VOLUMEN:
		int vol = 0;
		try {
			vol = c1.consultaNum("AJUSTES", "VOLUMEN", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()) );
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//barraVolAjustes.setValue(vol);
		//lblVolAjustes.setText(Integer.toString((int)(Math.floor(barraVolAjustes.getValue()))));


	}

    @FXML
    void clickBPerfil(MouseEvent event) {
    	pPerfil1.setVisible(true);
    	pUsuarios.setVisible(false);
    	pProfilePics.setVisible(false);
    	pContenido.setVisible(false);
    }
    
    
    @FXML
    void clickBEditarFoto(MouseEvent event) {
    	
    		if(pProfilePics.isVisible()==false) {
    			pProfilePics.setVisible(true);
    		}
    		else {
    			pProfilePics.setVisible(false);
    		}


    	
    }

    @FXML
    void clickBEliminarFoto(MouseEvent event) {
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

    @FXML
    void clickBVolver(MouseEvent event) throws IOException {
    	 
    		closeWindows();
    		controlador.pInicio.setVisible(false);
    		controlador.pRegistro.setVisible(false);


    	
    }

    @FXML
    void clickFoto(MouseEvent event) {

    	//CAMBIAR FOTO PERFIL
    	
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
    
    @FXML
    void clickBGuardarCambios(MouseEvent event) {

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

		if(!txtCambioPassword.getText().equals("")) { //CAMBIA CONTRASE�A
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

	   @FXML
	    void clickUsuario(MouseEvent event) {

		   pPerfil1.setVisible(false);
		   pUsuarios.setVisible(true);
		   pProfilePics.setVisible(false);
		   pContenido.setVisible(false);
		   
	    }
	

	    @FXML
	    void clickContenido(MouseEvent event) {
	    	pContenido.setVisible(true);
	    	pPerfil1.setVisible(false);
	    	pUsuarios.setVisible(false);
	    	pProfilePics.setVisible(false);
	    	
	    }
	
	    @FXML
	    void clickAjustes(MouseEvent event) {
	    	
	    	
	    	
	    }
	    
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
	    
	  
    
}
