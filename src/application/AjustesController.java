package application;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import utilidades.Conexion;
import utilidades.Correo;
import utilidades.Ficheros;
import utilidades.Usuario;

public class AjustesController {

	@FXML private AnchorPane pRootAjustes;
	@FXML private Pane pAjustes;
	@FXML private Label lblColorTema;
	@FXML private ImageView bEditarColor, bWeb, bGithub, bTwitter, bInstagram, bYoutube;
	@FXML protected Pane pPanelColores, pAvisoMus, pFondoAviso, pAvisoCuenta;		
	@FXML protected Slider barraVolAjustes, barraVolReproductor;
	@FXML protected Label lblVolAjustes;
	@FXML private Label lblColorAzul, lblColorMorado, lblColorNaranja, lblColorRojo, lblColorVerde;
	protected boolean abierto;  
	protected int color;

	private MenuController menu;
	private Usuario u1;

	public void initialize() {
		u1 = Usuario.getUsuario();
		initComponents();
	}

	public void initComponents() {
		pAjustes.setVisible(true);
		pPanelColores.setVisible(false); abierto = false;  
		try { //PONE EL COLOR DE PANEL QUE QUIERA EL USUARIO
			color=Conexion.consultaNum("AJUSTES", "CFONDO", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
		} catch (SQLException e1) {
			e1.printStackTrace();
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
			Conexion.updateTabla("AJUSTES", "CFONDO", color, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			

			
			

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML void clickBEliminarCuenta(MouseEvent event) {
		pFondoAviso.setVisible(true);
		pAvisoCuenta.setVisible(true);

	}

	@FXML void clickRechazaAvisoCuenta(MouseEvent event) {
		pFondoAviso.setVisible(false);
		pAvisoCuenta.setVisible(false);

	}

	@FXML void clickAceptaAvisoCuenta(MouseEvent event) {

		try {
			if(Conexion.eliminarUsuario(Usuario.getUsuario())) {
				try {
					menu.clickBVolver(event);
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






	public void cambiaColorFondo(){
		if(color==0) { menu.imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu.png"))); 				lblColorTema.setStyle("-fx-background-color: #08426c; -fx-border-color: white; -fx-border-width: 3px;"); } 	
		else if(color==1) {  menu.imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu1.png")));  		lblColorTema.setStyle("-fx-background-color: #fb7b14; -fx-border-color: white; -fx-border-width: 3px;");}
		else if(color==2) { menu.imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu3.png"))); 		lblColorTema.setStyle("-fx-background-color: #9fb65a; -fx-border-color: white; -fx-border-width: 3px;");	}
		else if(color==3) {  menu.imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu4.png"))); 		lblColorTema.setStyle("-fx-background-color: #c0060e; -fx-border-color: white; -fx-border-width: 3px;"); }
		else if(color==4) {  menu.imgFondoMenu.setImage(new Image(getClass().getResourceAsStream("/backgrounds/panelMenu2.png"))); 		lblColorTema.setStyle("-fx-background-color: #954c7b; -fx-border-color: white; -fx-border-width: 3px;"); }

	}





	public Pane getPane() {
		return pAjustes;
	}

	public void setMenuController(MenuController menu) {
		this.menu = menu;
	}

	


}