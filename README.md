# Sistema de Facturación

Este es un proyecto de demostración de Spring Boot para un sistema de facturación básico.

## Descripción

La aplicación facturation system es un proyecto de demostración desarrollado con Spring Boot que ofrece funcionalidades
básicas para la gestión de facturas. Permite a los usuarios realizar operaciones como crear, visualizar y gestionar
facturas, así como también subir una foto de perfil. Además, incluye características de seguridad
mediante el uso de roles de usuario y autenticación.

## Funcionalidades

1. **Gestión de Usuarios y Roles de Seguridad**:

    - Registro de usuarios.
    - Asignación de roles (por ejemplo, usuario estándar, administrador).

2. **Carga de Archivos**:

    - Permite a los usuarios cargar imágenes cómo su foto de perfil.

3. **Operaciones de facturación**:

    - Creación de nuevas facturas.
    - Visualización de detalles de las facturas.
    - Gestión de facturas existentes.

4. **Paginación de Resultados**:

    - Presentación paginada de resultados para una mejor experiencia de usuario.

Estas funcionalidades proporcionan una base sólida para un sistema de facturación simple y demuestran la integración de
tecnologías como Spring Boot, Thymeleaf y Spring Security en el desarrollo de la aplicación.

## Tecnologías

El proyecto utiliza Spring Boot junto con las siguientes tecnologías y dependencias:

- Spring Boot Starter Data JPA
- Spring Boot Starter Security
- Spring Boot Starter Thymeleaf
- Spring Boot Starter Validation
- Spring Boot Starter Web
- Thymeleaf Extras SpringSecurity6
- Spring Boot DevTools
- MySQL Connector/J
- Spring Boot Starter Test
- Spring Security Test

## Requisitos

- Java 21
- Maven
- MySQL (para ejecutar la aplicación con la base de datos)

## Configuración

1. Clona este repositorio: git clone https://github.com/Stevenrq/facturation-system.git
2. Importa el proyecto en tu IDE favorito como un proyecto Maven.
3. Configura tu base de datos MySQL en el archivo application.properties.
4. Cuentas de usuario:
    - **USER** username: user, password: user
    - **ADMIN** username: admin, password: admin

## Ejecución

Para ejecutar la aplicación, puedes utilizar el siguiente comando Maven:

`mvn spring-boot:run` en la terminal.

## Uso

La aplicación estará disponible en http://localhost:8080.

## Contribuciones

Las contribuciones son bienvenidas. Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Realiza un fork del proyecto.
2. Crea una nueva rama para tu funcionalidad: git checkout -b feature/NuevaFuncionalidad
3. Haz tus cambios y realiza un commit: git commit -am 'Añade nueva funcionalidad'
4. Realiza un push a la rama: git push origin feature/NuevaFuncionalidad
5. Envía un pull request.

## Autor

Steven [stevenrq8@gmail.com](mailto:stevenrq8@gmail.com).

## Licencia

Este proyecto está licenciado bajo la **Licencia MIT**.