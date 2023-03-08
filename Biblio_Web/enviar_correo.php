<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $to = 'probibliotech@gmail.com';
    $subject = 'Mensaje enviado desde el sitio web';
    $message = 'Nombre: ' . $_POST['userName'] . "\n" .
               'Apellido: ' . $_POST['userSurname'] . "\n" .
               'Correo Electrónico: ' . $_POST['userEmail'] . "\n" .
               'Mensaje: ' . $_POST['userMessage'] . "\n";
    $headers = 'From: ' . $_POST['userEmail'];

    if (mail($to, $subject, $message, $headers)) {
        echo 'El mensaje se ha enviado correctamente.';
    } else {
        echo 'Ha ocurrido un error al enviar el mensaje.';
    }
}
?>
