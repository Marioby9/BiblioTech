package application;

import java.io.File;
import java.sql.SQLException;

import elementos.Libro;
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

public class LibrosController {

	@FXML private AnchorPane pRootLibros;
	//PANEL LIBROS 
	@FXML protected Pane pLibroIndiv, pListaLibros;
	@FXML private Pane pLibros, bGuardaEditaLibro, bGuardaAgregaLibro, pAvisoLib, pFondoAvisoLib;
	//BOTONES PLIBROS
	@FXML private ImageView bLibAmor, bLibAventuras, bLibComedia, bLibFavoritos, bLibTerror, btnBackPaneLibros, bFavLib, addBook, portadaListaLib, portadaLibro;
	@FXML private TableView<Libro> tablaLibros;
	@FXML private TableColumn<Libro, String> tituloLib;
	@FXML private TableColumn<Libro, String> autorLib;
	@FXML private TableColumn<Libro, Integer> fechaLib;
	@FXML private TableColumn<Libro, Integer> nPagsLib;
	private Libro libActual = null;
	private String categLib = "";
	private String accLibro = "";
	private String rutaPortadaLib = "";
	private boolean cambiaPortadaLib = false; //CUANDO QUIERAS SUBIR UN ARCHIVO, SE PONDRA TRUE


	//PANELES LISTA LIBROS
	@FXML private Label lblTituloLibroInd, lblAutorLibroInd, lblNPagsLibro, lblGeneroLibroInd, lblYearLibroInd, lblResumenLibroInd, lblTituloListaLib, lblErrorLib;
	@FXML private TextArea txtAreaResumenLibro;
	@FXML private TextField txtFieldTitLibro, txtFieldAutorLibro, txtFieldFechaLibro, txtFieldPagLibro;


	//REFERENCIAS CONTROLADORES
	protected MenuController menu;
	private Usuario u1;


	public void initialize() {
		u1 = Usuario.getUsuario();
		initComponents();
	}

	public void initComponents() {
		pLibros.setVisible(true);
		pLibroIndiv.setVisible(false);
		pListaLibros.setVisible(false);
		initPanelesIndiv();
	}


	@FXML void backSinglePane(MouseEvent event) {

		if(event.getSource() == btnBackPaneLibros) {
			pLibroIndiv.setVisible(false);

		}
	}


	//LIBROS:

	@FXML void clickListaLibros(MouseEvent e) { //RELLENA LA TABLA DE CADA UNA DE LAS LISTAS. JUGANDO CON EL VALOR DE CATEGORIA (GENERO)

		pListaLibros.setVisible(true);
		addBook.setVisible(true);
		if(e.getSource()==bLibAventuras) {
			categLib = "AVENTURAS";
		}
		else if(e.getSource()==bLibAmor) {
			categLib = "AMOR";
		}
		else if(e.getSource()==bLibTerror) {
			categLib = "TERROR";
		}
		else if(e.getSource()==bLibComedia) {
			categLib = "COMEDIA";
		}
		else if(e.getSource()==bLibFavoritos) {
			categLib = "FAVORITOS";
			addBook.setVisible(false);
		}


		rellenaTablaLibros(categLib);
	}

	@FXML void showSinglePaneLib(){


		if(libActual!=null) {
			accLibro = "";
			pLibroIndiv.setVisible(true);
			bGuardaEditaLibro.setVisible(false);
			bGuardaAgregaLibro.setVisible(false);
			lblErrorLib.setVisible(false);
			cambiaPortadaLib = false;
			initPanelesIndiv(); //HACE VISIBLES LOS LABELS E INVISIBLES LOS TEXTFIELD

			lblTituloLibroInd.setText(libActual.getTitulo());
			lblAutorLibroInd.setText(libActual.getAutor());
			lblGeneroLibroInd.setText(libActual.getGenero());
			lblNPagsLibro.setText(Integer.toString(libActual.getnPaginas()));
			lblYearLibroInd.setText(Integer.toString(libActual.getLanzamiento()));

			String portada = libActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
					image = new Image(getClass().getResource(portada).toString());
					portadaLibro.setImage(image);
				} catch (Exception e) { //SI SE LEVANTA LA EXCEPCION, LA RUTA ES RELATIVA
					//portadaLibro.setImage(null);
					image = new Image(portada);
					portadaLibro.setImage(image);
				}
			}
			else {
				portadaLibro.setImage(null);
			}


