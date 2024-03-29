package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import elementos.Cancion;
import elementos.Juego;
import elementos.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;

/**
 * Esta clase contiene todas las funciones que conectan la aplicacion con la Base de Datos
 * @author Bibliotech
 * @param login. Usuario de la Base de datos
 * @param password. Contraseña del usuario de la Base de datos
 * @param url. Host de la Base de datos
 */
public class Conexion {

	private static String bd = "XE";
	private static String login = "Bibliotech";
	private static String password = "Bibliotech";
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String url = "jdbc:oracle:thin:@localhost:1521:"+bd;
	static Connection connection = null;


	/**
	 * Metodo que conecta con la base de datos
	 * @return boolean
	 */
	public static boolean conectar() {
		boolean conectado = false;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url,login,password);
			if(connection != null) {
				conectado = true;
				System.out.println("Conexion realizada correctamente.");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
		return conectado;
	}

	/**
	 * Funcion para hacer consultas de Strings
	 * @param tabla elige la tabla para hacer la query
	 * @param columna elige la tabla para hacer la query
	 * @param condicion elige la condicion para hacer la query
	 * @return str
	 * @throws SQLException
	 */
	
	public static String consultaStr(String tabla, String columna, String condicion) throws SQLException{ //FUNCION PARA HACER CONSULTAS DE NUMEROS 
		String str = "";

		st = connection.createStatement();
		String sql;

		if(condicion != null) {
			sql = "SELECT "+columna+ " FROM "+tabla+" WHERE " + condicion;
		}
		else {
			sql= "SELECT "+columna+ " FROM "+tabla;
		}

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			str = rs.getString(columna);
		}
		st.close();

