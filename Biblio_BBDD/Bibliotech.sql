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
Contraseņa  varchar2(30) Not Null,
Correo varchar(40) Not Null,
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
nPanel number(1),
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Genero      varchar2(30) Not Null,
Plataforma  varchar2(25) Not Null,
H_jugadas   number(7,2),
Lanzamiento number(4),
Terminado   varchar2(10) Not Null,
Portada varchar2(99),


constraint Pk_Juegos Primary key(ID_Juego),
constraint CK_terminado Check(Terminado in ('SI','NO'))
);

INSERT INTO JUEGOS (id_juego, nPanel, Titulo, Genero, Plataforma, H_jugadas,Lanzamiento, Terminado, Portada) VALUES(001, 1, 'GOD OF WAR','ACCION', 'PS3', 40, 2018, 'NO', '/caratulas/god-of-war.png'); 
INSERT INTO JUEGOS (id_juego, nPanel, Titulo,  Genero,  Plataforma, H_jugadas, Lanzamiento, Terminado, Portada)  VALUES(002, 2, 'ASSASINS CREED UNITY','ACCION', 'PS2', 40, 2005, 'NO', '/caratulas/assasins-creed.png');  
INSERT INTO JUEGOS (id_juego, nPanel, Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado, Portada)  VALUES(003, 3, 'UNCHARTED','ACCION', 'PS3', 40, 2007, 'NO', '/caratulas/uncharted.png');  
INSERT INTO JUEGOS (id_juego, nPanel, Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado, Portada)  VALUES(004, 4, 'THE LAST OF US','ACCION', 'PS3', 20, 2013, 'NO', '/caratulas/the-last-of-us.png');  

INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, H_jugadas,Lanzamiento, Terminado, Portada)  VALUES(005, 1, 'CALL OF DUTY BO2','SHOOTER', 'XBOX', 10, 2012, 'NO', '/caratulas/black-ops-2.png'); 
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(006, 2, 'FORTNITE','SHOOTER', 'PS4', 2017, 'NO', '/caratulas/fortnite.png');  
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(007, 3, 'CSGO','SHOOTER', 'PC', 2007, 'NO', '/caratulas/csgo.png');  
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, H_jugadas,Lanzamiento, Terminado, Portada)  VALUES(008, 4, 'BATTLEFIELD','SHOOTER', 'PS4', 30, 2016, 'NO', '/caratulas/battlefield.png');  

INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma,Lanzamiento, Terminado, Portada) VALUES(009, 1, 'FIFA 23','DEPORTES', 'PC', 2022, 'NO', '/caratulas/fifa.png'); 
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma,Lanzamiento, Terminado, Portada)  VALUES(010, 2, 'WII SPORTS','DEPORTES', 'WII', 2006, 'NO', '/caratulas/wii-sports.png');  
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada)  VALUES(011, 3, 'F1 2023','DEPORTES', 'PC', 2022, 'NO', '/caratulas/formula1.png');  
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada)  VALUES(012, 4, 'NBA 2K23','DEPORTES', 'PS5', 2022, 'NO', '/caratulas/nba-2k23.png');  

INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada)  VALUES(0013, 1, 'SILENT HILL 2 ','TERROR', 'PS2', 2003, 'NO', '/caratulas/silent-hill-2.png'); 
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(014, 2, 'RESIDENT EVIL 5','TERROR', 'PC', 2009, 'NO', '/caratulas/resident-evil-5.png');  
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma, Lanzamiento, Terminado, Portada) VALUES(015, 3, 'OUTLAST','TERROR', 'PC', 2013, 'NO', '/caratulas/outlast.png');  
INSERT INTO JUEGOS  (id_juego, nPanel, Titulo, Genero, Plataforma,Lanzamiento, Terminado, Portada)  VALUES(016, 4, 'VISAGE','TERROR', 'PC', 2020, 'NO', '/caratulas/visage.png');  


