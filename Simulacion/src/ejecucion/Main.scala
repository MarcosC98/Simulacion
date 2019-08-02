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
}