package we.code.fest.migratable.testing.step5.testsDef

import we.code.fest.migratable.testing.step5.lib.RulesSearchOfficeService
import we.code.fest.migratable.testing.step5.model.Office
import com.everis.scala.testing.jinq.helper.TestsJinqProvider
import we.code.fest.migratable.testing.step5.entities.OfficeEntity
import com.everis.scala.testing.jinq.helper.HibernateJpaProvider
import org.hibernate.cfg.AvailableSettings
import we.code.fest.migratable.testing.step5.lib.DefaultRules

object SampleBatch {

  def main(args: Array[String]) = {
    val batchFunction = new RulesSearchOfficeService().findOffice(DefaultRules.rules, _: Iterable[_ <: Office], Option("Spain"), null)

    val sourceFunction = initSource()
    val processFunction = batchFunction
      .andThen(_.map(o => "Office: " + o.name + " (" + o.country + ")"))
      .andThen(_.foreach(println(_)))

    val batchProcess = () => processFunction(sourceFunction)

    println("Starting computing...")
    batchProcess()
    println("Process finished")
  }

  def initSource: () => Iterable[_ <: Office] = {
    val source = new TestsJinqProvider(
      classOf[OfficeEntity],
      new HibernateJpaProvider(
          Map(
              AvailableSettings.HBM2DDL_AUTO -> "validate",
              AvailableSettings.SHOW_SQL -> true
              ),
          classOf[OfficeEntity]
          )
      )
    source.init
    source.registerAssociationAttribute(classOf[Office].getDeclaredMethod("name"), "name", false)
    source.registerAssociationAttribute(classOf[Office].getDeclaredMethod("country"), "country", false)
    source.registerAssociationAttribute(classOf[Office].getDeclaredMethod("language"), "language", false)
    source.registerAssociationAttribute(classOf[Office].getDeclaredMethod("year"), "year", false)
    source.setDefaultPageSize(5)
    () => source.createStandardIterable
  }

}