Muestra de cómo modificar una función con una query a BBDD para convertirla en una función reutilizable en distintos casos:
- Directamente o como parte de otra función.
- Distintos repositorios de datos. Desde una BBDD (planteamiento original) a un RDD de Spark, pasando por una lista interna (por ejemplo para pruebas unitarias).
- Como servicio o como parte de un batch
- Como parte de una red de procesamiento asíncrono de eventos (aplicable sólo porque la función se puede aplicar a todos los datos o a varias partes, con una distribución cualquiera).
- Cambio de la propia funcionalidad aplicada (hasta cierto punto) sin modificar la propia función.

Por el camino se verán también composiciones simples y aplicación parcial de parámetros, así como wrappers simples para realizar adaptaciones.

Al final de las modificaciones, los datos que no son per se colecciones estándar de Scala se proporcionan como una View de forma que la vista intente aplicar el mejor método para ejecutar la lógica de forma transparente a la función que pasa la lógica a ejecutar.

Cada paso incluye, en el mismo paquete que la función, un fichero txt con una explicación de qué se aplica en dicho paso.

Las librerías utilizadas (Hibernate, Jinq, Spark y Akka) son todas librerías open-source y que pueden utilizarse sin limitaciones en entornos productivos.

Nota: en las pruebas contra BBDD se está mostrando la llamada de Hibernate pero no la query real; dado que se está utilizando una H2, que permite ver las queries realizadas realmente en la BBDD, si se quieren ver dichas queries se puede cambiar, en la primera línea del método com.everis.scala.testing.jinq.helper.TestsJinqProvider[T].init(), el texto TRACE_LEVEL_SYSTEM_OUT=1 por TRACE_LEVEL_SYSTEM_OUT=2.