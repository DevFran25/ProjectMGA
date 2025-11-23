📁 ProjectMGA

Proyecto Java estructurado por capas para manejar lógica de negocio, modelos, servicios y servlets dentro de una aplicación web.

🧱 Estructura del proyecto

```src/main/java/pe/edu/utp/
  business/     -> Reglas y lógica de negocio
  exceptions/   -> Excepciones personalizadas
  filters/      -> Filtros para peticiones (validaciones, seguridad, etc.)
  model/        -> Clases del modelo / entidades
  service/      -> Servicios y acceso a datos o procesos del sistema
  servlets/     -> Controladores HTTP (manejan solicitudes y respuestas)
  util/         -> Funciones de ayuda (helpers)
  utils/        -> Utilidades adicionales
App.java        -> Punto de entrada de la app (si se usa con main)
```



🛠️ Tecnologías utilizadas

```
Java 11+

Maven (gestión del proyecto y dependencias)

Servlets (Java EE / Jakarta) para el manejo de peticiones HTTP

Arquitectura en capas / MVC
```

