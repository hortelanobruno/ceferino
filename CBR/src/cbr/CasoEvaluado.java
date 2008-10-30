/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cbr;

/**
 *
 * @author Administrador
 */
public class CasoEvaluado {

    private Caso caso;
    private float valor;

    public CasoEvaluado() {
    }

    public CasoEvaluado(Caso caso, float valor) {
        this.caso = caso;
        this.valor = valor;
    }

    public Caso getCaso() {
        return caso;
    }

    public void setCaso(Caso caso) {
        this.caso = caso;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
    
    
}
