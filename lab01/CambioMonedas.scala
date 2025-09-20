object CambioMonedas {
  def countChange(money: Int, coins: List[Int]): Int = {
    if (money == 0) 1
    else if (money < 0) 0
    else if (coins.isEmpty) 0
    else {
      countChange(money - coins.head, coins) +
      countChange(money, coins.tail)
    }
  }

  def main(args: Array[String]): Unit = {
    val monedas = List(1, 2)
    val cantidad = 4
    println(s"Formas de dar cambio para $cantidad con $monedas: " +
      countChange(cantidad, monedas))
  }
}
