/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package function;


/**
 * A function in the form y = a + bx.
 */
public class FuncionLineal {
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
    public FuncionLineal(double a, double b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Returns the function value.
     *
     * @param x  the x-value.
     *
     * @return The value.
     */
    public double getValue(double x) {
        return this.a + this.b * x;
    }
}
