package vehiculos
import java.awt.Shape

import movimiento.Velocidad
import org.jfree.util.ShapeUtilities


class Camion (var placaCM:String,velocidad:Velocidad,aceleracion:Double) extends Vehiculo(placaCM)(velocidad,aceleracion){
    
  var placaGenerada:String = "R"
  
  for (d <- 1 to 5) {placaGenerada = placaGenerada + (aleatorio.nextInt(10)).toString()}  
  
  placaCM = placaGenerada
  this.placa = placaCM
  override val figura: Shape = ShapeUtilities.createDownTriangle(4)
}