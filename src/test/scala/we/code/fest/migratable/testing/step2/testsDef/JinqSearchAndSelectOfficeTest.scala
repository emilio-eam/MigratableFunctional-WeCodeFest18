package we.code.fest.migratable.testing.step2.testsDef

import we.code.fest.migratable.testing.step2.lib.{InternalSearchOfficeService, JinqIteratorSearchOfficeService}
import we.code.fest.migratable.testing.step2.model.Office
import org.scalatest.BeforeAndAfterAll
import com.everis.scala.testing.jinq.helper.JinqTestBase
import com.everis.scala.testing.jinq.helper.TestsJinqProvider
import com.everis.scala.testing.jinq.helper.HibernateJpaProvider
import org.hibernate.cfg.AvailableSettings

class JinqSearchAndSelectOfficeTest extends SearchOfficeInternalTestBase
  with BeforeAndAfterAll with JinqTestBase[Office] {

  override val jinqProvider = new TestsJinqProvider(
      classOf[Office],
      new HibernateJpaProvider(
          Map(
              AvailableSettings.HBM2DDL_AUTO -> "validate",
              AvailableSettings.SHOW_SQL -> true
              ),
          classOf[Office]
          )
      )

  override val internalService = new JinqIteratorSearchOfficeService

  override def createSource() = jinqProvider.createJinqIterator

  override def beforeAll(): Unit = super.beforeAll()

  override def afterAll(): Unit = super.afterAll()

  override def additionalInit() = jinqProvider.setDefaultPageSize(5)

}
