package mapa
import plano.Punto
import ejecucion.Simulacion
import graficacion.Grafico.rand
class Interseccion(cx:Double ,cy:Double,val _nombre:Option[String]) extends Punto(cx,cy) {

  def nombre  = _nombre
  val color = "#%06x".format(rand.nextInt(256*256*256))
  
  var _vehiculosEnInter:Int = 0
  
  def vehiculosEnInter = _vehiculosEnInter
  def vehiculosEnInter_(vehiculosEnInter:Int):Unit = _vehiculosEnInter = vehiculosEnInter
  Simulacion.listaIntersecciones.append(this)
}