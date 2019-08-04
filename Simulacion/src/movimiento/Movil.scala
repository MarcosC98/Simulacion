package movimiento
import plano.Punto
abstract class Movil(private val _pos:Punto, val velocidad:Velocidad) {  
  
  def devolverAngulo = velocidad.direccion.grados 
  def aumentarPosicion(dt:Double):Unit
  
  var _posicion = new Punto(_pos.x,_pos.y)
  
  def posicion = _posicion
  def posicion_(posicion:Punto):Unit = _posicion = posicion
}