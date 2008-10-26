package applet;

import java.util.Vector;
import parser.Parser;

public class CambioDeConcavidadFinder 
{
    private Parser parser;
    private String funcion;
    private double xInicial;
    private double xFinal;
    private double h;
    double[] cambios;
    
    public CambioDeConcavidadFinder(String funcion, double xInicial, double xFinal, double h)
    {
        this.parser = new Parser();
        parser.agregarFuncion(funcion);
        this.setFuncion(funcion);
        this.setXInicial(xInicial);
        this.setXFinal(xFinal);
        this.setH(h);
    }
    
    private double[] vectorToDoubleArray(Vector<Double> vecAux)
    {
         this.cambios = new double[vecAux.size()];
         
         for(int i = 0; i<vecAux.size();i++)     
           this.cambios[i] = vecAux.elementAt(i);
         
         return this.cambios;
    }

    public double[] getCambiosConcavidad()    
    {
        double start = this.getXInicial() + this.getH();
        double top = (this.getXFinal() - this.getH());
        Vector<Double> vecAux = new Vector<Double>();
        
        while(start <= top)
        {
            double ant = this.parser.getValor(start - this.getH());
            double post = this.parser.getValor(start + this.getH());
            
                /* maximo */                            /* minimo  */
            if(  (start > ant) && (start > post)  || (  (start < ant)&& (start <post) ) )
                vecAux.add(start);
            
            start += this.getH();
        }
        
        return this.vectorToDoubleArray(vecAux);
    }
    
    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
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
