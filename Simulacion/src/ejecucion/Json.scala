package ejecucion
import net.liftweb.json._
import scala.collection.mutable.ArrayBuffer
import scala.io.Source
import scala.collection.mutable.Map

object Json{
  
  val ruta = "C:\\Users\\marco\\git\\Simulacion\\Simulacion\\src\\"//Varia dependiendo de donde está el archivo
  val archivo = "parametros.json"
  val rutaarchivo = ruta + archivo
  var datos:Datos = null
  
  implicit val formats = DefaultFormats
  
  case class pametrosVehiculos(minimo:Int, maximo:Int)
  
  case class pametrosVelocidad(minimo:Int, maximo:Int)
  
  case class pametrosProporciones(carros:Double,motos:Double,buses:Double
      ,camiones:Double,motoTaxis:Double)
      
  case class pametrosSimulacion(dt :Int,tRefresh:Int,vehiculos:pametrosVehiculos,
      velocidad:pametrosVelocidad,proporciones:pametrosProporciones)
      
  case class Datos(pametrosSimulacion:pametrosSimulacion) 
  
  //Ejecutar con cargarDatosJson(ruta+archivo)
  def cargarDatosJson{
    
    val cadena = Source.fromFile(rutaarchivo).getLines().mkString
    val json = parse(cadena)
    datos = json.extract[Datos]
    println("Funcionó el extraido de datos")  
  }
}