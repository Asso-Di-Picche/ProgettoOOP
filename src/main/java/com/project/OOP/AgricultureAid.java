package com.project.OOP;

import java.util.ArrayList;
import java.util.HashMap;

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
}
