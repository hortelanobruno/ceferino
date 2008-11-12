/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applet;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import utils.Flecha;
import utils.Punto;

/**
 *
 * @author Administrador
 */
public class GraficadorFases {
    
    private SistemasDinamicos vista;
    private XYDataset dataGraficoFases;
    private double[] raices;
    
    public GraficadorFases(SistemasDinamicos sistema) {
        this.vista = sistema;
    }

    public void graficarFases(){
        raices = this.vista.getRaices();
        
        List<Flecha> flechas = this.getFlechasFase();
        dataGraficoFases = crearPuntosRaices();
        this.vista.getJTabbedFases().removeAll();

        NumberAxis numberaxisx = new NumberAxis("");
        numberaxisx.setAutoRangeIncludesZero(false);

        numberaxisx.setRange(-5d, 5d);
        
        NumberAxis numberaxisy = new NumberAxis("x");
        numberaxisy.setAutoRangeIncludesZero(false);
        if(raices.length == 0){
            numberaxisy.setRange(-1d,1d);
        }else{
            numberaxisy.setRange(raices[0] - 1d, raices[raices.length - 1] + 1d);
        }
        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
        renderer.setSeriesPaint(0, Color.BLACK);
        XYPlot plot = new XYPlot(dataGraficoFases, numberaxisy, numberaxisx, renderer);
        plot.setRangeZeroBaselineVisible(true);

        VectorSeries vSeries = getVectorFlechasRojas(flechas);

        VectorSeriesCollection vsCol = new VectorSeriesCollection();
        vsCol.addSeries(vSeries);
        plot.setDataset(1, vsCol);

        VectorRenderer vRenderer = new VectorRenderer();
        vRenderer.setSeriesPaint(0, Color.RED);
        plot.setRenderer(1, vRenderer);

        VectorSeries vSeries1 = getVectorFlechasVerdes(flechas);

        VectorSeriesCollection vsCol1 = new VectorSeriesCollection();
        vsCol1.addSeries(vSeries1);
        plot.setDataset(2, vsCol1);

        VectorRenderer vRenderer1 = new VectorRenderer();
        vRenderer1.setSeriesPaint(0, Color.GREEN);
        plot.setRenderer(2, vRenderer1);

        JFreeChart jfreeChart = new JFreeChart("Fases", JFreeChart.DEFAULT_TITLE_FONT, plot, true);

        ChartPanel chartPanel = new ChartPanel(jfreeChart, false);
        chartPanel.setVisible(true);

        this.vista.getJTabbedFases().add("Fases", chartPanel);

        this.vista.getJTabbedFases().setVisible(true);

        this.vista.getJTabbedFases().repaint();
        this.vista.getPanelGraficoFases().repaint();
        this.vista.repaint();
    }
    
    private List<Flecha> getFlechasFase() {
        List<Flecha> ret = new ArrayList<Flecha>();
        double h = Double.parseDouble(this.vista.getTxtH().getText());

        for (int i = 0; i < raices.length; i++) {
            double raiz = raices[i];
            Flecha fDer = null;
            Flecha fIzq = null;

            try {
                double izq = (Double) this.vista.getParser().getValor(raiz - h);
                fIzq = new Flecha();

                if (izq < 0) {
                    fIzq.setDireccion('i');
                } else {
                    fIzq.setDireccion('d');
                }
            } catch (Exception e) {
            }

            try {
                double der = (Double) this.vista.getParser().getValor(raiz + h);
                fDer = new Flecha();

                if (der < 0) {
                    fDer.setDireccion('i');
                } else {
                    fDer.setDireccion('d');
                }
            } catch (Exception e) {
            }

            if ((fDer != null) && (fIzq != null)) {
                if ((fDer.getDireccion() == 'i') && (fIzq.getDireccion() == 'd')) {
                    fDer.setColor('v');
                    fIzq.setColor('v');
                    fDer.setPunto(new Punto(raiz + 1, 0));
                    fIzq.setPunto(new Punto(raiz - 1, 0));
                } else if ((fDer.getDireccion() == 'd') && (fIzq.getDireccion() == 'i')) {
                    fDer.setColor('r');
                    fIzq.setColor('r');
                    fDer.setPunto(new Punto(raiz, 0));
                    fIzq.setPunto(new Punto(raiz, 0));
                } else {
                    fDer.setColor('r');
                    fIzq.setColor('r');

                    if ((fDer.getDireccion() == 'd') && (fIzq.getDireccion() == 'd')) {
                        fDer.setPunto(new Punto(raiz, 0));
                        fIzq.setPunto(new Punto(raiz - 1, 0));
                    } else {
                        fDer.setPunto(new Punto(raiz + 1, 0));
                        fIzq.setPunto(new Punto(raiz, 0));
                    }
                }
                ret.add(fIzq);
                ret.add(fDer);
            } else {
                System.out.println("No se puede hacer una flecha aca.");
            }
        }
        if(ret.isEmpty()){
            Flecha flecha = new Flecha();
            flecha.setColor('r');
            flecha.setPunto(new Punto(0,0));
            double valor = (Double) this.vista.getParser().getValor(0);
            if(valor>0){
                flecha.setDireccion('d');
            }else{
                flecha.setDireccion('i');
            }
            ret.add(flecha);
        }
        return ret;
    }
    
    private XYDataset crearPuntosRaices() {
        XYSeries xyseries = new XYSeries("Raices");

        for (int i = 0; i < this.raices.length; i++) {
            xyseries.add((Double) raices[i], (Double) 0d);
        }

        XYSeriesCollection collection = new XYSeriesCollection(xyseries);

        return collection;
    }

    private VectorSeries getVectorFlechasRojas(List<Flecha> flechas) {
        VectorSeries vSeries = new VectorSeries("Atractoras");

        for (int i = 0; i < flechas.size(); i++) {
            Flecha flecha = flechas.get(i);

            if (flecha.getColor() == 'r') {
                if (flecha.getDireccion() == 'i') {
                    vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), -1, 0);
                } else {
                    vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), 1, 0);
                }
            }
        }

        return vSeries;
    }

    private VectorSeries getVectorFlechasVerdes(List<Flecha> flechas) {
        VectorSeries vSeries = new VectorSeries("Repulsoras");

        for (int i = 0; i < flechas.size(); i++) {
            Flecha flecha = flechas.get(i);

            if (flecha.getColor() == 'v') {
                if (flecha.getDireccion() == 'i') {
                    vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), -1, 0);
                } else {
                    vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), 1, 0);
                }
            }
        }

        return vSeries;
    }
    
}
