/*
 * Created on 22-feb-2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.welsungo.math.systemEqs;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;

import com.welsungo.math.systemEqs.exceptions.NoUniqueSolutionException;

/**
 * @author felipe
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AppSystemEqs extends Applet {
	private boolean mHayComa;
	private Label labNumEcs = null;	
	private Label labEc = null;
	private TextField txtNumEcs = null;
	private TextField txtCoef = null;
	private Label labCoef = null;
	private Button btnOk = null;
	private Button btnNext = null;
	private Panel panSol = null;
	private double[][] mCoef;
	private int mNumEcs;
	private Button btnCalcular = null;

	/**
	 * This is the default constructor
	 */
	public AppSystemEqs() {
		super();
		init();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	public void init() {
		this.setLayout(null);
		this.add(getLabCoef(), null);
		this.add(getTxtNumEcs(), null);
		this.add(getBtnOk(), null);
		this.add(getTxtCoef(), null);
		this.add(getLabEc(), null);
		this.add(getLabNumEcs(), null);
		this.add(getBtnNext(), null);
		this.add(getBtnCalcular(), null);
		this.add(getPanSol(), null);
		this.setBackground(new Color(154,169,194));
		this.setSize(364, 300);
	}
	/**
	 * This method initializes labNumEcs
	 * 
	 * @return Label
	 */
	private Label getLabNumEcs() {
		if(labNumEcs == null) {
			labNumEcs = new Label();
			labNumEcs.setBounds(36, 36, 139, 20);
			labNumEcs.setText("Número de ecuaciones");
		}
		return labNumEcs;
	}
	/**
	 * This method initializes labEc
	 * 
	 * @return Label
	 */
	private Label getLabEc() {
		if(labEc == null) {
			labEc = new Label();
			labEc.setBounds(37, 78, 74, 23);
			labEc.setText("Ecuación 1");
			labEc.setVisible(false);
		}
		return labEc;
	}
	/**
	 * This method initializes txtNumEcs
	 * 
	 * @return TextField
	 */
	private TextField getTxtNumEcs() {
		if(txtNumEcs == null) {
			txtNumEcs = new TextField();
			txtNumEcs.setBounds(180, 36, 36, 20);
			txtNumEcs.addKeyListener(new KeyListener() { 
				public void keyTyped(KeyEvent e) {
					char key = e.getKeyChar();
					if (txtNumEcs.getText().toString().length()==1) {
						if (key == '\b') {
							System.out.println("BACKSPACE");
						} else {
							e.setKeyChar((char)3);
						}
					} else {
						if((key >= '0' && key <= '9'))
							System.out.println("NUMERIC");
						else if (key == '\b') {
							System.out.println("BACKSPACE");
						} else {
							System.out.println("NO NUMERIC");
							e.setKeyChar((char)3);
						}
					}
				}
				public void keyPressed(KeyEvent e) {} 
				public void keyReleased(KeyEvent e) {} 
			});
		}
		return txtNumEcs;
	}
	/**
	 * This method initializes txtCoef
	 * 
	 * @return TextField
	 */
	private TextField getTxtCoef() {
		if(txtCoef == null) {
			txtCoef = new TextField();
			txtCoef.setBounds(166, 110, 74, 23);
			txtCoef.setVisible(false);
			txtCoef.addKeyListener(new KeyListener() { 
				public void keyTyped(KeyEvent e) {
					char key = e.getKeyChar();
					if((key >= '0' && key <= '9'))
						System.out.println("NUMERIC");
					else if (key == '\b') {
						String str = txtCoef.getText();
						System.out.println("substr=["+str.substring(str.length()-1,str.length())+"]");
						if(str.substring(str.length()-1,str.length()).equals(".")) {
							System.out.println("true");
							setHayComa(false);
						}
					}
					else if (key == '.') {
						boolean hayComa = isHayComa();
						if (hayComa == true) {
							e.setKeyChar((char)3);
						} else {
							setHayComa(true);
						}
						System.out.println("COMA");
					}
					else {
						System.out.println("NO NUMERIC");
						e.setKeyChar((char)3);
					}    
					System.out.println("keyTyped()");
				}
				public void keyPressed(KeyEvent e) {} 
				public void keyReleased(KeyEvent e) {} 
			});
			txtCoef.addFocusListener(new FocusListener() { 
				public void focusGained(FocusEvent e) {
					txtCoef.selectAll();    
				}
				public void focusLost(FocusEvent e) {} 
			});
		}
		return txtCoef;
	}
	/**
	 * This method initializes labCoef
	 * 
	 * @return Label
	 */
	private Label getLabCoef() {
		if(labCoef == null) {
			labCoef = new Label();
			labCoef.setBounds(57, 109, 99, 23);
			labCoef.setText("Coeficiente (1,1)");
			labCoef.setVisible(false);
		}
		return labCoef;
	}
	/**
	 * This method initializes btnOk
	 * 
	 * @return Button
	 */
	private Button getBtnOk() {
		if(btnOk == null) {
			btnOk = new Button();
			btnOk.setBounds(240, 36, 28, 20);
			btnOk.setLabel("OK");
			btnOk.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {

					try {
						mNumEcs = Integer.parseInt(txtNumEcs.getText().trim());
					} catch (NumberFormatException ex){}

					double coef[][] = new double[mNumEcs][mNumEcs+1];
					
					btnOk.setEnabled(false);
					txtNumEcs.setEnabled(false);
					
					labEc.setVisible(true);
					labCoef.setVisible(true);
					txtCoef.setVisible(true);
					txtCoef.requestFocus();
					btnNext.setVisible(true);
					
					setCoef(coef);

					System.out.println("actionPerformed()");
				}
			});
		}
		return btnOk;
	}
	
	private Button getBtnNext() {
		if(btnNext == null) {
			btnNext = new Button();
			btnNext.setBounds(251, 112, 20, 20);
			btnNext.setLabel(">>");
			/*
			try {
				btnNext.setArrowIndent(4);
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}
			*/
			/*
			try {
				btnNext.setBevelHeight(1);
			} catch (PropertyVetoException e1) {
				e1.printStackTrace();
			}
			*/
			//try {
				//btnNext.setDirection(1);
				btnNext.setVisible(false);
				btnNext.addActionListener(new ActionListener() {
					int iCount = 0;
					int jCount = 0;

					int labICount = 0;
					int labJCount = 0;

					public void actionPerformed(ActionEvent e) {
						if(iCount == mNumEcs-1 && jCount == mNumEcs) {
							System.out.println("Ultimo Click");
						} else {
							txtCoef.requestFocus();					
							if(jCount == mNumEcs-1) {
								labICount = iCount + 1;
								labJCount = jCount + 2;
							} else if(jCount == mNumEcs) {
								labICount = iCount + 2;
								labJCount = jCount - (mNumEcs-1);
							} else {
								labICount = iCount + 1;
								labJCount = jCount + 2;
							}
						}
						
						//System.out.println("iCount="+iCount);
						//System.out.println("jCount="+jCount);
						
						double coef = 0D; 
						try {
							String str = txtCoef.getText();
							if (str.equals("")) 
								str = "0";
							coef = Double.parseDouble(str);
						} catch (NumberFormatException ex){
							System.out.println("Exception");
						}
						
						mCoef[iCount][jCount] = coef;
						System.out.println("mCoef["+iCount+"]["+jCount+"] ="+coef);

						labEc.setText("Ecuación "+(labICount));
						
						if (labJCount == mNumEcs+1) {
							labCoef.setText("Término indep.");
						} else {
							labCoef.setText("Coeficiente ("+(labICount)+","+(labJCount)+")");
						}

						if(iCount==mNumEcs-1 && jCount==mNumEcs) {
							txtCoef.setEnabled(false);
							btnNext.setEnabled(false);
							btnNext.setVisible(false);
							btnCalcular.setVisible(true);
						}
						
						jCount++;
						if(jCount == mNumEcs+1) {
							jCount = 0;
							iCount++;
						}
					}
				});
			/*
			} catch (PropertyVetoException e2) {
				e2.printStackTrace();
			}
			*/
		}
		return btnNext;
	}
	
	/**
	 * @return
	 */
	public boolean isHayComa() {
		return mHayComa;
	}

	/**
	 * @param b
	 */
	public void setHayComa(boolean b) {
		mHayComa = b;
	}

	/**
	 * @return
	 */
	public double[][] getCoef() {
		return mCoef;
	}

	/**
	 * @param ds
	 */
	public void setCoef(double[][] ds) {
		mCoef = ds;
	}

	/**
	 * @return
	 */
	public int getNumEcs() {
		return mNumEcs;
	}

	/**
	 * @param i
	 */
	public void setNumEcs(int i) {
		mNumEcs = i;
	}

	/**
	 * This method initializes btnCalcular
	 * 
	 * @return Button
	 */
	private Button getBtnCalcular() {
		if(btnCalcular == null) {
			btnCalcular = new Button();
			btnCalcular.setBounds(251, 111, 69, 23);
			btnCalcular.setName("btnCalcular");
			btnCalcular.setLabel("Calcular");
			btnCalcular.setVisible(false);
			
			btnCalcular.addActionListener(new ActionListener() { 
				public void actionPerformed(ActionEvent e) {
					SistemaEcuaciones sm = new SistemaEcuaciones();
					sm.setNumEcs(mNumEcs);
					sm.setCoef(mCoef);
					double sol[];
					try{
						sol = sm.getSolucion();
						drawSolution(sol);
					} catch(NoUniqueSolutionException ex){
						String str = "Solución No única!";
						Label labTxt = new Label(str);
						panSol.add(labTxt);
						labTxt.setBounds(20, 20, 200, 20);
						panSol.setVisible(true);
						System.out.println(ex.getMessage());
					}
					btnCalcular.setEnabled(false);  
				}
			});
		}
		return btnCalcular;
	}
	
	private Panel getPanSol() {
		if(panSol == null) {
			panSol  = new Panel();
			panSol.setBounds(24, 148, 308, 138);
			panSol.setName("panSol");
			panSol.setVisible(false);

			/*
			try {
				//panSol.setBevelStyle(BorderPanel.BEVEL_LOWERED);
				panSol.setVisible(false);
			} catch(PropertyVetoException ex) {
				System.out.println("Exception");
			}
			*/
		}
		return panSol;
	}
		
	private void drawSolution(double[] pSol) {
		Label labX;

		int lblLeft = 10;
		int distHoriz = 50;
		int distVert = 20;
		int lblTop = 10;
		int numCols = mNumEcs/5+1;
		int numEcs = 0;
		int col = 0;
		int fila = 0;
		String labTxt = "";

		for(int i=0; i<mNumEcs; i++) {
			lblLeft = lblLeft + col*distHoriz;
			lblTop = lblTop + fila*distVert;
			labTxt = "x"+i+"= "+pSol[i];
			
			labX = new Label(labTxt);
			panSol.add(labX);
			labX.setBounds(lblLeft, lblTop, 70, 20);
						
			fila++;
			if(fila==4) {
				col++;
				fila=0;
			}
		}
		panSol.setVisible(true);
	}

	/* (non-Javadoc)
	 * @see java.applet.Applet#destroy()
	 */
	public void destroy() {
		this.cleanup();
		super.destroy();
		
	}

	public void cleanup() {
		Component cmp[] = this.getComponents();
		for(int i=0;i<cmp.length;i++) {
			if(cmp != null) {
				this.getInputContext().removeNotify(cmp[i]);
			}
		}
		this.getInputContext().removeNotify(this);
		this.removeNotify();
	}
}
