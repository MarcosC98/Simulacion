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

abstract case class Vehiculo(var placa:String)(val posi:Interseccion,val vel:Velocidad)
extends Movil(posi,vel) with MovimientoUniforme {
  
  val aleatorio = scala.util.Random
  
  val tamanioInter = Simulacion.listaIntersecciones.size
  
  var c:Boolean = true
  var interF:Interseccion = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioInter))

  while(c){
    if(posi == interF){
      interF = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioInter))
    } else{
      c = false
    }
  }
  
  val nodoi = GrafoVia.grafo.get(posi)
  val nodof = GrafoVia.grafo.get(interF)
  val recorrido = nodoi.shortestPathTo(nodof).get.edges.toList.map(_.toOuter.label.asInstanceOf[Via])

Simulacion.listaVehiculos.append(this)  

def mover(dt:Double){

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