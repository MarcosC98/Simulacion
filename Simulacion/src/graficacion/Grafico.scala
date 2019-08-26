package graficacion
import java.awt.event.{KeyEvent, KeyListener}
import java.awt.{BasicStroke, Color}

import javax.swing.JFrame
import mapa.{CamaraFotoDeteccion, Interseccion, Via}
import org.jfree.chart.ChartFactory
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.ChartFrame
import org.jfree.chart.annotations.XYTextAnnotation
import java.util.Random

import ejecucion.Simulacion
import vehiculos.Vehiculo

import scala.collection.mutable.ArrayBuffer

object Grafico {
  val rand = new Random()
  val dataset = new XYSeriesCollection()
  val graficaXY = ChartFactory.createScatterPlot(null, null, null, dataset,
    PlotOrientation.VERTICAL, false, false, false)
  val plantilla = graficaXY.getXYPlot()
  plantilla.setBackgroundPaint(Color.WHITE)
  plantilla.getRangeAxis().setVisible(false)
  plantilla.getDomainAxis().setVisible(false)


  val render = new XYLineAndShapeRenderer()
  render.setAutoPopulateSeriesStroke(false)
  render.setAutoPopulateSeriesPaint(false)
  render.setBaseStroke(new BasicStroke(4))
  render.setBasePaint(Color.decode("#c9c9c9"))
  plantilla.setRenderer(render)

  val cuadroGrafico: ChartFrame = new ChartFrame("Simulacion", graficaXY)
  cuadroGrafico.setVisible(true)
  cuadroGrafico.setSize(1520, 720)
  cuadroGrafico.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  cuadroGrafico.addKeyListener(new KeyListener {


    override def keyPressed(keyEvent: KeyEvent): Unit = {

      val key: Int = keyEvent.getKeyCode

      if (key == KeyEvent.VK_F5) {
        //Simulacion.encender
      }
      else if (key == KeyEvent.VK_F6) {
        Simulacion.parar
      }

    }

    override def keyTyped(e: KeyEvent): Unit = {}

    override def keyReleased(e: KeyEvent): Unit = {}
  })

  def graficaViasNodos(vias: ArrayBuffer[Via], intersecciones: ArrayBuffer[Interseccion],camaras:ArrayBuffer[CamaraFotoDeteccion])= {
    var numAux: Int = 0

    vias.foreach({ x =>
      val via = new XYSeries(numAux)
      via.add(x.origen.x, x.origen.y)
      via.add(x.fin.x, x.fin.y)
      dataset.addSeries(via)
      render.setSeriesShapesVisible(numAux, false)
      numAux += 1
    })
    camaras.foreach(camara => {
      dataset.addSeries(new XYSeries((numAux)))
      val graficoCamara = dataset.getSeries(numAux)
      graficoCamara.add(camara.posicion.x, camara.posicion.y)
      render.setSeriesShape(numAux, camara.figura)
      render.setSeriesPaint(numAux, Color.decode("#0026ff"))
      numAux +=1
    })
    intersecciones.foreach({
      x =>
        val interseccion = new XYTextAnnotation(x.nombre.getOrElse(""), x.x, x.y)
        interseccion.setPaint(Color.decode(x.color))
        plantilla.addAnnotation(interseccion)
    })


  }

  def dibujarVehiculos(vehiculos: ArrayBuffer[Vehiculo]) = {
    vehiculos.foreach(r => {
      dataset.addSeries(new XYSeries(r.placa))
      val vehiculoIndex = dataset.getSeriesIndex(r.placa)
      render.setSeriesShape(vehiculoIndex, r.figura)
      render.setSeriesPaint(vehiculoIndex, Color.decode(r.viaje.interF.color))

    })
  }

  def graficarVehiculos(vehiculos: ArrayBuffer[Vehiculo]) = {
    vehiculos.foreach(j => {
      val vehiculo = dataset.getSeries(j.placa)
      vehiculo.clear()
      vehiculo.add(j.posicion.x, j.posicion.y)
    })

  }
}