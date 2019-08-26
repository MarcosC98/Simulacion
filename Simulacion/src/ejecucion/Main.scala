package ejecucion
import ejecucion.Simulacion.listaVehiculos
import graficacion.Grafico

object Main extends App {
  Json.cargarDatosJson
  Simulacion.cargarDatosIniciales
  Grafico.graficaViasNodos(Simulacion.listaVias,Simulacion.listaIntersecciones,Simulacion.listaCamaras)
  GrafoVia.construir(Simulacion.listaVias)
  Simulacion.generarVehiculosAleatorios
  Grafico.dibujarVehiculos(listaVehiculos)
  Simulacion.run

}