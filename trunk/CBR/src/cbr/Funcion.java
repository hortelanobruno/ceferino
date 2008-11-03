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
public class Funcion {

    private List<Similitud> bebida;
    private List<Similitud> comida;
    private List<Similitud> locacion;
    private List<Similitud> musica;
    private List<Similitud> show;
    private List<Caso> baseCasos;

    public Funcion() {
        //TODO FALTA CARGAR LAS TABLAS EN LOS METODOS
        cargarTablas();
    }
    
    public void cargarTablas(){
        cargarTablaBebida();
        cargarTablaComida();
        cargarTablaLocacion();
        cargarTablaMusica();
        cargarTablaShow();
    }
    
    public List<CasoEvaluado> calcularSimilitud(List<List<String>> input, List<Caso> baseCasos, List<Float> importancias){
        List<CasoEvaluado> casos = new ArrayList<CasoEvaluado>();
        for(int i=0 ; i < baseCasos.size() ; i++){
            List<Float> valor = new ArrayList<Float>();
            Caso caso = baseCasos.get(i);
            List<String> dimensionBebida = input.get(0);
            List<String> casoBebida = caso.getBebida();
            float bebidaValor = obtenerValorSimilitud(dimensionBebida, casoBebida, "bebida");
            List<String> dimensionComida = input.get(1);
            List<String> casoComida = caso.getComida();
            float comidaValor = obtenerValorSimilitud(dimensionComida, casoComida, "comida");
            List<String> dimensionLocacion = input.get(2);
            Locacion casoLocacion = caso.getLocacion();
            List<String> aaaa = new ArrayList<String>();
            aaaa.add(casoLocacion.getCategoria());
            float locacionValor = obtenerValorSimilitud(dimensionLocacion, aaaa, "locacion");
            List<String> dimensionMusica = input.get(3);
            List<String> casoMusica = caso.getMusica();
            float musicaValor = obtenerValorSimilitud(dimensionMusica, casoMusica, "musica");
            List<String> dimensionShow = input.get(4);
            List<String> casoShow = caso.getShow();
            float showValor = obtenerValorSimilitud(dimensionShow, casoShow, "show");
            valor.add(bebidaValor);
            valor.add(comidaValor);
            valor.add(locacionValor);
            valor.add(musicaValor);
            valor.add(showValor);
            float valorCaso = evaluarCaso(valor,importancias);
            CasoEvaluado casito = new CasoEvaluado(caso,valorCaso);
            casos.add(casito);
        }
        return casos;
    }
    
    public float evaluarCaso(List<Float> valor,List<Float> importancias ){
        float valorCaso = 0;
        for(int i=0 ; i < valor.size() ; i++){
            valorCaso += valor.get(i)*importancias.get(i);
        }
        float aux = 0;
        for(int i=0 ; i < importancias.size() ; i++){
            aux += importancias.get(i);
        }
        return valorCaso/aux;
    }

