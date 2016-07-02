package ahusby.scalautils

package object io {

  def closing[A <: {def close() : Unit}, B](closeable: A)(f: A => B): B = {
    try {
      f(closeable)
    } finally {
      closeable.close()
    }
  }
}
