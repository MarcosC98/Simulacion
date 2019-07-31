package vehiculos
import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import scala.util.Random
class Bus (var placaB:String,origen:Interseccion,velocidad:Velocidad) extends Vehiculo(placaB)(origen,velocidad){
  
  val aleatorioC = scala.util.Random
  var placaGenerada:String = ""
  
  for (i <- 1 to 3) {placaGenerada = placaGenerada + (aleatorio.nextInt(25) + 65).toChar}
  for (d <- 1 to 3) {placaGenerada = placaGenerada + (aleatorio.nextInt(10)).toString()}  
  
  placaB = placaGenerada
  this.placa = placaB
}