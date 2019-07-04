package com.project.OOP.model;

import com.project.OOP.Filter;
import com.project.OOP.utils.FilterUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *La classe raggruppa tutti gli oggetti {@link AgricultureAid} in una collezione
 */
public class AgricultureAidCollection implements Filter<AgricultureAid, Object[]> {
    private ArrayList<AgricultureAid> agricultureAids;
    private FilterUtils<AgricultureAid> utils;

    /**
     * Costruttore dell'oggetto
     * @param agricultureAids una ArrayList di AgricultureAid
     * @param utils utils viene istanziato per accedere ai metodi di FilterUtils
     */
    public AgricultureAidCollection(ArrayList<AgricultureAid> agricultureAids, FilterUtils<AgricultureAid> utils) {
        this.agricultureAids = agricultureAids;
        this.utils = utils;
    }

    /**
     * Costruttore dell'oggetto
     * @param agricultureAids una ArrayList di AgricultureAid
     */
    public AgricultureAidCollection(ArrayList<AgricultureAid> agricultureAids) {
        this.agricultureAids = agricultureAids;
        this.utils = new FilterUtils<AgricultureAid>();
    }

    /**
     * Metodo che restituisce il contenuto della collezione
     * @return collezione di oggetti AgricultureAid
     */
    public ArrayList<AgricultureAid> getAgricultureAids() {
        return agricultureAids;
    }

    /**
     * Metodo capace di inserire nuovi oggetti nella collezione
     * @param agricultureAids oggetti AgricultureAid da inserire
     */
    public void setAgricultureAids(ArrayList<AgricultureAid> agricultureAids) {
        this.agricultureAids = agricultureAids;
    }

    /**
     * Metodo che restituisce le statistiche su base anno sul gruppo di oggetti eventualmente filtrati
     * @param year anno su cui si vuole ottenere le statistiche
     * @return restituisce le statistiche
     */
    public Map<String, Object> getStats(int year) {
        Map<String, Object> result = new HashMap<>();
        result.put("field", String.valueOf(year));
        result.put("avg", getAvg(year));
        result.put("min", getMin(year));
        result.put("max", getMax(year));
        result.put("sum", getSum(year));
        result.put("devstd", getDevStandard(year));
        return result;
    }

    /**
     * Metodo che calcola la media su base anno
     * @param year specifica l'anno su cui si vuole ottenere la media
     * @return restituisce la media
     */
    public float getAvg(int year) {
        int count = 0;
        float sum = 0;
        for (AgricultureAid val : agricultureAids) {
            sum += val.getYear(year);
            count++;
        }
        return sum/count;
    }

    /**
     * Metodo che calcola la deviazione standard su base anno
     * @param year specifica l'anno su cui si vuole ottenere la deviazione standard
     * @return restituisce la deviazione standard
     */
    public float getDevStandard(int year) {
        float avg = getAvg(year);
        int count = 0;
        float sum = 0;
        for (AgricultureAid val : agricultureAids) {
            sum += (float) Math.pow(val.getYear(year) - avg, 2);
            count++;
        }
        return (float) Math.pow(sum/count, 0.5);
    }

    /**
     * Metodo che trova il valore minimo su base anno
     * @param year specifica l'anno su cui si vuole sapere il valore minimo
     * @return restituisce il valore minimo
     */
    public Map<String, Object> getMin(int year) {
        float currentMin = agricultureAids.get(0).getYear(year);
        ArrayList str = new ArrayList();
        str.add(agricultureAids.get(0).getGeo());
        for (int i = 1; i < agricultureAids.size(); i++) {
            if(agricultureAids.get(i).getYear(year) == currentMin){
                currentMin = agricultureAids.get(i).getYear(year);
                str.add(agricultureAids.get(i).getGeo());
            }else if(agricultureAids.get(i).getYear(year) < currentMin){
                str.clear();
                currentMin = agricultureAids.get(i).getYear(year);
                str.add(agricultureAids.get(i).getGeo());
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("geo", str);
        result.put("value", currentMin);
        return result;
    }

    /**
     * Metodo che trova il valore massimo su base anno
     * @param year specifica l'anno su cui si vuole sapere il valore massimo
     * @return restituisce il valore massimo
     */
    public Map<String, Object> getMax(int year) {
        float currentMax = agricultureAids.get(0).getYear(year);
        ArrayList str = new ArrayList();
        str.add(agricultureAids.get(0).getGeo());
        for (int i = 1; i < agricultureAids.size(); i++) {
            if(agricultureAids.get(i).getYear(year) == currentMax){
                currentMax = agricultureAids.get(i).getYear(year);
                str.add(agricultureAids.get(i).getGeo());
            }else if(agricultureAids.get(i).getYear(year) > currentMax){
                str.clear();
                currentMax = agricultureAids.get(i).getYear(year);
                str.add(agricultureAids.get(i).getGeo());
            }
        }
        Map<String, Object> result = new HashMap<>();
        result.put("geo", str);
        result.put("value", currentMax);
        return result;
    }

    /**
     * Metodo che trova la somma di tutti i valori su base anno
     * @param year specifica l'anno su cui si vuole fare la somma
     * @return restituisce la somma
     */
    public float getSum(int year) {
        float sum = 0;
        for (AgricultureAid val : agricultureAids) {
            sum += val.getYear(year);
        }
        return sum;
    }

    /**
     * Metodo che permette di applicare i filtri specificati sulla collezione di oggettiAgricultureAid
     * @param fieldName campo su cui vi vuole specificare la condizione di filtro
     * @param operator condizione di filtro
     * @param value valori che caratterizzano la condizione di filtro
     * @return la collezione di oggetti filtrata
     */
    @Override
    public ArrayList<AgricultureAid> filterField(String fieldName, String operator, Object... value) {
        return (ArrayList<AgricultureAid>) utils.select(this.getAgricultureAids(), fieldName, operator, value);
    }
}
