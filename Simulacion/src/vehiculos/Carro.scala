package vehiculos
import java.awt.Shape

import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import org.jfree.util.ShapeUtilities

import scala.util.Random
class Carro (var placaC:String,velocidad:Velocidad,aceleracion:Int) extends Vehiculo(placaC)(velocidad,aceleracion){
  
  
  val aleatorioC = scala.util.Random
  
  for (i <- 1 to 3) {placaC = placaC + (aleatorio.nextInt(25) + 65).toChar}
  for (d <- 1 to 3) {placaC = placaC + (aleatorio.nextInt(10)).toString()}  
  this.placa =placaC
  override val figura: Shape = ShapeUtilities.createDiamond(4)
}