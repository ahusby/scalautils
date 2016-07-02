package ahusby.scalautils.io

import java.nio.file.Paths

import ahusby.scalautils.io.FileReader.readResourceFile
import org.scalatest.FunSpec

import scala.util.{Failure, Success}

class FileReaderTest extends FunSpec {

  describe("readResourceFile") {
    it("can read a file") {
      val resourceFileReader = readResourceFile(getClass.getClassLoader)(_)
      val filename = "ahusby/scalautils/io/aretest.txt"
      val actual = resourceFileReader(filename)
      val expected = """aaa
                       |bbb
                       |cccccccccc
                       |dddddddddddddd
                       |ee
                       |""".stripMargin
      assert(actual === expected)
    }
  }

  describe("readFile") {
    it("can read a file") {
      val filename = "./README.md"
      val p = Paths.get(filename)
      val actual = FileReader.readFile(p)
      assert(actual.isSuccess, actual)
      assert(actual.get.length > 0)
    }
  }
}
