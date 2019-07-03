package com.project.OOP.utils;

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

    public ArrayList<T> union (ArrayList<T> a, ArrayList<T> b) {
        Set<T> set = new HashSet<T>();

        set.addAll(a);
        set.addAll(b);

        return new ArrayList<T>(set);
    }

}
