package we.code.fest.migratable.testing.step2.model

import javax.persistence.Entity
import javax.persistence.Access
import javax.persistence.AccessType
import javax.persistence.Id
import javax.persistence.Column
import scala.beans.BeanProperty
import scala.annotation.meta.getter

@Entity(name = "OFFICE")
class Office extends Equals {
    @Id @getter
    @BeanProperty
    var id: Int = -1

    @Column @getter
    @BeanProperty
    var name: String = null

    @Column @getter
    @BeanProperty
    var country: String = null

    @Column @getter
    @BeanProperty
    var language: String = null

    @Column @getter
    @BeanProperty
    var year: Int = -1

  def canEqual(other: Any): Boolean = other.isInstanceOf[Office]

  override def equals(other: Any): Boolean = other match {
    case that: Office =>
      (that canEqual this) &&
        name == that.name &&
        country == that.country &&
        language == that.language &&
        year == that.year
    case _ => false
  }

  override def hashCode(): Int = {
    val state = Seq(name, country, language, year)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  override def toString = s"Office($name, $country, $language, $year)"

}
