package mapa
case class Sentido private(val nombre:String) {
}

object Sentido{
  def dobleVia:Sentido = new Sentido("Doble via")
  
  def unaVia:Sentido =new Sentido("Unico sentido")
}