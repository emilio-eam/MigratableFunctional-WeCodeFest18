Muestra de c�mo modificar una funci�n con una query a BBDD para convertirla en una funci�n reutilizable en distintos casos:
- Directamente o como parte de otra funci�n.
- Distintos repositorios de datos. Desde una BBDD (planteamiento original) a un RDD de Spark, pasando por una lista interna (por ejemplo para pruebas unitarias).
- Como servicio o como parte de un batch
- Como parte de una red de procesamiento as�ncrono de eventos (aplicable s�lo porque la funci�n se puede aplicar a todos los datos o a varias partes, con una distribuci�n cualquiera).
- Cambio de la propia funcionalidad aplicada (hasta cierto punto) sin modificar la propia funci�n.

Por el camino se ver�n tambi�n composiciones simples y aplicaci�n parcial de par�metros, as� como wrappers simples para realizar adaptaciones.

Al final de las modificaciones, los datos que no son per se colecciones est�ndar de Scala se proporcionan como una View de forma que la vista intente aplicar el mejor m�todo para ejecutar la l�gica de forma transparente a la funci�n que pasa la l�gica a ejecutar.

Cada paso incluye, en el mismo paquete que la funci�n, un fichero txt con una explicaci�n de qu� se aplica en dicho paso.

Las librer�as utilizadas (Hibernate, Jinq, Spark y Akka) son todas librer�as open-source y que pueden utilizarse sin limitaciones en entornos productivos.

Nota: en las pruebas contra BBDD se est� mostrando la llamada de Hibernate pero no la query real; dado que se est� utilizando una H2, que permite ver las queries realizadas realmente en la BBDD, si se quieren ver dichas queries se puede cambiar, en la primera l�nea del m�todo com.everis.scala.testing.jinq.helper.TestsJinqProvider[T].init(), el texto TRACE_LEVEL_SYSTEM_OUT=1 por TRACE_LEVEL_SYSTEM_OUT=2.