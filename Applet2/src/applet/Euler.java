package applet;

import parser.Parser;

/**
 *
 * @author Dexter
 */

/**
 * xk+1 = xk + h + f(kh,xk)
 * 
 */

public class Euler 
{
    private double h;
    private double seed;
    private Parser parser;
    private double xInicial;
    private double xFinal;
    private double[] internalGraphicPoints;
    private double[] internalError;
    
    
    public Euler(double h, double seed, String funcion, double xInicial, double xFinal) 
    {
        this.parser = new Parser();
        parser.agregarFuncion(funcion);
        this.setXFinal(xFinal);
        this.setXInicial(xInicial);
        this.setH(h);
        this.setSeed(seed);
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getSeed() {
        return seed;
    }

    public void setSeed(double seed) {
        this.seed = seed;
    }
    
    public double[] getError()
    {
        return(this.internalError!=null)?this.internalError:null;
    }
    
    public double[] getGraphicPoints()
    {
        if(this.internalGraphicPoints == null)
        {
            double index = this.getXInicial();
            double top = this.getXFinal();
            int size =((int) Math.ceil((this.getXFinal()-this.getXInicial())/h))+1;
            this.internalGraphicPoints = new double[size];
            this.internalError = new double[size];
            int i = 0;
            double x = this.parser.getValor(this.getSeed());
            this.internalGraphicPoints[i] = x;
            this.internalError[i++] = 0;
            
            while(index <= top)
            {
                index+=this.getH();
                x = x + (this.getH()*x);
                this.internalGraphicPoints[i] = x;
                double error = this.parser.getValor(index) - x;
                this.internalError[i++] = error;
            }
        }
        
        return this.internalGraphicPoints; 
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
}
