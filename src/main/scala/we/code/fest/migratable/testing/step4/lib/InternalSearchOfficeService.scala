package we.code.fest.migratable.testing.step4.lib

import we.code.fest.migratable.testing.step4.model.Office

trait InternalSearchOfficeService[T <: TraversableOnce[_ <: Office]] {

  def findOffice(officeData: T, country: String, language: String): T

  implicit class InternalSearchOfficeServiceToFunctionAdapter[T <: TraversableOnce[_ <: Office]](service: InternalSearchOfficeService[T]) extends ((T, String, String) => T) {
    override def apply(t: T, country: String, language: String) = service.findOffice(t, country, language)
  }

}
