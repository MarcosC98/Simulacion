package vehiculos
import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import scala.util.Random
class Moto (var placaM:String,origen:Interseccion,velocidad:Velocidad) extends Vehiculo(placaM)(origen,velocidad){
  
  val aleatorioM = scala.util.Random
  var placaGenerada:String = ""
  
  for (i <- 1 to 3) {placaGenerada = placaGenerada + (aleatorio.nextInt(25) + 65).toChar}
  for (d <- 1 to 2) {placaGenerada = placaGenerada + (aleatorio.nextInt(10)).toString()}  
  placaGenerada = placaGenerada + (aleatorio.nextInt(25) + 65).toChar
  
  placaM = placaGenerada
  this.placa = placaM
}