package mapa
import plano.Punto
import ejecucion.Simulacion
class Interseccion( cx:Int, cy:Int, _nombre:String ="") extends Punto(cx,cy) {

  def nombre  = _nombre
  Simulacion.listaIntersecciones.append(this)
}