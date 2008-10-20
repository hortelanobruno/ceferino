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
public class PruebaParser {
    
    private JEP jep;

    public PruebaParser() {
        iniciarParser();
        getValor(3.2);
    }
    
    public void iniciarParser(){
            jep = new JEP();
            // Allow implicit multiplication
            jep.setImplicitMul(true);
            jep.addStandardFunctions();
            jep.addStandardConstants();
            jep.addComplex();
            // Add and initialize x to 0
            jep.addVariable("x", 0);
            jep.parseExpression("x+2");
    }
    
    public void getValor(double x){
        jep.setVarValue("x", x);
        System.out.println(jep.getValue());//Devuelve el valor de F(3)
    }
    
    public static void main(String args[]){
        new PruebaParser();
    }
}
