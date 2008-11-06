/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package applet;

import jv.function.PuFunction;
import jv.vecmath.PdVector;
import jvx.numeric.PnRootFinder;

/**
 *
 * @author Administrador
 */
public class RootFinder {

    private PuFunction m_fx;
    private SistemasDinamicos vista;
    
    public RootFinder(SistemasDinamicos sistemas) {
        this.vista = sistemas;
        m_fx = new PuFunction(1,1);
        String[] variable = new String[1];
        variable[0] = "x";
        m_fx.setVariables(variable);
        m_fx.setName("Function Expression");
        
    }

    
    /**
     * Compute roots of function in a specified interval.
     * Method just invokes a numerical algorithm, and assigns roots to pointset of roots.
     */
    public double[] findRoot(String eq,double min, double max, int cantMaxRoot) {
            m_fx.setExpression(eq);
	    m_fx.update(m_fx);
            PdVector root = PnRootFinder.findRoots(m_fx,min,max,cantMaxRoot);
            if (root.getEntries().length == 0) {
                //Evaluar a la funcion en 0 para ver si hay raiz
                try{
                    double value = (Double) vista.getParser().getValor(0);
                    if(value == 0){
                        root.setEntry(0, 0);
                    }
                }catch(Exception e){
                    
                }
            }
            return root.getEntries();
    }
}
