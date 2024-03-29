/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applet;

import java.awt.Color;
import org.jfree.chart.ChartMouseEvent;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.event.ChartChangeEvent;
import org.jfree.chart.event.ChartChangeListener;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import parser.Parser;

/**
 *
 * @author Administrador
 */
public class GraficadorFuncion {

    private SistemasDinamicos vista;
    private XYDataset dataGraficoFuncion;
    
    public GraficadorFuncion(SistemasDinamicos sistema) {
        this.vista = sistema;
    }

    public void graficarFuncion(){
        this.vista.setParser(new Parser(vista, this.vista.getTxtFuncion().getText()));
        this.vista.getParser().agregarFuncion(this.vista.getTxtFuncion().getText());
        dataGraficoFuncion = cargarPuntosFuncion();  
        this.vista.getPanelGrafico1().removeAll();
        this.vista.getPanelGrafico1().setEnabled(true);
        this.vista.getJTabbedPane1().removeAll();
        this.vista.getJTabbedPane1().add("f vs x", crearPanelGraficoFuncion());
        this.vista.getPanelGrafico1().add(this.vista.getJTabbedPane1());
        this.vista.getJTabbedPane1().setVisible(true);
        this.vista.getJTabbedPane1().repaint();
        this.vista.getPanelGrafico1().repaint();
        this.vista.repaint(); 
    }
    
    private XYDataset cargarPuntosFuncion() 
    {
        XYSeries xyseries = new XYSeries("Series 1");
        double inicio = Double.parseDouble(this.vista.getTxtXinicial().getText());
        double fin = Double.parseDouble(this.vista.getTxtXfinal().getText());
        double index = inicio;
        
        double a;
        
        while(index <= fin)
        {
            try
            {
                a = (Double) this.vista.getParser().getValor(index);
                xyseries.add(index, a);
                index += 0.01;
            }
            catch(Exception e)
            {
                
            }
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
        return xyseriescollection;
    }
    
    private ChartPanel crearPanelGraficoFuncion() 
    {
        NumberAxis numberaxis = new NumberAxis("X");
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setUpperMargin(0.12D);
        numberaxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        NumberAxis numberaxis1 = new NumberAxis("Y");
        numberaxis1.setAutoRangeIncludesZero(false);
        numberaxis1.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        numberaxis1.setUpperMargin(0.12D);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
        xylineandshaperenderer.setSeriesPaint(0, Color.blue);
        xylineandshaperenderer.setBaseShapesVisible(true);
        xylineandshaperenderer.setBaseItemLabelsVisible(true);
        xylineandshaperenderer.setSeriesLinesVisible(0, true);
        xylineandshaperenderer.setSeriesShapesVisible(0, false);
        xylineandshaperenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        xylineandshaperenderer.setDefaultEntityRadius(6);
        XYPlot xyplot = new XYPlot(dataGraficoFuncion, numberaxis, numberaxis1, xylineandshaperenderer);
        xyplot.setDomainZeroBaselineVisible(true);
        xyplot.setRangeZeroBaselineVisible(true);     
        JFreeChart jfreechart = new JFreeChart("f vs x", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        ChartPanel chartpanel = new ChartPanel(jfreechart, false);
        chartpanel.setVisible(true);
        return chartpanel;
    }
    
}
