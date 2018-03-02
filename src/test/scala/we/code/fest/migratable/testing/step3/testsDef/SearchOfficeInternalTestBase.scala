package we.code.fest.migratable.testing.step3.testsDef

import we.code.fest.migratable.testing.step3.lib.{InternalSearchOfficeService, SearchOfficeService, SearchOfficeNameService}
import we.code.fest.migratable.testing.step3.model.Office

import scala.collection.TraversableOnce
import scala.util.Try
import org.jinq.orm.stream.scala.JinqIterator
import scala.language.implicitConversions

/**
  * Created by eaguiler on 7/02/18.
  */
abstract class SearchOfficeInternalTestBase
  extends SearchOfficeTestBase {

  def createSource(): JinqIterator[_ <: Office]
  val internalService: InternalSearchOfficeService

  override lazy val searchService = SearchOfficeInternalTestBase.getSearchOfficeService(internalService, createSource)
  override lazy val searchNameService = SearchOfficeInternalTestBase.getSearchOfficeNameService(internalService, _.select(_.name), createSource)

  class InternalServiceToFunction(service: InternalSearchOfficeService) extends ((JinqIterator[_ <: Office], String, String) => JinqIterator[_ <: Office]) {
    override def apply(data: JinqIterator[_ <: Office], country: String, language: String): JinqIterator[_ <: Office] = service.findOffice(data, country, language)
  }
  implicit def internalServiceToFunction(service: InternalSearchOfficeService): (JinqIterator[_ <: Office], String, String) => JinqIterator[_ <: Office] = new InternalServiceToFunction(service)

}

object SearchOfficeInternalTestBase {

  class ComposableThreeParameterFunction[T1, T2, T3, R](f: ((T1, T2, T3) => R)) {
    def function = f
    def andThen[F](after: R => F): ComposableThreeParameterFunction[T1, T2, T3, F] = new ComposableThreeParameterFunction[T1, T2, T3, F]((t1, t2, t3) => after(f(t1, t2, t3)))
  }
  implicit def andThenThreeParameters[T1, T2, T3, R](f: (T1, T2, T3) => R): ComposableThreeParameterFunction[T1, T2, T3, R] = new ComposableThreeParameterFunction(f)
  implicit def fromAndThenThreeParameters[T1, T2, T3, R](comp: ComposableThreeParameterFunction[T1, T2, T3, R]): (T1, T2, T3) => R = comp.function

  def getSearchOfficeService[T <: TraversableOnce[_ <: Office]](appliedSearchService: (T, String, String) => T, getData: () => T): SearchOfficeService =
    SearchOfficeService(
      withNewSource(
        appliedSearchService,
        getData
      )
    )

  def getSearchOfficeNameService(appliedSearchService: (JinqIterator[_ <: Office], String, String) => JinqIterator[_ <: Office], toNameView: JinqIterator[_ <: Office] => Iterator[String], getData: () => JinqIterator[_ <: Office]): SearchOfficeNameService =
    SearchOfficeNameService(
      withNewSource(
        appliedSearchService.andThen(toNameView),
        getData
      )
    )

  def withNewSource[S, T1, T2, R](f: (S, T1, T2) => R, s: () => S): (T1, T2) => R = tryClose(f, s(), _, _)

  def tryClose[S, T1, T2, R](f: (S, T1, T2) => R, source: S, d1: T1, d2: T2): R = {
      if (source.isInstanceOf[AutoCloseable]) {
        val result = Try(f(source, d1, d2))
        source.asInstanceOf[AutoCloseable].close
        return result.get
      } else {
        return f(source, d1, d2)
      }
    }

}