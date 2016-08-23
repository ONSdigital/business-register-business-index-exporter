package uk.gov.ons.business.export.elasticsearch.cli

import uk.gov.ons.business.export.elasticsearch.Constants._
import uk.gov.ons.business.export.elasticsearch.{Client, Exporter}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.{Inf => infinite}
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions

abstract class StatusCode(val value: Int)
case object Success extends StatusCode(0)
case object InvalidOption extends StatusCode(1)
case object UnexpectedError extends StatusCode(99)

class CommandLineLauncher {

  def launch(args: Array[String]): Future[StatusCode] = {
    new LauncherOptionsParser().parse(args, LauncherOptions(null)) match {
      case Some(arguments) =>
        def ensureIndexExists(): Future[Unit] = {
          val elasticsearchClient = new Client(arguments.elasticsearch.nodes.split(',').head)

          for {
            indexExists <- elasticsearchClient.checkIndexExists(index)
            _ <- if (!indexExists) elasticsearchClient.createIndex(index, documentTypeMappings) else Future.successful()
          } yield {}
        }

        ensureIndexExists() map { _ =>
          new Exporter().export(arguments.businessIndexPath, arguments.elasticsearch.nodes)
          Success
        } recover { case exception: Throwable =>
          exception.printStackTrace()
          UnexpectedError
        }
      case None =>
        Future(InvalidOption)
    }
  }
}

object CommandLineApplication extends App {

  private implicit def statusCodeToInt(statusCode: StatusCode): Int = statusCode.value

  System.exit(Await.result[StatusCode](new CommandLineLauncher().launch(args), infinite))

}
