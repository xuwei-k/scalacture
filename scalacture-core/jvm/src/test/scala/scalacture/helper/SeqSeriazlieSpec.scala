package scalacture.helper

import java.io.{ByteArrayInputStream, ByteArrayOutputStream, ObjectInputStream, ObjectOutputStream}
import org.scalacheck.{Arbitrary, Gen}
import org.scalatest.WordSpec
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import scala.collection.generic.{CanBuildFrom, GenericTraversableTemplate, SeqFactory}
import scala.collection.SeqLike
import scala.language.higherKinds

trait SeqSerializeSpec[A: Arbitrary, CC[X] <: Seq[X] with SeqLike[X, CC[X]] with GenericTraversableTemplate[X, CC]]()
  extends WordSpec
  with GeneratorDrivenPropertyChecks {

  protected[this] def factory: SeqFactory[CC]

  implicit protected[this] def seqCBF: CanBuildFrom[CC[_], A, CC[A]]

  "A Seq should be able to seriazlie" in {
    forAll { elems: List[A] =>
      val seq = factory(elems: _*)
      val outputStream = new ByteArrayOutputStream()
      new ObjectOutputStream(outputStream).writeObject(seq)
      val bytes = outputStream.toByteArray

      val inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes))
      val deserizized = inputStream.readObject().asInstanceOf[CC[A]]
      assert(deserizized === seq)
    }
  }
}
