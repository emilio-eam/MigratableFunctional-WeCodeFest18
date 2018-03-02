Muestra de c�mo modificar una funci�n con una query a BBDD para convertirla en una funci�n reutilizable en distintos casos:
 	- Cambio del repositorio de datos. Como podr�a ser de una BBDD a una lista interna o a un RDD de Spark.
	- Cambio de usarla como servicio a usarla como parte de un batch
	- Cambio para usarla como parte de una red de procesamiento de eventos (aplicable s�lo porque la funci�n se puede aplicar a todos los datos o a varias partes, con una distribuci�n cualquiera).
	- Cambio para poder cambiar la propia funcionalidad aplicada sin modificar la propia funci�n.

En todos los casos, los datos se proporcionan como una View de forma que la vista intente aplicar el mejor m�todo para ejecutar la l�gica de forma transparente a la funci�n.
