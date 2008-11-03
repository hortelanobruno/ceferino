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
public class Logica {

    private List<Caso> baseCasos;
    private Funcion funcion;
    
    public Logica() {
        cargarBaseDeCasos();
        funcion = new Funcion();
    }
    
    public void cargarBaseDeCasos(){
        baseCasos = new ArrayList<Caso>();
        cargarData1();
        cargarData2();
        cargarData3();
        cargarData4();
        cargarData5();
    }
    
    public List<CasoEvaluado> calcularSimilitud(List<List<String>> input, List<Float> importancias){
        return funcion.calcularSimilitud(input, baseCasos, importancias);
    }
    
    
    
    private void cargarData1(){
        List<String> bebida = new ArrayList<String>();
        List<String> comida = new ArrayList<String>();
        Locacion locacion = new Locacion("Los alamos",100,"Country");
        List<String> musica = new ArrayList<String>();
        List<String> show = new ArrayList<String>();
        
        bebida.add("Gaseosa");
        bebida.add("Vino");
        bebida.add("Cerveza");
        
        comida.add("Asado");
        comida.add("Ensalada");
        comida.add("Pizza");
        
        musica.add("Salsa");
        musica.add("Reggaeton");
        musica.add("Pop");
        
        show.add("Mago");
        show.add("Cantante");
        
        Caso caso = new Caso(bebida,comida,locacion,musica,show);
        baseCasos.add(caso);
    }
    private void cargarData2(){
        List<String> bebida = new ArrayList<String>();
        List<String> comida = new ArrayList<String>();
        Locacion locacion = new Locacion("Park",500,"Salon de hotel 5 estrellas");
        List<String> musica = new ArrayList<String>();
        List<String> show = new ArrayList<String>();
        
        bebida.add("Champagne");
        bebida.add("Vino");
        bebida.add("Whisky");
        
        comida.add("Sushi");
        comida.add("Ensalada");
        comida.add("Caviar");
        
        musica.add("Clasica");
        musica.add("Jazz");
        musica.add("Pop");
        
        Caso caso = new Caso(bebida,comida,locacion,musica,show);
        baseCasos.add(caso);
    }
    private void cargarData3(){
        List<String> bebida = new ArrayList<String>();
        List<String> comida = new ArrayList<String>();
        Locacion locacion = new Locacion("Museum",800,"Local bailable");
        List<String> musica = new ArrayList<String>();
        List<String> show = new ArrayList<String>();
        
        bebida.add("Gaseosa");
        bebida.add("Cerveza");
        bebida.add("Champagne");
        
        comida.add("Asado");
        comida.add("Canapes");
        comida.add("Sandwiches");
        
        musica.add("Reggaeton");
        musica.add("Rock");
        musica.add("Pop");
        
        show.add("Baile");
        
        Caso caso = new Caso(bebida,comida,locacion,musica,show);
        baseCasos.add(caso);
    }
    private void cargarData4(){
        List<String> bebida = new ArrayList<String>();
        List<String> comida = new ArrayList<String>();
        Locacion locacion = new Locacion("Palace",400,"Salon de hotel 3 estrellas");
        List<String> musica = new ArrayList<String>();
        List<String> show = new ArrayList<String>();
        
        bebida.add("Agua");
        bebida.add("Gaseosa");
        bebida.add("Champagne");
        
        comida.add("Empanada");
        comida.add("Pizza");
        comida.add("Sandwiches");
        
        musica.add("Reggaeton");
        musica.add("Cumbia");
        musica.add("Pop");
        
        show.add("Malabarista");
        
        Caso caso = new Caso(bebida,comida,locacion,musica,show);
        baseCasos.add(caso);
    }
    private void cargarData5(){
        List<String> bebida = new ArrayList<String>();
        List<String> comida = new ArrayList<String>();
        Locacion locacion = new Locacion("Estrella",150,"Club de barrio");
        List<String> musica = new ArrayList<String>();
        List<String> show = new ArrayList<String>();
        
        bebida.add("Agua");
        bebida.add("Gaseosa");
        bebida.add("Jugo");
        
        comida.add("Empanada");
        comida.add("Paty");
        comida.add("Pancho");
        
        musica.add("Punk");
        musica.add("Hard Rock");
        musica.add("Metal");
        
        show.add("Banda en vivo");
        
        Caso caso = new Caso(bebida,comida,locacion,musica,show);
        baseCasos.add(caso);
    }

}
