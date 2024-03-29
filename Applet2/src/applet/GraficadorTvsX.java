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

public class GraficadorTvsX 
{
    
    private SistemasDinamicos vista;
    private double[] raices;
    private XYDataset repulsoras;
    
    public GraficadorTvsX(SistemasDinamicos sistema) {
        this.vista = sistema;
    }
    
    private boolean entra(double a)
    {
        double fin = raices[raices.length-1];
        double inicio = raices[0];
        return ( (a<(fin +2)) && (a>(inicio -2))  )?true:false;
    }
    
    private XYSeries getSerieEuler(double seed)
    {
        Euler e = new Euler(Double.parseDouble(this.vista.getTxtHTiempo().getText()), 0, seed, 
                            this.vista.getTxtFuncion().getText(), Double.parseDouble(this.vista.getTxtTiempoMin().getText()),
                            Double.parseDouble(this.vista.getTxtTiempoMax().getText()), this.vista.getParser());
        int index = 0;
        XYSeries s = new XYSeries("serie ");
        List<Double> eu = e.getPoints();
        
       /* for(int i = 0; i<eu.size();i++)
            s.add(i,eu.get(i));*/
        
        for(double i =0 ; i < Double.parseDouble(this.vista.getTxtTiempoMax().getText());i+=Double.parseDouble(this.vista.getTxtHTiempo().getText()))
        {
            //if( (eu.get(index) < 4) && (eu.get(index) >-4))
            if(entra(eu.get(index)))
                s.add(i,eu.get(index++));
            else index++;
        }
            
        /*HashMap<Double,Double> points = e.getPoints();
        
        
        Set<Double> a =  points.keySet();
        
        for(Iterator<Double> i = a.iterator(); i.hasNext();)
        {
            Double key = i.next();
            s.add(key,points.get(key));
        }*/
        
    
        return s;
    }
    
    private XYSeries getSerieEulerNegativa(double seed)
    {
        Euler e = new Euler(Double.parseDouble(this.vista.getTxtHTiempo().getText()), 0, seed, 
                            this.vista.getTxtFuncion().getText(), Double.parseDouble(this.vista.getTxtTiempoMin().getText()),
                            Double.parseDouble(this.vista.getTxtTiempoMax().getText()), this.vista.getParser());
        int index = 0;
        XYSeries s = new XYSeries("serie ");
        List<Double> eu = e.getNegativePoints();
        
       /* for(int i = 0; i<eu.size();i++)
            s.add(i,eu.get(i));*/
        
       for(double i =0 ; i > Double.parseDouble(this.vista.getTxtTiempoMin().getText());i-=Double.parseDouble(this.vista.getTxtHTiempo().getText()))
       {
           //if( (eu.get(index) < 4) && (eu.get(index) > -4))
           if(entra(eu.get(index)))
                s.add(i,eu.get(index++));
           else index++;
       }
        
       return s;
    }
    
