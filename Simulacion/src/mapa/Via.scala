package mapa
import plano.Recta
import ejecucion.Simulacion
class Via (iorigen:Interseccion,ifinal:Interseccion,velmax:Int,
    tipoVia:TipoVia,sentido:Sentido,numero:String,var nombreVia:String)extends Recta {
  Simulacion.listaVias.append(this)
  type T = Interseccion
  val origen = iorigen
  val fin = ifinal
  def distancia=Math.sqrt(Math.pow((fin.x - origen.x),2) + Math.pow((fin.y - origen.y),2))
}