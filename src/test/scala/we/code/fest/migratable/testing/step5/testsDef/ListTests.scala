package we.code.fest.migratable.testing.step5.testsDef

import we.code.fest.migratable.testing.step5.lib.InternalSearchOfficeService
import we.code.fest.migratable.testing.step5.model.Office
import we.code.fest.migratable.testing.step5.lib.RulesSearchOfficeService

class ListTests extends SearchOfficeInternalTestBase {

  override def createSource(): List[Office] = allData

}
