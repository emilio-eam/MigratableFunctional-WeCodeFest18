package com.everis.scala.testing.jinq.helper

import java.util.{Arrays, Collections, Properties}
import javax.persistence.spi.{ClassTransformer, PersistenceUnitInfo, PersistenceUnitTransactionType}
import javax.persistence.{EntityManager, EntityManagerFactory}
import javax.sql.DataSource

import com.everis.iterable.provider.jpa.JinqStandardIterable
import liquibase.{Contexts, LabelExpression, Liquibase}
import liquibase.database.DatabaseFactory
import liquibase.database.jvm.JdbcConnection
import liquibase.resource.ClassLoaderResourceAccessor
import org.h2.jdbcx.JdbcConnectionPool
import org.hibernate.cfg.AvailableSettings
import org.hibernate.jpa.HibernatePersistenceProvider
import org.jinq.jpa.JinqJPAScalaIteratorProvider
import org.jinq.orm.stream.scala.JinqIterator
import org.scalatest.BeforeAndAfterAll

trait JinqTestBase[O] {

  self: BeforeAndAfterAll =>

  val jinqProvider: TestsJinqProvider[O]

  override def beforeAll(): Unit = {
    jinqProvider.init
    additionalInit
  }

  def additionalInit() = {}

  override def afterAll(): Unit = jinqProvider.freeResources

}

class TestsJinqProvider[T](entityClass: Class[T], jpaProvider: JpaProvider) {

  private var ds = Option.empty[DataSource]
  private var jinqProvider = Option.empty[JinqJPAScalaIteratorProvider]
  //private var iterableProvider = Option.empty[JinqJPAIterableProvider]
  private var em: EntityManager = _
  private var emf: EntityManagerFactory = _

  def init(): Unit = {
    val newDs = JdbcConnectionPool.create("jdbc:h2:mem:liquibaseSchema" + TestsJinqProvider.getCounter() + ";TRACE_LEVEL_SYSTEM_OUT=1", "TestDB", "TestDB")
    // Update Datasource with Liquibase
    val conn = newDs.getConnection
    try {
      val liquibaseDb = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(new JdbcConnection(conn))
      liquibaseDb.setDefaultSchemaName(null)
      val liquibase = new Liquibase("liquibase/changelog.xml", new ClassLoaderResourceAccessor(), liquibaseDb)
      liquibase.setChangeLogParameter("contexts", "local")
      liquibase.update(new Contexts("local"), new LabelExpression(null.asInstanceOf[String]))
    } finally {
      conn.close
    }
    // Create EntityManager
    emf = jpaProvider.createEntityManagerFactory(newDs)
    em = jpaProvider.createEntityManager(emf)
    ds = Option(newDs)
    // Create jinqProvider
    jinqProvider = Option(new JinqJPAScalaIteratorProvider(emf))
    // Create streamProvider
    //streamProvider = Option(new JinqJPAStandardStreamProvider(jinqProvider.get))
  }

  def registerAssociationAttribute(m: java.lang.reflect.Method, fieldName: String, isPlural: Boolean): Unit = jinqProvider.get.registerAssociationAttribute(m, fieldName, isPlural)
  def registerAttributeConverterType[U](convertedType: Class[U]): Unit = jinqProvider.get.registerAttributeConverterType(convertedType)

  def setDefaultPageSize(size: Int): Unit = jinqProvider.get.setHint("automaticPageSize", size.asInstanceOf[Integer])

  def createJinqIterator(): JinqIterator[T] = jinqProvider.get.streamAll(em, entityClass)//.setHint("automaticPageSize", 5.asInstanceOf[Integer])

  def createStandardIterable(): Iterable[T] = new JinqStandardIterable(createJinqIterator)

  def getJinqProvider(): JinqJPAScalaIteratorProvider = jinqProvider.get

  def getEntityManager(): EntityManager = em

  def freeResources(): Unit = {
    if (ds.isDefined) {
      try {
        em.close
        emf.close
        ds.get.asInstanceOf[JdbcConnectionPool].dispose
      } finally {
        ds = Option.empty
        //streamProvider = Option.empty
        jinqProvider = Option.empty
        em = null
        emf = null
      }
    }
  }

}

object TestsJinqProvider {
  private var counter = 0
  def getCounter(): Int = {counter += 1; return counter}
}

trait JpaProvider {

  def createEntityManagerFactory(ds: DataSource): EntityManagerFactory

  def createEntityManager(emf: EntityManagerFactory) = emf.createEntityManager

  def close(em: EntityManager) = em.close

  def close(emf: EntityManagerFactory) = emf.close

}

class HibernateJpaProvider(exclUnlistedClasses: Boolean, mappingFiles: java.util.List[String], managedClasses: java.util.List[String], emfProperties: Map[_, _]) extends JpaProvider {

  import scala.collection.JavaConverters._

  def this(emfProperties: Map[_, _]) = this(false, Collections.emptyList[String], Collections.emptyList[String], emfProperties)
  def this(mappingFiles: List[String], emfProperties: Map[_, _]) = this(!mappingFiles.isEmpty, Arrays.asList(mappingFiles: _*), Collections.emptyList[String](), emfProperties)
  def this(mappingFiles: Iterable[String], emfProperties: Map[_, _]) = this(mappingFiles.toList, emfProperties)
  def this(emfProperties: Map[_, _], managedClasses: Class[_]*) = this(!managedClasses.isEmpty, Collections.emptyList[String](), Arrays.asList(managedClasses.map(_.getName): _*), emfProperties)

  override def createEntityManagerFactory(ds: DataSource) = new HibernatePersistenceProvider()
    .createContainerEntityManagerFactory(
      new PersistenceUnitInfo() {
        override def getPersistenceUnitName = "ApplicationPersistenceUnit"
        override def getPersistenceProviderClassName = "org.hibernate.jpa.HibernatePersistenceProvider"
        override def getTransactionType = PersistenceUnitTransactionType.RESOURCE_LOCAL
        override def getJtaDataSource = null
        override def getNonJtaDataSource = ds
        override def getMappingFileNames = mappingFiles
        override def getJarFileUrls = if (exclUnlistedClasses) Collections.emptyList() else Collections.list(this.getClass.getClassLoader.getResources(""))
        override def getPersistenceUnitRootUrl = null
        override def getManagedClassNames = managedClasses
        override def excludeUnlistedClasses = exclUnlistedClasses
        override def getSharedCacheMode = null
        override def getValidationMode = null
        override def getProperties = new Properties
        override def getPersistenceXMLSchemaVersion = null
        override def getClassLoader = null
        override def addTransformer(transformer: ClassTransformer) = ()
        override def getNewTempClassLoader = null
      },
      emfProperties.asJava)

}

object HibernateJpaProvider extends HibernateJpaProvider(
  Map(
    AvailableSettings.HBM2DDL_AUTO -> "validate",
    AvailableSettings.SHOW_SQL -> true
  )
)