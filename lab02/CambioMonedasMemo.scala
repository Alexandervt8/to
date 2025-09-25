object CambioMonedasMemo {
  def countChange(amount: Int, coins: List[Int]): Int = {
    val arr = coins.toArray
    import scala.collection.mutable

    val memo = mutable.Map.empty[(Int, Int), Int]

    def go(money: Int, i: Int): Int = {
      if (money == 0) 1
      else if (money < 0 || i == arr.length) 0
      else memo.getOrElseUpdate((money, i),
        go(money - arr(i), i) +   // usar moneda i (repetible)
        go(money, i + 1)          // saltar a la siguiente moneda
      )
    }

    go(amount, 0)
  }

  def main(args: Array[String]): Unit = {
    val monedas = List(1, 2)
    val cantidad = 4
    println(s"Formas de dar cambio para $cantidad con $monedas: " +
      countChange(cantidad, monedas))
  }
}