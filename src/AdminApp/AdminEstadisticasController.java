package AdminApp;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

public class AdminEstadisticasController {

	@FXML private AnchorPane pRootEstadisticas;
	@FXML private Pane pEstadisticas;
	protected AdminMenuController menu;

	
	


	public Pane getPane() {
		return pEstadisticas;
	}

	public void setMenuController(AdminMenuController menu) {
		this.menu = menu;
	}

}
