/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1;

/**
 *
 * @author Administrador
 */
public class PruebaSistemaEcuaciones {

    public PruebaSistemaEcuaciones() {
        //http://www.sc.ehu.es/sbweb/fisica_/numerico/matriz/matriz1.xhtml
        //sistema de ecuaciones lineales
        double[][] m1={{3, -1, 0}, {-2, 1, 1}, {2, -1, 4}};
        Matriz coef=new Matriz(m1);
        double[] n1={5, 0, 15};
        Vector ter=new Vector(n1);
        Vector solucion=Matriz.producto(Matriz.inversa(coef), ter);
        System.out.println("solución "+solucion);
        //otro sistema de ecuaciones
        double[][] m2={{7.9, 5.6, 5.7, -7.2}, {8.5, -4.8, 0.8, 3.5}, 
        {4.3, 4.2, -3.2, 9.3}, {3.2, -1.4, -8.9, 3.3}};
        coef=new Matriz(m2);
        double[] n2={6.68, 9.95, 8.6, 1};
        ter=new Vector(n2);
        solucion=Matriz.producto(Matriz.inversa(coef), ter);
        System.out.println("solución "+solucion);

    }


    public static void main(String args[]){
        new PruebaSistemaEcuaciones();
    }
}
