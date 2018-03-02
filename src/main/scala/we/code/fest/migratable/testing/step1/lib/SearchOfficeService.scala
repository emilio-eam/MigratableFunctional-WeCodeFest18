package we.code.fest.migratable.testing.step1.lib

import we.code.fest.migratable.testing.step1.model.Office

trait SearchOfficeService {

  def findOffice(country: String, language: String): List[Office]

}

object SearchOfficeService {

  private class Wrapper(f: (String, String) => TraversableOnce[_ <: Office]) extends SearchOfficeService {
    override def findOffice(country: String, language: String) = f(country, language).toList
  }

  def apply(f: (String, String) => TraversableOnce[_ <: Office]): SearchOfficeService = new Wrapper(f)

}