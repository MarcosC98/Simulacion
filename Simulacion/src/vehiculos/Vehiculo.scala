package vehiculos
import java.awt.Shape

import plano.Punto
import mapa.Interseccion
import movimiento.Movil
import movimiento.MovimientoUniforme
import movimiento.Velocidad

import scala.util.Random
import ejecucion.Simulacion
import plano.Angulo
import ejecucion.GrafoVia
import mapa.Via

import scala.collection.mutable.Queue

abstract case class Vehiculo(var placa:String)(val vel:Velocidad)
extends Movil(vel) with MovimientoUniforme {
  
  val figura: Shape
  val aleatorio = scala.util.Random
  val tamanioInter = Simulacion.listaIntersecciones.size
  
  val viaje = new Viaje(this)
  
  var viaActual = viaje.pila.dequeue()
  var proximaInter:Interseccion = viaje.interF
  posicion = new Punto(viaje.interI.x,viaje.interI.y)
  var angulo:Double = 0
  
  val distanciaARecorrer = {
    var suma:Double = 0
    viaje.pila.foreach(v => suma = suma + v.distancia)
    suma
  }
  
Simulacion.listaVehiculos.append(this)  

def mover(dt:Double){
    if(posicion.x==viaje.interF.x && posicion.y == viaje.interF.y){
    }else{
      if(posicion==viaActual.origen){
        angulo = Simulacion.calcularTanInv(viaActual.origen.x, viaActual.fin.x, viaActual.origen.y, viaActual.fin.y)
        proximaInter = viaActual.fin
      }else if(posicion == viaActual.fin){
        angulo = Simulacion.calcularTanInv(viaActual.fin.x, viaActual.origen.x, viaActual.fin.y, viaActual.origen.y)
        proximaInter = viaActual.origen
        }
      vel.direccion.grados = angulo
      aumentarPosicion(dt)
      val diferenciax = posicion.x - proximaInter.x
      val diferenciay = posicion.y - proximaInter.y
      val hipotenusa = math.abs(math.sqrt(math.pow((diferenciax ),2) +math.pow((diferenciay ), 2)))

      if(hipotenusa <= Velocidad.kilometroHorMetroSeg(Simulacion.maxVelocidad)*dt){
        posicion.x_(proximaInter.x)
        posicion.y_(proximaInter.y)

        if(!viaje.pila.isEmpty){
          viaActual = viaje.pila.dequeue()
        }
        
      }
    }
}
  
}

object Vehiculo{
  def vehiculoAleatorio:Vehiculo ={
      val aleatorio = scala.util.Random
      val numeroAleatorio = aleatorio.nextInt(5)
      val magAleatoria = Simulacion.minVelocidad + aleatorio.nextInt(Simulacion.maxVelocidad - Simulacion.minVelocidad)
      val anguloAleatorio = aleatorio.nextInt(360)

      
      if (numeroAleatorio == 0){
        val instancia = new Carro("",new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }       
      else if (numeroAleatorio == 1){
        val instancia = new Moto("",new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }else if (numeroAleatorio == 2){
        val instancia = new Bus("",new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }else if (numeroAleatorio == 3){
        val instancia = new Camion("",new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }else{
        val instancia = new MotoTaxi("",new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }
  }
}