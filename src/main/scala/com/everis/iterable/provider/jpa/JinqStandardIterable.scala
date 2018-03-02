package com.everis.iterable.provider.jpa

import org.jinq.orm.stream.scala.JinqIterator

import scala.collection.IterableView
import scala.collection.generic.CanBuildFrom

/**
  * Created by eaguiler on 15/02/18.
  */
class JinqStandardIterable[T](jinqIterator: JinqIterator[T])
  extends IterableView[T, Iterable[T]] {

  override def iterator: Iterator[T] = jinqIterator

  override protected def underlying: Iterable[T] = jinqIterator.toIterable

  override def filter(p: (T) => Boolean): IterableView[T, Iterable[T]] = try {
    new JinqStandardIterable(jinqIterator.where(p))
  } catch {
    case e: IllegalArgumentException => jinqIterator.toIterable.view.filter(p)
  }

  override def map[B, That](f: (T) => B)(implicit bf: CanBuildFrom[IterableView[T, Iterable[T]], B, That]): That = try {
    new JinqStandardIterable(jinqIterator.select(f)).asInstanceOf[That]
  } catch {
    case e: IllegalArgumentException => jinqIterator.toIterable.view.map(f)
  }

}
