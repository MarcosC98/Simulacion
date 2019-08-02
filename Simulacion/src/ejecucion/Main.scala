package ejecucion
import graficacion.Grafico
import mapa.Interseccion
import mapa.Via
import mapa.TipoVia
import mapa.Sentido

import scala.util.Random
object Main extends App {
  Json.cargarDatosJson
  Simulacion.cargarDatosIniciales
  Grafico.graficarVias(Simulacion.listaVias,Simulacion.listaIntersecciones)
  GrafoVia.construir(Simulacion.listaVias)
  Simulacion.generarVehiculosAleatorios
  Simulacion.run
}