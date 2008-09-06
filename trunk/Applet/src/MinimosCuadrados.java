/*
 * MinimosCuadrados.java
 *
 * Created on 23 de agosto de 2008, 14:16
 */



import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author  Administrador
 */
public class MinimosCuadrados extends javax.swing.JApplet 
{
    private static final long serialVersionUID = -4246192305944890157L;

    private int cantPuntos;
    private int grados;
    private XYDataset data1;
    private FuncionEnesima funcion;
    private FuncionExponencial funcExp;
    private DefaultTableModel model;
    private JTabbedPane jtabbedpane;
    private String grado;
    
    /** Initializes the applet MinimosCuadrados */
    @Override
    public void init() {
        
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    setLookAndFeel();
                    initComponents();
                    cantPuntos=0;
                    grados=1;
                    radioGrado.setSelected(true);
                 /*   panelGrafico.removeAll();
                    jtabbedpane = new JTabbedPane();
                    panelGrafico.add(jtabbedpane);
                    jtabbedpane.setVisible(true);
                    jtabbedpane.repaint();
                    panelGrafico.repaint();
                     NumberAxis numberaxis = new NumberAxis("X");
                    numberaxis.setAutoRangeIncludesZero(false);
                    NumberAxis numberaxis1 = new NumberAxis("Y");
                    numberaxis1.setAutoRangeIncludesZero(false);
                    XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
                    XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);
                    JFreeChart jfreechart = new JFreeChart("Regresión ", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
                    ChartPanel chartpanel = new ChartPanel(jfreechart, false);
                    chartpanel.setVisible(true);
                    jtabbedpane.add("Regresión",chartpanel);
                     jtabbedpane.setVisible(true);
                    jtabbedpane.repaint();
                    panelGrafico.repaint();*/
                    funcion = new FuncionEnesima();
                    funcExp = new FuncionExponencial();
                    model =(DefaultTableModel) tablePuntos.getModel();
                    cmbEjemplos.setSelectedIndex(1);
                    generarEjemploParabola2();
                    model.addTableModelListener(new TableModelListener() {
                        public void tableChanged(TableModelEvent e) {
                            tableListener(e);
                        }
                    });
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void graficar()
    {
        int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());       
        
        if(spinnerGrado.isEnabled()) grados = (Integer)spinnerGrado.getValue();  //   VER !!! para exponencial no hay grados. Putea a veces
        else grados = 1;
        
        if(spinnerGrado.isEnabled()) this.grado = spinnerGrado.getValue().toString();
        else this.grado = "Exponencial";
       // SistemaDeEcuaciones s = new SistemaDeEcuaciones();

        double[][] a = new double[filas][grados + 1];
        double[][] b = new double[filas][1];
        double[][] at = new double[grados + 1][filas];
        double[] atxb = new double[grados+1];
        double[][] atxa = new double[grados+1][grados+1];
       // double[][] coef = new double[grados+1][grados+2];
        a = cargarValores();
        b = cargarB();
        at = trasponerMatriz(a,filas);
       // Matriz ata = Matriz.producto(new Matriz(at),new Matriz(a));
        //Matriz atb = Matriz.producto(new Matriz(at),new Matriz(b));

        atxa = multiplicarMatrices(at,a);
        atxb = multiplicarMatrices2(at,b);
        //coef  = armarSistema(atxa,atxb);
       /* SistemaEcuaciones sisEcu = new SistemaEcuaciones();
        sisEcu.setNumEcs(Integer.parseInt(spinnerGrado.getValue().toString())+1);
        sisEcu.setCoef(coef);
        double[] result = sisEcu.getSolucion();*/

       /* Matriz alfas = new Matriz(atxa);
        Vector res = new Vector(atxb);*/

        Matriz alfas = new Matriz(atxa);
        Vector res = new Vector(atxb);
        Vector resultSist = Matriz.producto(Matriz.inversa(alfas), res);

        double[] result = resultSist.x;




        /*for(int i = 0;i<result.length;i++)
            result[i]=Math.round(result[i]);*/
        //double[] resultado;
        //double[] result = s.resolver(atxa, atxb, resultado, Integer.parseInt(spinnerGrado.getValue().toString())+1); 
        //System.out.println("hola");

        if(this.grado.equalsIgnoreCase("Exponencial")){
            result = cambiarVariables(result);
        }
        //ACA JFREECHART

        data1 = createSampleData1();
        panelGrafico.removeAll();
        jtabbedpane = new JTabbedPane();

        jtabbedpane.add("Regresión", createChartPanel2(result));
        panelGrafico.add(jtabbedpane);
        //panelGrafico.add("HOLAAA",createChartPanel2(result));
        labelFuncion.setText(generarFuncion(result));
        jtabbedpane.setVisible(true);
        jtabbedpane.repaint();
        panelGrafico.repaint();
        this.repaint();    

        if(hayMuchasSoluciones(result))
        {
            lblSol.setText("Matriz singular.");
            labelFuncion.setText("");
        }
        else
        {
            lblErrorGlobal.setText("");
            lblCoefRegr.setText("");
            calcularErrores();
            lblSol.setText("");
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        panelGrafico = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePuntos = new javax.swing.JTable();
        buttonOK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        spinnerCantPuntos = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        labelFuncion = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtFieldLimiteInfX = new javax.swing.JTextField();
        txtFieldLimiteSupX = new javax.swing.JTextField();
        spinnerGrado = new javax.swing.JSpinner();
        radioGrado = new javax.swing.JRadioButton();
        radioExponencial = new javax.swing.JRadioButton();
        lblSol = new javax.swing.JLabel();
        lblTituloErrorGlobal = new javax.swing.JLabel();
        lblErrorGlobal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lblCoefRegr = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        cmbEjemplos = new javax.swing.JComboBox();
        btnClean = new javax.swing.JButton();

        setStub(null);
        getContentPane().setLayout(new java.awt.GridLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(800, 600));

        panelGrafico.setLayout(new java.awt.GridLayout(0, 1));

        tablePuntos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "X", "Y"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Double.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablePuntos);

        buttonOK.setText("Graficar");
        buttonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonOKActionPerformed(evt);
            }
        });

        jLabel1.setText("Cantidad Puntos");

        spinnerCantPuntos.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        spinnerCantPuntos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerCantPuntosStateChanged(evt);
            }
        });

        jLabel4.setText("Función Regresión: ");

        jLabel3.setText("Límite Inferior Eje X:");

        jLabel5.setText("Límite Superior Eje X:");

        txtFieldLimiteInfX.setText("0");
        txtFieldLimiteInfX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldLimiteInfXKeyPressed(evt);
            }
        });

        txtFieldLimiteSupX.setText("5");
        txtFieldLimiteSupX.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFieldLimiteSupXKeyPressed(evt);
            }
        });

        spinnerGrado.setModel(new javax.swing.SpinnerNumberModel(1, 1, 50, 1));

        buttonGroup1.add(radioGrado);
        radioGrado.setText("Grado");
        radioGrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioGradoActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioExponencial);
        radioExponencial.setSelected(true);
        radioExponencial.setText("Exponencial");
        radioExponencial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioExponencialActionPerformed(evt);
            }
        });

        lblSol.setFont(new java.awt.Font("Tahoma", 1, 14));

        lblTituloErrorGlobal.setText("Error Global: ");

        jLabel2.setText("Coeficiente de Regresión (R2): ");

        jLabel6.setText("Ejemplos:");

        cmbEjemplos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Ejemplo Recta", "Ejemplo Parabola 2º Grado", "Ejemplo Parabola 3º Grado", "Ejemplo Exponencial" }));
        cmbEjemplos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmbEjemplosActionPerformed(evt);
            }
        });

        btnClean.setText("Limpiar");
        btnClean.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCleanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFieldLimiteSupX)
                            .addComponent(txtFieldLimiteInfX, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEjemplos, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1)
                            .addComponent(spinnerCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(radioGrado)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(spinnerGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(radioExponencial)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(btnClean, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(buttonOK, javax.swing.GroupLayout.Alignment.LEADING)))
                        .addGap(6, 6, 6)
                        .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(labelFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblCoefRegr, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTituloErrorGlobal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblErrorGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblSol, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtFieldLimiteInfX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(cmbEjemplos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(txtFieldLimiteSupX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioGrado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioExponencial)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonOK)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnClean))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(labelFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel4)
                        .addGap(12, 12, 12)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblTituloErrorGlobal))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblErrorGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblSol, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(lblCoefRegr, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addGap(13, 13, 13))))
        );

        getContentPane().add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    
    private boolean hayMuchasSoluciones(double[] result)
    {
        for(int i = 0;i<result.length;i++)
        {
            if(String.valueOf(result[i]).equalsIgnoreCase("nan")) return true;
        }
        return false;
    }
    
    private double calcularErrorGlobal()
    {
        double err = 0;
        for(int i = 0;i<this.model.getRowCount();i++)
        {
            if(radioGrado.isSelected())
                err += Math.pow(funcion.getValue(Double.parseDouble(model.getValueAt(i, 0).toString()))-Double.parseDouble(model.getValueAt(i, 1).toString()),2);
            else
                err += Math.pow(funcExp.getValue(Double.parseDouble(model.getValueAt(i, 0).toString()))-Double.parseDouble(model.getValueAt(i, 1).toString()),2);
        }
        return err;
    }
    
    private double calcularPromY()
    {
        double sum =0;
        for(int i = 0;i<this.model.getRowCount();i++)
            sum += Double.parseDouble(model.getValueAt(i, 1).toString());
        
        return (sum/this.model.getRowCount());
    }
    
    private double calcularCoeficienteRegresion(double erGlo)
    {
        double denominador = 0;
        double promY = this.calcularPromY();
        
        for(int i = 0;i<this.model.getRowCount();i++)        
            denominador+= Math.pow(Double.parseDouble(model.getValueAt(i, 1).toString()) - promY,2);

        return erGlo/denominador;
    }
    
    private void calcularErrores()
    {
        double erGlo = this.calcularErrorGlobal();
        lblErrorGlobal.setText(String.valueOf(erGlo));
        lblCoefRegr.setText(String.valueOf(this.calcularCoeficienteRegresion(erGlo)));
    }
