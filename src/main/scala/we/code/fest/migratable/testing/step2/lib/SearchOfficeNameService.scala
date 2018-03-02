package we.code.fest.migratable.testing.step2.lib

import we.code.fest.migratable.testing.step2.model.Office

trait SearchOfficeNameService {

  def findOfficeName(country: String, language: String): List[String]

}

object SearchOfficeNameService {

  private class Wrapper(f: (String, String) => TraversableOnce[String]) extends SearchOfficeNameService {
    override def findOfficeName(country: String, language: String) = f(country, language).toList
  }

  def apply(f: (String, String) => TraversableOnce[String]): SearchOfficeNameService = new Wrapper(f)

}