<h1 align="center"> :robot: AR PROJECT :chair: </h1>

<p align="center">
  <img src="https://github.com/user-attachments/assets/bd21c7d0-f24a-4096-906a-528345f42108" alt="Descripción de la imagen" width="300">
</p>

![Kotlin](https://img.shields.io/badge/Kotlin-1.8.10-blueviolet?logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose_Boom-2023.08.00-brightgreen?logo=jetpackcompose&logoColor=white)
![Architecture-MVVM](https://img.shields.io/badge/Architecture-MVVM-blue)
![Coil](https://img.shields.io/badge/Coil-2.4.0-tomato?logo=coil&logoColor=white)
![Navigation](https://img.shields.io/badge/Navigation_Compose-2.8.1-cyan?logo=android&logoColor=white)
![Splash](https://img.shields.io/badge/Splash_Screen-1.0.1-lightgreen.svg?logo=lottie&logoColor=white)
![Zoomable](https://img.shields.io/badge/Zoomable-1.6.2-yellow)
![UUID Kotlin](https://img.shields.io/badge/Kotlinx_UUID-0.0.23-orange?logo=android&logoColor=white)
![AR Scene](https://img.shields.io/badge/AR_Scene_View-2.2.1-red?logo=google&logoColor=white)
![Dagger Hilt](https://img.shields.io/badge/Dagger_Hilt-2.51.1-yellowgreen?logo=android&logoColor=white)
![Material 3](https://img.shields.io/badge/Material%203-blue?logo=android&logoColor=white)
![Min SDK Version](https://img.shields.io/badge/Min%20SDK%20Version-24-lightblue)
![Coroutines](https://img.shields.io/badge/Coroutines-1.7.0-purple?logo=kotlin&logoColor=white)

## Descripción :open_book:

El proyecto es una aplicación Kotlin diseñada con Jetpack Compose y usando las arquitecturas MVVM en conjunto con Clean Architecture, la cual consume la API https://api.rawg.io/docs/ :link: de la web [RAWG :video_game:](https://rawg.io/) la cual es una base de datos con más de 350,000 juegos en su catálogo, disponible para que 
cualquier desarrollador pueda hacer uso de ella en sus proyectos y acceda a todo su contenido, desde próximos lanzamientos, los juegos más votados, favoritos del público, etc.

En nuestra aplicación podrás encontrar información detallada de cada título, género o plataforma, así como también crear tu cuenta para registrar tus títulos favoritos. Con una interfaz moderna, animaciones que enriquecen la experiencia y fácil de navegar,
podrás explorar una vasta colección de juegos y tener datos sobre ellos siempre al alcance de tu mano. 

### Características clave


## Tecnologías utilizadas :iphone:
* **Jetpack Compose**: Toolkit moderno de UI de Android que permite construir interfaces de usuario de manera declarativa.
* **Clean Architecture**: Organiza el código en capas bien definidas, promoviendo la separación de responsabilidades. Facilita la escalabilidad y el mantenimiento.
* **MVVM**: Enfoque arquitectónico que desacopla la lógica de negocio de la interfaz de usuario. Facilita la creación de interfaces reactivas con una clara separación de responsabilidades entre las capas.
* **Dagger Hilt**: Biblioteca de inyección de dependencias que simplifica la creación y gestión de dependencias en Android.
* **Coroutines**: Son una forma avanzada y sencilla de manejar tareas asíncronas. Facilitan la ejecución de operaciones de larga duración, como solicitudes de red o tareas de I/O, sin bloquear el hilo principal.
* **Navigation Compose**: Permite gestionar la navegación entre pantallas de manera declarativa dentro de aplicaciones construidas con Jetpack Compose.
* **Material Design 3**: Ofrece una experiencia de usuario moderna y coherente a través de temas personalizables, componentes de UI optimizados y nuevas directrices para interfaces accesibles y atractivas.
* **Coil**: Biblioteca ligera para la carga de imágenes en Android. Proporciona una forma rápida y eficiente de mostrar imágenes en la UI.
* **Zoomable**: Librería de código abierto que proporciona una interfaz fluida para aplicar gestos de zoom y desplazamiento en imágenes.
* **AR Scene View**: Librería de código abierto diseñada para simplificar la integración de elementos 3D interactivos con realidad aumentada (AR) en Android. Basada en ARCore.

## Estructura del proyecto :hammer_and_wrench:

El proyecto está basado en los principios de Clean Architecture y sigue el patrón MVVM (Model-View-ViewModel), lo que asegura una clara separación de responsabilidades y facilita la escalabilidad y el mantenimiento del código. Cada feature se organiza de manera modular, facilitando su desarrollo, prueba y mantenimiento.

### Capas del proyecto

* **Capa de Datos (Data)**: Aquí se encuentra la implementación de la lógica de los repositorios. Esta capa es responsable de proporcionar datos a la capa de dominio.
 
 * ***Repositorios***: Implementan las interfaces de los repositorios definidos en la capa de dominio.

* **Capa de DI**: Es responsable de proporcionar las dependencias que las diferentes clases necesitan para funcionar, sin que estos tengan que crearlas por sí mismos. En lugar de instanciar las dependencias directamente, las clases reciben (o inyectan) sus dependencias desde fuera, lo que promueve el principio de inversión de dependencias.

* **Capa de Dominio (Domain)**: Esta capa es independiente de cualquier detalle de implementación. Contiene las reglas de negocio y las entidades del sistema.

  * ***Repositorios***: Interfaces que definen los métodos necesarios para interactuar con los datos del sistema. Estas interfaces no conocen detalles específicos de dónde provienen los datos o cómo se almacenan.
  * ***UseCases***: Definen las acciones que la aplicación puede realizar, como obtener productos, etc.
  * ***Modelos***: Representan los objetos de negocio del sistema (por ejemplo, Product, ArProductModel).

* **Capa de Presentación (Presentation)**: Esta capa contiene la lógica relacionada con la UI y es donde se maneja la interacción del usuario.

  * ***ViewModels***: Siguiendo el patrón MVVM, los ViewModels son responsables de preparar los datos para ser mostrados en la UI y manejar las interacciones de los usuarios.
  * ***Composables***: Esta es la capa donde se definen las interfaces de usuario utilizando Jetpack Compose. Los Composables reciben los datos del ViewModel y reaccionan a los cambios de estado.

* **Navegación**: Define la ruta de cada feature, así como también la forma en que navega hacia otras pantallas de acuerdo a las interacciones del usuario. Inicializa el ViewModel.

### Organización modular por features

* **Core**: Contiene todos los archivos que se comparten entre todos los módulos, Shared ViewModel, Scaffold, Modifier Extensions.
* **Product**: Responsable de permitir que el usuario inicie sesión en la app, así como también de validar sus credenciales o permitirle usar biométricos si se encuentran disponibles.
* **Ar_Product**: Aquí el usuario se da de alta en la app, valida los datos ingresados e igualmente permite activar los datos biométricos.

## Requisitos :bookmark_tabs:

1. Android Studio Jellyfish | 2023.3.1 o superior
2. Gradle Version 8.6
3. Kotlin 1.9.0
4. Android API 24 o superior (Android 7+)
5. Compatibilidad con Servicios AR de Google

## Instalación :arrow_down:

1. Clona el repositorio:
   ```
    git clone https://github.com/Deivid117/AR-Project.git

2. Ejecuta el proyecto :rocket:

## Capturas :camera:


## Video demostrativo :movie_camera:

<a href="https://drive.google.com/file/d/1ev2-y-BYaefwfdCjdN_7uhFPHU8jVEYJ/view?usp=sharing">
  <img src="https://github.com/user-attachments/assets/aa30fd95-7b0d-42dd-810b-a2752fab2f38" alt="Game app video"/>
</a>

## Autor :man_technologist:

*David Huerta* :copyright:	2024

email :email:: deivid.was.here117@gmail.com<br>
linkedin :man_office_worker:: [Perfil LinkedIn](https://www.linkedin.com/in/david-de-jes%C3%BAs-ju%C3%A1rez-huerta-159695241/)