			//RESUMEN
			if(libActual.getResumen()!=null) {
				lblResumenLibroInd.setText(libActual.getResumen());
			}
			else {
				lblResumenLibroInd.setText("Este libro no tiene resumen");
			}

		}


	}

	@FXML void editSinglePaneLib(){


		if(libActual!=null && libActual.getID_Usuario()>0) { //NO PUEDES EDITAR LOS POR DEFECTO
			accLibro = "EDITAR";
			pLibroIndiv.setVisible(true);
			bGuardaEditaLibro.setVisible(true);
			bGuardaAgregaLibro.setVisible(false);
			lblErrorLib.setVisible(false);
			cambiaPortadaLib = false;

			//LABELS INVISIBLES
			lblTituloLibroInd.setVisible(false); lblAutorLibroInd.setVisible(false); lblNPagsLibro.setVisible(false); lblYearLibroInd.setVisible(false); lblResumenLibroInd.setVisible(false); 

			//TEXTFIELDS VISIBLES
			txtFieldTitLibro.setVisible(true); txtFieldAutorLibro.setVisible(true); txtFieldPagLibro.setVisible(true); txtFieldFechaLibro.setVisible(true); txtAreaResumenLibro.setVisible(true);

			//PONER LOS DATOS QUE TIENEN ACTUALMENTE Y EDITAR A PARTIR DE AHI
			txtFieldTitLibro.setText(libActual.getTitulo()); 						txtFieldAutorLibro.setText(libActual.getAutor()); 
			txtFieldPagLibro.setText(Integer.toString(libActual.getnPaginas())); 	txtFieldFechaLibro.setText(Integer.toString(libActual.getLanzamiento())); 
			lblGeneroLibroInd.setText(libActual.getGenero());


			String portada = libActual.getPortada(); 
			Image image; 
			if(portada!=null) {
				image = new Image(portada);
				portadaLibro.setImage(image);
			}
			else {
				portadaLibro.setImage(null);
			}


			//RESUMEN
			if(libActual.getResumen()!=null) {
				txtAreaResumenLibro.setText(libActual.getResumen());
			}
			else {
				txtAreaResumenLibro.setText(null);
			}

		}

	}


	@FXML void agregaSinglePaneLib(){

		accLibro = "AGREGAR";  //PARA QUE AL PULSAR EN LA PORTADA, SOLO SALGA EL FILECHOOSER CUANDO ESTEMOS EDITANDO O AGREGANDO UN LIBRO
		pLibroIndiv.setVisible(true);
		bGuardaAgregaLibro.setVisible(true);
		bGuardaEditaLibro.setVisible(false);
		lblErrorLib.setVisible(false);
		cambiaPortadaLib = false;



		//LABELS INVISIBLES
		lblTituloLibroInd.setVisible(false); lblAutorLibroInd.setVisible(false); lblNPagsLibro.setVisible(false); lblYearLibroInd.setVisible(false); lblResumenLibroInd.setVisible(false); portadaLibro.setImage(null);

		//TEXTFIELDS VISIBLES
		txtFieldTitLibro.setVisible(true); txtFieldAutorLibro.setVisible(true); txtFieldPagLibro.setVisible(true); txtFieldFechaLibro.setVisible(true); txtAreaResumenLibro.setVisible(true);

		//VACIAR TEXTFIELDS PARA QUE EL USUARIO INTRODUZCA DATOS DE 0
		txtFieldTitLibro.setText(""); 	txtAreaResumenLibro.setText(""); 	txtFieldAutorLibro.setText(""); 
		txtFieldPagLibro.setText(""); 	txtFieldFechaLibro.setText(""); 
		lblGeneroLibroInd.setText(libActual.getGenero());

	}


	@FXML void clickGuardaLibroEditado(MouseEvent event) { 
		try {
			String titulo = txtFieldTitLibro.getText();
			String autor = txtFieldAutorLibro.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaLibro.getText());
			int pags = Integer.parseInt(txtFieldPagLibro.getText());
			String resumen = txtAreaResumenLibro.getText();

			if(!titulo.equals("") && !autor.equals("") && lanzamiento >= 0 && pags >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN) Y NO SER NEGATIVOS

				//UPDATE DATOS LIBRO
				libActual.setTitulo(titulo); libActual.setAutor(autor); libActual.setLanzamiento(lanzamiento); libActual.setnPaginas(pags);
				libActual.setResumen(resumen);
				Conexion.updateLibro(libActual);
				lblErrorLib.setVisible(false);

				//UPDATE PORTADA
				if(cambiaPortadaLib) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					libActual.setPortada(rutaPortadaLib);
					Conexion.actualizaPortadaLib(libActual, true);
				}



			}
			else {
				lblErrorLib.setText("ERROR: Campos incompletos");
				lblErrorLib.setVisible(true);

			}

		}catch (NumberFormatException e) {
			lblErrorLib.setText("ERROR: No se pudo editar");
			lblErrorLib.setVisible(true);


		}catch (Exception e) {
			lblErrorLib.setText("ERROR: No se pudo editar");
			lblErrorLib.setVisible(true);


		}
	}

	@FXML void clickGuardaLibroAgregado(MouseEvent event) {
		try {
			String titulo = txtFieldTitLibro.getText();
			String autor = txtFieldAutorLibro.getText();
			int lanzamiento = Integer.parseInt(txtFieldFechaLibro.getText());
			int pags = Integer.parseInt(txtFieldPagLibro.getText());
			String resumen = txtAreaResumenLibro.getText();
			int id_libro = Conexion.consultaNum("LIBROS", "MAX(ID_LIBRO)", null) +1;

			if(!titulo.equals("") && !autor.equals("") && lanzamiento >= 0 && pags >= 0) { //TODOS LOS CAMPOS DEBEN ESTAR RELLENADOS (MENOS RESUMEN) Y NO SER NEGATIVOS
				libActual.setTitulo(titulo); libActual.setAutor(autor); libActual.setLanzamiento(lanzamiento); libActual.setnPaginas(pags); libActual.setID_Libro(id_libro);
				libActual.setID_Usuario(u1.getID_Usuario()); libActual.setResumen(resumen);

				libActual.setTerminado("NO"); //MODIFICAR Y PONER UNA OPCION PARA RELLENAR POR EL USUARIO

				if(cambiaPortadaLib) { //SI HAS ABIERTO EL FILE CHOOSER PARA CAMBIAR O AGREGAR PORTADA:
					libActual.setPortada(rutaPortadaLib);
				}

				Conexion.agregaLibro(libActual, cambiaPortadaLib);
				lblErrorLib.setVisible(false);
			}
			else {
				lblErrorLib.setText("ERROR: Campos incompletos");
				lblErrorLib.setVisible(true);
			}

		}catch (NumberFormatException e) {
			lblErrorLib.setText("ERROR: No se pudo añadir");
			lblErrorLib.setVisible(true);


		}catch (Exception e) {
			lblErrorLib.setText("ERROR: No se pudo añadir");
			lblErrorLib.setVisible(true);
		}

	}

	@FXML void clickBEliminaLibro(MouseEvent event){
		if(libActual != null && libActual.getID_Libro() > 16) { //PARA QUE NO PERMITA ELIMINAR LOS POR DEFECTO
			pFondoAvisoLib.setVisible(true);
			pAvisoLib.setVisible(true);
		}
	}

	@FXML void clickAceptaAvisoLib(MouseEvent event) {

		try {
			Conexion.eliminaLibro(libActual);

		} catch (Exception e) {
			e.printStackTrace();
		}

		pAvisoLib.setVisible(false);
		pFondoAvisoLib.setVisible(false);
	}
	@FXML void clickRechazaAvisoLib(MouseEvent event) {
		pAvisoLib.setVisible(false);
		pFondoAvisoLib.setVisible(false);
	}

	@FXML void refreshTablaLib(MouseEvent event) { //CLICK ACTUALIZAR TABLA LIBROS
		rellenaTablaLibros(categLib);
	}


	//LIBROS, JUEGOS, CANCIONES FUNCIONES EQUIVALENTES
	private void rellenaTablaLibros(String categoria) {

		try {
			ObservableList<Libro> listaLibros;
			if(categoria.equalsIgnoreCase("FAVORITOS")) {
				listaLibros = Conexion.fillFavBooks(u1.getID_Usuario());
			}
			else {
				listaLibros = Conexion.fillListBooks(categoria, u1.getID_Usuario()); //NOS TRAEMOS LA LISTA DE LA CONSULTA
			}

			//CUANDO CAMBIAMOS DE CATEGORIA, PONEMOS EL TITULO Y LA PORTADA DEL PRIMER LIBRO DE LA LISTA
			if(listaLibros.size()!=0) { 
				bFavLib.setVisible(true);
				libActual = listaLibros.get(0);
				lblTituloListaLib.setText(libActual.getTitulo()); 

				String portada = libActual.getPortada(); 
				Image image; 

				if(portada!=null) {
					try { //NECESARIO PARA QUE CUANDO NO ENCUENTRE LA RUTA, NO DE ERROR. INCLUIR DIFERENCIAS ENTRE RELATIVA Y ABSOLUTA
						image = new Image(getClass().getResource(portada).toString());
						portadaListaLib.setImage(image);
					} catch (Exception e) {
						//portadaListaLib.setImage(null);
						image = new Image(portada);
						portadaListaLib.setImage(image);
					}
				}

				Image imgFav;
				if(libActual.getFavorito().equalsIgnoreCase("NO")) {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				}
				else {
					imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				}

				bFavLib.setImage(imgFav);

			}
			else { //SI NO HAY ELEMENTOS EN LA LISTA:
				portadaListaLib.setImage(null);
				lblTituloListaLib.setText("No hay libros");
				bFavLib.setVisible(false);
				libActual = null;
			}

			tablaLibros.setItems(listaLibros); //RELLENAR TABLA CON LOS LIBROS DE CADA CATEGORIA

			tituloLib.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
			autorLib.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutor()));
			fechaLib.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getLanzamiento()).asObject()); //SE LE PONE AS OBJECT() PORQUE ESPERA UN INTEGER Y ESTAMOS PASANDO UN INT
			nPagsLib.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getnPaginas()).asObject());


		} catch (SQLException ex) {
			ex.printStackTrace();
		}

	}

	@FXML void clickEligeLibro(MouseEvent event){ //CUANDO PULSAMOS UN OBJETO DE LA TABLA, COGEMOS SUS DATOS
		libActual = tablaLibros.getSelectionModel().getSelectedItem();

		if(libActual!=null) {
			String portada = libActual.getPortada(); 
			if(portada!=null) {
				try {
					Image image = new Image(getClass().getResource(portada).toString()); //RUTA RELATIVA
					portadaListaLib.setImage(image);
				} catch (Exception e) {
					Image image = new Image(portada); //RUTA ABSOLUTA
					portadaListaLib.setImage(image);

				}
			}
			else {
				portadaListaLib.setImage(null);
			}

			lblTituloListaLib.setText(libActual.getTitulo());


			Image imgFav;
			if(libActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
			}

			bFavLib.setImage(imgFav);

		}
	}

	@FXML void cambiaPortadaLibro(MouseEvent event) { //SOLO DISPONIBLE CUANDO EDITAS O AGREGAS UN LIBRO

		if(accLibro.equalsIgnoreCase("EDITAR") || accLibro.equalsIgnoreCase("AGREGAR") ) { 

			FileChooser fChooser = new FileChooser();
			fChooser.setTitle("Selecciona una imagen");
			fChooser.getExtensionFilters().addAll(
					new FileChooser.ExtensionFilter("Imágenes", "*.png", "*.jpg", "*.jpeg")
					);
			File selectedFile = fChooser.showOpenDialog(null);
			if (selectedFile != null) {
				rutaPortadaLib = selectedFile.toURI().toString();
				Image image = new Image(rutaPortadaLib);
				portadaLibro.setImage(image);
				cambiaPortadaLib = true;
			}
		}

	}

	@FXML void clickBFavLib(MouseEvent event) { //CLICK PARA AGREGAR A FAVORITOS
		try {

			Image imgFav;
			if(libActual.getFavorito().equalsIgnoreCase("NO")) {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favRojo.png"));
				libActual.setFavorito("SI");
				Conexion.updateTabla("LIBROS", "FAVORITO", "SI", "ID_LIBRO = "+libActual.getID_Libro());
			}
			else {
				imgFav = new Image(getClass().getResourceAsStream("/icons/favBlanco.png"));
				libActual.setFavorito("NO");
				Conexion.updateTabla("LIBROS", "FAVORITO", "NO", "ID_LIBRO = "+libActual.getID_Libro());
			}

			bFavLib.setImage(imgFav);
		}
		catch (Exception e) {
			System.out.println("ERROR AL AÑADIR FAVORITO");
			e.printStackTrace();
		}
	}


	private void initPanelesIndiv() {
		lblAutorLibroInd.setVisible(true);
		lblGeneroLibroInd.setVisible(true);
		lblNPagsLibro.setVisible(true);
		lblTituloLibroInd.setVisible(true);
		lblYearLibroInd.setVisible(true);
		lblResumenLibroInd.setVisible(true);



		txtAreaResumenLibro.setVisible(false);
		txtFieldAutorLibro.setVisible(false);
		txtFieldFechaLibro.setVisible(false);
		txtFieldTitLibro.setVisible(false);
		txtFieldPagLibro.setVisible(false);


	}



	public Pane getPane() {
		return pLibros;
	}

	public void setMenuController(MenuController menu) {
		this.menu = menu;
	}





}
