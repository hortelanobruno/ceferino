/*
 * MinimosCuadrados.java
 *
 * Created on 23 de agosto de 2008, 14:16
 */

package javaapplication1;

import com.welsungo.math.systemEqs.SistemaEcuaciones;
import function.FuncionExponencial;
import function.FuncionLineal;
import function.FuncionSegundoGrado;
import function.FuncionTercerGrado;
import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.function.PowerFunction2D;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

/**
 *
 * @author  Administrador
 */
public class MinimosCuadrados extends javax.swing.JApplet {

    private int cantPuntos;
    private int grados;
    private int ejexmin;
    private int ejexmax;
    private int ejeymin;
    private int ejeymax;
    private XYDataset data1;
    /** Initializes the applet MinimosCuadrados */
    public void init() {
        try {
            java.awt.EventQueue.invokeAndWait(new Runnable() {
                public void run() {
                    setLookAndFeel();
                    initComponents();
                    cantPuntos=0;
                    grados=1;
                    ejexmin=0;
                    ejexmax=10;
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

        jPanel2 = new javax.swing.JPanel();
        panelGrafico = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablePuntos = new javax.swing.JTable();
        buttonOK = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        spinnerCantPuntos = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        comboboxGrado = new javax.swing.JComboBox();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();

        setStub(null);

        jPanel2.setPreferredSize(new java.awt.Dimension(600, 600));

        panelGrafico.setLayout(new java.awt.GridLayout(0, 1));

        jLabel3.setFont(new java.awt.Font("Bookman Old Style", 3, 18));
        jLabel3.setText("ACA VA EL GRAFICO");
        panelGrafico.add(jLabel3);

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

        buttonOK.setText("Ok");
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

        jLabel2.setText("Grado");

        comboboxGrado.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Primero Grado", "Segundo Grado", "Tercer Grado", "Exponencial" }));
        comboboxGrado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxGradoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboboxGrado, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(buttonOK))
                        .addGap(107, 107, 107)
                        .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1)
                    .addComponent(spinnerCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spinnerCantPuntos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelGrafico, javax.swing.GroupLayout.PREFERRED_SIZE, 355, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(82, 82, 82)
                        .addComponent(jLabel2)
                        .addGap(43, 43, 43)
                        .addComponent(comboboxGrado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(buttonOK)))
                .addContainerGap(140, Short.MAX_VALUE))
        );

        jMenu1.setText("File");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 725, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }// </editor-fold>//GEN-END:initComponents

private void buttonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonOKActionPerformed
// TODO add your handling code here:
    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());
    
    double[][] a = new double[filas][grados + 1];
    double[][] b = new double[filas][1];
    double[][] at = new double[grados + 1][filas];
    double[][] atxb = new double[grados+1][1];
    double[][] atxa = new double[grados+1][grados+1];
    double[][] coef = new double[grados+1][grados+2];
    a = cargarValores();
    b = cargarB();
    at = trasponerMatriz(a,filas);
    atxa = multiplicarMatrices(at,a);
    atxb = multiplicarMatrices2(at,b);
    coef  = armarSistema(atxa,atxb);
    
    SistemaEcuaciones sisEcu = new SistemaEcuaciones();
    sisEcu.setNumEcs(grados+1);
    sisEcu.setCoef(coef);
    double[] result = sisEcu.getSolucion();
    
    String grado = ((DefaultComboBoxModel)comboboxGrado.getModel()).getSelectedItem().toString();
    if(grado.equalsIgnoreCase("Exponencial")){
        result = cambiarVariables(result);
    }
    String funcion = generarFuncion(result);
    
    //ACA JFREECHART
    
    data1 = createSampleData1();
    panelGrafico.removeAll();
    JTabbedPane jtabbedpane = new JTabbedPane();
    jtabbedpane.add("Linear", createChartPanel2(result));
    panelGrafico.add(jtabbedpane);
    //panelGrafico.add("HOLAAA",createChartPanel2(result));
    jtabbedpane.setVisible(true);
    jtabbedpane.repaint();
    panelGrafico.repaint();
    this.repaint();
    
    
    
}//GEN-LAST:event_buttonOKActionPerformed

    private XYDataset createSampleData1()
    {
        XYSeries xyseries = new XYSeries("Series 1");
        
        DefaultTableModel model = (DefaultTableModel)tablePuntos.getModel();
        for(int i = 0 ; i < model.getRowCount() ; i++){
            xyseries.add(Double.parseDouble(model.getValueAt(i, 0).toString()),Double.parseDouble(model.getValueAt(i, 1).toString()));
        }
        
//        xyseries.add(2D, 56.270000000000003D);
//        xyseries.add(3D, 41.32D);
//        xyseries.add(4D, 31.449999999999999D);
//        xyseries.add(5D, 30.050000000000001D);
//        xyseries.add(6D, 24.690000000000001D);
//        xyseries.add(7D, 19.780000000000001D);
//        xyseries.add(8D, 20.940000000000001D);
//        xyseries.add(9D, 16.73D);
//        xyseries.add(10D, 14.210000000000001D);
//        xyseries.add(11D, 12.44D);
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
        double ad[] = Regression.getPowerRegression(data1, 0);
        PowerFunction2D powerfunction2d = new PowerFunction2D(ad[0], ad[1]);
        //XYDataset xydataset = DatasetUtilities.sampleFunction2D(powerfunction2d, 2D, 11D, 100, "Fitted Regression Line");
        
        XYSeriesCollection coordenadas = cordenadasGrafico(result);
        
        
        XYLineAndShapeRenderer xylineandshaperenderer1 = new XYLineAndShapeRenderer(true, false);
        xylineandshaperenderer1.setSeriesPaint(0, Color.blue);
        //xyplot.setDataset(1, xydataset);
        xyplot.setDataset(1,coordenadas);
        xyplot.setRenderer(1, xylineandshaperenderer1);
        JFreeChart jfreechart = new JFreeChart("Power Regression", JFreeChart.DEFAULT_TITLE_FONT, xyplot, true);
        ChartPanel chartpanel = new ChartPanel(jfreechart, false);
        chartpanel.setVisible(true);
        return chartpanel;
    }
    
    