		return str;
	}

	/**
	 * Funcion para hacer consultas de Strings
	 * @param tabla elige la tabla para hacer la query
	 * @param columna elige la columna para hacer la query
	 * @param condicion elige la condicion para hacer la query
	 * @return num
	 * @throws SQLException
	 */
	public static int consultaNum(String tabla, String columna, String condicion) throws SQLException{ //FUNCION PARA HACER CONSULTAS DE NUMEROS
		int num = 0;

		st = connection.createStatement();
		String sql;

		if(condicion != null) {
			sql = "SELECT "+columna+ " FROM "+tabla+" WHERE " + condicion;
		}
		else {
			sql= "SELECT "+columna+ " FROM "+tabla;
		}

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			num = rs.getInt(columna);
		}
		st.close();

		return num;
	}


	//REGISTRAR, ELIMINAR Y ACTUALIZAR USUARIO 
	/**
	 * Funcion para registrar un usuario
	 * @param u Objeto de tipo usuario
	 * @return boolean
	 * @throws SQLException
	 */

	public static boolean registraUsuario (Usuario u) throws SQLException{ //MODIFICAR
		boolean insertado = false;

		String sql= "insert into Usuario (ID, Nickname, contraseña, correo) values(?, ? , ? , ?)";

		pst = connection.prepareStatement(sql);


		pst.setInt(1, u.getID_Usuario());
		pst.setString(2, u.getNickname());
		pst.setString(3, u.getContrasena());
		pst.setString(4, u.getCorreo());

		insertado = pst.executeUpdate()>0; 

		if(insertado) { //SI LO HA HECHO BIEN, LO INSERTAMOS TAMBIEN EN LAS DEMAS TABLAS 

			sql= "insert into Ajustes (ID_usuario) values(?)";
			pst = connection.prepareStatement(sql);

			pst.setInt(1, u.getID_Usuario());
			insertado = pst.executeUpdate()>0; 
		}
		
		if(insertado) { //SI LO HA HECHO BIEN, LO INSERTAMOS TAMBIEN EN LAS DEMAS TABLAS 

			sql= "insert into TIEMPO_USUARIO (ID_usuario) values(?)";
			pst = connection.prepareStatement(sql);

			pst.setInt(1, u.getID_Usuario());
			insertado = pst.executeUpdate()>0; 
		}

		pst.close();
		return insertado;
	}

	/**
	 * Funcion para modificar un usuario
	 * @param u Objeto de tipo usuario
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean eliminarUsuario (Usuario u) throws SQLException{ //MODIFICAR

		boolean eliminado = false;


		String sql= "DELETE FROM USUARIO WHERE ID = "+u.getID_Usuario() + " AND ID != 1";

		pst = connection.prepareStatement(sql);

		eliminado = pst.executeUpdate()>0;
		pst.close();

		return eliminado;
	}

	/**
	 * Funcion para actualizar un usuario
	 * @param u Objeto de tipo usuario
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean updateUsuario(Usuario u) throws SQLException{ 
		boolean updated = false;

		String sql= "UPDATE USUARIO SET NICKNAME = ?, CONTRASEÑA = ?, CORREO = ? WHERE ID = "+u.getID_Usuario();
		pst = connection.prepareStatement(sql);
		pst.setObject(1, u.getNickname());
		pst.setObject(2, u.getContrasena());
		pst.setObject(3, u.getCorreo());

		updated = pst.executeUpdate()>0;
		pst.close();


		return updated;
	}


	//QUERYS DEL LOGIN
	/**
	 * Funcion que comprueba las condiciones para iniciar sesion
	 * @param nombre nombre de usuario
	 * @param password contraseña
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean compruebaLogIn(String nombre, String password) throws SQLException  {  //MODIFICAR

		boolean accede = false;
		String nickname;
		String contrasena;

		st = connection.createStatement();
		ResultSet rs = st.executeQuery("select nickname, contraseña from usuario WHERE nickname = '"+nombre+"'");

		while(rs.next()) {
			nickname = rs.getString("nickname");
			contrasena = rs.getString("contraseña");
			if(nickname.equals(nombre) && contrasena.equals(password)){

				accede = true;
			}

		}

		st.close();

		return accede;
	}
	
	/**
	 * Funcion que inicia el tiempo cuando entra en la aplicacion
	 * @param u Objeto de tipo usuario
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean iniciaTiempo(Usuario u) throws SQLException{ 
		boolean updated = false;

		String sql= "UPDATE TIEMPO_USUARIO SET ult_inicio = TO_CHAR(SYSDATE,'HH24:MI') WHERE ID_USUARIO = "+u.getID_Usuario();
		pst = connection.prepareStatement(sql);

		updated = pst.executeUpdate()>0;
		pst.close();


		return updated;
	}
	
	/**
	 * Funcion que termina el tiempo cuando sale en la aplicacion
	 * @param u Objeto de tipo usuario
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean terminaTiempo(Usuario u) throws SQLException{ 
		boolean updated = false;
		
		/*A ULT_CIERRE LE ASIGNA EL VALOR DE LA HORA Y MINUTO EN EL QUE SE CIERRA LA APLICACION
		  Y A USO_TOTAL LE SUMA EL VALOR QUE TIENE USO_TOTAL + LA RESTA DE (ULT_CIERR - ULT_INICIO), LO PASA A MINUTOS Y REDONDEA CON ROUND
		 */
		String sql= "UPDATE TIEMPO_USUARIO SET ult_cierre = TO_CHAR(SYSDATE,'HH24:MI'), uso_Total = uso_Total + (ROUND((TO_DATE(TO_CHAR(SYSDATE, 'HH24:MI'), 'HH24:MI') - TO_DATE(ult_Inicio, 'HH24:MI')) * 24 * 60))  WHERE ID_USUARIO = "+u.getID_Usuario();
		pst = connection.prepareStatement(sql);

		updated = pst.executeUpdate()>0;
		pst.close();


		return updated;
	}
	
	

	//CAMBIAR VALORES PARA TABLAS
	/**
	 * Funcion que cambia los valores en las tablas
	 * @param tabla Tabla donde se hace la query
	 * @param columna columna donde se hace la query
	 * @param valor tipo de valor
	 * @param condicion condicion para hacer la query
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean updateTabla(String tabla, String columna, Object valor, String condicion) throws SQLException{
		boolean updated = false;


		String sql= "UPDATE "+tabla+" SET "+ columna+ " = ? WHERE " + condicion;
		pst = connection.prepareStatement(sql);
		pst.setObject(1, valor);

		updated = pst.executeUpdate()>0; 

		pst.close();
		return updated;
	}

	//LIBROS:
	/**
	 * Funcion para agregar libro
	 * @param libro libro que se quiere agregar
	 * @param portada portada que se quiere agregar
	 * @return boolean
	 * @throws SQLException
	 */

	public static boolean agregaLibro(Libro libro, boolean portada) throws SQLException {
		boolean insertado = false;
		String sql= "INSERT INTO LIBROS (ID_LIBRO, ID_USUARIO, TITULO, AUTOR, GENERO, N_PAGINAS, ANO_LANZ, RESUMEN, TERMINADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		pst = connection.prepareStatement(sql);
		pst.setInt(1, libro.getID_Libro());
		pst.setInt(2, libro.getID_Usuario());
		pst.setString(3, libro.getTitulo());
		pst.setString(4, libro.getAutor());
		pst.setString(5, libro.getGenero());
		pst.setInt(6, libro.getnPaginas());
		pst.setInt(7, libro.getLanzamiento());
		pst.setString(8, libro.getResumen());
		pst.setString(9, libro.getTerminado());
		insertado = pst.executeUpdate()>0;
		pst.close();

		if(portada) {
			insertado = false;
			sql= "UPDATE LIBROS SET PORTADA = ? WHERE ID_LIBRO = "+libro.getID_Libro();
			pst = connection.prepareStatement(sql);
			pst.setString(1, libro.getPortada());
			insertado = pst.executeUpdate()>0;
			pst.close();
		}


		return insertado;
	}

	/**
	 * Funcion que actualiza un libro
	 * @param libro libro que se actualiza
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean updateLibro(Libro libro) throws SQLException{ 
		boolean updated = false;

		String sql= "UPDATE LIBROS SET TITULO = ?, AUTOR = ?, ANO_LANZ = ?, N_PAGINAS = ?, RESUMEN = ?  WHERE ID_LIBRO = "+libro.getID_Libro();
		pst = connection.prepareStatement(sql);
		pst.setObject(1, libro.getTitulo());
		pst.setObject(2, libro.getAutor());
		pst.setObject(3, libro.getLanzamiento());
		pst.setObject(4, libro.getnPaginas());
		pst.setObject(5, libro.getResumen());
		updated = pst.executeUpdate()>0;
		pst.close();


		return updated;
	}

	/**
	 * Funcion que actualiza la portada de un libro
	 * @param libro libro a actualizar
	 * @param portada portada que se actualiza
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean actualizaPortadaLib(Libro libro, boolean portada) throws SQLException {
		boolean updated = false;
		String sql;
		if(portada) {
			updated = false;
			sql= "UPDATE LIBROS SET PORTADA = ?  WHERE ID_LIBRO = "+libro.getID_Libro();
			pst = connection.prepareStatement(sql);
			pst.setObject(1, libro.getPortada());
			updated = pst.executeUpdate()>0;
			pst.close();
		}
		else {
			updated = false;
			sql= "UPDATE LIBROS SET PORTADA = NULL WHERE ID_LIBRO = "+libro.getID_Libro();
			pst = connection.prepareStatement(sql);
			updated = pst.executeUpdate()>0;
			pst.close();
		}

		return updated;
	}



	/**
	 * Funcion que elimina un libro
	 * @param libro libro que se elimina
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean eliminaLibro(Libro libro) throws SQLException {
		boolean eliminado = false;

		//LOS LIBROS POR DEFECTO NO SE BORRAN
		String sql= "DELETE FROM LIBROS WHERE ID_LIBRO = "+libro.getID_Libro()+"AND ID_USUARIO IS NOT NULL";

		pst = connection.prepareStatement(sql);

		eliminado = pst.executeUpdate()>0;
		pst.close();

		return eliminado;
	}


	/**
	 * Funcion que rellena la lista libros
	 * @param genero genero del libro
	 * @param id_user id del usuario
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<Libro> fillListBooks(String genero, int id_user) throws SQLException{ 
		ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
		String titulo, autor, portada, resumen, terminado, favorito;
		int id_libro, id_usuario, n_pags, ano_lanz;

		st = connection.createStatement();
		String sql =  "SELECT ID_LIBRO, ID_USUARIO, TITULO, AUTOR, GENERO, N_PAGINAS, ANO_LANZ, TERMINADO, FAVORITO, PORTADA, RESUMEN FROM LIBROS WHERE UPPER(GENERO) = '" + genero
				+"' AND (ID_USUARIO IS NULL OR ID_USUARIO = "+id_user+")";

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			titulo = rs.getString("TITULO");
			autor = rs.getString("AUTOR");
			portada = rs.getString("PORTADA");
			resumen = rs.getString("RESUMEN");
			terminado = rs.getString("TERMINADO");
			n_pags = rs.getInt("N_PAGINAS");
			ano_lanz = rs.getInt("ANO_LANZ");
			id_libro = rs.getInt("ID_LIBRO");
			id_usuario = rs.getInt("ID_USUARIO");
			favorito = rs.getString("FAVORITO");

			Libro lib = new Libro(id_libro, id_usuario, titulo, genero, autor, n_pags, ano_lanz, terminado, portada, resumen);
			lib.setFavorito(favorito);
			listaLibros.add(lib);
		}
		st.close();



		return listaLibros;
	}


	/**
	 * Funcion que rellena la lista favoritos de libros
	 * @param id_user id del usuario
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<Libro> fillFavBooks(int id_user) throws SQLException{ //RELLENA LA TABLA SOLO CON LOS FAVORITOS
		ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
		String titulo, autor, portada, resumen, terminado, genero;
		int id_libro, id_usuario, n_pags, ano_lanz;

		st = connection.createStatement();
		String sql =  "SELECT ID_LIBRO, ID_USUARIO, TITULO, AUTOR, GENERO, N_PAGINAS, ANO_LANZ, TERMINADO, PORTADA, RESUMEN FROM LIBROS WHERE UPPER(FAVORITO) = 'SI' AND (ID_USUARIO IS NULL OR ID_USUARIO = "+id_user+")";

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			titulo = rs.getString("TITULO");
			autor = rs.getString("AUTOR");
			portada = rs.getString("PORTADA");
			resumen = rs.getString("RESUMEN");
			terminado = rs.getString("TERMINADO");
			n_pags = rs.getInt("N_PAGINAS");
			ano_lanz = rs.getInt("ANO_LANZ");
			id_libro = rs.getInt("ID_LIBRO");
			id_usuario = rs.getInt("ID_USUARIO");
			genero = rs.getString("GENERO");

			Libro lib = new Libro(id_libro, id_usuario, titulo, genero, autor, n_pags, ano_lanz, terminado, portada, resumen);
			lib.setFavorito("SI");
			listaLibros.add(lib);
		}
		st.close();



		return listaLibros;
	}


	//JUEGOS

	/**
	 * Funcion que agrega un juego
	 * @param juego juego que se agrega
	 * @param portada portada que se agrega
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean agregaJuego(Juego juego, boolean portada) throws SQLException {
		boolean insertado = false;
		String sql= "INSERT INTO JUEGOS (ID_JUEGO, ID_USUARIO, TITULO, EMPRESA, PLATAFORMA, GENERO, H_JUGADAS, LANZAMIENTO, RESUMEN, TERMINADO) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		pst = connection.prepareStatement(sql);
		pst.setInt(1, juego.getID_Juego());
		pst.setInt(2, juego.getID_Usuario());
		pst.setString(3, juego.getTitulo());
		pst.setString(4, juego.getEmpresa());
		pst.setString(5, juego.getPlataforma());
		pst.setString(6, juego.getGenero());
		pst.setInt(7, juego.gethJugadas());
		pst.setInt(8, juego.getLanzamiento());
		pst.setString(9, juego.getResumen());
		pst.setString(10, juego.getTerminado());
		insertado = pst.executeUpdate()>0;
		pst.close();

		if(portada) {
			insertado = false;
			sql= "UPDATE JUEGOS SET PORTADA = ? WHERE ID_JUEGO = "+juego.getID_Juego();
			pst = connection.prepareStatement(sql);
			pst.setString(1, juego.getPortada());
			insertado = pst.executeUpdate()>0;
			pst.close();
		}


		return insertado;
	}

	/**
	 * Funcion que actualiza un juego
	 * @param juego juego que se actualiza
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean updateJuego(Juego juego) throws SQLException{ 
		boolean updated = false;

		String sql= "UPDATE JUEGOS SET TITULO = ?, PLATAFORMA = ?, EMPRESA = ?, LANZAMIENTO = ?, H_JUGADAS = ?, RESUMEN = ?  WHERE ID_JUEGO = "+ juego.getID_Juego();
		pst = connection.prepareStatement(sql);
		pst.setObject(1, juego.getTitulo());
		pst.setObject(2, juego.getPlataforma());
		pst.setObject(3, juego.getEmpresa());
		pst.setObject(4, juego.getLanzamiento());
		pst.setObject(5, juego.gethJugadas());
		pst.setObject(6, juego.getResumen());

		updated = pst.executeUpdate()>0;
		pst.close();


		return updated;
	}

	/**
	 * Funcion que actualiza la portada de un juego
	 * @param juego juego que se actualiza
	 * @param portada portada que se actualiza
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean actualizaPortadaJue(Juego juego, boolean portada) throws SQLException {
		boolean updated = false;
		String sql;
		if(portada) {
			updated = false;
			sql= "UPDATE JUEGOS SET PORTADA = ?  WHERE ID_JUEGO = "+juego.getID_Juego();
			pst = connection.prepareStatement(sql);
			pst.setObject(1, juego.getPortada());
			updated = pst.executeUpdate()>0;
			pst.close();
		}
		else {
			updated = false;
			sql= "UPDATE JUEGOS SET PORTADA = NULL WHERE ID_JUEGO = "+juego.getID_Juego();
			pst = connection.prepareStatement(sql);
			updated = pst.executeUpdate()>0;
			pst.close();
		}

		return updated;
	}

	
	/**
	 * Funcion que elimina un juego
	 * @param juego juego que se elimina
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean eliminaJuego(Juego juego) throws SQLException {
		boolean eliminado = false;

		//LOS LIBROS POR DEFECTO NO SE BORRAN
		String sql= "DELETE FROM JUEGOS WHERE ID_JUEGO = "+juego.getID_Juego()+" AND ID_USUARIO IS NOT NULL";

		pst = connection.prepareStatement(sql);

		eliminado = pst.executeUpdate()>0;
		pst.close();

		return eliminado;
	}



	/**
	 * Funcion que llena la tabla de juegos
	 * @param genero genero de juego
	 * @param id_user id del usuario
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<Juego> fillListGames(String genero, int id_user) throws SQLException{ 
		ObservableList<Juego> listaJuegos = FXCollections.observableArrayList();
		String titulo, plataforma, portada, resumen, terminado, empresa, favorito;
		int id_Juego, id_usuario, ano_lanz;
		int hJugadas;

		st = connection.createStatement();
		String sql =  "SELECT ID_JUEGO, ID_USUARIO, TITULO, GENERO, PLATAFORMA, TERMINADO, FAVORITO, LANZAMIENTO, H_JUGADAS, PORTADA, RESUMEN, EMPRESA FROM JUEGOS WHERE UPPER(GENERO) = '" + genero
				+"' AND (ID_USUARIO IS NULL OR ID_USUARIO = "+id_user+")";

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			id_Juego = rs.getInt("ID_JUEGO");
			id_usuario = rs.getInt("ID_USUARIO");
			titulo = rs.getString("TITULO");
			genero = rs.getString("GENERO");
			plataforma = rs.getString("PLATAFORMA");
			terminado = rs.getString("TERMINADO");
			ano_lanz = rs.getInt("LANZAMIENTO");
			hJugadas = rs.getInt("H_JUGADAS");
			portada = rs.getString("PORTADA");
			resumen = rs.getString("RESUMEN");
			empresa = rs.getString("EMPRESA");
			favorito = rs.getString("FAVORITO");


			Juego juego = new Juego(id_Juego, id_usuario, titulo, genero, plataforma, hJugadas, ano_lanz, terminado, portada, resumen, empresa);
			juego.setFavorito(favorito);
			listaJuegos.add(juego);
		}
		st.close();



		return listaJuegos;
	}

	
	/**
	 * Funcion que rellena la lista favorita de juegos
	 * @param id_user id del usuario
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<Juego> fillFavGames(int id_user) throws SQLException{ //RELLENA LA TABLA SOLO CON LOS FAVORITOS
		ObservableList<Juego> listaJuegos = FXCollections.observableArrayList();
		String titulo, plataforma, portada, resumen, terminado, empresa, genero;
		int id_Juego, id_usuario, ano_lanz;
		int hJugadas;

		st = connection.createStatement();
		String sql =  "SELECT ID_JUEGO, ID_USUARIO, TITULO, GENERO, PLATAFORMA, TERMINADO, LANZAMIENTO, H_JUGADAS, PORTADA, RESUMEN, EMPRESA FROM JUEGOS WHERE UPPER(FAVORITO) = 'SI' AND (ID_USUARIO IS NULL OR ID_USUARIO = "+id_user+")";

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			id_Juego = rs.getInt("ID_JUEGO");
			id_usuario = rs.getInt("ID_USUARIO");
			titulo = rs.getString("TITULO");
			plataforma = rs.getString("PLATAFORMA");
			terminado = rs.getString("TERMINADO");
			ano_lanz = rs.getInt("LANZAMIENTO");
			hJugadas = rs.getInt("H_JUGADAS");
			portada = rs.getString("PORTADA");
			resumen = rs.getString("RESUMEN");
			empresa = rs.getString("EMPRESA");
			genero = rs.getString("GENERO");


			Juego juego = new Juego(id_Juego, id_usuario, titulo, genero, plataforma, hJugadas, ano_lanz, terminado, portada, resumen, empresa);
			juego.setFavorito("SI");
			listaJuegos.add(juego);
		}
		st.close();



		return listaJuegos;
	}

	
	/**
	 * Funcion que agrega las canciones encontradas a la BBDD
	 * @param lista. Lista de canciones en carpetas
	 * @param u. Usuario
	 * @return boolean. True en caso de que haya sido agregada con exito.
	 * @throws SQLException
	 */
	public static boolean agregaCanciones(ObservableList<Cancion> lista, Usuario u) throws SQLException {
		boolean agregadas = false;
		int nuevoId;
	
		for(Cancion cancion : lista) {
			int cuantas = 0;
			
			cuantas = Conexion.consultaNum("Canciones", "COUNT(ID_CANCION)", "TITULO = '"+cancion.getNombre()+"' AND ID_USUARIO = "+ u.getID_Usuario());
			if (cuantas == 0) {
				
				nuevoId = Conexion.consultaNum("Canciones", "MAX(ID_CANCION)", null) +1;
				cancion.setID_Usuario(u.getID_Usuario());
				
				String sql= "INSERT INTO CANCIONES (ID_CANCION, ID_USUARIO, TITULO, ARTISTA, GENERO, RUTA) VALUES (?, ?, ?, ?, ?, ?)";
				pst = connection.prepareStatement(sql);
				pst.setInt(1, nuevoId);
				pst.setInt(2, cancion.getID_Usuario());
				pst.setString(3, cancion.getNombre());
				pst.setString(4, cancion.getArtista());
				pst.setString(5, cancion.getGenero());
				pst.setString(6, cancion.getRuta());
				
				agregadas = pst.executeUpdate()>0;
				
			}
			pst.close();
			
		}
		
		return agregadas;
	}

	/**
	 * Funcion que elimina una cancion de la base de datos (solo puede el admin)
	 * @param cancion
	 * @return boolean. True si se ha eliminado correctamente.
	 * @throws SQLException
	 */
	public static boolean eliminaCancion(Cancion cancion) throws SQLException {
		boolean eliminado = false;

		//LOS LIBROS POR DEFECTO NO SE BORRAN
		String sql= "DELETE FROM CANCIONES WHERE ID_CANCION = "+cancion.getID_Cancion()+" AND ID_USUARIO IS NOT NULL";

		pst = connection.prepareStatement(sql);

		eliminado = pst.executeUpdate()>0;
		pst.close();

		return eliminado;
	}
	
	/**
	 * Funcion que actualiza titulo, artista, genero o ruta de las canciones.
	 * @param cancion. Recibida al hacer click en ella
	 * @return boolean. True si se ha hecho con exito
	 * @throws SQLException
	 */
	public static boolean updateCancion(Cancion cancion) throws SQLException{ 
		boolean updated = false;

		String sql= "UPDATE CANCIONES SET TITULO = ?, ARTISTA = ?, GENERO = ?, RUTA = ?  WHERE ID_CANCION = "+ cancion.getID_Cancion();
		pst = connection.prepareStatement(sql);
		pst.setObject(1, cancion.getNombre());
		pst.setObject(2, cancion.getArtista());
		pst.setObject(3, cancion.getGenero());
		pst.setObject(4, cancion.getRuta());


		updated = pst.executeUpdate()>0;
		pst.close();


		return updated;
	}

	/* ----------------------------------FUNCIONES PARA EL ADMINISTRADOR------------------------------------- */
	
	
	/**
	 * Funcion que rellena la tabla de usuarios
	 * @param filtro tipo de filtro usado
	 * @param paramFil usuario a buscar
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<Usuario> rellenaTablaUsu(String filtro, String paramFil) throws SQLException{
		int id;
		String nickname, password, correo, sql;
		ObservableList<Usuario> listaUsu = FXCollections.observableArrayList();

		st = connection.createStatement();
		if(filtro == null) {
			sql =  "SELECT ID, NICKNAME, CONTRASEÑA, CORREO FROM USUARIO WHERE ID != 1 ORDER BY ID";
		}
		else if (filtro.equalsIgnoreCase("eliminados")){
			sql =  "SELECT ID, NICKNAME, CONTRASEÑA, CORREO FROM GESTION_ADMIN ORDER BY ID";
		}
		else {
			sql =  "SELECT ID, NICKNAME, CONTRASEÑA, CORREO FROM USUARIO WHERE UPPER("+ filtro + ") LIKE UPPER('%" + paramFil +"%') AND ID != 1 ORDER BY ID";
		}

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			id = rs.getInt("ID");
			nickname = rs.getString("NICKNAME");
			password = rs.getString("CONTRASEÑA");
			correo = rs.getString("CORREO");

			Usuario usu = new Usuario(nickname, password, correo);
			usu.setID_Usuario(id);
			listaUsu.add(usu);

		}

		return listaUsu;
	}


	/**
	 * Funcion que muestra los juegos en la pagina admin
	 * @param id_user id del usuario
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<Juego> adminListGames(int id_user) throws SQLException{ 
		ObservableList<Juego> listaJuegos = FXCollections.observableArrayList();
		String titulo, plataforma, portada, resumen, genero, terminado, empresa, favorito;
		int id_Juego, id_usuario, ano_lanz;
		int hJugadas;

		st = connection.createStatement();
		String sql =  "SELECT ID_JUEGO, ID_USUARIO, TITULO, GENERO, PLATAFORMA, TERMINADO, FAVORITO, LANZAMIENTO, H_JUGADAS, PORTADA, RESUMEN, EMPRESA FROM JUEGOS WHERE ID_USUARIO = "+id_user;

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			id_Juego = rs.getInt("ID_JUEGO");
			id_usuario = rs.getInt("ID_USUARIO");
			titulo = rs.getString("TITULO");
			genero = rs.getString("GENERO");
			plataforma = rs.getString("PLATAFORMA");
			terminado = rs.getString("TERMINADO");
			ano_lanz = rs.getInt("LANZAMIENTO");
			hJugadas = rs.getInt("H_JUGADAS");
			portada = rs.getString("PORTADA");
			resumen = rs.getString("RESUMEN");
			empresa = rs.getString("EMPRESA");
			favorito = rs.getString("FAVORITO");


			Juego juego = new Juego(id_Juego, id_usuario, titulo, genero, plataforma, hJugadas, ano_lanz, terminado, portada, resumen, empresa);
			juego.setFavorito(favorito);
			listaJuegos.add(juego);
		}
		st.close();

		return listaJuegos;
	}


	/**
	 * Funcion que muestra la lista de libros en la pagina admin
	 * @param id_user id del usuario
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<Libro> adminListBooks(int id_user) throws SQLException{ 
		ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
		String titulo, autor, portada, resumen, genero, terminado, favorito;
		int id_libro, id_usuario, n_pags, ano_lanz;

		st = connection.createStatement();
		String sql =  "SELECT ID_LIBRO, ID_USUARIO, TITULO, AUTOR, GENERO, N_PAGINAS, ANO_LANZ, TERMINADO, FAVORITO, PORTADA, RESUMEN FROM LIBROS WHERE ID_USUARIO = "+id_user;

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			titulo = rs.getString("TITULO");
			autor = rs.getString("AUTOR");
			portada = rs.getString("PORTADA");
			genero = rs.getString("GENERO");
			resumen = rs.getString("RESUMEN");
			terminado = rs.getString("TERMINADO");
			n_pags = rs.getInt("N_PAGINAS");
			ano_lanz = rs.getInt("ANO_LANZ");
			id_libro = rs.getInt("ID_LIBRO");
			id_usuario = rs.getInt("ID_USUARIO");
			favorito = rs.getString("FAVORITO");

			Libro lib = new Libro(id_libro, id_usuario, titulo, genero, autor, n_pags, ano_lanz, terminado, portada, resumen);
			lib.setFavorito(favorito);
			listaLibros.add(lib);
		}
		st.close();

		return listaLibros;
	}
	
	/**
	 * Funcion que muestra las canciones del usuario seleccionado.
	 * @param id_user. ID del usuario seleccionado
	 * @return ObservableList .Lista de canciones 
	 * @throws SQLException
	 */
	public static ObservableList<Cancion> adminListSongs(int id_user) throws SQLException{ 
		ObservableList<Cancion> listaCanciones = FXCollections.observableArrayList();
		String titulo, artista, ruta, genero;
		int id_cancion, id_usuario;

		st = connection.createStatement();
		String sql =  "SELECT ID_CANCION, ID_USUARIO, TITULO, ARTISTA, GENERO, RUTA FROM CANCIONES WHERE ID_USUARIO = "+id_user;

		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			id_cancion = rs.getInt("ID_CANCION");
			id_usuario = rs.getInt("ID_USUARIO");
			titulo = rs.getString("TITULO");
			artista = rs.getString("ARTISTA");
			genero = rs.getString("GENERO");
			ruta = rs.getString("RUTA");

			Cancion cancion = new Cancion(id_cancion, id_usuario, titulo, artista, genero, ruta);
			listaCanciones.add(cancion);
		}
		st.close();

		return listaCanciones;
	}

	
	/**
	 * Funcion que muestra el total de usuarios activos
	 * @return int
	 * @throws SQLException
	 */
	public static int usuTotales() throws SQLException {
		int total = 0;
		st = connection.createStatement();
		String sql =  "SELECT COUNT(*) total FROM USUARIO";
		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			total = rs.getInt("total");


		}
		st.close();

		return total;

	}

	/**
	 * Funcion que muestra el total de autores
	 * @return int
	 * @throws SQLException
	 */
	public static int autoresTotales() throws SQLException {
		int total = 0;
		st = connection.createStatement();
		String sql =  "SELECT DISTINCT COUNT(AUTOR) FROM LIBROS";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			total += rs.getInt("COUNT(AUTOR)");
		}
		
		st = connection.createStatement();
		sql =  "SELECT DISTINCT COUNT(ARTISTA) FROM CANCIONES";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			total += rs.getInt("COUNT(ARTISTA)");
		}
		
		st.close();

		return total;

	}

	
	/**
	 * Funcion que muestra el uso medio de todos los usuarios que la han usado alguna vez
	 * @return String Uso medio de la app
	 * @throws SQLException
	 */
	public static String usoMedio() throws SQLException { //DEVUELVE EL TIEMPO MEDIO QUE SE HA USADO LA APLICACION
		String tiempo = "";
		int minutos = 0;
		int horas = 0;
		int dias = 0;
		
		st = connection.createStatement();
		String sql =  "SELECT ROUND(AVG(USO_TOTAL)) FROM TIEMPO_USUARIO WHERE USO_TOTAL != 0";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			minutos = rs.getInt("ROUND(AVG(USO_TOTAL))");
		}
		
		if (minutos >= 60) {
	        horas = minutos / 60;
	        minutos %= 60;
	        if (horas >= 24) {
	            dias = horas / 24;
	            horas %= 24;
	            tiempo = dias+" d "+ horas+ " h "+ minutos+ " m";
	        } else {
	            tiempo = horas+ " h "+ minutos+" m";
	        }
	    } else {
	        tiempo = minutos+" m";
	    }
		
		
		
		return tiempo;
	}

	
	/**
	 * Funcion que muestra todos los usuarios borrados
	 * @return int
	 * @throws SQLException
	 */
	public static int usuBorrados() throws SQLException {
		int total = 0;
		st = connection.createStatement();
		String sql =  "SELECT COUNT(ID) FROM GESTION_ADMIN";
		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			total = rs.getInt("COUNT(ID)");
		}
		st.close();

		return total;
	}

	
	/**
	 * Funcion que muestra el total de elementos que hay
	 * @return int
	 * @throws SQLException
	 */
	public static int totalElementos() throws SQLException {
		int total = 0;
		st = connection.createStatement();
		String sql =  "SELECT COUNT(*) FROM LIBROS WHERE ID_USUARIO IS NOT null";
		ResultSet rs = st.executeQuery(sql);
		while(rs.next()) {
			total += rs.getInt("COUNT(*)");
		}
		
		st = connection.createStatement();
		sql =  "SELECT COUNT(*) FROM JUEGOS WHERE ID_USUARIO IS NOT null";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			total += rs.getInt("COUNT(*)");
		}

		st = connection.createStatement();
		sql =  "SELECT COUNT(*) FROM CANCIONES WHERE ID_USUARIO IS NOT null";
		rs = st.executeQuery(sql);
		while(rs.next()) {
			total += rs.getInt("COUNT(*)");
		}

		st.close();

		return total;
	}


	/**
	 * Funcion que devuelve el ultimo usuario registrado en la ultima semana
	 * @return int
	 * @throws SQLException
	 */
	public static int ultimosUsu() throws SQLException{ /*DEVUELVE EL NUMERO DE USUARIOS REGISTRADOS EN LA ULTIMA SEMANA*/
		int total = 0;
		st = connection.createStatement();
		String sql =  "SELECT COUNT(ID) FROM USUARIO WHERE SYSDATE - FECHA <= 7";
		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			total = rs.getInt("COUNT(ID)");
		}
		st.close();

		return total;
	}
	
	/**
	 * Funcion que restaura el usuario eliminado
	 * @param u Objeto de tipo usuario
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean restauraUsu(Usuario u) throws SQLException{ //ELIMINA EL USUARIO DE GESTION_ADMIN Y CON EL TRIGGER LO INSERTA EN USUARIO
		boolean restaurado = false;

		String sql= "DELETE FROM GESTION_ADMIN WHERE ID = "+u.getID_Usuario();
		pst = connection.prepareStatement(sql);
		restaurado = pst.executeUpdate()>0;
		pst.close();

		return restaurado;
	}

	
	/*ADMIN GRAFICOS*/

	/**
	 * Funcion que muestra la grafica de libros
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<BarChart.Data> graficaLibros() throws SQLException {
		ObservableList<BarChart.Data> datos = FXCollections.observableArrayList();
		String nickname, sql;
		int num;

		st = connection.createStatement();
		sql =  "SELECT USUARIO.NICKNAME, COUNT(ID_LIBRO) FROM LIBROS JOIN USUARIO ON LIBROS.ID_USUARIO = USUARIO.ID GROUP BY USUARIO.NICKNAME ";
		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			nickname = rs.getString("NICKNAME");
			num = rs.getInt("COUNT(ID_LIBRO)");
			datos.add(new BarChart.Data(nickname, num));

		}
		st.close();
		return datos;
	}

	
	/**
	 * Funcion que muestra la grafica de juegos
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<BarChart.Data> graficaJuegos() throws SQLException {
		ObservableList<BarChart.Data> datos = FXCollections.observableArrayList();
		String nickname, sql;
		int num;

		st = connection.createStatement();
		sql =  "SELECT USUARIO.NICKNAME, COUNT(ID_JUEGO) FROM JUEGOS JOIN USUARIO ON JUEGOS.ID_USUARIO = USUARIO.ID GROUP BY USUARIO.NICKNAME ";
		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			nickname = rs.getString("NICKNAME");
			num = rs.getInt("COUNT(ID_JUEGO)");
			datos.add(new BarChart.Data(nickname, num));

		}
		st.close();
		return datos;
	}

	/**
	 * Funcion que muestra la grafica de Musica
	 * @return ObservableList
	 * @throws SQLException
	 */
	public static ObservableList<BarChart.Data> graficaMusica() throws SQLException {
		ObservableList<BarChart.Data> datos = FXCollections.observableArrayList();
		String nickname, sql;
		int num;

		st = connection.createStatement();
		sql =  "SELECT USUARIO.NICKNAME, COUNT(ID_CANCION) FROM CANCIONES JOIN USUARIO ON CANCIONES.ID_USUARIO = USUARIO.ID GROUP BY USUARIO.NICKNAME ";
		ResultSet rs = st.executeQuery(sql);

		while(rs.next()) {
			nickname = rs.getString("NICKNAME");
			num = rs.getInt("COUNT(ID_CANCION)");
			datos.add(new BarChart.Data(nickname, num));

		}
		st.close();
		return datos;
	}





	//CIERRE CONEXION
	/**
	 * Funcion que cierra la conexion con la base de datos
	 * @return boolean
	 * @throws SQLException
	 */
	public static boolean cerrar() throws SQLException {
		boolean cerrado = false;
		if(rs!=null) {
			rs.close();
		}
		if(st!=null) {
			st.close();
		}
		if(connection!=null) {
			connection.close();
			if(connection.isClosed()) {
				cerrado = true;
				System.out.println("Conexion Cerrada correctamente");
			}
		}
		return cerrado;
	}








}
