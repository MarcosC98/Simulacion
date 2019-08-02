package movimiento
import plano.Angulo
case class Velocidad(val magnitud:Int)(val direccion:Angulo) {
}
  object Velocidad{
    
    def metroSegkilometroHor(metroSegundo:Double):Double=metroSegundo*3.6
    def kilometroHorMetroSeg(kilometroHora:Double):Double=kilometroHora/3.6
  }
