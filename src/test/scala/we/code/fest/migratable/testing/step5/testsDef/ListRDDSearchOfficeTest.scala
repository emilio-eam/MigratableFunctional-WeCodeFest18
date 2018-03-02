package we.code.fest.migratable.testing.step5.testsDef

import com.everis.iterable.provider.spark.rdd.RddIterable
import we.code.fest.migratable.testing.step5.lib.RulesSearchOfficeService
import we.code.fest.migratable.testing.step5.model.Office
import org.apache.spark.{SparkConf, SparkContext}

class ListRDDSearchOfficeTest extends SearchOfficeInternalTestBase {

  override def createSource(): Iterable[Office] = {
    val context = SparkContext.getOrCreate(new SparkConf().setAppName("test").setMaster("local[2]"))
    return new RddIterable(context.parallelize(allData))
  }

}