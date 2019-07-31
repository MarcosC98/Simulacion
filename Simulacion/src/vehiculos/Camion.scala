package vehiculos
import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import scala.util.Random
class Camion (var placaCM:String,origen:Interseccion,velocidad:Velocidad) extends Vehiculo(placaCM)(origen,velocidad){
    
  var placaGenerada:String = "R"
  
  for (d <- 1 to 5) {placaGenerada = placaGenerada + (aleatorio.nextInt(10)).toString()}  
  
  placaCM = placaGenerada
  this.placa = placaCM
}