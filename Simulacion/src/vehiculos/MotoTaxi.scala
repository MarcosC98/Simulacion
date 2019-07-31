package vehiculos
import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import scala.util.Random
class MotoTaxi (var placaMT:String,origen:Interseccion,velocidad:Velocidad) extends Vehiculo(placaMT)(origen,velocidad){
  
  var placaGenerada:String = ""
  
  for (i <- 1 to 3) {placaGenerada = placaGenerada + aleatorio.nextInt(10).toString()}
  for (d <- 1 to 3) {placaGenerada = placaGenerada + (aleatorio.nextInt(25) + 65).toChar}  
  
  placaMT = placaGenerada
  this.placa = placaMT
}