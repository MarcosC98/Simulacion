package mapa
import ejecucion.Simulacion
import plano.Punto
import scala.util.Random
class Semaforo(_lugar:Punto) {
  
  val lugar = _lugar
  var estado = "Verde"
  val tVerdeOriginal = Simulacion.minTiempoVerde + scala.util.Random.nextInt((Simulacion.maxTiempoVerde - Simulacion.minTiempoVerde).toInt)
  var tVerde:Double = tVerdeOriginal
  val tAmarilloOriginal = Simulacion.tiempoAmarillo
  var tAmarillo:Double = tAmarilloOriginal
  Simulacion.listaSemaforos.append(this)
}