create table Libros
(
ID_Libro number(3) ,
nPanel number(1),
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Genero      varchar2(30) Not Null,
N_paginas   number(4) Not Null,
Autor       varchar2(30) default 'Anónimo' Not Null,
Ano_Lanz  number(4) not null,
Terminado   varchar2(10) Not Null,
Portada varchar2(99),

constraint Pk_Libros Primary key(ID_Libro),
constraint CK_terminado3 Check(Terminado in ('SI','NO'))
);

INSERT INTO LIBROS  (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (001, 1, 'IT','TERROR', 1504, 'STEPHEN KING',1986, 'NO', '/caratulas/it.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (002, 2,  'EL EXORCISTA','TERROR', 376, 'WILLIAM PETER BLATTY', 1971,  'NO', '/caratulas/exorcista.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (003, 3, 'DRACULA','TERROR', 418, 'BRAM STOKER', 1897, 'NO', '/caratulas/dracula.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (004, 4, 'FRANKENSTEIN','TERROR', 136, 'MARY SHELLEY',1818, 'NO', '/caratulas/frankenstein.png');  

INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (005, 1, 'LA ISLA DEL TESORO','AVENTURAS', 283, 'ROBERT LOUIS STEVENSON',1883, 'NO', '/caratulas/isla-del-tesoro.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (006, 2,  'ROBINSON CRUSOE','AVENTURAS', 304, 'DANIEL DEFOE', 1719,  'NO', '/caratulas/robinson-crusoe.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (007, 3, 'EL CORREDOR DEL LABERINTO', 'AVENTURAS', 376, 'JAMES DASHNER',2009, 'NO', '/caratulas/corredor-del-laberinto.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (0008, 4, 'VIAJE AL CENTRO DE LA TIERRA','AVENTURAS', 330, 'JULIO VERNE', 1862, 'NO', '/caratulas/viaje-al-centro-de-la-tierra.png');  

INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (009, 1, 'ORGULLO Y PREJUICIO','AMOR', 424, 'JANE AUSTEN',1813, 'NO', '/caratulas/orgullo-y-prejuicio.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (010, 2,  'BAJO LA MISMA ESTRELLA','AMOR', 301, 'JOHN GREEN', 2012,  'NO', '/caratulas/bajo-la-misma-estrella.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (011, 3, 'TRES METROS SOBRE EL CIELO','AMOR', 390, 'FEDERICO MOCCIA Y KONY', 1992, 'NO', '/caratulas/a-tres-metros-sobre-el-cielo.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (012, 4, 'CIUDADES DE PAPEL', 'AMOR', 368, 'JOHN GREEN', 2008, 'NO', '/caratulas/ciudades-de-papel.png');  

INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (013, 1, 'SIN NOTICIAS DE GURB', 'COMEDIA', 144, 'EDUARDO MENDOZA', 2018, 'NO', '/caratulas/sin-noticias-de-gurb.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (014, 2,  'BARTLEBY EL ESCRIBIENTE', 'COMEDIA', 112, 'HERMAN MELVILLE', 1853,  'NO', '/caratulas/bartleby-el-escribiente.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)  VALUES (015, 3, 'DIARIO DE GREG', 'COMEDIA', 240, 'JEFF KINNEY', 2007, 'NO', '/caratulas/diario-de-greg.png');  
INSERT INTO LIBROS (id_libro, nPanel, Titulo, Genero, n_paginas, autor ,ano_lanz, Terminado, Portada)   VALUES (016, 4, 'MORTADELO Y FILEMON',  'COMEDIA', 82, 'FRANCISCO IBAŅEZ', 1958, 'NO', '/caratulas/mortadelo-y-filemon.png');  


create table Canciones
(
ID_Cancion number(3),
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Artista     varchar2(30) Not Null,
Genero      varchar2(30) Not Null,
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

constraint Pk_Ajustes Primary key(ID_Usuario),
constraint ck_fPerfil check (Fperfil between 0 and 9),
constraint ck_Volumen check (Volumen between 0 and 100),
constraint ck_Cfondo check (Cfondo between 0 and 4)
);




