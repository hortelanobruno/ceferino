package applet;

import java.text.DecimalFormat;
import java.util.Vector;
import javax.swing.JOptionPane;
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
    
    private double[] vectorToDoubleArray(Vector<Double> vecAux)
    {
         this.raices = new double[vecAux.size()];
         
         for(int i = 0; i<vecAux.size();i++)     
           this.raices[i] = vecAux.elementAt(i);
         
         return this.raices;
    }
    
    /*
     * 
     * DecimalFormat df = new DecimalFormat();
                df.setMaximumFractionDigits(this.cantDecimales);
     * */
    
    private double redondear(double val)
    {
         DecimalFormat df = new DecimalFormat();
         df.setMaximumFractionDigits(this.cantDec);
         
         String aux = df.format(val);
         aux = aux.replace(',', '.');
         val = Double.parseDouble(aux);
         return val;
    }
    
    private Double raizPorBiseccion(double x1, double x2)
    {
         double epsilon = 0.001;
         
         double m;
         for (m= (x1+x2)/2.0; Math.abs(x1-x2)> epsilon; m= (x1+x2)/2.0)
         {
                if (((Double)this.parser.getValor(x1)*(Double)this.parser.getValor(m)) <= 0.0)
                    x2= m; // Utiliza el subintervalo izquierdo
                else
                    x1= m; // Utiliza el subintervalo derecho
         }
         
        m= this.redondear(m);
       // System.out.println(m);
        double rI = m-0.005;
        double rS = m+0.005;
        double funVal = (Double)this.parser.getValor(m);
        funVal = this.redondear(funVal);
        if( (funVal>rI)&&(funVal<rS)) return m;
        return null;
    }
    
    private Vector<Double> eliminarRepetidos(Vector<Double> vec)
    {
        Vector<Double> ret = new Vector<Double>();
        
        for(int i = 0; i < vec.size();i++)
        {
            if( !(ret.contains(vec.elementAt(i)))  )
                ret.add(vec.elementAt(i));
        }
        
        return ret;
    }
    
    private double derivada(double seed) //Dereivada centrada: (fi+1 - fi-1)2h
    {
        double adelante = (Double) this.parser.getValor(seed + this.getH());
        double atras = (Double) this.parser.getValor(seed - this.getH());
        return (adelante - atras)/(2*this.getH());
    }
    
    private double raizPorNewtonRaphson(double seed)
    {
        return seed - ((Double)this.parser.getValor(seed)/(Double)this.derivada(seed));
    }
    
    private double getRaizPorNewton()
    {
        double seed = (Math.random()*this.getXFinal())+this.getXInicial();
        
        for(int i = 0; i< 200;i++)
        {
            double xold = seed;
            seed = this.raizPorNewtonRaphson(seed);
            
            if(seed == xold)  return seed;
        }
        
        return seed;
    }
    
    public double[] getRaices()
    {    
        
        Vector<Double> vec = new Vector<Double>();
        
        for(int i = 0; i< 100;i++)
        {
            double temp = this.getRaizPorNewton();
            if(!(vec.contains((Double)temp)))
            {
                vec.add(temp);
            }
        }
        
        return this.vectorToDoubleArray(vec);
     /*   Vector<Double> vecAux = new Vector<Double>();
        double index = this.getXInicial();
        double next = index + this.getH();
        
        while(index<=this.getXFinal())
        {
            Double auxRoot = this.raizPorBiseccion(index, next);
            
            if (auxRoot!=null)
            {
                double a = auxRoot;
                a = Math.round(a);
                vecAux.add(auxRoot);
            }
            
            index = next;
            next += this.getH();
        }
        
        vecAux = this.eliminarRepetidos(vecAux);
        
        this.raices = new double[vecAux.size()];
           
        for(int i = 0; i<vecAux.size();i++)
            this.raices[i] = vecAux.elementAt(i);
        
       // System.out.println("cant:" + vecAux.size());
        return this.raices;
   
      
      
      /*   if(this.raices == null)
        {
           DecimalFormat df = new DecimalFormat();
           df.setMaximumFractionDigits(this.cantDec);
           double index = this.getXInicial();
                   
                   /* String aux = df.format(result[i]);
                    aux = aux.replace(',', '.');
                    result[i] = Double.parseDouble(aux);*/
           
       /*    Vector<Double> vecAux = new Vector<Double>();
           
           while(index<=this.getXFinal())
           {
               
               double val = this.parser.getValor(index);
               String aux = df.format(val);
               aux = aux.replace(',', '.');
               val = Double.parseDouble(aux);
              // System.out.println("x: " + index + "  y: " + val);
               
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
        else return this.raices;*/
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
