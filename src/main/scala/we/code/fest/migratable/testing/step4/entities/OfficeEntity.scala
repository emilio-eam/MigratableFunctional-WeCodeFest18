package we.code.fest.migratable.testing.step4.entities

import javax.persistence._
import we.code.fest.migratable.testing.step4.model.Office

/**
  * Created by eaguiler on 9/02/18.
  */
@Entity(name = "OFFICE")
@Access(AccessType.FIELD)
class OfficeEntity(officeId: Int, officeName: String, officeCountry: String, officeLanguage: String, officeYear: Int) extends Office(officeName, officeCountry, officeLanguage, officeYear) {
  private def this() = this(-1, null, null, null, -1)
  @Id val id = officeId
  override val name = officeName
  override val country = officeCountry
  override val language = officeLanguage
  override val year = officeYear
}
