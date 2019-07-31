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
  Simulacion.listaVehiculos.foreach(Vehiculo  => println("I: " + Vehiculo.posi + " F: " + Vehiculo.interF +" P: " + Vehiculo.placa +
      " V: " + Vehiculo.vel.magnitud))
}