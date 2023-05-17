package AdminApp;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utilidades.Conexion;

public class AdminEstadisticasController {

	@FXML private AnchorPane pRootEstadisticas;
	@FXML private Pane pEstadisticas, pCarga;
	protected AdminMenuController menu;
	@FXML private Label totalUsu,  totalAutores, usoMedio, ultRegistros,cuentasBorradas, totalElementos;
	@FXML private BarChart<String, Integer> grafico;

	protected void actualizaEstadisticas() {
		try {
			totalUsu.setText(Integer.toString(Conexion.usuTotales()));
			totalAutores.setText(Integer.toString(Conexion.autoresTotales()));
			totalElementos.setText(Integer.toString(Conexion.totalElementos()));
			cuentasBorradas.setText(Integer.toString(Conexion.usuBorrados()));
			ultRegistros.setText(Integer.toString(Conexion.ultimosUsu()));
			usoMedio.setText(Conexion.usoMedio());
			pCarga.setVisible(true);


		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML void verGrafLibros(MouseEvent event) {
		try {
			pCarga.setVisible(false);
			grafico.getData().clear();
			ObservableList<BarChart.Data> datos = Conexion.graficaLibros();
			XYChart.Series<String, Integer> series = new XYChart.Series<>();
			for (BarChart.Data<String, Integer> dato : datos) {
				series.getData().add(dato);
			}	
			grafico.getData().add(series);
			

		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML void verGrafJuegos(MouseEvent event) {
		try {
			pCarga.setVisible(false);
			grafico.getData().clear();
			ObservableList<BarChart.Data> datos = Conexion.graficaJuegos();
			XYChart.Series<String, Integer> series = new XYChart.Series<>();
			for (BarChart.Data<String, Integer> dato : datos) {
				series.getData().add(dato);
			}	
			grafico.getData().add(series);
			

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
