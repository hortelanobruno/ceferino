/*
 * SistemasDinamicos.java
 *
 * Created on 17 de octubre de 2008, 20:36
 */
package applet;

import java.awt.Color;
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
    
    /*******************PARAMETROS********************************/
    
    private String funcion;
    private double hTiempo;
    private double h;
    private double tMax;
    private double tMin;
    private double xInicial;
    private double xFinal;
    private String fondoForm;
    private String fondoGrafico;    
    private String fontColor;
    
    
    /*******************FIN PARAMETROS********************************/
    
    /** Initializes the applet SistemasDinamicos */
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
                    initComponents();
                    initComponents2();
                    
                    setParser(new Parser(getInstance()));
                    rootFinder = new RootFinder(getInstance());
                    graficadorFuncion = new GraficadorFuncion(getInstance());
                    graficadorFases = new GraficadorFases(getInstance());
                    graficadorTvsX = new GraficadorTvsX(getInstance());           
                    
                    loadDefaultParams();
                    boolean okLoadParams = loadParams();
                    setLookAndFeel();
                         
                    if(okLoadParams)
                        setParams();
                }
            });
        } 
        catch (Exception ex) 
        {
            ex.printStackTrace();
        }
    }
    
    public SistemasDinamicos getInstance()
    {
        return this;
    }
    
    private void setParams()
    {
        if(this.funcion != null)
            this.txtFuncion.setText(this.funcion);
        
        if(this.hTiempo != 0)
            this.txtHTiempo.setText(String.valueOf(this.hTiempo));
        
        if(this.h != 0)
            this.txtH.setText(String.valueOf(this.h));
        
        if(this.tMin != 0)
            this.txtTiempoMin.setText(String.valueOf(this.tMin));
        
        if(this.tMax != 0)
            this.txtTiempoMax.setText(String.valueOf(this.tMax));
        
        if(this.xInicial != 0)
            this.txtXinicial.setText(String.valueOf(this.xInicial));
        
        if(this.xFinal != 0)
            this.txtXfinal.setText(String.valueOf(this.xFinal));
        
        if(!(this.fondoForm.equalsIgnoreCase("")))
        {
            int i = Integer.valueOf(fondoForm, 16).intValue();
            panelCentral.setBackground(new Color(i));
            jPanel4.setBackground(new Color(i));
            jLabel1.setBackground(new Color(i));
            jLabel10.setBackground(new Color(i));
            jLabel11.setBackground(new Color(i));
            jLabel2.setBackground(new Color(i));
            jLabel5.setBackground(new Color(i));
            jLabel7.setBackground(new Color(i));
            jLabel9.setBackground(new Color(i));
            buttonGraficar.setBackground(new Color(i));
        }
        
        if(!(this.fondoGrafico.equalsIgnoreCase("")))
        {
             int i = Integer.valueOf(fondoGrafico, 16).intValue();
             panelFvsT.setBackground(new Color(i));
             panelGrafico1.setBackground(new Color(i));
             panelGraficoFases.setBackground(new Color(i));
        }
        
        if(!(this.fontColor.equalsIgnoreCase("")))
        {
            int i = Integer.valueOf(fontColor, 16).intValue();
            jLabel1.setForeground(new Color(i));
            jLabel10.setForeground(new Color(i));
            jLabel11.setForeground(new Color(i));
            jLabel2.setForeground(new Color(i));
            jLabel5.setForeground(new Color(i));
            jLabel7.setForeground(new Color(i));
            jLabel9.setForeground(new Color(i));
        }
    }
    
    private void loadDefaultParams() 
    {
        this.hTiempo = 0;
        this.h = 0;
        this.tMax = 0;
        this.tMin =0;
        this.xFinal = 0;
        this.xInicial = 0;
        getTxtH().setText("0.01");
        getTxtXfinal().setText("5");
        getTxtXinicial().setText("-5");
        getTxtHTiempo().setText("0.01");
        getTxtTiempoMin().setText("-2");
        getTxtTiempoMax().setText("2");
        getTxtFuncion().setText("x^2-2");
        this.fondoForm = "";
        this.fondoGrafico = "";
        this.fontColor = "";
    }
    
    private boolean loadParams()
    {
        try
        {
            if(this.getParameter("funcion") != null)
                this.funcion = this.getParameter("funcion");
            
            if(this.getParameter("h") != null)
                this.h = Double.parseDouble(this.getParameter("h"));
            
            if(this.getParameter("hTiempo") != null)
                this.hTiempo = Double.parseDouble(this.getParameter("hTiempo"));
                        
            if(this.getParameter("tMax") != null)
                this.tMax = Double.parseDouble(this.getParameter("tMax"));
            
            if(this.getParameter("tMin") != null)
                this.tMin = Double.parseDouble(this.getParameter("tMin"));
            
            if(this.getParameter("xFinal") != null)
                this.xFinal = Double.parseDouble(this.getParameter("xFinal"));
                
            if(this.getParameter("xInicial") != null)
                this.xInicial = Double.parseDouble(this.getParameter("xInicial"));
                
            if(this.getParameter("fondoForm") != null)
                this.fondoForm = this.getParameter("fondoForm");
                
            if(this.getParameter("fondoGrafico") != null)
                this.fondoGrafico = this.getParameter("fondoGrafico");            
                
            if(this.getParameter("fontColor") != null)
                this.fontColor = this.getParameter("fontColor");
                            
            return true;
        }
        catch(Exception e)
        {
            System.out.println("Exxxxxxxx");
            return false;
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
        jLabel5 = new javax.swing.JLabel();
        txtTiempoMax = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtXinicial = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtXfinal = new javax.swing.JTextField();
        buttonGraficar = new javax.swing.JButton();
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

        jLabel5.setText("t max");

        jLabel9.setText("x-inicial");

        jLabel10.setText("x-final");

        buttonGraficar.setText("Graficar");
        buttonGraficar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGraficarActionPerformed(evt);
            }
        });

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
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel11))
                                .addGap(39, 39, 39)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtH)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtTiempoMax, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE))
                                    .addComponent(txtTiempoMin, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(21, 21, 21)
                                .addComponent(txtHTiempo)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel10))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtXinicial)
                                    .addComponent(txtXfinal, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(buttonGraficar)))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(txtFuncion, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                            .addComponent(jLabel9)
                            .addComponent(txtXinicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel10)
                            .addComponent(txtXfinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(buttonGraficar))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(txtH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtTiempoMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txtTiempoMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(txtHTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
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
                        .addGap(18, 18, 18)
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
        
        this.getTxtFuncion().setNextFocusableComponent(this.getTxtH());
        this.getTxtH().setNextFocusableComponent(this.getTxtTiempoMin());
        this.getTxtTiempoMin().setNextFocusableComponent(this.getTxtTiempoMax());
        this.getTxtTiempoMax().setNextFocusableComponent(this.getTxtHTiempo());
        this.getTxtHTiempo().setNextFocusableComponent(this.getTxtXinicial());
        this.getTxtXinicial().setNextFocusableComponent(this.getTxtXfinal());
        this.getTxtXfinal().setNextFocusableComponent(this.getButtonGraficar());
        this.getButtonGraficar().setNextFocusableComponent(this.getTxtFuncion());
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
        graficadorFases.graficarFases();
        graficadorTvsX.graficarTvsX();
    }
}//GEN-LAST:event_txtFuncionKeyPressed

private void buttonGraficarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonGraficarActionPerformed

    //if(this.parser.okParse(this.getTxtFuncion().getText()))
    //{
        vaciarTabbeds();
        graficadorFuncion.graficarFuncion();
        setRaices(rootFinder.findRoot(this.getTxtFuncion().getText(), Double.parseDouble(this.getTxtXinicial().getText()), Double.parseDouble(this.getTxtXfinal().getText()), 200));
        graficadorFases.graficarFases();
        graficadorTvsX.graficarTvsX();
   /* }
    else JOptionPane.showMessageDialog(this, "no se puede parsear");*/
}//GEN-LAST:event_buttonGraficarActionPerformed

private void txtFuncionCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFuncionCaretUpdate
    String newExpressionString = getTxtFuncion().getText();
    //getParser().agregarFuncion(newExpressionString);
    if(!(this.getParser().okParse(newExpressionString)))
    {
       this.getTxtFuncion().setForeground(Color.red);
       this.getButtonGraficar().setEnabled(false);
    }
    else
    {
        this.getTxtFuncion().setForeground(Color.black);
        this.getButtonGraficar().setEnabled(true);
    }
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedFases;
    private javax.swing.JTabbedPane jTabbedFvsT;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JPanel panelCentral;
    private javax.swing.JPanel panelFvsT;
    private javax.swing.JPanel panelGrafico1;
    private javax.swing.JPanel panelGraficoFases;
    private javax.swing.JTextField txtFuncion;
    private javax.swing.JTextField txtH;
    private javax.swing.JTextField txtHTiempo;
    private javax.swing.JTextField txtTiempoMax;
    private javax.swing.JTextField txtTiempoMin;
    private javax.swing.JTextField txtXfinal;
    private javax.swing.JTextField txtXinicial;
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
