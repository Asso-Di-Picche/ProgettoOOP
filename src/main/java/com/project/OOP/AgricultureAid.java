package com.project.OOP;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgricultureAid {

    @JsonPropertyDescription("Frequenza del campionamento ('A' = Annuale)")
    private String freq;
    @JsonPropertyDescription("Entità geopolitica di interesse")
    private String geo;
    @JsonPropertyDescription("Unità di misura considerata (MEUR_KP_PRE = Milioni di euro, PC_GDP = Percentuale in GDP)")
    private String unit;
    @JsonPropertyDescription("Non lo so")
    private String objectiv;
    @JsonPropertyDescription("Oggetto contenente gli aiuti ricevuti per ogni anno, dal 2000 al 2017. I campi all'interno (2000-2017) denotano gli aiuti ricevuti in un anno preciso")
    private HashMap<Integer, Float> aids;

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

    public String getFreq() {
        return freq;
    }

    public String getGeo() {
        return geo;
    }

    public String getUnit() {
        return unit;
    }

    public String getObjectiv() { return objectiv; }

    public HashMap<Integer, Float> getAids() {
        return aids;
    }

    @JsonIgnore
    public float getYear(int year) {
        return aids.get(year);
    }

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

    @JsonIgnore
    public float getMax() {
        float currentMax = 0;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            if(val.getValue() >= currentMax)
                currentMax = val.getValue();
        }
        return  currentMax;
    }

    @JsonIgnore
    public float getMin() {
        float currentMin = 10000;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            if(val.getValue() <= currentMin)
                currentMin = val.getValue();
        }
        return currentMin;
    }

    @JsonIgnore
    public float getSum() {
        float sum = 0;
        for (Map.Entry<Integer, Float> val : aids.entrySet()) {
            sum += val.getValue();
        }
        return sum;
    }
}
