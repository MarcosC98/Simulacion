package mapa
import plano.Recta
import ejecucion.Simulacion
import plano.Angulo
class Via (iorigen:Interseccion,ifinal:Interseccion,velmax:Int,
    tipoVia:TipoVia,_sentido:Sentido,numero:String,var nombreVia:String)extends Recta {
  Simulacion.listaVias.append(this)
  type T = Interseccion
  val origen = iorigen
  val fin = ifinal 
  def sentido = _sentido
  def distancia=Math.sqrt(Math.pow((fin.x - origen.x),2) + Math.pow((fin.y - origen.y),2))
  val angulo = new Angulo(Simulacion.calcularTanInv(origen.x, fin.x, origen.y, fin.y))
}