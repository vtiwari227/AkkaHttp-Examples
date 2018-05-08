
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpRequest, HttpResponse}
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.util.{Failure, Success}


/**
  * This example shows Akka client API
  */
object WebClient1 {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer =  ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val responseFuture: Future[HttpResponse] = Http().singleRequest(HttpRequest(uri =  "http://akka.io"))
    responseFuture.onComplete {
      case Success(resp) => println(resp)
      case Failure(ex) => sys.error(s"something went wrong $ex")
    }


  }



}
