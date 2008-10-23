package parser;

import com.singularsys.jep.FunctionTable;
import com.singularsys.jep.parser.Node;
import com.singularsys.jep.standard.Complex;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.nfunk.jep.JEP;
//import org.nfunk.jep.type.Complex;

public class GraficadorClasico extends JApplet 
{
	private static final long serialVersionUID = 3217242672416252554L;
	
	private JEP miEvaluador;
	
	boolean errorEnExpresion; // si hay error de sintaxis en la funcion
	boolean errorEnNumero; // si hay error de sintaxis en la funcion
	boolean errorEnParametros = false; // si hay error en algun parametro
	
	protected Font ft10 = new Font("Arial", Font.PLAIN, 10);
	protected Font ft12 = new Font("Arial", Font.PLAIN, 12);
	protected Font ft7 = new Font("Arial", Font.PLAIN, 7);
	
	protected Color colorIzquierda;
	protected Color colorDerecha;
	
	protected JComboBox cmbAlgoritmoIzq;
	protected JComboBox cmbAlgoritmoDer;
	protected JTextField Tffun;
	protected JTextField txtPuntoXcero;
	protected JTextField txtPuntoXultimo;
	protected JTextField txtIteraciones;
	protected JTextField txtTolerancia;
	protected JLabel Mensaje;
	protected JButton BtnGraficar; 
	protected JLabel iteracionesIzq = new JLabel();
	protected JLabel iteracionesDer = new JLabel();
	
	protected JPanel SP; // Slider Panel
	protected ZonaGrafica ZG; // aca se va a poner la ZonaGrafica de la izquierda
	protected ZonaGrafica ZG2; // aca se va a poner la ZonaGrafica de la derecha
	protected ZonaGrafica ZGVeloc; // aca se va a poner la ZonaGrafica de Xr(Velocidad)
	protected JPanel ControlPanel; // panel para la funcion, los parametros y el slider de paso a paso
	protected JPanel DisplayPanel1 = new JPanel(); // aca se va a poner ZG y ZG2 para obtener un buen borde
	protected JPanel DisplayPanel2 = new JPanel(); // aqui se va a poner los Sliders, el controlPanel y la ZGVeloc
	protected JSlider IteracionesSlider;
	
	protected int escalaX, escalaY;
	protected int Galto, Gancho; // dimesiones de las zonas de graficacion
	
	//Parametros
	protected String funcion;
	protected double xmin, xmax, imgx;
	protected double puntoXcero, puntoXultimo;
	protected double tolerancia;
	protected int iteraciones;
	protected int x0, y0; // origen
	protected int x0Veloc;
	
	protected ArrayList<Double> xrDerecha;
	protected ArrayList<Double> xrIzquierda;
	
	protected final static BasicStroke grosor1 = new BasicStroke(1.5f); // thickness
	protected final static float dash1[] = { 5.0f };
	protected final static BasicStroke dashed = new BasicStroke(1.0f,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 5.0f, dash1, 0.0f);

	// Metodo para inicializar la aplicacion	 
	public void init() 
	{
		//Setea dimensiones generales
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(screenSize.width - 8, screenSize.height - 108);
		
		Container Contenedor = getContentPane();
		Contenedor.setPreferredSize(getMaximumSize());
		Gancho = (screenSize.width - 8) / 2; //510; 
		Galto = screenSize.height + 200; // -200 // 950;

		getParametrosApplet();
		
		crearZonasGraficas();

		crearCombosSeleccionAlgoritmo();
		
		crearPanelesParametrosYFuncion();
	
		Contenedor.setLayout(new BorderLayout(1, 1)); // aplicar al applet
		Contenedor.add("Center", DisplayPanel1);
		Contenedor.add("South", DisplayPanel2);

		crearParserFuncion();

		agregarListeners();
		
		iteracionesIzq.setFont(ft10);
		iteracionesDer.setFont(ft10);
	}
	
	private void getParametrosApplet()
	{
		//Valores por defecto
		colorIzquierda = new Color(255,0,0);
		colorDerecha = new Color(0,0,0);
		funcion = "sin(x)";
		tolerancia = 0.1;
		iteraciones = 20;
		puntoXcero = -2.0;
		puntoXultimo = 2.0;
		
		//Seteo variables configurables (Parametros):
		try
		{ 
			Integer colorRizq = 0;
			if(this.getParameter("colorRizq")!= null)
				colorRizq = Integer.parseInt(this.getParameter("colorRizq"));
			Integer colorGizq = 0;
			if(this.getParameter("colorGizq")!= null)
				colorGizq = Integer.parseInt(this.getParameter("colorGizq"));
			Integer colorBizq = 0;
			if(this.getParameter("colorBizq")!= null)
				colorBizq = Integer.parseInt(this.getParameter("colorBizq"));
			colorIzquierda = new Color(colorRizq, colorGizq, colorBizq);
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Error en los parámetros RGB para la Zona Grafica Izquierda.");
		}
		try
		{
			Integer colorRder = 0;
			if(this.getParameter("colorRder")!= null)
				colorRder = Integer.parseInt(this.getParameter("colorRder"));
			Integer colorGder = 0;
			if(this.getParameter("colorGder")!= null)
				colorGder = Integer.parseInt(this.getParameter("colorGder"));
			Integer colorBder = 0;
			if(this.getParameter("colorBder")!= null)
				colorBder = Integer.parseInt(this.getParameter("colorBder"));
			colorDerecha = new Color(colorRder, colorGder, colorBder);
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Error en los parámetros RGB para la Zona Grafica Derecha.");
		}
		if(this.getParameter("funcion")!= null)
			funcion = this.getParameter("funcion").toString();
		try
		{
			if(this.getParameter("iteraciones")!= null)
				iteraciones = Integer.parseInt(this.getParameter("iteraciones"));
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Error en el parametro iteraciones.");
		}
		try
		{
			if(this.getParameter("tolerancia")!= null)
				tolerancia = Double.parseDouble(this.getParameter("tolerancia"));
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Error en el parametro tolerancia.");
		}
		try
		{
			if(this.getParameter("puntoXcero")!= null)
				puntoXcero = Double.parseDouble(this.getParameter("puntoXcero"));
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Error en el parametro Punto X Cero.");
		}
		try
		{
			if(this.getParameter("puntoXultimo")!= null)
				puntoXultimo = Double.parseDouble(this.getParameter("puntoXultimo"));
		}
		catch(NumberFormatException ex)
		{
			System.out.println("Error en el parametro Punto X Ultimo.");
		}
	}
	
