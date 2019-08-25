package vehiculos
import java.awt.Shape

import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import org.jfree.util.ShapeUtilities

import scala.util.Random
class MotoTaxi (var placaMT:String,velocidad:Velocidad,aceleracion:Int) extends Vehiculo(placaMT)(velocidad,aceleracion){
  
  var placaGenerada:String = ""
  
  for (i <- 1 to 3) {placaGenerada = placaGenerada + aleatorio.nextInt(10).toString()}
  for (d <- 1 to 3) {placaGenerada = placaGenerada + (aleatorio.nextInt(25) + 65).toChar}  
  
  placaMT = placaGenerada
  this.placa = placaMT
  override val figura: Shape = ShapeUtilities.createUpTriangle(4)
}