package minimoscuadrados;
/*
 * MinimosCuadrados.java
 *
 * Created on 23 de agosto de 2008, 14:16
 */

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
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
 * @author  Ignacio Cicero, Nerina Gaggero y Bruno Hortelano - Universidad Argentina de la Empresa (UADE)
 */
public class MinimosCuadrados extends javax.swing.JApplet 
{
    private static final long serialVersionUID = -4246192305944890157L;

    /* PARAMETROS */
    
    private String fondoForm;
    private String fondoGrafico;
    private String font;
    private int fontSize;
    private String fontColor;
    private int anchoTrazoGrafico;
    private int xMin;
    private int xMax;
    private String idioma;
    private String colorEjes;
    private int formHeight;
    private int formWidth;
    private int cantDecimales;
    private boolean truncar;
    
    private boolean cargarEjemplo;
    private double[][] points;
    private String param_Grado;
    private String nombreEjemplo;
    
    /* FIN PARAMETROS */
    
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
                    fontSize = 0;
                    xMax = 0;
                    xMin = 0;
                    formHeight = 0;
                    formWidth = 0;
                    boolean ok = loadParams();
                    if(ok) setParams();
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
                    funcion = new FuncionEnesima();
                    funcExp = new FuncionExponencial();
                    model =(DefaultTableModel) tablePuntos.getModel();
                    cmbEjemplos.setSelectedIndex(1);
                   
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
    
    private void traducir()
    {
        this.lblEjemplos.setText("Examples");
        this.lblFuncReg.setText("Regression function");
        this.lblLimInf.setText("X axis inferior limit");
        this.lblLimSup.setText("X axis superior limit");
        this.lblTituloCoefReg.setText("Regression coeficient");
        this.lblTituloErrorGlobal.setText("Global error");
        this.btnClean.setText("Clear");
        this.buttonOK.setText("Graphic");
        this.radioGrado.setText("Grade");
        this.chkTruncar.setText("Truncate");
        this.lblCantPuntos.setText("Number of points");
    }
    
    private void loadPoints()
    {
        /* value = "(1.3,5);(3,4);(3,5.4)..... "  */
        
        String auxPoints = this.getParameter("puntos");
        if(auxPoints == null) return;
        String [] pares = auxPoints.split(";"); /* pares [0] => (1.3,5)  pares[1] => (3,4) */
        this.points = new double[pares.length][2];
        for(int i = 0; i< pares.length;i++)
        {
            pares[i] = pares[i].replace('(', ' '); // pares[0] => 1.3,5)
            pares[i] = pares[i].replace(')', ' '); // pares[0] => 1.3,5
            String [] xy = pares[i].split(","); // xy[0] => 1,3   xy[1] => 5
            this.points[i][0] = Double.valueOf(xy[0]); //points[i][0] => 1,3
            this.points[i][1] = Double.valueOf(xy[1]); //points[i][1] => 5
        }
    }
    
    private void setPoints()
    {
        this.spinnerCantPuntos.setValue(this.points.length);
        for(int i =0; i < this.points.length; i++)
        {
            this.model.setValueAt(points[i][0], i, 0);
            this.model.setValueAt(points[i][1], i, 1);
        }
        
        if(this.grado.equalsIgnoreCase("exponencial"))
        {
            this.radioExponencial.setSelected(true);
            this.radioGrado.setSelected(false);
        }
        else
        {
            this.radioExponencial.setSelected(false);
            this.radioGrado.setSelected(true);
            this.spinnerGrado.setValue(Integer.valueOf(this.grado));
        }
        
        this.cmbEjemplos.addItem(this.nombreEjemplo);
        this.graficar();
    }
    
