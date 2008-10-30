/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cbr;

/**
 *
 * @author Administrador
 */
public class Locacion {

    private String id;
    private int cantidadPersonas;
    private String categoria;

    public Locacion() {
    }

    public Locacion(String id, int cantidadPersonas, String categoria) {
        this.id = id;
        this.cantidadPersonas = cantidadPersonas;
        this.categoria = categoria;
    }
    
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    public boolean equals(String id) {
        return this.id.equals(id);
    }
}
