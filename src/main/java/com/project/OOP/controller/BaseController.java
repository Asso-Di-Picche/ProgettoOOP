package com.project.OOP.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.OOP.errors.CommandNotFoundException;
import com.project.OOP.model.AgricultureAid;
import com.project.OOP.model.AgricultureAidCollection;
import com.project.OOP.utils.ArrayListUtils;
import com.project.OOP.utils.CSVParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class BaseController {

    @RequestMapping(value = "/data", method = RequestMethod.GET, produces="application/json")
    String getAllData(@RequestParam(defaultValue = "") String filter){
        try {
            AgricultureAidCollection objects = CSVParser.getDataFromCSV();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(objects.getAgricultureAids());
        } catch (IOException e){
            return e.toString();
        }
    }

    @RequestMapping(value = "/data", method = RequestMethod.POST, produces="application/json")
    String getAllDataFiltered(@RequestBody(required = false) String filter){
        try {
            AgricultureAidCollection objects = CSVParser.getDataFromCSV();
            JSONObject json = null;
            ArrayList<AgricultureAid> result = null;
            if(filter != null){
                try {
                    json = new JSONObject(filter);
                    result = parseCommands(objects, json);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    return makeErrorMessage("Sono stati inseriti dei valori in un formato errato");
                } catch (JSONException e) {
                    e.printStackTrace();
                    return makeErrorMessage("Il JSON non sembra essere ben formato");
                } catch (CommandNotFoundException e) {
                    return makeErrorMessage(e.getMessage());
                }
            }

            ObjectMapper mapper = new ObjectMapper();
            if(result != null) {
                return mapper.writeValueAsString(result);
            } else return mapper.writeValueAsString(objects.getAgricultureAids());
        } catch (IOException e){
            return e.toString();
        }
    }

    @RequestMapping(value = "/stats/year/{field}", method = RequestMethod.POST, produces="application/json")
    String getStats(@PathVariable("field") int field, @RequestBody(required = false) String filter){
        try {
            if(field >= 2000 && field <= 2017) {
                AgricultureAidCollection objects = CSVParser.getDataFromCSV();
                JSONObject json = null;
                ArrayList<AgricultureAid> result = null;
                if(filter != null){
                    try {
                        json = new JSONObject(filter);
                        result = parseCommands(objects, json);
                        objects.setAgricultureAids(result);
                    } catch (ClassCastException e) {
                        return makeErrorMessage("Sono stati inseriti dei valori in un formato errato");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        return makeErrorMessage("Il JSON non sembra essere ben formato");
                    } catch (CommandNotFoundException e) {
                        return makeErrorMessage(e.getMessage());
                    }
                }
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(objects.getStats(field));
            } else {
                return makeErrorMessage("Il campo specificato non può essere utilizzato per effettuare operazioni statistiche.");
            }
        } catch (IOException e){
            return e.toString();
        }
    }

    @RequestMapping(value = "/stats/geo/{geo}/unit/{unit}", method = RequestMethod.GET, produces="application/json")
    String getStatsOnObject(@PathVariable("geo") String geo, @PathVariable("unit") String unit){
        try {
            if(geo != null && unit != null) {
                AgricultureAidCollection objects = CSVParser.getDataFromCSV();
                AgricultureAid foundObj = objects.getAgricultureAids().stream().filter(el -> el.getGeo().equals(geo) && el.getUnit().equals(unit)).findFirst().orElse(null);
                HashMap<String, Object> result = new HashMap<>();
                if(foundObj != null){
                    result.put("geo", foundObj.getGeo());
                    result.put("unit", foundObj.getUnit());
                    result.put("avg", foundObj.getAvg());
                    result.put("min", foundObj.getMin());
                    result.put("max", foundObj.getMax());
                    result.put("sum", foundObj.getSum());
                    result.put("devstd", foundObj.getDevStandard());
                }
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(result);
            } else return makeErrorMessage("Devono essere forniti sia l'unità che l'area geografica!");
        } catch (Exception e){
            return e.toString();
        }
    }

    @RequestMapping(value = "/metadata", method = RequestMethod.GET, produces="application/json")
    String getMetadata(){
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonSchemaGenerator schemaGen = new JsonSchemaGenerator(mapper);
            JsonSchema schema = schemaGen.generateSchema(AgricultureAid.class);
            return mapper.writeValueAsString(schema);
        } catch (Exception e){
            return e.toString();
        }
    }

    private String makeErrorMessage(String message) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String, String> result = new HashMap<>();
            result.put("error", message);
            return mapper.writeValueAsString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private ArrayList<AgricultureAid> parseCommands(AgricultureAidCollection obj, JSONObject parsedJson) throws CommandNotFoundException{
        String field = parsedJson.keys().next();
        if (field.equals("$or")) {
            ArrayListUtils<AgricultureAid> utils = new ArrayListUtils<>();
            ArrayList<ArrayList<AgricultureAid>> conditions = new ArrayList<>();
            for (Object currentCondition : parsedJson.getJSONArray(field)) {
                if(currentCondition instanceof JSONObject) {
                    conditions.add(parseCommands(obj, (JSONObject) currentCondition));
                }
            }
            return utils.union(conditions);
        } else if (field.equals("$and")) {
            ArrayListUtils<AgricultureAid> utils = new ArrayListUtils<>();
            ArrayList<ArrayList<AgricultureAid>> conditions = new ArrayList<>();
            for (Object currentCondition : parsedJson.getJSONArray(field)) {
                if(currentCondition instanceof JSONObject) {
                    conditions.add(parseCommands(obj, (JSONObject) currentCondition));
                }
            }
            return utils.intersection(conditions);
        } else {
            JSONObject innerObj = parsedJson.getJSONObject(field);
            String operator = innerObj.keys().next();
            switch (operator) {
                case "$bt":
                    double min = innerObj.getJSONArray(operator).getFloat(0);
                    double max = innerObj.getJSONArray(operator).getFloat(1);
                    return obj.filterField(field, operator, min, max);
                case "$in":
                case "$nin":
                    ArrayList<Object> values = new ArrayList<>();
                    for(Object el : innerObj.getJSONArray(operator)) {
                        values.add(el);
                    }
                    return obj.filterField(field, operator, values.toArray());
                case "$eq":
                case "$not":
                case "$lt":
                case "$gt":
                    try {
                        float val = innerObj.getFloat(operator);
                        return obj.filterField(field, operator, val);
                    } catch (Exception e) {
                        String val = innerObj.getString(operator);
                        return obj.filterField(field, operator, val);
                    }
                default:
                    throw new CommandNotFoundException("Uno o più dei comandi inseriti non è valido");
            }
        }
    }

}
