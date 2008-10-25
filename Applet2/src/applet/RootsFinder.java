package applet;

import java.text.DecimalFormat;
import java.util.Vector;
import parser.Parser;

/**
 *
 * @author Dexter
 */
public class RootsFinder 
{
   private Parser parser;
   private String funcion;
   private double[] raices;
   private double xInicial;
   private double xFinal;
   private double h;
   private int cantDec;
   
   public RootsFinder(String funcion, double xInicial, double xFinal, double h, int cantDec)
   {
       this.parser = new Parser();
       this.parser.agregarFuncion(funcion);
       this.setH(h);
       this.setFuncion(funcion);
       this.setXInicial(xInicial);
       this.setXFinal(xFinal);
       this.cantDec = cantDec;
   }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }
    
    /*
     * 
     * DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(this.cantDecimales);
     * */
    
    public double[] getRaices()
    {
        if(this.raices == null)
        {
           DecimalFormat df = new DecimalFormat();
           df.setMaximumFractionDigits(this.cantDec);
           double index = this.getXInicial();
                   
                   /* String aux = df.format(result[i]);
                    aux = aux.replace(',', '.');
                    result[i] = Double.parseDouble(aux);*/
           
           Vector<Double> vecAux = new Vector<Double>();
           
           while(index<=this.getXFinal())
           {
               double val = this.parser.getValor(index);
               String aux = df.format(val);
               aux = aux.replace(',', '.');
               val = Double.parseDouble(aux);
               
               if(val == 0d)
               {
                   vecAux.add(index);
               }
               
               index += this.getH();
           }
           
           this.raices = new double[vecAux.size()];
           
           for(int i = 0; i<vecAux.size();i++)
                this.raices[i] = vecAux.elementAt(i);
           
           return this.raices;
        }
        else return this.raices;
    }

    public double getXInicial() {
        return xInicial;
    }

    public void setXInicial(double xInicial) {
        this.xInicial = xInicial;
    }

    public double getXFinal() {
        return xFinal;
    }

    public void setXFinal(double xFinal) {
        this.xFinal = xFinal;
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }
   
}
