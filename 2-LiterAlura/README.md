<h1> LiterAlura </h1>
<p>Esta es una aplicación de consola que hace uso de la api de Gutendex para consultar información sobre libros, la aplicación permite buscar libros y almacenarla en una base de datos local, y partir de ello realizar distintos tipos de consultas.</p>

## Funcionalidades
- `Buscar libro`: Busca la información de un libro consultando la API de Gutendex, y si se encuentra el recurso es alamcenado en la base de datos local.
- `Mostrar libros registrados.`: Muestra la información de los libros almacenados.
- `Mostrar autores registrados`: Muestra todos los autores de libros almacenados.
- `Mostrar autores vivos en un determinado año`: Muestra solo aquellos autores de libros vivos en un año dado consultado a la base de datos local.
- `Mostrar libros por idioma`: Muestra la información de solo aquellos libros que estén en el idioma seleccionado.
- `Mostrar libros por idioma`: Muestra la información estadística (total de libros) de solo aquellos libros que estén en el idioma seleccionado.
- `Salir`: Termina la ejecución de la aplicación.

## Dependencias
- `PostgreSQL`: Es necesario instalar PostgreSQL ya que es el motor de base de datos elegida para almacenar los datos de la aplicación.