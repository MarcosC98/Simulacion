package plano
case class Punto(private var _x:Int,private var _y:Int) {
  
  //Accesores
  def x = _x
  def y = _y
  def x_(x:Int):Unit = _x = x
  def y_(y:Int):Unit = _y = y
}