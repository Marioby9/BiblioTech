package application;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import utilidades.Conexion;
import utilidades.Usuario;
import utilidades.Correo;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class LogInController{

	//PANELES
	@FXML Pane pInicio, pFondoIzq, pFondoDer, pIncioSesion, pRegistro;

	//LABELS
	@FXML private Label lblshowPassReg, lblShowConfPassReg, lblshowPassLogin, lblErrorInicio;

	//BOTONES O ELEMENTOS QUE HACEN DE BOTONES
	@FXML private ImageView bVolverIni, bVolverIni1;
	@FXML private Label bIsesion, bRegistrar;
	@FXML private Button bISesionDef, bRegistrarDef, btnCompRegis, backToLogIn, backToRegis;


	//IN.SESION
	@FXML private TextField txtUsuISes;
	@FXML private PasswordField txtPassISes;

	//REGISTRO
	@FXML private TextField txtUsuReg, txtCorreoReg;
	@FXML private PasswordField txtPassRegis, txtConfPassRegis;


	//FOTOS
	@FXML private ImageView imgShowPassReg, imgShowPassLogin, bApagar;

	//VARIABLES
	private boolean cambiarLado=false;

	//BBDD
	Conexion c1;


	public void initialize() { //FUNCION INICIAL (SE EJECUTA ANTES QUE EL CONSTRUCTOR)
		c1 = new Conexion();
		if(!Main.existeAdmin) {creaAdmin();}
		initComponents();
		

	}

	public void initComponents() { //INICIALIZAR COMPONENTES 
		pInicio.setVisible(true);
		lblshowPassReg.setVisible(false);
		lblShowConfPassReg.setVisible(false);
		lblshowPassLogin.setVisible(false);
		
	}

	@FXML
	void clickbApagar(MouseEvent event) {
		Stage stage = (Stage) this.bApagar.getScene().getWindow();
		try {
			Conexion.cerrar();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stage.close();

	}

	@FXML
	void ClickbIni(MouseEvent event) { //PASAR A INICIOSESION DESDE VENTANA INCIAL 
		pInicio.setVisible(false);
		pFondoIzq.setVisible(false);
		pRegistro.setVisible(false);
		
	}

	

	@FXML
	void ClickbReg(MouseEvent event) { //PASAR A REGISTRAR DESDE VENTANA INCIAL
		pInicio.setVisible(false);
		pFondoDer.setVisible(false);
		pIncioSesion.setVisible(false);
		pFondoIzq.setVisible(true);
	}

	@FXML
	void clickBackLogin(ActionEvent event) { //PASAR DE REGISTRO A INICIO SESION

		pFondoIzq.setVisible(false);
		pRegistro.setVisible(false);
		pFondoDer.setVisible(true);
		pIncioSesion.setVisible(true);
		btnCompRegis.setText(""); 
		vaciarTxtField();
	}

	@FXML
	void clickbVolverIni(MouseEvent event) { //VOLVER AL INICIO TOTAL DESDE INICIO SESION O REGISTRAR
		pInicio.setVisible(true);
		pIncioSesion.setVisible(true);
		pRegistro.setVisible(true);
		pFondoDer.setVisible(true);
		pFondoIzq.setVisible(true);
		btnCompRegis.setText("");
		vaciarTxtField();
	}

	@FXML
	void clickBackRegis(ActionEvent event) { //PASAR DE INICIO SESION A REGISTRO
		pFondoDer.setVisible(false);
		pIncioSesion.setVisible(false);
		pFondoIzq.setVisible(true);
		pRegistro.setVisible(true);
		vaciarTxtField();
	}

	@FXML
	void ClickbIniDef(ActionEvent event) {
		String nickname = txtUsuISes.getText();
		String password = txtPassISes.getText();
		String correo = "";
		try {
			if(c1.compruebaLogIn(nickname, password)) {
				lblErrorInicio.setVisible(false);
				try {
					correo = c1.consultaStr("USUARIO", "CORREO", "NICKNAME = '"+nickname+"'");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}

				Usuario u1 = new Usuario(nickname, password, correo);
				try {
					u1.setID_Usuario(c1.consultaNum("USUARIO", "ID", "NICKNAME = '"+nickname+"'"));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}				
				Usuario.setUsuario(u1); 

				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("MenuView.fxml"));
					Parent root = loader.load();
					MenuController controlador = loader.getController();

					Scene scene = new Scene(root);
					Stage venMenu = new Stage();
					venMenu.setScene(scene);
					venMenu.setResizable(false);
					venMenu.setTitle("BiblioTech");
					//venMenu.initStyle(StageStyle.UNDECORATED);//QUITAR BARRA

					venMenu.show();

					//    		File file = new File("src/icons/logoNegroCF.png");		//CAMBIAR ICONO DEL PROGRAMA. FORMA ANTIGUA
					//			venMenu.getIcons().add(new Image(file.toURI().toString())); 

					Image icon = new Image(getClass().getResourceAsStream("/icons/logoNegroCF.png"));//CAMBIAR ICONO DEL PROGRAMA
					venMenu.getIcons().add(icon);//FUNCIONA PERFECTAMENTE

					venMenu.setOnCloseRequest(e->controlador.closeWindows());

					Stage myStage = (Stage) this.bISesionDef.getScene().getWindow();
					myStage.close();

				} catch(IOException ex) {
					Logger.getLogger(LogInController.class.getName()).log(Level.SEVERE,null,ex);
				}
			}

			else {
				lblErrorInicio.setText("USUARIO O CONTRASEÑA INCORRECTO");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@FXML
	void clickBRegisDef(ActionEvent event) {  //COMPRUEBA QUE CORREO VALIDO // PASSWORD Y CONFIRMPASSWORD COINCIDAN
		String pass1 = txtPassRegis.getText();
		String pass2 = txtConfPassRegis.getText();
		String correo = txtCorreoReg.getText();
		Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+"[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
		Matcher mather = pattern.matcher(correo);

		btnCompRegis.setVisible(true);
		if(mather.find()) {
			if(!pass1.equals(pass2) || pass1.equals("")) {
				btnCompRegis.setStyle("-fx-text-fill: RED; -fx-background-color: White "); //PARA CAMBIAR EL ESTILO A UN BOTON, HAY QUE HACERELO EN LA MISMA LINEA
				btnCompRegis.setUnderline(false);
				btnCompRegis.setText("ERROR. Contraseña no válida.");
				txtPassRegis.setText("");
				txtConfPassRegis.setText("");
				cambiarLado = false;
			}
			else if(txtUsuReg.getText().equals("")) { //COMPRUEBA QUE NO DEJE EL CAMPO USUARIO VACIO
				btnCompRegis.setStyle("-fx-text-fill: RED; -fx-background-color: White ");
				btnCompRegis.setUnderline(false);
				btnCompRegis.setText("ERROR. Nombre usuario no válido.");
				txtUsuReg.setText("");
				cambiarLado = false;
			}
			else {
				
				//DAR ALTA USUARIO EN BBDD (FUNCIONA PERFECTAMENTE)
				String nom = txtUsuReg.getText();
				Usuario u1 = new Usuario(nom, pass1, correo);
				try {
					if(c1.registraUsuario(u1)) { 
						System.out.println(Correo.enviarMailConf(correo, nom, pass1)); //MUESTRA SI HA PODIDO ENVIAR EL CORREO O NO
						System.out.println("Usuario registrado correctamente");
						btnCompRegis.setVisible(true);
						btnCompRegis.setUnderline(true);
						btnCompRegis.setStyle("-fx-text-fill: GREEN; -fx-background-color: White "); //
						

						btnCompRegis.setText("¡Cuenta creada con éxito!. Ir a iniciar sesión");
						cambiarLado = true;
					}
					
				} catch (SQLException e) {
					System.out.println("No se pudo registrar el usuario");
					btnCompRegis.setVisible(true);
					btnCompRegis.setUnderline(false);
					btnCompRegis.setStyle("-fx-text-fill: RED; -fx-background-color: White "); //
					
					if(e instanceof SQLIntegrityConstraintViolationException) {
						btnCompRegis.setText("El usuario ya existe");
					}
					
					cambiarLado = false;
					
				}

			}
		}
		else{
			btnCompRegis.setStyle("-fx-text-fill: RED; -fx-background-color: White ");
			btnCompRegis.setUnderline(false);
			btnCompRegis.setText("ERROR. Formato de correo no válido.");
			txtCorreoReg.setText("");
			cambiarLado = false;
		}




	}

	@FXML
	void clickbRegExito(ActionEvent event) { //ESTE BOTON SOLO FUNCIONA CUANDO SE REGISTRA CON ÉXITO
		if(cambiarLado==true) {
			pRegistro.setVisible(false);
			pIncioSesion.setVisible(true);
			pFondoDer.setVisible(true);
			btnCompRegis.setStyle("-fx-text-fill: RED; -fx-background-color: White ");
			btnCompRegis.setUnderline(false);
			vaciarTxtField();

			btnCompRegis.setText(""); //PARA VACIAR EL TEXTO DEL BOTON (OPCIONAL)

		}	


	}

	//MOSTRAR Y NO MOSTRAR CONTRASEÑAS

	@FXML
	void showPassReg(MouseEvent event) {
		lblshowPassReg.setText(txtPassRegis.getText());
		lblShowConfPassReg.setText(txtConfPassRegis.getText());
		lblshowPassReg.setVisible(true);
		lblShowConfPassReg.setVisible(true);

	}

	@FXML
	void stopShowPassReg(MouseEvent event) {
		lblshowPassReg.setVisible(false);
		lblShowConfPassReg.setVisible(false);
	}

	@FXML
	void showPassLogin(MouseEvent event) {
		lblshowPassLogin.setText(txtPassISes.getText());
		lblshowPassLogin.setVisible(true);
	}

	@FXML
	void stopShowPassLogin(MouseEvent event) {
		lblshowPassLogin.setVisible(false);
	}



	//FUNCIONES VARIAS
	
	private void vaciarTxtField() {//VACIA TODOS LOS CAMPOS . 
		txtUsuReg.setText("");
		txtCorreoReg.setText("");
		txtPassRegis.setText(""); 
		txtConfPassRegis.setText("");
		txtCorreoReg.setText("");
		txtUsuISes.setText("");
		txtPassISes.setText("");
		lblErrorInicio.setText("");
		cambiarLado = false; //REINICIAMOS BOOLEAN PARA QUE EL btnCompRegis NO FUNCIONE
	}

	private void creaAdmin() { //COMPRUEBA NADA MÁS INICIAR QUE HAYA UN USUARIO ADMIN CREADO, SINO LO CREA. USUARIO ADMIN NO SE PUEDE BORRAR
		try {
			int num = c1.consultaNum("USUARIO", "ID", "UPPER(NICKNAME) = 'ADMIN'");
			if(num==0) {
				Usuario u = new Usuario("ADMIN", "ADMIN","admin@gmail.com" );
				c1.registraUsuario(u);
				System.out.println("ADMIN CREADO");
				Main.existeAdmin = true;

			}
			else {
				System.out.println("EL ADMIN EXISTE");
				Main.existeAdmin = true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	









}
