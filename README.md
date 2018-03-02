Muestra de cómo modificar una función con una query a BBDD para convertirla en una función reutilizable en distintos casos:
 	- Cambio del repositorio de datos. Como podría ser de una BBDD a una lista interna o a un RDD de Spark.
	- Cambio de usarla como servicio a usarla como parte de un batch
	- Cambio para usarla como parte de una red de procesamiento de eventos (aplicable sólo porque la función se puede aplicar a todos los datos o a varias partes, con una distribución cualquiera).
	- Cambio para poder cambiar la propia funcionalidad aplicada sin modificar la propia función.

En todos los casos, los datos se proporcionan como una View de forma que la vista intente aplicar el mejor método para ejecutar la lógica de forma transparente a la función.
