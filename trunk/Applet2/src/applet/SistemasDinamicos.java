/*
 * SistemasDinamicos.java
 *
 * Created on 17 de octubre de 2008, 20:36
 */

package applet;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.StandardXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.VectorRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.VectorSeries;
import org.jfree.data.xy.VectorSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import parser.Parser;
import utils.Flecha;
import utils.Punto;

/**
 *
 * @author  Administrador
 */
public class SistemasDinamicos extends javax.swing.JApplet 
{
    
    private static final long serialVersionUID = 327575554503844280L;
    private Parser parser;
    private XYDataset dataGraficoFuncion;
    private XYDataset dataGraficoFases;
    private XYDataset dataGraficoFvsT;
    private List<Double> raices;
    
    /** Initializes the applet SistemasDinamicos */
    @Override
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    initComponents();
                    initComponents2();
                    setLookAndFeel();
                    spinnerDecimales.setValue(3);
                    txtH.setText("0.01");
                    txtXfinal.setText("5");
                    txtXinicial.setText("-5");
                    getTxtFuncion().setText("x^2");
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /** This method is called from within the init() method to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelCentral = new javax.swing.JPanel();
        panelGraficoFases = new javax.swing.JPanel();
        jTabbedFases = new javax.swing.JTabbedPane();
        panelFvsT = new javax.swing.JPanel();
        jTabbedFvsT = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtFuncion = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtH = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtX0 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtY0 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtTiempo = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtXinicial = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtXfinal = new javax.swing.JTextField();
        buttonGraficar = new javax.swing.JButton();
        spinnerDecimales = new javax.swing.JSpinner();
        panelGrafico1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();

        panelGraficoFases.setBorder(javax.swing.BorderFactory.createTitledBorder("Grafico 2"));
        panelGraficoFases.setPreferredSize(new java.awt.Dimension(150, 150));
        panelGraficoFases.setLayout(new java.awt.GridLayout(0, 1));
        panelGraficoFases.add(jTabbedFases);

        panelFvsT.setBorder(javax.swing.BorderFactory.createTitledBorder("Grafico 3"));
        panelFvsT.setPreferredSize(new java.awt.Dimension(150, 150));
        panelFvsT.setLayout(new java.awt.GridLayout(0, 1));
        panelFvsT.add(jTabbedFvsT);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Panel Data"));

        jLabel1.setText("dx/dt=");

        txtFuncion.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFuncionCaretUpdate(evt);
            }
        });
        txtFuncion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFuncionKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFuncionKeyTyped(evt);
            }
        });

        jLabel2.setText("h= ");

        jLabel3.setText("X0= ");

        jLabel4.setText("Y0= ");

        jLabel5.setText("Tiempo= ");

        jLabel8.setText("Decimales= ");

        jLabel9.setText("x-inicial");

        jLabel10.setText("x-final");

        buttonGraficar.setText("Graficar");
        buttonGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGraficarActionPerformed(evt);
            }
        });

        spinnerDecimales.setModel(new javax.swing.SpinnerNumberModel(1, 1, 10, 1));

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(buttonGraficar)
                                .addGap(35, 35, 35)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtY0)
                                            .addComponent(txtX0, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtXinicial, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)
                                            .addComponent(txtXfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                .addGap(65, 65, 65))
                            .addComponent(txtFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addComponent(jLabel5)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtH)
                            .addComponent(spinnerDecimales)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTiempo, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)))
                        .addContainerGap(206, Short.MAX_VALUE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonGraficar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(spinnerDecimales, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtX0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txtY0, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(txtXinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtXfinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())))
        );

        panelGrafico1.setBorder(javax.swing.BorderFactory.createTitledBorder("Grafico 1"));
        panelGrafico1.setPreferredSize(new java.awt.Dimension(150, 150));
        panelGrafico1.setLayout(new java.awt.GridLayout(0, 1));
        panelGrafico1.add(jTabbedPane1);

        javax.swing.GroupLayout panelCentralLayout = new javax.swing.GroupLayout(panelCentral);
        panelCentral.setLayout(panelCentralLayout);
        panelCentralLayout.setHorizontalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelGraficoFases, javax.swing.GroupLayout.DEFAULT_SIZE, 345, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelFvsT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelGrafico1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addComponent(panelGrafico1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelFvsT, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelGraficoFases, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, 897, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    public void initComponents2(){
        getTxtFuncion().setBackground(java.awt.Color.white);
        getTxtFuncion().setFont(new java.awt.Font ("Dialog", 0, 11));
        getTxtFuncion().setForeground(java.awt.Color.black);
        
        parser = new Parser(this);
    }
    
    public String getDecimales()
    {
        String ret = "0.";
        int spin = (Integer)this.spinnerDecimales.getValue();
        
        for(int i = 0; i< spin;i++)
            ret +="0";

        return ret;
    }
    
    private void graficarFuncion()
    { 
        this.parser.agregarFuncion(this.getTxtFuncion().getText());
        dataGraficoFuncion = cargarPuntosFuncion();  
        panelGrafico1.removeAll();
        panelGrafico1.setEnabled(true);
        jTabbedPane1.removeAll();
        jTabbedPane1.add("f vs x", crearPanelGraficoFuncion());
        panelGrafico1.add(jTabbedPane1);
        jTabbedPane1.setVisible(true);
        jTabbedPane1.repaint();
        panelGrafico1.repaint();
        this.repaint(); 
    }
    
    private XYDataset cargarPuntosFuncion() 
    {
        XYSeries xyseries = new XYSeries("Series 1");
        double inicio = Double.parseDouble(txtXinicial.getText());
        double fin = Double.parseDouble(txtXfinal.getText());
        double index = inicio;
        
        double a;
        
        while(index <= fin)
        {
            try
            {
                a = (Double) this.parser.getValor(index);
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
        xylineandshaperenderer.setSeriesPaint(0, Color.black);
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
        
        TextTitle source = new TextTitle("f vs x");
        source.setFont(new Font("SansSerif", Font.PLAIN, 10));
        source.setPosition(RectangleEdge.BOTTOM);
        source.setHorizontalAlignment(HorizontalAlignment.RIGHT);
        jfreechart.addSubtitle(source);
        
        ChartPanel chartpanel = new ChartPanel(jfreechart, false);
        chartpanel.setVisible(true);
        return chartpanel;
    }
    

     private void doGraficarFuncion()
     {
          parser = new Parser(this,Double.parseDouble(this.txtH.getText()), 
                            Double.parseDouble(this.txtXinicial.getText()), 
                            Double.parseDouble(this.txtXfinal.getText()),
                            this.getDecimales(), this.getTxtFuncion().getText());
         
          graficarFuncion(); 
     }

     private XYDataset crearPuntosRaices()
     {
         XYSeries xyseries = new XYSeries("Raices");
         
         for(int i = 0; i< this.raices.size();i++)
         {
             xyseries.add((Double)raices.get(i),(Double)0d);
             System.out.println(raices.get(i) + "\n");
         }
 
         XYSeriesCollection collection = new XYSeriesCollection(xyseries);
         
         return collection;
     }
     
     private VectorSeries getVectorFlechasRojas(List<Flecha> flechas)
     {
         VectorSeries vSeries = new VectorSeries("Atractoras");
         
         for(int i = 0; i < flechas.size();i++)
         {
             Flecha flecha = flechas.get(i);
             
             if(flecha.getColor() == 'r')
             {
                 if(flecha.getDireccion() == 'i')
                 {
                     vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), -1, 0);
                 }
                 else
                 {
                      vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), 1, 0);
                 }
             }
         }
         
         return vSeries;
     }
     
     private VectorSeries getVectorFlechasVerdes(List<Flecha> flechas)
     {
         VectorSeries vSeries = new VectorSeries("Repulsoras");
         
         for(int i = 0; i < flechas.size();i++)
         {
             Flecha flecha = flechas.get(i);
             
             if(flecha.getColor() == 'v')
             {
                 if(flecha.getDireccion() == 'i')
                 {
                     vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), -1, 0);
                 }
                 else
                 {
                      vSeries.add(flecha.getPunto().getX(), flecha.getPunto().getY(), 1, 0);
                 }
             }
         }
         
         return vSeries;
     }
     
     
     private void graficarFases(List<Flecha> flechas)
     {
         dataGraficoFases = crearPuntosRaices();
         jTabbedFases.removeAll();
         
         NumberAxis numberaxisx = new NumberAxis("x");
         numberaxisx.setAutoRangeIncludesZero(false);
         
         numberaxisx.setRange(-5d, 5d);
         
         NumberAxis numberaxisy = new NumberAxis("y");
         numberaxisy.setAutoRangeIncludesZero(false); 
         
         numberaxisy.setRange(raices.get(0)-1d, raices.get(raices.size()-1)+1d);
 
         XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
         renderer.setSeriesPaint(0, Color.BLACK);
         XYPlot plot = new XYPlot(dataGraficoFases, numberaxisy, numberaxisy, renderer);
         plot.setRangeZeroBaselineVisible(true);
         
         VectorSeries vSeries = getVectorFlechasRojas(flechas);
         
         VectorSeriesCollection vsCol = new VectorSeriesCollection();
         vsCol.addSeries(vSeries);
         plot.setDataset(1, vsCol);
         
         VectorRenderer vRenderer = new VectorRenderer();
         vRenderer.setSeriesPaint(0, Color.RED);
         plot.setRenderer(1,vRenderer);

         VectorSeries vSeries1 = getVectorFlechasVerdes(flechas);
         
         VectorSeriesCollection vsCol1 = new VectorSeriesCollection();
         vsCol1.addSeries(vSeries1);
         plot.setDataset(2, vsCol1);
         
         VectorRenderer vRenderer1 = new VectorRenderer();
         vRenderer1.setSeriesPaint(0, Color.GREEN);
         plot.setRenderer(2,vRenderer1);
         
         JFreeChart jfreeChart = new JFreeChart("Fases", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
         
         ChartPanel chartPanel = new ChartPanel(jfreeChart, false);
         chartPanel.setVisible(true);

         jTabbedFases.add("Fases",chartPanel);
         
         jTabbedFases.setVisible(true);
         
         jTabbedFases.repaint();
         panelGraficoFases.repaint();
         this.repaint();
     }
     
     private void doGraficarFase()
     {
        List<Flecha> flechas = this.getFlechasFase();
        this.graficarFases(flechas);
     }
     
     private List<Flecha> getFlechasFase()
     {
         List<Flecha> ret = new ArrayList<Flecha>();
         double h = Double.parseDouble(this.txtH.getText());
         
         for(int i = 0; i<raices.size();i++)
         {
             double raiz = raices.get(i);
             Flecha fDer = null;
             Flecha fIzq = null;
             
             try
             {
                  double izq = (Double)this.parser.getValor(raiz-h);
                  fIzq = new Flecha();
                              
                  if(izq < 0) fIzq.setDireccion('i');
                  else fIzq.setDireccion('d');  
             }
             catch(Exception e)
             {
                 
             }
             
             try
             {
                double der = (Double)this.parser.getValor(raiz+h);
                fDer = new Flecha();
                
                if(der < 0) fDer.setDireccion('i');
                else fDer.setDireccion('d');
             }
             catch(Exception e)
             {
             
             }
             
             if((fDer != null) && (fIzq !=null))
             {
                 if( (fDer.getDireccion() == 'i') &&  (fIzq.getDireccion() == 'd')   )
                 {
                     fDer.setColor('v');
                     fIzq.setColor('v');
                     fDer.setPunto(new Punto(raiz+1,0));
                     fIzq.setPunto(new Punto(raiz-1,0));
                 }
                 else if( (fDer.getDireccion() == 'd') &&  (fIzq.getDireccion() == 'i')   )
                 {
                      fDer.setColor('r');
                      fIzq.setColor('r');
                      fDer.setPunto(new Punto(raiz,0));
                      fIzq.setPunto(new Punto(raiz,0));
                 }
                 else
                 {
                      fDer.setColor('r');
                      fIzq.setColor('r');
                      
                     if( (fDer.getDireccion() == 'd') &&  (fIzq.getDireccion() == 'd')   )
                     {
                        fDer.setPunto(new Punto(raiz,0));
                        fIzq.setPunto(new Punto(raiz-1,0));
                     }
                     else
                     {
                        fDer.setPunto(new Punto(raiz+1,0));
                        fIzq.setPunto(new Punto(raiz,0));
                     }
                 }
                 ret.add(fIzq);
                 ret.add(fDer);
             }
             else System.out.println("No se puede hacer una flecha aca.");
         }
         return ret;
     }
     
    private XYDataset cargarEuler(){
        
        Euler e = new Euler(Double.parseDouble(this.txtH.getText()),
                            Double.parseDouble(this.txtX0.getText()),
                            Double.parseDouble(this.txtY0.getText()),
                            this.getTxtFuncion().getText(),
                            Double.parseDouble(this.txtTiempo.getText()),
                            this.parser);
        
        XYSeries xyseries = new XYSeries("Euler");
        double[] eu = e.getPoints();
        for(int i = 0 ; i < eu.length ; i++){
            
            xyseries.add(i,eu[i]);//Esto nose si es asi o al reves
        }
       
        
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
        return xyseriescollection;
    }
     
     
     private XYDataset cargarPuntosFvsT() 
    {
        XYSeries xyseries = new XYSeries("Series 1");
        double inicio = Double.parseDouble(txtXinicial.getText());
        double fin = Double.parseDouble(txtXfinal.getText());

        XYSeriesCollection xyseriescollection = new XYSeriesCollection();
        for(int i = 0; i< raices.size(); i++)
        {
            xyseries = new XYSeries("Series "+(i+1));
            for(double j = -10; j < 10; j+=0.1)
            {
                xyseries.add(j,raices.get(i));
            }
            xyseriescollection.addSeries(xyseries);
        }
        //ACA FIJATE Q T CARGA UNA SERIE POR CADA RAIZ
        //ENTONCES T PONE UNA DE CADA COLOR
        
        return xyseriescollection;
    }
     
     private void doGraficarFvsT2(){
         dataGraficoFvsT = this.cargarPuntosFvsT(); //Cargo XYSeries para las raices
         XYDataset euler1 = this.cargarEuler(); //Cargo a euler en otra serie
         jTabbedFvsT.removeAll();
         
         
        JFreeChart jfreechart = ChartFactory.createXYLineChart("Line Chart Demo 4", "X", "Y", dataGraficoFvsT, PlotOrientation.VERTICAL, true, true, false);
        XYPlot xyplot = (XYPlot)jfreechart.getPlot();
        xyplot.setDataset(1,euler1);
        xyplot.getDomainAxis().setLowerMargin(0.0D);
        xyplot.getDomainAxis().setUpperMargin(0.0D);

        XYLineAndShapeRenderer xylineandshaperenderer = (XYLineAndShapeRenderer)xyplot.getRenderer();
        //xylineandshaperenderer.setLegendLine(new java.awt.geom.Rectangle2D.Double(-4D, -3D, 8D, 6D));
        
        ChartPanel chartPanel = new ChartPanel(jfreechart, false);
        chartPanel.setVisible(true);
        jTabbedFvsT.add("Fases",chartPanel);
         
         jTabbedFvsT.setVisible(true);
         
         jTabbedFvsT.repaint();
         panelFvsT.repaint();
         this.repaint();

     }
     
     private void doGraficarFvsT()
     {
         dataGraficoFvsT = this.cargarPuntosFvsT();
         XYDataset euler1 = this.cargarEuler();
         
         
         jTabbedFvsT.removeAll();
         
         NumberAxis numberaxisx = new NumberAxis("t");
         numberaxisx.setAutoRangeIncludesZero(false);
      /*   numberaxisx.setUpperMargin(0.12D);
         numberaxisx.setStandardTickUnits(NumberAxis.createIntegerTickUnits());*/
         //numberaxisx.setRange(-5d, 5d);
         
