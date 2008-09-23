package metodo;

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
import java.util.HashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import metodo.funciones.FuncionEnesima;
import metodo.funciones.FuncionExponencial;
import metodo.util.Constantes;
import metodo.util.Matriz;
import metodo.util.Vector;
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
    private int cantDecimales;
    private boolean truncar;
    private String param_Grado;
    private HashMap<String, double[][]> ejemplos;
    private int cantEjemplos;
    private String colorTrazo;
    
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
    public void init() 
    {
        try 
        {
            java.awt.EventQueue.invokeAndWait(new Runnable() 
            {
                @Override
                public void run() 
                {
                    setLookAndFeel();
                    initComponents();
                    cantPuntos = 0;
                    grados = 1;
                    radioGrado.setSelected(true);
                    fontSize = Constantes.DEFAULT_FONT_SIZE;
                    font = Constantes.DEFAULT_FONT;
                    xMax = Constantes.DEFAULT_XMax;
                    xMin = Constantes.DEFAULT_XMin;
                    idioma = Constantes.DEFAULT_LANGUAGE;
                    truncar = Constantes.DEFAULT_TRUNCATE;
                    model = (DefaultTableModel) tablePuntos.getModel();
                    ejemplos = new HashMap<String, double[][]>();
                    boolean ok = loadParams();
                    if (ok) {
                        setParams();
                    }
                    funcion = new FuncionEnesima();
                    funcExp = new FuncionExponencial();
                    if (cantEjemplos > 0) {
                        DefaultComboBoxModel model = (DefaultComboBoxModel) cmbEjemplos.getModel();
                        cmbEjemplos.setSelectedIndex(model.getSize() - 1);
                    } else {
                        cmbEjemplos.setSelectedIndex(1);
                    }
                    model.addTableModelListener(new TableModelListener() {

                        @Override
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
        this.lblFuncReg.setText("Regression function: ");
        this.lblLimInf.setText("X axis inferior limit");
        this.lblLimSup.setText("X axis superior limit");
        this.lblTituloCoefReg.setText("Regression coeficient (R2): ");
        this.lblTituloErrorGlobal.setText("Global error: ");
        this.btnClean.setText("Clear");
        this.buttonOK.setText("Plot");
        this.radioGrado.setText("Grade");
        this.chkTruncar.setText("Truncate");
        this.lblCantPuntos.setText("Number of points");
    }

    private void loadPoints() {
        
        if (this.truncar) {
            this.chkTruncar.setSelected(true);
        } else {
            this.chkTruncar.setSelected(false);
        }
        /* value = "(1.3,5);(3,4);(3,5.4)..... "  */
        int size = Integer.parseInt(this.getParameter("cantEjemplos"));
        String[] nombreEjemplos = new String[size];
        
        for (int i = 0; i < size; i++)
            nombreEjemplos[i] = this.getParameter("nombreEjemplo" + (i + 1));
        
        for (int i = 0; i < size; i++) 
        {
            String aux = this.getParameter("puntos" + (i + 1));
            String[] pares = aux.split(";"); /* pares [0] => (1.3,5)  pares[1] => (3,4) */
            double[][] points = new double[pares.length][2];
            for (int j = 0; j < pares.length; j++) 
            {
                pares[j] = pares[j].replace('(', ' '); // pares[0] => 1.3,5)
                pares[j] = pares[j].replace(')', ' '); // pares[0] => 1.3,5
                String[] xy = pares[j].split(","); // xy[0] => 1,3   xy[1] => 5
                points[j][0] = Double.valueOf(xy[0]); //points[i][0] => 1,3
                points[j][1] = Double.valueOf(xy[1]); //points[i][1] => 5
            }
            ejemplos.put(nombreEjemplos[i], points);
        }
    }

    private void setPoints() {
        String aux = cmbEjemplos.getSelectedItem().toString();
        double[][] points = ejemplos.get(aux);
        this.spinnerCantPuntos.setValue(points.length);
        for (int i = 0; i < points.length; i++) {
            this.model.setValueAt(points[i][0], i, 0);
            this.model.setValueAt(points[i][1], i, 1);
        }

        if (this.grado.equalsIgnoreCase("exponencial")) {
            this.radioExponencial.setSelected(true);
            this.radioGrado.setSelected(false);
        } else {
            this.radioExponencial.setSelected(false);
            this.radioGrado.setSelected(true);
            this.spinnerGrado.setValue(Integer.valueOf(this.grado));
        }
        this.graficar();
    }

    private void setParams() {
        
        if (xMax == 0) {
            txtFieldLimiteSupX.setText(String.valueOf(Constantes.DEFAULT_XMax));
        } else {
            txtFieldLimiteSupX.setText(String.valueOf(xMax));
        }
        if (xMin == 0) {
            txtFieldLimiteInfX.setText(String.valueOf(Constantes.DEFAULT_XMin));
        } else {
            txtFieldLimiteInfX.setText(String.valueOf(xMin));
        }
        if (!fondoForm.equalsIgnoreCase("")) {
            int i = Integer.valueOf(fondoForm, 16).intValue();
            jPanel2.setBackground(new Color(i));
            chkTruncar.setBackground(new Color(i));
            radioGrado.setBackground(new Color(i));
            radioExponencial.setBackground(new Color(i));
            jScrollPane1.setBackground(new Color(i));
            panelGrafico.setBackground(new Color(i));
            btnClean.setBackground(new Color(i));
            buttonOK.setBackground(new Color(i));
        }

        if (!fontColor.equalsIgnoreCase("")) {
            int i = Integer.valueOf(fontColor, 16).intValue();
            lblLimInf.setForeground(new Color(i));
            labelFuncion.setForeground(new Color(i));
            lblCantPuntos.setForeground(new Color(i));
            lblCoefRegr.setForeground(new Color(i));
            lblEjemplos.setForeground(new Color(i));
            lblErrorGlobal.setForeground(new Color(i));
            lblFuncReg.setForeground(new Color(i));
            lblLimSup.setForeground(new Color(i));
            lblSol.setForeground(new Color(i));
            lblTituloCoefReg.setForeground(new Color(i));
            lblTituloErrorGlobal.setForeground(new Color(i));
            chkTruncar.setForeground(new Color(i));
            radioExponencial.setForeground(new Color(i));
            radioGrado.setForeground(new Color(i));
        }

        lblLimInf.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        labelFuncion.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblCantPuntos.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblCoefRegr.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblEjemplos.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblErrorGlobal.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblFuncReg.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblLimSup.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblSol.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblTituloCoefReg.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        lblTituloErrorGlobal.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        chkTruncar.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        radioExponencial.setFont(new java.awt.Font(this.font, 1, this.fontSize));
        radioGrado.setFont(new java.awt.Font(this.font, 1, this.fontSize));

        if (this.idioma.equalsIgnoreCase("en")) {
            traducir();
        }
        if (this.param_Grado.equalsIgnoreCase("exp")) {
            this.grado = "Exponencial";
        } else {
            this.grado = this.param_Grado;
        }
        Object[] nomEjemplos = ejemplos.keySet().toArray();
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) cmbEjemplos.getModel();
        for (int i = 0; i < nomEjemplos.length; i++) {
            modelo.addElement(nomEjemplos[i].toString());
        }        
    }

    private boolean loadParams() 
    {
        try 
        {     
            if(this.getParameter("truncar") != null){
                this.truncar = Boolean.parseBoolean(this.getParameter("truncar"));
            }
            else{
                this.truncar = false;
            }
            
            if(this.getParameter("colorTrazo") != null){
                this.colorTrazo = this.getParameter("colorTrazo");
            }else{
                this.colorTrazo = "";
            }
            
            if (this.getParameter("fondoForm") != null) {
                this.fondoForm = this.getParameter("fondoForm");
            } else {
                this.fondoForm = "";
            }
            
            if (this.getParameter("fondoGrafico") != null) {
                this.fondoGrafico = this.getParameter("fondoGrafico");
            }
            
            if (this.getParameter("font") != null) {
                this.font = this.getParameter("font");
            } else {
                this.font = "";
            }
            
            if (this.getParameter("fontSize") != null) {
                this.fontSize = Integer.parseInt(this.getParameter("fontSize"));
            } else {
                this.fontSize = 0;
            }
            
            if (this.getParameter("fontColor") != null) {
                this.fontColor = this.getParameter("fontColor");
            } else {
                this.fontColor = "";
            }
            
            if (this.getParameter("anchoTrazo") != null) {
                this.anchoTrazoGrafico = Integer.valueOf(this.getParameter("anchoTrazo"));
            }
            
            if (this.getParameter("xMin") != null) {
                this.xMin = Integer.parseInt(this.getParameter("xMin"));
            }
            
            if (this.getParameter("xMax") != null) {
                this.xMax = Integer.parseInt(this.getParameter("xMax"));
            }
            
            if (this.getParameter("idioma") != null) {
                this.idioma = this.getParameter("idioma");
            }
            
            if (this.getParameter("colorEjes") != null) {
                this.colorEjes = this.getParameter("colorEjes");
            }
            
            if (this.getParameter("grado") != null) {
                this.param_Grado = this.getParameter("grado"); // grado = 1 o grado = exp
            }
            
            if (this.getParameter("cantEjemplos") != null) {
                this.cantEjemplos = Integer.parseInt(this.getParameter("cantEjemplos"));
            }
            
            if (this.getParameter("cantDecimales") != null) {
                this.cantDecimales = Integer.parseInt(this.getParameter("cantDecimales"));
            }
            
            if (this.getParameter("truncar") != null) {
                this.truncar = Boolean.parseBoolean(this.getParameter("truncar"));
            }
            
            if (cantEjemplos > 0) {
                loadPoints();
            }
            
            return true;
        } 
        catch (Exception e) 
        {
            return false;
        }
    }
    
    private boolean okGraficar()
    {
        int aux2 = model.getRowCount();
        boolean bool = true;
        if (aux2 > 1) 
        {
            for (int i = 0; i < aux2; i++) 
            {
                Object aux3 = model.getValueAt(i, 0);
                Object aux4 = model.getValueAt(i, 1);

                if ((aux3 == null) || (aux4 == null)) bool = false;
            }
            if (bool)  return true;
            else return false;
        }
        return false;
    }

    private void graficar() 
    {
        if(okGraficar())
        {
            int filas = model.getRowCount();

            if (spinnerGrado.isEnabled()) {
                grados = (Integer) spinnerGrado.getValue();
                if (grados >= filas) {
                    grados = filas - 1;
                }
            } else {
                grados = 1;
            }

            if (spinnerGrado.isEnabled()) {
                this.grado = spinnerGrado.getValue().toString();
            } else {
                this.grado = "Exponencial";
            }

            double[][] a = new double[filas][grados + 1];
            double[][] b = new double[filas][1];
            double[][] at = new double[grados + 1][filas];
            double[] atxb = new double[grados + 1];
            double[][] atxa = new double[grados + 1][grados + 1];

            a = cargarValores();
            b = cargarB();
            at = trasponerMatriz(a, filas);
            atxa = multiplicarMatrices(at, a);
            atxb = multiplicarMatrices2(at, b);      

            Matriz alfas = new Matriz(atxa);
            Vector res = new Vector(atxb);
            Vector resultSist = Matriz.producto(Matriz.inversa(alfas), res);

            double[] result = resultSist.x;

            if (chkTruncar.isSelected()) {
                DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(this.cantDecimales);

                for (int i = 0; i < result.length; i++) 
                {
                    String aux = df.format(result[i]);
                    aux = aux.replace(',', '.');
                    result[i] = Double.parseDouble(aux);
                }
            }

            if (this.grado.equalsIgnoreCase("Exponencial")) {
                result = cambiarVariables(result);
            }

            data1 = createSampleData1();
            panelGrafico.removeAll();
            jtabbedpane = new JTabbedPane();

            if(this.idioma.equalsIgnoreCase("ES")) jtabbedpane.add("Regresión", createChartPanel2(result));
            else jtabbedpane.add("Regression", createChartPanel2(result));

            panelGrafico.add(jtabbedpane);
            labelFuncion.setText(generarFuncion(result));
            jtabbedpane.setVisible(true);
            jtabbedpane.repaint();
            panelGrafico.repaint();
            this.repaint();

            if (hayMuchasSoluciones(result)) 
            {
                if(this.idioma.equalsIgnoreCase("ES")) JOptionPane.showMessageDialog(this, "Matriz Singular","Mínimos Cuadrados",JOptionPane.ERROR_MESSAGE);
                else JOptionPane.showMessageDialog(this, "Singular Matrix","Minimal Squares",JOptionPane.ERROR_MESSAGE);
                labelFuncion.setText("");
            } else {
                lblErrorGlobal.setText("");
                lblCoefRegr.setText("");
                calcularErrores();
                lblSol.setText("");
            }
        }
        else
        {
            if(this.idioma.equalsIgnoreCase("ES")) JOptionPane.showMessageDialog(this, "No hay suficientes puntos.\nDebe completar toda la tabla.","Mínimos Cuadrados",JOptionPane.ERROR_MESSAGE);
            else JOptionPane.showMessageDialog(this, "No enough points.\nYou must fill the whole table.","Minimal Squares",JOptionPane.ERROR_MESSAGE);
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
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTituloCoefReg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCoefRegr, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblTituloErrorGlobal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblErrorGlobal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(lblFuncReg)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelFuncion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 585, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(lblSol, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
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
                    .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(42, 42, 42)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblFuncReg)
                    .addComponent(labelFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(27, 27, 27)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblErrorGlobal, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTituloErrorGlobal))
                .addGap(29, 29, 29)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(lblCoefRegr, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTituloCoefReg))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSol, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        getContentPane().add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private boolean hayMuchasSoluciones(double[] result) {
        for (int i = 0; i < result.length; i++) {
            if (String.valueOf(result[i]).equalsIgnoreCase("nan")) {
                return true;
            }
        }
        return false;
    }

    private double calcularErrorGlobal() {
        double err = 0;
        for (int i = 0; i < this.model.getRowCount(); i++) {
            if (radioGrado.isSelected()) {
                err += Math.pow(funcion.getValue(Double.parseDouble(model.getValueAt(i, 0).toString())) - Double.parseDouble(model.getValueAt(i, 1).toString()), 2);
            } else {
                err += Math.pow(funcExp.getValue(Double.parseDouble(model.getValueAt(i, 0).toString())) - Double.parseDouble(model.getValueAt(i, 1).toString()), 2);
            }
        }
        return err;
    }

    private double calcularProm(int index) {
        double sum = 0;
        for (int i = 0; i < this.model.getRowCount(); i++) {
            sum += Double.parseDouble(model.getValueAt(i, index).toString());
        }
        return (sum / this.model.getRowCount());
    }

    private double calcularSumaXY() {
        double sum = 0;
        for (int i = 0; i < this.model.getRowCount(); i++) {
            sum += Double.parseDouble(model.getValueAt(i, 0).toString()) * Double.parseDouble(model.getValueAt(i, 1).toString());
        }
        return sum;
    }

    private double calcularSuma2(int index) {
        double sum = 0;
        for (int i = 0; i < this.model.getRowCount(); i++) {
            sum += Math.pow(Double.parseDouble(model.getValueAt(i, index).toString()), 2);
        }
        return sum;
    }

    private double calcularCoeficienteRegresion() {
        double denominador = 0;
        double erGlo = this.calcularErrorGlobal();
        double promY = this.calcularProm(1);
        for (int i = 0; i < this.model.getRowCount(); i++) {
            denominador += Math.pow(Double.parseDouble(model.getValueAt(i, 1).toString()) - promY, 2);
        }
        return (1 - (erGlo / denominador));
    }

    private void calcularErrores() {
        double erGlo = this.calcularErrorGlobal();
        if (chkTruncar.isSelected()) {
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(this.cantDecimales);
            String aux = df.format(erGlo);
            aux = aux.replace(',', '.');
            erGlo = Double.parseDouble(aux);
            lblErrorGlobal.setText(String.valueOf(erGlo));
            double cr = this.calcularCoeficienteRegresion();
            String aux2 = df.format(cr);
            aux2 = aux2.replace(',', '.');
            cr = Double.parseDouble(aux2);
            lblCoefRegr.setText(String.valueOf(aux2));
        } else {
            lblErrorGlobal.setText(String.valueOf(erGlo));
            lblCoefRegr.setText(String.valueOf(this.calcularCoeficienteRegresion()));
        }
    }
private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
// TODO add your handling code here:
            graficar();
}//GEN-LAST:event_buttonOKActionPerformed

    private void tableListener(TableModelEvent e) {
        if (e.getColumn() > -1) {
            int col = e.getColumn();
            boolean flag = false;

            if (col == 0) {
                if ((model.getValueAt(e.getLastRow(), col) != null) &&
                        (model.getValueAt(e.getLastRow(), col + 1)) != null) {
                    flag = true;
                }
            } else {
                if ((model.getValueAt(e.getLastRow(), col - 1) != null) &&
                        (model.getValueAt(e.getLastRow(), col)) != null) {
                    flag = true;
                }
            }

            if (flag) 
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
                JFreeChart jfreechart = null;
                
                if(this.idioma.equalsIgnoreCase("ES")) jfreechart = new JFreeChart("Regresión ", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
                else jfreechart = new JFreeChart("Regression ", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
                
                ChartPanel chartpanel = new ChartPanel(jfreechart, false);
                chartpanel.setVisible(true);
                
                if(this.idioma.equalsIgnoreCase("ES")) jtabbedpane.add("Regresión", chartpanel);
                else jtabbedpane.add("Regression", chartpanel);
                
                jtabbedpane.setVisible(true);
                jtabbedpane.repaint();
                panelGrafico.repaint();
               
                if(okGraficar()) graficar();
            }
        }
    }

    private XYDataset createSampleData1() {
        XYSeries xyseries = new XYSeries("Series 1");

        for (int i = 0; i < model.getRowCount(); i++) {
            if ((model.getValueAt(i, 0) != null) && (model.getValueAt(i, 1) != null)) {
                xyseries.add(Double.parseDouble(model.getValueAt(i, 0).toString()), Double.parseDouble(model.getValueAt(i, 1).toString()));
            }
        }
        XYSeriesCollection xyseriescollection = new XYSeriesCollection(xyseries);
        return xyseriescollection;
    }

    private ChartPanel createChartPanel2(double[] result) {
        NumberAxis numberaxis = new NumberAxis("X");
        numberaxis.setAutoRangeIncludesZero(false);
        NumberAxis numberaxis1 = new NumberAxis("Y");
        numberaxis1.setAutoRangeIncludesZero(false);
        XYLineAndShapeRenderer xylineandshaperenderer = new XYLineAndShapeRenderer(false, true);
        XYPlot xyplot = new XYPlot(data1, numberaxis, numberaxis1, xylineandshaperenderer);

        if (!fondoGrafico.equalsIgnoreCase("")) {
            int i = Integer.valueOf(fondoGrafico, 16).intValue();
            xyplot.setBackgroundPaint(new Color(i));
        }
        xyplot.setDomainZeroBaselineVisible(true);
        xyplot.setRangeZeroBaselineVisible(true);
        XYSeriesCollection coordenadas = cordenadasGrafico(result);
        XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, false);
        
        if (!colorTrazo.equalsIgnoreCase("")) {
            int i = Integer.valueOf(colorTrazo, 16).intValue();
            xylineandshaperenderer1.setSeriesPaint(0, new Color(i));
        } else {
            xylineandshaperenderer1.setSeriesPaint(0, Color.black);
        }

        xyplot.setDataset(1, coordenadas);
        xyplot.setRenderer(1, xylineandshaperenderer1);
        if (idioma.equalsIgnoreCase("es")) {
            JFreeChart jfreechart = new JFreeChart("Regresión " + ((spinnerGrado.isEnabled()) ? "de grado " + spinnerGrado.getValue().toString() : "Exponencial"), JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
            ChartPanel chartpanel = new ChartPanel(jfreechart, false);
            chartpanel.setVisible(true);
            return chartpanel;
        } else {
            JFreeChart jfreechart = new JFreeChart(((spinnerGrado.isEnabled()) ? spinnerGrado.getValue().toString() + " grade regression" : "Exponencial regression"), JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
            ChartPanel chartpanel = new ChartPanel(jfreechart, false);
            chartpanel.setVisible(true);
            return chartpanel;
        }
    }

    private String generarFuncion(double[] result) {
        String func = new String();

        if (spinnerGrado.isEnabled()) {
            grado = spinnerGrado.getValue().toString();
        } else {
            grado = "Exponencial";
        }
        if (grado.equalsIgnoreCase("Exponencial")) {
            func = "Y = " + result[0] + " e^" + result[1] + "X";
        } else {
            func = this.funcion.getFunctionString();
        }
        return func;
    }

    private XYSeriesCollection cordenadasGrafico(double[] result) {
        
        XYSeries series;
        if(this.idioma.equalsIgnoreCase("ES")) series = new XYSeries("Curva de Regresión");
        else series = new XYSeries("Regression Curve");
        
        double step = (Double.parseDouble(txtFieldLimiteSupX.getText()) - Double.parseDouble(txtFieldLimiteInfX.getText())) / (100 - 1);

        if (spinnerGrado.isEnabled()) {
            grado = spinnerGrado.getValue().toString();
        } else {
            grado = "Exponencial";
        }
        if (grado.equalsIgnoreCase("Exponencial")) {
            for (int i = 0; i < 100; i++) {
                this.funcExp = new FuncionExponencial(result[0], result[1]);
                double x = Double.parseDouble(txtFieldLimiteInfX.getText()) + (step * i);
                series.add(x, funcExp.getValue(x));
            }
        } else {
            ArrayList<Double> coeficientes = new ArrayList();

            for (int i = 0; i < result.length; i++) {
                coeficientes.add(result[i]);
            }
            this.funcion = new FuncionEnesima(coeficientes, grados);

            for (int i = 0; i < 100; i++) {
                double x = Double.parseDouble(txtFieldLimiteInfX.getText()) + (step * i);
                series.add(x, funcion.getValue(x));
            }
        }
        XYSeriesCollection collection = new XYSeriesCollection(series);
        return collection;
    }

    private double[] cambiarVariables(double[] a) {
        a[0] = Math.pow(Math.E, a[0]);
        return a;
    }

    private double[] multiplicarMatrices2(double[][] at, double[][] b) {
        double[] atxb = new double[grados + 1];

        int filas = model.getRowCount();

        for (int i = 0; i < grados + 1; i++) 
        {
            for (int p = 0; p < filas; p++) 
            {
                atxb[i] += at[i][p] * b[p][0];
            }
        }
        return atxb;
    }

    private double[][] multiplicarMatrices(double[][] at, double[][] a) {
        double[][] atxa = new double[grados + 1][grados + 1];

        int filas = model.getRowCount();

        for (int i = 0; i < grados + 1; i++) 
        {
            for (int j = 0; j < grados + 1; j++) 
            {
                for (int p = 0; p < filas; p++) 
                {
                    atxa[i][j] += at[i][p] * a[p][j];
                }
            }
        }
        return atxa;
    }

    private double[][] cargarB() {
        int filas = model.getRowCount();
        double[][] b = new double[filas][1];
        model = (DefaultTableModel) tablePuntos.getModel();

        if (spinnerGrado.isEnabled()) {
            grado = spinnerGrado.getValue().toString();
        } else {
            grado = "Exponencial";
        }
        if (grado.equalsIgnoreCase("Exponencial")) {
            for (int i = 0; i < filas; i++) {
                b[i][0] = Math.log(Double.parseDouble(model.getValueAt(i, 1).toString()));
            }
        } else {
            for (int i = 0; i < filas; i++) {
                b[i][0] = Double.parseDouble(model.getValueAt(i, 1).toString());
            }
        }
        return b;
    }

    private double[][] cargarValores() {
        int filas = model.getRowCount();
        
        double[][] a = new double[filas][grados + 1];

        for (int j = 0; j < model.getRowCount(); j++) {
            a[j][0] = 1;
            for (int i = 1; i < grados + 1; i++) {
                a[j][i] = Math.pow(Double.parseDouble(model.getValueAt(j, 0).toString()), i);
            }
        }
        return a;
    }

    private double[][] trasponerMatriz(double[][] a, int filas) {
        double[][] at = new double[grados + 1][filas];
        for (int i = 0; i < grados + 1; i++) {
            for (int j = 0; j < filas; j++) {
                at[i][j] = a[j][i];
            }
        }
        return at;
    }
private void spinnerCantPuntosStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spinnerCantPuntosStateChanged
    // TODO add your handling code here:
    if (Integer.parseInt(spinnerCantPuntos.getValue().toString()) < cantPuntos) {
        int aux = cantPuntos - Integer.parseInt(spinnerCantPuntos.getValue().toString());
        for (int i = 0; i < aux; i++) {
            ((DefaultTableModel) tablePuntos.getModel()).removeRow(tablePuntos.getRowCount() - 1);
        }
        cantPuntos = Integer.parseInt(spinnerCantPuntos.getValue().toString());
        int aux2 = model.getRowCount();
        boolean bool = true;
        if (aux2 > 1) {
            for (int i = 0; i < aux2; i++) {
                Object aux3 = model.getValueAt(i, 0);
                Object aux4 = model.getValueAt(i, 1);
                if ((aux3 == null) || (aux4 == null)) {
                    bool = false;
                }
            }
            if (bool) {
                graficar();
            }
        }
    } else {
        int aux = Integer.parseInt(spinnerCantPuntos.getValue().toString()) - cantPuntos;
        for (int i = 0; i < aux; i++) {
            ((DefaultTableModel) tablePuntos.getModel()).addRow(new Double[]{});
        }
        cantPuntos = Integer.parseInt(spinnerCantPuntos.getValue().toString());
    }
}//GEN-LAST:event_spinnerCantPuntosStateChanged

private void radioGradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioGradoActionPerformed
    if (radioGrado.isSelected()) {
        spinnerGrado.setEnabled(true);
    } else {
        spinnerGrado.setEnabled(false);
    }
}//GEN-LAST:event_radioGradoActionPerformed

private void radioExponencialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioExponencialActionPerformed
    if (radioExponencial.isSelected()) {
        spinnerGrado.setEnabled(false);
    } else {
        spinnerGrado.setEnabled(true);
    }
}//GEN-LAST:event_radioExponencialActionPerformed

    private void generarEjemploRecta() {

        this.spinnerCantPuntos.setValue(4);
        this.spinnerGrado.setEnabled(true);
        this.radioGrado.setSelected(true);
        this.spinnerGrado.setValue(1);
        ((DefaultTableModel) tablePuntos.getModel()).getDataVector().removeAllElements();
        for (int i = 0; i < Constantes.DEFAULT_NUMEBER_POINTS; i++) {
            model.addRow(new Double[]{(double) i, (double) i});
        }
        graficar();
    }

    private void generarEjemploParabola2() {

        this.spinnerGrado.setEnabled(true);
        this.radioGrado.setSelected(true);
        this.spinnerGrado.setValue(2);
        this.spinnerCantPuntos.setValue(4);
        ((DefaultTableModel) tablePuntos.getModel()).getDataVector().removeAllElements();
        for (int i = 0; i < Constantes.DEFAULT_NUMEBER_POINTS; i++) {
            model.addRow(new Double[]{(double) i, Math.pow(i, 2)});
        }
        graficar();
    }

    private void generarEjemploParabola3() {

        this.spinnerCantPuntos.setValue(4);
        this.spinnerGrado.setEnabled(true);
        this.radioGrado.setSelected(true);
        this.spinnerGrado.setValue(3);
        ((DefaultTableModel) tablePuntos.getModel()).getDataVector().removeAllElements();
        for (int i = 0; i < Constantes.DEFAULT_NUMEBER_POINTS; i++) {
            model.addRow(new Double[]{(double) i, Math.pow(i, 3)});
        }
        graficar();
    }

    private void generarEjemploExponencial() {

        this.spinnerCantPuntos.setValue(4);
        this.radioExponencial.setSelected(true);
        this.spinnerGrado.setEnabled(false);
        ((DefaultTableModel) tablePuntos.getModel()).getDataVector().removeAllElements();
        for (int i = 0; i < Constantes.DEFAULT_NUMEBER_POINTS; i++) {
            model.addRow(new Double[]{(double) i, Math.pow(3, i)});
        }
        graficar();
    }

private void cmbEjemplosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmbEjemplosActionPerformed

    if (cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Recta")) {
        this.grado = "1";
        generarEjemploRecta();
    } else {
        if (cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Parabola 2º Grado")) {
            this.grado = "2";
            generarEjemploParabola2();
        } else {
            if (cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Parabola 3º Grado")) {
                this.grado = "3";
                generarEjemploParabola3();
            } else {
                if (cmbEjemplos.getSelectedItem().toString().equals("Ejemplo Exponencial")) {
                    this.grado = "Exponencial";
                    generarEjemploExponencial();
                } else {
                    setPoints();
                }
            }
        }
    }
}//GEN-LAST:event_cmbEjemplosActionPerformed

    private void cleanComponentes() {
        this.spinnerGrado.setEnabled(true);
        this.radioExponencial.setSelected(false);
        this.radioGrado.setSelected(true);
        this.spinnerCantPuntos.setValue(1);
        this.spinnerGrado.setValue(1);
        this.lblCoefRegr.setText("");
        this.lblErrorGlobal.setText("");
        this.labelFuncion.setText("");

        for (int i = 0; i < model.getRowCount() - 1; i++) {
            model.removeRow(i);
        }
        model.setValueAt(null, 0, 0);
        model.setValueAt(null, 0, 1);
    }

    private void limpiar() {
        cleanComponentes();
        data1 = null;
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
        
        JFreeChart jfreechart = null;
        
        if(this.idioma.equalsIgnoreCase("ES")){
            jfreechart = new JFreeChart("Regresión", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        }else{
            jfreechart = new JFreeChart("Regression", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        }
        ChartPanel chartpanel = new ChartPanel(jfreechart, false);
        chartpanel.setVisible(true);
        
        if(this.idioma.equalsIgnoreCase("ES")) jtabbedpane.add("Regresión", chartpanel);
        else jtabbedpane.add("Regression", chartpanel);
        
        jtabbedpane.setVisible(true);
        jtabbedpane.repaint();
        panelGrafico.repaint();
    }

private void btnCleanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCleanActionPerformed
    limpiar();
}//GEN-LAST:event_btnCleanActionPerformed

private void txtFieldLimiteSupXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldLimiteSupXKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        graficar();
    }
}//GEN-LAST:event_txtFieldLimiteSupXKeyPressed

private void txtFieldLimiteInfXKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFieldLimiteInfXKeyPressed
    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        graficar();
    }
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