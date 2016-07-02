package ahusby.scalautils.io

import java.io.File.separator
import java.nio.file.{Files, Path}

import scala.io.Source
import scala.language.reflectiveCalls
import scala.util.Try

object FileReader {

  class ResourcefileNotFound(msg: String) extends RuntimeException(msg)

  class FileNotFound(msg: String) extends RuntimeException(msg)

  class FileNotReadable(msg: String) extends RuntimeException(msg)

  class NotARegularRegularFile(msg: String) extends RuntimeException(msg)

  def closing[A <: {def close() : Unit}, B](closeable: A)(f: A => B): B = {
    try {
      f(closeable)
    } finally {
      closeable.close()
    }
  }


  def readResourceFile(classLoader: ClassLoader)(resourcefileName: String): String = {
    assert(resourcefileName != null)
    assert(resourcefileName.trim.length > 0)
    val name = resourcefileName.stripPrefix(separator)
    val url = classLoader.getResource(name)
    if (url == null) {
      throw new ResourcefileNotFound(name)
    } else {
      closing(Source.fromURL(url))(_.mkString)
    }
  }


  def readFile(p: Path): Try[String] = Try {
    assert(p != null)
    val filename = p.toAbsolutePath.toString
    if (!Files.exists(p)) throw new FileNotFound(filename)
    if (!Files.isReadable(p)) throw new FileNotReadable(filename)
    if (!Files.isRegularFile(p)) throw new NotARegularRegularFile(filename)
    closing(Source.fromFile(p.toFile))(_.mkString)
  }
}

