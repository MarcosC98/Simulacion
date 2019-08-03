package movimiento
trait MovimientoUniforme extends Movil {
  val velc = Velocidad.kilometroHorMetroSeg(velocidad.magnitud)
  def aumentarPosicion(dt:Double):Unit = {   
    val velx = math.cos(velocidad.direccion.grados.toRadians) * velc
    val vely = math.sin(velocidad.direccion.grados.toRadians) * velc
    posicion.x_((posicion.x + velx * dt).toInt)
    posicion.y_((posicion.y + vely * dt).toInt)
  }  
}