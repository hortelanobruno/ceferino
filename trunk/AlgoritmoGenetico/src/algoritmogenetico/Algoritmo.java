/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmogenetico;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 *
 * @author dext
 */
public class Algoritmo {

    private HashMap<String,HashMap<ArrayList<String>,Double>> mapaDetodo;
    public List<Integer> datosIngresados;
    public HashMap<String,String> priorizacion;
    public double[][] rangoDefault;
    public ArrayList<Double> pesoFinal;
    public ArrayList<ArrayList<Double>> poblacionFinal;
    public int mejorFinal;
    
    public HashMap<String,HashMap<ArrayList<String>,Double>> cargaDatos(){
        HashMap<String,HashMap<ArrayList<String>,Double>> mapGeneral = new HashMap<String,HashMap<ArrayList<String>,Double>>();
        
        //aa
        ArrayList<String> lista1 = new ArrayList<String>();
        lista1.add("Sandwiches");
        lista1.add("Pollo al champignon");
        ArrayList<String> lista2 = new ArrayList<String>();
        lista2.add("Sandwiches");
        lista2.add("Caviar");
        lista2.add("Asado");
        ArrayList<String> lista3 = new ArrayList<String>();
        lista3.add("Salmon");
        lista3.add("Canapes");
        lista3.add("Caviar");
        lista3.add("Sushi");
        HashMap<ArrayList<String>,Double> hash1 = new HashMap<ArrayList<String>,Double>();
        hash1.put(lista1, 0.3);
        hash1.put(lista2, 0.6);
        hash1.put(lista3, 0.9);
        //aa
        mapGeneral.put("Comida",hash1);
        
        
        lista1 = new ArrayList<String>();
        lista1.add("Champagne");
        lista1.add("Vino");
        lista1.add("Agua mineral");
        lista2 = new ArrayList<String>();
        lista2.add("Jugo");
        lista2.add("Cerveza");
        lista2.add("Sidra");
        lista3 = new ArrayList<String>();
        lista3.add("Gaseosa");
        lista3.add("Vino");
        lista3.add("Wisky");
        hash1 = new HashMap<ArrayList<String>,Double>();
        hash1.put(lista1, 0.8);
        hash1.put(lista2, 0.4);
        hash1.put(lista3, 0.6);
        mapGeneral.put("Bebida", hash1);
        
        
        lista1 = new ArrayList<String>();
        lista1.add("Club de barrio");
        lista2 = new ArrayList<String>();
        lista2.add("Hotel 5 estrellas");
        lista3 = new ArrayList<String>();
        lista3.add("Hotel 4 estrellas");
        ArrayList<String> lista4 = new ArrayList<String>();
        lista4.add("Hotel 3 estrellas");
        ArrayList<String> lista5 = new ArrayList<String>();
        lista5.add("Country");
        ArrayList<String> lista6 = new ArrayList<String>();
        lista6.add("Local bailable");
        hash1 = new HashMap<ArrayList<String>,Double>();
        hash1.put(lista1, 0.2);
        hash1.put(lista2, 1.0);
        hash1.put(lista3, 0.8);
        hash1.put(lista4, 0.7);
        hash1.put(lista5, 0.7);
        hash1.put(lista6, 0.4);
        mapGeneral.put("Localizacion", hash1);
        
        //Despues seguimos cargando papa
        return mapGeneral;
    }
    
    public List<Integer> obtenerDatosIngresados(){
        return datosIngresados;
    }
    
    public HashMap<String,String> obtenerPriorizacion(){
        
        
        return priorizacion;
    }
    
    public double[][] obtenerDatosRangoDefault(){
        return rangoDefault;
    }
    
    public ArrayList<ArrayList<Double>> generarPonderacion(List<Integer> input, double[][] rango ){
        ArrayList<ArrayList<Double>> pond = new ArrayList<ArrayList<Double>>();
        return generarInput(pond,input,rango);
    }
    
