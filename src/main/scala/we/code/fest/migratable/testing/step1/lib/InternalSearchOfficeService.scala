package we.code.fest.migratable.testing.step1.lib

import we.code.fest.migratable.testing.step1.model.Office
import org.jinq.orm.stream.scala.JinqIterator

trait InternalSearchOfficeService {

  def findOffice(officeData: JinqIterator[Office], country: String, language: String): List[Office]

  implicit class InternalSearchOfficeServiceToFunctionAdapter(service: InternalSearchOfficeService) extends ((JinqIterator[Office], String, String) => List[Office]) {
    override def apply(t: JinqIterator[Office], country: String, language: String) = service.findOffice(t, country, language)
  }

}
