package utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
	static String contraseña;
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
		pst.setString(3, u.getContraseña());
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
			contraseña = rs.getString("contraseña");
			if(nickname.equals(nombre) && contraseña.equals(password)) {

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