    public void iniciar(){
        setMapaDetodo(cargaDatos());
        List<Integer> input = obtenerDatosIngresados();
        HashMap<String,String> input2 = obtenerPriorizacion();
        double[][] rango = obtenerDatosRangoDefault();
        ArrayList<ArrayList<Double>> matriz = new ArrayList<ArrayList<Double>>();
        ArrayList<ArrayList<Double>> ponderacion = generarPonderacion(input,rango);
        generarPoblacionInicial(matriz,input2);
        
        int cantIteraciones = 20;
        int menor = 0;
        int menor2 = 0;
        ArrayList<Double> pesoCromosoma = generarPeso(matriz,ponderacion);
        System.out.println("Poblacion Inicial");
        imprimirPoblacion(matriz,pesoCromosoma);
        for(int i=0 ; i < cantIteraciones ; i++){
            ArrayList<Double> pesoAuxiliar = new ArrayList<Double>();
            pesoAuxiliar.addAll(pesoCromosoma);
            menor = buscarMenor(pesoAuxiliar);
            pesoAuxiliar.remove(menor);
            pesoAuxiliar.add(menor,99D);
            menor2 = buscarMenor(pesoAuxiliar);
            pesoAuxiliar.remove(menor2);
            pesoAuxiliar.add(menor2,99D);
            pesoAuxiliar.clear();
            pesoAuxiliar.addAll(pesoCromosoma);
            //creo neuva poblacion
            ArrayList<ArrayList<Double>> nuevaPoblacion = new ArrayList<ArrayList<Double>>();
            //elitismos
            nuevaPoblacion.add(matriz.get(menor));
            nuevaPoblacion.add(matriz.get(menor2));
            //recombinacion
            menor = buscarMenor(pesoAuxiliar);
            pesoAuxiliar.remove(menor);
            pesoAuxiliar.add(menor,99D);
            menor2 = buscarMenor(pesoAuxiliar);
            pesoAuxiliar.remove(menor2);
            pesoAuxiliar.add(menor2,99D);
            //genero y cargo hijos mutados
            generarYCargarHijosMutados(nuevaPoblacion,matriz.get(menor),matriz.get(menor2));
            while(nuevaPoblacion.size() <50 ){
                menor = buscarMenor(pesoAuxiliar);
                pesoAuxiliar.remove(menor);
                pesoAuxiliar.add(menor,99D);
                menor2 = buscarMenor(pesoAuxiliar);
                pesoAuxiliar.remove(menor2);
                pesoAuxiliar.add(menor2,99D);
                generarYCargarHijosMutados(nuevaPoblacion,matriz.get(menor),matriz.get(menor2));
            }
            matriz = nuevaPoblacion;
            pesoCromosoma = generarPeso(matriz,ponderacion);
            System.out.println("Poblacion Nueva "+(i+1));
            imprimirPoblacion(matriz,pesoCromosoma);
            boolean b = encontre(pesoCromosoma);
            if(b){
                //break;
            }
        }
        pesoCromosoma = generarPeso(matriz,ponderacion);
        //obtengo el mejor del mundo
        menor = buscarMenor(pesoCromosoma);
        poblacionFinal = matriz;
        mejorFinal = menor;
        pesoFinal = pesoCromosoma;
    }
    
    public boolean encontre(ArrayList<Double> pesoCromosoma){
        for(int i=0 ; i < pesoCromosoma.size() ; i++){
            if(pesoCromosoma.get(i) == 0){
                return true;
            }
        }
        return false;
    }
    
    
    public void imprimirPoblacion(ArrayList<ArrayList<Double>> aux, ArrayList<Double> pesoCromosoma){
        System.out.println();
        for(int i=0 ; i< aux.size() ; i++){
            ArrayList<Double> aux2 = aux.get(i);
            for(int j=0 ; j< aux2.size() ; j++){
                System.out.print(aux2.get(j)+" ");
            }
            System.out.println("=> "+pesoCromosoma.get(i));
        }
        System.out.println();
    }
    
    
    public Algoritmo(){
        
    }
    
    public void generarYCargarHijosMutados(ArrayList<ArrayList<Double>> pobla, ArrayList<Double> hijo1, ArrayList<Double> hijo2){
        int corte = 2;
        int invertirValor = (int) (Math.random() * hijo1.size());
        int limite = 3;
        ArrayList<Double> hijoNuevo1 = new ArrayList<Double>();
        ArrayList<Double> hijoNuevo2 = new ArrayList<Double>();
        for(int i = 0 ; i < hijo1.size() ; i++){
            if(i < corte){
                hijoNuevo1.add(hijo1.get(i));
                hijoNuevo2.add(hijo2.get(i));
            }else{
                hijoNuevo1.add(hijo2.get(i));
                hijoNuevo2.add(hijo1.get(i));
            }
        }
        //cambiar valor
        Random r = new java.util.Random();
        if(r.nextInt(99) < limite){
            double aux1 = hijoNuevo1.get(invertirValor);
            double aux2 = hijoNuevo2.get(invertirValor);
            hijoNuevo1.toArray()[invertirValor] = aux2;
            hijoNuevo2.toArray()[invertirValor] = aux1;
        }
        //agregar hijos
        pobla.add(hijoNuevo1);
        pobla.add(hijoNuevo2);
    }
    
    
    public int buscarMenor(ArrayList<Double> aux){
        int menor = 0;
        double aux2 = aux.get(0);
        for(int i = 1 ; i < aux.size() ; i++ ){
            if(aux.get(i) < aux2){
                menor = i;
                aux2 = aux.get(i);
            }
        }
        return menor;
    }
    
