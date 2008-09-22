// Decompiled by DJ v3.9.9.91 Copyright 2005 Atanas Neshkov  Date: 22/09/2008 10:52:04
// Home Page : http://members.fortunecity.com/neshkov/dj.html  - Check often for new version!
// Decompiler options: packimports(3) 

package metodo;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

// Referenced classes of package demo:
//            SampleXYDataset2

public class ScatterPlotDemo1 extends ApplicationFrame
{

    public ScatterPlotDemo1(String s)
    {
        super(s);
        JPanel jpanel = createDemoPanel();
        jpanel.setPreferredSize(new Dimension(500, 270));
        setContentPane(jpanel);
    }

    private static JFreeChart createChart(XYDataset xydataset)
    {
        JFreeChart jfreechart = ChartFactory.createScatterPlot("Scatter Plot Demo 1", "X", "Y", xydataset, PlotOrientation.VERTICAL, true, false, false);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.setNoDataMessage("NO DATA");
        xyplot.setDomainZeroBaselineVisible(false);
        xyplot.setRangeZeroBaselineVisible(false);
        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
        xylineandshaperenderer.setSeriesOutlinePaint(0, Color.black);
        xylineandshaperenderer.setUseOutlinePaint(true);
        NumberAxis numberaxis = (NumberAxis)xyplot.getDomainAxis();
        numberaxis.setAutoRangeIncludesZero(false);
        numberaxis.setTickMarkInsideLength(2.0F);
        numberaxis.setTickMarkOutsideLength(0.0F);
        NumberAxis numberaxis1 = (NumberAxis)xyplot.getRangeAxis();
        numberaxis1.setTickMarkInsideLength(2.0F);
        numberaxis1.setTickMarkOutsideLength(0.0F);
        return jfreechart;
    }

    
    public static JPanel createDemoPanel()
    {
        JFreeChart jfreechart = createChart(new SampleXYDataset2());
        ChartPanel chartpanel = new ChartPanel(jfreechart);
        chartpanel.setVerticalAxisTrace(true);
        chartpanel.setHorizontalAxisTrace(true);
        chartpanel.setPopupMenu(null);
        chartpanel.setDomainZoomable(true);
        chartpanel.setRangeZoomable(true);
        return chartpanel;
    }

    public static void main(String args[])
    {
        ScatterPlotDemo1 scatterplotdemo1 = new ScatterPlotDemo1("JFreeChart: ScatterPlotDemo1.java");
        scatterplotdemo1.pack();
        RefineryUtilities.centerFrameOnScreen(scatterplotdemo1);
        scatterplotdemo1.setVisible(true);
    }
}