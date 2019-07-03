package com.project.OOP;

import com.project.OOP.utils.FilterUtils;

import java.util.ArrayList;

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

    @Override
    public ArrayList<AgricultureAid> filterField(String fieldName, String operator, Object... value) {
        return (ArrayList<AgricultureAid>) utils.select(this.getAgricultureAids(), fieldName, operator, value);
    }
}
