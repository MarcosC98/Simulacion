package vehiculos
import java.awt.Shape


import movimiento.Velocidad
import org.jfree.util.ShapeUtilities

class Bus (var placaB:String,velocidad:Velocidad,aceleracion:Double) extends Vehiculo(placaB)(velocidad,aceleracion){
  
  val aleatorioC = scala.util.Random
  var placaGenerada:String = ""

  for (i <- 1 to 3) {placaGenerada = placaGenerada + (aleatorio.nextInt(25) + 65).toChar}
  for (d <- 1 to 3) {placaGenerada = placaGenerada + (aleatorio.nextInt(10)).toString()}  
  
  placaB = placaGenerada
  this.placa = placaB
  override val figura: Shape = ShapeUtilities.createDiagonalCross(4, 1f)
}