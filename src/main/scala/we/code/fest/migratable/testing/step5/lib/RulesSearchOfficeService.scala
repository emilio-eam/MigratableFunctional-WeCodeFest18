package we.code.fest.migratable.testing.step5.lib

import we.code.fest.migratable.testing.step5.model.Office

/**
  * Created by eaguiler on 16/02/18.
  */
case class Rule(appliesTo: Condition => Boolean, content: Condition => (Office => Boolean))

class RulesSearchOfficeService {

  def findOffice(rules: Iterable[Rule], officeData: Iterable[Office], country: Option[String], language: Option[String]): Iterable[Office] = findOffice(rules, officeData, new Condition(country, language))

  def findOffice(rules: Iterable[Rule], officeData: Iterable[Office], cond: Condition): Iterable[Office] =
    rules
      .filter(_.appliesTo(cond))
      .map(_.content(cond))
      .foldLeft (officeData) (_.filter(_)) //((data, c) => data.filter(c))

}

object DefaultRules {
  val rules = List(
    Rule(_.country.exists(!_.isEmpty),
      condition => office => condition.country.contains(office.country)
    ),
    Rule(_.language.exists(!_.isEmpty),
      condition => office => condition.language.contains(office.language)
    )
  )
}

case class Condition(country: Option[String], language: Option[String])