private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
// TODO add your handling code here:
    graficar();
}//GEN-LAST:event_buttonOKActionPerformed

    private void tableListener(TableModelEvent e)
    {
        if(e.getColumn() > -1)
        {
            int col = e.getColumn();
            boolean flag = false;
           
            if(col == 0)
            { 
                if( (model.getValueAt(e.getLastRow(), col) != null)&&
                    (model.getValueAt(e.getLastRow(), col+1)) != null 
                  ) 
                    flag= true;
            }
            else
            {
                if( (model.getValueAt(e.getLastRow(), col-1) != null )&&
                    (model.getValueAt(e.getLastRow(), col)) != null
                  ) 
                    flag= true;
            }
            
            if(flag)
            {           
                data1 = createSampleData1();
                panelGrafico.removeAll();
                jtabbedpane = new JTabbedPane();
                panelGrafico.add(jtabbedpane);
                jtabbedpane.setVisible(true);
                jtabbedpane.repaint();
                panelGrafico.repaint();
                NumberAxis numberaxis = new NumberAxis("X");
                numberaxis.setAutoRangeIncludesZero(false);
                NumberAxis numberaxis1 = new NumberAxis("Y");
                numberaxis1.setAutoRangeIncludesZero(false);
                XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true); 
                XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);
                JFreeChart jfreechart = new JFreeChart("Regresión ", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
                ChartPanel chartpanel = new ChartPanel(jfreechart, false);
                chartpanel.setVisible(true);
                jtabbedpane.add("Regresión",chartpanel);
                jtabbedpane.setVisible(true);
                jtabbedpane.repaint();
                panelGrafico.repaint();
            }
        }
    }
    
    private XYDataset createSampleData1()
    {
        XYSeries xyseries = new XYSeries("Series 1");
        
        //DefaultTableModel model = (DefaultTableModel)tablePuntos.getModel();
        for(int i = 0 ; i < model.getRowCount() ; i++){
            xyseries.add(Double.parseDouble(model.getValueAt(i, 0).toString()),Double.parseDouble(model.getValueAt(i, 1).toString()));
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
        return xyseriescollection;
    }
    
    private ChartPanel createChartPanel2(double[] result)
    {
        NumberAxis numberaxis = new NumberAxis("X");
        numberaxis.setAutoRangeIncludesZero(false);
        NumberAxis numberaxis1 = new NumberAxis("Y");
        numberaxis1.setAutoRangeIncludesZero(false);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
        XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);
       // double ad[] = Regression.getPowerRegression(data1, 0);
        XYSeriesCollection coordenadas = cordenadasGrafico(result);
        XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, false);
        xylineandshaperenderer1.setSeriesPaint(0, Color.blue);
        //xyplot.setDataset(1, xydataset);
        xyplot.setDataset(1,coordenadas);
        xyplot.setRenderer(1, xylineandshaperenderer1);// 
        
        JFreeChart jfreechart = new JFreeChart("Regresión " + ((spinnerGrado.isEnabled())?"de grado " + spinnerGrado.getValue().toString():"Exponencial"), JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        ChartPanel chartpanel = new ChartPanel(jfreechart, false);
        chartpanel.setVisible(true);
        return chartpanel;
    }
    
    
