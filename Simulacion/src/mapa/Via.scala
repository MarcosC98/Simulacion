package mapa
import plano.Recta
import ejecucion.Simulacion
import scala.collection.mutable.ArrayBuffer
import plano.Angulo
class Via(iorigen: Interseccion, ifinal: Interseccion, _velmax: Int,
          tipoVia: TipoVia, _sentido: Sentido, numero: String, var nombreVia: Option[String]) extends Recta {
  Simulacion.listaVias.append(this)
  type T = Interseccion
  val origen = iorigen
  val fin = ifinal
  val velmax = _velmax
  val semaforos = ArrayBuffer[Semaforo]()//El semaforo en 0 será el de fin, y el de 1 será el origen si es doble via
  def sentido = _sentido
  def distancia = math.abs(math.sqrt(math.pow((origen.x - fin.x), 2) + math.pow((origen.y - fin.y), 2)))
  val angulo = new Angulo(Simulacion.calcularTanInv(origen.x, fin.x, origen.y, fin.y))
  semaforos.append(new Semaforo(fin))
  if (_sentido.nombre == "Doble via") {
    semaforos.append(new Semaforo(origen))
  }else{
    semaforos.append(null)
  }
  var tieneCamara:Boolean = false
  var camara:CamaraFotoDeteccion = null
}