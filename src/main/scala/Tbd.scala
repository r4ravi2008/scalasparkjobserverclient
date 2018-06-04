import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Tbd {

  def main(args: Array[String]): Unit = {
    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val url = "http://localhost:8090"
    val responseFuture: Future[HttpResponse] = Http()
      .singleRequest(HttpRequest(uri =
        s"${url}/contexts/"))

    val createContextUri = s"${url}/contexts/testcontext?context-factory=spark.jobserver.context" +
      s".SessionContextFactory"

    val postFuture = Http().singleRequest(HttpRequest(uri = createContextUri, method =
      HttpMethods.POST))

    postFuture.onComplete{
      case Success(res) => println(res)
      case Failure(_) => sys.error("something wrong")
    }

  }

}
