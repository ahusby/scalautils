package ahusby.scalautils.io

import java.nio.file.Paths

import ahusby.scalautils.io.FileReader.{readFile, readResourceFile}
import org.scalatest.FunSpec

class FileReaderTest extends FunSpec {

  describe("readResourceFile") {
    it("can read two files with one classloader") {
      val read = readResourceFile(getClass.getClassLoader) _

      val actual1 = read("ahusby/scalautils/io/randomText1.txt")
      val expected1 = "aaabbb"
      assert(actual1 === expected1)

      val actual2 = read("ahusby/scalautils/io/randomText2.txt")
      val expected2 = "Lorem ipsum"
      assert(actual2 === expected2)
    }
  }

  describe("readFile (Path)") {
    it("can read a file") {
      val p = Paths.get("README.md")
      val actual = FileReader.readFile(p)
      assert(actual.isSuccess, actual)
      assert(actual.get.length > 0)
    }
  }

  describe("readFile (String)") {
    it("can read a file") {
      val actual = readFile("README.md")
      assert(actual.isSuccess, actual)
      assert(actual.get.length > 0)
    }
  }

}
