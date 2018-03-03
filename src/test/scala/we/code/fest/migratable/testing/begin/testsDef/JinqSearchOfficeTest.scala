package we.code.fest.migratable.testing.begin.testsDef

import we.code.fest.migratable.testing.begin.model.Office
import org.scalatest.BeforeAndAfterAll
import com.everis.scala.testing.jinq.helper.JinqTestBase
import com.everis.scala.testing.jinq.helper.TestsJinqProvider
import com.everis.scala.testing.jinq.helper.HibernateJpaProvider
import we.code.fest.migratable.testing.begin.lib.JinqSearchOfficeService
import org.hibernate.cfg.AvailableSettings

class JinqSearchOfficeTest extends SearchOfficeTestBase
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

  override lazy val searchService = new JinqSearchOfficeService(jinqProvider.getEntityManager(), jinqProvider.getJinqProvider())

  //def createSource() = jinqProvider.createJinqIterator

  override def beforeAll(): Unit = super.beforeAll()

  override def afterAll(): Unit = super.afterAll()

  override def additionalInit() = jinqProvider.setDefaultPageSize(5)

}
