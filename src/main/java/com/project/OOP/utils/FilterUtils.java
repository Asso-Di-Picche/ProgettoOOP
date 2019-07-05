package com.project.OOP.utils;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Classe che permette di filtrare gli oggetti secondo i paramentri specificati
 * @param <T> Tipo generico
 */
public class FilterUtils<T> {
    /**
     * Metodo che determina per ciscun'oggetto della collezione, in base al filtro specificato, se deve essere incluso nella risposta
     * @param value oggetto che viene considerato nel test
     * @param operator condizione del filtro
     * @param th oggetti che caratterizzano il filtro
     * @return un booleano che determina se l'oggette deve essere tenuto o meno
     */
    public static boolean check(Object value, String operator, Object... th) {
        if (th.length == 1 && th[0] instanceof Number && value instanceof Number) {
            Double thC = ((Number)th[0]).doubleValue();
            Double valuec = ((Number)value).doubleValue();
            if (operator.equals("$eq"))
                return value.equals(th[0]);
            else if (operator.equals("$not"))
                return !value.equals(th[0]);
            else if (operator.equals("$gt"))
                return valuec > thC;
            else if (operator.equals("$lt"))
                return valuec < thC;
        } else if(th.length == 1 && th[0] instanceof String && value instanceof String) {
            if(operator.equals("$eq") || operator.equals("$in")) return value.equals(th[0]);
            else if(operator.equals("$not") || operator.equals("$nin")) return !value.equals(th[0]);
        } else if(th.length > 1) {
            if (operator.equals("$bt")) {
                if(th.length == 2 && th[0] instanceof Number && th[1] instanceof Number) {
                    Double min = ((Number)th[0]).doubleValue();
                    Double max = ((Number)th[1]).doubleValue();
                    Double valuec = ((Number)value).doubleValue();
                    return valuec > min && valuec < max;
                }
            }
            else if (operator.equals("$in"))
                return Arrays.asList(th).contains(value);
            else if (operator.equals("$nin"))
                return !Arrays.asList(th).contains(value);
        }
        return false;
    }

    /**
     * Metodo che riceve l'intera collezione di oggetti ed il filtro e restituisce una collezione parziale con gli oggetti selezionati
     * @param src l'intra collezione di oggetti
     * @param fieldName campo su cui opera il filtro
     * @param operator condizione del filtro
     * @param value oggetti che caratterizzano il filtro
     * @return collezione risultante
     */
    public Collection<T> select(Collection<T> src, String fieldName, String operator, Object... value) {
        Collection<T> out = new ArrayList<T>();
        for(T item:src) {
            try {
                Method m = null;
                if(isInteger(fieldName)) {
                    m = item.getClass().getMethod("getYear", int.class);
                } else {
                    m = item.getClass().getMethod("get"+fieldName.substring(0, 1).toUpperCase()+fieldName.substring(1),null);
                }
                try {
                    Object tmp = null;
                    if(isInteger(fieldName)) {
                        tmp = m.invoke(item, Integer.parseInt(fieldName));
                    } else {
                        tmp = m.invoke(item);
                    }
                    if(FilterUtils.check(tmp, operator, value))
                        out.add(item);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            }
        }
        return out;
    }

    /**
     * Metodo che stabilisce se l'argomento in ingresso può essere parsato come intero
     * @param fieldName stringa sui testare il parse
     * @return restituisce un booleano per esprimere l'esito del test
     */
    private static boolean isInteger(String fieldName) {
        try {
            Integer.parseInt(fieldName);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

