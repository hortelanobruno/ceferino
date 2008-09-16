package minimoscuadrados;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */



/**
 * A function in the form y = a + e^bx.
 *  
 */
public class FuncionExponencial {
/** The intercept. */
    private double a;

    /** The slope of the line. */
    private double b;

    /**
     * Constructs a new line function.
     *
     * @param a  the intercept.
     * @param b  the slope.
     */
    public FuncionExponencial(double a, double b) {
        this.a = a;
        this.b = b;
    }
    
    public FuncionExponencial()
    {
        
    }

    /**
     * Returns the function value.
     *
     * @param x  the x-value.
     *
     * @return The value.
     */
    public double getValue(double x) {
        return this.a * Math.pow(Math.E, this.b*x);
    }
}
