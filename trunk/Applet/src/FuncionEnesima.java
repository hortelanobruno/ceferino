

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class FuncionEnesima 
{
    private List<Double> coef;
    private int grado;
    /**
     * Constructs a new line function.
     *
     * @param a  the intercept.
     * @param b  the slope.
     */
    
    public FuncionEnesima()
    {
        
    }
    
    public FuncionEnesima(ArrayList<Double> coeficientes, int grado) 
    {
        this.setGrado(grado);
        this.coef = new ArrayList<Double>();
        this.coef.addAll(coeficientes);
    }

    /*
     * Returns the function value.
     *
     * @param x  the x-value.
     *
     * @return The value.
     */
    public double getValue(double x) {
        double aux = coef.get(0);
        for(int i=1 ; i< grado +1 ; i++)
            aux = aux + coef.get(i) *(Math.pow(x, i));
        return aux;
    }
    
    public String getFunctionString()
    {
        StringBuilder sb = new StringBuilder();
        
        sb.append("f(X) = ");
        for(int i = this.getGrado();i>-1;i--)
        {
            double co = this.coef.get(i);
            if(co != 0.0f)
            {
                if(i == 0) 
                {
                    if(co != 0.0) sb.append(String.valueOf(co));
                }
                else
                {
                    if(i == 1)
                    {   
                        if(Math.abs(co) != 1.0)sb.append(co + "X" + " + ");
                        else sb.append("X" + " + ");
                    }
                    else
                    {
                        if(Math.abs(co) != 1.0) sb.append(co + "X^" + i + " + ");
                        else  sb.append("X^" + i + " + ");
                    }
                }
            }
        }
        char ult = sb.toString().charAt(sb.toString().length()-2);
        if( (ult == '+')) 
            return sb.toString().substring(0, sb.toString().length()-2);
        else 
            return sb.toString().substring(0, sb.toString().length());
    }

    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }
}
