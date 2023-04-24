package application;

import java.io.File;
import java.sql.SQLException;

import elementos.Juego;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import utilidades.Conexion;
import utilidades.Usuario;

public class JuegosController {

	@FXML private AnchorPane pRootJuegos;
	//PANEL JUEGOS
	@FXML protected Pane pJuegoIndiv, pListaJuegos;
	@FXML private Pane pJuegos,  bGuardaEditaJuego, bGuardaAgregaJuego, pAvisoJue, pFondoAvisoJue;
	//BOTONES PJUEGOS
	@FXML private ImageView bJueAccion, bJueDeportes, bJueFavoritos, bJueShooter, bJueTerror, btnBackPaneJuegos, bFavJue, addGame, portadaListaJue, portadaJuego;
	@FXML private TableView<Juego> tablaJuegos;
	@FXML private TableColumn<Juego, String> tituloJue;
	@FXML private TableColumn<Juego, String> plataformaJue;
	@FXML private TableColumn<Juego, Integer> fechaJue;
	@FXML private TableColumn<Juego, Integer> horasJue;
	private Juego jueActual = null;
	private String categJue = "";
	private String accJuego = "";
	private String rutaPortadaJue = "";
	private boolean cambiaPortadaJue = false; //CUANDO QUIERAS SUBIR UN ARCHIVO, SE PONDRA TRUE


	//PANELES LISTA JUEGOS
	@FXML private Label lblTituloJuegoInd, lblNHorasJuegoInd, lblPlataformaJuegoInd, lblGeneroJuegoInd, lblYearJuegoInd, lblResumenJuegoInd, lblCompaniaJuegoInd, lblTituloListaJue, lblErrorJue;
	@FXML private TextArea txtAreaResumenJuego;
	@FXML private TextField txtFieldTitJuego, txtFieldPlataformaJuego, txtFieldGeneroJuego, txtFieldFechaJuego, txtFieldHorJuego, txtFieldCompaniaJuegoInd;


	//REFERENCIAS CONTROLADORES
	protected MenuController menu;

	private Usuario u1;



	public void initialize() {
		u1 = Usuario.getUsuario();
		initComponents();
	}

	public void initComponents() {
		pJuegos.setVisible(true);
		pJuegoIndiv.setVisible(false);
		pListaJuegos.setVisible(false);
		initPanelesIndiv();

	}


	//JUEGOS:
	@FXML void clickListaJuegos(MouseEvent event) {

		pListaJuegos.setVisible(true);
		addGame.setVisible(true);

		if(event.getSource()==bJueFavoritos) {
			categJue = "FAVORITOS";
			addGame.setVisible(false);
		}
		else if(event.getSource()==bJueAccion) {
			categJue = "ACCION";
		}
		else if(event.getSource()==bJueDeportes) {
			categJue = "DEPORTES";
		}
		else if(event.getSource()==bJueTerror) {
			categJue = "TERROR";
		}
		else if(event.getSource()==bJueShooter) {
			categJue = "SHOOTER";
		}

		rellenaTablaJuegos(categJue);
	}

	@FXML void showSinglePaneJue(){
		if(jueActual!=null) {
			accJuego = "";
			pJuegoIndiv.setVisible(true);
			bGuardaAgregaJuego.setVisible(false);
			bGuardaEditaJuego.setVisible(false);
			lblErrorJue.setVisible(false);
			cambiaPortadaJue = false;
			initPanelesIndiv(); //HACE VISIBLES LOS LABELS E INVISIBLES LOS TEXTFIELD

			lblTituloJuegoInd.setText(jueActual.getTitulo());
			lblCompaniaJuegoInd.setText(jueActual.getEmpresa());
			lblGeneroJuegoInd.setText(jueActual.getGenero());
			lblPlataformaJuegoInd.setText(jueActual.getPlataforma());
			lblNHorasJuegoInd.setText(Integer.toString(jueActual.gethJugadas()));
			lblYearJuegoInd.setText(Integer.toString(jueActual.getLanzamiento()));

			String portada = jueActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
					image = new Image(getClass().getResource(portada).toString());
					portadaJuego.setImage(image);
				} catch (Exception e) { //SI SE LEVANTA LA EXCEPCION, LA RUTA ES RELATIVA
					//portadaLibro.setImage(null);
					image = new Image(portada);
					portadaJuego.setImage(image);
				}
			}
			else {
				portadaJuego.setImage(null);
			}

