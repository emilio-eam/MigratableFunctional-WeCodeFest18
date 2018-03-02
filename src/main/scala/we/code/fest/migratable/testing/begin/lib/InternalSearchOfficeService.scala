package we.code.fest.migratable.testing.begin.lib

import we.code.fest.migratable.testing.begin.model.Office
import org.jinq.orm.stream.scala.JinqIterator

trait InternalSearchOfficeService {

  def findOffice(officeData: JinqIterator[Office], country: String, language: String): JinqIterator[Office]

  implicit class InternalSearchOfficeServiceToFunctionAdapter(service: InternalSearchOfficeService) extends ((JinqIterator[Office], String, String) => JinqIterator[Office]) {
    override def apply(t: JinqIterator[Office], country: String, language: String) = service.findOffice(t, country, language)
  }

}
