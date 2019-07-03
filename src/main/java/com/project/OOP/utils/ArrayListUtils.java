package com.project.OOP.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ArrayListUtils<T> {

    public ArrayList<T> intersection (ArrayList<T> a, ArrayList<T> b) {
        ArrayList<T> list = new ArrayList<T>();

        for (T t : a) {
            if(b.contains(t)) {
                list.add(t);
            }
        }

        return list;
    }

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

    public ArrayList<T> union (ArrayList<T> a, ArrayList<T> b) {
        Set<T> set = new HashSet<T>();

        set.addAll(a);
        set.addAll(b);

        return new ArrayList<T>(set);
    }

    public ArrayList<T> union (ArrayList<ArrayList<T>> items) {
        Set<T> set = new HashSet<T>();

        for (ArrayList<T> item : items) {
            set.addAll(item);
        }

        return new ArrayList<T>(set);
    }

}
