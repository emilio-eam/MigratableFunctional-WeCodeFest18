package we.code.fest.migratable.testing.step3.lib

import we.code.fest.migratable.testing.step3.model.Office
import org.jinq.orm.stream.scala.JinqIterator

trait InternalSearchOfficeService {

  def findOffice(officeData: JinqIterator[_ <: Office], country: String, language: String): JinqIterator[_ <: Office]

  implicit class InternalSearchOfficeServiceToFunctionAdapter(service: InternalSearchOfficeService) extends ((JinqIterator[_ <: Office], String, String) => JinqIterator[_ <: Office]) {
    override def apply(t: JinqIterator[_ <: Office], country: String, language: String) = service.findOffice(t, country, language)
  }

}
