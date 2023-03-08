drop table Usuario;
drop table Bibliotecas;
drop table Elementos;
drop table Elementos_Bibliotecas;
drop table Juegos;
drop table Libros;
drop table Canciones;
drop table Ajustes;

create table Usuario 
(
ID          number(3),
Nickname    varchar2(30) UNIQUE Not Null,
Contraseña  varchar2(30) Not Null,
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

/*
create table Elementos
(
ID_element    number(3),
Titulo      varchar2(30) Not Null,
Ano_lanz    date Not Null,
constraint Pk_Elementos Primary key(ID_element)
);


create table Elementos_Bibliotecas
(
ID_element   number(3) references Elementos  on delete cascade ,
ID_Usuario   number(3),
ID_biblio   number(3),  
constraint Pk_Elementos_Biblio Primary key(ID_element, ID_biblio, ID_Usuario),
constraint FK_Elementos_Biblio Foreign key (ID_biblio, ID_Usuario) references Bibliotecas on delete cascade
);
*/

create table Juegos
(
ID_Juego number(3),
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Genero      varchar2(30) Not Null,
Plataforma  varchar2(25) Not Null,
H_jugadas   number(7,2),
Lanzamiento number(4),
Terminado   varchar2(10) Not Null,


constraint Pk_Juegos Primary key(ID_Juego),
constraint CK_terminado Check(Terminado in ('SI','NO'))
);

INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, H_jugadas,Lanzamiento, Terminado) VALUES(001,'GOD OF WAR','ACCION', 'PS3', 40, 2018, 'NO'); 
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado)  VALUES(002,'ASSASINS CREED UNITY','ACCION', 'PS2', 40, 2005, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado)  VALUES(003,'UNCHARTED','ACCION', 'PS3', 40, 2007, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado)  VALUES(004,'THE LAST OF US','ACCION', 'PS3', 20, 2013, 'NO');  

INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado)  VALUES(005,'CALL OF DUTY BO2','SHOOTER', 'XBOX', 10, 2012, 'NO'); 
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento,Terminado)  VALUES(006,'FORTNITE','SHOOTER', 'PS4', 2017, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento,  Terminado)  VALUES(007,'CSGO','SHOOTER', 'PC', 2007, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, H_jugadas, Lanzamiento, Terminado)  VALUES(008,'BATTLEFIELD','SHOOTER', 'PS4', 30, 2016, 'NO');  

INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento,  Terminado)  VALUES(009,'FIFA 23','DEPORTES', 'PC', 2022, 'NO'); 
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento, Terminado)  VALUES(010,'WII SPORTS','DEPORTES', 'WII', 2006, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento,  Terminado)  VALUES(011,'F1 2023','DEPORTES', 'PC', 2022, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento,  Terminado)  VALUES(012,'NBA 2K23','DEPORTES', 'PS5', 2022, 'NO');  

INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento,  Terminado)  VALUES(0013,'SILENT HILL 2 ','TERROR', 'PS2', 2003, 'NO'); 
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento, Terminado)  VALUES(014,'RESIDENT EVIL 5','TERROR', 'PC', 2009, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento, Terminado)  VALUES(015,'OUTLAST','TERROR', 'PC', 2013, 'NO');  
INSERT INTO JUEGOS (id_juego, Titulo, Genero, Plataforma, Lanzamiento, Terminado)  VALUES(016,'VISAGE','TERROR', 'PC', 2020, 'NO');  





create table Libros
(
ID_Libro number(3) ,
ID_Usuario  number(3) references Usuario on delete cascade,
Titulo varchar2(40) Not Null,
Genero      varchar2(30) Not Null,
N_paginas   number(4) Not Null,
Autor       varchar2(30) default 'Anónimo' Not Null,
Año_Lanz  number(4) not null,
Terminado   varchar2(10) Not Null,

constraint Pk_Libros Primary key(ID_Libro),
constraint CK_terminado3 Check(Terminado in ('SI','NO'))
);

INSERT INTO LIBROS  (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (001,'IT','TERROR', 1504, 'STEPHEN KING',1986, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (002, 'EL EXORCISTA','TERROR', 376, 'WILLIAM PETER BLATTY', 1971,  'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (003,'DRACULA','TERROR', 418, 'BRAM STOKER', 1897, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (004,'FRANKENSTEIN','TERROR', 136, 'MARY SHELLEY',1818, 'NO');  

INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (005,'LA ISLA DEL TESORO','AVENTURAS', 283, 'ROBERT LOUIS STEVENSON',1883, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (006, 'ROBINSON CRUSOE','AVENTURAS', 304, 'DANIEL DEFOE', 1719,  'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (007,'EL CORREDOR DEL LABERINTO', 'AVENTURAS', 376, 'JAMES DASHNER',2009, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (0008,'VIAJE AL CENTRO DE LA TIERRA','AVENTURAS', 330, 'JULIO VERNE', 1862, 'NO');  

INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (009,'ORGULLO Y PREJUICIO','AMOR', 424, 'JANE AUSTEN',1813, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (010, 'BAJO LA MISMA ESTRELLA','AMOR', 301, 'JOHN GREEN', 2012,  'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (011,'TRES METROS SOBRE EL CIELO','AMOR', 390, 'FEDERICO MOCCIA Y KONY', 1992, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (012,'CIUDADES DE PAPEL', 'AMOR', 368, 'JOHN GREEN', 2008, 'NO');  

INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (013,'SIN NOTICIAS DE GURB', 'COMEDIA', 144, 'EDUARDO MENDOZA', 2018, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (014, 'BARTLEBY EL ESCRIBIENTE', 'COMEDIA', 112, 'HERMAN MELVILLE', 1853,  'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (015,'DIARIO DE GREG', 'COMEDIA', 240, 'JEFF KINNEY', 2007, 'NO');  
INSERT INTO LIBROS (id_libro, Titulo, Genero, n_paginas, autor ,año_lanz, Terminado)  VALUES (016,'MORTADELO Y FILEMON',  'COMEDIA', 82, 'FRANCISCO IBAÑEZ', 1958, 'NO');  



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

/*INSERTS PARA VALORES POR DEFECTO*/
/*USUARIOS*/
INSERT INTO USUARIO VALUES (001,  'ADMIN', 'ADMIN', 'mmartin.mrmg@gmail.com');

/*LIBROS*/

COMMIT;



/*SELECTS */
SELECT * FROM USUARIO ORDER BY ID;
SELECT * FROM AJUSTES ORDER BY ID_USUARIO;
SELECT * FROM LIBROS;
SELECT * FROM CANCIONES;



select correo from usuario where nickname = 'ADMIN';
DELETE FROM USUARIO WHERE ID BETWEEN  3 AND 10  ;
DELETE FROM AJUSTES WHERE ID_USUARIO = 1;


