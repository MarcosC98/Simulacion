package mapa
case class Sentido private(val nombre:String) {
}

object Sentido{
  def dobleVia:Sentido = {
    return Sentido("Doble via")
  }
  
  def unaVia:Sentido = {
    return Sentido("Unico sentido")
  }
}