    private void setParams()    
    {
        if(xMax == 0) txtFieldLimiteSupX.setText(String.valueOf(Constantes.DEFAULT_XMax));
        else txtFieldLimiteSupX.setText(String.valueOf(xMax));
        
        if(xMin == 0) txtFieldLimiteInfX.setText(String.valueOf(Constantes.DEFAULT_XMin));
        else txtFieldLimiteSupX.setText(String.valueOf(xMin));
        
        if(formHeight == 0)
        {
            if(formWidth == 0) this.setSize(Constantes.DEFAULT_FRAME_WIDTH, Constantes.DEFAULT_FRAME_HEIGHT);
            else this.setSize(formWidth, Constantes.DEFAULT_FRAME_HEIGHT);
        }
        else
        {
             if(formWidth == 0) this.setSize(Constantes.DEFAULT_FRAME_WIDTH, formHeight);
             else this.setSize(formWidth, formHeight);
        }
        
        if(this.idioma.equalsIgnoreCase("en")) traducir();
        
        if(this.param_Grado.equalsIgnoreCase("exp")) this.grado = "Exponencial";
        else this.grado = this.param_Grado;
        
        if(this.cargarEjemplo) setPoints();
        
        if(this.truncar) this.chkTruncar.setSelected(true);
        else this.chkTruncar.setSelected(false);
    }
    
    private boolean loadParams()
    {
        try
        {
            this.fondoForm = this.getParameter("fondoForm");
            this.fondoGrafico = this.getParameter("fondoGrafico");
            this.font = this.getParameter("font");
            this.fontSize = Integer.parseInt(this.getParameter("fontSize"));
            this.fontColor = this.getParameter("fontColor");
            this.anchoTrazoGrafico = Integer.valueOf(this.getParameter("anchoTrazo"));
            this.xMin = Integer.parseInt(this.getParameter("xMin"));
            this.xMax = Integer.parseInt(this.getParameter("xMax"));
            this.idioma = this.getParameter("idioma");
            this.colorEjes = this.getParameter("colorEjes");
            this.formWidth = Integer.parseInt(this.getParameter("formWidth"));
            this.formHeight = Integer.parseInt(this.getParameter("formHeight"));
            this.cargarEjemplo = Boolean.parseBoolean(this.getParameter("cargarEjemplo"));
            this.param_Grado = this.getParameter("grado"); // grado = 1 o grado = exp
            this.nombreEjemplo = this.getParameter("nombreEjemplo");
            this.cantDecimales = Integer.parseInt(this.getParameter("cantDecimales"));
            this.truncar = Boolean.parseBoolean(this.getParameter("truncar"));
                    
            
            if(cargarEjemplo) loadPoints();
            
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    private void graficar()
    {
        int filas = model.getRowCount();       
        //int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());       
        
        if(spinnerGrado.isEnabled()){
            grados = (Integer)spinnerGrado.getValue();
            if(grados >= filas){
                grados = filas - 1;
            }
        }  
        else{
            grados = 1;
        }
        
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



        if(chkTruncar.isSelected())
        {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(this.cantDecimales);
            
            for(int i = 0;i<result.length;i++)
            {
               //result[i]= Math.floor(result[i] * 100.0)/100.0;
              // result[i]= Math.round(result[i]);
               String aux = df.format(result[i]);
               aux = aux.replace(',', '.');
               result[i] = Double.parseDouble(aux);
            }
        }
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
        lblCantPuntos = new javax.swing.JLabel();
        spinnerCantPuntos = new javax.swing.JSpinner();
        lblFuncReg = new javax.swing.JLabel();
        labelFuncion = new javax.swing.JLabel();
        lblLimInf = new javax.swing.JLabel();
        lblLimSup = new javax.swing.JLabel();
        txtFieldLimiteInfX = new javax.swing.JTextField();
        txtFieldLimiteSupX = new javax.swing.JTextField();
        spinnerGrado = new javax.swing.JSpinner();
        radioGrado = new javax.swing.JRadioButton();
        radioExponencial = new javax.swing.JRadioButton();
        lblSol = new javax.swing.JLabel();
        lblTituloErrorGlobal = new javax.swing.JLabel();
        lblErrorGlobal = new javax.swing.JLabel();
        lblTituloCoefReg = new javax.swing.JLabel();
        lblCoefRegr = new javax.swing.JLabel();
        lblEjemplos = new javax.swing.JLabel();
        cmbEjemplos = new javax.swing.JComboBox();
        btnClean = new javax.swing.JButton();
        chkTruncar = new javax.swing.JCheckBox();

       // setStub(null);
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

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

        lblCantPuntos.setText("Cantidad de Puntos");

        spinnerCantPuntos.setModel(new javax.swing.SpinnerNumberModel(0, 0, 100, 1));
        spinnerCantPuntos.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spinnerCantPuntosStateChanged(evt);
            }
        });

