package mapa
import scala.collection.mutable.ArrayBuffer
import plano.Punto
import ejecucion.Simulacion

class NodoSemaforo {
  

  def controlarFlujoSemaforos {
    Simulacion.listaIntersecciones.foreach(i => {
      val semaforos = hallarSemaforos(i)
      controlarSemaforos(semaforos)
    })
  }
  
  
  def hallarSemaforos(i:Interseccion):ArrayBuffer[Semaforo] = {
    var numero = ArrayBuffer[Semaforo]()
    Simulacion.listaSemaforos.foreach(s => {
      if(s.lugar.x == i.x && s.lugar.y == i.y){
        numero.append(s)
      }
    })
    numero
  }
  
  def controlarUnSemaforo(s:Semaforo){
    
    if(s.estado == "Verde"){
      s.tVerde = (s.tVerde - Simulacion.dt)    
      
      if(s.tVerde <= 0){      
        s.tVerde = s.tVerdeOriginal
        s.estado = "Amarillo"
      }
    }
    
    if(s.estado == "Amarillo"){
      s.tAmarillo = (s.tAmarillo - Simulacion.dt)
      if(s.tAmarillo <= 0){
        s.tAmarillo = s.tAmarilloOriginal
        s.estado = "Rojo"
      }
    }
  }
  
  def controlarSemaforos(semaforos:ArrayBuffer[Semaforo]){
    //El primero en verde, el resto en rojo(Solo entra al inicio cuando todos los semaforos están en verde)
    if(semaforos.size == semaforos.filter(s => s.estado == "Verde").size){
      semaforos.foreach(s => s.estado = "Rojo")
      semaforos(0).estado = "Verde"
    }
    //El actual estará en rojo o amarillo. Se envia a controlarUnSemaforo
    val semaforoActual = semaforos.filter(s => s.estado == "Verde" || s.estado == "Amarillo")(0)
    controlarUnSemaforo(semaforoActual)
    
    //Si cambia a rojo, se setea el estado Verde al siguiente(O si es el ultimo, al primero)
    if(semaforoActual.estado == "Rojo"){
      if(semaforos.indexOf(semaforoActual) == semaforos.size-1){
        semaforos(0).estado = "Verde"
      }else{
          semaforos(semaforos.indexOf(semaforoActual) + 1).estado = "Verde"
      }
    }
  }
}



















