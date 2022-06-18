package quickcheck

import org.scalacheck.*
import Arbitrary.*
import Gen.*
import Prop.forAll

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap:
  lazy val genHeap: Gen[H] = oneOf(
    const(empty),
    for {
      v <- arbitrary[Int]
      h <- oneOf(const(empty), genHeap)
    } yield insert(v, h)
  )

  given Arbitrary[H] = Arbitrary(genHeap)

  property("gen1") = forAll { (h: H) =>
    val m = if isEmpty(h) then 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("min1") = forAll { (a: Int) =>
    val h = insert(a, empty)
    findMin(h) == a
  }

  property("smaller1") = forAll { (a: Int, b: Int) =>
    val h = insert(a, empty)
    insert(b, h)
    if (a > b) findMin(h) == b else findMin(h) == a
  }

  property("empty1") = forAll { (a: Int) =>
    val h = insert(a, empty)
    deleteMin(h)
    isEmpty(h)
  }

  property("meld1") = forAll { (a: Int, b: Int) =>
    val h = insert(a, empty)
    val h2 = insert(b, empty)
    val h3 = meld(h, h2)
    findMin(h3) == findMin(h2) || findMin(h3) == findMin(h)
  }
