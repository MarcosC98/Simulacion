package mapa
import java.awt.Rectangle

import ejecucion.Simulacion
import plano.Punto

class CamaraFotoDeteccion(_via :Via,_distanciaDeOrigen:Int) {
  val figura = new Rectangle(-8,-8,6,6)
  def via = _via
  def distanciaDeOrigen = _distanciaDeOrigen
  Simulacion.listaCamaras.append(this)
 val angulo = Simulacion.calcularTanInv(via.origen.x, via.fin.x, via.origen.y, via.fin.y)
 var posicion:Punto = null
  if(angulo == 90){
    posicion = Punto(via.origen.x,via.origen.y + distanciaDeOrigen)
  }
  else if(angulo ==180){
    posicion = Punto(via.origen.x - distanciaDeOrigen, via.origen.y)
  }
  else if(angulo == 270){
    posicion = Punto(via.origen.x,via.origen.y - distanciaDeOrigen)
  }
  else if(angulo == 0){
    posicion = Punto(via.origen.x + distanciaDeOrigen,via.origen.y)
  }else{
    posicion = Punto( via.origen.x + math.cos(angulo.toRadians)*distanciaDeOrigen,via.origen.y+math.sin(angulo.toRadians)*distanciaDeOrigen)
  }
  via.tieneCamara = true
  via.camara = this
}