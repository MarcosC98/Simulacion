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
  println("pre-error")
  val a1 = Simulacion.listaIntersecciones(Simulacion.aleatorio.nextInt(Simulacion.listaIntersecciones.size-1))
  val a2 = Simulacion.listaIntersecciones(Simulacion.aleatorio.nextInt(Simulacion.listaIntersecciones.size-1))
  println("a1 : " + a1.nombre)
  println("a2 : " + a2.nombre)
  val a = GrafoVia.grafo.get(a1)
  val b = GrafoVia.grafo.get(a2)
  val c = a.shortestPathTo(b).get.edges.toList.map(_.toOuter.label.asInstanceOf[Via])
  println(c)
}