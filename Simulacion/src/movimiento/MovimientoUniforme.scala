package movimiento
trait MovimientoUniforme extends Movil {
  val velc = Velocidad.kilometroHorMetroSeg(velocidad.magnitud)
  def aumentarPosicion(dt:Double):Unit = {   
    val velx = math.cos(velocidad.direccion.grados.toRadians).toDouble * velc.toDouble
    val vely = math.sin(velocidad.direccion.grados.toRadians).toDouble * velc.toDouble
    this.posicion.x_((posicion.x.toDouble + velx * dt).toInt)
    this.posicion.y_((posicion.y.toDouble + vely * dt).toInt)
  }  
}