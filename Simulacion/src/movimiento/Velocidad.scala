package movimiento
import plano.Angulo
case class Velocidad(val magnitud:Int)(val direccion:Angulo) {
  object Velocidad{
    
    val metroSegkilometroHor=(metroSegundo:Double)=>metroSegundo*3.6
    val kilometroHorMetroSeg=(kilometroHora:Double)=>kilometroHora/3.6
  }
}