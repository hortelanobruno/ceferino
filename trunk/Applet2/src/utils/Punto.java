/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utils;

/**
 *
 * @author Dexter
 */
public class Punto 
{
    private double x;
    private double y;
    
    public Punto()
    {
        this.setX(0);
        this.setY(0);
    }

    public Punto(double x, double y)
    {
        this.setX(x);
        this.setY(y);
    }
    
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
