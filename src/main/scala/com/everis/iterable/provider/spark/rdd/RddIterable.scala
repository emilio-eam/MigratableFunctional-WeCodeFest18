package com.everis.iterable.provider.spark.rdd

import org.apache.spark.api.java.function.FlatMapFunction
import org.apache.spark.rdd.RDD

import scala.collection.{GenTraversableOnce, IterableView}
import scala.collection.generic.CanBuildFrom
import scala.language.higherKinds

/**
  * Created by eaguiler on 15/02/18.
  */
class RddIterable[T](rdd: RDD[T]) extends IterableView[T, Iterable[T]] {

  override def iterator: Iterator[T] = rdd.toLocalIterator

  override protected def underlying: Iterable[T] = rdd.collect()

  override def map[B, That](f: T => B)(implicit bf: CanBuildFrom[IterableView[T, Iterable[T]], B, That]): That = {
    val finalFunction: org.apache.spark.api.java.function.Function[T, B] = new org.apache.spark.api.java.function.Function[T, B] {override def call(t: T) = f(t)}
    new RddIterable(rdd.toJavaRDD.map(finalFunction)).asInstanceOf[That]
  }

  override def filter(p: T => Boolean): IterableView[T, Iterable[T]] = new RddIterable(rdd.filter(p))

  override def foreach[U](f: T => U): Unit = rdd.foreach(t => f(t))

  override def to[Col[_]](implicit cbf: CanBuildFrom[Nothing, T, Col[T]]): Col[T] = rdd.collect.to(cbf)

}
