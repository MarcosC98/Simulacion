package mapa
import plano.Punto
import ejecucion.Simulacion
case class Interseccion(cx:Int, cy:Int, _nombre:String ="") extends Punto(cx,cy) {
  Simulacion.listaIntersecciones.append(this)
  def nombre  = _nombre
}