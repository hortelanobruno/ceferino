/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cbr;

import java.util.List;

/**
 *  Aca hay que poner todas las dimensiones,
 * 
 * 
 * @author Administrador
 */
public class Caso {

    private List<String> bebida;
    private List<String> comida;
    private Locacion locacion;
    private List<String> musica;
    private List<String> show;

    public Caso() {
    }

    public Caso(List<String> bebida, List<String> comida, Locacion locacion, List<String> musica, List<String> show) {
        this.bebida = bebida;
        this.comida = comida;
        this.locacion = locacion;
        this.musica = musica;
        this.show = show;
    }

    
    public List<String> getBebida() {
        return bebida;
    }

    public void setBebida(List<String> bebida) {
        this.bebida = bebida;
    }

    public List<String> getComida() {
        return comida;
    }

    public void setComida(List<String> comida) {
        this.comida = comida;
    }

    public Locacion getLocacion() {
        return locacion;
    }

    public void setLocacion(Locacion locacion) {
        this.locacion = locacion;
    }

    public List<String> getMusica() {
        return musica;
    }

    public void setMusica(List<String> musica) {
        this.musica = musica;
    }

    public List<String> getShow() {
        return show;
    }

    public void setShow(List<String> show) {
        this.show = show;
    }

}
