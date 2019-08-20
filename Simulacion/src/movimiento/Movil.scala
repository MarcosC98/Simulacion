package movimiento
import plano.Punto
abstract class Movil(val velocidad:Velocidad) {  
  
  var posicion:Punto = _
  def devolverAngulo = velocidad.direccion.grados 
  def aumentarPosicion(dt:Double):Unit
  
}