private String generarFuncion(double[] result){
    String func = new String();

    
    if(spinnerGrado.isEnabled()) grado = spinnerGrado.getValue().toString();
    else grado = "Exponencial";
    if(grado.equalsIgnoreCase("Exponencial")){
        func = "Y = "+result[0]+" e^"+result[1]+"X"; 
    }else{
        /*if(grados == 1){
            funcion = "Y = "+result[0]+" + "+result[1]+"X";
        }else if(grados == 2){
            funcion = "Y = "+result[0]+" + "+result[1]+"X + "+result[2]+"X^2";
        }else{
            funcion = "Y = "+result[0]+" + "+result[1]+"X + "+result[2]+"X^2 + "+result[3]+"X^3";
        }*/
        func = this.funcion.getFunctionString();
    }
    return func;
}

private XYSeriesCollection cordenadasGrafico(double[] result){
    XYSeries series = new XYSeries("Curva de Regresión");
    double step = (Double.parseDouble(txtFieldLimiteSupX.getText()) - Double.parseDouble(txtFieldLimiteInfX.getText())) / (100 - 1);//aca hay que ver los valores de 11F 2D y 100
    
    
    
    if(spinnerGrado.isEnabled()) grado = spinnerGrado.getValue().toString();
    else grado = "Exponencial";
    
    if(grado.equalsIgnoreCase("Exponencial")){
        for(int i = 0 ; i < 100 ; i++){
            this.funcExp = new FuncionExponencial(result[0], result[1]);
            double x = Double.parseDouble(txtFieldLimiteInfX.getText()) + (step * i);
            series.add(x, funcExp.getValue(x));
        }
    }else{
        /*if(grados == 1){
            for(int i = 0 ; i < 100 ; i++){
                FuncionLineal lin = new FuncionLineal(result[0], result[1]);
                double x = Double.parseDouble(txtFieldLimiteInfX.getText()) + (step * i);
                series.add(x,lin.getValue(x));
            }
        }else if(grados ==2){
            for(int i = 0 ; i < 100 ; i++){
                FuncionSegundoGrado seg = new FuncionSegundoGrado(result[0], result[1], result[2]);
                double x = Double.parseDouble(txtFieldLimiteInfX.getText()) + (step * i);
                series.add(x,seg.getValue(x));
            }
        }else{
            for(int i = 0 ; i < 100 ; i++){
                FuncionTercerGrado ter = new FuncionTercerGrado(result[0], result[1], result[2], result[3]);
                double x = Double.parseDouble(txtFieldLimiteInfX.getText()) + (step * i);
                series.add(x,ter.getValue(x));
            }
        }*/
        
        ArrayList<Double> coeficientes = new ArrayList();
        
        for(int i = 0;i < result.length;i++)
            coeficientes.add(result[i]);
        
        this.funcion = new FuncionEnesima(coeficientes, Integer.parseInt(spinnerGrado.getValue().toString()));
        
        for(int i = 0; i<100;i++)
        {
            double x = Double.parseDouble(txtFieldLimiteInfX.getText()) + (step * i);
            series.add(x,funcion.getValue(x));
        }    
    }
    XYSeriesCollection collection = new XYSeriesCollection(series);
    return collection;
}


