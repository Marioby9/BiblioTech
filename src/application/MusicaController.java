package application;

import java.io.File;
import elementos.Cancion;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import utilidades.Ficheros;
import utilidades.Usuario;


public class MusicaController {

	@FXML private AnchorPane pRootMusica;
	@FXML private Pane pMusica;

	//MUSICA
	@FXML protected Pane cabeceraMus, pListaMus, pFondoAvisoMus, pAvisoMus;
	@FXML protected Label lblTitListaMus, lblTiempoCanc, lblNumCanciones, lblTitReproductor ;
	@FXML protected ProgressBar barraMusica;
	@FXML private ImageView bPlayMusica, btnBucle, imgIconoLista, btnBorraMusica;
	protected Image imgPlayMus, imgPauseMus, imgBucleBlanco, imgBucleVerde, iconoLista;
	private Timeline timeline;
	private Duration duracionTotal;

	//BOTONES PMUSICA
	@FXML private ImageView bMusElectronica, bMusFlamenco, bMusPop, bMusReggaeton, bMusRock;
	@FXML private TableView<Cancion> tablaMusica;
	@FXML private TableColumn<Cancion, String> tituloCanc;
	@FXML private TableColumn<Cancion, String> artistaCanc;
	@FXML private TableColumn<Cancion, String> generoCanc;
	@FXML protected Slider barraVolReproductor;

	protected MediaPlayer reproductor;
	protected Cancion cancActual = null;
	private String categMus = "";
	private String estiloCabMus = "-fx-border-width: 4px; -fx-border-color: BLACK; -fx-border-radius: 20px; -fx-background-radius: 25px;";
	protected boolean activo = false;
	protected boolean inBucle = false;

	private MenuController menu;
	protected Usuario u1;



	public void initialize() {
		u1 = Usuario.getUsuario();
		initComponents();
	}

	public void initComponents() {
		pMusica.setVisible(true);
		pListaMus.setVisible(false);

	}


	//MUSICA:
	@FXML void clickListaMusica(MouseEvent event) {
		if(event.getSource()==bMusReggaeton) {
			categMus = "REGGAETON";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #ff748d, #ff7daf);"+estiloCabMus);
			iconoLista = new Image(getClass().getResourceAsStream("/icons/reggaeton.png"));

		}
		else if(event.getSource()==bMusPop) {
			categMus = "POP";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #00c0fa, #015eea);"+estiloCabMus);
			iconoLista = new Image(getClass().getResourceAsStream("/icons/pop.png"));
		}
		else if(event.getSource()==bMusElectronica) {
			categMus = "ELECTRONICA";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #facc22, #f83600);"+estiloCabMus);
			iconoLista = new Image(getClass().getResourceAsStream("/icons/electronica.png"));
		}
		else if(event.getSource()==bMusFlamenco) {
			categMus = "FLAMENCO";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom, #e1afcc, #7530e3);"+estiloCabMus);
			iconoLista = new Image(getClass().getResourceAsStream("/icons/flamenco.png"));
		}
		else if(event.getSource()==bMusRock) {
			categMus = "ROCK";
			cabeceraMus.setStyle("-fx-background-color: linear-gradient(to bottom,#fba981, #5c2c15);"+estiloCabMus);
			iconoLista = new Image(getClass().getResourceAsStream("/icons/rock.png"));
		}
		imgIconoLista.setImage(iconoLista);
		lblTitListaMus.setText(categMus);
		tablaMusica.setVisible(true);
		rellenaTablaMus(categMus);
		pListaMus.setVisible(true);




	}


	@FXML void clickBackMusica(MouseEvent event) {
		pListaMus.setVisible(false);
		pMusica.setVisible(true);
	}   


	@FXML void arrastraVolRepro(MouseEvent event) {
		if(activo) {
			double vol = Math.floor(barraVolReproductor.getValue())/100;
			reproductor.setVolume(vol);
		}
	}



	@FXML void clickPlayLista(MouseEvent event) {
		if(tablaMusica.getItems().size() != 0) {
			tablaMusica.getSelectionModel().select(0);
			cancActual = tablaMusica.getItems().get(0);
			lblTitReproductor.setText(cancActual.getArtista()+ "  |  "+cancActual.getNombre());
			reproductor.stop();

			String rutaArchivo = cancActual.getRuta();
			File archivo = new File(rutaArchivo);
			Media media = new Media(archivo.toURI().toString());
			reproductor = new MediaPlayer(media);

			bPlayMusica.setImage(imgPlayMus);
			reproductor.setVolume(menu.vol);

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
			activo = true;
			inBucle = false;
			btnBucle.setImage(imgBucleBlanco);


			reproductor.setOnEndOfMedia(null);
			bPlayMusica.setImage(imgPauseMus);
			reproductor.play();
			actualizaBarraMusica(duracionTotal);


		}

	}


