package mapa
import movimiento.Velocidad
import vehiculos.Vehiculo
import ejecucion.Simulacion
class Comparendo (vehiculo:Vehiculo,velocidad:Double,velocidadMaxima:Double){
  Simulacion.listaComparendos.append(this)
  println("COMPARENDO")
}