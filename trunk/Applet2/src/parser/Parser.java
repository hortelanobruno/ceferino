/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;


import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nfunk.jep.JEP;
import java.text.DecimalFormat;
import org.nfunk.jep.ParseException;
/**
 *
 * @author Administrador
 */
public class Parser {
    
    private Jep jep;
    private double h = 0.1;
    private double xmin = -3;
    private double xmax = 3;
    private List<Double> raices = new ArrayList<Double>();
    private DecimalFormat money = new DecimalFormat("0.000");

    public static void main(String args[]){
        new Parser();
    }
    
    public Parser() 
    {
        iniciarParser();
        //agregarFuncion("2(x^2)+x-10");
        agregarFuncion("x^2");
        biseccion();
        for(int i=0 ; i < raices.size() ; i++){
           System.out.println(raices.get(i));
        }
    }
    
    public void biseccion(){
        double aux,a,b;
        for(double i = xmin ; i < xmax ; i=Double.parseDouble(money.format(i + h))){
            aux = Double.parseDouble(money.format(i + h));
            try{
                a = Double.parseDouble(money.format(getValor(i)));
                b = Double.parseDouble(money.format((getValor(aux))));
                if((a==0) || (b==0)){
                    if(a==0){
                         raices.add(i);
                    }else{
                         raices.add(aux);
                    }
                }else{
                    if((a*b)<0){
                        biseccion2(i,aux);
                    }
                }
            }catch(Exception e){
                
            }
        }
    }
    
    public void biseccion2(double a, double b){
        double valorA = Double.parseDouble(money.format(getValor(a)));
        double c = Double.parseDouble(money.format((a+b)/2));
        double valorC =Double.parseDouble(money.format(getValor(c)));
        if(Double.parseDouble(money.format(valorA*valorC))<0){
            //La raiz esta en el intervalo "a" y "c"
            biseccion2(a,c);
        }else if(Double.parseDouble(money.format(valorA*valorC))>0){
            //La raiz esta en el intervalo "c" y "b"
            biseccion2(c,b);
        }else if(Double.parseDouble(money.format(valorA*valorC))==0){
            //La raiz es "c"
            raices.add(c);
        }
    }
    
    
    public void agregarFuncion(String funcion)
    {
        try {
            this.jep.parse(funcion);
        } catch (com.singularsys.jep.ParseException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void iniciarParser()
    {
            jep = new Jep();
             // Allow implicit multiplication
            jep.setImplicitMul(true);
            // Add and initialize x to 0
            jep.addVariable("x", 0);
            //jep.addFunction("sen", new org.nfunk.jep.function.Sine());
    }
    
    public Object getValor(double x){
       jep.addVariable("x", x);
       Object result = null;
       try {
            result = jep.evaluate();
            if (result instanceof Double) {
                return ((Double) jep.evaluate()).doubleValue();
            } else {
                throw new EvaluationException("Non-Double result");
            }
        } catch (EvaluationException e) {
            return result;
        }
    }
    
}