    private XYSeriesCollection getAllEulerSeries()
    {
        XYSeries xyseries = new XYSeries("Series 1");
        XYSeriesCollection ret = new XYSeriesCollection(xyseries);
        if(raices.length != 0){
            //double seed = (Math.random()*raices[0]) + (raices[0] -1);
            //double seed = raices[0]-Double.parseDouble(vista.getTxtH().getText());  //con esto grafica, pero no hace casi nada abajo
            double seed = raices[0] -1; //si pones esto (es lo mismo que hacemos con la ultima raiz) no anda nada
            //ret.addSeries(this.getSerieEuler(this.vista.getRaices()[0]-1));
            //ret.addSeries(this.getSerieEuler(this.vista.getRaices()[this.vista.getRaices().length-1]-1));
            ret.addSeries(this.getSerieEuler(seed));
            ret.addSeries(this.getSerieEulerNegativa(seed));
            
            /* si se quiere una sola linea, borrar todo lo de abajo */
            
             seed += 50*Double.parseDouble(this.vista.getTxtH().getText());
                 ret.addSeries(this.getSerieEuler(seed));
                 ret.addSeries(this.getSerieEulerNegativa(seed));
                 
            /* si se quiere una sola linea, borrar todo lo de arriba */
            
            System.out.println("Semilla : " + seed);
            for(int i = 0; i < this.vista.getRaices().length-1;i++)
            {  
                //seed = (Math.random()*raices[i+1]) + (raices[i]);
                //seed = (Math.random()*raices[i]) + (raices[i+1]);
                
//                seed = (Math.random()*raices[i+1]) + raices[i];
//                //seed+= 10*Double.parseDouble(this.vista.getTxtH().getText());
//                if( (seed > raices[i]) && (seed < raices[i+1]))
//                {
//                    //seed = raices[i];
//                    //seed = (raices[i+1] + raices[i])/2;
//                    ret.addSeries(this.getSerieEuler(seed));
//                    ret.addSeries(this.getSerieEulerNegativa(seed));
//                    System.out.println("Semilla : " + seed + "   Raiz" + raices[i] + "--> Agregada");
//                }
               // else System.out.println("Semilla : " + seed + "   Raiz" + raices[i] + "--> N0 Agregada");
                
                 seed = (raices[i+1] + raices[i])/2;
                 ret.addSeries(this.getSerieEuler(seed));
                 ret.addSeries(this.getSerieEulerNegativa(seed));
                 
                 /* si se quiere una sola linea, borrar todo lo de abajo */
                  seed += 50*Double.parseDouble(this.vista.getTxtH().getText());
                 ret.addSeries(this.getSerieEuler(seed));
                 ret.addSeries(this.getSerieEulerNegativa(seed));
                 
                 seed -= 100*Double.parseDouble(this.vista.getTxtH().getText());
                 
                 ret.addSeries(this.getSerieEuler(seed));
                 ret.addSeries(this.getSerieEulerNegativa(seed));
                 
                 /* si se quiere una sola linea, borrar todo lo de arriba */
            }
           // seed =  (Math.random()*(raices[raices.length-1]+1)) + raices[raices.length-1];
            seed = raices[raices.length-1]+1;
            ret.addSeries(this.getSerieEuler(seed));
            ret.addSeries(this.getSerieEulerNegativa(seed));
            
             /* si se quiere una sola linea, borrar todo lo de abajo */
            
             seed += 50*Double.parseDouble(this.vista.getTxtH().getText());
                 ret.addSeries(this.getSerieEuler(seed));
                 ret.addSeries(this.getSerieEulerNegativa(seed));
                 
            /* si se quiere una sola linea, borrar todo lo de arriba */
            System.out.println("Semilla : " + seed+ "   Raiz" +raices[raices.length-1]);
        }else{
            double seed = 0;
            ret.addSeries(this.getSerieEuler(seed));
        }
        return ret;
    }    
    
