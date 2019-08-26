package ejecucion
import org.neo4j.driver.v1._
import mapa.Interseccion
import mapa._
import scala.collection.mutable.ArrayBuffer
import vehiculos._
object ConexionNeo4J {
  val url = "bolt://localhost:7687"
  val user = "neo4j"
  val pass = "1234"

  def getSession(): (Driver, Session) = {
    val driver = GraphDatabase.driver(url, AuthTokens.basic(user, pass))
    val session = driver.session
    (driver, session)
  }

  def getCamaras {
    val (driver, session) = getSession()
    val script = s"MATCH (v:Via)-[:INICIA_EN]-(i:Interseccion), (v)-[:TERMINA_EN]-(i2),(v)-[:ESTA_EN]-(c) RETURN i,i2,c"
    val resultado = session.run(script)
    while (resultado.hasNext()) {
      val fila = resultado.next()
      val nodoInterI = fila.values().get(0)
      val nodoInterF = fila.values().get(1)
      val distanciaOrigen = fila.values().get(2).get("distanciaDeOrigen").asInt()
      val via = buscarVia(nodoInterI.get("x").asInt(), nodoInterI.get("y").asInt(), nodoInterF.get("x").asInt, nodoInterF.get("y").asInt)
      new CamaraFotoDeteccion(via, distanciaOrigen)
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
      val interI = new Interseccion(nodoInterI.get("x").asInt(), nodoInterI.get("y").asInt(), Some(nodoInterI.get("nombre").asString()))
      val interF = new Interseccion(nodoInterF.get("x").asInt(), nodoInterF.get("y").asInt(), Some(nodoInterF.get("nombre").asString()))
      val caracteristicaSentido = nodoVia.get("sentido").asString()
      var sentido: Sentido = null
      if (caracteristicaSentido == "Doble") sentido = Sentido.dobleVia
      if (caracteristicaSentido == "Una") sentido = Sentido.unaVia
      var tipovia = new TipoVia(nodoVia.get("tipo").asString())
      val via = new Via(interI, interF, nodoVia.get("velmax").asInt(), tipovia, sentido, nodoVia.get("numero").asString(), Some(nodoVia.get("nombre").asString()))
    }
    session.close()
    driver.close()
  }

  def buscarVia(x1: Int, y1: Int, x2: Int, y2: Int): Via = {
    var via: Via = null
    for (x: Int <- 0 to Simulacion.listaVias.size - 1) {
      val viaBuscada = Simulacion.listaVias(x)
      if (viaBuscada.origen.x == x1.toDouble && viaBuscada.origen.y == y1.toDouble && viaBuscada.fin.x == x2.toDouble && viaBuscada.fin.y == y2.toDouble) {
        via = viaBuscada
      }
    }
    via
  }

  def borrarSemaforos {
    println("Borrando semaforos anteriores...")
    val (driver, session) = getSession()
    val script = s"MATCH (s:Semaforo) DELETE s"
    session.run(script)
    session.close()
    driver.close()
    println("Semaforos anteriores borrados!")
  }

  def cargarSemaforosNeo4j(semaforos: ArrayBuffer[Semaforo]) {
    borrarSemaforos
    println("Guardando registros de semaforos...")
    val (driver, session) = getSession()
    semaforos.foreach(s => {
      val script = s"CREATE (s:Semaforo{x:${s.lugar.x},y:${s.lugar.y},tVerdeO:${s.tVerdeOriginal},tAmarilloO:${s.tAmarilloOriginal},tVerdeA:${s.tVerde},tAmarilloA:${s.tAmarillo},estado:'${s.estado}'})"
      session.run(script)
    })
    session.close()
    driver.close()
    println("Registros de semaforos actuales guardados!")
  }

  def borrarComparendos {
    println("Borrando comparendos anteriores...")
    val (driver, session) = getSession()
    val script = s"MATCH (c:Comparendo) DELETE c"
    session.run(script)
    session.close()
    driver.close()
    println("Comparendos anteriores borrados!")
  }

  def borrarVehiculos {
    println("Borrando vehiculos anteriores...")
    val (driver, session) = getSession()
    val script = s"MATCH (v:Vehiculo) DELETE v"
    session.run(script)
    session.close()
    driver.close()
    println("Vehiculos anteriores borrados!")
  }

  def cargarComparendos(comparendos: ArrayBuffer[Comparendo]) {
    borrarComparendos
    val (driver, session) = getSession()
    comparendos.foreach(c => {
      val script = s"CREATE (c:Comparendo{ placa:'${c.vehiculo.placa}', velocidad:${c.velocidad}, velocidadMaxima:${c.velocidadMaxima}})"
      session.run(script)
    })
    session.close()
    driver.close()
  }
  
  def revisarTipoVehiculo(vehiculo:Vehiculo):String = {
    var tipo = ""
    if(vehiculo.isInstanceOf[Bus]){tipo = "Bus"}
    if(vehiculo.isInstanceOf[Camion]){tipo = "Camion"}
    if(vehiculo.isInstanceOf[Carro]){tipo = "Carro"}
    if(vehiculo.isInstanceOf[Moto]){tipo="Moto"}
    if(vehiculo.isInstanceOf[MotoTaxi]){tipo="MotoTaxi"}
    tipo
  }
  
  def cargarVehiculos(vehiculos: ArrayBuffer[Vehiculo]){
    borrarVehiculos
    println("Cargando informacion de vehiculos...")
    val(driver,session) = getSession()
    vehiculos.foreach(v => { 
      var tipo = revisarTipoVehiculo(v)
      //constructor
      val placa = v.placa
      val velmaxMag = v.velMax
      val acelO = v.aceleracionOriginal
      //posicion actual
      val posicionx = v.posicion.x
      val posiciony = v.posicion.y
      //velocidad y aceleracion actual
      val velAmag = v.velActual.magnitud
      //val velAang = v.velActual.direccion.grados // El angulo se puede calcular entre posicion actual y proximaInter
      val acel = v.aceleracion
      //Si ya se ha sacado comparendo en la via actual
      var comparendoEnVia = "no"
      if(v.tieneComparendoEnViaActual){comparendoEnVia = "si"}
      //Nombre de la proxima interseccion
      val nombreProximaInter:String = v.proximaInter.nombre.getOrElse("")
      //Nombre de la ultima interseccion
      val nombreInterFinal = v.viaje.interF.nombre.getOrElse("")    
      //Nombres de la interseccion origen y la interseccion final de la via actual(Para hallarla)
      val nombreInterOrigenVia = v.viaActual.origen.nombre.getOrElse("")
      val nombreInterFinVia = v.viaActual.fin.nombre.getOrElse("")
      val script = s"""
                    CREATE (v:Vehiculo{tipo:'${tipo}',placa:'${placa}',velMaxMag:${velmaxMag},acelO:${acelO},x:${posicionx},
                    y:${posiciony},velA:${velAmag},acelA:${acel},comparendoEnVia:'${comparendoEnVia}',proximaInter:'${nombreProximaInter}',nombreInterF:'${nombreInterFinal}',
                    origenVia:'${nombreInterOrigenVia}',finVia: '${nombreInterFinVia}'})
                    """
      session.run(script)
    })
  session.close()
  driver.close()
  println("Se cargó la información de los vehiculos!")
  }
}




































