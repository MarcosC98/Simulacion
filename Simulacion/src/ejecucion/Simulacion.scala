package ejecucion
import mapa.Via
import scala.collection.mutable.ArrayBuffer
//Singleton Object para que solo haya una instancia
import movimiento.Vehiculo
import scala.util
object Simulacion extends Runnable{
  
  val aleatorio = scala.util.Random
  
  val dt = Json.datos.pametrosSimulacion.dt
  val tRefresh = Json.datos.pametrosSimulacion.tRefresh
  val minVehiculos = Json.datos.pametrosSimulacion.vehiculos.minimo
  val maxVehiculos = Json.datos.pametrosSimulacion.vehiculos.maximo
  val minVelocidad = Json.datos.pametrosSimulacion.velocidad.minimo
  val maxVelocidad = Json.datos.pametrosSimulacion.velocidad.maximo
  
  var t: Int =0
  //VAL a
  val listaVehiculos = ArrayBuffer[Vehiculo]()
  val listaVias = ArrayBuffer[Via]()
    
 def run() {
 /*while (true) {
 listadevehiculosOSimilar.foreach(_.mover(dt))
 t += dt
 Grafico.graficarVehiculos(listadevehiculosOSimilar)
 Thread.sleep(tRefresh)
 }*/
}
}