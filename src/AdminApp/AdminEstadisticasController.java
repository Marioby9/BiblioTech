package AdminApp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utilidades.Conexion;

public class AdminEstadisticasController {

	@FXML private AnchorPane pRootEstadisticas;
	@FXML private Pane pEstadisticas;
	protected AdminMenuController menu;
	@FXML private Label totalUsu,  totalAutores, totalArtistas, ultRegistros,cuentasBorradas, mediaUsoApp;
	
	
	protected void actualizaEstadisticas() {
		try {
		totalUsu.setText(Integer.toString(Conexion.usuTotales()));
		totalAutores.setText(Integer.toString(Conexion.autoresTotales()));
		
		
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	

	public Pane getPane() {
		return pEstadisticas;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}

}
