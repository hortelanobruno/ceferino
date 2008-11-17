package applet;

import java.util.ArrayList;
import java.util.List;
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
    private List<Double> internalPositiveGraphicPoints;
    private List<Double> internalNegativeGraphicPoints;
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
    
    public List<Double> getPoints()
    {
        
        double index = this.getTimeMin();
        //double index = 0;
        double top = this.getTimeMax();
        this.internalPositiveGraphicPoints = new ArrayList<Double>();

        double funcVal =(Double)this.parser.getValor(this.getSeedX());
        double xant = this.getSeedY();
        double x;

        while(index<=top)
        {
            x = xant + (this.getH()*funcVal);
            xant = x;
            index+=this.getH();
            funcVal =(Double)this.parser.getValor(xant);
            this.internalPositiveGraphicPoints.add(x);
        }
       
        return this.internalPositiveGraphicPoints;
    }
    
    public List<Double> getNegativePoints()
    { 
        double index =0;
        double top = this.getTimeMin();
        this.internalNegativeGraphicPoints = new ArrayList<Double>();

        double funcVal =(Double)this.parser.getValor(this.getSeedX());
        double xant = this.getSeedY();
        double x;

        while(index>=top)
        {
            x = xant- (this.getH()*funcVal);
            xant = x;
            index-=this.getH();
            funcVal =(Double)this.parser.getValor(xant);
            this.internalNegativeGraphicPoints.add(x);
        }
        
        return this.internalNegativeGraphicPoints; 
    }
    
//     public HashMap<Double,Double> getPoints()
//    {
//        if(this.internalPositiveGraphicPoints == null)
//        {
//            double index = this.getTimeMin();
//            double top = this.getTimeMax();
//            this.internalPositiveGraphicPoints = new HashMap<Double, Double>();
//           
//            double funcVal =(Double)this.parser.getValor(this.getSeedX());
//            double xant = this.getSeedY();
//            double x;
//            
//            while(index<=top)
//            {
//                x = xant + (this.getH()*funcVal);
//                xant = x;
//                
//                funcVal =(Double)this.parser.getValor(xant);
//                this.internalPositiveGraphicPoints.put(index, x);
//                index+=this.getH();
//            }
//        }
//        return this.internalPositiveGraphicPoints; 
//    }

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
