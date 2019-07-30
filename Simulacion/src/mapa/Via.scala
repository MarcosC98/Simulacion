package mapa
import plano.Recta
class Via (iorigen:Interseccion,ifinal:Interseccion,velmax:Int,var
    nombreVia:String,tipoVia:TipoVia,sentido:Sentido)extends Recta {
  type T = Interseccion
  val origen = iorigen
  val fin = ifinal
  def distancia=Math.sqrt(Math.pow(fin.x - origen.x,2) + Math.pow(fin.y - origen.y,2))

}