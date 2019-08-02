package mapa
import plano.Punto
import ejecucion.Simulacion
class Interseccion(var cx:Int,var cy:Int, _nombre:String ="") extends Punto(cx,cy) {

  def nombre  = _nombre
  Simulacion.listaIntersecciones.append(this)
}