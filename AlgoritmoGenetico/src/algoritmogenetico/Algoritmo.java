/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package algoritmogenetico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author dext
 */
public class Algoritmo {

    public HashMap<String,HashMap<ArrayList<String>,Double>> mapaDetodo;
    public List<Integer> datosIngresados;
    public HashMap<String,String> priorizacion;
    public ArrayList<Double> rangoDefault;
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
        lista2.add("Salon de hotel cinco estrellas");
        lista3 = new ArrayList<String>();
        lista3.add("Salon de hotel cuatro estrellas");
        ArrayList<String> lista4 = new ArrayList<String>();
        lista4.add("Salon de hotel tres estrellas");
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
        mapGeneral.put("Locacion", hash1);
        
        //Despues seguimos cargando papa
        return mapGeneral;
    }
    
    public List<Integer> obtenerDatosIngresados(){
        return datosIngresados;
    }
    
    public HashMap<String,String> obtenerPriorizacion(){
        return priorizacion;
    }
    
    public ArrayList<Double> obtenerDatosRangoDefault(){
        return rangoDefault;
    }
    
    public void iniciar(){
        mapaDetodo = cargaDatos();
        List<Integer> input = obtenerDatosIngresados();
        HashMap<String,String> input2 = obtenerPriorizacion();
        ArrayList<Double> rango = obtenerDatosRangoDefault();
        
        
        ArrayList<ArrayList<Double>> aux = generarPoblacionInicial(input,input2,rango);
        int cantIteraciones = 10;
        int menor = 0;
        int menor2 = 0;
        for(int i=0 ; i < cantIteraciones ; i++){
            ArrayList<Double> pesoCromozoma = generarPeso(aux);
            ArrayList<Double> pesoAuxiliar = new ArrayList<Double>();
            pesoAuxiliar.addAll(pesoCromozoma);
            menor = buscarMenor(pesoAuxiliar);
            pesoAuxiliar.remove(menor);
            menor2 = buscarMenor(pesoAuxiliar);
            pesoAuxiliar.remove(menor2);
            //creo neuva poblacion
            ArrayList<ArrayList<Double>> nuevaPoblacion = new ArrayList<ArrayList<Double>>();
            //cargo los mejores 2 padres
            nuevaPoblacion.add(aux.get(menor));
            nuevaPoblacion.add(aux.get(menor2));
            //genero y cargo hijos mutados
            generarYCargarHijosMutados(nuevaPoblacion,aux.get(menor),aux.get(menor2));
            while(nuevaPoblacion.size() <10 ){
                menor = buscarMenor(pesoAuxiliar);
                menor2 = buscarMenor(pesoAuxiliar);
                generarYCargarHijosMutados(nuevaPoblacion,aux.get(menor),aux.get(menor2));
            }
            aux = nuevaPoblacion;
        }
        ArrayList<Double> pesoCromozoma = generarPeso(aux);
        //obtengo el mejor del mundo
        menor = buscarMenor(pesoCromozoma);
        poblacionFinal = aux;
        mejorFinal = menor;
        pesoFinal = pesoCromozoma;
    }
    
    public Algoritmo(){
        
    }
    
    public void generarYCargarHijosMutados(ArrayList<ArrayList<Double>> pobla, ArrayList<Double> hijo1, ArrayList<Double> hijo2){
        int corte = 2;
        int invertirValor = 1;
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
        double aux1 = hijoNuevo1.get(invertirValor);
        double aux2 = hijoNuevo2.get(invertirValor);
        hijoNuevo1.toArray()[invertirValor] = aux2;
        hijoNuevo2.toArray()[invertirValor] = aux1;
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
    
    public ArrayList<Double> generarPeso(ArrayList<ArrayList<Double>> aux){
        ArrayList<Double> lista = new ArrayList<Double>();
        ArrayList<Double> auxaux = null;
        ArrayList<Double> top = new ArrayList<Double>();
        top = aux.get(0);
        for(int i = 1 ; i < 10 ; i++){
            auxaux = new ArrayList<Double>();
            auxaux = aux.get(i);
            double aux2 = 0;
            for(int j = 0 ; j < 3 ; j++){
                aux2 = aux2 + Math.abs(top.get(j) - auxaux.get(j));
            }
            lista.add(aux2);
        }
        return lista;
    }
    
    public ArrayList<ArrayList<Double>> generarPoblacionInicial(List<Integer> input, HashMap<String,String> input2, ArrayList<Double> rango){
        //desp es 10x9
        HashMap<ArrayList<String>,Double> aux;
        ArrayList<ArrayList<Double>> matriz = new ArrayList<ArrayList<Double>>();
        //carga de primer fila
        ArrayList<Double> auxout = new ArrayList<Double>();
        for(int i = 0 ; i < 3 ; i++){
            if(input.get(i) == 1){
                auxout.add(rango.get(0));
            }
            if(input.get(i) == 2){
                auxout.add(rango.get(1));
            }
            if(input.get(i) == 3){
                auxout.add(rango.get(2));
            }
        }
        matriz.add(auxout);
        String priorizacion = null;
        for(int i = 0 ; i < 9 ; i++){
            ArrayList<Double> auxaux = new ArrayList<Double>();
            for(int j = 0 ; j < 3 ; j++){
                if(j == 0){
                    if(input2.containsKey("Comida")){
                        priorizacion = input2.get("Comida");
                        aux = mapaDetodo.get("Comida");
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
                        int random = (int) (Math.random() * (aux2.size() - 1));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }else{
                        aux = mapaDetodo.get("Comida");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            aux2.add((ArrayList<String>) lista);
                        }
                        int random = (int) (Math.random() * (aux2.size() - 1));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }
                }
                if(j == 1){
                    if(input2.containsKey("Bebida")){
                        priorizacion = input2.get("Bebida");
                        aux = mapaDetodo.get("Bebida");
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
                        int random = (int) (Math.random() * (aux2.size() - 1));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }else{
                        aux = mapaDetodo.get("Bebida");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            aux2.add((ArrayList<String>) lista);
                        }
                        int random = (int) (Math.random() * (aux2.size() - 1));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }
                }
                if(j == 2){
                    if(input2.containsKey("Locacion")){
                        priorizacion = input2.get("Locacion");
                        aux = mapaDetodo.get("Locacion");
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
                        int random = (int) (Math.random() * (aux2.size() - 1));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }else{
                        aux = mapaDetodo.get("Locacion");
                        Iterator it = aux.keySet().iterator();
                        ArrayList<ArrayList<String>> aux2 = new ArrayList<ArrayList<String>>();
                        while(it.hasNext()){
                            List<String> lista = (List<String>) it.next();
                            aux2.add((ArrayList<String>) lista);
                        }
                        int random = (int) (Math.random() * (aux2.size() - 1));
                        auxaux.add(aux.get(aux2.get(random)));    
                    }
                }
            }
            matriz.add(auxaux);
        }
        return matriz;
    }
    
    
    
}
