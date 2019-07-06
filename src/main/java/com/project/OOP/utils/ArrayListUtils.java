package com.project.OOP.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe che mette a disposizione i metodi che permettono di utilizzare la logica AND e OR
 * @param <T> tipo generico
 */
public class ArrayListUtils<T>{

    /**
     * Metodo che implementa la logica AND su un gruppo di insiemi di oggetti
     * @param items argomento che contiene il gruppo di insiemi di oggetti
     * @return restituisce l'insieme di oggetti comuni tra il gruppo di insiemi
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
     * Metodo che implementa la logica OR su un gruppo di insiemi di oggetti
     * @param items argomento che contiene il gruppo di insiemi di oggetti
     * @return restituisce l'insieme di oggetti complessivo dato dall'unione del gruppo di insiemi
     */
    public ArrayList<T> union (ArrayList<ArrayList<T>> items) {
        Set<T> set = new HashSet<T>();

        for (ArrayList<T> item : items)
            set.addAll(item);

        return new ArrayList<T>(set);
    }

}
