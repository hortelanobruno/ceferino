/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package parser;


import org.nfunk.jep.JEP;

/**
 *
 * @author Administrador
 */
public class Parser {
    
    private JEP jep;

    public Parser() 
    {
        iniciarParser();
    }
    
    public void agregarFuncion(String funcion)
    {
        this.jep.parseExpression(funcion);
    }
    
    public void iniciarParser()
    {
            jep = new JEP();
            jep.setImplicitMul(true);
            jep.addStandardFunctions();
            jep.addStandardConstants();
            jep.addComplex();
            //jep.addFunction("sen", new org.nfunk.jep.function.Sine());
            jep.addVariable("x", 0);
            jep.parseExpression("x");
    }
    
    public double getValor(double x){
        jep.addVariable("x", x);
        return jep.getValue();
       // System.out.println(jep.getValue());//Devuelve el valor de F(3)
    }
    
    /*public static void main(String args[]){
        new Parser();
    }*/
}
