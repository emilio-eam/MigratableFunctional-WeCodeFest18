package we.code.fest.migratable.testing.testsDef

object SearchOfficeTestsData {

  val data = List(
      ("Andorra La Vella", "Andorra", "CA", 1990),
      ("Buenos Aires", "Argentina", "ES", 1996),
      ("Tucuman", "Argentina", "ES", 1990),
      ("Brussels", "Belgium", "ES", 2010),
      ("São Paulo", "Brazil", "PT", 1990),
      ("Uberlândia", "Brazil", "PT", 1990),
      ("Concepción", "Chile", "ES", 1990),
      ("Santiago de Chile", "Chile", "ES", 1996),
      ("Temuco", "Chile", "ES", 1990),
      ("Bogotá", "Colombia", "ES", 1990),
      ("Medellín", "Colombia", "ES", 1990),
      ("Milan", "Italy", "IT", 2000),
      ("Rome", "Italy", "ES", 1990),
      ("Luxemburg", "Luxembourg", "FR", 1990),
      ("CARSO", "Mexico", "ES", 1990),
      ("Querétaro", "Mexico", "ES", 1990),
      ("Sevilla", "Mexico", "ES", 1990),
      ("Tetouan", "Morocco", "ARB", 1990),
      ("Amsterdam", "Netherlands", "NL", 1990),
      ("Lima", "Peru", "ES", 2010),
      ("Lisbon", "Portugal", "PT", 1990),
      ("A Coruña", "Spain", "GL", 1990),
      ("Alicante", "Spain", "CA", 1990),
      ("Alicante 2", "Spain", "CA", 1990),
      ("Barcelona", "Spain", "CA", 1990),
      ("Barcelona BPO", "Spain", "CA", 1990),
      ("Bilbao", "Spain", "EU", 1990),
      ("Ciudad Real", "Spain", "ES", 1990),
      ("Madrid", "Spain", "ES", 1996),
      ("Murcia", "Spain", "ES", 2007),
      ("Santiago de Compostela", "Spain", "ES", 2017),
      ("Sevilla", "Spain", "ES", 1990),
      ("Valencia", "Spain", "CA", 1990),
      ("Valladolid", "Spain", "ES", 1990),
      ("Zaragoza", "Spain", "ES", 1990),
      ("Glasgow", "United Kingdom", "EN", 1990),
      ("London", "United Kingdom", "EN", 1990),
      ("Milton Keynes", "United Kingdom", "EN", 1990),
      ("Birmingham", "United States of America", "EN", 1990),
      ("Boston", "United States of America", "EN", 1990),
      ("Washington D.C.", "United States of America", "EN", 1990)
    )

  def noFilter[T](f: ((Int, String, String, String, Int)) => T): List[T] = data.map(d => (data.indexOf(d), d._1, d._2, d._3, d._4)).map(f)
      
  def countrySpain[T](f: ((Int, String, String, String, Int)) => T) = List(
      ("A Coruña", "Spain", "GL", 1990),
      ("Alicante", "Spain", "CA", 1990),
      ("Alicante 2", "Spain", "CA", 1990),
      ("Barcelona", "Spain", "CA", 1990),
      ("Barcelona BPO", "Spain", "CA", 1990),
      ("Bilbao", "Spain", "EU", 1990),
      ("Ciudad Real", "Spain", "ES", 1990),
      ("Madrid", "Spain", "ES", 1996),
      ("Murcia", "Spain", "ES", 2007),
      ("Santiago de Compostela", "Spain", "ES", 2017),
      ("Sevilla", "Spain", "ES", 1990),
      ("Valencia", "Spain", "CA", 1990),
      ("Valladolid", "Spain", "ES", 1990),
      ("Zaragoza", "Spain", "ES", 1990)
    ).map(d => (data.indexOf(d), d._1, d._2, d._3, d._4)).map(f)

  def langNA[T](f: ((Int, String, String, String, Int)) => T) = List()

  def langES[T](f: ((Int, String, String, String, Int)) => T) = List(
      ("Buenos Aires", "Argentina", "ES", 1996),
      ("Tucuman", "Argentina", "ES", 1990),
      ("Brussels", "Belgium", "ES", 2010),
      ("Concepción", "Chile", "ES", 1990),
      ("Santiago de Chile", "Chile", "ES", 1996),
      ("Temuco", "Chile", "ES", 1990),
      ("Bogotá", "Colombia", "ES", 1990),
      ("Medellín", "Colombia", "ES", 1990),
      ("Rome", "Italy", "ES", 1990),
      ("CARSO", "Mexico", "ES", 1990),
      ("Querétaro", "Mexico", "ES", 1990),
      ("Sevilla", "Mexico", "ES", 1990),
      ("Lima", "Peru", "ES", 2010),
      ("Ciudad Real", "Spain", "ES", 1990),
      ("Madrid", "Spain", "ES", 1996),
      ("Murcia", "Spain", "ES", 2007),
      ("Santiago de Compostela", "Spain", "ES", 2017),
      ("Sevilla", "Spain", "ES", 1990),
      ("Valladolid", "Spain", "ES", 1990),
      ("Zaragoza", "Spain", "ES", 1990)
    ).map(d => (data.indexOf(d), d._1, d._2, d._3, d._4)).map(f)

  def countrySpainLangEN[T](f: ((Int, String, String, String, Int)) => T) = List()

  def countrySpainLangES[T](f: ((Int, String, String, String, Int)) => T) = List(
      ("Ciudad Real", "Spain", "ES", 1990),
      ("Madrid", "Spain", "ES", 1996),
      ("Murcia", "Spain", "ES", 2007),
      ("Santiago de Compostela", "Spain", "ES", 2017),
      ("Sevilla", "Spain", "ES", 1990),
      ("Valladolid", "Spain", "ES", 1990),
      ("Zaragoza", "Spain", "ES", 1990)
    ).map(d => (data.indexOf(d), d._1, d._2, d._3, d._4)).map(f)

  def namesCountrySpain = List(
      "A Coruña",
      "Alicante",
      "Alicante 2",
      "Barcelona",
      "Barcelona BPO",
      "Bilbao",
      "Ciudad Real",
      "Madrid",
      "Murcia",
      "Santiago de Compostela",
      "Sevilla",
      "Valencia",
      "Valladolid",
      "Zaragoza"
    )

  def namesLangNA = List()

  def namesLangES = List(
      "Buenos Aires",
      "Tucuman",
      "Brussels",
      "Concepción",
      "Santiago de Chile",
      "Temuco",
      "Bogotá",
      "Medellín",
      "Rome",
      "CARSO",
      "Querétaro",
      "Sevilla",
      "Lima",
      "Ciudad Real",
      "Madrid",
      "Murcia",
      "Santiago de Compostela",
      "Sevilla",
      "Valladolid",
      "Zaragoza"
    )

  def namesCountrySpainLangEN = List()

  def namesCountrySpainLangES = List(
      "Ciudad Real",
      "Madrid",
      "Murcia",
      "Santiago de Compostela",
      "Sevilla",
      "Valladolid",
      "Zaragoza"
    )

}