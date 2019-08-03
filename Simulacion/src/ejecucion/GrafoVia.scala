package ejecucion
import mapa.{Interseccion, Via}
import scalax.collection.mutable.Graph
import scalax.collection.edge.WLDiEdge

import scala.collection.mutable.ArrayBuffer

object GrafoVia {
  val grafo =Graph[Interseccion,WLDiEdge]()
  
  def revisarSentido(v:Via){

    if(v.sentido.nombre == "Doble via"){
      grafo.add(WLDiEdge(v.fin,v.origen)(v.distancia,v))
    }
      grafo.add(WLDiEdge(v.origen,v.fin)(v.distancia,v))
  }

  def construir(vias:ArrayBuffer[Via]): Unit ={
    vias.foreach(r => revisarSentido(r))
  }
  
  def Dijkstra(origen: Interseccion, destino: Interseccion): Option[GrafoVia.grafo.Path] = {
    grafo.get(origen).shortestPathTo(grafo.get(destino))
  }
}
