/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parser;

import applet.SistemasDinamicos;
import com.singularsys.jep.EvaluationException;
import com.singularsys.jep.Jep;
import java.awt.Color;
import java.util.ArrayList;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 *
 * @author Administrador
 */
public class Parser {

    private Jep jep;
    private SistemasDinamicos vista;
    

    public Parser(SistemasDinamicos sis) {
        this.vista = sis;
        iniciarParser();
    }

    public Parser(SistemasDinamicos sis, String funcion) {
        this.vista = sis;
        iniciarParser();
        this.agregarFuncion(funcion);
    }


    public void agregarFuncion(String funcion) {
        try {
            this.jep.parse(funcion);
            vista.getTxtFuncion().setForeground(Color.black);
            vista.getButtonGraficar().setEnabled(true);
        } catch (com.singularsys.jep.ParseException ex) {
            vista.getTxtFuncion().setForeground(Color.red);
            vista.getButtonGraficar().setEnabled(false);
        }
    }

    private void iniciarParser() {
        jep = new Jep();
        // Allow implicit multiplication
        jep.setImplicitMul(true);
        // Add and initialize x to 0
        jep.addVariable("x", 0);
        //jep.addFunction("sen", new org.nfunk.jep.function.Sine());
    }

    public Object getValor(double x) {
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
