package we.code.fest.migratable.testing.step4.testsDef

import we.code.fest.migratable.testing.step4.lib.InternalSearchOfficeService
import we.code.fest.migratable.testing.step4.model.Office
import we.code.fest.migratable.testing.step4.lib.IterableSearchOfficeService

class ListTests extends SearchOfficeInternalTestBase[Iterable[_ <: Office]] {

  override def createSource(): List[Office] = allData

  override val internalService = new IterableSearchOfficeService

}
