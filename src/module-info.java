
module Proyecto {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires java.logging;
	requires javafx.base;
	requires javafx.media;
	requires java.desktop;
	requires java.sql;
	requires java.mail;
	requires java.xml;
	requires junit;

	
	opens application to javafx.graphics, javafx.fxml;
	opens AdminApp to javafx.graphics, javafx.fxml;
}
