<h1> Foro Hub </h1>
<p>Esta aplicación backend que proporciona una API para un foro, permitiendo la creación de temas (tópicos) y el registro de respuestas a través de la interacción con los endpoints programados.</p>

## Funcionalidades
### Autenticación
- `Login`: Genera el token de autenticación.
### Operaciones con tópicos
- `Listar`
- `Registrar`
- `Actualizar`
- `Eliminar`

### Operaciones con tópicos/respuestas
- `Listar`
- `Registrar`
- `Actualizar`
- `Eliminar`

### Documentación API
- `Se puede acceder a la documentación de los endpoints en /swagger-ui/index.html`

## Dependencias
- `PostgreSQL`: Es necesario instalar PostgreSQL ya que es el motor de base de datos elegida para almacenar los datos de la aplicación.

## ¿Qué falta por hacer?
- `Gestión de usuarios`
- `Aplicar algunas políticas de negocio al actualizar/eliminar algún tópico o respuesta`