package utilidades;

import java.util.Properties;

//imports:

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/***
 * Esta clase envia un correo de confirmacion con los credenciales de la cuenta creada y abre URLs.
 * @author BiblioTech
 *
 */

public class Correo {

	
	
	/**
	 * Esta es la funcion que envia un mail al correo introducido en el apartado registro.
	 * @param destinatario
	 * @param nickname
	 * @param password
	 * @return Mensaje de que el correo se haya enviado correctamente
	 */
	
	public static String enviarMailConf(String destinatario, String nickname, String password) { //DEVUELVE UN MENSAJE EN FUNCIÓN DE SI SE HA ENVIADO O NO
		String remitente = "probibliotech"; //lo que va antes de @gmail.com
		String clave = "imxjsectmdojtian"; //GOOGLE desactivó en Mayo de 2022 el acceso a cuentas desde aplicaciones poco seguras. Hemos tenido que activar la verif. 2 pasos y añadir contraseña del correo para aplicación externa (Copiar desde generar contraseña para dispositivos (conf GMAIL))
		String asunto = "Confirmación de cuenta Bibliotech";
		String cuerpo = "Enhorabuena, su cuenta ha sido creada correctamente\n\n\nCorreo electrónico: "+destinatario+"\nNombre de usuario: "+nickname+"\nContraseña: "+password;
		
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.user", remitente);
		props.put("mail.smtp.clave", clave); //La clave de la cuenta
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");

		

		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.setProperty("mail.smtp.socketFactory.fallback", "false");
		props.setProperty("mail.smtp.port", "465");
		props.setProperty("mail.smtp.socketFactory.port", "465");


		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(remitente));
			message.addRecipients(Message.RecipientType.TO, destinatario);
			message.setSubject(asunto);
			message.setText(cuerpo);
			Transport transport = session.getTransport("smtp");
			transport.connect("smtp.gmail.com", remitente, clave);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			return "Correo enviado correctamente";
		}
		catch (MessagingException me) {
			//me.printStackTrace();
			return "Correo no enviado";
		}
	}

	/***
	 * Esta funcion abre la URL indicada en el navegador por defecto que tenga el sistema
	 * @param url
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static void abrirURL(String url) throws IOException, URISyntaxException { //ABRE UN ENLACE WEB DESDE LA APLICACION
	    if (Desktop.isDesktopSupported()) {
	        Desktop desktop = Desktop.getDesktop();
	            desktop.browse(new URI(url));
	    } else {
	       System.out.println("No se puede abrir el navegador");
	    }
	}
	
}
