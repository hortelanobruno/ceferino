/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author Administrador
 */
public class GraficadorTvsX {
    
    private SistemasDinamicos vista;
    private double[] raices;
    private XYDataset dataGraficoFvsT;
    
    public GraficadorTvsX(SistemasDinamicos sistema) {
        this.vista = sistema;
    }
    
    private XYSeries getSerieEuler(double seed)
    {
        Euler e = new Euler(Double.parseDouble(this.vista.getTxtHTiempo().getText()), Double.parseDouble(this.vista.getTxtX0().getText()),
                            seed, this.vista.getTxtFuncion().getText(), Double.parseDouble(this.vista.getTxtTiempoMin().getText()),
                            Double.parseDouble(this.vista.getTxtTiempoMax().getText()), this.vista.getParser());
        XYSeries s = new XYSeries("serie ");
        double[] eu = e.getPoints();
        
        for(int i = 0; i<eu.length;i++)
        {
            s.add(i,eu[i]);
        }
        
        return s;
    }
    
    private XYSeriesCollection getAllEulerSeries()
    {
        XYSeries xyseries = new XYSeries("Series 1");
        XYSeriesCollection ret = new XYSeriesCollection(xyseries);
        double seed = (Math.random()*this.vista.getRaices()[0]) + (this.vista.getRaices()[0] -1);
        System.out.println("seed: " + seed);
        //ret.addSeries(this.getSerieEuler(this.vista.getRaices()[0]-1));
       // ret.addSeries(this.getSerieEuler(this.vista.getRaices()[this.vista.getRaices().length-1]-1));
        ret.addSeries(this.getSerieEuler(seed));
        for(int i = 0; i < this.vista.getRaices().length-1;i++)
        {  
            seed = Math.ceil(Math.random()*this.vista.getRaices()[i+1]) + (this.vista.getRaices()[i]);
            System.out.println("seed: " + seed);
            ret.addSeries(this.getSerieEuler(seed));
        }
        
         seed =  (Math.random()*this.vista.getRaices()[this.vista.getRaices().length-1]) + this.vista.getRaices()[this.vista.getRaices().length-1];
         System.out.println("seed: " + seed);
         ret.addSeries(this.getSerieEuler(seed));
       // System.out.println("Ultima raiz: " + this.vista.getRaices()[this.vista.getRaices().length-1]);
        
        return ret;
    }
    
    private XYDataset cargarPuntosSeriesEuler() 
    {
        return this.getAllEulerSeries();
    }
    
    public void graficarTvsX(){
        dataGraficoFvsT = this.cargarPuntosFvsT(); //Cargo XYSeries para las raices
        //XYDataset euler1 = this.cargarEuler(); //Cargo a euler en otra serie
        XYDataset euler1 = this.cargarPuntosSeriesEuler(); //Cargo a euler en otra serie
        this.vista.getJTabbedFvsT().removeAll();


        JFreeChart jfreechart = ChartFactory.createXYLineChart("Line Chart Demo 4", "X", "Y", dataGraficoFvsT, PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        xyplot.setDataset(1, euler1);
        xyplot.getDomainAxis().setLowerMargin(0.0D);
        xyplot.getDomainAxis().setUpperMargin(0.0D);

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer) xyplot.getRenderer();
        //xylineandshaperenderer.setLegendLine(new java.awt.geom.Rectangle2D.Double(-4D, -3D, 8D, 6D));

        ChartPanel chartPanel = new ChartPanel(jfreechart, false);
        chartPanel.setVisible(true);
        this.vista.getJTabbedFvsT().add("Fases", chartPanel);

        this.vista.getJTabbedFvsT().setVisible(true);

        this.vista.getJTabbedFvsT().repaint();
        this.vista.getPanelFvsT().repaint();
        this.vista.repaint();
    }
    
    private XYDataset cargarPuntosFvsT() {
        XYSeries xyseries = new XYSeries("Series 1");
       // double inicio = Double.parseDouble(this.vista.getTxtXinicial().getText());
        //double fin = Double.parseDouble(this.vista.getTxtXfinal().getText());

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        for (int i = 0; i < this.vista.getRaices().length; i++) {
            xyseries = new XYSeries("Series " + (i + 1));
            for (double j = -10; j < 10; j += 0.1) {
                xyseries.add(j, this.vista.getRaices()[i]);
            }
            xyseriescollection.addSeries(xyseries);
        }
        //ACA FIJATE Q T CARGA UNA SERIE POR CADA RAIZ
        //ENTONCES T PONE UNA DE CADA COLOR

        return xyseriescollection;
    }
    
    private XYDataset cargarEuler() {

        Euler e = new Euler(Double.parseDouble(this.vista.getTxtHTiempo().getText()),
        Double.parseDouble(this.vista.getTxtX0().getText()),
        Double.parseDouble(this.vista.getTxtY0().getText()),
        this.vista.getTxtFuncion().getText(), Double.parseDouble(this.vista.getTxtTiempoMin().getText()),
        Double.parseDouble(this.vista.getTxtTiempoMax().getText()), this.vista.getParser());

        XYSeries xyseries = new XYSeries("Euler");
        double[] eu = e.getPoints();
        for (int i = 0; i < eu.length; i++) {

            xyseries.add(i, eu[i]);//Esto nose si es asi o al reves
        }


        XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
        return xyseriescollection;
    }

}
