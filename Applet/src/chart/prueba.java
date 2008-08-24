/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package chart;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
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
public class prueba {

    public prueba() {
    }

//    public static void main(String[] args) {
//        // create a dataset...
//        DefaultPieDataset data = new DefaultPieDataset();
//        data.setValue("Category 1", 43.2);
//        data.setValue("Category 2", 27.9);
//        data.setValue("Category 3", 79.5);
//        // create a chart...
//        JFreeChart chart = ChartFactory.createPieChart(
//        "Sample Pie Chart",
//        data,
//        true, // legend?
//        true, // tooltips?
//        false // URLs?
//        );
//        // create and display a frame...
//        ChartFrame frame = new ChartFrame("First", chart);
//        frame.setVisible(true);
//        frame.pack();
//    }
    
    public static void main(String[] args){
        JFrame frm_grafica = new JFrame("Grafica - B-Rabbit");        
        frm_grafica.setVisible(true);
        frm_grafica.setSize(250, 250);
        XYSeries serie1 = new XYSeries("Diagrama de Dispersion");
        XYSeries serie2 = new XYSeries("Regresion Lineal");
        for(int i = 0; i < 10; i++){
            serie1.add(i, i);
        }
        for(double i = 0; i <= 10; i+=0.1){
            serie2.add(i*2, i*2);
        }
        XYSeriesCollection datasetCollection = new XYSeriesCollection();
        datasetCollection.addSeries(serie1);
        XYSeriesCollection datasetCollection2 = new  XYSeriesCollection();
        datasetCollection.addSeries(serie2);
        XYDataset dataset = datasetCollection;   
        XYDataset dataset2 = datasetCollection2;
        JFreeChart chart = ChartFactory.createScatterPlot(
                "Diagrama de Dispersion", 
                "X", 
                "Y", 
                dataset, 
                PlotOrientation.VERTICAL, 
                false, 
                true, 
                false
        );
        XYPlot plot = (XYPlot)chart.getXYPlot();
        plot.setDataset(1, dataset2);
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesShapesVisible(0, true);
        renderer.setSeriesShapesFilled(0, true);
        NumberAxis rangeAxis = (NumberAxis)plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createStandardTickUnits());
        ChartPanel chartPanel = new ChartPanel(chart);
        frm_grafica.add(chartPanel);
        chartPanel.setVisible(true);

    }
}