        lblFuncReg.setText("Función Regresión: ");

        lblLimInf.setText("Límite Inferior Eje X:");

        lblLimSup.setText("Límite Superior Eje X:");

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

        lblTituloCoefReg.setText("Coeficiente de Regresión (R2): ");

        lblEjemplos.setText("Ejemplos:");

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

        chkTruncar.setText("Truncar");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLimInf)
                            .addComponent(lblLimSup))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtFieldLimiteSupX)
                            .addComponent(txtFieldLimiteInfX, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblEjemplos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cmbEjemplos, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(chkTruncar))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblFuncReg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 474, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCantPuntos)
                                    .addComponent(spinnerCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnClean)
                                    .addComponent(buttonOK)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(radioGrado)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(spinnerGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(radioExponencial))
                                .addGap(18, 18, 18)
                                .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTituloErrorGlobal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblErrorGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 294, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTituloCoefReg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCoefRegr, javax.swing.GroupLayout.PREFERRED_SIZE, 611, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblSol, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(156, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLimInf)
                    .addComponent(txtFieldLimiteInfX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEjemplos)
                    .addComponent(cmbEjemplos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(chkTruncar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblLimSup)
                            .addComponent(txtFieldLimiteSupX, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lblCantPuntos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(spinnerCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(16, 16, 16)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(spinnerGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(radioGrado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(radioExponencial)
                        .addGap(32, 32, 32)
                        .addComponent(buttonOK)
                        .addGap(18, 18, 18)
                        .addComponent(btnClean))
                    .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(lblFuncReg))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(labelFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(lblTituloErrorGlobal))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblErrorGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblTituloCoefReg)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblCoefRegr, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(lblSol, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        if(chkTruncar.isSelected())
        {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(this.cantDecimales);
            String aux = df.format(erGlo);
            aux = aux.replace(',', '.');
            erGlo = Double.parseDouble(aux);
            lblErrorGlobal.setText(String.valueOf(erGlo));
            double cr = this.calcularCoeficienteRegresion(erGlo);
            String aux2 = df.format(cr);
            aux2 = aux2.replace(',', '.');
            cr = Double.parseDouble(aux2);
            lblCoefRegr.setText(String.valueOf(aux2));
        }
        else
        {
            lblErrorGlobal.setText(String.valueOf(erGlo));
            lblCoefRegr.setText(String.valueOf(this.calcularCoeficienteRegresion(erGlo)));
        }
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
                int aux2 = model.getRowCount();
                boolean bool = true;
                if(aux2>1){
                    for(int i=0 ; i < aux2 ; i++){
                        Object aux3 = model.getValueAt(i, 0);
                        Object aux4 = model.getValueAt(i, 1);
                        if(( aux3 == null) || (aux4 == null)){
                            bool = false;
                        }
                    }
                    if(bool){
                        graficar();
                    }
                }
            }
        }
    }
    
    private XYDataset createSampleData1()
    {
        XYSeries xyseries = new XYSeries("Series 1");
        
        //DefaultTableModel model = (DefaultTableModel)tablePuntos.getModel();
        for(int i = 0 ; i < model.getRowCount() ; i++){
            if( (model.getValueAt(i, 0)!=null) &&(model.getValueAt(i, 1)!= null))
            {
                xyseries.add(Double.parseDouble(model.getValueAt(i, 0).toString()),Double.parseDouble(model.getValueAt(i, 1).toString()));
            }
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
        

        this.funcion = new FuncionEnesima(coeficientes, grados);
        
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

    int filas = model.getRowCount();
    //int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());


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

    int filas = model.getRowCount();
    //int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());


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
    int filas = model.getRowCount();
//    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());
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
    int filas = model.getRowCount();
    //int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());
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
        int aux2 = model.getRowCount();
        boolean bool = true;
        if(aux2>1){
            for(int i=0 ; i < aux2 ; i++){
                Object aux3 = model.getValueAt(i, 0);
                Object aux4 = model.getValueAt(i, 1);
                if(( aux3 == null) || (aux4 == null)){
                    bool = false;
                }
            }
            if(bool){
                graficar();
            }
        }
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

/*public void vaciarTabla() 
{
    ((DefaultTableModel)tablePuntos.getModel()).getDataVector().removeAllElements();
}*/


private void generarEjemploRecta()
{
    
    this.spinnerCantPuntos.setValue(4);
    this.spinnerGrado.setEnabled(true);
    this.radioGrado.setSelected(true);
    this.spinnerGrado.setValue(1);
    ((DefaultTableModel)tablePuntos.getModel()).getDataVector().removeAllElements();
    for(int i =0; i<Constantes.DEFAULT_NUMEBER_POINTS;i++)
    {
        model.addRow(new Double[]{(double)i,(double)i});
        /*model.setValueAt(i, i  , 0);
        model.setValueAt(i, i  , 1);*/
    }
    graficar();
}

private void generarEjemploParabola2()
{
    
    this.spinnerGrado.setEnabled(true);
    this.radioGrado.setSelected(true);
    this.spinnerGrado.setValue(2);
    this.spinnerCantPuntos.setValue(4);
    ((DefaultTableModel)tablePuntos.getModel()).getDataVector().removeAllElements();
    for(int i =0; i<Constantes.DEFAULT_NUMEBER_POINTS;i++)
    {
        model.addRow(new Double[]{(double)i,Math.pow(i, 2)});
        /*model.setValueAt(i, i  , 0);
        model.setValueAt(Math.pow(i, 2), i  , 1);*/
    }
    graficar();
}

private void generarEjemploParabola3()
{
    
    this.spinnerCantPuntos.setValue(4);
    this.spinnerGrado.setEnabled(true);
    this.radioGrado.setSelected(true);
    this.spinnerGrado.setValue(3);
    ((DefaultTableModel)tablePuntos.getModel()).getDataVector().removeAllElements();
    for(int i =0; i < Constantes.DEFAULT_NUMEBER_POINTS;i++)
    {
        model.addRow(new Double[]{(double)i,Math.pow(i, 3)});
        /*model.setValueAt(i, i  , 0);
        model.setValueAt(Math.pow(i, 3), i  , 1);*/
    }
    graficar();
}


private void generarEjemploExponencial()
{
    
    this.spinnerCantPuntos.setValue(4);
    this.radioExponencial.setSelected(true);
    this.spinnerGrado.setEnabled(false);
    ((DefaultTableModel)tablePuntos.getModel()).getDataVector().removeAllElements();
    for(int i =0; i<Constantes.DEFAULT_NUMEBER_POINTS;i++)
    {
        model.addRow(new Double[]{(double)i,Math.pow(3, i)});
        /*model.setValueAt(i, i  ,0);
        model.setValueAt(Math.pow(3, i), i  ,1);*/
    }
    graficar();
}

private void cmbEjemplosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEjemplosActionPerformed
    
    /*if(model.getRowCount() > 1)
    {
         for(int i = 0; i<model.getRowCount()-1;i++)
            model.removeRow(i);
        model.setValueAt(0, 0, 0);
        model.setValueAt(0, 0, 1);
    }*/
   
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
                if(cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Exponencial"))
                {
                    this.grado = "Exponencial";
                    generarEjemploExponencial();
                }
                else this.setPoints();
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
   /* data1=null;
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
    panelGrafico.repaint();*/
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
    private javax.swing.JCheckBox chkTruncar;
    private javax.swing.JComboBox cmbEjemplos;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelFuncion;
    private javax.swing.JLabel lblCantPuntos;
    private javax.swing.JLabel lblCoefRegr;
    private javax.swing.JLabel lblEjemplos;
    private javax.swing.JLabel lblErrorGlobal;
    private javax.swing.JLabel lblFuncReg;
    private javax.swing.JLabel lblLimInf;
    private javax.swing.JLabel lblLimSup;
    private javax.swing.JLabel lblSol;
    private javax.swing.JLabel lblTituloCoefReg;
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