private double[] cambiarVariables(double[] a){
    a[0] = Math.pow(Math.E, a[0]);
    return a;
}

/*private double[][] armarSistema(double[][] atxa, double[][] atxb){
    double[][] coef = new double[grados+1][grados+2];
    
    for(int i = 0 ; i < grados+1 ; i++ ){
        for(int j = 0 ; j < grados+1 ; j++){
            coef[i][j] = atxa[i][j];
        }
    }
    for(int k = 0 ; k < grados+1 ; k++){
        coef[k][grados+1] = atxb[k][0];
    }
    
    return coef;
}*/

private double[] multiplicarMatrices2(double[][] at, double[][] b){
    double[] atxb = new double[grados+1];

    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());


    for (int i=0; i < grados+1; i++)
    {
        /*for (int j=0; j < 1; j++)
        {*/
            for (int p=0; p < filas; p++)
            atxb[i] += at[i][p]*b[p][0];
        //}
    }
    return atxb;
}

private double[][] multiplicarMatrices(double[][] at, double[][] a){
    double[][] atxa = new double[grados+1][grados+1];

    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());


    for (int i=0; i < grados+1; i++)
    {
        for (int j=0; j < grados+1; j++)
        {
            for (int p=0; p < filas; p++)
            atxa[i][j] += at[i][p]*a[p][j];
        }
    }
    return atxa;
}