			//RESUMEN
			if(jueActual.getResumen()!=null) {
				lblResumenJuegoInd.setText(jueActual.getResumen());
			}
			else {
				lblResumenJuegoInd.setText("Este juego no tiene resumen");
			}

		}
	}

	@FXML void editSinglePaneJue(){

		if(jueActual!=null && jueActual.getID_Usuario()>0) { //NO PUEDES EDITAR LOS POR DEFECTO
			accJuego = "EDITAR";
			pJuegoIndiv.setVisible(true);
			bGuardaEditaJuego.setVisible(true);
			bGuardaAgregaJuego.setVisible(false);
			lblErrorJue.setVisible(false);
			cambiaPortadaJue = false;

			//LABELS INVISIBLES
			lblTituloJuegoInd.setVisible(false); lblPlataformaJuegoInd.setVisible(false); lblNHorasJuegoInd.setVisible(false); lblYearJuegoInd.setVisible(false); 
			lblCompaniaJuegoInd.setVisible(false); lblResumenJuegoInd.setVisible(false); 

			//TEXTFIELDS VISIBLES
			txtFieldTitJuego.setVisible(true); txtFieldCompaniaJuegoInd.setVisible(true); txtFieldHorJuego.setVisible(true); txtFieldFechaJuego.setVisible(true); txtAreaResumenJuego.setVisible(true);
			txtFieldPlataformaJuego.setVisible(true); 

			//PONER LOS DATOS QUE TIENEN ACTUALMENTE Y EDITAR A PARTIR DE AHI
			txtFieldTitJuego.setText(jueActual.getTitulo()); 						txtFieldCompaniaJuegoInd.setText(jueActual.getEmpresa()); 
			txtFieldHorJuego.setText(Integer.toString(jueActual.gethJugadas())); 	txtFieldFechaJuego.setText(Integer.toString(jueActual.getLanzamiento())); 
			lblGeneroJuegoInd.setText(jueActual.getGenero()); txtFieldPlataformaJuego.setText(jueActual.getPlataforma());


			String portada = jueActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				image = new Image(portada);
				portadaJuego.setImage(image);
			}
			else {
				portadaJuego.setImage(null);
			}


			//RESUMEN
			if(jueActual.getResumen()!=null) {
				txtAreaResumenJuego.setText(jueActual.getResumen());
			}
			else {
				txtAreaResumenJuego.setText(null);
			}

		}



	}


	@FXML void agregaSinglePaneJue(){
		accJuego = "AGREGAR";  //PARA QUE AL PULSAR EN LA PORTADA, SOLO SALGA EL FILECHOOSER CUANDO ESTEMOS EDITANDO O AGREGANDO UN LIBRO
		pJuegoIndiv.setVisible(true);
		bGuardaAgregaJuego.setVisible(true);
		bGuardaEditaJuego.setVisible(false);
		lblErrorJue.setVisible(false);
		cambiaPortadaJue = false;



		//LABELS INVISIBLES
		lblTituloJuegoInd.setVisible(false); lblPlataformaJuegoInd.setVisible(false); lblNHorasJuegoInd.setVisible(false); lblYearJuegoInd.setVisible(false); 
		lblCompaniaJuegoInd.setVisible(false); lblResumenJuegoInd.setVisible(false); portadaJuego.setImage(null);

		//TEXTFIELDS VISIBLES
		txtFieldTitJuego.setVisible(true); txtFieldCompaniaJuegoInd.setVisible(true); txtFieldHorJuego.setVisible(true); txtFieldFechaJuego.setVisible(true); txtAreaResumenJuego.setVisible(true);
		txtFieldPlataformaJuego.setVisible(true);

		//VACIAR TEXTFIELDS PARA QUE EL USUARIO INTRODUZCA DATOS DE 0
		txtFieldTitJuego.setText(""); 	txtAreaResumenJuego.setText(""); 	txtFieldCompaniaJuegoInd.setText(""); 
		txtFieldHorJuego.setText(""); 	txtFieldFechaJuego.setText(""); 	txtFieldPlataformaJuego.setText(""); 
		lblGeneroJuegoInd.setText(jueActual.getGenero());
	}

	@FXML void clickGuardaJuegoEditado(MouseEvent event) { 
		try {

			String titulo = txtFieldTitJuego.getText();
			String empresa = txtFieldCompaniaJuegoInd.getText();
			String plataforma = txtFieldPlataformaJuego.getText();
			String resumen = txtAreaResumenJuego.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaJuego.getText());
			int horas = Integer.parseInt(txtFieldHorJuego.getText());


			if(!titulo.equals("") && !plataforma.equals("") && lanzamiento >= 0 && horas >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN Y EMPRESA)

				//UPDATE DATOS LIBRO
				jueActual.setTitulo(titulo); jueActual.setPlataforma(plataforma); jueActual.setLanzamiento(lanzamiento); jueActual.sethJugadas(horas);;
				jueActual.setResumen(resumen); jueActual.setEmpresa(empresa);
				Conexion.updateJuego(jueActual);
				lblErrorJue.setVisible(false);

				//UPDATE PORTADA
				if(cambiaPortadaJue) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					jueActual.setPortada(rutaPortadaJue);
					Conexion.actualizaPortadaJue(jueActual, true);
				}



			}
			else {
				lblErrorJue.setText("ERROR: Campos incompletos");
				lblErrorJue.setVisible(true);

			}

		}catch (NumberFormatException e) {
			lblErrorJue.setText("ERROR: No se pudo editar");
			lblErrorJue.setVisible(true);


		}catch (Exception e) {
			lblErrorJue.setText("ERROR: No se pudo editar");
			lblErrorJue.setVisible(true);


		}
	}

	@FXML void clickGuardaJuegoAgregado(MouseEvent event) {
		try {

			String titulo = txtFieldTitJuego.getText();
			String empresa = txtFieldCompaniaJuegoInd.getText();
			String plataforma = txtFieldPlataformaJuego.getText();
			String resumen = txtAreaResumenJuego.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaJuego.getText());
			int horas = Integer.parseInt(txtFieldHorJuego.getText());
			int id_juego = Conexion.consultaNum("JUEGOS", "MAX(ID_JUEGO)", null) +1;

			if(!titulo.equals("") && !plataforma.equals("") && lanzamiento >= 0 && horas >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN) Y NO SER NEGATIVOS
				jueActual.setTitulo(titulo); jueActual.setPlataforma(plataforma); jueActual.setLanzamiento(lanzamiento); jueActual.sethJugadas(horas);;
				jueActual.setResumen(resumen); jueActual.setEmpresa(empresa); jueActual.setID_Juego(id_juego); jueActual.setID_Usuario(u1.getID_Usuario());

				jueActual.setTerminado("NO"); //MODIFICAR Y PONER UNA OPCION PARA RELLENAR POR EL USUARIO

				if(cambiaPortadaJue) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					jueActual.setPortada(rutaPortadaJue);
				}

				Conexion.agregaJuego(jueActual, cambiaPortadaJue);
				lblErrorJue.setVisible(false);
			}
			else {
				lblErrorJue.setText("ERROR: Campos incompletos");
				lblErrorJue.setVisible(true);
			}

		}catch (NumberFormatException e) {
			lblErrorJue.setText("ERROR: No se pudo añadir");
			lblErrorJue.setVisible(true);


		}catch (Exception e) {
			lblErrorJue.setText("ERROR: No se pudo añadir");
			lblErrorJue.setVisible(true);
			e.printStackTrace();

		}


	}


	@FXML void clickBEliminaJuego(MouseEvent event){
		if(jueActual!=null && jueActual.getID_Juego() > 16) { //PARA QUE NO PERMITA ELIMINAR LOS POR DEFECTO
			pAvisoJue.setVisible(true);
			pFondoAvisoJue.setVisible(true);
		}

	}


	@FXML void clickAceptaAvisoJue(MouseEvent event) {

		try {
			Conexion.eliminaJuego(jueActual);

		} catch (Exception e) {
			e.printStackTrace();
		}

		pAvisoJue.setVisible(false);
		pFondoAvisoJue.setVisible(false);
	}
	@FXML void clickRechazaAvisoJue(MouseEvent event) {
		pAvisoJue.setVisible(false);
		pFondoAvisoJue.setVisible(false);
	}
	

	@FXML void refreshTablaJue(MouseEvent event) { //CLICK ACTUALIZAR TABLA LIBROS
		rellenaTablaJuegos(categJue);
	}



	@FXML void backSinglePane(MouseEvent event) {

		if(event.getSource() == btnBackPaneJuegos) {
			pJuegoIndiv.setVisible(false);

		}
	}


	private void initPanelesIndiv() {

		lblTituloJuegoInd.setVisible(true);
		lblGeneroJuegoInd.setVisible(true);
		lblPlataformaJuegoInd.setVisible(true);
		lblYearJuegoInd.setVisible(true);
		lblCompaniaJuegoInd.setVisible(true);
		lblNHorasJuegoInd.setVisible(true);
		lblResumenJuegoInd.setVisible(true);


		txtAreaResumenJuego.setVisible(false);
		txtFieldTitJuego.setVisible(false);
		txtFieldGeneroJuego.setVisible(false);
		txtFieldCompaniaJuegoInd.setVisible(false);
		txtFieldFechaJuego.setVisible(false);
		txtFieldHorJuego.setVisible(false);
		txtFieldPlataformaJuego.setVisible(false);

	}


	private void rellenaTablaJuegos(String categoria) {

		try {
			ObservableList<Juego> listaJuegos; 
			if(categoria.equalsIgnoreCase("FAVORITOS")) {
				listaJuegos = Conexion.fillFavGames(u1.getID_Usuario());
			}
			else {
				listaJuegos = Conexion.fillListGames(categoria, u1.getID_Usuario()); //NOS TRAEMOS LA LISTA DE LA CONSULTA
			}

			//CUANDO CAMBIAMOS DE CATEGORIA, PONEMOS EL TITULO Y LA PORTADA DEL PRIMER LIBRO DE LA LISTA
			if(listaJuegos.size()!=0) { 
				bFavJue.setVisible(true);
				jueActual = listaJuegos.get(0);
				lblTituloListaJue.setText(jueActual.getTitulo()); 

				String portada = jueActual.getPortada(); 
				Image image; 

				if(portada!=null) {
					try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
						image = new Image(getClass().getResource(portada).toString());
						portadaListaJue.setImage(image);
					} catch (Exception e) {
						//portadaListaLib.setImage(null);
						image = new Image(portada);
						portadaListaJue.setImage(image);
					}
				}

				Image imgFav;
				if(jueActual.getFavorito().equalsIgnoreCase("NO")) {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				}
				else {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				}

				bFavJue.setImage(imgFav);

			}
			else { //SI NO HAY ELEMENTOS EN LA LISTA:
				portadaListaJue.setImage(null);
				bFavJue.setVisible(false);
				lblTituloListaJue.setText("No hay Juegos");
				jueActual = null;
			}

			tablaJuegos.setItems(listaJuegos); //RELLENAR TABLA CON LOS LIBROS DE CADA CATEGORIA

			tituloJue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
			plataformaJue.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPlataforma()));
			fechaJue.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLanzamiento()).asObject()); //SE LE PONE AS OBJECT() PORQUE ESPERA UN INTEGER Y ESTAMOS PASANDO UN INT
			horasJue.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().gethJugadas()).asObject());



		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}



	@FXML void clickEligeJuego(MouseEvent event){ //CUANDO PULSAMOS UN OBJETO DE LA TABLA, COGEMOS SUS DATOS
		jueActual = tablaJuegos.getSelectionModel().getSelectedItem();

		if(jueActual!=null) {
			String portada = jueActual.getPortada(); 
			if(portada!=null) {
				try {
					Image image = new Image(getClass().getResource(portada).toString()); //RUTA RELATIVA
					portadaListaJue.setImage(image);
				} catch (Exception e) {
					Image image = new Image(portada); //RUTA ABSOLUTA
					portadaListaJue.setImage(image);


				}
			}
			else {
				portadaListaJue.setImage(null);
			}

			lblTituloListaJue.setText(jueActual.getTitulo());

			Image imgFav;
			if(jueActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
			}

			bFavJue.setImage(imgFav);
		}
	}



	@FXML void cambiaPortadaJuego(MouseEvent event) { //SOLO DISPONIBLE CUANDO EDITAS O AGREGAS UN JUEGO

		if(accJuego.equalsIgnoreCase("EDITAR") || accJuego.equalsIgnoreCase("AGREGAR") ) { 

			FileChooser fChooser = new FileChooser();
			fChooser.setTitle("Selecciona una imagen");
			fChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
					);
			File selectedFile = fChooser.showOpenDialog(null);
			if (selectedFile != null) {
				rutaPortadaJue = selectedFile.toURI().toString();
				Image image = new Image(rutaPortadaJue);
				portadaJuego.setImage(image);
				cambiaPortadaJue = true;
			}
		}

	}



	@FXML void clickBFavJue(MouseEvent event) { //CLICK PARA AGREGAR A FAVORITOS
		try {

			Image imgFav;
			if(jueActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				jueActual.setFavorito("SI");
				Conexion.updateTabla("JUEGOS", "FAVORITO", "SI", "ID_JUEGO = "+jueActual.getID_Juego());
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				jueActual.setFavorito("NO");
				Conexion.updateTabla("JUEGOS", "FAVORITO", "NO", "ID_JUEGO = "+jueActual.getID_Juego());
			}

			bFavJue.setImage(imgFav);
		}
		catch (Exception e) {
			System.out.println("ERROR AL AÑADIR FAVORITO");
			e.printStackTrace();
		}
	}




	public Pane getPane() {
		return pJuegos;
	}

	public void setMenuController(MenuController menu) {
		this.menu = menu;
	}







}
