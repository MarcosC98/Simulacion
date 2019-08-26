package mapa
import movimiento.Velocidad
import vehiculos.Vehiculo
import ejecucion.Simulacion
class Comparendo (vehiculo:Vehiculo,velocidad:Double,velocidadMaxima:Double){
  Simulacion.listaComparendos.append(this)
  val exceso = (velocidad-velocidadMaxima)*100/velocidadMaxima
}