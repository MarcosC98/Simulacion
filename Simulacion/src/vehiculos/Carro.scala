package vehiculos
import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import scala.util.Random
class Carro (var placaC:String,origen:Interseccion,velocidad:Velocidad) extends Vehiculo(placaC)(origen,velocidad){
  
  
  val aleatorioC = scala.util.Random
  
  for (i <- 1 to 3) {placaC = placaC + (aleatorio.nextInt(25) + 65).toChar}
  for (d <- 1 to 3) {placaC = placaC + (aleatorio.nextInt(10)).toString()}  
  this.placa =placaC
}