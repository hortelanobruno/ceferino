/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cbr;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Administrador
 */
public class Similitud {

    private List<String> input = new ArrayList<String>();
    private List<String> caso = new ArrayList<String>();
    private float valor;

    public Similitud() {
    }

    public Similitud(List<String> input, List<String> caso, float valor) {
        this.input = input;
        this.caso = caso;
        this.valor = valor;
    }
    

    public List<String> getInput() {
        return input;
    }

    public void setInput(List<String> input) {
        this.input = input;
    }

    public List<String> getCaso() {
        return caso;
    }

    public void setCaso(List<String> caso) {
        this.caso = caso;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    
}
