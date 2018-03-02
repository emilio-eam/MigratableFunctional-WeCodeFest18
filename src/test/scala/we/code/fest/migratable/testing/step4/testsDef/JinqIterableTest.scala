package we.code.fest.migratable.testing.step4.testsDef

import we.code.fest.migratable.testing.step4.lib.{InternalSearchOfficeService, IterableSearchOfficeService}
import we.code.fest.migratable.testing.step4.model.Office
import org.scalatest.BeforeAndAfterAll
import com.everis.scala.testing.jinq.helper.JinqTestBase
import com.everis.scala.testing.jinq.helper.TestsJinqProvider
import com.everis.scala.testing.jinq.helper.HibernateJpaProvider
import we.code.fest.migratable.testing.step4.entities.OfficeEntity
import org.hibernate.cfg.AvailableSettings

class JinqIterableTest extends SearchOfficeInternalTestBase[Iterable[_ <: Office]]
  with BeforeAndAfterAll with JinqTestBase[OfficeEntity] {

  override val jinqProvider = new TestsJinqProvider(
      classOf[OfficeEntity],
      new HibernateJpaProvider(
          Map(
              AvailableSettings.HBM2DDL_AUTO -> "validate",
              AvailableSettings.SHOW_SQL -> true
              ),
          classOf[OfficeEntity]
          )
      )

  override val internalService = new IterableSearchOfficeService

  override def createSource() = jinqProvider.createStandardIterable().asInstanceOf[Iterable[_ <: Office]]

  override def beforeAll(): Unit = super.beforeAll()

  override def afterAll(): Unit = super.afterAll()

  override def additionalInit() = {
    jinqProvider.registerAssociationAttribute(classOf[Office].getDeclaredMethod("name"), "name", false)
    jinqProvider.registerAssociationAttribute(classOf[Office].getDeclaredMethod("country"), "country", false)
    jinqProvider.registerAssociationAttribute(classOf[Office].getDeclaredMethod("language"), "language", false)
    jinqProvider.registerAssociationAttribute(classOf[Office].getDeclaredMethod("year"), "year", false)
    jinqProvider.setDefaultPageSize(5)
  }

}
