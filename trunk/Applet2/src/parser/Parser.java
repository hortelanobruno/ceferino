/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import applet.SistemasDinamicos;
import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Administrador
 */
public class Parser {

    private Jep jep;
    private double h;
    private double xmin;
    private double xmax;
    private List<Double> raices;
    private DecimalFormat money;
    private SistemasDinamicos vista;
    private double corte;
    private int decimales;
    private double ultimoResultado;

    public Parser(SistemasDinamicos sis) {
        this.vista = sis;
        iniciarParser();
    }

    public Parser(SistemasDinamicos sis, double h, double xmin, double xmax, String cantDec, String funcion) {
        this.vista = sis;
        this.raices = new ArrayList<Double>();
        iniciarParser();
        this.h = h;
        this.xmax = xmax;
        this.xmin = xmin;
        this.money = new DecimalFormat(cantDec);
        money.setDecimalFormatSymbols(new DecimalFormatSymbols(Locale.ENGLISH));
        this.agregarFuncion(funcion);


    //agregarFuncion("2(x^2)+x-10");
    // agregarFuncion("x^2");
    //  biseccion();
       /* for(int i=0 ; i < raices.size() ; i++){
    System.out.println(raices.get(i));
    }*/
    }

    public List<Double> getRaices() {
        this.raices = new ArrayList<Double>();
        biseccion();
        return this.raices;
    }

    
    private void biseccion3(double xii, double xuu){
        double xi =xii; 
        double xu =xuu;
        int iteraciones = 20;
        double xr = xi;
        double yr;
        double gxi;
        double gxu;
        double gyi;
        double gyu;
        List<Double> xrs = new ArrayList<Double>();
        double yi = (Double) getValor(xi);
        double yu = (Double) getValor(xu);
        for (int i = 1; i<=iteraciones; i++)
        {
                ultimoResultado = xr;
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
                        gyi = (Double) getValor(gxi);
                        gxu = xu;
                        gyu = yu;
                }
                else if ( xr > xu )
                {
                        gxu = xr;
                        gyu = (Double) getValor(gxu);
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
                yr = (Double) getValor(xr);
                if ( (Math.abs((xr-ultimoResultado)/xr)*100 < getCorte()) && (Math.abs(yr) < getCorte()) )
                {
                        raices.add(xr);
                }
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
    }
    
    
    
    private void biseccion() {
        double aux, a, b;
        for (double i = xmin; i < xmax; i = i+h) {
            //aux = Double.parseDouble(money.format(i + h).replace(',', '.'));
            aux =(i + h);
            try {
                //a = Double.parseDouble(money.format(getValor(i)).replace(',', '.'));
                a = (Double)getValor(i);
                //b = Double.parseDouble(money.format((getValor(aux))).replace(',', '.'));
                b = (Double) getValor(aux);
                if ((a == 0) || (b == 0)) {
                    if (a == 0) {
                        if (!(raices.contains(i))) {
                            raices.add(i);
                        }
                    } else {
                        if (!(raices.contains(aux))) {
                            raices.add(aux);
                        }
                    }
                } else {
                    if ((a * b) < 0) {
                        ultimoResultado = i;
                        biseccion3(i, aux);
                    }
                }
            } catch (Exception e) {
            }
        }
    }

    private void biseccion2(double a, double b) {
        //double valorA = Double.parseDouble(money.format(getValor(a)).replace(',', '.'));
        //double c = Double.parseDouble(money.format((a + b) / 2).replace(',', '.'));
        //double valorC = Double.parseDouble(money.format(getValor(c)).replace(',', '.'));
        double valorA = (Double) getValor(a);
        double c = a+b/2;
        double valorC = (Double) getValor(c);
//        if(Double.parseDouble(money.format(valorA*valorC))<0){
//            //La raiz esta en el intervalo "a" y "c"
//            biseccion2(a,c);
//        }else if(Double.parseDouble(money.format(valorA*valorC).replace(',', '.'))>0){
//            //La raiz esta en el intervalo "c" y "b"
//            biseccion2(c,b);
//        }else if(Double.parseDouble(money.format(valorA*valorC).replace(',', '.'))==0){
//            //La raiz es "c"
//            if(!(raices.contains(c)))
//                raices.add(c);
//        }
        //PROFE  if (Double.parseDouble(money.format(Math.abs(valorC)).replace(',', '.')) < getCorte())
        if ((Math.abs((c-ultimoResultado)/c)*100 < getCorte()) && (Math.abs(valorC) < getCorte())) {
            //La raiz es "c"
            if (!(raices.contains(c))) {
                raices.add(c);
            }
        }else if ((valorA * valorC) < 0) {
            //La raiz esta en el intervalo "a" y "c"
            ultimoResultado=c;
            biseccion2(a, c);
        } else if ((valorA * valorC) > 0) {
            //La raiz esta en el intervalo "c" y "b"
            ultimoResultado=c;
            biseccion2(c, b);
        }
    }

    public void agregarFuncion(String funcion) {
        try {
            this.jep.parse(funcion);
            vista.getTxtFuncion().setForeground(Color.black);
            vista.getButtonGraficar().setEnabled(true);
        } catch (com.singularsys.jep.ParseException ex) {
            vista.getTxtFuncion().setForeground(Color.red);
            vista.getButtonGraficar().setEnabled(false);
        }
    }

    private void iniciarParser() {
        jep = new Jep();
        // Allow implicit multiplication
        jep.setImplicitMul(true);
        // Add and initialize x to 0
        jep.addVariable("x", 0);
    //jep.addFunction("sen", new org.nfunk.jep.function.Sine());
    }

    public Object getValor(double x) {
        jep.addVariable("x", x);
        Object result = null;
        try {
            result = jep.evaluate();
            if (result instanceof Double) {
                return ((Double) jep.evaluate()).doubleValue();
            } else {
                throw new EvaluationException("Non-Double result");
            }
        } catch (EvaluationException e) {
            return result;
        }
    }

    public Double getCorte() {
        return corte;
    }

    public void setCorte(Double corte) {
        this.corte = corte;
    }

    public double round(double nD, int nDec) {
        return Math.round(nD * Math.pow(10, nDec)) / Math.pow(10, nDec);
    }

    public int getDecimales() {
        return decimales;
    }

    public void setDecimales(int decimales) {
        this.decimales = decimales;
    }
}
