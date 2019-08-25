package movimiento
import plano.Punto
abstract class Movil(val velActual:Velocidad,var aceleracion:Double) {  
  
  val aceleracionO = aceleracion
  val velMax = velActual.magnitud
  velActual.magnitud = 0
  var posicion:Punto = _
  def devolverAngulo = velActual.direccion.grados 
  def aumentarPosicion(dt:Double):Unit
  
}