	//MEDIAPLAYER
	@FXML void clickBPlayMusica(MouseEvent event) {

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
	@FXML void clickPreviousSong(MouseEvent event) {
		int numCanAct = tablaMusica.getSelectionModel().getSelectedIndex();

		int numPrevCan = numCanAct - 1;


		if(numPrevCan >= 0) {
			tablaMusica.getSelectionModel().select(numPrevCan);
			cancActual = tablaMusica.getItems().get(numPrevCan);
			lblTitReproductor.setText(cancActual.getArtista()+ "  |  "+cancActual.getNombre());
			reproductor.stop();

			String rutaArchivo = cancActual.getRuta();
			File archivo = new File(rutaArchivo);
			Media media = new Media(archivo.toURI().toString());
			reproductor = new MediaPlayer(media);

			bPlayMusica.setImage(imgPlayMus);
			reproductor.setVolume(menu.vol);

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
			activo = false;
			inBucle = false;
			btnBucle.setImage(imgBucleBlanco);
			reproductor.setOnEndOfMedia(null);
		}



	} 
	@FXML void clickNextSong(MouseEvent event) {
		int numCanAct = tablaMusica.getSelectionModel().getSelectedIndex();
		int canTotales = tablaMusica.getItems().size();
		int numNextCan = numCanAct + 1;


		if(numNextCan < canTotales) {
			tablaMusica.getSelectionModel().select(numNextCan);
			cancActual = tablaMusica.getItems().get(numNextCan);
			lblTitReproductor.setText(cancActual.getArtista()+ "  |  "+cancActual.getNombre());
			reproductor.stop();

			String rutaArchivo = cancActual.getRuta();
			File archivo = new File(rutaArchivo);
			Media media = new Media(archivo.toURI().toString());
			reproductor = new MediaPlayer(media);

			bPlayMusica.setImage(imgPlayMus);
			reproductor.setVolume(menu.vol);

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
			activo = false;
			inBucle = false;
			btnBucle.setImage(imgBucleBlanco);
			reproductor.setOnEndOfMedia(null);


		}

	} 
	@FXML void clickBucleSong(MouseEvent event) {

		if(!inBucle) {
			btnBucle.setImage(imgBucleVerde);
			inBucle = true;
			reproductor.setOnEndOfMedia(() -> {
				reproductor.seek(Duration.ZERO);

				barraMusica.setProgress(0);
				reproductor.play();


			});

		}
		else {
			btnBucle.setImage(imgBucleBlanco);
			inBucle = false;
			reproductor.setOnEndOfMedia(null);
		}

	} 

	
	@FXML void clickBorraMusica(MouseEvent event) {
		pFondoAvisoMus.setVisible(true);
		pAvisoMus.setVisible(true);
	}
	

	/*----------------------ARREGLAR---------------------*/
	
	@FXML void aceptaBorraMusica(MouseEvent event){ /*DA ERROR PORQUE NO SE PUEDE MOVER MIENTRAS ESTA SIENDO UTLIZADO POR OTRO PROCESO*/ 

		if(cancActual!=null) {
			try {
				
				
			
				String carpeta = menu.carpCanciones;
				File cancion = new File(cancActual.getRuta());
				
				if(Ficheros.mueveCancion(cancion, carpeta)) {
					System.out.println("Cancion movida a papelera correctamente");
					pFondoAvisoMus.setVisible(false);
					pAvisoMus.setVisible(false);
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	@FXML void rechazaBorraMusica(MouseEvent event){ /*DA ERROR PORQUE NO SE PUEDE MOVER MIENTRAS ESTA SIENDO UTLIZADO POR OTRO PROCESO*/

		pFondoAvisoMus.setVisible(false);
		pAvisoMus.setVisible(false);
	}
	
	
	




	private void rellenaTablaMus(String categoria) {

		if(menu.carpCanciones != null) {
			try {
				ObservableList<Cancion> listaMusica;
				listaMusica = Ficheros.leeCarpetaMus(menu.carpCanciones, categMus);
				lblNumCanciones.setText(listaMusica.size()+" Canciones");

				//CUANDO CAMBIAMOS DE CATEGORIA, PONEMOS EL TITULO Y LA PORTADA DEL PRIMER LIBRO DE LA LISTA
				if(listaMusica.size()!=0 && !activo) { 
					
					btnBorraMusica.setVisible(true);
					cancActual = listaMusica.get(0);
					barraMusica.setProgress(0);
					lblTitReproductor.setText(cancActual.getArtista()+ "  |  "+cancActual.getNombre());

					String rutaArchivo = cancActual.getRuta();
					File archivo = new File(rutaArchivo);
					Media media = new Media(archivo.toURI().toString());
					reproductor = new MediaPlayer(media);
					reproductor.setOnReady(() -> {
						duracionTotal = reproductor.getTotalDuration();
						reproductor.setVolume(menu.vol);
					});
					tablaMusica.getSelectionModel().select(0);

				}
				else if(listaMusica.size()==0 && !activo) { //SI NO HAY ELEMENTOS EN LA LISTA:
					tablaMusica.setVisible(false);
					btnBorraMusica.setVisible(false);
					cancActual = null;
					lblTitReproductor.setText("ARTISTA  |  TITULO CANCION  ");
					
					if(timeline!=null) {
						timeline.stop();
						reproductor.dispose();
						timeline = null;
					}
			
					barraMusica.setProgress(0);
				}
				else if(listaMusica.size()==0 && activo) { //SI NO HAY ELEMENTOS EN LA LISTA:
					tablaMusica.setVisible(false);
					btnBorraMusica.setVisible(true);
					
					
				}


				tablaMusica.setItems(listaMusica); //RELLENAR TABLA CON LOS LIBROS DE CADA CATEGORIA

				tituloCanc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
				artistaCanc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArtista()));
				generoCanc.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGenero()));

			} catch (Exception ex) {
				ex.printStackTrace();
			}
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
			reproductor.setVolume(menu.vol);

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
			activo = false;
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


	@FXML void refreshTablaMus(MouseEvent event) { //CLICK ACTUALIZAR TABLA LIBROS
		rellenaTablaMus(categMus);
	}


	public Pane getPane() {
		return pMusica;
	}

	public void setMenuController(MenuController menu) {
		this.menu = menu;
	}


}