	private void crearZonasGraficas()
	{
//		 Crea los distintos ejes cartesianos.
		ZG = new ZonaGrafica("izquierda"); // zona grafica de la izquierda
		ZG.setAlignmentX(0);
		ZG2 = new ZonaGrafica("derecha"); // zona grafica de la derecha
		
		ZGVeloc = new ZonaGrafica("velocidad");
		xrIzquierda = new ArrayList<Double>();
		xrDerecha = new ArrayList<Double>();
		
		SP = new SliderPanel(); // panel para Sliders de escala
		ControlPanel = new JPanel();
		JPanel pnlGraficos = new JPanel();
		pnlGraficos.setLayout(new GridLayout(1, 2));
		pnlGraficos.add(ZG);
		pnlGraficos.add(ZG2);
		
		DisplayPanel1.setLayout(new BorderLayout());
		DisplayPanel1.setPreferredSize(new Dimension(Gancho, Galto));
		DisplayPanel1.add(pnlGraficos, BorderLayout.CENTER);
	}
	
	private void crearCombosSeleccionAlgoritmo()
	{
		JPanel pnlSeleccionAlgoritmo = new JPanel();
		pnlSeleccionAlgoritmo.setLayout(new BorderLayout());

		JPanel pnlSeleccionAlgoritmoIzq = new JPanel();
		JLabel lblAlgoritmoIzq = new JLabel("Algoritmo a graficar: ");
		cmbAlgoritmoIzq = new JComboBox();
		
		cmbAlgoritmoIzq.addItem("Bisección");
		cmbAlgoritmoIzq.addItem("Secante");
		cmbAlgoritmoIzq.addItem("Regula Falsi");
		cmbAlgoritmoIzq.addItem("Regula Falsi Mejorado");

		pnlSeleccionAlgoritmoIzq.add(lblAlgoritmoIzq);
		pnlSeleccionAlgoritmoIzq.add(cmbAlgoritmoIzq);
		pnlSeleccionAlgoritmoIzq.add(iteracionesIzq);

		JPanel pnlSeleccionAlgoritmoDer = new JPanel();
		JLabel lblAlgoritmoDer = new JLabel("Algoritmo a graficar: ");
		cmbAlgoritmoDer = new JComboBox();
		
		cmbAlgoritmoDer.addItem("Secante");
		cmbAlgoritmoDer.addItem("Regula Falsi");
		cmbAlgoritmoDer.addItem("Regula Falsi Mejorado");
		cmbAlgoritmoDer.addItem("Bisección");

		pnlSeleccionAlgoritmoDer.add(lblAlgoritmoDer);
		pnlSeleccionAlgoritmoDer.add(cmbAlgoritmoDer);
		pnlSeleccionAlgoritmoDer.add(iteracionesDer);

		pnlSeleccionAlgoritmo.add(pnlSeleccionAlgoritmoIzq, BorderLayout.WEST);
		pnlSeleccionAlgoritmo.add(pnlSeleccionAlgoritmoDer, BorderLayout.EAST);
		DisplayPanel1.add(pnlSeleccionAlgoritmo, BorderLayout.NORTH);
	}
	
	private void crearPanelesParametrosYFuncion()
	{
		ControlPanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;

		Tffun = new JTextField(funcion, 20);
		c.gridy = 1;
		c.gridx = 0;
		ControlPanel.add(Tffun, c);

		JLabel esp1 = new JLabel(" ");
		c.gridy = 1;
		c.gridx = 1;
		ControlPanel.add(esp1, c);

		BtnGraficar = new JButton("Graficar");
		c.gridy = 1;
		c.gridx = 2;
		ControlPanel.add(BtnGraficar, c); // AGREGAR BOTON GRAFICAR
		BtnGraficar.setFont(ft12);

		// Label Punto X cero
		JLabel lblPuntoXcero = new JLabel("Punto X cero:", JLabel.LEFT);

		// TextBox Punto X cero
		txtPuntoXcero = new JTextField(String.valueOf(puntoXcero), 4);

		JPanel pnlPuntoXcero = new JPanel();
		pnlPuntoXcero.add(lblPuntoXcero);
		pnlPuntoXcero.add(txtPuntoXcero);

		// Label Punto X ultimo
		JLabel lblPuntoXultimo = new JLabel("Punto X último:", JLabel.LEFT);

		// TextBox Punto X ultimo
		txtPuntoXultimo = new JTextField(String.valueOf(puntoXultimo), 4);

		JPanel pnlPuntoXultimo = new JPanel();
		pnlPuntoXultimo.add(lblPuntoXultimo);
		pnlPuntoXultimo.add(txtPuntoXultimo);

		c.gridy = 2; // fila
		c.gridx = 0; // columna
		ControlPanel.add(pnlPuntoXcero, c);

		c.gridy = 3; // fila
		c.gridx = 0; // columna
		ControlPanel.add(pnlPuntoXultimo, c);
		
		// Label Numero iteraciones
		JLabel lblIteraciones = new JLabel("Iteraciones:", JLabel.LEFT);

		// TextBox Punto X cero
		txtIteraciones = new JTextField(String.valueOf(iteraciones), 4);

		JPanel pnlIteraciones = new JPanel();
		pnlIteraciones.add(lblIteraciones);
		pnlIteraciones.add(txtIteraciones);

		// Label Tolerancia
		JLabel lblTolerancia = new JLabel("Tolerancia:", JLabel.LEFT);

		// TextBox Punto X ultimo
		txtTolerancia = new JTextField(String.valueOf(tolerancia), 4);

		JPanel pnlTolerancia = new JPanel();
		pnlTolerancia.add(lblTolerancia);
		pnlTolerancia.add(txtTolerancia);

		c.gridy = 4; // fila
		c.gridx = 0; // columna
		ControlPanel.add(pnlIteraciones, c);
		
		c.gridy = 5; // fila
		c.gridx = 0; // columna
		ControlPanel.add(pnlTolerancia, c);

		Mensaje = new JLabel("", JLabel.LEFT);
		c.gridwidth = 3;
		c.gridy = 6; // fila
		c.gridx = 0; // columna
		ControlPanel.add(Mensaje, c);
		Mensaje.setFont(ft12);
		Mensaje.setForeground(new Color(0, 100, 0));
	
		c.gridy = 7;
		c.gridx = 0;
		ControlPanel.add(esp1, c);
		
		JLabel lblIterSlider = new JLabel("Vea el método en cada iteración:");
		
		c.gridy = 8; // fila
		c.gridx = 0; // columna
		ControlPanel.add(lblIterSlider, c);
		
		IteracionesSlider = new JSlider(JSlider.HORIZONTAL);
		IteracionesSlider.setMinorTickSpacing(1);
		IteracionesSlider.setMajorTickSpacing(5);
		IteracionesSlider.setMaximum(iteraciones);
		IteracionesSlider.setPaintTicks(true);
		IteracionesSlider.setPaintLabels(true);
		
		c.gridy = 9; // fila
		c.gridx = 0; // columna
		ControlPanel.add(IteracionesSlider, c);

		// Bordes
		Border colorline = BorderFactory.createLineBorder(new Color(220, 220,
				220));
		DisplayPanel1.setBorder(colorline);
		TitledBorder rotulo;
		rotulo = BorderFactory.createTitledBorder(" Escala");
		rotulo.setTitleFont(ft10);
		rotulo.setTitleColor(new Color(0, 0, 128));
		SP.setBorder(rotulo);

		rotulo = BorderFactory.createTitledBorder(" CRV ");
		rotulo.setTitleFont(ft10);
		rotulo.setTitleColor(new Color(0, 0, 128));

		rotulo = BorderFactory.createTitledBorder(" Función y Parámetros");
		rotulo.setTitleColor(new Color(0, 0, 128));
		rotulo.setTitleFont(ft10);
		ControlPanel.setBorder(rotulo);
		// fin de Bordes

		ControlPanel.setPreferredSize(new Dimension(60 * Gancho / 100,
				30 * Galto / 100));
		SP.setPreferredSize(new Dimension(30 * Gancho / 100,
				30 * getSize().height / 100));

		JPanel pnlControlPanelSP = new JPanel();
		pnlControlPanelSP.setLayout(new BorderLayout(1,1));
		pnlControlPanelSP.add("West", SP);
		pnlControlPanelSP.add("Center", ControlPanel);
		DisplayPanel2.setLayout(new GridLayout(1, 2));
		DisplayPanel2.add(pnlControlPanelSP);
		DisplayPanel2.add(ZGVeloc);
	}
	
