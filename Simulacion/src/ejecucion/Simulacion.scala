package ejecucion
import graficacion.Grafico
import movimiento.Velocidad
import vehiculos._
import mapa._
import plano.Angulo

import scala.collection.mutable.ArrayBuffer
//Singleton Object para que solo haya una instancia
import vehiculos.Vehiculo
import scala.util
import mapa.CamaraFotoDeteccion

object Simulacion extends Runnable{
  
  val aleatorio = scala.util.Random
  var promedioOrigen:Double = _
  var promedioDestino:Double = _
  val dt = Json.datos.pametrosSimulacion.dt
  val tRefresh = Json.datos.pametrosSimulacion.tRefresh
  val msEspera = (tRefresh * 1000).toLong
  val minVehiculos = Json.datos.pametrosSimulacion.vehiculos.minimo
  val maxVehiculos = Json.datos.pametrosSimulacion.vehiculos.maximo
  val minVelocidad = Json.datos.pametrosSimulacion.velocidad.minimo
  val maxVelocidad = Json.datos.pametrosSimulacion.velocidad.maximo
  val minTiempoVerde = Json.datos.pametrosSimulacion.semaforos.minTiempoVerde
  val maxTiempoVerde = Json.datos.pametrosSimulacion.semaforos.maxTiempoVerde
  val tiempoAmarillo = Json.datos.pametrosSimulacion.semaforos.tiempoAmarillo
  val minAce = Json.datos.pametrosSimulacion.aceleracion.minimo
  val maxAce = Json.datos.pametrosSimulacion.aceleracion.maximo
  val XSemaforoFrenar = Json.datos.pametrosSimulacion.distanciasFrenadoVehiculos.XSemaforoFrenar
  val XAmarilloContinuar = Json.datos.pametrosSimulacion.distanciasFrenadoVehiculos.XSemaforoAmarilloContinuar
  val numeroVehiculos = minVehiculos + aleatorio.nextInt(maxVehiculos - minVehiculos)
  val buses = scala.math.round(Json.datos.pametrosSimulacion.proporciones.buses * numeroVehiculos)
  val carros = scala.math.round(Json.datos.pametrosSimulacion.proporciones.carros * numeroVehiculos)
  val motos = scala.math.round(Json.datos.pametrosSimulacion.proporciones.motos * numeroVehiculos)
  val mototaxis = scala.math.round(Json.datos.pametrosSimulacion.proporciones.motoTaxis * numeroVehiculos)
  val camiones = scala.math.round(Json.datos.pametrosSimulacion.proporciones.camiones * numeroVehiculos)
  val Nodo = new NodoSemaforo
  var t: Double =0
  

  val listaIntersecciones = ArrayBuffer[Interseccion]()
  val listaVias = ArrayBuffer[Via]()
  val listaVehiculos = ArrayBuffer[Vehiculo]()
  val listaViajes = ArrayBuffer[Viaje]()
  val listaSemaforos = ArrayBuffer[Semaforo]()
  val listaCamaras =  ArrayBuffer[CamaraFotoDeteccion]()
  val listaComparendos = ArrayBuffer[Comparendo]()

  var c:Boolean = true
 def run() {
 while (c) {
   Nodo.controlarFlujoSemaforos
   listaVehiculos.foreach(_.mover(dt))
   t += dt
   Grafico.graficarVehiculos(listaVehiculos)
   Thread.sleep(msEspera)
   if (terminar) c = false
 }
 
 if(terminar == true){
   promedioDestino = calcularPromedioVehiInter
   enviarDatosResultadosSimulacion
 }
 
}
  
  def parar={
    c=false
  }
  