    public float obtenerValorSimilitud(List<String> input, List<String> caso, String tipo){
        if(tipo.equalsIgnoreCase("bebida")){
            for(int i=0 ; i< bebida.size() ; i++){
                Similitud sim = bebida.get(i);
                if(sim.getInput().containsAll(input)&&sim.getCaso().containsAll(caso)){
                    return sim.getValor();
                }else if(sim.getInput().containsAll(caso)&&sim.getCaso().containsAll(input)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("comida")){
            for(int i=0 ; i< comida.size() ; i++){
                Similitud sim = comida.get(i);
                if(sim.getInput().containsAll(input)&&sim.getCaso().containsAll(caso)){
                    return sim.getValor();
                }else if(sim.getInput().containsAll(caso)&&sim.getCaso().containsAll(input)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("locacion")){
            for(int i=0 ; i< locacion.size() ; i++){
                Similitud sim = locacion.get(i);
                if(sim.getInput().containsAll(input)&&sim.getCaso().containsAll(caso)){
                    return sim.getValor();
                }else if(sim.getInput().containsAll(caso)&&sim.getCaso().containsAll(input)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("musica")){
            for(int i=0 ; i< musica.size() ; i++){
                Similitud sim = musica.get(i);
                if(sim.getInput().containsAll(input)&&sim.getCaso().containsAll(caso)){
                    return sim.getValor();
                }else if(sim.getInput().containsAll(caso)&&sim.getCaso().containsAll(input)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("show")){
            for(int i=0 ; i< show.size() ; i++){
                Similitud sim = show.get(i);
                if(sim.getInput().containsAll(input)&&sim.getCaso().containsAll(caso)){
                    return sim.getValor();
                }else if(sim.getInput().containsAll(caso)&&sim.getCaso().containsAll(input)){
                    return sim.getValor();
                }
            }
        }
        System.out.println("No encontro valor en la similitud");
        return 0;
    }
    
    public void cargarTablaBebida(){
        Similitud sim1 = null;
        bebida = new ArrayList<Similitud>();
        sim1 = new Similitud();
        sim1.getInput().add("Agua");
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Jugo");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Jugo");
        sim1.setValor(1F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Agua");
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Champagne");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Jugo");
        sim1.setValor(0.7F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Agua");
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Champagne");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Champagne");
        sim1.setValor(1F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Cerveza");
        sim1.getInput().add("Champagne");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Jugo");
        sim1.setValor(0.3F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Cerveza");
        sim1.getInput().add("Champagne");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Champagne");
        sim1.setValor(0.5F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Cerveza");
        sim1.getInput().add("Champagne");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Cerveza");
        sim1.getCaso().add("Champagne");
        sim1.setValor(1F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Champagne");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Whisky");
        sim1.getCaso().add("Champagne");
        sim1.getCaso().add("Vino");
        sim1.getCaso().add("Whisky");
        sim1.setValor(1F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Champagne");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Whisky");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Jugo");
        sim1.setValor(0F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Champagne");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Whisky");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Champagne");
        sim1.setValor(0.2F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Champagne");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Whisky");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Cerveza");
        sim1.getCaso().add("Champagne");
        sim1.setValor(0.3F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Cerveza");
        sim1.getCaso().add("Champagne");
        sim1.getCaso().add("Vino");
        sim1.getCaso().add("Whisky");
        sim1.setValor(0.4F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Cerveza");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Cerveza");
        sim1.getCaso().add("Champagne");
        sim1.setValor(0.7F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Cerveza");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Champagne");
        sim1.setValor(0.5F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Cerveza");
        sim1.getCaso().add("Agua");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Jugo");
        sim1.setValor(0.3F);
        bebida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Gaseosa");
        sim1.getInput().add("Vino");
        sim1.getInput().add("Cerveza");
        sim1.getCaso().add("Gaseosa");
        sim1.getCaso().add("Vino");
        sim1.getCaso().add("Cerveza");
        sim1.setValor(1F);
        bebida.add(sim1);
    }
    public void cargarTablaComida(){
        Similitud sim1 = null;
        comida = new ArrayList<Similitud>();
        sim1 = new Similitud();
        sim1.getInput().add("Empanada");
        sim1.getInput().add("Paty");
        sim1.getInput().add("Pancho");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Paty");
        sim1.getCaso().add("Pancho");
        sim1.setValor(1F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Empanada");
        sim1.getInput().add("Pizza");
        sim1.getInput().add("Sandwiches");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Paty");
        sim1.getCaso().add("Pancho");
        sim1.setValor(0.3F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Empanada");
        sim1.getInput().add("Pizza");
        sim1.getInput().add("Sandwiches");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Pizza");
        sim1.getCaso().add("Sandwiches");
        sim1.setValor(1F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Canapes");
        sim1.getInput().add("Sandwiches");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Paty");
        sim1.getCaso().add("Pancho");
        sim1.setValor(0.1F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Canapes");
        sim1.getInput().add("Sandwiches");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Pizza");
        sim1.getCaso().add("Sandwiches");
        sim1.setValor(0.4F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Canapes");
        sim1.getInput().add("Sandwiches");
        sim1.getCaso().add("Asado");
        sim1.getCaso().add("Canapes");
        sim1.getCaso().add("Sandwiches");
        sim1.setValor(1F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Sushi");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Caviar");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Paty");
        sim1.getCaso().add("Pancho");
        sim1.setValor(0F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Sushi");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Caviar");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Pizza");
        sim1.getCaso().add("Sandwiches");
        sim1.setValor(0F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Sushi");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Caviar");
        sim1.getCaso().add("Asado");
        sim1.getCaso().add("Canapes");
        sim1.getCaso().add("Sandwiches");
        sim1.setValor(0F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Sushi");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Caviar");
        sim1.getCaso().add("Sushi");
        sim1.getCaso().add("Ensalada");
        sim1.getCaso().add("Caviar");
        sim1.setValor(1F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Pizza");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Paty");
        sim1.getCaso().add("Pancho");
        sim1.setValor(0F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Pizza");
        sim1.getCaso().add("Empanada");
        sim1.getCaso().add("Pizza");
        sim1.getCaso().add("Sandwiches");
        sim1.setValor(0.4F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Pizza");
        sim1.getCaso().add("Asado");
        sim1.getCaso().add("Canapes");
        sim1.getCaso().add("Sandwiches");
        sim1.setValor(0.4F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Pizza");
        sim1.getCaso().add("Sushi");
        sim1.getCaso().add("Ensalada");
        sim1.getCaso().add("Caviar");
        sim1.setValor(0.2F);
        comida.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Asado");
        sim1.getInput().add("Ensalada");
        sim1.getInput().add("Pizza");
        sim1.getCaso().add("Asado");
        sim1.getCaso().add("Ensalada");
        sim1.getCaso().add("Pizza");
        sim1.setValor(1F);
        comida.add(sim1);
    }
    public void cargarTablaLocacion(){
        Similitud sim1 = null;
        locacion = new ArrayList<Similitud>();
        sim1 = new Similitud();
        sim1.getInput().add("Club de barrio");
        sim1.getCaso().add("Club de barrio");
        sim1.setValor(1F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salon de hotel 3 estrellas");
        sim1.getCaso().add("Club de barrio");
        sim1.setValor(0.4F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salon de hotel 3 estrellas");
        sim1.getCaso().add("Salon de hotel 3 estrellas");
        sim1.setValor(1F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Local bailable");
        sim1.getCaso().add("Club de barrio");
        sim1.setValor(0.6F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Local bailable");
        sim1.getCaso().add("Salon de hotel 3 estrellas");
        sim1.setValor(0.3F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Local bailable");
        sim1.getCaso().add("Local bailable");
        sim1.setValor(1F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salon de hotel 5 estrellas");
        sim1.getCaso().add("Club de barrio");
        sim1.setValor(0F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salon de hotel 5 estrellas");
        sim1.getCaso().add("Salon de hotel 3 estrellas");
        sim1.setValor(0.3F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salon de hotel 5 estrellas");
        sim1.getCaso().add("Local bailable");
        sim1.setValor(0F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salon de hotel 5 estrellas");
        sim1.getCaso().add("Salon de hotel 5 estrellas");
        sim1.setValor(1F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Country");
        sim1.getCaso().add("Club de barrio");
        sim1.setValor(0F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Country");
        sim1.getCaso().add("Salon de hotel 3 estrellas");
        sim1.setValor(0.3F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Country");
        sim1.getCaso().add("Local bailable");
        sim1.setValor(0F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Country");
        sim1.getCaso().add("Salon de hotel 5 estrellas");
        sim1.setValor(0.5F);
        locacion.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Country");
        sim1.getCaso().add("Country");
        sim1.setValor(1F);
        locacion.add(sim1);
    }
    public void cargarTablaMusica(){
        Similitud sim1 = null;
        musica = new ArrayList<Similitud>();
        sim1 = new Similitud();
        sim1.getInput().add("Punk");
        sim1.getInput().add("Hard Rock");
        sim1.getInput().add("Metal");
        sim1.getCaso().add("Punk");
        sim1.getCaso().add("Hard Rock");
        sim1.getCaso().add("Metal");
        sim1.setValor(1F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Cumbia");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Punk");
        sim1.getCaso().add("Hard Rock");
        sim1.getCaso().add("Metal");
        sim1.setValor(0F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Cumbia");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Cumbia");
        sim1.getCaso().add("Pop");
        sim1.setValor(1F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getInput().add("Rock");
        sim1.getCaso().add("Punk");
        sim1.getCaso().add("Hard Rock");
        sim1.getCaso().add("Metal");
        sim1.setValor(0.3F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getInput().add("Rock");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Cumbia");
        sim1.getCaso().add("Pop");
        sim1.setValor(0.8F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getInput().add("Rock");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Pop");
        sim1.getCaso().add("Rock");
        sim1.setValor(1F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Clasica");
        sim1.getInput().add("Jazz");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Punk");
        sim1.getCaso().add("Hard Rock");
        sim1.getCaso().add("Metal");
        sim1.setValor(0F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Clasica");
        sim1.getInput().add("Jazz");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Cumbia");
        sim1.getCaso().add("Pop");
        sim1.setValor(0.3F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Clasica");
        sim1.getInput().add("Jazz");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Pop");
        sim1.getCaso().add("Rock");
        sim1.setValor(0.3F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Clasica");
        sim1.getInput().add("Jazz");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Clasica");
        sim1.getCaso().add("Jazz");
        sim1.getCaso().add("Pop");
        sim1.setValor(1F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salsa");
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Punk");
        sim1.getCaso().add("Hard Rock");
        sim1.getCaso().add("Metal");
        sim1.setValor(0F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salsa");
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Cumbia");
        sim1.getCaso().add("Pop");
        sim1.setValor(0.9F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salsa");
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Pop");
        sim1.getCaso().add("Rock");
        sim1.setValor(0.7F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salsa");
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Clasica");
        sim1.getCaso().add("Jazz");
        sim1.getCaso().add("Pop");
        sim1.setValor(0.3F);
        musica.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Salsa");
        sim1.getInput().add("Reggaeton");
        sim1.getInput().add("Pop");
        sim1.getCaso().add("Salsa");
        sim1.getCaso().add("Reggaeton");
        sim1.getCaso().add("Pop");
        sim1.setValor(1F);
        musica.add(sim1);
    }
    public void cargarTablaShow(){
        Similitud sim1 = null;
        show = new ArrayList<Similitud>();
        sim1 = new Similitud();
        sim1.getInput().add("Banda en vivo");
        sim1.getCaso().add("Banda en vivo");
        sim1.setValor(1F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Malabarista");
        sim1.getCaso().add("Banda en vivo");
        sim1.setValor(0.2F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Malabarista");
        sim1.getCaso().add("Malabarista");
        sim1.setValor(1F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Baile");
        sim1.getCaso().add("Banda en vivo");
        sim1.setValor(0.4F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Baile");
        sim1.getCaso().add("Malabarista");
        sim1.setValor(0.3F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Baile");
        sim1.getCaso().add("Baile");
        sim1.setValor(1F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Mago");
        sim1.getInput().add("Cantante");
        sim1.getCaso().add("Banda en vivo");
        sim1.setValor(0.6F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Mago");
        sim1.getInput().add("Cantante");
        sim1.getCaso().add("Malabarista");
        sim1.setValor(0.4F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Mago");
        sim1.getInput().add("Cantante");
        sim1.getCaso().add("Baile");
        sim1.setValor(0.3F);
        show.add(sim1);
        sim1 = new Similitud();
        sim1.getInput().add("Mago");
        sim1.getInput().add("Cantante");
        sim1.getCaso().add("Mago");
        sim1.getCaso().add("Cantante");
        sim1.setValor(1F);
        show.add(sim1);
    }
    
    public List<Similitud> getBebida() {
        return bebida;
    }

    public void setBebida(List<Similitud> bebida) {
        this.bebida = bebida;
    }

    public List<Similitud> getComida() {
        return comida;
    }

    public void setComida(List<Similitud> comida) {
        this.comida = comida;
    }

    public List<Similitud> getLocacion() {
        return locacion;
    }

    public void setLocacion(List<Similitud> locacion) {
        this.locacion = locacion;
    }

    public List<Similitud> getMusica() {
        return musica;
    }

    public void setMusica(List<Similitud> musica) {
        this.musica = musica;
    }

    public List<Similitud> getShow() {
        return show;
    }

    public void setShow(List<Similitud> show) {
        this.show = show;
    }

    public List<Caso> getBaseCasos() {
        return baseCasos;
    }

    public void setBaseCasos(List<Caso> baseCasos) {
        this.baseCasos = baseCasos;
    }
    
}
