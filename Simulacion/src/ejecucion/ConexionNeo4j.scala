package ejecucion
import org.neo4j.driver.v1._
import scala.collection.mutable.ArrayBuffer
import mapa.Interseccion
import mapa._

object ConexionNeo4J {
  val url = "bolt://localhost:7687"
  val user = "neo4j"
  val pass = "1234"

  def getSession(): (Driver, Session) = {
    val driver = GraphDatabase.driver(url, AuthTokens.basic(user, pass))
    val session = driver.session
    (driver, session)
  }

  def getIntersecciones = {
    val (driver, session) = getSession()
    val script = s"MATCH (i:Interseccion)RETURN i"
    val resultado = session.run(script)
    while (resultado.hasNext()) {
      val nodo = resultado.next().values().get(0)
      val nombreInterseccion: Option[String] = Some(nodo.get("nombre").asString())
      val inter = new Interseccion(nodo.get("x").asInt(), nodo.get("y").asInt(), nombreInterseccion)
    }
    session.close()
    driver.close()
  }

  def getVias = {
    val (driver, session) = getSession()
    val script = s"MATCH (v:Via)-[:INICIA_EN]-(i:Interseccion), (v)-[:TERMINA_EN]-(i2) RETURN v,i,i2"
    val resultado = session.run(script)
    while (resultado.hasNext()) {
      val fila = resultado.next()
      val nodoVia = fila.values().get(0)
      val nodoInterI = fila.values().get(1)
      val nodoInterF = fila.values().get(2)
      val interI = new Interseccion(nodoInterI.get("x").asInt(),nodoInterI.get("y").asInt(),Some(nodoInterI.get("nombre").asString()))
      val interF = new Interseccion(nodoInterF.get("x").asInt(),nodoInterF.get("y").asInt(),Some(nodoInterF.get("nombre").asString()))
      val caracteristicaSentido = nodoVia.get("sentido").asString()
      var sentido:Sentido = null
      if(caracteristicaSentido =="Doble") sentido = Sentido.dobleVia
      if(caracteristicaSentido == "Una") sentido = Sentido.unaVia
      var tipovia = new TipoVia(nodoVia.get("tipo").asString())
      val via = new Via(interI,interF,nodoVia.get("velmax").asInt(),tipovia,sentido,nodoVia.get("numero").asString(),Some(nodoVia.get("nombre").asString()))
    }
    session.close()
    driver.close()
  }

}