  def iniciar = {
    c = true
    run
  }
 

def calcularVehiculosEnInter{
  listaIntersecciones.foreach(i =>{
  var contadorVehiculos = 0
  listaVehiculos.foreach(v => 
    if(v.posicion.x == i.x && v.posicion.y == i.y){
      contadorVehiculos = contadorVehiculos +  1
    })
    i.vehiculosEnInter_(contadorVehiculos)
})
}

def calcularPromedioVehiInter:Double ={
  calcularVehiculosEnInter
  var promedio:Double = 0
  listaIntersecciones.foreach(i=>{
  promedio = promedio + (i.vehiculosEnInter.toDouble / listaIntersecciones.size.toDouble)
  })
  promedio
}

def terminar :Boolean = {
  var listaVehiculosTerminados = listaVehiculos.filter(v => v.posicion.x == v.viaje.interF.x && v.posicion.y == v.viaje.interF.y)
  var listaVehiculosNoTerminados = listaVehiculos.filter(v=> v.posicion.x != v.viaje.interF.x && v.posicion.y != v.viaje.interF.y)
  if (listaVehiculosTerminados.size ==  listaVehiculos.size){
    return true
  }else{
    return false
  }
}



def enviarDatosResultadosSimulacion{

  val longitudPromedio = {
  var suma:Double = 0
  listaVias.foreach(v =>suma = suma + v.distancia)
  suma / listaVias.size
}

val mayorVelocidad = {
  val lista = (listaVehiculos.sortBy(v => v.velmax.magnitud))
  lista(lista.size-1).velmax.magnitud
}

val menorVelocidad = {
  val lista = (listaVehiculos.sortBy(v => v.velmax.magnitud))
  lista(0).velmax.magnitud
}

val promedioVelocidad = {
  var suma:Double = 0
  listaVehiculos.foreach(v => suma = suma+v.velmax.magnitud)
  suma/listaVehiculos.size
}

val distanciaMinima = {
  val lista =listaVehiculos.sortBy(v => v.distanciaRecorrida)
  lista(1).distanciaRecorrida
}

val distanciaMaxima = {
  val lista = listaVehiculos.sortBy(v => v.distanciaRecorrida)
  lista(lista.size - 1).distanciaRecorrida
}

val distanciaPromedio = {
  var suma:Double = 0
  listaVehiculos.foreach(v => suma = suma + v.distanciaRecorrida)
  suma/listaVehiculos.size
}

val promedioComparendos = {
  var sumaExcesos:Double = 0
  listaComparendos.foreach(c => {
    sumaExcesos = sumaExcesos + c.exceso
  })
  val promedio = sumaExcesos/listaComparendos.size - 1
  promedio
}


  val vehiculos = Vehiculos(numeroVehiculos,Simulacion.carros.toInt,
      Simulacion.motos.toInt,Simulacion.buses.toInt,
      Simulacion.camiones.toInt,Simulacion.mototaxis.toInt)
  val vehiculosenInter  = VehiculosEnInter(Simulacion.promedioOrigen,
      Simulacion.promedioDestino,0,0)
  val mallaVial = MallaVial(listaVias.size,listaIntersecciones.size,
      listaVias.filter(_.sentido.nombre == "Unico sentido").size,
      listaVias.filter(_.sentido.nombre == "Doble via").size,
      menorVelocidad,mayorVelocidad,
      longitudPromedio,vehiculosenInter)
  val tiempo = Tiempo(Simulacion.t,Simulacion.t * tRefresh * 1/100)
  val velocidad = VelocidadResultados(minVelocidad,maxVelocidad,promedioVelocidad)
  val distancia = Distancia(distanciaMinima,distanciaMaxima,distanciaPromedio)
  val comparendos = Comparendos(listaComparendos.size,promedioComparendos)
  val resultados =  Resultados(vehiculos,mallaVial,tiempo,velocidad,distancia,comparendos)
  val resultadosSimulacion = ResultadosSimulacion(resultados)
  Json.escribirArchivo(resultadosSimulacion)
  println("PROGRAMA FINALIZA")
  System.exit(1)
}


  
def generarVehiculosAleatorios{
  //contadores para proporciones:
  var b = 0
  var c = 0
  var m = 0
  var mt = 0
  var ca = 0
while(b<buses || c<carros || m<motos || mt<mototaxis || ca<camiones){
    val v = Vehiculo.vehiculoAleatorio
    
    if (v.isInstanceOf[Carro]){
      c = c + 1
      if(c > carros){
        listaVehiculos.remove(listaVehiculos.length-1)
      }
    }
    
    if (v.isInstanceOf[Bus]){
      b = b + 1
      if ( b > buses){
        listaVehiculos.remove(listaVehiculos.length-1)
      }
    }
    
    if (v.isInstanceOf[Moto]){
      m = m + 1
      if ( m > motos){
        listaVehiculos.remove(listaVehiculos.length-1)
      }
    }
    
    if (v.isInstanceOf[Camion]){
      ca = ca + 1
      if ( ca > camiones){
        listaVehiculos.remove(listaVehiculos.length-1)
      }
    }
    
    if (v.isInstanceOf[MotoTaxi]){
      mt = mt + 1
      if ( mt > mototaxis){
        listaVehiculos.remove(listaVehiculos.length-1)
      }
    }
  }
  if(listaVehiculos.size > numeroVehiculos){
    listaVehiculos.remove(listaVehiculos.size-1)
  }
  if(listaVehiculos.size < numeroVehiculos){
    Vehiculo.vehiculoAleatorio
  }
  promedioOrigen = calcularPromedioVehiInter
}

def cargarDatosIniciales{
  ConexionNeo4J.getVias
  ConexionNeo4J.getCamaras
}
  
def calcularTanInv(x1:Double,x2:Double,y1:Double,y2:Double):Double ={
  val difx = x2 - x1
  val dify = y2 - y1
  
  if (difx == 0){
    if(dify > 0){
      return 90
    }else{
      return 270
    }
  }else if(dify == 0){
    if(difx > 0){
      return 0
    }else{
      return 180
    }
  }else{
    val m = dify/difx
    val a = scala.math.atan(m).toDegrees
    if(difx > 0 && dify > 0){
      //primer cuadrante
      return a
    }else if(difx < 0 && dify >0){
      //segundo cuadrante"
      return a + 180
    }else if(difx < 0 && dify <0){
      //tercer cuadrante
      return a + 180
    }else if (difx > 0 && dify<0){
      //cuarto cuadrante
      return 360 + a
    }else{
      return 0
    }
  }
}
}

