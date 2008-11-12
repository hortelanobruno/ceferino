/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applet;

import java.awt.Color;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
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
        List<Double> eu = e.getPoints();
        
        for(int i = 0; i<eu.size();i++)
        {
            s.add(i,eu.get(i));
        }
        
        return s;
    }
    
    private XYSeriesCollection getAllEulerSeries()
    {
        XYSeries xyseries = new XYSeries("Series 1");
        XYSeriesCollection ret = new XYSeriesCollection(xyseries);
        if(raices.length != 0){
            //double seed = (Math.random()*raices[0]) + (raices[0] -1);
            double seed = raices[0]-Double.parseDouble(vista.getTxtH().getText());
            //ret.addSeries(this.getSerieEuler(this.vista.getRaices()[0]-1));
            //zret.addSeries(this.getSerieEuler(this.vista.getRaices()[this.vista.getRaices().length-1]-1));
            ret.addSeries(this.getSerieEuler(seed));
            System.out.println("Semilla : " + seed);
            for(int i = 0; i < this.vista.getRaices().length-1;i++)
            {  
                //seed = Math.ceil(Math.random()*raices[i+1]) + (raices[i]);
                seed = raices[i];
                ret.addSeries(this.getSerieEuler(seed));
                System.out.println("Semilla : " + seed);
            }
            //seed =  (Math.random()*(raices[raices.length-1]+1)) + raices[raices.length-1];
            seed = raices[raices.length-1]+Double.parseDouble(vista.getTxtH().getText());
            ret.addSeries(this.getSerieEuler(seed));
            System.out.println("Semilla : " + seed);
        }else{
            double seed = 0;
            ret.addSeries(this.getSerieEuler(seed));
        }
        return ret;
    }
    
    
    public void graficarTvsX(){
        raices = this.vista.getRaices();
        dataGraficoFvsT = this.cargarPuntosFvsT(); //Cargo XYSeries para las raices
        //XYDataset euler1 = this.cargarEuler(); //Cargo a euler en otra serie
        XYDataset euler1 = this.getAllEulerSeries(); //Cargo a euler en otra serie
        this.vista.getJTabbedFvsT().removeAll();


        JFreeChart jfreechart = ChartFactory.createXYLineChart("F vs T", "t", "x", dataGraficoFvsT, PlotOrientation.VERTICAL, true, true, false);
        jfreechart.removeLegend();
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        
        NumberAxis numberaxisy = new NumberAxis("x");
        numberaxisy.setAutoRangeIncludesZero(false);
        if(raices.length == 0){
            numberaxisy.setRange(-1d,1d);
        }else{
            numberaxisy.setRange(raices[0] - 1d, raices[raices.length - 1] + 1d);
        }
        
        xyplot.setRangeAxis(numberaxisy);
        
        xyplot.setDataset(1, euler1);
        xyplot.getDomainAxis().setLowerMargin(0.0D);
        xyplot.getDomainAxis().setUpperMargin(0.0D);

        xyplot.getRenderer(0).setPaint(Color.black);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
        xylineandshaperenderer.setShapesVisible(false);
        xylineandshaperenderer.setPaint(Color.red);
        xylineandshaperenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

        xyplot.setRenderer(1,xylineandshaperenderer);
        
        
        ChartPanel chartPanel = new ChartPanel(jfreechart, false);
        chartPanel.setVisible(true);
        
        
        this.vista.getJTabbedFvsT().add("f vs t", chartPanel);

        this.vista.getJTabbedFvsT().setVisible(true);

        this.vista.getJTabbedFvsT().repaint();
        this.vista.getPanelFvsT().repaint();
        this.vista.repaint();
    }
    
    private XYDataset cargarPuntosFvsT() {
        XYSeries xyseries = new XYSeries("Series 1");
        //double inicio = Double.parseDouble(this.vista.getTxtXinicial().getText());
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
}
