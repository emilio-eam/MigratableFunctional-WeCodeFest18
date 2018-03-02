package we.code.fest.migratable.testing.begin.lib

import we.code.fest.migratable.testing.begin.model.Office
import org.jinq.orm.stream.scala.JinqIterator

class JinqSearchOfficeService(provider: () => JinqIterator[Office]) extends SearchOfficeService {

  override def findOffice(country: String, language: String): List[Office] = {
		var officeData = provider()
		if ((country != null) && (!country.isEmpty())) {
			officeData = officeData.where(_.getCountry() == country)
		}
		if ((language != null) && (!language.isEmpty())) {
			officeData = officeData.where(_.getLanguage() == language)
		}
		return officeData.toList
	}

}