private String generarFuncion(double[] result){
    String funcion = new String();
    String grado = ((DefaultComboBoxModel)comboboxGrado.getModel()).getSelectedItem().toString();
    if(grado.equalsIgnoreCase("Exponencial")){
        funcion = "Y = "+result[0]+" e^"+result[1]+"X"; 
    }else{
        if(grados == 1){
            funcion = "Y = "+result[0]+" "+result[1]+"X";
        }else if(grados == 2){
            funcion = "Y = "+result[0]+" "+result[1]+"X "+result[2]+"X^2 ";
        }else{
            funcion = "Y = "+result[0]+" "+result[1]+"X "+result[2]+"X^2 "+result[3]+"X^3";
        }
    }
    return funcion;
}

private XYSeriesCollection cordenadasGrafico(double[] result){
    XYSeries series = new XYSeries("Fitted Regression Line");
    double step = (11D - 2D) / (100 - 1);//aca hay que ver los valores de 11F 2D y 100
    
    String grado = ((DefaultComboBoxModel)comboboxGrado.getModel()).getSelectedItem().toString();
    if(grado.equalsIgnoreCase("Exponencial")){
        for(int i = 0 ; i < 100 ; i++){
            FuncionExponencial exp = new FuncionExponencial(result[0], result[1]);
            double x = 2D + (step * i);
            series.add(x, exp.getValue(x));
        }
    }else{
        if(grados == 1){
            for(int i = 0 ; i < 100 ; i++){
                FuncionLineal lin = new FuncionLineal(result[0], result[1]);
                double x = 2D + (step * i);
                series.add(x,lin.getValue(x));
            }
        }else if(grados ==2){
            for(int i = 0 ; i < 100 ; i++){
                FuncionSegundoGrado seg = new FuncionSegundoGrado(result[0], result[1], result[2]);
                double x = 2D + (step * i);
                series.add(x,seg.getValue(x));
            }
        }else{
            for(int i = 0 ; i < 100 ; i++){
                FuncionTercerGrado ter = new FuncionTercerGrado(result[0], result[1], result[2], result[3]);
                double x = 2D + (step * i);
                series.add(x,ter.getValue(x));
            }
        }
    }
    XYSeriesCollection collection = new XYSeriesCollection(series);
    return collection;
}


private double[] cambiarVariables(double[] a){
    a[0] = Math.pow(Math.E, a[0]);
    return a;
}

private double[][] armarSistema(double[][] atxa, double[][] atxb){
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
}

private double[][] multiplicarMatrices2(double[][] at, double[][] b){
    double[][] atxb = new double[grados+1][1];

    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());


    for (int i=0; i < grados+1; i++)
    for (int j=0; j < 1; j++)
    {
        for (int p=0; p < filas; p++)
        atxb[i][j] += at[i][p]*b[p][j];
    }
    return atxb;
}

private double[][] multiplicarMatrices(double[][] at, double[][] a){
    double[][] atxa = new double[grados+1][grados+1];

    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());


    for (int i=0; i < grados+1; i++)
    for (int j=0; j < grados+1; j++)
    {
        for (int p=0; p < filas; p++)
        atxa[i][j] += at[i][p]*a[p][j];
    }
    return atxa;
}

private double[][] cargarB(){
    int filas = Integer.parseInt(spinnerCantPuntos.getValue().toString());
    double[][] b = new double[filas][1];
    DefaultTableModel model = (DefaultTableModel)tablePuntos.getModel();
    String grado = ((DefaultComboBoxModel)comboboxGrado.getModel()).getSelectedItem().toString();
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
    DefaultTableModel model = (DefaultTableModel)tablePuntos.getModel();
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

private void comboboxGradoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxGradoActionPerformed
// TODO add your handling code here:
    String grado = ((DefaultComboBoxModel)comboboxGrado.getModel()).getSelectedItem().toString();
    if(grado.equalsIgnoreCase("Primer grado")){
        grados = 1;
    }else if(grado.equalsIgnoreCase("Segundo grado")){
        grados = 2;
    }else if(grado.equalsIgnoreCase("Tercer grado")){
        grados = 3;
    }else if(grado.equalsIgnoreCase("Exponencial")){
        grados = 1;
    }else{
        System.out.println("ERROR EN LOS GRADOS");
    }
}//GEN-LAST:event_comboboxGradoActionPerformed


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
    private javax.swing.JButton buttonOK;
    private javax.swing.JComboBox comboboxGrado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panelGrafico;
    private javax.swing.JSpinner spinnerCantPuntos;
    private javax.swing.JTable tablePuntos;
    // End of variables declaration//GEN-END:variables

}
