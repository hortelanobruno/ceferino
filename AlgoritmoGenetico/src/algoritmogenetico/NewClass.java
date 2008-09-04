/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmogenetico;

/**
 *
 * @author Administrador
 */
public class NewClass {

    public NewClass() {
        for(int i=0 ; i < 100 ; i++){
            int random = (int) (Math.random() * (3));
            System.out.println("Es " + random);
        }
    }

    
    public static void main(String args[]){
        new NewClass();
    }
}
