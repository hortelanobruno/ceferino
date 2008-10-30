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
            aaaa.add(casoLocacion.getId());
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
                if(sim.getInput().equals(input)&&sim.getCaso().equals(caso)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("comida")){
            for(int i=0 ; i< comida.size() ; i++){
                Similitud sim = comida.get(i);
                if(sim.getInput().equals(input)&&sim.getCaso().equals(caso)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("locacion")){
            for(int i=0 ; i< locacion.size() ; i++){
                Similitud sim = locacion.get(i);
                if(sim.getInput().equals(input)&&sim.getCaso().equals(caso)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("musica")){
            for(int i=0 ; i< musica.size() ; i++){
                Similitud sim = musica.get(i);
                if(sim.getInput().equals(input)&&sim.getCaso().equals(caso)){
                    return sim.getValor();
                }
            }
        }
        if(tipo.equalsIgnoreCase("show")){
            for(int i=0 ; i< show.size() ; i++){
                Similitud sim = show.get(i);
                if(sim.getInput().equals(input)&&sim.getCaso().equals(caso)){
                    return sim.getValor();
                }
            }
        }
        return 0;
    }
    
    public void cargarTablaBebida(){
        
        Similitud simBebida = new Similitud();
        
        for(int i = 0; i<this.getBaseCasos().size();i++)
        {
            
        }
        
    }
    public void cargarTablaComida(){
        
    }
    public void cargarTablaLocacion(){
        
    }
    public void cargarTablaMusica(){
        
    }
    public void cargarTablaShow(){
        
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
