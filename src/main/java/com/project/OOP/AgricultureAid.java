package com.project.OOP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgricultureAid {

    private String freq;
    private String geo;
    private String unit;
    private HashMap<Integer, Float> obj;

    public AgricultureAid(String freq, String geo, String unit, ArrayList<Float> invs) {
        this.freq = freq;
        this.geo = geo;
        this.unit = unit;
        obj = new HashMap<>();
        int dateStart = 2000;
        for (float inv: invs) {
            obj.put(dateStart++, inv);
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

    public HashMap<Integer, Float> getObj() {
        return obj;
    }

    public float getAvg() {
        int count = 0;
        float sum = 0;
        for (Map.Entry<Integer, Float> val : obj.entrySet()) {
            sum += val.getValue();
            count++;
        }
        return sum/count;
    }

    public float getMax() {
        float currentMax = 0;
        for (Map.Entry<Integer, Float> val : obj.entrySet()) {
            if(val.getValue() >= currentMax)
                currentMax = val.getValue();
        }
        return  currentMax;
    }

    public float getMin() {
        float currentMin = 10000;
        for (Map.Entry<Integer, Float> val : obj.entrySet()) {
            if(val.getValue() <= currentMin)
                currentMin = val.getValue();
        }
        return  currentMin;
    }

    public float getSum() {
        float sum = 0;
        for (Map.Entry<Integer, Float> val : obj.entrySet()) {
            sum += val.getValue();
        }
        return sum;
    }
}
