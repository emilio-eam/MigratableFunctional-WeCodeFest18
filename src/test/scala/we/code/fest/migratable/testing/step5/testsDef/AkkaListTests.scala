package we.code.fest.migratable.testing.step5.testsDef

import we.code.fest.migratable.testing.step5.lib.InternalSearchOfficeService
import we.code.fest.migratable.testing.step5.model.Office
import we.code.fest.migratable.testing.step5.lib.RulesSearchOfficeService
import akka.actor.Actor
import akka.actor.ActorRef
import scala.collection.mutable.ListBuffer
import akka.util.Timeout
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import akka.actor.ActorSystem
import akka.actor.Props
import we.code.fest.migratable.testing.step5.lib.SearchOfficeService
import we.code.fest.migratable.testing.step5.lib.SearchOfficeNameService
import org.scalatest.BeforeAndAfterEach
import we.code.fest.migratable.testing.step5.lib.DefaultRules

class AkkaListTests extends SearchOfficeTestBase with BeforeAndAfterEach {

  override val searchService = SearchOfficeService((c, l) => {
    AkkaListTests.country = Option(c)
    AkkaListTests.language = Option(l)
    allData.foreach(AkkaListTests.searchFlowActor ! _)
    Thread.sleep(1000)
    OfficeReceiver.getAndRemove
  })
  override val searchNameService = SearchOfficeNameService((c, l) => {
    AkkaListTests.country = Option(c)
    AkkaListTests.language = Option(l)
    allData.foreach(AkkaListTests.searchNamesFlowActor ! _)
    Thread.sleep(1000)
    StringReceiver.getAndRemove
  })

  override def beforeEach() = {OfficeReceiver.clear; StringReceiver.clear; AnyReceiver.clear}
  override def additionalChecks = {
    assertResult(List(), "There are unexpected offices")(OfficeReceiver.buffer)
    assertResult(List(), "There are unexpected strings")(StringReceiver.buffer)
    assertResult(List(), "There are unexpected messages")(AnyReceiver.buffer)
  }

}

object AkkaListTests {

  //implicit val timeout: Timeout = Timeout(1 seconds)
  val system = ActorSystem("AkkaTestSystem")
  val internalService = new RulesSearchOfficeService
  var country = Option("")
  var language = Option("")
  val internalFunction = internalService.findOffice(DefaultRules.rules, _: Iterable[Office], country, language)

  val searchFlowActor = system.actorOf(Props[SearchFlowActor])
  val searchNamesFlowActor = system.actorOf(Props[SearchNamesFlowActor])
  val getNameActor = system.actorOf(Props[GetNameActor])
  val receiverActor = system.actorOf(Props[TestReceiverActor])
  val errorActor = system.actorOf(Props[ErrorReceiverActor])

}

abstract class MyFunctionActor(next: ActorRef) extends Actor {

  val nextActor: ActorRef = next

  override def receive: Receive = {
    case office: Office => AkkaListTests.internalFunction(List(office)).foreach(nextActor forward _)
    case _ => sender ! "unknown greeting"
    }

}

class SearchFlowActor() extends MyFunctionActor(AkkaListTests.receiverActor)
class SearchNamesFlowActor() extends MyFunctionActor(AkkaListTests.getNameActor)

class GetNameActor extends Actor {

  override def receive: Receive = {
    case office: Office => AkkaListTests.receiverActor forward office.name
    case _ => AnyReceiver.add(_: Any)
    }

}

class TestReceiverActor extends Actor {

  override def receive: Receive = {
    case office: Office => OfficeReceiver.add(office)
    case s: String => StringReceiver.add(s)
    case _ => AnyReceiver.add(_: Any)
    }

}

class ErrorReceiverActor extends Actor {

  override def receive: Receive = {
    case _ => AnyReceiver.add(_: Any)
    }

}

abstract class Receiver[T] {
  val buffer = new ListBuffer[T]

  def add(data: T): Unit = buffer += data
  def clear: Unit = buffer.clear()
  def getAndRemove: Iterable[T] = {val ret = buffer.toList; buffer.clear; return ret;}
}

object OfficeReceiver extends Receiver[Office]

object StringReceiver extends Receiver[String]

object AnyReceiver extends Receiver[Any]
