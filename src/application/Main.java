
package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import utilidades.Conexion;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;


public class Main extends Application {
	
	public static boolean existeAdmin = false; //ASI LA FUNCION creaADMIN SOLO SE EJECUTA UNA VEZ POR PROGRAMA. 
											  //BASICAMENTE PARA QUE NO HAGA UNA QUERYCADA VEZ QUE SE VUELVA AL LOGIN.
	@Override
	public void start(Stage ventana) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("/UserFXML/LogInView.fxml"));
			Scene scene = new Scene(root);

			scene.getStylesheets().add(getClass().getResource("/UserFXML/application.css").toExternalForm());
			ventana.setResizable(false);
			
			//ventana.initStyle(StageStyle.UNDECORATED);//QUITAR BARRA
			
			ventana.setScene(scene);
			ventana.setTitle("BiblioTech");

			//FORMA ANTIGUA
			//File file = new File("src/icons/logoNegroCF.png");//CAMBIAR ICONO DEL PROGRAMA  
			//ventana.getIcons().add(new Image(file.toURI().toString())); //FUNCIONA PERFECTAMENTE

			Image icon = new Image(getClass().getResourceAsStream("/icons/logoNegroCF.png"));
			ventana.getIcons().add(icon);

			ventana.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		Conexion.conectar();

		launch(args);

	}
}
