package we.code.fest.migratable.testing.step4.testsDef

import com.everis.iterable.provider.spark.rdd.RddIterable
import we.code.fest.migratable.testing.step4.lib.IterableSearchOfficeService
import we.code.fest.migratable.testing.step4.model.Office
import org.apache.spark.{SparkConf, SparkContext}

class ListRDDSearchOfficeTest extends SearchOfficeInternalTestBase[Iterable[_ <: Office]] {

  override val internalService = new IterableSearchOfficeService

  override def createSource(): Iterable[Office] = {
    val context = SparkContext.getOrCreate(new SparkConf().setAppName("test").setMaster("local[2]"))
    return new RddIterable(context.parallelize(allData))
  }

}