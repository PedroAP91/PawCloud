# PawCloud

PawCloud es una aplicación diseñada para mejorar la gestión de clínicas veterinarias de pequeña escala. Este proyecto tiene como objetivo proporcionar una solución asequible y eficiente para que las clínicas optimicen sus recursos sin los altos costos asociados con sistemas más complejos.

## Tabla de Contenidos
- [Descripción General](#descripción-general)
- [Características](#características)
- [Tecnologías Utilizadas](#tecnologías-utilizadas)
- [Instalación](#instalación)
- [Uso](#uso)
- [Desarrollo](#desarrollo)
- [Contribuciones](#contribuciones)
- [Licencia](#licencia)

## Descripción General

PawCloud se desarrolló para agilizar los procesos administrativos y operativos de las clínicas veterinarias. Ofrece funcionalidades como la programación de citas, gestión de registros médicos, control de inventarios y facturación, todo a través de una interfaz intuitiva y fácil de usar. La aplicación garantiza un acceso seguro y centralizado a toda la información crucial mediante almacenamiento en la nube, mejorando la eficiencia operativa y la calidad del servicio al cliente.

## Características

- **Autenticación de Usuarios**: Registro y inicio de sesión seguro.
- **Gestión de Clientes y Mascotas**: Registros detallados de clientes y sus mascotas, incluyendo historiales médicos y registros de vacunación.
- **Programación de Citas**: Fácil programación y seguimiento de citas veterinarias.
- **Gestión de Inventarios**: Seguimiento eficiente de medicamentos, suministros de alimentos y otros elementos de inventario.
- **Facturación y Generación de Recibos**: Generación y gestión automatizada de recibos y facturas.
- **Escalabilidad**: Diseñado para crecer junto con su clínica, permitiendo la adición de múltiples usuarios, funciones y almacenamiento según sea necesario.

## Tecnologías Utilizadas

- **Java**: Lenguaje de programación principal para la lógica de la aplicación.
- **Swing**: Biblioteca de Java para construir interfaces gráficas de usuario.
- **JDBC**: Conectividad de bases de datos Java para interacciones con bases de datos.
- **MySQL**: Sistema de gestión de bases de datos.
- **AWS**: Almacenamiento y servicios en la nube.
- **BCrypt**: Para hashing seguro de contraseñas.
- **Maven**: Gestión de proyectos y dependencias.
- **GitHub**: Sistema de control de versiones.
- **Excalidraw**: Herramienta para diseñar interfaces de usuario.
- **DBeaver**: Herramienta de gestión de bases de datos.

## Instalación

1. **Clonar el Repositorio**:
   ```bash
   git clone https://github.com/PedroAP91/PAWCLOUD.git
   cd PAWCLOUD
Instalar Dependencias:
Asegúrate de tener Java y Maven instalados. Instala las bibliotecas requeridas usando Maven:

bash
Copiar código
mvn install
Configurar la Base de Datos:

Configura tu base de datos MySQL.
Actualiza los detalles de conexión a la base de datos en la configuración de la aplicación.
Ejecutar la Aplicación:

bash
Copiar código
mvn exec:java
Uso
Inicio de Sesión: Inicia la aplicación e inicia sesión con tus credenciales.
Panel de Control: Accede a varios paneles para gestionar clientes, mascotas, citas, inventarios y facturación.
Configuración: Configura cuentas de usuario y otros ajustes administrativos.
Desarrollo
Metodología
El proyecto siguió un enfoque de desarrollo incremental, dividiendo los requisitos en pequeñas unidades funcionales. Cada unidad se diseñó, implementó, probó y evaluó individualmente.

Estrategia de Ramas
El desarrollo se gestionó utilizando GitHub con ramas de características. Cada nueva característica o corrección de errores se implementó en su propia rama y se fusionó con la rama principal después de la revisión y las pruebas.

Herramientas
Eclipse IDE: Utilizado para codificación y depuración.
Excalidraw: Para diseñar maquetas de UI.
WindowBuilder: Plugin de Eclipse para diseñar componentes GUI.
DBeaver: Para gestionar la base de datos.
Contribuciones
¡Las contribuciones son bienvenidas! Por favor, haz un fork del repositorio y crea un pull request con tus cambios. Asegúrate de que tu código siga el estilo existente e incluya pruebas apropiadas.

Licencia
Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.
