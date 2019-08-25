package movimiento
import vehiculos.Vehiculo
trait MovimientoAcelerado extends Movil {
 
  
  def aumentarPosicion(dt:Double):Unit = {
    val velc = Velocidad.kilometroHorMetroSeg(velActual.magnitud)
    var velx = math.cos(velActual.direccion.grados.toRadians).toDouble * velc.toDouble 
    var vely = math.sin(velActual.direccion.grados.toRadians).toDouble * velc.toDouble  
    
    
    this.posicion.x_(posicion.x.toDouble + velx * dt)
    this.posicion.y_(posicion.y.toDouble + vely * dt)
    velActual.magnitud = velActual.magnitud + Velocidad.metroSegkilometroHor(aceleracion * dt)
    
    if(velActual.magnitud > velMax){
      velActual.magnitud = velMax
    }
    
  } 
}