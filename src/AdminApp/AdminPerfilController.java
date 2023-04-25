package AdminApp;

import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utilidades.Conexion;
import utilidades.Usuario;

public class AdminPerfilController {

	@FXML private AnchorPane pRootPerfil;
	@FXML private Pane pPerfil;
	@FXML private ImageView bEditarFoto, bEliminarFoto;		
	@FXML private Label  lblBienvenidoPerfil;
	@FXML private Pane pProfilePics;
	@FXML private ImageView fotoPerfil;
	protected Image fPerfil, fPerfil1;
	protected int numFPerfil;
	@FXML private ImageView foto1, foto2, foto3, foto4, foto5, foto6, foto7,foto8, foto9;
	@FXML private TextField txtCambioCorreo, txtCambioPassword, txtCambioUsuario;

	private Usuario u1;
	
	//REFERENCIAS PARA FUNCIONES DE OTROS CONTROLADORES:
	private AdminMenuController menu;
	
	public void initialize() {
		u1 = Usuario.getUsuario();
		initComponents();
	}
	
	
	public void initComponents() {
		pPerfil.setVisible(true);
		pProfilePics.setVisible(false);
		fPerfil = new Image(fotoPerfil.getImage().getUrl());
	
		try {//PONE FPERFIL QUE TENGA EL USUARIO EN SU BBDD
			numFPerfil = Conexion.consultaNum("AJUSTES", "FPERFIL", "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		//INICIAR COMPONENTES PARA CADA USUARIO
		lblBienvenidoPerfil.setText("Bienvenido, "+ u1.getNickname());
		txtCambioUsuario.setText("");
		txtCambioCorreo.setText("");
		txtCambioPassword.setText("");
		pProfilePics.setVisible(false);
		
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
			Conexion.updateTabla("AJUSTES", "FPERFIL", numFPerfil, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
			cambiaFPerfil();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	@FXML void clickBGuardarCambios(MouseEvent event) { //CAMBIA FOTO PERFIL, NOMBRE, CORREO, CONTRASEÑA

		menu.fotoPerfil1.setImage(fotoPerfil.getImage());

		if(!txtCambioUsuario.getText().equals("")) { //CAMBIA NICKNAME
			String nuevoNom = txtCambioUsuario.getText();
			try {
				Conexion.updateTabla("USUARIO", "NICKNAME", nuevoNom, "ID = "+Integer.toString(u1.getID_Usuario()));
				u1.setNickname(nuevoNom);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			menu.lblNomUsuario.setText(nuevoNom);
			lblBienvenidoPerfil.setText("Bienvenido, "+nuevoNom);
		}

		if(!txtCambioPassword.getText().equals("")) { //CAMBIA CONTRASEÑA
			String nuevaCon = txtCambioPassword.getText();
			try {
				Conexion.updateTabla("USUARIO", "CONTRASEÑA", nuevaCon, "ID = "+Integer.toString(u1.getID_Usuario()));
				u1.setContrasena(nuevaCon);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		if(!txtCambioCorreo.getText().equals("")) { //CAMBIA CORREO
			String nuevoCorreo = txtCambioCorreo.getText();
			try {
				Conexion.updateTabla("USUARIO", "CORREO", nuevoCorreo, "ID = "+Integer.toString(u1.getID_Usuario()));
				u1.setCorreo(nuevoCorreo);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		//CAMBIA FPERFIL
		try {
			Conexion.updateTabla("AJUSTES", "FPERFIL", numFPerfil, "ID_USUARIO = "+Integer.toString(u1.getID_Usuario()));
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
	

	public void cambiaFPerfil(){

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

		menu.fotoPerfil1.setImage(fotoPerfil.getImage());
	}


	public Pane getPane() {
		return pPerfil;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}

	
	
}
