package ejecucion
import mapa.Interseccion
import mapa.Via
import mapa.TipoVia
import mapa.Sentido
import scala.util.Random
object Main extends App {
  Json.cargarDatosJson
  Simulacion.cargarDatosIniciales
  GrafoVia.construir(Simulacion.listaVias)
  Simulacion.generarVehiculosAleatorios
  val a = GrafoVia.grafo.get(Simulacion.listaIntersecciones(Simulacion.aleatorio.nextInt(Simulacion.listaIntersecciones.size)))
  val b = GrafoVia.grafo.get(Simulacion.listaIntersecciones(Simulacion.aleatorio.nextInt(Simulacion.listaIntersecciones.size)))
  val c = a.shortestPathTo(b).get.edges.toList.map(_.toOuter.label.asInstanceOf[Via])
  print(c)
}