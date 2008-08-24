/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package function;

/**
 * A function in the form y = a + bx + cx^2.
 */
public class FuncionSegundoGrado {
/** The intercept. */
    private double a;

    /** The slope of the line. */
    private double b;
    private double c;
    /**
     * Constructs a new line function.
     *
     * @param a  the intercept.
     * @param b  the slope.
     */
    public FuncionSegundoGrado(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    /**
     * Returns the function value.
     *
     * @param x  the x-value.
     *
     * @return The value.
     */
    public double getValue(double x) {
        return this.a + this.b * x + this.c * ( x * x);
    }
}
