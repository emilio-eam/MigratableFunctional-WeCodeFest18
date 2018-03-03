package we.code.fest.migratable.testing.step1.lib

import we.code.fest.migratable.testing.step1.model.Office
import org.jinq.orm.stream.scala.JinqIterator

class JinqIteratorSearchOfficeService extends InternalSearchOfficeService {

  override def findOffice(officeData: JinqIterator[Office], country: String, language: String): List[Office] = {
		var newOfficeData = officeData
		if ((country != null) && (!country.isEmpty())) {
			newOfficeData = newOfficeData.where(_.getCountry() == country)
		}
		if ((language != null) && (!language.isEmpty())) {
			newOfficeData = newOfficeData.where(_.getLanguage() == language)
		}
		return newOfficeData.toList
	}

}
