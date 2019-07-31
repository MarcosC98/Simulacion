package vehiculos
import plano.Punto
import mapa.Interseccion
import movimiento.Movil
import movimiento.MovimientoUniforme
import movimiento.Velocidad
import scala.util.Random
import ejecucion.Simulacion
abstract case class Vehiculo(val placa:String)(val posi:Interseccion,val vel:Velocidad)
extends Movil(posi,vel) with MovimientoUniforme {
  var interF:Interseccion
}
