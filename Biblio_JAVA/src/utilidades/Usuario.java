package utilidades;

import java.sql.SQLException;

public class Usuario {
	Conexion c1 = new Conexion();
	private int ID_Usuario;
	private String Nickname;
	private String Correo;
	private String Contrasena;
	public static Usuario uActual;
	
	public Usuario(String nickname, String constrasena, String correo) {
		
			try {
				ID_Usuario = c1.consultaNum("USUARIO", "MAX(ID)", null) +1; //COGE EL ID MAS ALTO QUE HAYA EN LA TABLA Y LE SUMA 1.

			} catch (SQLException e) {
				e.printStackTrace();
			}  
			this.Nickname = nickname;
			this.Contrasena = constrasena;
			this.Correo = correo;
	}

	public  String getNickname() {
		return Nickname;
	}
	public String getCorreo() {
		return Correo;
	}	
	public String getContrasena() {
		return Contrasena;
	}
	public int getID_Usuario() {
		return ID_Usuario;
	}
	
	
	public void setNickname(String nickname) {
		Nickname = nickname;
	}
	public void setCorreo(String correo) {
		Correo = correo;
	}
	public void setContrasena(String contrasena) {
		Contrasena = contrasena;
	}
	
	public static Usuario getUsuario() {
		return uActual;
	}
	public static void  setUsuario(Usuario u) {
		uActual = u;
		
	}

	public void setID_Usuario(int iD_Usuario) {
		ID_Usuario = iD_Usuario;
	}
	
}