	private void crearParserFuncion()
	{
		miEvaluador = new JEP();
		miEvaluador.addStandardFunctions(); // agrega las funciones matematicas comunes.
		miEvaluador.addStandardConstants(); // agrega las constantes estandar.
		miEvaluador.addComplex(); // agrega numeros complejos.
		//miEvaluador.addFunction("sen", new org.nfunk.jep.function.Sine());// habilitar 'sen'
		miEvaluador.addVariable("x", 0);
		miEvaluador.setImplicitMul(true); // permite 2x en vez de 2*x
		escalaX = 50;
		escalaY = 50;
                
                try
                {
                  miEvaluador.parseExpression("x^3-5x^2+x+1");
                  miEvaluador.addVariable("x", 5);
                  double a = miEvaluador.getValue();
                  JOptionPane.showMessageDialog(null,a);
                    
                  double tol=0.0001;
                  double x1,x2;
                  double c = 0;
                   double yc = 0;
                  x1 = -10;
                  x2 = 10;
                   
                  miEvaluador.addVariable("x", x1);
                  double ya= miEvaluador.getValue();
                  miEvaluador.addVariable("x", x2);
                  double yb= miEvaluador.getValue();
                   
                  double max = 1 + Math.round( (Math.log(x2-x1) - Math.log(tol))/Math.log(2)  );
                  
                  double i = 0.1;
                  
                  while(i<max)
                  {
                      c = (x1+x2)/2;
                      miEvaluador.addVariable("x", c);
                     yc = miEvaluador.getValue();
                      
                      if(yc ==0)
                      {
                          x1 = c;
                          x2 = c;
                      }
                      else
                      {
                          if(yb*yc > 0)
                          {
                              x2 = c;
                              yb = yc;
                          }
                          else
                          {
                              x1 = c;
                              ya = yc;
                          }
                      }
                      i+=0.1;
                  }
                  
                  c = (x1+x2)/2;
                   miEvaluador.addVariable("x", c);
                    yc = miEvaluador.getValue(); 
                  
                  JOptionPane.showMessageDialog(null, "x1: " + x1 + "   " + "x2: " + x2 + "    " + yc + "   " + yb +"     " + ya + "      ");

                }
                catch(Exception e)
                {
                    JOptionPane.showMessageDialog(null,"Exx");
                }
		x0 = Gancho / 2;
		y0 = (Galto / 2)-300;
		
		x0Veloc = (Gancho / 2) - 200; //Para la zona grafica Xr(velocidad)
	}
	
