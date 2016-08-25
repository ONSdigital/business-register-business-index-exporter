package uk.gov.ons.business.export.elasticsearch.cli

import uk.gov.ons.business.export.elasticsearch.Constants._
import uk.gov.ons.business.export.elasticsearch.{Client, Exporter}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.Duration.{Inf => infinite}
import scala.concurrent.{Await, Future}
import scala.language.implicitConversions

class Launcher {

  def launch(args: Array[String]): Future[Unit] = {
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
        }
      case None => // parser will print error in that case
        Future()
    }
  }

}

object Application extends App {

  Await.result(new Launcher().launch(args), infinite)

}
