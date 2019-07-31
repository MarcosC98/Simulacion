package movimiento
import plano.Punto
abstract class Movil(var _posicion:Punto, velocidad:Velocidad) {  
  
  def devolverAngulo = velocidad.direccion.grados
  
  def aumentarPosicion(dt:Double)
  
  def posicion = _posicion
  def posicion_(posicion:Punto):Unit = _posicion = posicion
}