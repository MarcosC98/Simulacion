package ejecucion
import org.neo4j.driver.v1._
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
  
  def getCamaras{
    val (driver,session) = getSession()
    val script = s"MATCH (v:Via)-[:INICIA_EN]-(i:Interseccion), (v)-[:TERMINA_EN]-(i2),(v)-[:ESTA_EN]-(c) RETURN i,i2,c"
    val resultado = session.run(script)
    while(resultado.hasNext()){
      val fila = resultado.next()
      val nodoInterI = fila.values().get(0)
      val nodoInterF = fila.values().get(1)
      val distanciaOrigen = fila.values().get(2).get("distanciaDeOrigen").asInt()
      val via = buscarVia(nodoInterI.get("x").asInt(), nodoInterI.get("y").asInt(), nodoInterF.get("x").asInt, nodoInterF.get("y").asInt)
      new CamaraFotoDeteccion(via,distanciaOrigen)
    }
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
  
  def buscarVia(x1:Int,y1:Int,x2:Int,y2:Int):Via ={
    var via:Via = null
    for( x:Int <- 0 to Simulacion.listaVias.size -1){
      val viaBuscada = Simulacion.listaVias(x)
      if(viaBuscada.origen.x == x1.toDouble && viaBuscada.origen.y == y1.toDouble && viaBuscada.fin.x == x2.toDouble && viaBuscada.fin.y == y2.toDouble){
        via = viaBuscada
      }
    }
    via
  }
  

}