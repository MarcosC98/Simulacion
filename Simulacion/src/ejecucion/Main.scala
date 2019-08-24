package ejecucion
import ejecucion.Simulacion.listaVehiculos
import graficacion.Grafico

object Main extends App {
  Json.cargarDatosJson
  Simulacion.cargarDatosIniciales
  Grafico.graficaViasNodos(Simulacion.listaVias,Simulacion.listaIntersecciones)
  GrafoVia.construir(Simulacion.listaVias)
  Simulacion.generarVehiculosAleatorios
  Grafico.dibujarVehiculos(listaVehiculos)
  Simulacion.listaSemaforos.foreach(s => println(s.estado))
  Simulacion.run
  Simulacion.listaSemaforos.foreach(s => println(s.estado))
}