         NumberAxis numberaxisy = new NumberAxis("x");
         numberaxisy.setAutoRangeIncludesZero(false); 
       /*  numberaxisy.setUpperMargin(0.12D);
         numberaxisy.setStandardTickUnits(NumberAxis.createIntegerTickUnits());*/
         
        //   numberaxisy.setRange(raices.get(0)-1d, raices.get(raices.size()-1)+1d);
 
         XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(false, true);
         renderer.setSeriesPaint(0, Color.black);
         XYPlot plot = new XYPlot(dataGraficoFvsT, numberaxisx, numberaxisy, renderer);
         plot.setRangeZeroBaselineVisible(true);
         
         plot.setDataset(1,euler1);
         
         
         JFreeChart jfreeChart = new JFreeChart("F vs T", JFreeChart.DEFAULT_TITLE_FONT, plot, true);
         
         ChartPanel chartPanel = new ChartPanel(jfreeChart, false);
         chartPanel.setVisible(true);

         jTabbedFvsT.add("Fases",chartPanel);
         
         jTabbedFvsT.setVisible(true);
         
         jTabbedFvsT.repaint();
         panelFvsT.repaint();
         this.repaint();
     }
     
      private void vaciarTabbeds()
     {
         jTabbedFases.removeAll();
         jTabbedFvsT.removeAll();
         jTabbedPane1.removeAll();
     }
     
