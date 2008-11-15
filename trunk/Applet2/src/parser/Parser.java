/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import applet.SistemasDinamicos;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.nfunk.jep.JEP;
import org.nfunk.jep.ParseException;

/**
 *
 * @author Administrador
 */
public class Parser {

    private JEP jep;
    private SistemasDinamicos vista;

    public Parser(String funcion) {
        iniciarParser();
        this.addFunc(funcion);
    }

    public Parser(SistemasDinamicos sis) {
        this.vista = sis;
        iniciarParser();
    }

    public Parser(SistemasDinamicos sis, String funcion) {
        this.vista = sis;
        iniciarParser();
        this.agregarFuncion(funcion);
    }

    private void addFunc(String func) {
        try {
            this.jep.parse(func);
        } catch (ParseException ex) {
            Logger.getLogger(Parser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void agregarFuncion(String funcion) {
        try {
            this.jep.parseExpression(funcion);
            vista.getTxtFuncion().setForeground(Color.black);
            vista.getButtonGraficar().setEnabled(true);
        } catch (Exception ex) {
            vista.getTxtFuncion().setForeground(Color.red);
            vista.getButtonGraficar().setEnabled(false);
        }
    }

    private void iniciarParser() {
        jep = new JEP();
        // Allow implicit multiplication
        jep.setImplicitMul(true);
        // Add and initialize x to 0
        jep.addVariable("x", 0);
    //jep.addFunction("sen", new org.nfunk.jep.function.Sine());
//        jep = new JEP();
//        jep.addStandardFunctions(); // agrega las funciones matematicas comunes.
//        jep.addStandardConstants(); // agrega las constantes estandar.
//        jep.addComplex(); // agrega numeros complejos.
//        jep.addFunction("sen", new org.nfunk.jep.function.Sine());// habilitar 'sen'
//        jep.addVariable("x", 0);
//        jep.setImplicitMul(true); // permite 2x en vez de 2*x
        
                
    }

    public Object getValor(double x) {
        jep.addVariable("x", x);
        Object result = null;
       
        try
        {
            result = jep.getValueAsObject();
            if (result instanceof Double) {
                return ((Double) jep.getValue()).doubleValue();
            } else {
               throw new Exception();
            }
        }
        catch(Exception e)
        {
            return 0;
        }
    }
}