	private void agregarListeners()
	{
		ManejadorDeEvento ManejadorDevt = new ManejadorDeEvento();

		// Agrega action listeners para el manejo de eventos

		Tffun.addActionListener(ManejadorDevt);
		txtPuntoXcero.addActionListener(ManejadorDevt);
		txtPuntoXultimo.addActionListener(ManejadorDevt);
		txtIteraciones.addActionListener(ManejadorDevt);
		txtTolerancia.addActionListener(ManejadorDevt);
		BtnGraficar.addActionListener(ManejadorDevt);

		
		cmbAlgoritmoIzq.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) 
				{
					txtPuntoXcero.setForeground(Color.black);
					txtPuntoXultimo.setForeground(Color.black);
					txtIteraciones.setForeground(Color.black);
					txtTolerancia.setForeground(Color.black);
					errorEnParametros = false;

					try 
					{
						puntoXcero = Double.parseDouble(txtPuntoXcero.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("X cero debe ser un número de coma flotante.");
						txtPuntoXcero.setForeground(Color.red);
						errorEnParametros = true;
					}
					try 
					{
						puntoXultimo = Double
								.parseDouble(txtPuntoXultimo.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("X último debe ser un número de coma flotante.");
						txtPuntoXultimo.setForeground(Color.red);
						errorEnParametros = true;
					}
					try 
					{
						iteraciones = Integer.parseInt(txtIteraciones.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("La cantidad de iteraciones debe ser un número entero.");
						txtIteraciones.setForeground(Color.red);
						errorEnParametros = true;
					}
					try 
					{
						tolerancia = Double.parseDouble(txtTolerancia.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("La tolerancia debe ser un número de coma flotante.");
						txtTolerancia.setForeground(Color.red);
						errorEnParametros = true;
					}

					if (!errorEnParametros) 
					{
						IteracionesSlider.setMaximum(iteraciones);
						ZG.repaint();
						ZG2.repaint();
						ZGVeloc.repaint();
					}
				}
			}
		});
		
		cmbAlgoritmoDer.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent evt) {
				if (evt.getStateChange() == ItemEvent.SELECTED) 
				{
					txtPuntoXcero.setForeground(Color.black);
					txtPuntoXultimo.setForeground(Color.black);
					txtIteraciones.setForeground(Color.black);
					txtTolerancia.setForeground(Color.black);
					errorEnParametros = false;

					try 
					{
						puntoXcero = Double.parseDouble(txtPuntoXcero.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("X cero debe ser un número de coma flotante.");
						txtPuntoXcero.setForeground(Color.red);
						errorEnParametros = true;
					}
					try 
					{
						puntoXultimo = Double.parseDouble(txtPuntoXultimo.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("X último debe ser un número de coma flotante.");
						txtPuntoXultimo.setForeground(Color.red);
						errorEnParametros = true;
					}
					try 
					{
						iteraciones = Integer.parseInt(txtIteraciones.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("La cantidad de iteraciones debe ser un número entero.");
						txtIteraciones.setForeground(Color.red);
						errorEnParametros = true;
					}
					try 
					{
						tolerancia = Double.parseDouble(txtTolerancia.getText());
					} catch (NumberFormatException ex) {
						Mensaje.setText("La tolerancia debe ser un número de coma flotante.");
						txtTolerancia.setForeground(Color.red);
						errorEnParametros = true;
					}

					if (!errorEnParametros) 
					{
						IteracionesSlider.setMaximum(iteraciones);
						ZG.repaint();
						ZG2.repaint();
						ZGVeloc.repaint();
					}
				}
			}
		});
		
		IteracionesSliderListener IteracionesSliderListener = new IteracionesSliderListener();
		IteracionesSlider.addChangeListener(IteracionesSliderListener);
	}
	
