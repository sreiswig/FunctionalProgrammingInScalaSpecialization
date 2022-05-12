package funsets

/**
 * This class is a test suite for the methods in object FunSets.
 *
 * To run this test suite, start "sbt" then run the "test" command.
 */
class FunSetSuite extends munit.FunSuite:

  import FunSets.*

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }

  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   *
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   *
   *   val s1 = singletonSet(1)
   *
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   *
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   *
   */

  trait TestSets:
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)

  /**
   * This test is currently disabled (by using @Ignore) because the method
   * "singletonSet" is not yet implemented and the test would fail.
   *
   * Once you finish your implementation of "singletonSet", remove the
   * .ignore annotation.
   */
  test("singleton set one contains one") {

    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3".
     */
    new TestSets:
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
  }

  test("union contains all elements of each set") {
    new TestSets:
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
  }

  test("intersect contains all elements in both sets") {
    new TestSets:
      val ts1 = union(s1, s2)
      val ts2 = union(s2, s3)
      val ts3 = intersect(ts1, ts2)

      assert(!contains(ts3, 1), "Intersect 1")
      assert(contains(ts3, 2), "Intersect 2")
      assert(!contains(ts3, 3), "Intersect 3")
  }

  test("diff contains all elements in first set but not in second") {
    new TestSets:
      val ts1 = union(s1, s2)
      val ts2 = union(s2, s3)
      val ts3 = diff(ts1, ts2)

      assert(contains(ts3, 1), "Diff 1")
      assert(!contains(ts3, 2), "Diff 2")
      assert(!contains(ts3, 3), "Diff 3")
  }

  test("filter contains all elements in first set and where conditions holds") {
    new TestSets:
      val s4 = singletonSet(4)
      val ts1 = union(s1, s2)
      val ts2 = union(s2, s3)
      val ts3 = union(ts1, ts2)
      val ts4 = filter(ts3, x => x %2 == 0)
      val ts5 = union(ts3, s4)
      val ts6 = filter(ts5, x => x %2 == 0)

      assert(!contains(ts4, 1), "Filter 1")
      assert(contains(ts4, 2), "Filter 2")
      assert(!contains(ts4, 3), "Filter 3")
      assert(contains(ts6, 4), "Filter 4")
  }

  test("forall checks if all elements in set satisfy condition") {
    new TestSets:
      val ts1 = union(s1, s2)
      val ts2 = union(s2, s3)
      val ts3 = union(s1, s3)

      assert(!forall(ts1, x => x % 2 == 0), "Forall 1")
      assert(!forall(ts2, x => x % 2 == 0), "Forall 2")
      assert(forall(ts3, x => x % 2 == 1), "Forall 3")
  }

  test("exists checks if at least one element in set satisfies condition") {
    new TestSets:
      val ts1 = union(s1, s2)
      val ts2 = union(s2, s3)
      val ts3 = union(s1, s3)

      assert(exists(ts1, x => x % 2 == 0), "Exists 1")
      assert(exists(ts2, x => x % 2 == 1), "Exists 2")
      assert(!exists(ts3, x => x % 2 == 0), "Exists 3")
  }

  test("map applies function to all elements in set") {
    new TestSets:
      val ts1 = union(s1, s2)
      val ts2 = map(ts1, x => x * 2)
      assert(contains(ts2, 2), "Map 1")
      assert(contains(ts2, 4), "Map 2")
      assert(!contains(ts2, 3), "Map 3")
  }

  import scala.concurrent.duration.*
  override val munitTimeout = 10.seconds
