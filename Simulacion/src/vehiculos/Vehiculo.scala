package vehiculos
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
Simulacion.listaVehiculos.append(this)  

def mover(dt:Double){
    var angulo:Double = 0
    var proximaInter:Interseccion = null
    if(posicion==interF){
      return //pasar
    }else{
      if(posicion==viaActual.origen){
        angulo = Simulacion.calcularTanInv(viaActual.origen.x, viaActual.fin.x, viaActual.origen.y, viaActual.fin.y)
        proximaInter = viaActual.fin
      }else if(posicion == viaActual.fin){
        angulo = Simulacion.calcularTanInv(viaActual.fin.x, viaActual.origen.x, viaActual.fin.y, viaActual.origen.y)
        proximaInter = viaActual.origen
      }
      velocidad.direccion = new Angulo(angulo)
      println(posicion)
      aumentarPosicion(dt)
      println(posicion)
      val hipotenusa = math.abs(math.sqrt(math.pow((posicion.x - proximaInter.x ),2) +math.pow((posicion.y - proximaInter.y ), 2)))
      if(hipotenusa <= velocidad.magnitud *dt){
        println("Se llego a interseccion!")
        posicion_(proximaInter.asInstanceOf[Punto])
        println(proximaInter.nombre)
        if(!pila.isEmpty){
          viaActual = pila.dequeue()
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