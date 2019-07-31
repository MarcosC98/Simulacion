package vehiculos
import mapa.Interseccion
import ejecucion.Simulacion
import plano.Punto
import movimiento.Velocidad
import scala.util.Random
class Carro (placaC:String,origen:Interseccion,velocidad:Velocidad) extends Vehiculo(placaC)(origen,velocidad){
  Simulacion.listaVehiculos.append(this)
  val aleatorio = scala.util.Random
  val tamanioInter = Simulacion.listaIntersecciones.size
  var c:Boolean = true
  var interF = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioInter))
  
  while(c){
    if(origen == interF){
      interF = Simulacion.listaIntersecciones(aleatorio.nextInt(tamanioInter))
    } else{
      c = false
    }
  }
}