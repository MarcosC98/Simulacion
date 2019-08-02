package plano
abstract case class Punto(private val _x:Int,private val _y:Int) {
  
  //Accesores
  def x = _x
  def y = _y
}