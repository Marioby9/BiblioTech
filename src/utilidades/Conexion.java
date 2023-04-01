package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import elementos.Juego;
import elementos.Libro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Conexion {

	private static String bd = "XE";
	private static String login = "Bibliotech";
	private static String password = "Bibliotech";
	private static Statement st;
	private static PreparedStatement pst;
	private static ResultSet rs;
	private static String url = "jdbc:oracle:thin:@localhost:1521:"+bd;
	static Connection connection = null;

	static int Id;
	static String contrasena;
	static String nickname;
	static String correo;

	public static void conectar() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			connection = DriverManager.getConnection(url,login,password);
			if(connection != null) {
				System.out.println("Conexion realizada correctamente.");
			}

		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	//CONSULTAS TABLA USUARIO

	public String consultaStr(String tabla, String columna, String condicion) throws SQLException{ //FUNCION PARA HACER CONSULTAS DE NUMEROS
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


	public int consultaNum(String tabla, String columna, String condicion) throws SQLException{ //FUNCION PARA HACER CONSULTAS DE NUMEROS
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


	//REGISTRAR Y ELIMINAR USUARIO 
	public boolean registraUsuario (Usuario u) throws SQLException{ //MODIFICAR
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

		pst.close();
		return insertado;
	}

	public boolean eliminarUsuario (Usuario u) throws SQLException{ //MODIFICAR

		boolean eliminado = false;


		String sql= "DELETE FROM USUARIO WHERE ID = "+u.getID_Usuario() + " AND "+u.getID_Usuario()+" != 1";

		pst = connection.prepareStatement(sql);

		eliminado = pst.executeUpdate()>0;
		pst.close();

		return eliminado;
	}


	//QUERYS DEL LOGIN
	public boolean compruebaLogIn(String nombre, String password) throws SQLException  {  //MODIFICAR

		boolean accede = false;


		st = connection.createStatement();
		ResultSet rs = st.executeQuery("select nickname, contraseña from usuario");

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

	//CAMBIAR VALORES PARA TABLAS
	public boolean updateTabla(String tabla, String columna, Object valor, String condicion) throws SQLException{
		boolean updated = false;


		String sql= "UPDATE "+tabla+" SET "+ columna+ " = ? WHERE " + condicion;
		pst = connection.prepareStatement(sql);
		pst.setObject(1, valor);

		updated = pst.executeUpdate()>0; 

		pst.close();
		return updated;
	}

	//LIBROS:

	public boolean agregaLibro(Libro libro, boolean portada) throws SQLException {
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

	public boolean updateLibro(Libro libro) throws SQLException{ 
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

	public boolean actualizaPortadaLib(Libro libro, boolean portada) throws SQLException {
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
	
	
	
	public boolean eliminaLibro(Libro libro) throws SQLException {
		boolean eliminado = false;

		//LOS LIBROS POR DEFECTO NO SE BORRAN
		String sql= "DELETE FROM LIBROS WHERE ID_LIBRO = "+libro.getID_Libro()+"AND ID_USUARIO IS NOT NULL";

		pst = connection.prepareStatement(sql);

		eliminado = pst.executeUpdate()>0;
		pst.close();

		return eliminado;
	}


	public ObservableList<Libro> fillListBooks(String genero, int id_user) throws SQLException{ 
		ObservableList<Libro> listaLibros = FXCollections.observableArrayList();
		String titulo, autor, portada, resumen, terminado;
		int id_libro, id_usuario, n_pags, ano_lanz;

		st = connection.createStatement();
		String sql =  "SELECT ID_LIBRO, ID_USUARIO, TITULO, AUTOR, GENERO, N_PAGINAS, ANO_LANZ, TERMINADO, PORTADA, RESUMEN FROM LIBROS WHERE UPPER(GENERO) = '" + genero
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

			listaLibros.add(new Libro(id_libro, id_usuario, titulo, genero, autor, n_pags, ano_lanz, terminado, portada, resumen));
		}
		st.close();



		return listaLibros;
	}

	
	//JUEGOS
	
	public boolean agregaJuego(Juego juego, boolean portada) throws SQLException {
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

	public boolean updateJuego(Juego juego) throws SQLException{ 
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

	public boolean actualizaPortadaJue(Juego juego, boolean portada) throws SQLException {
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
	
	public boolean eliminaJuego(Juego juego) throws SQLException {
		boolean eliminado = false;

		//LOS LIBROS POR DEFECTO NO SE BORRAN
		String sql= "DELETE FROM JUEGOS WHERE ID_JUEGO = "+juego.getID_Juego()+" AND ID_USUARIO IS NOT NULL";

		pst = connection.prepareStatement(sql);

		eliminado = pst.executeUpdate()>0;
		pst.close();

		return eliminado;
	}
	

	
	public ObservableList<Juego> fillListGames(String genero, int id_user) throws SQLException{ 
		ObservableList<Juego> listaJuegos = FXCollections.observableArrayList();
		String titulo, plataforma, portada, resumen, terminado, empresa;
		int id_Juego, id_usuario, ano_lanz;
		int hJugadas;

		st = connection.createStatement();
		String sql =  "SELECT ID_JUEGO, ID_USUARIO, TITULO, GENERO, PLATAFORMA, TERMINADO, LANZAMIENTO, H_JUGADAS, PORTADA, RESUMEN, EMPRESA FROM JUEGOS WHERE UPPER(GENERO) = '" + genero
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
			

			listaJuegos.add(new Juego(id_Juego, id_usuario, titulo, genero, plataforma, hJugadas, ano_lanz, terminado, portada, resumen, empresa));
		}
		st.close();



		return listaJuegos;
	}
	

	//CIERRE CONEXION
	public static void cerrar() throws SQLException {

		if(rs!=null) {
			rs.close();
		}
		if(st!=null) {
			st.close();
		}
		if(connection!=null) {
			connection.close();
			if(connection.isClosed()) {
				System.out.println("Conexion Cerrada correctamente");
			}
		}
	}








}