    public void graficarTvsX(){
        raices = this.vista.getRaices();
        repulsoras = this.cargarRepulsoras(); //Cargo XYSeries para las raices
        //XYDataset euler1 = this.cargarEuler(); //Cargo a euler en otra serie
        XYDataset euler1 = this.getAllEulerSeries(); //Cargo a euler en otra serie
        XYDataset atractoras = this.cargarAtractoras();
        this.vista.getJTabbedFvsT().removeAll();


        JFreeChart jfreechart = ChartFactory.createXYLineChart("F vs T", "t", "x", repulsoras, PlotOrientation.VERTICAL, true, true, false);
        jfreechart.removeLegend();
        XYPlot xyplot = (XYPlot) jfreechart.getPlot();
        
        NumberAxis numberaxisy = new NumberAxis("x");
        numberaxisy.setAutoRangeIncludesZero(false);
        if(raices.length == 0){
            numberaxisy.setRange(-1d,1d);
        }else{
            numberaxisy.setRange(raices[0] - 1d, raices[raices.length - 1] + 1d);
        }
        
        xyplot.setDomainZeroBaselineVisible(true);
        xyplot.setRangeZeroBaselineVisible(true);
        
        xyplot.setDataset(1, euler1);
        xyplot.setDataset(2, atractoras);
        xyplot.getDomainAxis().setLowerMargin(0.0D);
        xyplot.getDomainAxis().setUpperMargin(0.0D);
        
        xyplot.getRenderer(0).setPaint(Color.red);
        /*XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer();
        xylineandshaperenderer.setShapesVisible(false);
        xylineandshaperenderer.setPaint(Color.blue);
        xylineandshaperenderer.setBaseToolTipGenerator(new StandardXYToolTipGenerator());*/
        
        XYLineAndShapeRenderer xylineandshaperenderer2 = new XYLineAndShapeRenderer();
        xylineandshaperenderer2.setShapesVisible(false);
        xylineandshaperenderer2.setPaint(Color.green);
        xylineandshaperenderer2.setBaseToolTipGenerator(new StandardXYToolTipGenerator());
        xyplot.setRenderer(2,xylineandshaperenderer2);
        
        XYLineAndShapeRenderer xylineandshaperenderer3 = new XYLineAndShapeRenderer();
        xylineandshaperenderer3.setShapesVisible(false);
        xylineandshaperenderer3.setPaint(Color.blue);
        xylineandshaperenderer3.setBaseToolTipGenerator(new StandardXYToolTipGenerator());

        xyplot.setRenderer(1,xylineandshaperenderer3);
        
        
        ChartPanel chartPanel = new ChartPanel(jfreechart, false);
        chartPanel.setVisible(true);
        
        
        this.vista.getJTabbedFvsT().add("f vs t", chartPanel);

        this.vista.getJTabbedFvsT().setVisible(true);

        this.vista.getJTabbedFvsT().repaint();
        this.vista.getPanelFvsT().repaint();
        this.vista.repaint();
    }
    
    private XYDataset cargarAtractoras() {
        XYSeries xyseries = new XYSeries("Series 1");
        double inicio = Double.parseDouble(this.vista.getTxtTiempoMin().getText())-1;
        double fin = Double.parseDouble(this.vista.getTxtTiempoMax().getText())+1;
        
        
        double h = Double.valueOf(this.vista.getTxtH().getText());

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        
        for (int i = 0; i < this.vista.getRaices().length; i++) 
        {
            double raiz = raices[i];
            double izq = (Double) this.vista.getParser().getValor(raiz - h);
            double der = (Double) this.vista.getParser().getValor(raiz + h);
            
            if( (der < 0) && (izq > 0))
            {
                xyseries = new XYSeries("Series " + (i + 1));
           // for (double j = -10; j < 10; j += 0.1) {
                for(double j = inicio; j < fin;j+=0.1){
                    xyseries.add(j, this.vista.getRaices()[i]);
                }
                xyseriescollection.addSeries(xyseries);
            }
        }
        //ACA FIJATE Q T CARGA UNA SERIE POR CADA RAIZ
        //ENTONCES T PONE UNA DE CADA COLOR

        return xyseriescollection;
    }
    
    private XYDataset cargarRepulsoras() {
        XYSeries xyseries = new XYSeries("Series 1");
        double inicio = Double.parseDouble(this.vista.getTxtTiempoMin().getText())-1;
        double fin = Double.parseDouble(this.vista.getTxtTiempoMax().getText())+1;
        
        
        double h = Double.valueOf(this.vista.getTxtH().getText());

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        
        for (int i = 0; i < this.vista.getRaices().length; i++) 
        {
            double raiz = raices[i];
            double izq = (Double) this.vista.getParser().getValor(raiz - h);
            double der = (Double) this.vista.getParser().getValor(raiz + h);
            
            if( ((der > 0) && (izq < 0)) || (((der < 0) && (izq < 0)) || ((der > 0) && (izq > 0))))
            {
                xyseries = new XYSeries("Series " + (i + 1));
           // for (double j = -10; j < 10; j += 0.1) {
                for(double j = inicio; j < fin;j+=0.1){
                    xyseries.add(j, this.vista.getRaices()[i]);
                }
                xyseriescollection.addSeries(xyseries);
            }
        }
        //ACA FIJATE Q T CARGA UNA SERIE POR CADA RAIZ
        //ENTONCES T PONE UNA DE CADA COLOR

        return xyseriescollection;
    }
}
