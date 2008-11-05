package applet;

import parser.Parser;

/**
 *
 * @author Dexter
 */

/**
 * xk+1 = xk + h.f(kh,xk)
 * 
 */

public class Euler 
{
    private double h;
    private double seedx;
    private double seedy;
    private Parser parser;
    private double timeMax;
    private double timeMin;
    private double[] internalGraphicPoints;
    private double[] internalError;
    private String funcion;
    
    
    public Euler(double h, double seedx, double seedy, String funcion, double timemin, double timemax, Parser jep) 
    {
        this.parser = jep;
        parser.agregarFuncion(funcion);
        this.setFuncion(funcion);
        this.setTimeMax(timemax);
        this.setTimeMin(timemin);
        this.setH(h);
        this.setSeedX(seedx);
        this.setSeedY(seedy);
    }

    public double getH() {
        return h;
    }

    public void setH(double h) {
        this.h = h;
    }

    public double getSeedX() {
        return seedx;
    }

    public void setSeedX(double seed) {
        this.seedx= seed;
    }
    
    public double getSeedY() {
        return seedy;
    }

    public void setSeedY(double seed) {
        this.seedy= seed;
    }
    
    public double[] getError()
    {
        return(this.internalError!=null)?this.internalError:null;
    }
    
    public double[] getPoints()
    {
        if(this.internalGraphicPoints == null)
        {
            double index = this.getTimeMin();
            double top = this.getTimeMax();
            int size =((int) Math.ceil( (this.getTimeMax() - this.getTimeMin())/h));
            this.internalGraphicPoints = new double[size+1];
            this.internalError = new double[size+1];
            int i = 0; 

            double funcVal =(Double)this.parser.getValor(this.getSeedX());
            double xant = this.getSeedY();
            double x;
            
            while(index<=top)
            {
                x = xant + (this.getH()*funcVal);
                xant = x;
                index+=this.getH();
                funcVal =(Double)this.parser.getValor(xant);
                this.internalGraphicPoints[i] = x;
                double error = (Double)this.parser.getValor(index) - x;
                this.internalError[i++] = error;
            }
        }
        
        return this.internalGraphicPoints; 
    }

    public double getTimeMax() {
        return timeMax;
    }

    public void setTimeMax(double time) {
        this.timeMax = time;
    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public double getTimeMin() {
        return timeMin;
    }

    public void setTimeMin(double timeMin) {
        this.timeMin = timeMin;
    }
}
