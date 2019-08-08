package plano
case class Punto(private var _x:Double,private var _y:Double) {
  
  //Accesores
  def x = _x
  def y = _y
  def x_(x:Double):Unit = _x = x
  def y_(y:Double):Unit = _y = y

}