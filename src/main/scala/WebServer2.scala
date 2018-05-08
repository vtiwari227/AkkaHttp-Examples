import akka.Done
import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.marshallers.
import scala.concurrent.Future

object WebServer2 {

  //required to run routes
  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  //require for future map/flatmap
  implicit val executionContext = system.dispatcher

  var orders: List[Item] = Nil

  case class Item(name: String, id: Long)
  case class Order(items: List[Item])

  implicit val itemFormat = jsonFormat2(Item)

  def fetchItem(itemID: Long): Future[Option[Item]] = Future {
    orders.find(o => o.id == itemID)
  }

  def saveOrder(order: Order): Future[Done] = {
    orders = order match {
      case Order(items) => items :::  orders
      case _ => orders
    }
    Future {Done}
  }

  def main(args: Array[String]): Unit = {
    val route: Route =
      get {
        pathPrefix("item" / LongNumber) { id =>
          val maybeItem: Future[Option[Item]] = fetchItem(id)
          onSuccess(maybeItem) match {
            case Some(item) => complete(item)
            case None => complete(StatusCodes.NotFound)
          }
        }
      }

  }
}