private double[][] cargarB(){
    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());
    double[][] b = new double[filas][1];
    model = (DefaultTableModel)tablePuntos.getModel();
    
    if(spinnerGrado.isEnabled()) grado = spinnerGrado.getValue().toString();
    else grado = "Exponencial";
    
    if(grado.equalsIgnoreCase("Exponencial")){
        for(int i = 0 ; i < filas ; i++){
            b[i][0] = Math.log(Double.parseDouble(model.getValueAt(i, 1).toString()));
        }
    }else{
        for(int i = 0 ; i < filas ; i++){
            b[i][0] = Double.parseDouble(model.getValueAt(i, 1).toString());
        }
    }
    return b;
}

private double[][] cargarValores(){
    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());
    double[][] a = new double[filas][grados+1];
   
    for(int j = 0 ; j < model.getRowCount() ; j++  ){
        a[j][0] = 1;
        for(int i = 1 ; i < grados +1 ; i++){
            a[j][i] = Math.pow(Double.parseDouble(model.getValueAt(j, 0).toString()), i);
        }
    }
    return a;
}
private double[][] trasponerMatriz(double[][] a,int filas){
    double[][] at = new double[grados+1][filas];
    for(int i = 0 ; i < grados+1 ; i++){
        for(int j = 0 ; j < filas ; j++){
            at[i][j] = a[j][i];
        }
    }
    return at;
}
private void spinnerCantPuntosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerCantPuntosStateChanged
    // TODO add your handling code here:
    if(Integer.parseInt(spinnerCantPuntos.getValue().toString()) < cantPuntos){
        int aux = cantPuntos - Integer.parseInt(spinnerCantPuntos.getValue().toString());
        for(int i = 0 ; i < aux ; i++){
            ((DefaultTableModel)tablePuntos.getModel()).removeRow(tablePuntos.getRowCount() -1);
        }
        cantPuntos = Integer.parseInt(spinnerCantPuntos.getValue().toString());
    }else{
        int aux = Integer.parseInt(spinnerCantPuntos.getValue().toString()) - cantPuntos;
        for(int i = 0 ; i < aux ; i++){
            ((DefaultTableModel)tablePuntos.getModel()).addRow(new Double[]{});
        }
        cantPuntos = Integer.parseInt(spinnerCantPuntos.getValue().toString());
    }
}//GEN-LAST:event_spinnerCantPuntosStateChanged

private void radioGradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioGradoActionPerformed
    if(radioGrado.isSelected()) spinnerGrado.setEnabled(true);
    else spinnerGrado.setEnabled(false);
}//GEN-LAST:event_radioGradoActionPerformed

private void radioExponencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioExponencialActionPerformed
    if(radioExponencial.isSelected()) spinnerGrado.setEnabled(false);
    else spinnerGrado.setEnabled(true);
}//GEN-LAST:event_radioExponencialActionPerformed

private void generarEjemploRecta()
{
    this.spinnerCantPuntos.setValue(4);
    this.spinnerGrado.setEnabled(true);
    this.radioGrado.setSelected(true);
    this.spinnerGrado.setValue(1);
    for(int i =0; i<this.model.getRowCount();i++)
    {
        model.setValueAt(i, i  , 0);
        model.setValueAt(i, i  , 1);
    }
    graficar();
}

private void generarEjemploParabola2()
{
    this.spinnerCantPuntos.setValue(4);
    this.spinnerGrado.setEnabled(true);
    this.radioGrado.setSelected(true);
    this.spinnerGrado.setValue(2);
    for(int i =0; i<this.model.getRowCount();i++)
    {
        model.setValueAt(i, i  , 0);
        model.setValueAt(Math.pow(i, 2), i  , 1);
    }
    graficar();
}

