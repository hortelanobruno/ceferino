/*
 * SistemasDinamicos.java
 *
 * Created on 17 de octubre de 2008, 20:36
 */
package applet;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import parser.Parser;

/**
 *
 * @author  Administrador
 */
public class SistemasDinamicos extends javax.swing.JApplet {

    private static final long serialVersionUID = 327575554503844280L;
    private Parser parser;
    private double[] raices;
    private RootFinder rootFinder;
    private GraficadorFuncion graficadorFuncion;
    private GraficadorFases graficadorFases;
    private GraficadorTvsX graficadorTvsX;

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
                    spinnerDecimales.setValue(10);
                    getTxtH().setText("0.01");
                    getTxtXfinal().setText("5");
                    getTxtXinicial().setText("-5");
                    getTxtCorte().setText("0.0001");
                    getTxtX0().setText("0");
                    getTxtY0().setText("1");
                    getTxtHTiempo().setText("0.2");
                    getTxtTiempoMin().setText("0");
                    getTxtTiempoMax().setText("0.6");
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
        txtTiempoMax = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtXinicial = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtXfinal = new javax.swing.JTextField();
        buttonGraficar = new javax.swing.JButton();
        spinnerDecimales = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();
        txtCorte = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtHTiempo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtTiempoMin = new javax.swing.JTextField();
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
        });

        jLabel2.setText("h= ");

        jLabel3.setText("X0= ");

        jLabel4.setText("t0= ");

        jLabel5.setText("t max");

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

        jLabel6.setText("Corte");

        jLabel7.setText("h tiempo");

        jLabel11.setText("t min");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(txtCorte, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                                                    .addComponent(txtTiempoMax, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)))))
                                    .addComponent(jLabel6))
                                .addGap(37, 37, 37)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addGap(39, 39, 39)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtY0)
                                            .addComponent(txtX0, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel10))
                                        .addGap(27, 27, 27)
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtXinicial)
                                            .addComponent(txtXfinal, javax.swing.GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)))))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(txtFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel11))
                        .addGap(21, 21, 21)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtTiempoMin, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
                            .addComponent(txtHTiempo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                        .addGap(55, 55, 55)
                        .addComponent(buttonGraficar)
                        .addGap(39, 39, 39))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(txtXfinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6)
                            .addComponent(txtCorte, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
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
                            .addComponent(txtTiempoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(buttonGraficar)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtHTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtTiempoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
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
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelGraficoFases, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFvsT, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE)
                    .addComponent(panelGrafico1, javax.swing.GroupLayout.DEFAULT_SIZE, 446, Short.MAX_VALUE))
                .addContainerGap())
        );
        panelCentralLayout.setVerticalGroup(
            panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCentralLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCentralLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(panelGrafico1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelFvsT, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCentralLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(panelGraficoFases, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCentral, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelCentral, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    public void initComponents2() {
        getTxtFuncion().setBackground(java.awt.Color.white);
        getTxtFuncion().setFont(new java.awt.Font("Dialog", 0, 11));
        getTxtFuncion().setForeground(java.awt.Color.black);

        setParser(new Parser(this));

        rootFinder = new RootFinder(this);
        graficadorFuncion = new GraficadorFuncion(this);
        graficadorFases = new GraficadorFases(this);
        graficadorTvsX = new GraficadorTvsX(this);
    }

    public String getDecimales() {
        String ret = "0.";
        int spin = (Integer) this.spinnerDecimales.getValue();

        for (int i = 0; i < spin; i++) {
            ret += "0";
        }
        return ret;
    }

    private void vaciarTabbeds() {
        getJTabbedFases().removeAll();
        getJTabbedFvsT().removeAll();
        getJTabbedPane1().removeAll();
    }

private void txtFuncionKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFuncionKeyPressed

    if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
        vaciarTabbeds();
        graficadorFuncion.graficarFuncion();
        setRaices(rootFinder.findRoot(this.getTxtFuncion().getText(), Double.parseDouble(this.getTxtXinicial().getText()), Double.parseDouble(this.getTxtXfinal().getText()), 200));

        if (!(raices.length == 0)) {
            graficadorFases.graficarFases();
            graficadorTvsX.graficarTvsX();
        }
    }
}//GEN-LAST:event_txtFuncionKeyPressed

private void buttonGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGraficarActionPerformed

    vaciarTabbeds();
    graficadorFuncion.graficarFuncion();
    setRaices(rootFinder.findRoot(this.getTxtFuncion().getText(), Double.parseDouble(this.getTxtXinicial().getText()), Double.parseDouble(this.getTxtXfinal().getText()), 200));

    if (!(raices.length == 0)) {
        graficadorFases.graficarFases();
        graficadorTvsX.graficarTvsX();
    }
}//GEN-LAST:event_buttonGraficarActionPerformed

