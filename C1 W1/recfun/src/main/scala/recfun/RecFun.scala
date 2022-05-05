package recfun

object RecFun extends RecFunInterface:

  def main(args: Array[String]): Unit =
    println("Pascal's Triangle")
    for row <- 0 to 10 do
      for col <- 0 to row do
        print(s"${pascal(col, row)} ")
      println()

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = 
    if (c == 0 || c == r) 1
    else pascal(c - 1, r - 1) + pascal(c, r - 1)

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = 
    def balance(chars: List[Char], open: Int): Boolean = 
      if (chars.isEmpty) open == 0
      else if (chars.head == '(') balance(chars.tail, open + 1)
      else if (chars.head == ')') open > 0 && balance(chars.tail, open - 1)
      else balance(chars.tail, open)
    balance(chars, 0)


  /**
   * Exercise 3
   */
  def countChange(money: Int, coins: List[Int]): Int = 
    def countChange(money: Int, coins: List[Int], currentTotal: Int): Int = 
      if (money == 0) 1
      else if (coins.isEmpty) 0
      else if (currentTotal > money) 0
      else if (money - currentTotal == 0) 1
      else countChange(money, coins.tail, currentTotal) + countChange(money, coins, currentTotal + coins.head)
    countChange(money, coins.sorted, 0)
