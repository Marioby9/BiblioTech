/*SCRIPT BIBLIOTECH TOMAS, MARIO, RAUL 2023*/
/*
drop table Usuario;
drop table Bibliotecas;
drop table Juegos;
drop table Libros;
drop table Canciones;
drop table Ajustes;

SELECT * FROM USUARIO ORDER BY ID;
DELETE FROM USUARIO;
COMMIT; 
*/

create table Usuario 
(
ID          number(3),
Nickname    varchar2(30) UNIQUE Not Null,
Contrase�a  varchar2(30) Not Null,
Correo varchar(40) Not Null,
Fecha date default sysdate,
constraint Pk_Usuarioid Primary key(ID)
);


create table Bibliotecas
(
Cod_Biblioteca number(3),
ID_biblio number(3),
ID_Usuario  number(3) references Usuario on delete cascade,
Nombre      varchar2(25) Not Null,
constraint Pk_Bibliotecas Primary key(ID_biblio, ID_usuario)
);


create table Juegos
(
ID_Juego number(3),
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Genero      varchar2(30) Not Null,
Plataforma  varchar2(25) Not Null,
Empresa varchar2(20),
H_jugadas   number(7,2),
Lanzamiento number(4),
Terminado   varchar2(10) Not Null,
Favorito varchar2(2) default 'NO',
Portada varchar2(99),
Resumen CLOB,


constraint Pk_Juegos Primary key(ID_Juego),
constraint CK_terminado Check(Terminado in ('SI','NO'))
);

INSERT INTO JUEGOS (id_juego,  Titulo, Genero, Plataforma, H_jugadas,Lanzamiento, Terminado, Portada) VALUES(001,  'GOD OF WAR','ACCION', 'PS3', 40, 2018, 'NO', '/caratulas/god-of-war.png'); 
INSERT INTO JUEGOS (id_juego,  Titulo,  Genero,  Plataforma, H_jugadas, Lanzamiento, Terminado, Portada)  VALUES(002,  'ASSASINS CREED UNITY','ACCION', 'PS2', 40, 2005, 'NO', '/caratulas/assasins-creed.png');  
INSERT INTO JUEGOS (id_juego,  Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado, Portada)  VALUES(003, 'UNCHARTED','ACCION', 'PS3', 40, 2007, 'NO', '/caratulas/uncharted.png');  
INSERT INTO JUEGOS (id_juego,  Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado, Portada)  VALUES(004,  'THE LAST OF US','ACCION', 'PS3', 20, 2013, 'NO', '/caratulas/the-last-of-us.png');  

INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, H_jugadas,Lanzamiento, Terminado, Portada)  VALUES(005,  'CALL OF DUTY BO2','SHOOTER', 'XBOX', 10, 2012, 'NO', '/caratulas/black-ops-2.png'); 
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(006,  'FORTNITE','SHOOTER', 'PS4', 2017, 'NO', '/caratulas/fortnite.png');  
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(007,  'CSGO','SHOOTER', 'PC', 2007, 'NO', '/caratulas/csgo.png');  
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, H_jugadas,Lanzamiento, Terminado, Portada)  VALUES(008,  'BATTLEFIELD','SHOOTER', 'PS4', 30, 2016, 'NO', '/caratulas/battlefield.png');  

INSERT INTO JUEGOS  (id_juego, Titulo, Genero, Plataforma,Lanzamiento, Terminado, Portada) VALUES(009,  'FIFA 23','DEPORTES', 'PC', 2022, 'NO', '/caratulas/fifa.png'); 
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma,Lanzamiento, Terminado, Portada)  VALUES(010,  'WII SPORTS','DEPORTES', 'WII', 2006, 'NO', '/caratulas/wii-sports.png');  
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada)  VALUES(011,  'F1 2023','DEPORTES', 'PC', 2022, 'NO', '/caratulas/formula1.png');  
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada)  VALUES(012,  'NBA 2K23','DEPORTES', 'PS5', 2022, 'NO', '/caratulas/nba-2k23.png');  

INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada)  VALUES(0013,  'SILENT HILL 2 ','TERROR', 'PS2', 2003, 'NO', '/caratulas/silent-hill-2.png'); 
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(014,  'RESIDENT EVIL 5','TERROR', 'PC', 2009, 'NO', '/caratulas/resident-evil-5.png');  
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(015,  'OUTLAST','TERROR', 'PC', 2013, 'NO', '/caratulas/outlast.png');  
INSERT INTO JUEGOS  (id_juego,  Titulo, Genero, Plataforma,Lanzamiento, Terminado, Portada)  VALUES(016,  'VISAGE','TERROR', 'PC', 2020, 'NO', '/caratulas/visage.png');  


create table Libros
(
ID_Libro number(3) ,
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Genero      varchar2(30) Not Null,
N_paginas   number(4) Not Null,
Autor       varchar2(30) default 'An�nimo' Not Null,
Ano_Lanz  number(4) not null,
Terminado   varchar2(10) Not Null,
Favorito varchar2(2) default 'NO',
Portada varchar2(99),
Resumen CLOB,

constraint Pk_Libros Primary key(ID_Libro),
constraint CK_terminado3 Check(Terminado in ('SI','NO'))
);



INSERT INTO LIBROS  (id_libro, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (001, 'IT','TERROR', 1504, 'STEPHEN KING',1986, 'NO', '/caratulas/it.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (002,  'EL EXORCISTA','TERROR', 376, 'WILLIAM PETER BLATTY', 1971,  'NO', '/caratulas/exorcista.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (003,  'DRACULA','TERROR', 418, 'BRAM STOKER', 1897, 'NO', '/caratulas/dracula.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (004,  'FRANKENSTEIN','TERROR', 136, 'MARY SHELLEY',1818, 'NO', '/caratulas/frankenstein.png');  

INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (005,  'LA ISLA DEL TESORO','AVENTURAS', 283, 'ROBERT LOUIS STEVENSON',1883, 'NO', '/caratulas/isla-del-tesoro.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (006,   'ROBINSON CRUSOE','AVENTURAS', 304, 'DANIEL DEFOE', 1719,  'NO', '/caratulas/robinson-crusoe.png');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (007,  'EL CORREDOR DEL LABERINTO', 'AVENTURAS', 376, 'JAMES DASHNER',2009, 'NO', '/caratulas/corredor-del-laberinto.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (0008,  'VIAJE AL CENTRO DE LA TIERRA','AVENTURAS', 330, 'JULIO VERNE', 1862, 'NO', '/caratulas/viaje-al-centro-de-la-tierra.png');  

INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (009,  'ORGULLO Y PREJUICIO','AMOR', 424, 'JANE AUSTEN',1813, 'NO', '/caratulas/orgullo-y-prejuicio.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (010,   'BAJO LA MISMA ESTRELLA','AMOR', 301, 'JOHN GREEN', 2012,  'NO', '/caratulas/bajo-la-misma-estrella.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (011,  'TRES METROS SOBRE EL CIELO','AMOR', 390, 'FEDERICO MOCCIA Y KONY', 1992, 'NO', '/caratulas/a-tres-metros-sobre-el-cielo.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (012,  'CIUDADES DE PAPEL', 'AMOR', 368, 'JOHN GREEN', 2008, 'NO', '/caratulas/ciudades-de-papel.png');  

INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (013,  'SIN NOTICIAS DE GURB', 'COMEDIA', 144, 'EDUARDO MENDOZA', 2018, 'NO', '/caratulas/sin-noticias-de-gurb.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (014,   'BARTLEBY EL ESCRIBIENTE', 'COMEDIA', 112, 'HERMAN MELVILLE', 1853,  'NO', '/caratulas/bartleby-el-escribiente.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (015,  'DIARIO DE GREG', 'COMEDIA', 240, 'JEFF KINNEY', 2007, 'NO', '/caratulas/diario-de-greg.png');  
INSERT INTO LIBROS (id_libro,  Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (016,  'MORTADELO Y FILEMON',  'COMEDIA', 82, 'FRANCISCO IBA�EZ', 1958, 'NO', '/caratulas/mortadelo-y-filemon.png');  


create table Canciones
(
ID_Cancion number(3),
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Artista     varchar2(30) Not Null,
Genero      varchar2(30) Not Null,
Favorito varchar2(2),
Portada varchar2(500),
Ruta    varchar2(50) Not Null,

constraint Pk_Cancion Primary key(ID_Cancion)
);

INSERT INTO CANCIONES (ID_CANCION, TITULO, ARTISTA, GENERO, RUTA)  VALUES (1,'Shakira-Bzrp',  'Shakira',  'Reggaeton',  '/canciones/shakira-bzrp.mp3');  
INSERT INTO CANCIONES (ID_CANCION, TITULO, ARTISTA, GENERO, RUTA)  VALUES (2,'Traductor',  'Myke Towers',  'Reggaeton',  '/canciones/mykeTowers-Traductor.mp3');  

create table Ajustes
(
ID_Usuario number(3) references Usuario on delete cascade,
Fperfil number  default 0,
Volumen number default 50, 
Cfondo number default 0,
Carp_Musica varchar2(99),

constraint Pk_Ajustes Primary key(ID_Usuario),
constraint ck_fPerfil check (Fperfil between 0 and 9),
constraint ck_Volumen check (Volumen between 0 and 100),
constraint ck_Cfondo check (Cfondo between 0 and 4)
);

create table GESTION_ADMIN(  /*EN ESTA TABLA SE ALMACENAN LOS USUARIOS ELIMINADOS*/
    ID number,
    Nickname    varchar2(30) UNIQUE Not Null,
    Contrase�a  varchar2(30) Not Null,
    Correo varchar(40) Not Null,
    Fecha date default sysdate,
    constraint Pk_GestionAdmin Primary key(ID)
);


CREATE OR REPLACE TRIGGER USER_ELIMINADO
AFTER DELETE
ON USUARIO
FOR EACH ROW

BEGIN 
    INSERT INTO GESTION_ADMIN VALUES (:OLD.ID, :OLD.NICKNAME, :OLD.CONTRASE�A, :OLD.CORREO, SYSDATE);
END;


--A�ADIMOS RESUMENES JUEGOS: 

UPDATE JUEGOS  SET  RESUMEN =  'God of War es un juego de acci�n y aventura desarrollado por Santa Monica Studio. El juego sigue al personaje principal, Kratos, mientras viaja a trav�s de la mitolog�a n�rdica con su hijo Atreus. El juego es conocido por su jugabilidad intensa, su historia emocionante y sus gr�ficos impresionantes.' WHERE ID_JUEGO = 001;

UPDATE JUEGOS  SET  RESUMEN =  'Assassins Creed Unity es un juego de acci�n y aventura en tercera personadesarrollado por Ubisoft. El juego est� ambientado en la Revoluci�n Francesa y sigue al personaje principal, Arno Dorian, mientras lucha contra la Orden de los Templarios. El juego es conocido por su recreaci�n detallada de Par�s y su jugabilidad de sigilo y combate.' WHERE ID_JUEGO = 002;

UPDATE JUEGOS  SET  RESUMEN =  'Uncharted es una serie de juegos de acci�n y aventura desarrollada por Naughty Dog.Los juegos siguen al personaje principal, Nathan Drake, mientras viaja por todo el mundo en busca de tesoros y artefactos antiguos. Los juegos son conocidos por su narrativa emocionante, sus personajes carism�ticos y su jugabilidad de exploraci�n y tiroteos.' WHERE ID_JUEGO = 003;

UPDATE JUEGOS  SET  RESUMEN =  'The Last of Us es un juego de acci�n y aventura desarrollado por Naughty Dog. El juego tiene lugar en un mundo post-apocal�ptico en el que la mayor�a de la poblaci�n ha sido infectada por un hongo que los convierte en monstruos. El juego sigue al personaje principal, Joel, mientras lucha por sobrevivir y proteger a una joven llamada Ellie. El juego es conocido por su narrativa emocional y su jugabilidad de sigilo y supervivencia.' WHERE ID_JUEGO = 004;

UPDATE JUEGOS  SET  RESUMEN =  'Call of Duty: Black Ops 2 es un juego de disparos en primera persona que se desarrolla en el futuro y en la Guerra Fr�a. Los jugadores pueden jugar en una variedad de modos de juego, incluyendo la campa�a para un jugador, el modo multijugador y el modo zombis. Los jugadores tambi�n pueden personalizar sus armas y personajes.' WHERE ID_JUEGO = 005;

UPDATE JUEGOS  SET  RESUMEN =  'Fortnite es un juego de disparos en tercera persona que combina elementos de construcci�n y supervivencia. Los jugadores aterrizan en una isla y deben recolectar recursos y armas para construir fortificaciones y luchar contra otros jugadores. El �ltimo jugador o equipo que quede en pie gana.' WHERE ID_JUEGO = 006;

UPDATE JUEGOS  SET  RESUMEN =  'Counter-Strike: Global Offensive (CSGO) es un juego de disparos en primera persona que se juega en equipos de terroristas y antiterroristas. Los jugadores pueden elegir entre diferentes armas y trabajan juntos para cumplir objetivos, como plantar o desactivar una bomba o rescatar o retener rehenes.' WHERE ID_JUEGO = 007;

UPDATE JUEGOS  SET  RESUMEN =  'Battlefield es una serie de juegos de disparos en primera persona que se enfoca en combates en grandes escenarios con veh�culos y equipos militares. Los jugadores pueden elegir entre diferentes clases de personajes con habilidades �nicas y trabajar en equipo para cumplir objetivos.' WHERE ID_JUEGO = 008;

UPDATE JUEGOS  SET  RESUMEN =  'FIFA es una serie de videojuegos de f�tbol desarrollados por EA Sports. Los juegos permiten a los jugadores controlar equipos y competir en partidos de f�tbol. FIFA incluye modos de juego como carreras de temporada, torneos y partidos amistosos en l�nea y fuera de l�nea. Adem�s, la serie tambi�n cuenta con licencias oficiales de equipos y ligas de f�tbol de todo el mundo, lo que le permite a los jugadores jugar como sus equipos y jugadores favoritos. FIFA es uno de los juegos de deportes m�s populares en todo el mundo y ha sido aclamado por sus gr�ficos, jugabilidad y modo multijugador.' WHERE ID_JUEGO = 009;

UPDATE JUEGOS  SET  RESUMEN =  'Wii Esports es una plataforma de juegos en l�nea que permite a los jugadores competir en una variedad de juegos en la consola Wii de Nintendo, incluyendo deportes, carreras y juegos de lucha. Los jugadores pueden participar en torneos en l�nea y clasificatorios para ganar premios y reconocimiento. La plataforma tambi�n cuenta con funciones sociales y de comunidad, lo que permite a los jugadores conectarse y comunicarse con otros jugadores de todo el mundo. Wii Esports ha sido popular entre los jugadores casuales y competitivos desde su lanzamiento en 2006.' WHERE ID_JUEGO = 010;

UPDATE JUEGOS  SET  RESUMEN =  'La F�rmula 1 es un deporte de carreras de alta velocidad que se celebra en circuitos de todo el mundo. Los equipos compiten con coches dise�ados espec�ficamente para alcanzar velocidades extremadamente altas en rectas largas y curvas cerradas. El campeonato mundial se lleva a cabo anualmente y consta de una serie de carreras que otorgan puntos a los equipos y pilotos. La F�rmula 1 es conocida por ser uno de los deportes m�s exigentes f�sica y mentalmente, y los pilotos deben ser altamente capacitados para enfrentar las demandas del deporte. Tambi�n es un deporte con una gran cantidad de tecnolog�a y ciencia involucradas en el dise�o y construcci�n de los coches.' WHERE ID_JUEGO = 011;

UPDATE JUEGOS  SET  RESUMEN =  'NBA 2K23 es un videojuego de simulaci�n de baloncesto desarrollado por Visual Concepts y publicado por 2K Sports. El juego cuenta con equipos y jugadores de la NBA, as� como de otras ligas internacionales. Los jugadores pueden crear su propio personaje y jugar en la NBA, ganar partidos, mejorar habilidades y competir en l�nea contra otros jugadores. El juego cuenta con gr�ficos mejorados y nuevas caracter�sticas, incluyendo nuevos modos de juego y una banda sonora curada por la estrella del hip-hop, Dr. Dre.' WHERE ID_JUEGO = 012;

UPDATE JUEGOS  SET  RESUMEN =  'Silent Hill es un juego de terror psicol�gico en el que el jugador asume el papel de Harry Mason, un padre que busca a su hija desaparecida en la ciudad de Silent Hill. El juego cuenta con un ambiente opresivo y una trama que se desarrolla a trav�s de pistas y puzzles.' WHERE ID_JUEGO = 013;

UPDATE JUEGOS  SET  RESUMEN =  'Resident Evil 5 es un juego de acci�n y terror en el que el jugador asume el papel de Chris Redfield, un agente de la BSAA que debe investigar una aldea africana infestada de zombis. El juego cuenta con un modo cooperativo para dos jugadores y tiene un enfoque en la acci�n y la resoluci�n de puzzles.' WHERE ID_JUEGO = 014;

UPDATE JUEGOS  SET  RESUMEN =  'Outlast es un juego de terror en el que el jugador asume el papel de un periodista que investiga un hospital psiqui�trico abandonado. El juego se centra en la supervivencia y en el sigilo, ya que el jugador no tiene armas y debe esconderse de los enemigos.' WHERE ID_JUEGO = 015;

UPDATE JUEGOS  SET  RESUMEN =  'Visage es un juego de terror psicol�gico en el que el jugador asume el papel de una persona que est� atrapada en una casa encantada. El juego cuenta con un ambiente opresivo y una trama que se desarrolla a trav�s de pistas y puzzles, adem�s de una mec�nica de juego de terror basada en la evasi�n y la ocultaci�n.' WHERE ID_JUEGO = 016;



--A�ADIMOS RESUMENES LIBROS: 
UPDATE LIBROS  SET  RESUMEN =  'IT, la novela de Stephen King cuenta la historia de un grupo de amigos que se enfrentan a una entidad mal�vola que se alimenta del miedo y la violencia en su peque�a ciudad de Derry, Maine. La criatura, que puede adoptar diferentes formas, los persigue desde su infancia hasta su edad adulta, y deben unirse para destruirla de una vez por todas.' WHERE ID_LIBRO = 001;

UPDATE LIBROS  SET  RESUMEN =  'EL EXORCISTA, la novela de William Peter Blatty cuenta la historia de Regan, una ni�a de 12 a�os que es pose�da por un demonio. Desesperada, su madre busca ayuda de dos sacerdotes, quienes se enfrentan al mal�volo ser en una batalla espiritual que pone en peligro sus propias vidas.' WHERE ID_LIBRO = 002;

UPDATE LIBROS  SET  RESUMEN =  'DRACULA, el libro de Bram Stoker cuenta la historia del Conde Dr�cula, un vampiro que viaja desde Transilvania a Inglaterra para buscar nuevas v�ctimas y extender su reinado de terror. A trav�s de diferentes narradores, se describe la lucha para detener al monstruo, liderada por el profesor Van Helsing y un grupo de valientes hombres y mujeres.' WHERE ID_LIBRO = 003;

UPDATE LIBROS  SET  RESUMEN =  'FRANKENSTEIN, la obra de Mary Shelley cuenta la historia del cient�fico Victor Frankenstein, quien crea una criatura a partir de restos humanos. Sin embargo, su creaci�n resulta ser un monstruo que no puede controlar y que causa terror y muerte en su entorno.' WHERE ID_LIBRO = 004;

UPDATE LIBROS  SET  RESUMEN =  '"La isla del tesoro" de Robert Louis Stevenson: Este cl�sico de aventuras sigue la historia de Jim Hawkins, un joven que se une a una tripulaci�n en busca de un tesoro pirata en una isla misteriosa. En el camino, enfrentan peligros y traiciones, mientras Jim aprende a confiar en s� mismo y en sus instintos.' WHERE ID_LIBRO = 005;

UPDATE LIBROS  SET  RESUMEN =  '"Robinson Crusoe" de Daniel Defoe: La historia sigue las aventuras de Robinson Crusoe, un hombre que naufraga en una isla desierta y lucha por sobrevivir. Crusoe aprende a construir su propia casa, cultivar alimentos y cazar para sobrevivir, mientras enfrenta la soledad y la desesperaci�n.' WHERE ID_LIBRO = 006;

UPDATE LIBROS  SET  RESUMEN =  '"El corredor del laberinto" de James Dashner: La historia se centra en un grupo de j�venes atrapados en un laberinto, con su memoria borrada y sin saber c�mo llegaron all�. Thomas, el personaje principal, trata de descubrir el prop�sito del laberinto y encontrar una salida antes de que sea demasiado tarde.' WHERE ID_LIBRO = 007;

UPDATE LIBROS  SET  RESUMEN =  '"Viaje al centro de la Tierra" de Julio Verne: La historia sigue al profesor Lidenbrock y su sobrino Axel en una expedici�n al centro de la Tierra. A medida que descienden m�s y m�s en el interior de la Tierra, se enfrentan a peligros y descubren maravillas incre�bles en el camino.' WHERE ID_LIBRO = 008;

UPDATE LIBROS  SET  RESUMEN =  '"Orgullo y prejuicio" es una novela del autor brit�nico Jane Austen, publicada en 1813. La novela se desarrolla en la Inglaterra rural del siglo XIX y sigue la historia de Elizabeth Bennet, una joven inteligente y sarc�stica, y su relaci�n con el rico y orgulloso se�or Darcy. La novela aborda temas como la clase social, el matrimonio y el papel de la mujer en la sociedad de la �poca.' WHERE ID_LIBRO = 009;

UPDATE LIBROS  SET  RESUMEN =  '"Bajo la misma estrella" es una novela juvenil del autor estadounidense John Green, publicada en 2012. La historia gira en torno a Hazel, una adolescente con c�ncer de pulm�n, y su relaci�n con Augustus, otro joven enfermo de c�ncer. La novela aborda temas como el amor, la amistad, la muerte y la fragilidad de la vida.' WHERE ID_LIBRO = 010;

UPDATE LIBROS  SET  RESUMEN =  '"A tres metros sobre el cielo" es una novela rom�ntica del autor italiano Federico Moccia, publicada en 1992. Cuenta la historia de amor entre Babi, una chica de clase alta, y Step, un joven de barrio bajo que vive al l�mite. La novela explora temas como la rebeld�a, el primer amor, la familia y la b�squeda de la identidad.' WHERE ID_LIBRO = 011;

UPDATE LIBROS  SET  RESUMEN =  '"Ciudades de papel" es una novela juvenil del autor estadounidense John Green, publicada en 2008. La trama sigue a Quentin, un joven que est� enamorado de su vecina Margo, una chica misteriosa y aventurera. Despu�s de que Margo desaparece, Quentin y sus amigos tratan de seguir sus pistas para encontrarla. La novela explora temas como la amistad, la identidad y la b�squeda de la verdad.' WHERE ID_LIBRO = 012;

UPDATE LIBROS  SET  RESUMEN =  '"Sin noticias de Gurb" de Eduardo Mendoza. El libro cuenta la historia de Gurb, un extraterrestre que se encuentra en la ciudad de Barcelona y desaparece sin dejar rastro. Otro extraterrestre se hace pasar por Gurb mientras intenta encontrarlo, pero enfrenta varios obst�culos y situaciones extra�as en el proceso.' WHERE ID_LIBRO = 013;

UPDATE LIBROS  SET  RESUMEN =  '"Bartleby, el escribiente" de Herman Melville. El libro cuenta la historia de un abogado que contrata a un nuevo empleado llamado Bartleby. Sin embargo, Bartleby comienza a negarse a realizar cualquier tarea y simplemente dice "preferir�a no hacerlo". Con el tiempo, se convierte en una carga para el abogado y comienza a tener un efecto negativo en su vida.' WHERE ID_LIBRO = 014;

UPDATE LIBROS  SET  RESUMEN =  '"Diario de Greg" de Jeff Kinney. El libro es una serie de diarios escritos por Greg Heffley, un estudiante de secundaria que intenta navegar por la vida y la escuela. A lo largo de la serie, Greg enfrenta varios desaf�os, incluyendo problemas con amigos, problemas familiares y dificultades en la escuela.' WHERE ID_LIBRO = 015;

UPDATE LIBROS  SET  RESUMEN =  '"Mortadelo y Filem�n" de Francisco Ib��ez. El libro es una serie de historietas c�micas protagonizadas por dos agentes secretos llamados Mortadelo y Filem�n. Cada historia sigue a los personajes mientras intentan resolver un caso, pero a menudo se encuentran en situaciones rid�culas y divertidas.' WHERE ID_LIBRO = 016;


COMMIT;


SELECT * FROM canciones;