private void txtFuncionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFuncionCaretUpdate
    String newExpressionString = getTxtFuncion().getText();
    getParser().agregarFuncion(newExpressionString);
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
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
    private javax.swing.JTextField txtCorte;
    private javax.swing.JTextField txtFuncion;
    private javax.swing.JTextField txtH;
    private javax.swing.JTextField txtHTiempo;
    private javax.swing.JTextField txtTiempoMax;
    private javax.swing.JTextField txtTiempoMin;
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

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public javax.swing.JTextField getTxtCorte() {
        return txtCorte;
    }

    public void setTxtCorte(javax.swing.JTextField txtCorte) {
        this.txtCorte = txtCorte;
    }

    public javax.swing.JTextField getTxtH() {
        return txtH;
    }

    public void setTxtH(javax.swing.JTextField txtH) {
        this.txtH = txtH;
    }

    public javax.swing.JTextField getTxtXinicial() {
        return txtXinicial;
    }

    public void setTxtXinicial(javax.swing.JTextField txtXinicial) {
        this.txtXinicial = txtXinicial;
    }

    public javax.swing.JTextField getTxtXfinal() {
        return txtXfinal;
    }

    public void setTxtXfinal(javax.swing.JTextField txtXfinal) {
        this.txtXfinal = txtXfinal;
    }

    public javax.swing.JPanel getPanelGrafico1() {
        return panelGrafico1;
    }

    public void setPanelGrafico1(javax.swing.JPanel panelGrafico1) {
        this.panelGrafico1 = panelGrafico1;
    }

    public javax.swing.JTabbedPane getJTabbedPane1() {
        return jTabbedPane1;
    }

    public void setJTabbedPane1(javax.swing.JTabbedPane jTabbedPane1) {
        this.jTabbedPane1 = jTabbedPane1;
    }

    public javax.swing.JTabbedPane getJTabbedFases() {
        return jTabbedFases;
    }

    public void setJTabbedFases(javax.swing.JTabbedPane jTabbedFases) {
        this.jTabbedFases = jTabbedFases;
    }

    public javax.swing.JPanel getPanelGraficoFases() {
        return panelGraficoFases;
    }

    public void setPanelGraficoFases(javax.swing.JPanel panelGraficoFases) {
        this.panelGraficoFases = panelGraficoFases;
    }

    public double[] getRaices() {
        return raices;
    }

    public void setRaices(double[] raices) {
        this.raices = raices;
    }

    public javax.swing.JTabbedPane getJTabbedFvsT() {
        return jTabbedFvsT;
    }

    public void setJTabbedFvsT(javax.swing.JTabbedPane jTabbedFvsT) {
        this.jTabbedFvsT = jTabbedFvsT;
    }

    public javax.swing.JPanel getPanelFvsT() {
        return panelFvsT;
    }

    public void setPanelFvsT(javax.swing.JPanel panelFvsT) {
        this.panelFvsT = panelFvsT;
    }

    public javax.swing.JTextField getTxtHTiempo() {
        return txtHTiempo;
    }

    public void setTxtHTiempo(javax.swing.JTextField txtHTiempo) {
        this.txtHTiempo = txtHTiempo;
    }

    public javax.swing.JTextField getTxtX0() {
        return txtX0;
    }

    public void setTxtX0(javax.swing.JTextField txtX0) {
        this.txtX0 = txtX0;
    }

    public javax.swing.JTextField getTxtY0() {
        return txtY0;
    }

    public void setTxtY0(javax.swing.JTextField txtY0) {
        this.txtY0 = txtY0;
    }

    public javax.swing.JTextField getTxtTiempoMin() {
        return txtTiempoMin;
    }

    public void setTxtTiempoMin(javax.swing.JTextField txtTiempoMin) {
        this.txtTiempoMin = txtTiempoMin;
    }

    public javax.swing.JTextField getTxtTiempoMax() {
        return txtTiempoMax;
    }

    public void setTxtTiempoMax(javax.swing.JTextField txtTiempoMax) {
        this.txtTiempoMax = txtTiempoMax;
    }

}
