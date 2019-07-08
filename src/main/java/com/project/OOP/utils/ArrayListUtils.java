package com.project.OOP.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che mette a disposizione dei metodi che permettono di eseguire l'intersezione e l'unione tra più ArrayList,
 * in modo da ottenere un dato il cui risultato sia dato da una logica AND e/o OR
 * @param <T> Tipo generico
 */
public class ArrayListUtils<T>{

    /**
     * Metodo che implementa la logica AND su un gruppo di insiemi di oggetti, facendone l'intersezione
     * @param items Argomento che contiene il gruppo di insiemi di oggetti
     * @return Restituisce L'insieme di oggetti comuni tra il gruppo di insiemi
     */
    public ArrayList<T> intersection (ArrayList<ArrayList<T>> items) {
        ArrayList<T> list = new ArrayList<T>();
        for(int i = 0; i < items.size(); i++) {
            for(T t : items.get(i)){
                boolean included = true;
                for(ArrayList<T> itemToCompare : items) {
                    if(!itemToCompare.contains(t)) {
                        included = false;
                        break;
                    }
                }
                if(included && !list.contains(t))
                    list.add(t);
            }
        }
        return list;
    }

    /**
     * Metodo che implementa la logica OR su un gruppo di insiemi di oggetti, facendone l'unione
     * @param items Argomento che contiene il gruppo di insiemi di oggetti
     * @return Restituisce l'insieme di oggetti complessivo dato dall'unione del gruppo di insiemi
     */
    public ArrayList<T> union (ArrayList<ArrayList<T>> items) {
        Set<T> set = new HashSet<T>();

        for (ArrayList<T> item : items)
            set.addAll(item);

        return new ArrayList<T>(set);
    }

}
