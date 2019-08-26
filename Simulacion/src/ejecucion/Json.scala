package ejecucion
import net.liftweb.json._
import scala.io.Source
import java.io.PrintWriter
import java.io.File
import net.liftweb.json.Serialization.write
object Json{
  
  val ruta = "D:\\Eclipse - Scala\\proyectos\\git\\Simulacion\\Simulacion\\src\\"//Varia dependiendo de donde está el archivo
  val archivo = "parametros.json"
  val archivor = "resultados.json"
  val rutaarchivo = ruta + archivo
  var datos:Datos = null
  
  implicit val formats = DefaultFormats
  
  case class pametrosVehiculos(minimo:Int, maximo:Int)
  
  case class pametrosVelocidad(minimo:Int, maximo:Int)
  
  case class pametrosProporciones(carros:Double,motos:Double,buses:Double
      ,camiones:Double,motoTaxis:Double)
      
  case class pametrosSemaforos(minTiempoVerde:Int,maxTiempoVerde:Int,tiempoAmarillo:Int)
  
  case class pametrosAceleracion(minimo:Int,maximo:Int)
  
  case class pametrosDistanciaSemaforos(XSemaforoFrenar:Int,XSemaforoAmarilloContinuar:Int)
      
  case class pametrosSimulacion(dt :Double,tRefresh:Double,vehiculos:pametrosVehiculos,
      velocidad:pametrosVelocidad,proporciones:pametrosProporciones,semaforos:pametrosSemaforos,aceleracion:pametrosAceleracion,distanciasFrenadoVehiculos:pametrosDistanciaSemaforos)

  case class Datos(pametrosSimulacion:pametrosSimulacion) 
  
  //Ejecutar con cargarDatosJson(ruta+archivo)
  def cargarDatosJson{
    
    val cadena = Source.fromFile(rutaarchivo).getLines().mkString
    val json = parse(cadena)
    datos = json.extract[Datos]
    println("Funcionó el extraido de datos")  
  }
  
    def escribirArchivo( resultados: ResultadosSimulacion) {
    val pw = new PrintWriter(new File(ruta+archivor))
    pw.write(write(resultados))
    pw.close
    println("Se escribió archivo")
  }
}