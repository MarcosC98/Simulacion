package mapa
import ejecucion.Simulacion
import plano.Punto
class Semaforo(_lugar:Punto) {
  
  val lugar = _lugar
  var estado = "Verde"
  var tVerdeOriginal = Simulacion.minTiempoVerde + scala.util.Random.nextInt((Simulacion.maxTiempoVerde - Simulacion.minTiempoVerde).toInt)
  var tVerde:Double = tVerdeOriginal
  var tAmarilloOriginal = Simulacion.tiempoAmarillo
  var tAmarillo:Double = tAmarilloOriginal
  Simulacion.listaSemaforos.append(this)
  
}