private void generarEjemploParabola3()
{
    this.spinnerCantPuntos.setValue(4);
    this.spinnerGrado.setEnabled(true);
    this.radioGrado.setSelected(true);
    this.spinnerGrado.setValue(3);
    for(int i =0; i<this.model.getRowCount();i++)
    {
        model.setValueAt(i, i  , 0);
        model.setValueAt(Math.pow(i, 3), i  , 1);
    }
    graficar();
}


private void generarEjemploExponencial()
{
    this.spinnerCantPuntos.setValue(3);
    this.radioExponencial.setSelected(true);
    this.spinnerGrado.setEnabled(false);
    for(int i =0; i<this.model.getRowCount();i++)
    {
        model.setValueAt(i, i  ,0);
        model.setValueAt(Math.pow(3, i), i  ,1);
    }
    graficar();
}

private void cmbEjemplosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEjemplosActionPerformed
       
    if(cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Recta"))
    {
        this.grado = "1";
        generarEjemploRecta();
    }
    else
    {
        if(cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Parabola 2º Grado"))
        {
            this.grado = "2";
            generarEjemploParabola2();
        }
        else
        {
            if(cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Parabola 3º Grado"))
            {
                this.grado = "3";
                generarEjemploParabola3();
            }
            else
            {
                this.grado = "Exponencial";
                generarEjemploExponencial();
            }
        }
    }
}//GEN-LAST:event_cmbEjemplosActionPerformed

private void cleanComponentes() 
{
    this.spinnerGrado.setEnabled(true);
    this.radioExponencial.setSelected(false);
    this.radioGrado.setSelected(true);
    this.spinnerCantPuntos.setValue(1);
    this.spinnerGrado.setValue(1);
    this.lblCoefRegr.setText("");
    this.lblErrorGlobal.setText("");
    this.labelFuncion.setText("");
    
    for(int i = 0; i<model.getRowCount()-1;i++)
        model.removeRow(i);
    model.setValueAt(null, 0, 0);
    model.setValueAt(null, 0, 1);
}

private void limpiar()
{
    cleanComponentes();
    data1=null;
    panelGrafico.removeAll();
    jtabbedpane = new JTabbedPane();
    panelGrafico.add(jtabbedpane);
    jtabbedpane.setVisible(true);
    jtabbedpane.repaint();
    panelGrafico.repaint();
     NumberAxis numberaxis = new NumberAxis("X");
    numberaxis.setAutoRangeIncludesZero(false);
    NumberAxis numberaxis1 = new NumberAxis("Y");
    numberaxis1.setAutoRangeIncludesZero(false);
    XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
    XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);
    JFreeChart jfreechart = new JFreeChart("Regresión ", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
    ChartPanel chartpanel = new ChartPanel(jfreechart, false);
    chartpanel.setVisible(true);
    jtabbedpane.add("Regresión",chartpanel);
    jtabbedpane.setVisible(true);
    jtabbedpane.repaint();
    panelGrafico.repaint();
}

private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
    limpiar();
}//GEN-LAST:event_btnCleanActionPerformed

private void txtFieldLimiteSupXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldLimiteSupXKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        graficar();
}//GEN-LAST:event_txtFieldLimiteSupXKeyPressed

private void txtFieldLimiteInfXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldLimiteInfXKeyPressed
    if(evt.getKeyCode()==KeyEvent.VK_ENTER)
        graficar();
}//GEN-LAST:event_txtFieldLimiteInfXKeyPressed

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
    private javax.swing.JButton btnClean;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton buttonOK;
    private javax.swing.JComboBox cmbEjemplos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelFuncion;
    private javax.swing.JLabel lblCoefRegr;
    private javax.swing.JLabel lblErrorGlobal;
    private javax.swing.JLabel lblSol;
    private javax.swing.JLabel lblTituloErrorGlobal;
    private javax.swing.JPanel panelGrafico;
    private javax.swing.JRadioButton radioExponencial;
    private javax.swing.JRadioButton radioGrado;
    private javax.swing.JSpinner spinnerCantPuntos;
    private javax.swing.JSpinner spinnerGrado;
    private javax.swing.JTable tablePuntos;
    private javax.swing.JTextField txtFieldLimiteInfX;
    private javax.swing.JTextField txtFieldLimiteSupX;
    // End of variables declaration//GEN-END:variables

}
