📁 ProjectMGA

Proyecto Java estructurado por capas para manejar lógica de negocio, modelos, servicios y servlets dentro de una aplicación web.

🧱 Estructura del proyecto
src/main/java/pe/edu/utp/
│
├── 📂 business
│     └─ Lógica de negocio (procesos y reglas del sistema)
│
├── 📂 exceptions
│     └─ Excepciones personalizadas del proyecto
│
├── 📂 filters
│     └─ Filtros aplicados a las peticiones (seguridad, validación, etc.)
│
├── 📂 model
│     └─ Entidades / clases del dominio
│
├── 📂 service
│     └─ Servicios que conectan lógica de negocio con datos
│
├── 📂 servlets
│     └─ Controladores HTTP (manejan solicitudes del usuario)
│
├── 📂 util
│     └─ Funciones generales de apoyo
│
├── 📂 utils
│     └─ Utilidades adicionales
│
└── 📄 App.java
       Punto de entrada de la aplicación (si se ejecuta con main)

🎯 ¿Para qué sirve cada parte?

business → Aquí vive la lógica principal del sistema.

service → Métodos que gestionan operaciones y datos.

model → Representa objetos y entidades del proyecto.

servlets → Responden a las peticiones web (controladores).

filters → Se ejecutan antes que los servlets (seguridad/validación).

exceptions → Manejo ordenado de errores.

util / utils → Herramientas y helpers reutilizables.

🛠️ Tecnologías utilizadas

Java 11+

Maven (gestión del proyecto y dependencias)

Servlets (Java EE / Jakarta) para el manejo de peticiones HTTP

Arquitectura en capas / MVC
