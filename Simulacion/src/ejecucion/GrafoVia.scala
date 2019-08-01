package ejecucion
import mapa.{Interseccion, Via}
import scalax.collection.mutable.Graph
import scalax.collection.edge.WLUnDiEdge

import scala.collection.mutable.ArrayBuffer

object GrafoVia {
  val grafo =Graph[Interseccion,WLUnDiEdge]()
  def construir(vias:ArrayBuffer[Via]): Unit ={
    vias.foreach(r => grafo.add(WLUnDiEdge.apply(r.origen,r.fin)(r.distancia,r.nombreVia)))
  }
  def Dijkstra(origen: Interseccion, destino: Interseccion): Option[GrafoVia.grafo.Path] = {
    grafo.get(origen).shortestPathTo(grafo.get(destino))
  }
}