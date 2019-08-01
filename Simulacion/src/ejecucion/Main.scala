package ejecucion
import mapa.Interseccion
import mapa.Via
import mapa.TipoVia
import mapa.Sentido
import scala.util.Random
object Main extends App {
  Json.cargarDatosJson
  Simulacion.cargarDatosIniciales
  Simulacion.generarVehiculosAleatorios
  
  println(Simulacion.buses,Simulacion.carros,Simulacion.motos,Simulacion.camiones,Simulacion.mototaxis)
  println(Simulacion.numeroVehiculos)
  Simulacion.listaVehiculos.foreach(Vehiculo  => println("I: " + Vehiculo.posi + " F: " + Vehiculo.interF +" P: " + Vehiculo.placa +" V: " + Vehiculo.vel.magnitud))
  print(Simulacion.listaVehiculos.size)
  //print(Simulacion.listaVehiculos.foreach(Vehiculo. => println(vehiculoAleatorio.pila)))
}