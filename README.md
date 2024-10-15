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

El proyecto es una aplicación Kotlin diseñada con Jetpack Compose y usando las arquitecturas MVVM en conjunto con Clean Architecture, la cual simula el uso de la Realidad Aumentada en una mini app tipo e-commerce. La aplicación cuenta con una interfaz principal donde muestra los detalles generales de un posible producto de alguna tienda, permitiendo ver a mayor detalle la imagen del producto y cómo es que este se vería ya en el hogar de quien busque adquirirlo.<br>
La idea principal es la Realidad Aumentada, permitir que los usuarios puedan ver modelos 3D de los productos y poder analizarlos a mayor detalle, saber cómo lucirían en su sala, comedor, habitación, etc. o simplemente por mera curiosidad (si es que tu dispositivo es compatible con AR Core).

La app realiza las validaciones por sí sola para saber si tu teléfono es compatible o no con AR Core, así como también si es necesario que instales los servicios de realidad aumentada de Google para poder hacer uso de esta tecnología.


### Características clave

* **Realidad Aumentada**:<br>

Visualiza tu próximo sillón, televisión, ¡o auto! en tiempo real gracias a la poderosa tecnología AR. ¿Quieres comprar algún artículo, pero no sabes si se lucirá bien en tu habitación, sala o comedor? No hay problema, si tu dispositivo es compatible con la Realidad Aumentada tendrás a tu disposición el uso de esta tecnología en nuestra aplicación para poder visualizar nuestros productos en cualquier parte en donde estés como si ya estuvieran contigo.

* **Zoom a las imágenes de los productos**:<br>

Zoomable permite a los usuarios poder ver a mayor detalle la imagen del producto, como sucede en otras aplicaciones como redes sociales, navegadores, etc.

* **Agregar productos al carrito**:warning::hammer::<br>

Simulación de cómo agregarías productos a tu carrito de compras en una app e-commerce.

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
* **Product**: Muestra el listado de productos de la tienda, permite hacer zoom a la imagen del producto y valida si el servicio AR es soportado en el dispositivo.
* **Ar_Product**: Permite hacer uso de la realidad aumentada para ver los modelos de los productos.

## Requisitos :bookmark_tabs:

1. Android Studio Jellyfish | 2023.3.1 o superior
2. Gradle Version 8.6
3. Kotlin 1.9.0
4. Android API 24 o superior (Android 7+)
5. Compatibilidad con Servicios RA de Google

## Instalación :arrow_down:

1. Clona el repositorio:
   ```
    git clone https://github.com/Deivid117/AR-Project.git
2. Ejecuta el proyecto :rocket:

## Capturas :camera:

<p align="center">
  <img src="https://github.com/user-attachments/assets/9d6908f6-0165-4757-8a3b-8ac8e7be33ed" alt="Descripción de la imagen">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/297252cd-6f01-4c0f-9f47-947afd8c8179" alt="Descripción de la imagen">
</p>

## Video demostrativo :movie_camera:

<a href="https://drive.google.com/file/d/1vYzW-_KYrRGhkKS81t8w8se-dkrLT6h3/view?usp=sharing">
  <img src="https://github.com/user-attachments/assets/fb5587b3-90be-4232-9956-959d7ce62b9a" alt="Game app video"/>
</a>

## Autor :man_technologist:

*David Huerta* :copyright:	2024

email :email:: deivid.was.here117@gmail.com<br>
linkedin :man_office_worker:: [Perfil LinkedIn](https://www.linkedin.com/in/david-de-jes%C3%BAs-ju%C3%A1rez-huerta-159695241/)

