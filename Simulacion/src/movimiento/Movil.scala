package movimiento
import plano.Punto
abstract class Movil(var _posicion:Punto, _velocidad:Velocidad) {  
  def devolverAngulo = _velocidad.direccion.grados
  def aumentarPosicion(dt:Double)
  def posicion = _posicion
  def velocidad = _velocidad
  def posicion_(posicion:Punto):Unit = _posicion = posicion
}