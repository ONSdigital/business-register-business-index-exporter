package uk.gov.ons.business.export.elasticsearch

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scalaj.http.{Http, HttpRequest, HttpResponse}

class Client(node: String) {

  def checkIndexExists(name: String): Future[Boolean] = {
    Future {
      val request: HttpRequest = Http(indexURL(name)).method("HEAD")
      val response: HttpResponse[String] = request.asString

      response.code match {
        case 200 => true
        case 404 => false
        case statusCode => throw new ClientException(s"Index checking error - unexpected $statusCode response")
      }
    }
  }

  def createIndex(name: String, mappings: String): Future[Unit] = {
    Future {
      val request: HttpRequest = Http(indexURL(name)).postData(mappings)
      val response: HttpResponse[String] = request.asString

      if (response.code != 200) {
        throw new ClientException(s"Index creation error - unexpected ${response.code} response")
      }
    }
  }

  private def indexURL(indexName: String) = {
    s"http://$node/$indexName"
  }

}

class ClientException(message: String) extends RuntimeException(message)
