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

abstract case class Vehiculo(var placa:String)(val interInicial:Interseccion,val vel:Velocidad)
extends Movil(interInicial,vel) with MovimientoUniforme {
  val figura: Shape
  val aleatorio = scala.util.Random
  val tamanioInter = Simulacion.listaIntersecciones.size
  
  var c:Boolean = true
  var interF:Interseccion = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioInter))
  while(c){
    if(interInicial == interF){
      interF = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioInter))
    } else{
      c = false
    }
  }
  
  val nodoi = GrafoVia.grafo.get(interInicial)
  val nodof = GrafoVia.grafo.get(interF)
  val recorrido = nodoi.shortestPathTo(nodof).get.edges.toList.map(_.toOuter.label.asInstanceOf[Via])
  val pila = Queue(recorrido: _*)
  var viaActual = pila.dequeue()
  var proximaInter:Interseccion = interF
  var angulo:Double = 0
  
Simulacion.listaVehiculos.append(this)  

def mover(dt:Double){
    if(posicion.x==interF.x && posicion.y == interF.y){
    }else{
      if(posicion==viaActual.origen){
        angulo = Simulacion.calcularTanInv(viaActual.origen.x, viaActual.fin.x, viaActual.origen.y, viaActual.fin.y)
        proximaInter = viaActual.fin
        println(viaActual.origen.nombre + " a " + proximaInter._nombre+ " con angulo " + angulo)
      }else if(posicion == viaActual.fin){
        angulo = Simulacion.calcularTanInv(viaActual.fin.x, viaActual.origen.x, viaActual.fin.y, viaActual.origen.y)
        proximaInter = viaActual.origen
        println(viaActual.fin.nombre + " a " + proximaInter.nombre+  " con angulo " + angulo)
      }
      vel.direccion.grados = angulo
      aumentarPosicion(dt)
      println(posicion)
      val diferenciax = posicion.x - proximaInter.x
      val diferenciay = posicion.y - proximaInter.y
      println(diferenciax)
      println(diferenciay)
      val hipotenusa = math.abs(math.sqrt(math.pow((diferenciax ),2) +math.pow((diferenciay ), 2)))
      if(hipotenusa <= Velocidad.metroSegkilometroHor(Simulacion.maxVelocidad)*dt*1.3){
        println("pos antes" + posicion)
        posicion.x_(proximaInter.x)
        posicion.y_(proximaInter.y)
        println("pos despues" + posicion)

        if(!pila.isEmpty){
          viaActual = pila.dequeue()
        }else{
          println("Terminaste!")
          println("de: " + interInicial + " a " + interF)
        }
      }
    }
}
  
}

object Vehiculo{
  def vehiculoAleatorio:Vehiculo ={
      val aleatorio = scala.util.Random
      val numeroAleatorio = aleatorio.nextInt(5)
      val interseccionAleatoria = Simulacion.listaIntersecciones(aleatorio.nextInt(Simulacion.listaIntersecciones.length))
      val magAleatoria = Simulacion.minVelocidad + aleatorio.nextInt(Simulacion.maxVelocidad - Simulacion.minVelocidad)
      val anguloAleatorio = aleatorio.nextInt(360)

      
      if (numeroAleatorio == 0){
        val instancia = new Carro("",interseccionAleatoria,new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }       
      else if (numeroAleatorio == 1){
        val instancia = new Moto("",interseccionAleatoria,new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }else if (numeroAleatorio == 2){
        val instancia = new Bus("",interseccionAleatoria,new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }else if (numeroAleatorio == 3){
        val instancia = new Camion("",interseccionAleatoria,new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }else{
        val instancia = new MotoTaxi("",interseccionAleatoria,new Velocidad(magAleatoria)(new Angulo(anguloAleatorio)))
        instancia
      }
  }
}