package uk.gov.ons.business

import java.io.File
import java.nio.file.Files

import org.apache.commons.io.FileUtils.forceDelete
import org.elasticsearch.common.settings.Settings._
import org.elasticsearch.node.Node
import org.elasticsearch.node.NodeBuilder._
import org.scalatest.{BeforeAndAfterAll, Suite}

trait ElasticsearchServerSuiteMixin extends BeforeAndAfterAll {
  this: Suite =>

  private val homeDirectory = new File(s"${Files.createTempDirectory(getClass.getSimpleName)}/embedded-elasticsearch")

  val server: Node = nodeBuilder()
    .settings(settingsBuilder().put("path.home", homeDirectory.getAbsolutePath))
    .build()


  override protected def beforeAll(): Unit = {
    server.start()
  }

  override protected def afterAll(): Unit = {
    server.close()
    forceDelete(homeDirectory)
  }

}
