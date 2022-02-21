# devNest
**Red social para la interacción de desarrolladores de videojuegos, con la posibilidad de organizar y gestionar Game Jams.**

**De forma pública**, se podrán ver los perfiles de otros usuarios, sus juegos y comentar en ellos. También se podrán ver las Game Jams organizadas anteriormente y los proyectos que se presentaron en ellas. Los juegos se podrán descargar para jugarlos. Además, todos los usuarios podrán hacer uso de un randomizador que devuelve aleatoriamente 2 temáticas de una pool. 

**De forma privada**, los usuarios se registraran con un alias (que debe ser único) y una contraseña. Tendrán un muro donde aparecerán sus juegos publicados. Por otro lado, podrán apuntarse y organizar jams, definiendo un nombre y una fecha. La temática puede elegirse libremente o hacer uso del randomizador para obtener 2 palabras que la definan. Dentro de la organización de la Jam, podrán avisar de novedades a los usuarios apuntados. Al apuntarse a una Jam, tendrán la posibilidad de subir un juego durante la duración de la misma.

## Entidades:

  * **Game Jam**: compuesto de Nombre de la Game Jam, Usuario Administrador, Descripción, Temática, Equipos, Fecha de Inicio y Fecha de Fin.  
  * **Equipos**: Nombre del equipo, Miembros.  
  * **Usuarios**: Alias Único, Contraseña, Correo Electrónico, Foto de Perfil (opcional) y Descripción (opcional).  
  * **Videojuegos**: Título, Equipo, Game Jam, Fecha de subida, Categoría, Plataformas, Fichero.  
  * **Comentario**: Juego, Usuario, Fecha, Texto, Comentario al que responde (opcional).
  
## Servicios internos:

  * Las notificaciones se envían al correo electrónico.  
  * Randomizador que devuelve 2 palabras aleatorias de una pool para proponer temas.  
  
## Páginas Web:
![Diagrama de pantallas](https://user-images.githubusercontent.com/49963607/155017804-c55d094a-5b66-47c8-b7fc-689157e39b23.png)

**Home**: Página inicial. 
* El usuario puede registrarse o iniciar sesión desde la cabecera. Si tiene la sesión iniciada, puede ir a su perfil, crear una GameJam o cerrar sesión.
* Como característica principal, puede utilizar el "Random Topic Generator" para conseguir dos temas aleatorios.
* Además, se puede ver un listado con todas las GameJams existentes y entrar en las respectivas páginas de cada una.

**Register**: Página de registro de usuario.
* Desde la cabecera se puede ir a la página de inicio de sesión o a la página principal.
* El usuario puede introducir sus datos para registrar una cuenta, para ello ha de introducir: Alias (irrepetible), correo electrónico,

**Log In**: Página de inicio de sesión.
* El usuario puede utilizar la cabecera para ir a la página de registro o volver a la página principal.
* En esta página se puede iniciar sesión introduciendo el nombre de usuario o alias y la contraseña

**Profile**: Página de perfil de usuario.
* Página que muestra los datos de los usuarios así como los juegos creados.
* Si el usuario entra en su propio perfil, puede editar la descripción y la imagen de perfil.

**Create Jam**: Página de creación de GameJams.
* El usuario puede rellenar un formulario con la información de la GameJam que quiere crear.
* Solo puede crear una Game Jam si tiene la sesión iniciada.

**GameJam**: Página de información de GameJam.
* En esta página, se pueden ver los datos relacionados con una GameJam como sus topics, la fecha de inicio y de fin y los equipos apuntados con sus respectivos juegos.
* Los usuarios pueden crear equipos y apuntarse a otros ya existentes.

**Upload Game**: Página para subir un videojuego.
* El usuario puede rellenar un formulario para subir un videojuego con información relevante de este como nombre, descripción y el archivo del juego.
* Es necesario que sea miembro de un equipo para acceder a esta página.

**Game**: Página de información de videojuego.
* Web que recoge la información sobre un videojuego subido: Título, descripción, lista de los desarrolladores y link de descarga.
* Los usuarios con sesión iniciada podrán comentar.


