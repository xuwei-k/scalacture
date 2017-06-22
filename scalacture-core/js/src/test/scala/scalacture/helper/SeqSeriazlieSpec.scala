package scalacture.helper

trait SeqSerializeSpec[A, CC[X] <: Seq[X] with SeqLike[X, CC[X]] with GenericTraversableTemplate[X, CC]]
