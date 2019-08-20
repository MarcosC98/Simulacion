package vehiculos
import scala.util.Random
import ejecucion.Simulacion
import mapa.Interseccion
import mapa.Via
import ejecucion.GrafoVia
import scala.collection.mutable.Queue

class Viaje(v: Vehiculo) {

  val aleatorio = scala.util.Random
  val tamanioListaInter = Simulacion.listaIntersecciones.length
  val interI = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioListaInter))
  val interF = otraInter(interI)
  
  val nodoi = GrafoVia.grafo.get(interI)
  val nodof = GrafoVia.grafo.get(interF)
  val recorrido = nodoi.shortestPathTo(nodof).get.edges.toList.map(_.toOuter.label.asInstanceOf[Via])
  val pila = Queue(recorrido: _*)

  
  
  def otraInter(i: Interseccion):Interseccion =  {
    var c: Boolean = true
    var interF: Interseccion = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioListaInter))
    while (c) {
      if (interI == interF) {
        interF = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioListaInter))
      } else {
        c = false
      }
    }
    interF
  }

}