package we.code.fest.migratable.testing.step1.testsDef

import we.code.fest.migratable.testing.step1.model.Office
import we.code.fest.migratable.testing.step1.lib.SearchOfficeService
import org.scalatest.FlatSpec
import we.code.fest.migratable.testing.testsDef.SearchOfficeTestsData._

abstract class SearchOfficeTestBase extends FlatSpec {

  val searchService: SearchOfficeService

  def getOffice(name: String, country: String, language: String, year: Integer): Office = {
    var office = new Office
    office.setName(name)
    office.setCountry(country)
    office.setLanguage(language)
    office.setYear(year)
    return office
  }

  def additionalChecks(): Unit = {}

  lazy val allData: List[Office] = noFilter(d => getOffice(d._2, d._3, d._4, d._5))

  "List test" should "return everything without filters" in {
		val result = searchService.findOffice("", "")
    assertResult(allData, "Result was not the expected")(result)
    additionalChecks
	}

  it should "filter by Country" in {
    val result = searchService.findOffice("Spain", "")
    assertResult(countrySpain(d => getOffice(d._2, d._3, d._4, d._5)), "Result was not the expected")(result)
    additionalChecks
	}

  it should "return empty filtering by unknown Language" in {
    val result = searchService.findOffice("", "NA")
    assertResult(langNA(d => getOffice(d._2, d._3, d._4, d._5)), "Result was not the expected")(result)
    additionalChecks
	}

  it should "filter by Language" in {
    val result = searchService.findOffice("", "ES")
    assertResult(langES(d => getOffice(d._2, d._3, d._4, d._5)), "Result was not the expected")(result)
    additionalChecks
  }

  it should "filter by both Country and Language (no results)" in {
    val result = searchService.findOffice("Spain", "EN")
    assertResult(countrySpainLangEN(d => getOffice(d._2, d._3, d._4, d._5)), "Result was not the expected")(result)
    additionalChecks
  }

  it should "filter by both Country and Language" in {
    val result = searchService.findOffice("Spain", "ES")
    assertResult(countrySpainLangES(d => getOffice(d._2, d._3, d._4, d._5)), "Result was not the expected")(result)
    additionalChecks
  }
/*
  "Names test" should "return everyone without filters" in {
    val result = searchNameService.findOfficeName("", "")
    assertResult(allData.map(_.name), "Result was not the expected")(result)
    additionalChecks
  }

  it should "filter by Country" in {
    val result = searchNameService.findOfficeName("Spain", "")
    assertResult(namesCountrySpain, "Result was not the expected")(result)
    additionalChecks
  }

  it should "return empty filtering by unknown Language" in {
    val result = searchNameService.findOfficeName("", "NA")
    assertResult(namesLangNA, "Result was not the expected")(result)
    additionalChecks
  }

  it should "filter by Language" in {
    val result = searchNameService.findOfficeName("", "ES")
    assertResult(namesLangES, "Result was not the expected")(result)
    additionalChecks
  }

  it should "filter by both Country and Language (no results)" in {
    val result = searchNameService.findOfficeName("Spain", "EN")
    assertResult(namesCountrySpainLangEN, "Result was not the expected")(result)
    additionalChecks
  }

  it should "filter by both Country and Language" in {
    val result = searchNameService.findOfficeName("Spain", "ES")
    assertResult(namesCountrySpainLangES, "Result was not the expected")(result)
    additionalChecks
  }
*/
}