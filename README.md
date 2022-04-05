# devNest
**Red social para la interacción de desarrolladores de videojuegos, con la posibilidad de organizar y gestionar Game Jams.**

**De forma pública**, se podrán ver los perfiles de otros usuarios, sus juegos y comentar en ellos. También se podrán ver las Game Jams organizadas anteriormente y los proyectos que se presentaron en ellas. Los juegos se podrán descargar para jugarlos. Además, todos los usuarios podrán hacer uso de un randomizador que devuelve aleatoriamente 2 temáticas de una pool. 

**De forma privada**, los usuarios se registraran con un alias (que debe ser único) y una contraseña. Tendrán un muro donde aparecerán sus juegos publicados. Por otro lado, podrán apuntarse y organizar jams, definiendo un nombre y una fecha. La temática puede elegirse libremente o hacer uso del randomizador para obtener 2 palabras que la definan. Dentro de la organización de la Jam, podrán avisar de novedades a los usuarios apuntados. Al apuntarse a una Jam, tendrán la posibilidad de subir un juego durante la duración de la misma.

## Instrucciones para el despliegue la aplicación:
  ### Compilación:
  Las siguientes instrucciones se corresponden con la compilación de la aplicación y su servicio interno en una máquina con un sistema operativo Windows. Si se buscase compilar el proyecto en un sistema distinto (por ejemplo, una distribución de Linux), los pasos necesarios serían los mismos, pero adaptados a los comandos de dicho sistema.
  1. Instalar el JDK de Java 17 o posterior y MySQL Server en la máquina donde se vayan a compilar los proyectos:
     * En MySQL Server se debe crear una base de datos llamada 'devnest' y un usuario 'devnest' con contraseña 'devnest1234' con permisos de administrador.
  2. Descargar y descomprimir Maven en la carpeta que el usuario desee.
  3. Comprobar que la variable del sistema JAVA_HOME apunta al directorio raíz del JDK (suele hacerse automáticamente al instalar el JDK).
  4. Comprobar que la variable del sistema PATH apunta a los directorios *bin* del JDK (también suele hacerse automáticamente al instalarlo) y de Maven.
  5. Abrir una terminal (por ejemplo, PowerShell en Windows) y desplazarse hasta el directorio raíz del proyecto de la aplicación web (devNest).
  6. Ejecutar el comando `mvn clean package`.
  7. El ejecutable (archivo con extensión .jar) se guardará en el directorio *target* del proyecto.
  8. Repetir los pasos 5, 6 y 7 con el proyecto del servicio interno (devNestInternalService).
  ### Despliegue en Máquina Virtual:
  Las siguientes instrucciones se corresponden con el despliegue de la aplicación y su servicio interno en una máquina virtual con un sistema operativo Ubuntu (o derivado de este). Si se buscase desplegar el proyecto en un sistema distinto (por ejemplo, Windows), los pasos necesarios serían los mismos, pero adaptados a los comandos de dicho sistema.
  1. Instalar el JRE de Java 17 o posterior en la máquina virtual: `sudo apt install openjdk-17-jre-headless`
  2. Instalar MySQL Server en la máquina virtual: 
      1. `sudo apt install mysql-server-8.0`
      2. `sudo mysql`
      3. `ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'devnest1234';`
      4.  `quit;`
      5.  `mysql -u root -p`: introducir contraseña 'devnest1234'
      6.  `CREATE DATABASE devnest;`
      7.  `CREATE USER 'devnest'@'localhost' IDENTIFIED BY 'devnest1234';`
      8.  `GRANT ALL PRIVILEGES ON devnest.*  TO 'devnest'@'localhost';`
      9.  `flush privileges;`
      10.  `quit;`
  3. Cargar los 2 archivos .jar compilados anteriormente al sistema. En caso de no poder realizar el proceso de compilación, pueden obtenerse aquí:
     * [devNest](https://github.com/darkops96/devNest/raw/main/builds/devNest-1.0.0.jar): `wget https://github.com/darkops96/devNest/raw/main/builds/devNest-1.0.0.jar`
     * [devNest Internal Service](https://github.com/darkops96/devNest/raw/main/builds/devNestInternalService-1.0.0.jar): `wget https://github.com/darkops96/devNest/raw/main/builds/devNestInternalService-1.0.0.jar`
  4. Abrir una terminal y desplazarse hasta el directorio donde se hayan guardado ambos archivos.
  5. Ejecutar los comandos `sudo java -jar devNest-1.0.0.jar` y `sudo java -jar devNestInternalService-1.0.0.jar` para lanzar la aplicación web y la API REST del servicio interno.

## Entidades:

  * **Game Jam**: ID, Nombre de la Game Jam, Usuario Administrador, Descripción, Temática, Equipos, Equipo Ganador, Fecha de Inicio y Fecha de Fin.  
  * **Equipos**: ID, Nombre del equipo, Miembros, Videojuego y Game Jam en la que participa (los equipos se crean para una Game Jam en concreto).  
  * **Usuarios**: ID, Alias Único, Contraseña, Correo Electrónico, Foto de Perfil (opcional), Descripción (opcional) y Roles (para controlar el acceso a cada URL de la web).  
  * **Videojuegos**: ID, Título, Fecha de subida, Descripción, Categoría, Plataformas, Fichero y Comentarios.  
  * **Comentario**: ID, Juego, Usuario, Fecha, Texto y Comentario al que responde (opcional).
  
## Servicios internos:

  * Sistema de notificaciones al correo electrónico (registro, creación de una nueva Game Jam y unión a un equipo).  
  * Randomizador que devuelve 2 palabras aleatorias de una pool para proponer temas. 
  * Sistema de descarga archivos de videojuegos (.zip).
  
## Páginas Web:
![diagrama_de_pantallas](https://user-images.githubusercontent.com/49963607/161752551-329478d4-267f-4286-8afe-164efb3b0bb5.png)

**Home**: Página inicial. Pública a todos los usuarios. 

* El usuario puede registrarse o iniciar sesión desde la cabecera. Si tiene la sesión iniciada, puede ir a su perfil, crear una GameJam o cerrar sesión.
* Como característica principal, puede utilizar el "Random Topic Generator" para conseguir dos temas aleatorios.
* Además, se puede ver un listado con todas las GameJams existentes y entrar en las respectivas páginas de cada una.
* En el caso del administrador, puede pulsar un botón para borrar las GameJams existentes

![initialWeb](https://user-images.githubusercontent.com/58952176/155036300-fd914655-298b-46be-bd32-f6b5968b8ee2.PNG)

![initialWeb-Admin](https://user-images.githubusercontent.com/58952176/161638122-7d1345b5-d15b-46f0-8226-c3d5eac8081b.PNG)

**Register**: Página de registro de usuario. Pública a todos los usuarios. 
* Desde la cabecera se puede ir a la página de inicio de sesión o a la página principal.
* El usuario puede introducir sus datos para registrar una cuenta, para ello ha de introducir: Alias (irrepetible), correo electrónico,

![Register](https://user-images.githubusercontent.com/58952176/155036319-ed4ad540-bb4b-46c3-834a-8a82db62e1cf.PNG)


**Log In**: Página de inicio de sesión. Pública a todos los usuarios. 
* El usuario puede utilizar la cabecera para ir a la página de registro o volver a la página principal.
* En esta página se puede iniciar sesión introduciendo el nombre de usuario o alias y la contraseña

![LogIn](https://user-images.githubusercontent.com/58952176/155036323-aaf15c3e-e5ed-45c8-8bcb-9a66636e348a.PNG)

**Profile**: Página de perfil de usuario. Pública a todos los usuarios. 
* Página que muestra los datos de los usuarios así como los juegos creados.
* Si el usuario entra en su propio perfil, puede editar la descripción y la imagen de perfil.

![Profile](https://user-images.githubusercontent.com/58952176/155036334-9f74a10a-f9f2-4454-aabb-517592facb91.PNG)

**Create Jam**: Página de creación de GameJams. El usuario debe tener iniciada sesión.
* El usuario puede rellenar un formulario con la información de la GameJam que quiere crear.
* Solo puede crear una Game Jam si tiene la sesión iniciada.

![CreateJam](https://user-images.githubusercontent.com/58952176/155036342-be590ffa-4c9a-4148-a0a0-e16884420944.PNG)

**GameJam**: Página de información de GameJam. Pública a todos los usuarios.
* En esta página, se pueden ver los datos relacionados con una GameJam como sus topics, la fecha de inicio y de fin y los equipos apuntados con sus respectivos juegos.
* Los usuarios pueden crear equipos y apuntarse a otros ya existentes.

![GameJam](https://user-images.githubusercontent.com/58952176/155036371-5622a17e-a379-414e-bb0a-8040a116bb9b.PNG)

**Upload Game**: Página para subir un videojuego. El usuario debe tener iniciada sesión.
* El usuario puede rellenar un formulario para subir un videojuego con información relevante de este como nombre, descripción y el archivo del juego.
* Es necesario que sea miembro de un equipo para acceder a esta página.

![UploadGame](https://user-images.githubusercontent.com/58952176/155036392-b3d0b4e5-fe8b-4c5f-a5d2-1012ef3d3dc0.PNG)

**Game**: Página de información de videojuego. Pública a todos los usuarios.
* Web que recoge la información sobre un videojuego subido: Título, descripción, lista de los desarrolladores y link de descarga.
* Los usuarios con sesión iniciada podrán comentar.

![GameWeb](https://user-images.githubusercontent.com/58952176/155036403-3bc9326f-66d7-431c-8f86-3e4ae49ccaff.PNG)

**Delete GameJam**: Página para eliminar GameJams existentes. Solo puede acceder el administrador.
* Página que lista todas GameJams existentes junto a un botón para eliminarlas.

![Admin-deleteJams](https://user-images.githubusercontent.com/58952176/161638292-7f261d39-6de2-4197-a27c-16ac4888a1eb.PNG)

## Diagrama UML - devNest
![devNest](https://user-images.githubusercontent.com/58952176/161655299-3d3b1bf0-5399-4c4d-9fcc-41c676149917.png)

## Diagrama UML - devNest Internal Services
![devNestInternalService](https://user-images.githubusercontent.com/49963607/161751437-dc828561-9381-45f8-9c7b-00a09a6086f5.png)

## Diagrama UML - Rest Template connections
![Template](https://user-images.githubusercontent.com/49963607/161751674-e5aa9961-f049-4669-aa85-7d440dcdf5c3.png)

## Diagrama Entidad-Relación
![Diagrama-Entidad-Relacion](https://user-images.githubusercontent.com/58952176/155035518-28213136-7775-4a52-815b-81e821234202.PNG)

