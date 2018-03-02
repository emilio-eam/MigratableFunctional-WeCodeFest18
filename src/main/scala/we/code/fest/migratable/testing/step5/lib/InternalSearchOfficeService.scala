package we.code.fest.migratable.testing.step5.lib

import we.code.fest.migratable.testing.step5.model.Office

trait InternalSearchOfficeService {

  def findOffice(officeData: Iterable[Office], country: Option[String], language: Option[String]): Iterable[Office]

  implicit class InternalSearchOfficeServiceToFunctionAdapter(service: InternalSearchOfficeService) extends ((Iterable[Office], Option[String], Option[String]) => Iterable[Office]) {
    override def apply(t: Iterable[Office], country: Option[String], language: Option[String]) = service.findOffice(t, country, language)
  }

  implicit class InternalSearchOfficeServiceToNonOptionFunctionAdapter(service: InternalSearchOfficeService) extends ((Iterable[Office], String, String) => Iterable[Office]) {
    override def apply(t: Iterable[Office], country: String, language: String) = service.findOffice(t, Option(country), Option(language))
  }

}

object InternalSearchOfficeService {

  private class Wrapper(f: (Iterable[Office], Option[String], Option[String]) => Iterable[Office]) extends InternalSearchOfficeService {
    override def findOffice(officeData: Iterable[Office], country: Option[String], language: Option[String]) = f(officeData, country, language)
  }

  def apply(f: (Iterable[Office], Option[String], Option[String]) => Iterable[Office]): InternalSearchOfficeService = new Wrapper(f)

}