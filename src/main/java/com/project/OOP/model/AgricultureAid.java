package com.project.OOP.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * La classe AgricultureAid modella un singolo elemento del dataset, rappresentante i sussidi agriculturali ricevuti
 * in un'area geografica in una certa unità di misura (milioni di euro o percentuale della GDP).
 */
public class AgricultureAid {

    /**
     * Frequenza del campionamento dell'elemento del dataset
     */
    @JsonPropertyDescription("Frequenza del campionamento ('A' = Annuale)")
    private String freq;

    /**
     * Entità geopolitica relativa all'elemento del dataset
     */
    @JsonPropertyDescription("Entità geopolitica di interesse")
    private String geo;

    /**
     * Unità di misura relativa all'elemento del dataset
     */
    @JsonPropertyDescription("Unità di misura considerata (MEUR_KP_PRE = Milioni di euro, PC_GDP = Percentuale in GDP)")
    private String unit;

    /**
     * Obiettivo dell'elemento del dataset
     */
    @JsonPropertyDescription("Non lo so")
    private String objectiv;

    /**
     * Oggetto contenente coppie chiave-valore sui sussidi ricevuti per ogni anno. E' possibile accedere ai valori usando
     * come chiave l'anno di riferimento.
     */
    @JsonPropertyDescription("Oggetto contenente gli aiuti ricevuti per ogni anno, dal 2000 al 2017. I campi all'interno (2000-2017) denotano gli aiuti ricevuti in un anno preciso")
    private HashMap<Integer, Float> aids;

    /**
     * Costruttore della classe che inizializza l'oggetto.
     * @param freq Stringa contenente la frequenza del campionamento del dato
     * @param geo Stringa contenente l'area geopolitica di riferimento
     * @param unit Stringa contenente l'unità di misura
     * @param objectiv Stringa contenente l'obiettivo relativo al dato
     * @param invs Arraylist contenente i sussidi ricevuti dal primo anno di riferimento
     */
    public AgricultureAid(String freq, String geo, String unit, String objectiv, ArrayList<Float> invs) {
        this.freq = freq;
        this.geo = geo;
        this.unit = unit;
        this.objectiv = objectiv;
        aids = new HashMap<>();
        int dateStart = 2000;
        for (float inv: invs) {
            aids.put(dateStart++, inv);
        }
    }

    /**
     * Funzione che restituisce la frequenza di campionamento del dato
     * @return Frequenza di campionamento dell'oggetto
     */
    public String getFreq() {
        return freq;
    }

    /**
     * Funzione che restituisce l'entità geopolitica relativa all'oggetto
     * @return Entità geopolitica dell'oggetto
     */
    public String getGeo() {
        return geo;
    }

    /**
     * Funzione che restituisce l'unità di misura dell'oggetto
     * @return Unità di misura dell'oggetto
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Funzione che restituisce l'obiettivo considerato nel dato
     * @return Obiettivo dell'oggetto
     */
    public String getObjectiv() {
        return objectiv;
    }

    /**
     * Funzione che restituisce l'oggetto contenente tutti gli aiuti ricevuti per ogni anno
     * @return Oggetto contenente i sussidi relativi al dato ragruppati per anno
     */
    public HashMap<Integer, Float> getAids() {
        return aids;
    }

    /**
     * Funzione che restituisce i sussidi ricevuti in un anno preciso
     * @param year Anno di cui si vuole conoscere i sussidi ricevuti
     * @return Sussidi ricevuti nell'anno specificato
     */
    @JsonIgnore
    public float getYear(int year) {
        return aids.get(year);
    }

    /**
     * Funzione che fa una media tra tutti i sussidi del singolo elemento
     * @return Media degli aiuti ricevuti per il dato
     */
    @JsonIgnore
    public float getAvg() {
        int count = 0;
        float sum = 0;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            sum += val.getValue();
            count++;
        }
        return sum/count;
    }

    /**
     * Funzione che restituisce la deviazione standard relativa a tutti i sussidi del singolo elemento
     * @return Deviazione standard degli aiuti ricevuti per il dato
     */
    @JsonIgnore
    public float getDevStandard() {
        float avg = getAvg();
        int count = 0;
        float sum = 0;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            sum += (float) Math.pow(val.getValue() - avg, 2);
            count++;
        }
        return (float) Math.pow(sum/count, 0.5);
    }

    /**
     * Funzione che restituisce il massimo valore tra tutti i sussidi relativi al dato
     * @return Massimo tra tutti i sussidi dell'oggetto
     */
    @JsonIgnore
    public float getMax() {
        float currentMax = 0;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            if(val.getValue() >= currentMax)
                currentMax = val.getValue();
        }
        return  currentMax;
    }

    /**
     * Funzione che restituisce il minimo valore tra tutti i sussidi relativi al dato
     * @return Minimo tra tutti i sussidi dell'oggetto
     */
    @JsonIgnore
    public float getMin() {
        float currentMin = 10000;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            if(val.getValue() <= currentMin)
                currentMin = val.getValue();
        }
        return currentMin;
    }

    /**
     * Funzione che restituisce la somma tra tutti i sussidi relativi al dato
     * @return Somma tra tutti i sussidi dell'oggetto
     */
    @JsonIgnore
    public float getSum() {
        float sum = 0;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            sum += val.getValue();
        }
        return sum;
    }
}