private void txtFuncionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFuncionKeyPressed
     
     if (evt.getKeyCode() == KeyEvent.VK_ENTER) 
     {
        vaciarTabbeds();
        doGraficarFuncion();
        raices = parser.getRaices();
        
        if(!(raices.isEmpty()))
        {
            doGraficarFase();
            doGraficarFvsT();
        }
     }
}//GEN-LAST:event_txtFuncionKeyPressed

private void buttonGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGraficarActionPerformed
       
        vaciarTabbeds();
        doGraficarFuncion(); 
        raices = parser.getRaices();
        
        if(!(raices.isEmpty()))
        {
            doGraficarFase();
            doGraficarFvsT2();
        }
        
        
}//GEN-LAST:event_buttonGraficarActionPerformed

private void txtFuncionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFuncionKeyTyped
    
}//GEN-LAST:event_txtFuncionKeyTyped

private void txtFuncionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFuncionCaretUpdate
    String newExpressionString = getTxtFuncion().getText();
    parser.agregarFuncion(newExpressionString);
}//GEN-LAST:event_txtFuncionCaretUpdate

private void setLookAndFeel() throws HeadlessException {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            SwingUtilities.updateComponentTreeUI(this);

            Toolkit t = Toolkit.getDefaultToolkit();
            this.setLocation((int) (t.getScreenSize().getWidth() - this.getWidth()) / 2, (int) (t.getScreenSize().getHeight() - this.getHeight()) / 2);
        } catch (ClassNotFoundException ex) {
        } catch (InstantiationException ex) {
        } catch (IllegalAccessException ex) {
        } catch (UnsupportedLookAndFeelException ex) {
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonGraficar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedFases;
    private javax.swing.JTabbedPane jTabbedFvsT;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelFvsT;
    private javax.swing.JPanel panelGrafico1;
    private javax.swing.JPanel panelGraficoFases;
    private javax.swing.JSpinner spinnerDecimales;
    private javax.swing.JTextField txtFuncion;
    private javax.swing.JTextField txtH;
    private javax.swing.JTextField txtTiempo;
    private javax.swing.JTextField txtX0;
    private javax.swing.JTextField txtXfinal;
    private javax.swing.JTextField txtXinicial;
    private javax.swing.JTextField txtY0;
    // End of variables declaration//GEN-END:variables

    public javax.swing.JTextField getTxtFuncion() {
        return txtFuncion;
    }

    public void setTxtFuncion(javax.swing.JTextField txtFuncion) {
        this.txtFuncion = txtFuncion;
    }

    public javax.swing.JButton getButtonGraficar() {
        return buttonGraficar;
    }

    public void setButtonGraficar(javax.swing.JButton buttonGraficar) {
        this.buttonGraficar = buttonGraficar;
    }

}
