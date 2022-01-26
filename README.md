# devNest
Red social para la interacción de desarrolladores de videojuegos, con la posibilidad de organizar y gestionar Game Jams.  

De forma pública, se podrán ver los perfiles de otros usuarios, sus posts y comentar en ellos. También se podrán ver las Game Jams organizadas anteriormente y los proyectos que se presentaron en ellas. Los juegos se podrán descargar para jugarlos. Además, todos los usuarios podrán hacer uso de un randomizador que devuelve aleatoriamente 2 temáticas de una pool. 

De forma privada, los usuarios se registraran con un alias (que debe ser único) y una contraseña. Tendrán un muro donde aparecerán sus juegos publicados y donde podrán poner posts de texto e imágenes. Por otro lado, podrán apuntarse y organizar jams, definiendo un nombre y una fecha. La temática puede elegirse libremente o hacer uso del randomizador para obtener 2 palabras que la definan. Dentro de la organización de la Jam, podrán avisar de novedades a los usuarios apuntados. Al apuntarse a una Jam, tendrán la posibilidad de subir un juego durante la duración de la misma.

Entidades:

  1.- Game Jam: compuesto de Nombre de la Game Jam, Usuario Administrador, Descripción, Temática, Equipos, Fecha de Inicio y Fecha de Fin.  
  2.- Equipos: compuesto de Game Jam en la que participa, Nombre, Miembros y Juego.  
  3.- Usuarios: Alias Único, Contraseña, Correo Electrónico, Foto de Perfil (opcional) y Descripción (opcional).  
  4.- Videojuegos: Título, Equipo, Game Jam, Fecha de subida, Categoría, Plataformas, Fichero.  
  5.- Post: Usuario, Fecha, Texto, Imagen (opcional), Video (opcional), Post al que responde (opcional).  
  6.- Comentario: Juego, Usuario, Fecha, Texto, Comentario al que responde (opcional).
  
Servicios internos:

  1.- Las notificaciones se envían al correo electrónico.  
  2.- Randomizador que devuelve 2 palabras aleatorias de una pool para proponer temas.  
  3.- Modo claro y modo oscuro.
