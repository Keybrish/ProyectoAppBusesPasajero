# 🚍Safey - Aplicativo Móvil para los Pasajeros

Safey es un sistema que permite la gestión y venta de pasajes a usuarios de buses interprovinciales en Ecuador.
La presente aplicación móvil tiene como propósito brindar al usuario la facilidad de comprar boletos de acuerdo a sus necesidades.

## 👣 Primeros Pasos

Estas instrucciones te guiarán en la configuración y ejecución de la aplicación en tu entorno local para propósitos de desarrollo y pruebas.

#### ✔️ Prerrequisitos

Asegúrate de tener instalado lo siguiente:

- [Android Studio](https://developer.android.com/studio) [🔗](https://developer.android.com/studio)
  Este proyecto se desarrolló en el IDE de Android Studio, versión: Dolphin | 2021.3.1 Patch 1

  ⚠️Para evitar errores de compatibilidad, te recomendamos ejecutar el proyecto en la misma versión mencionada del IDE, o en superiores.
- [Repositorio - Backend](https://github.com/YadiraAllauca/ServiciosProyectoDAS)
  Los enlaces a los servicios y la base de datos que se encuentran implicados en el código de este proyecto están alojados en un HostingWeb. Pero puedes verificar la estructura en el repositorio de backend. Te recomendamos revisar la documentación[🔗](https://github.com/YadiraAllauca/ServiciosProyectoDAS)

## ⚙️ Instalación

1. Clona el repositorio en tu máquina local dentro de htdocs del directorio de XAMPP:

```bash
git clone https://github.com/Keybrish/ProyectoAppBusesPasajero.git
```

2. La app actualmente está conectado a un proyecto personal en Firebase <img src="https://www.gstatic.com/mobilesdk/160503_mobilesdk/logo/2x/firebase_28dp.png" alt="Logo de Firebase" width="20">, si deseas efectuar tu propia conexión con tu cuenta, revisa la documentación proporcionada por esta plataforma [🔗](https://firebase.google.com/docs/android/setup?hl=es-419)

3. Verifica que la ejecución se realice sin problema, de lo contrario puedes actualizar las versiones de las dependencias en el archivo build.gradle (Module).
   Principalmente las relacionadas a:

- Picasso: Carga de imágenes [🔗](https://github.com/square/picasso)
- Firebase: Autenticación y almacenamiento [🔗](https://firebase.google.com/docs?hl=es-419)
- Retrofit: Consumo de servicios [🔗](https://github.com/square/retrofit)

4. La pantalla principal se debe mostrar así:
<div style="text-align:center">
  <img src="https://cdn.glitch.global/1d3dd682-c1e7-4386-94b7-857b9d3c741b/5674b83a-4c51-4845-a8e9-ffad0b9e079a.jpg?v=1688863735394" width="200">
</div>

## 💻 Uso

#### Login

Te permitirá ingresar como usuario pasajero para adquirir boletos. Su funcionalidad en código está presente en **appBuses/app/src/main/java/dev/android/appbuses/LoginActivity.kt**

<div style="text-align:center">
<img src="https://cdn.glitch.global/67cd472b-72c6-4b72-8f91-3c3387cbf446/e599368f-c69c-4a93-8d66-27892f6badbc.image.png?v=1688961858676" width="200">
</div>

#### Registro

Esta pantalla permitirá al usuario registrarse si es que aún no cuenta con alguna cuenta. Su funcionalidad en código está presente en **appBuses/app/src/main/java/dev/android/appbuses/RegisterActivity.kt**

<div style="text-align:center">
<img src="https://cdn.glitch.global/67cd472b-72c6-4b72-8f91-3c3387cbf446/ddfcfeec-0cb6-4c87-9355-5db03bf8aa81.image.png?v=1688961887325" width="200">
</div>

#### Principal

Esta pantalla muestra funcionalidades como ver viajes disponibles, búsqueda por filtros según un origen y destino, además de información más detallada de cada viaje. Su funcionalidad en código está presente en
- **appBuses/app/src/main/java/dev/android/appbuses/MainActivity.kt**
- **appBuses/app/src/main/java/dev/android/appbuses/FilterMenuActivity.kt**
<div style="text-align:center">
<img src="https://cdn.glitch.global/67cd472b-72c6-4b72-8f91-3c3387cbf446/b10d9c2c-d762-45b8-869a-0caeceb4659a.image.png?v=1688961895687" width="200">
</div>

#### Comprar

En esta pantalla se podrá efectuar una compra, seleccionar la preferencia de asientos, método de pago, etc. Su funcionalidad en código está presente en

- **appBuses/app/src/main/java/dev/android/appbuses/PaymentActivity.kt**
- **appBuses/app/src/main/java/dev/android/appbuses/SeatActivity.kt**
<div style="text-align:center">
<img src="https://cdn.glitch.global/67cd472b-72c6-4b72-8f91-3c3387cbf446/1a55cfe6-0f30-400f-bdf7-c83907dd0467.image.png?v=1688961920557" width="200">
</div>

#### Perfil de usuario

En esta pantalla el usuario puede editar sus datos, además de ver el historial de compras. Su funcionalidad en código está presente en

- **appBuses/app/src/main/java/dev/android/appbuses/ProfileActivity.kt**
- **appBuses/app/src/main/java/dev/android/appbuses/ProfileInfoActivity.kt**
<div style="text-align:center">
<img src="https://cdn.glitch.global/67cd472b-72c6-4b72-8f91-3c3387cbf446/2dbcf7c9-743b-4913-ae1f-c5eede7bbb9e.image.png?v=1688961932977" width="200">
</div>

#### Historial de compras

En esta pantalla el usuario puede observar todas las compras que ha efectuado desde la aplicación. Su funcionalidad en código está presente en

- **appBuses/app/src/main/java/dev/android/appbuses/HistoryQRActivity.kt**
- **appBuses/app/src/main/java/dev/android/appbuses/HistoryActivity.kt**
<div style="text-align:center">
<img src="https://cdn.glitch.global/67cd472b-72c6-4b72-8f91-3c3387cbf446/9ea9b7ec-7fbd-4658-a13e-dd91ba6f6493.image.png?v=1688961942690" width="200">
</div>

#### Código QR

La vista del código QR es fundamental para comprobar que la compra es válida. Su funcionalidad en código está presente en **appBuses/app/src/main/java/dev/android/appbuses/QRCodeActivity.kt**

<div style="text-align:center">
<img src="https://cdn.glitch.global/67cd472b-72c6-4b72-8f91-3c3387cbf446/0d301058-8656-45fd-90f2-e7f9953f1947.image.png?v=1688961949625" width="200">
</div>

## 🤝 Contribución

Si deseas contribuir a este proyecto, sigue los siguientes pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama:

```bash
git checkout -b nombre-de-la-rama
```

3. Realiza los cambios y haz commit:

```bash
git commit -m "Descripción de los cambios"
```

4. Envía los cambios a tu fork:

```bash
git push origin nombre-de-la-rama
```

5. Crea una pull request en este repositorio.

## ©️ Licencia

Este proyecto académico no tiene una licencia específica asignada. Todos los derechos de autor pertenecen a los miembros del equipo de desarrollo. Ten en cuenta que esto significa que no se otorgan permisos explícitos para utilizar, modificar o distribuir el código fuente o los archivos relacionados. Cualquier uso, reproducción o distribución del proyecto debe obtener permiso previo.

## 📧 Contacto

Si tienes alguna pregunta o comentario, puedes contactarte con los miembros del equipo de desarrollo:

- kzamora7938@uta.edu.ec
- anaranjo1676@uta.edu.ec
- dpinchao9519@uta.edu.ec
- tarmijos0985@uta.edu.ec
- yallauca3770@uta.edu.ec
