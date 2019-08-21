package mapa
import ejecucion.Simulacion
import plano.Punto
import scala.util.Random
class Semaforo(lugar:Punto) {
  
  val estado = ""
  val tVerde = Simulacion.minTiempoVerde + scala.util.Random.nextInt((Simulacion.maxTiempoVerde - Simulacion.minTiempoVerde).toInt)
  val tAmarillo = Simulacion.tiempoAmarillo
}