package we.code.fest.migratable.testing.begin.lib

import we.code.fest.migratable.testing.begin.model.Office
import org.jinq.orm.stream.scala.JinqIterator
import org.jinq.jpa.JinqJPAScalaIteratorProvider
import javax.persistence.EntityManager

class JinqSearchOfficeService(em: EntityManager, provider: JinqJPAScalaIteratorProvider) extends SearchOfficeService {

  override def findOffice(country: String, language: String): List[Office] = {
		var officeData: JinqIterator[Office] = provider.streamAll(em, classOf[Office])
		if ((country != null) && (!country.isEmpty())) {
			officeData = officeData.where(_.getCountry() == country)
		}
		if ((language != null) && (!language.isEmpty())) {
			officeData = officeData.where(_.getLanguage() == language)
		}
		return officeData.toList
	}

}
