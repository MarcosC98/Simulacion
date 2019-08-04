package ejecucion

case class Vehiculos (total: Int , carros: Int, motos: Int, buses: Int, camiones: Int, motoTaxis:Int)
case class VehiculosEnInter (promedioOrigen: Double, promedioDestino: Double, sinOrigen: Double, sinDestino: Double)
case class MallaVial (vias: Int, intersecciones:Int, viasUnSentido: Int, viasDobleSentido: Int, velocidadMinima: Double, velocidadMaxima: Double, longitudPromedio: Double, vehiculosEnInterseccion: VehiculosEnInter)
case class Tiempo (simulacion: Double, realidad: Double)
case class VelocidadResultados (minima: Double, maxima: Double, prodemio: Double)
case class Distancia (minima: Double, maxima: Double, prodemio: Double)
case class Resultados (veliculos: Vehiculos, mallaVial: MallaVial,tiempos: Tiempo, velocidades: VelocidadResultados, distancias: Distancia)
case class ResultadosSimulacion(resultados: Resultados) 