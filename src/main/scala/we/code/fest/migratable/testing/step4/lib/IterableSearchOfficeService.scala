package we.code.fest.migratable.testing.step4.lib

import we.code.fest.migratable.testing.step4.model.Office

class IterableSearchOfficeService extends InternalSearchOfficeService[Iterable[_ <: Office]] {

  override def findOffice(officeData: Iterable[_ <: Office], country: String, language: String): Iterable[_ <: Office] = {
		var newOfficeData = officeData
		if ((country != null) && (!country.isEmpty())) {
			newOfficeData = newOfficeData.filter(_.country == country)
		}
		if ((language != null) && (!language.isEmpty())) {
			newOfficeData = newOfficeData.filter(_.language == language)
		}
		return newOfficeData
	}

}