	private class IteracionesSliderListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) 
		{
			txtPuntoXcero.setForeground(Color.black);
			txtPuntoXultimo.setForeground(Color.black);
			txtIteraciones.setForeground(Color.black);
			txtTolerancia.setForeground(Color.black);
			errorEnParametros = false;

			try {
				puntoXcero = Double.parseDouble(txtPuntoXcero.getText());
			} catch (NumberFormatException ex) {
				Mensaje.setText("X cero debe ser un número de coma flotante.");
				txtPuntoXcero.setForeground(Color.red);
				errorEnParametros = true;
			}
			try {
				puntoXultimo = Double.parseDouble(txtPuntoXultimo.getText());
			} catch (NumberFormatException ex) {
				Mensaje.setText("X último debe ser un número de coma flotante.");
				txtPuntoXultimo.setForeground(Color.red);
				errorEnParametros = true;
			}
			try {
				iteraciones = Integer.parseInt(txtIteraciones.getText());
			} catch (NumberFormatException ex) {
				Mensaje.setText("La cantidad de iteraciones debe ser un número entero.");
				txtIteraciones.setForeground(Color.red);
				errorEnParametros = true;
			}
			try {
				tolerancia = Double.parseDouble(txtTolerancia.getText());
			} catch (NumberFormatException ex) {
				Mensaje.setText("La tolerancia debe ser un número de coma flotante.");
				txtTolerancia.setForeground(Color.red);
				errorEnParametros = true;
			}

			if (!errorEnParametros) 
			{
				IteracionesSlider.setMaximum(iteraciones);
				iteraciones = IteracionesSlider.getValue();
				ZG.repaint();
				ZG2.repaint();
				ZGVeloc.repaint();
			}
			
		}
	}

	private class ManejadorDeEvento implements ActionListener {
		public void actionPerformed(ActionEvent evt) {
			Object source = evt.getSource();
			// si se presiona el boton o se da 'enter' en algun campo de texto
			if (source == BtnGraficar || source == Tffun) {
				// Mensaje.setText("Arrastre el mouse para mover ejes");
				txtPuntoXcero.setForeground(Color.black);
				txtPuntoXultimo.setForeground(Color.black);
				txtIteraciones.setForeground(Color.black);
				txtTolerancia.setForeground(Color.black);
				errorEnParametros = false;

				try {
					puntoXcero = Double.parseDouble(txtPuntoXcero.getText());
				} catch (NumberFormatException ex) {
					Mensaje.setText("X cero debe ser un número de coma flotante.");
					txtPuntoXcero.setForeground(Color.red);
					errorEnParametros = true;
				}
				try {
					puntoXultimo = Double.parseDouble(txtPuntoXultimo.getText());
				} catch (NumberFormatException ex) {
					Mensaje.setText("X último debe ser un número de coma flotante.");
					txtPuntoXultimo.setForeground(Color.red);
					errorEnParametros = true;
				}
				try {
					iteraciones = Integer.parseInt(txtIteraciones.getText());
				} catch (NumberFormatException ex) {
					Mensaje.setText("La cantidad de iteraciones debe ser un número entero.");
					txtIteraciones.setForeground(Color.red);
					errorEnParametros = true;
				}
				try {
					tolerancia = Double.parseDouble(txtTolerancia.getText());
				} catch (NumberFormatException ex) {
					Mensaje.setText("La tolerancia debe ser un número de coma flotante.");
					txtTolerancia.setForeground(Color.red);
					errorEnParametros = true;
				}

				if (!errorEnParametros) 
				{
					ZG.repaint();
					ZG2.repaint();
					ZGVeloc.repaint();
				}
			}

		}
	}

	public class ZonaGrafica extends JPanel implements MouseListener, MouseMotionListener 
	{
		private static final long serialVersionUID = 9176674739156338838L;
		private int offsetX, offsetY;
		boolean dragging;
		private String posicion;

		public ZonaGrafica(String posicion) 
		{
			setBackground(Color.white);
			this.setBorder(BorderFactory.createLineBorder(Color.black));
			
			if(posicion.equalsIgnoreCase("velocidad"))
			{
				offsetX = x0Veloc;
				
				this.setLayout(new BorderLayout());
				
				this.add(new JLabel("   Xr                                  Comparación de velocidades - Xr(iteración)"), BorderLayout.NORTH);
				
				JPanel ejeX = new JPanel();
				ejeX.setLayout(new FlowLayout(FlowLayout.RIGHT));
				ejeX.setBackground(null);
				ejeX.add(new JLabel("N° iteración "));
				this.add(ejeX, BorderLayout.SOUTH);
			}
			else
			{
				offsetX = x0;
			}
			offsetY = y0;
			addMouseListener(this);
			addMouseMotionListener(this);
		
			this.posicion = posicion; //La posicion indica si esta Zona Grafica corresponse a la de la izquierda, 
									  //derecha o la de abajo usada para comparar la velocidad de los metodos
		}

		public void mousePressed(MouseEvent evt) {

			if (dragging)
				return;
			int x = evt.getX(); // clic inicial
			int y = evt.getY();
			if(posicion.equalsIgnoreCase("velocidad"))
			{
				offsetX = x - x0Veloc;
			}
			else
			{
				offsetX = x - x0;
			}
			
			offsetY = y - y0;
			dragging = true;
		}

		public void mouseReleased(MouseEvent evt) {
			dragging = false;
			repaint();
		}

		public void mouseDragged(MouseEvent evt) {
			if (dragging == false)
				return;

			int x = evt.getX(); // posicion del mouse
			int y = evt.getY();
			if(posicion.equalsIgnoreCase("velocidad"))
			{
				x0Veloc = x - offsetX; // mover origen
			}
			else
			{
				x0 = x - offsetX; // mover origen
			}
			
			y0 = y - offsetY;

			repaint();
		}

		// el resto hace nada
		public void mouseMoved(MouseEvent evt) {
		}

		public void mouseClicked(MouseEvent evt) {
		}

		public void mouseEntered(MouseEvent evt) {
		}

		public void mouseExited(MouseEvent evt) {
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g); // clear to background color

			if(posicion.equalsIgnoreCase("velocidad"))
			{
				GraficarVelocidad(g, x0Veloc, y0);
			}
			else
			{
				Graficar(g, x0, y0);
			}

			showStatus("Modelado y simulación - Baretto, Lorden, Palumbo");
		}

		void Graficar(Graphics ap, int xg, int yg) {
			int xi = 0, yi = 0, xi1 = 0, yi1 = 0, numPuntos = 1;
			int cxmin, cxmax, cymin, cymax;
			double valxi = 0.0, valxi1 = 0.0, valyi = 0.0, valyi1 = 0.0;

			// convertimos el objeto ap en un objeto Graphics2D para usar los metodos Java2D
			Graphics2D g = (Graphics2D) ap;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g.setFont(ft10);
			g.setPaint(new Color(0, 0, 150));

			// eje Y
			g.draw(new Line2D.Double(xg, 10, xg, Galto - 10));
			// eje X
			g.draw(new Line2D.Double(10, yg, Gancho - 10, yg));

			xmin = -1.0 * xg / escalaX;
			xmax = (1.0 * (Gancho - xg) / escalaX);
			cxmin = (int) Math.round(xmin); // pantalla
			cxmax = (int) Math.round(xmax);
			cymin = (int) Math.round(1.0 * (yg - Galto) / escalaY);
			cymax = (int) Math.round(1.0 * yg / escalaY);

			numPuntos = Gancho; // num pixels

			g.setPaint(Color.gray);
			g.setFont(ft7);

			// marcas en los ejes (ticks)
			if (escalaX > 5) {
				for (int i = cxmin + 1; i < cxmax; i++) {
					g.draw(new Line2D.Double(xg + i * escalaX, yg - 2, xg + i
							* escalaX, yg + 2));
					if (i > 0)
						g.drawString("" + i, xg + i * escalaX - 2, yg + 12);
					if (i < 0)
						g.drawString("" + i, xg + i * escalaX - 6, yg + 12);
				}
			}

			if (escalaY > 5) {
				for (int i = cymin + 1; i < cymax; i++) {
					g.draw(new Line2D.Double(xg - 2, yg - i * escalaY, xg + 2,
							yg - i * escalaY));
					if (i > 0)
						g.drawString("" + i, xg - 12, yg - i * escalaY + 3);
					if (i < 0)
						g.drawString("" + i, xg - 14, yg - i * escalaY + 3);
				}
			}
			g.setPaint(new Color(50, 100, 0));

			g.setStroke(grosor1);

			miEvaluador.parseExpression(Tffun.getText());
			errorEnExpresion = miEvaluador.hasError();
                        
			if (!errorEnExpresion && !errorEnParametros) {
				Mensaje.setText("Arrastre el mouse para mover ejes");
				Tffun.setForeground(Color.black);

				for (int i = 0; i < numPuntos - 1; i++) {
					valxi = xmin + i * 1.0 / escalaX;
					valxi1 = xmin + (i + 1) * 1.0 / escalaX;
					miEvaluador.addVariable("x", valxi);
					valyi = miEvaluador.getValue();
					miEvaluador.addVariable("x", valxi1);
					valyi1 = miEvaluador.getValue();
					xi = (int) Math.round(escalaX * (valxi));
					yi = (int) Math.round(escalaY * valyi);
					xi1 = (int) Math.round(escalaX * (valxi1));
					yi1 = (int) Math.round(escalaY * valyi1);

					// control de discontinuidades groseras y complejos
					Complex valC = miEvaluador.getComplexValue();
					
					double imgx = (double) Math.abs(valC.im());
					if (dist(valxi, valyi, valxi1, valyi1) < 1000 && imgx == 0) {
						g.draw(new Line2D.Double(xg + xi, yg - yi, xg + xi1, yg
								- yi1));
					}
				}

				// GRAFICOS DE LOS METODOS
				if (this.posicion.equals("izquierda"))
				{
					if(cmbAlgoritmoIzq.getSelectedItem().toString().equals("Bisección"))
					{
						biseccion(ap, xg, yg, puntoXcero, puntoXultimo, xrIzquierda, colorIzquierda);
					}
					else if(cmbAlgoritmoIzq.getSelectedItem().toString().equals("Secante"))
					{
						secante(ap, xg, yg, puntoXcero, puntoXultimo, xrIzquierda, colorIzquierda);
					}
					else if(cmbAlgoritmoIzq.getSelectedItem().toString().equals("Regula Falsi"))
					{
						regulafalsi(ap, xg, yg, puntoXcero, puntoXultimo, xrIzquierda, colorIzquierda);
					}
					else if(cmbAlgoritmoIzq.getSelectedItem().toString().equals("Regula Falsi Mejorado"))
					{
						regulafalsiMejorado(ap, xg, yg, puntoXcero, puntoXultimo, xrIzquierda, colorIzquierda);
					}
				}
				else if (this.posicion.equals("derecha"))
				{
					if(cmbAlgoritmoDer.getSelectedItem().toString().equals("Bisección"))
					{
						biseccion(ap, xg, yg, puntoXcero, puntoXultimo, xrDerecha, colorDerecha);
					}
					else if(cmbAlgoritmoDer.getSelectedItem().toString().equals("Secante"))
					{
						secante(ap, xg, yg, puntoXcero, puntoXultimo, xrDerecha, colorDerecha);
					}
					else if(cmbAlgoritmoDer.getSelectedItem().toString().equals("Regula Falsi"))
					{
						regulafalsi(ap, xg, yg, puntoXcero, puntoXultimo, xrDerecha, colorDerecha);
					}
					else if(cmbAlgoritmoDer.getSelectedItem().toString().equals("Regula Falsi Mejorado"))
					{
						regulafalsiMejorado(ap, xg, yg, puntoXcero, puntoXultimo, xrDerecha, colorDerecha);
					}
				}
			} else {
				if (!errorEnParametros) {
					Mensaje.setText("Hay un error en la función.");
					Tffun.setForeground(Color.red);
				}
			}
		}
		
		void GraficarVelocidad(Graphics ap, int xg, int yg) 
		{
			int numPuntos = 1;
			int cxmin, cxmax, cymin, cymax;

			// convertimos el objeto ap en un objeto Graphics2D para usar los metodos Java2D
			Graphics2D g = (Graphics2D) ap;
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);

			g.setFont(ft10);
			g.setPaint(new Color(0, 0, 150));

			// eje Y
			g.draw(new Line2D.Double(xg, 10, xg, Galto - 10));
			// eje X
			g.draw(new Line2D.Double(10, yg, Gancho - 10, yg));

			xmin = -1.0 * xg / escalaX;
			xmax = (1.0 * (Gancho - xg) / escalaX);
			cxmin = (int) Math.round(xmin); // pantalla
			cxmax = (int) Math.round(xmax);
			cymin = (int) Math.round(1.0 * (yg - Galto) / escalaY);
			cymax = (int) Math.round(1.0 * yg / escalaY);

			numPuntos = Gancho; // num pixels

			g.setPaint(Color.gray);
			g.setFont(ft7);

			// marcas en los ejes (ticks)
			if (escalaX > 5) {
				for (int i = cxmin + 1; i < cxmax; i++) {
					g.draw(new Line2D.Double(xg + i * escalaX, yg - 2, xg + i
							* escalaX, yg + 2));
					if (i > 0)
						g.drawString("" + i, xg + i * escalaX - 2, yg + 12);
					if (i < 0)
						g.drawString("" + i, xg + i * escalaX - 6, yg + 12);
				}
			}

			if (escalaY > 5) {
				for (int i = cymin + 1; i < cymax; i++) {
					g.draw(new Line2D.Double(xg - 2, yg - i * escalaY, xg + 2,
							yg - i * escalaY));
					if (i > 0)
						g.drawString("" + i, xg - 12, yg - i * escalaY + 3);
					if (i < 0)
						g.drawString("" + i, xg - 14, yg - i * escalaY + 3);
				}
			}
			g.setPaint(new Color(50, 100, 0));

			g.setStroke(grosor1);

			miEvaluador.parseExpression(Tffun.getText());
			errorEnExpresion = miEvaluador.hasError();

			if (!errorEnExpresion && !errorEnParametros) 
			{
				Velocidad(ap, xg, yg);
			} 
			else 
			{
				if (!errorEnParametros) 
				{
					Mensaje.setText("Hay un error en la función.");
					Tffun.setForeground(Color.red);
				}
			}
		}
		
		void Velocidad(Graphics ap, int xg, int yg)
		{
			Double ultimoResultado, xr;
			Graphics2D g = (Graphics2D) ap;
			g.setColor(colorIzquierda);
			
			if(xrIzquierda.size()>0)
			{
				for(int i=1; i < xrIzquierda.size(); i++)
				{
					ultimoResultado = xrIzquierda.get(i-1);
					xr = xrIzquierda.get(i);
					g.draw(new Line2D.Double(xg+((i-1)*escalaX),yg-(ultimoResultado*escalaY),xg+(i*escalaX),yg-(xr*escalaY)));
				}
			}
			
			g.setColor(colorDerecha);
			
			if(xrDerecha.size()>0)
			{
				for(int i=1; i < xrDerecha.size(); i++)
				{
					ultimoResultado = xrDerecha.get(i-1);
					xr = xrDerecha.get(i);
					g.draw(new Line2D.Double(xg+((i-1)*escalaX),yg-(ultimoResultado*escalaY),xg+(i*escalaX),yg-(xr*escalaY)));
				}
			}
		}

		void biseccion(Graphics ap, int xg, int yg, double xi, double xu, ArrayList<Double> xrs, Color color)
		{
			if(xi!=xu)
			{
				double ultimoResultado;
				double xr = xi;
				double yr;
				double gxi;
				double gxu;
				double gyi;
				double gyu;
				xrs.clear();
				Graphics2D g = (Graphics2D) ap;
	//			Color de la linea
				g.setColor(color);
				miEvaluador.addVariable("x", xi);
				double yi = miEvaluador.getValue();
				miEvaluador.addVariable("x", xu);
				double yu = miEvaluador.getValue();
				
				for (int i = 1; i<=iteraciones; i++)
				{
					ultimoResultado = xr;
		//			Calculamos la falsa posicion
					xr = (xu + xi)/2;
					xrs.add(xr);
					System.out.println("***********************");
					System.out.println("it:"+i);
					System.out.println("xu:"+xu+" - yu:"+yu);
					System.out.println("xi:"+xi+" - yi:"+yi);
					System.out.println("xr:"+xr);
					if ( xr < xi )
					{
						gxi = xr;
						miEvaluador.addVariable("x", gxi);
						gyi = miEvaluador.getValue();
						gxu = xu;
						gyu = yu;
					}
					else if ( xr > xu )
					{
						gxu = xr;
						miEvaluador.addVariable("x", gxu);
						gyu = miEvaluador.getValue();
						gxi = xi;
						gyi = yi;
					}
					else 
					{
						gxi = xi;
						gyi = yi;
						gxu = xu;
						gyu = yu;
					}
		//			Graficamos las uniones entre los puntos
					miEvaluador.addVariable("x", xr);
					yr = miEvaluador.getValue();
		
					g.draw(new Line2D.Double(xg+(gxi*escalaX),yg-(gyi*escalaY),xg+(gxu*escalaX),yg-(gyu*escalaY)));
					g.draw(new Line2D.Double(xg+(xr*escalaX),yg,xg+(xr*escalaX),yg-(yr*escalaY)));
					
					if ( (Math.abs((xr-ultimoResultado)/xr)*100 < tolerancia) && (Math.abs(yr) < tolerancia) )
					{
						System.out.println("/////////////////////////////////////////");
						System.out.println("Raiz encontrada! ");
						System.out.println("Raiz: "+xr);
						System.out.println("Iteración: "+i);
						if (cmbAlgoritmoDer.getSelectedItem().toString().equals("Bisección"))
							iteracionesDer.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						if (cmbAlgoritmoIzq.getSelectedItem().toString().equals("Bisección"))
							iteracionesIzq.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						return;
					}
		//			La raiz se encuentra en el primer termino
					if((yi * yr)<0)
					{
						xu = xr;
						yu = yr;
					}
					else
					{
						xi = xr;
						yi = yr;
					}
				}
				System.out.println("xu:"+xu+" - yu:"+yu);
				return;
			}
			else
			{
				return;
			}
		}

		void secante(Graphics ap, int xg, int yg, double xi, double xu, ArrayList<Double> xrs, Color color)
		{
			if(xi!=xu)
			{
				double ultimoResultado;
				double xr = xi;
				double gxi;
				double gxu;
				double gyi;
				double gyu;
				xrs.clear();
				Graphics2D g = (Graphics2D) ap;
	//			Color de la linea
				g.setColor(color);
				miEvaluador.addVariable("x", xi);
				double yi = miEvaluador.getValue();
				miEvaluador.addVariable("x", xu);
				double yu = miEvaluador.getValue();
				for (int i = 1; i<=iteraciones; i++)
				{
					ultimoResultado = xr;
					xr = xu - yu*(xi-xu)/(yi-yu);
					xrs.add(xr);
					System.out.println("***********************");
					System.out.println("it:"+i);
					System.out.println("xu:"+xu+" - yu:"+yu);
					System.out.println("xi:"+xi+" - yi:"+yi);
					System.out.println("x:"+xr);
		
					if(xi<xu)
					{
						if(xi<xr)
						{
							gxi=xi;
							gyi=yi;
						}
						else
						{
							gxi=xr;
							gyi=0;
						}
					}
					else
					{
						gxi=xu;
						gyi=yu;
					}
					if(xi>xu)
					{
						if(xi>xr)
						{
							gxu=xi;
							gyu=yi;
						}
						else
						{
							gxu=xr;
							gyu=0;
						}
					}
					else
					{
						gxu=xu;
						gyu=yu;
					}
					g.draw(new Line2D.Double(xg+(gxi*escalaX),yg-(gyi*escalaY),xg+(gxu*escalaX),yg-(gyu*escalaY)));
					
					xi = xu;
					yi = yu;
					xu = xr;
					miEvaluador.addVariable("x", xr);
					yu = miEvaluador.getValue();
					
					if ( (Math.abs((xr-ultimoResultado)/xr)*100 < tolerancia) && (Math.abs(yu) < tolerancia) )
					{
						System.out.println("/////////////////////////////////////////");
						System.out.println("Raiz encontrada! ");
						System.out.println("Raiz: "+xr);
						System.out.println("Iteración: "+i);
						if (cmbAlgoritmoDer.getSelectedItem().toString().equals("Secante"))
							iteracionesDer.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						if (cmbAlgoritmoIzq.getSelectedItem().toString().equals("Secante"))
							iteracionesIzq.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						return;
					}
		
					g.draw(new Line2D.Double(xg+(xr*escalaX),yg,xg+(xr*escalaX),yg-(yu*escalaY)));
				}
				System.out.println("xu:"+xu+" - yu:"+yu);
				return;
			}
			else
			{
				return;
			}
		}
			
		void regulafalsi(Graphics ap, int xg, int yg, double xi, double xu, ArrayList<Double> xrs, Color color)
		{
			if(xi!=xu)
			{
				double ultimoResultado;
				double xr = xi;
				double yr;
				double gxi;
				double gxu;
				double gyi;
				double gyu;
				xrs.clear();
				Graphics2D g = (Graphics2D) ap;
	//			Color de la linea
				g.setColor(color);
				miEvaluador.addVariable("x", xi);
				double yi = miEvaluador.getValue();
				miEvaluador.addVariable("x", xu);
				double yu = miEvaluador.getValue();
				
				for (int i = 1; i<=iteraciones; i++)
				{
					ultimoResultado = xr;
		//			Calculamos la falsa posicion
					xr = xu - yu*(xi-xu)/(yi-yu);
					xrs.add(xr);
					System.out.println("***********************");
					System.out.println("it:"+i);
					System.out.println("xu:"+xu+" - yu:"+yu);
					System.out.println("xi:"+xi+" - yi:"+yi);
					System.out.println("xr:"+xr);
					if ( xr < xi )
					{
						gxi = xr;
						miEvaluador.addVariable("x", gxi);
						gyi = miEvaluador.getValue();
						gxu = xu;
						gyu = yu;
					}
					else if ( xr > xu )
					{
						gxu = xr;
						miEvaluador.addVariable("x", gxu);
						gyu = miEvaluador.getValue();
						gxi = xi;
						gyi = yi;
					}
					else 
					{
						gxi = xi;
						gyi = yi;
						gxu = xu;
						gyu = yu;
					}
		//			Graficamos las uniones entre los puntos
					miEvaluador.addVariable("x", xr);
					yr = miEvaluador.getValue();
		
					g.draw(new Line2D.Double(xg+(gxi*escalaX),yg-(gyi*escalaY),xg+(gxu*escalaX),yg-(gyu*escalaY)));
					g.draw(new Line2D.Double(xg+(xr*escalaX),yg,xg+(xr*escalaX),yg-(yr*escalaY)));
					if ( (Math.abs((xr-ultimoResultado)/xr)*100 < tolerancia) && (Math.abs(yr) < tolerancia) )
					{
						System.out.println("/////////////////////////////////////////");
						System.out.println("Raiz encontrada! ");
						System.out.println("Raiz: "+xr);
						System.out.println("Iteración: "+i);
						if (cmbAlgoritmoDer.getSelectedItem().toString().equals("Regula Falsi"))
							iteracionesDer.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						if (cmbAlgoritmoIzq.getSelectedItem().toString().equals("Regula Falsi"))
							iteracionesIzq.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						return;
					}
		//			La raiz se encuentra en el primer termino
					if((yi * yr)<0)
					{
						xu = xr;
						yu = yr;
					}
					else
					{
						xi = xr;
						yi = yr;
					}
				}
				System.out.println("xu:"+xu+" - yu:"+yu);
				return;
			}
			else
			{
				return;
			}
		}
			
		void regulafalsiMejorado(Graphics ap, int xg, int yg, double xi, double xu, ArrayList<Double> xrs, Color color)
		{
			if(xi!=xu)
			{
				double ultimoResultado;
				double xr = xi;
				double yr;
				double gxi;
				double gxu;
				double gyi;
				double gyu;
				xrs.clear();
				int contador = 0;
				Graphics2D g = (Graphics2D) ap;
	//			Color de la linea
				g.setColor(color);
				miEvaluador.addVariable("x", xi);
				double yi = miEvaluador.getValue();
				miEvaluador.addVariable("x", xu);
				double yu = miEvaluador.getValue();
				
				for (int i = 1; i<=iteraciones; i++)
				{
					ultimoResultado = xr;
		//			Calculamos la falsa posicion
					xr = xu - yu*(xi-xu)/(yi-yu);
					xrs.add(xr);
					System.out.println("***********************");
					System.out.println("it:"+i);
					System.out.println("xu:"+xu+" - yu:"+yu);
					System.out.println("xi:"+xi+" - yi:"+yi);
					System.out.println("xr:"+xr);
					if ( xr < xi )
					{
						gxi = xr;
						miEvaluador.addVariable("x", gxi);
						gyi = miEvaluador.getValue();
						gxu = xu;
						gyu = yu;
					}
					else if ( xr > xu )
					{
						gxu = xr;
						miEvaluador.addVariable("x", gxu);
						gyu = miEvaluador.getValue();
						gxi = xi;
						gyi = yi;
					}
					else 
					{
						gxi = xi;
						gyi = yi;
						gxu = xu;
						gyu = yu;
					}
		//			Graficamos las uniones entre los puntos
					miEvaluador.addVariable("x", xr);
					yr = miEvaluador.getValue();
		
					g.draw(new Line2D.Double(xg+(gxi*escalaX),yg-(gyi*escalaY),xg+(gxu*escalaX),yg-(gyu*escalaY)));
					g.draw(new Line2D.Double(xg+(xr*escalaX),yg,xg+(xr*escalaX),yg-(yr*escalaY)));
					if ( (Math.abs((xr-ultimoResultado)/xr)*100 < tolerancia) && (Math.abs(yr) < tolerancia) )
					{
						System.out.println("/////////////////////////////////////////");
						System.out.println("Raiz encontrada! ");
						System.out.println("Raiz: "+xr);
						System.out.println("Iteración: "+i);
						if (cmbAlgoritmoDer.getSelectedItem().toString().equals("Regula Falsi Mejorado"))
							iteracionesDer.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						if (cmbAlgoritmoIzq.getSelectedItem().toString().equals("Regula Falsi Mejorado"))
							iteracionesIzq.setText("Raiz: "+xr+" - Iteraciones:"+(i-1));
						return;
					}
					contador++;
		//			La raiz se encuentra en el primer termino
					if((yi * yr)<0)
					{
						xu = xr;
						yu = yr;
						
						if(contador>=2)
						{
							contador = 0;
							yi = yi/2;
							miEvaluador.addVariable("x", xi);
							Double yprueba = miEvaluador.getValue();
							while (Math.abs(yprueba - yi)>0.2) //Busca el xi para ese yi/2
							{
								
								xi += 0.01;
								
								miEvaluador.addVariable("x", xi);
								yprueba = miEvaluador.getValue();
							}
						}
					}
					else
					{
						xi = xr;
						yi = yr;
						
						if(contador>=2)
						{
							contador = 0;
							yu = yu/2;
							miEvaluador.addVariable("x", xu);
							Double yprueba = miEvaluador.getValue();
							while (Math.abs(yprueba - yu)>0.02) //Busca el xu para ese yu/2
							{
								xu -= 0.01;
						
								miEvaluador.addVariable("x", xu);
								yprueba = miEvaluador.getValue();
							}
						}
					}
				}
				System.out.println("xu:"+xu+" - yu:"+yu);
				return;
			}
			else
			{
				return;
			}
		}



		double dist(double xA, double yA, double xB, double yB) {
			return (xA - xB) * (xA - xB) + (yA - yB) * (yA - yB);
		}

	}


	class SliderPanel extends JPanel {

		private static final long serialVersionUID = -4472455862245221864L;

		JSlider xSlider, ySlider; // Manejo de escala

		SliderPanel() 
		{
			setLayout(new GridLayout(1, 2));

			SliderListener auditor = new SliderListener();

			xSlider = new JSlider(JSlider.VERTICAL, 1, 200, 20);
			xSlider.addChangeListener(auditor);
			add(xSlider);

			ySlider = new JSlider(JSlider.VERTICAL, 1, 200, 20);
			ySlider.addChangeListener(auditor);
			add(ySlider);

			xSlider.setMinorTickSpacing(20);
			xSlider.setPaintTicks(true);
			xSlider.setPaintLabels(true);

			ySlider.setMinorTickSpacing(20);
			ySlider.setPaintTicks(true);
			ySlider.setPaintLabels(true);

		}

		public void ajusteEscala() 
		{   // se ejecuta si se 'oyo' algun cambio en algun Slider
			escalaX = (int) xSlider.getValue();
			escalaY = (int) ySlider.getValue();
			ZG.repaint();
			ZG2.repaint();
			ZGVeloc.repaint();
		}

		class SliderListener implements ChangeListener 
		{
			public void stateChanged(ChangeEvent e) 
			{
				ajusteEscala();
			}
		}
	}

}
