package com.project.OOP.utils;

import java.util.Collection;

/**
 * Intefaccia che mette a disposizione la dichiarazione del metodo per filtrare i dati di una classe
 * @param <E> Tipo dell'oggetto che verrà filtrato
 * @param <T> Tipo del valore che verrà utilizzato per filtrare l'oggetto
 */
public interface Filter<E,T> {
    /**
     * Metodo astratto la cui impementazione andrà a filtrare la collezione di elementi rispettivamente a un campo,
     * un operatore e un insieme di valori
     * @param fieldName Nome del campo che deve essere filtrato
     * @param operator Operatore che viene utilizzato per filtrare l'elemento
     * @param value Valori su cui verrà eseguito il filtro
     * @return Collezione degli elementi fltrata mediante i parametri specificati
     */
    abstract Collection<E> filterField(String fieldName, String operator, T value);
}