    public ArrayList<Double> generarPeso(ArrayList<ArrayList<Double>> aux, ArrayList<ArrayList<Double>> pond){
        ArrayList<Double> lista = new ArrayList<Double>();
        ArrayList<Double> auxaux = null;
        ArrayList<Double> rangoMin = pond.get(0);
        ArrayList<Double> rangoMax = pond.get(1);
        for(int i = 0 ; i < aux.size() ; i++){
            auxaux = new ArrayList<Double>();
            auxaux = aux.get(i);
            double aux2 = 0;
            for(int j = 0 ; j < 3 ; j++){
                if((auxaux.get(j) >= rangoMin.get(j)) && (auxaux.get(j) <= rangoMax.get(j))){
                    aux2 = aux2 + 0;
                }else{
                    double aux33 = (rangoMin.get(j) + rangoMax.get(j))/2;
                    aux2 = aux2 + Math.abs(aux33-auxaux.get(j));
                }
            }
            lista.add(aux2);
        }
        return lista;
    }
    
    public ArrayList<ArrayList<Double>> generarInput(ArrayList<ArrayList<Double>> matriz, List<Integer> input, double[][] rango){
        ArrayList<Double> auxout = new ArrayList<Double>();
        ArrayList<Double> auxout2 = new ArrayList<Double>();
        for(int i = 0 ; i < 3 ; i++){
            if(input.get(i) == 1){
                auxout.add(rango[0][0]);
                auxout2.add(rango[0][1]);
            }
            if(input.get(i) == 2){
                auxout.add(rango[1][0]);
                auxout2.add(rango[1][1]);
            }
            if(input.get(i) == 3){
                auxout.add(rango[2][0]);
                auxout2.add(rango[2][1]);
            }
        }
        matriz.add(auxout);
        matriz.add(auxout2);
        return matriz;
    }
    public void generarPoblacionInicial(ArrayList<ArrayList<Double>> matriz, HashMap<String,String> input2){
        //desp es 10x9
        HashMap<ArrayList<String>,Double> aux;
        //carga de primer fila
        String priorizacion = null;
        for(int i = 0 ; i < 50 ; i++){
            ArrayList<Double> auxaux = new ArrayList<Double>();
            for(int j = 0 ; j < 3 ; j++){
                if(j == 0){
                    if(input2.containsKey("Comida")){
                        priorizacion = input2.get("Comida");
                        aux = getMapaDetodo().get("Comida");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            for(int l = 0 ; l < lista.size() ; l++){
                                if(lista.get(l).equalsIgnoreCase(priorizacion)){
                                    aux2.add((ArrayList<String>) lista);
                                    break;
                                }
                            }
                        }
                        int random = (int) (Math.random() * (aux2.size()));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }else{
                        aux = getMapaDetodo().get("Comida");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            aux2.add((ArrayList<String>) lista);
                        }
                        int random = (int) (Math.random() * (aux2.size()));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }
                }
                if(j == 1){
                    if(input2.containsKey("Bebida")){
                        priorizacion = input2.get("Bebida");
                        aux = getMapaDetodo().get("Bebida");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            for(int l = 0 ; l < lista.size() ; l++){
                                if(lista.get(l).equalsIgnoreCase(priorizacion)){
                                    aux2.add((ArrayList<String>) lista);
                                    break;
                                }
                            }
                        }
                        int random = (int) (Math.random() * (aux2.size()));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }else{
                        aux = getMapaDetodo().get("Bebida");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            aux2.add((ArrayList<String>) lista);
                        }
                        int random = (int) (Math.random() * (aux2.size()));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }
                }
                if(j == 2){
                    if(input2.containsKey("Localizacion")){
                        priorizacion = input2.get("Localizacion");
                        aux = getMapaDetodo().get("Localizacion");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            for(int l = 0 ; l < lista.size() ; l++){
                                if(lista.get(l).equalsIgnoreCase(priorizacion)){
                                    aux2.add((ArrayList<String>) lista);
                                    break;
                                }
                            }
                        }
                        int random = (int) (Math.random() * (aux2.size()));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }else{
                        aux = getMapaDetodo().get("Localizacion");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            aux2.add((ArrayList<String>) lista);
                        }
                        int random = (int) (Math.random() * (aux2.size()));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }
                }
            }
            matriz.add(auxaux);
        }
    }

    public HashMap<String, HashMap<ArrayList<String>, Double>> getMapaDetodo() {
        return mapaDetodo;
    }

    public void setMapaDetodo(HashMap<String, HashMap<ArrayList<String>, Double>> mapaDetodo) {
        this.mapaDetodo = mapaDetodo;
    }
    
    
    
}
