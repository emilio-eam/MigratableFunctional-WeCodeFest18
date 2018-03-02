package we.code.fest.migratable.testing.step3.lib

import we.code.fest.migratable.testing.step3.model.Office
import org.jinq.orm.stream.scala.JinqIterator

class JinqIteratorSearchOfficeService extends InternalSearchOfficeService {

  override def findOffice(officeData: JinqIterator[_ <: Office], country: String, language: String): JinqIterator[_ <: Office] = {
		var newOfficeData = officeData
		if ((country != null) && (!country.isEmpty())) {
			newOfficeData = newOfficeData.where(_.country == country)
		}
		if ((language != null) && (!language.isEmpty())) {
			newOfficeData = newOfficeData.where(_.language == language)
		}
		return newOfficeData
	}

}
