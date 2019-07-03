package com.project.OOP;

import com.project.OOP.utils.FilterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AgricultureAidCollection implements Filter<AgricultureAid, Object[]> {
    private ArrayList<AgricultureAid> agricultureAids;
    private FilterUtils<AgricultureAid> utils;

    public AgricultureAidCollection(ArrayList<AgricultureAid> agricultureAids, FilterUtils<AgricultureAid> utils) {
        this.agricultureAids = agricultureAids;
        this.utils = utils;
    }

    public AgricultureAidCollection(ArrayList<AgricultureAid> agricultureAids) {
        this.agricultureAids = agricultureAids;
        this.utils = new FilterUtils<AgricultureAid>();
    }

    public ArrayList<AgricultureAid> getAgricultureAids() {
        return agricultureAids;
    }

    public FilterUtils<AgricultureAid> getUtils() {
        return utils;
    }

    public void setAgricultureAids(ArrayList<AgricultureAid> agricultureAids) {
        this.agricultureAids = agricultureAids;
    }

    public Map<String, Object> getStats(int year) {
        Map<String, Object> result = new HashMap<>();
        result.put("field", String.valueOf(year));
        result.put("avg", getAvg(year));
        result.put("min", getMin(year));
        result.put("max", getMax(year));
        result.put("sum", getSum(year));
        return result;
    }

    public float getAvg(int year) {
        int count = 0;
        float sum = 0;
        for (AgricultureAid val : agricultureAids) {
            sum += val.getYear(year);
            count++;
        }
        return sum/count;
    }

    public float getMin(int year) {
        float currentMin = agricultureAids.get(0).getYear(year);
        for (AgricultureAid val : agricultureAids) {
            if(val.getYear(year) <= currentMin)
                currentMin = val.getYear(year);
        }
        return currentMin;
    }

    public float getMax(int year) {
        float currentMax = agricultureAids.get(0).getYear(year);
        for (AgricultureAid val : agricultureAids) {
            if(val.getYear(year) >= currentMax)
                currentMax = val.getYear(year);
        }
        return currentMax;
    }

    public float getSum(int year) {
        float sum = 0;
        for (AgricultureAid val : agricultureAids) {
            sum += val.getYear(year);
        }
        return sum;
    }

    @Override
    public ArrayList<AgricultureAid> filterField(String fieldName, String operator, Object... value) {
        return (ArrayList<AgricultureAid>) utils.select(this.getAgricultureAids(), fieldName, operator, value);
    }
}
