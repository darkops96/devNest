# devNest
**Red social para la interacción de desarrolladores de videojuegos, con la posibilidad de organizar y gestionar Game Jams.**

**De forma pública**, se podrán ver los perfiles de otros usuarios, sus juegos y comentar en ellos. También se podrán ver las Game Jams organizadas anteriormente y los proyectos que se presentaron en ellas. Los juegos se podrán descargar para jugarlos. Además, todos los usuarios podrán hacer uso de un randomizador que devuelve aleatoriamente 2 temáticas de una pool. 

**De forma privada**, los usuarios se registraran con un alias (que debe ser único) y una contraseña. Tendrán un muro donde aparecerán sus juegos publicados. Por otro lado, podrán apuntarse y organizar jams, definiendo un nombre y una fecha. La temática puede elegirse libremente o hacer uso del randomizador para obtener 2 palabras que la definan. Dentro de la organización de la Jam, podrán avisar de novedades a los usuarios apuntados. Al apuntarse a una Jam, tendrán la posibilidad de subir un juego durante la duración de la misma.

## Entidades:

  * **Game Jam**: ID, Nombre de la Game Jam, Usuario Administrador, Descripción, Temática, Equipos, Equipo Ganador, Fecha de Inicio y Fecha de Fin.  
  * **Equipos**: ID, Nombre del equipo, Miembros, Videojuego y Game Jam en la que participa (los equipos se crean para una Game Jam en concreto).  
  * **Usuarios**: ID, Alias Único, Contraseña, Correo Electrónico, Foto de Perfil (opcional) y Descripción (opcional).  
  * **Videojuegos**: ID, Título, Fecha de subida, Descripción, Categoría, Plataformas, Fichero y Comentarios.  
  * **Comentario**: ID, Juego, Usuario, Fecha, Texto y Comentario al que responde (opcional).
  
## Servicios internos:

  * Las notificaciones se envían al correo electrónico.  
  * Randomizador que devuelve 2 palabras aleatorias de una pool para proponer temas. 
  * Sistema de descarga archivos de videojuegos (.zip, .rar o .exe).
  
## Páginas Web:
![Diagrama de pantallas](https://user-images.githubusercontent.com/49963607/155017804-c55d094a-5b66-47c8-b7fc-689157e39b23.png)

**Home**: Página inicial. 
* El usuario puede registrarse o iniciar sesión desde la cabecera. Si tiene la sesión iniciada, puede ir a su perfil, crear una GameJam o cerrar sesión.
* Como característica principal, puede utilizar el "Random Topic Generator" para conseguir dos temas aleatorios.
* Además, se puede ver un listado con todas las GameJams existentes y entrar en las respectivas páginas de cada una.

![initialWeb](https://user-images.githubusercontent.com/58952176/155036300-fd914655-298b-46be-bd32-f6b5968b8ee2.PNG)

**Register**
: Página de registro de usuario.
* Desde la cabecera se puede ir a la página de inicio de sesión o a la página principal.
* El usuario puede introducir sus datos para registrar una cuenta, para ello ha de introducir: Alias (irrepetible), correo electrónico,

![Register](https://user-images.githubusercontent.com/58952176/155036319-ed4ad540-bb4b-46c3-834a-8a82db62e1cf.PNG)


**Log In**: Página de inicio de sesión.
* El usuario puede utilizar la cabecera para ir a la página de registro o volver a la página principal.
* En esta página se puede iniciar sesión introduciendo el nombre de usuario o alias y la contraseña

![LogIn](https://user-images.githubusercontent.com/58952176/155036323-aaf15c3e-e5ed-45c8-8bcb-9a66636e348a.PNG)

**Profile**: Página de perfil de usuario.
* Página que muestra los datos de los usuarios así como los juegos creados.
* Si el usuario entra en su propio perfil, puede editar la descripción y la imagen de perfil.

![Profile](https://user-images.githubusercontent.com/58952176/155036334-9f74a10a-f9f2-4454-aabb-517592facb91.PNG)

**Create Jam**: Página de creación de GameJams.
* El usuario puede rellenar un formulario con la información de la GameJam que quiere crear.
* Solo puede crear una Game Jam si tiene la sesión iniciada.

![CreateJam](https://user-images.githubusercontent.com/58952176/155036342-be590ffa-4c9a-4148-a0a0-e16884420944.PNG)

**GameJam**: Página de información de GameJam.
* En esta página, se pueden ver los datos relacionados con una GameJam como sus topics, la fecha de inicio y de fin y los equipos apuntados con sus respectivos juegos.
* Los usuarios pueden crear equipos y apuntarse a otros ya existentes.

![GameJam](https://user-images.githubusercontent.com/58952176/155036371-5622a17e-a379-414e-bb0a-8040a116bb9b.PNG)

**Upload Game**: Página para subir un videojuego.
* El usuario puede rellenar un formulario para subir un videojuego con información relevante de este como nombre, descripción y el archivo del juego.
* Es necesario que sea miembro de un equipo para acceder a esta página.

![UploadGame](https://user-images.githubusercontent.com/58952176/155036392-b3d0b4e5-fe8b-4c5f-a5d2-1012ef3d3dc0.PNG)

**Game**: Página de información de videojuego.
* Web que recoge la información sobre un videojuego subido: Título, descripción, lista de los desarrolladores y link de descarga.
* Los usuarios con sesión iniciada podrán comentar.

![GameWeb](https://user-images.githubusercontent.com/58952176/155036403-3bc9326f-66d7-431c-8f86-3e4ae49ccaff.PNG)

## Diagrama UML
![devNest](https://user-images.githubusercontent.com/49963607/155034005-160ecb9a-391a-4ddc-ac33-074fe1df03ac.png)

## Diagrama Entidad-Relación
![Diagrama-Entidad-Relacion](https://user-images.githubusercontent.com/58952176/155035518-28213136-7775-4a52-815b-81e821234202.PNG)

