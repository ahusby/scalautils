package ahusby.scalautils.io

import java.io.File.separator
import java.nio.file.{Files, Path, Paths}

//import resource.managed
import scala.io.Source
import scala.util.Try

object FileReader {

  /**
    * Brukes slik:{{{
    * private val read = readResourceFile(getClass.getClassLoader) _
    * ...
    * val fileContents1 = read(filename1)
    * val fileContents2 = read(filename2)
    * }}}
    *
    */
  def readResourceFile(cl: ClassLoader)(resourcefileName: String): String = {
    val name = resourcefileName.stripPrefix(separator)
    val url = cl.getResource(name)
    if (url == null) {
      throw new ResourcefileNotFound(name)
    } else {
      //      managed(Source.fromURL(url)).acquireAndGet(_.mkString)
      import scala.language.reflectiveCalls
      closing(Source.fromURL(url))(_.mkString)
    }
  }


  def readFile(p: Path): Try[String] = Try {
    val filename = p.toAbsolutePath.toString
    if (!Files.exists(p)) throw new FileNotFound(filename)
    if (!Files.isReadable(p)) throw new FileNotReadable(filename)
    if (!Files.isRegularFile(p)) throw new NotARegularRegularFile(filename)
    //    managed(Source.fromFile(p.toFile)).acquireAndGet(_.mkString)
    closing(Source.fromFile(p.toFile))(_.mkString)
  }

  def readFile(filename: String): Try[String] = readFile(Paths.get(filename))

  class ResourcefileNotFound(msg: String) extends RuntimeException(msg)

  class FileNotFound(msg: String) extends RuntimeException(msg)

  class FileNotReadable(msg: String) extends RuntimeException(msg)

  class NotARegularRegularFile(msg: String) extends RuntimeException(msg)

}
