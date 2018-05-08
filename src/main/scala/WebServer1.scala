import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer

import scala.io.StdIn


object WebServer1 {
  def main(args: Array[String]):Unit = {

    implicit val system = ActorSystem("system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher


    val route: Route =
      path("hello") {
        get {
          complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1> Say hello to new learning </h1>"))
        }
      }

    val bindingServer = Http().bindAndHandle(route, "localhost", 8080)

    println("Server is online at http://localhost:8080/ \n Press return to stop")
    StdIn.readLine() //let it read until user presses the button
    bindingServer.flatMap(_.unbind()) //unbind the port
      .onComplete(_ => system.terminate()) //shutdown from system
  }
}
