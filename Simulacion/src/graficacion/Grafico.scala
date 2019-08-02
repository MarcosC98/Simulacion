package graficacion
import java.awt.{BasicStroke, Color}

import javax.swing.JFrame
import mapa.{Interseccion, Via}
import org.jfree.chart.ChartFrame
import org.jfree.chart.ChartFactory
import org.jfree.chart.JFreeChart
import org.jfree.chart.plot.PlotOrientation
import org.jfree.chart.plot.XYPlot
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer
import org.jfree.data.xy.XYSeries
import org.jfree.data.xy.XYSeriesCollection
import org.jfree.chart.ChartFrame
import org.jfree.chart.axis.ValueAxis
import org.jfree.chart.annotations.XYTextAnnotation

import scala.collection.mutable.ArrayBuffer
object Grafico {
  val dataset = new XYSeriesCollection()
  val graficaXY= ChartFactory.createScatterPlot(null, null,null,dataset,
    PlotOrientation.VERTICAL, false, false, false)
  val plantilla = graficaXY.getXYPlot()
  plantilla.setBackgroundPaint(Color.WHITE)
  plantilla.getRangeAxis().setVisible(true)
  plantilla.getDomainAxis().setVisible(true)

  val render = new XYLineAndShapeRenderer()
  render.setAutoPopulateSeriesStroke(false)
  render.setAutoPopulateSeriesPaint(false)
  render.setBaseStroke(new BasicStroke(4))
  render.setBasePaint(Color.decode("#c9c9c9"))
  plantilla.setRenderer(render)

  val ventana: ChartFrame = new ChartFrame("Simulacion", graficaXY)
  ventana.setVisible(true)
  ventana.setSize(1080, 720)
  ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)

  def graficarVias(vias:ArrayBuffer[Via], intersecciones:ArrayBuffer[Interseccion]) ={
    var numAux:Int=0
    vias.foreach({x  => val via = new XYSeries(numAux)
      via.add(x.origen.x,x.origen.y)
      via.add(x.fin.x, x.fin.y)
      dataset.addSeries(via)
      render.setSeriesShapesVisible(numAux, false)
      render.setSeriesPaint(numAux,Color.lightGray)
      numAux += 1
    })
    intersecciones.foreach({
      x => val interseccion = new XYTextAnnotation(x.nombre,x.x,x.y+0.1)
        plantilla.addAnnotation(interseccion)
    })
  }
}
