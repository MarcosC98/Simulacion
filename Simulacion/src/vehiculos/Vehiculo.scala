package vehiculos
import java.awt.Shape
import plano.Punto
import mapa.Interseccion
import movimiento.Movil
import movimiento.MovimientoAcelerado
import movimiento.Velocidad
import mapa.Semaforo
import mapa.NodoSemaforo
import scala.util.Random
import ejecucion.Simulacion
import plano.Angulo
import ejecucion.GrafoVia
import mapa.Via
import scala.collection.mutable.Queue
import movimiento.MovimientoAcelerado
import mapa.Comparendo

abstract case class Vehiculo(var placa: String)(val velmax: Velocidad, val aceleracionOriginal: Double)
  extends Movil(velmax, aceleracionOriginal) with MovimientoAcelerado {
  //aceleracion en m/s
  val figura: Shape
  val aleatorio = scala.util.Random
  val viaje = new Viaje(this)
  var viaActual = viaje.pila.dequeue()
  var siguienteSemaforo: Semaforo = null
  var lugarSemaforo: Punto = null
  var proximaInter: Interseccion = viaje.interF
  posicion = new Punto(viaje.interI.x, viaje.interI.y)
  var angulo: Double = 0
  var frenado: Double = 0
  var atraviesaAmarillo: Boolean = false
  var tieneComparendoEnViaActual:Boolean = false
  var distanciaRecorrida:Double = 0
  var distanciaHastaSemaforo: Double = 0

  Simulacion.listaVehiculos.append(this)

  def mover(dt: Double) {
    if (posicion.x == viaje.interF.x && posicion.y == viaje.interF.y) {
      viaje.setValoresViaje(viaje.interI, viaje.interF)
         distanciaRecorrida = {
    var suma: Double = 0
    viaje.pila.foreach(v => suma = suma + v.distancia)
    suma
  }
    } else if (siguienteSemaforo == null || siguienteSemaforo.estado == "Verde" || posicion != lugarSemaforo) {
      obtenerSiguientesDatos
      lugarSemaforo = siguienteSemaforo.lugar
      velActual.direccion.grados = angulo
      distanciaHastaSemaforo = math.abs(math.sqrt(math.pow((posicion.x - proximaInter.x), 2) + math.pow((posicion.y - proximaInter.y), 2)))
      if ( ((siguienteSemaforo.estado == "Rojo" || siguienteSemaforo.estado == "Amarillo")  || proximaInter == viaje.interF) && distanciaHastaSemaforo <= Simulacion.XSemaforoFrenar) {
        calcularFrenado //en la variable frenado se queda la aceleracion negativa necesaria para frenar al siguiente semaforo
        aceleracion = frenado
      }

      if (siguienteSemaforo.estado == "Amarillo" && distanciaHastaSemaforo <= Simulacion.XAmarilloContinuar) {
        aceleracion = aceleracionO

      }
      if(viaActual.tieneCamara && !(tieneComparendoEnViaActual)){
        revisarFotoMulta
      }
      aumentarPosicion(dt)
      margenDeErrorAceleracion
      revisarMargenDeError
    }
  }
  
  def revisarFotoMulta{
    val camara = viaActual.camara
    val distanciaHastaCamara = math.abs(math.sqrt(math.pow((camara.posicion.x - posicion.x), 2) + math.pow((camara.posicion.y - posicion.y), 2)))
    if(distanciaHastaCamara <=Velocidad.kilometroHorMetroSeg(Simulacion.maxVelocidad) * Simulacion.dt){
      if (velActual.magnitud > viaActual.velmax){
        new Comparendo(this,velActual.magnitud,viaActual.velmax)
        tieneComparendoEnViaActual = true
      }
    }
  }
  
  
  def calcularFrenado {
    val tiempoHastaSemaforo = distanciaHastaSemaforo / Velocidad.kilometroHorMetroSeg(velActual.magnitud)
    frenado = -Velocidad.kilometroHorMetroSeg(velActual.magnitud) / tiempoHastaSemaforo
  }
  
  def margenDeErrorAceleracion{      
    if (velActual.magnitud != 0 && velActual.magnitud < 0.1) {
        velActual.magnitud = 0
        aceleracion = aceleracionO
        posicion.x_(proximaInter.x)
        posicion.y_(proximaInter.y)
      }
  }

  def obtenerSiguientesDatos {
    if (posicion == viaActual.origen) {
      angulo = Simulacion.calcularTanInv(viaActual.origen.x, viaActual.fin.x, viaActual.origen.y, viaActual.fin.y)
      proximaInter = viaActual.fin
      siguienteSemaforo = viaActual.semaforos(0)
    } else if (posicion == viaActual.fin) {
      angulo = Simulacion.calcularTanInv(viaActual.fin.x, viaActual.origen.x, viaActual.fin.y, viaActual.origen.y)
      proximaInter = viaActual.origen
      siguienteSemaforo = viaActual.semaforos(1)
    }
  }

  def revisarMargenDeError {
    val diferenciax = posicion.x - proximaInter.x
    val diferenciay = posicion.y - proximaInter.y
    val hipotenusa = math.abs(math.sqrt(math.pow((diferenciax), 2) + math.pow((diferenciay), 2)))
    if (hipotenusa <= Velocidad.kilometroHorMetroSeg(Simulacion.maxVelocidad) * Simulacion.dt) {
      posicion.x_(proximaInter.x)
      posicion.y_(proximaInter.y)

      if (!viaje.pila.isEmpty) {
        viaActual = viaje.pila.dequeue()
        frenado = 0
        aceleracion = aceleracionO
        if(siguienteSemaforo.estado == "Rojo")velActual.magnitud = 0
        tieneComparendoEnViaActual = false
      }

    }
  }
  
  def revisarSemaforo{
    if(proximaInter == viaActual.origen){
      siguienteSemaforo =  viaActual.semaforos(1)
    }
    if(proximaInter == viaActual.fin){
      siguienteSemaforo = viaActual.semaforos(0)
    }
  }

}

object Vehiculo {

  def vehiculoAleatorio: Vehiculo = {
    val aleatorio = scala.util.Random
    val numeroAleatorio = aleatorio.nextInt(5)
    val magAleatoria = Simulacion.minVelocidad + aleatorio.nextInt(Simulacion.maxVelocidad - Simulacion.minVelocidad)
    val anguloAleatorio = aleatorio.nextInt(360)
    val aceleracion = Simulacion.minAce + aleatorio.nextInt((Simulacion.maxAce - Simulacion.minAce).round.toInt) + aleatorio.nextInt(100)/100 

    if (numeroAleatorio == 0) {
      val instancia = new Carro("", new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)), aceleracion)
      instancia
    } else if (numeroAleatorio == 1) {
      val instancia = new Moto("", new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)), aceleracion)
      instancia
    } else if (numeroAleatorio == 2) {
      val instancia = new Bus("", new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)), aceleracion)
      instancia
    } else if (numeroAleatorio == 3) {
      val instancia = new Camion("", new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)), aceleracion)
      instancia
    } else {
      val instancia = new MotoTaxi("", new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)), aceleracion)
      instancia
    }
  }
}