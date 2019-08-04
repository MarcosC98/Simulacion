package mapa
import plano.Punto
import ejecucion.Simulacion
import graficacion.Grafico.rand
class Interseccion(cx:Int ,cy:Int,val _nombre:String ="") extends Punto(cx,cy) {

  def nombre  = _nombre
  val color = "#%06x".format(rand.nextInt(256*256*256))
  Simulacion.listaIntersecciones.append(this)
  
  var _vehiculosEnInter:Int = 0
  
  def vehiculosEnInter = _vehiculosEnInter
  def vehiculosEnInter_(vehiculosEnInter:Int):Unit = _vehiculosEnInter = vehiculosEnInter
}