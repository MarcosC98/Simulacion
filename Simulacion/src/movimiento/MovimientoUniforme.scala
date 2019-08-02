package movimiento
trait MovimientoUniforme extends Movil {
  var velc:Double = _
  def aumentarPosicion(inicio:Int,velo:Double,dt:Double):Double = {
    val fin = inicio + velo*dt
    fin
  }  
}