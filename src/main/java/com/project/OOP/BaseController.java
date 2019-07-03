package com.project.OOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.OOP.utils.ArrayListUtils;
import com.project.OOP.utils.CSVParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

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
            if(!filter.isEmpty()){
                try {
                    json = new JSONObject(filter);
                    result = parseCommands(objects, json);
                } catch (ClassCastException e) {
                    return makeErrorMessage("Sono stati inseriti dei valori in un formato errato");
                } catch (JSONException e) {
                    return makeErrorMessage("Il JSON non sembra essere ben formato");
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

    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces="application/json")
    String getStats(@RequestParam String Geo){
        try {
            AgricultureAidCollection objects = CSVParser.getDataFromCSV();
            AgricultureAid foundObj = objects.getAgricultureAids().stream().filter(el -> el.getGeo().equals(Geo)).findFirst().orElse(null);
            HashMap<String, String> result = new HashMap<>();
            if(foundObj != null){
                result.put("geo", foundObj.getGeo());
                result.put("avg", Float.toString(foundObj.getAvg()));
                result.put("min", Float.toString(foundObj.getMin()));
                result.put("max", Float.toString(foundObj.getMax()));
                result.put("sum", Float.toString(foundObj.getSum()));
            } else {
                result.put("error", "Element not found!");
            }
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(result);
        } catch (IOException e){
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

    private ArrayList<AgricultureAid> parseCommands(AgricultureAidCollection obj, JSONObject parsedJson){
        //TODO: implementare in e nin per i numeri?
        String field = parsedJson.keys().next();
        if (field.equals("$or")) {
            ArrayListUtils<AgricultureAid> utils = new ArrayListUtils<>();
            ArrayList<AgricultureAid> firstSelection = parseCommands(obj, parsedJson.getJSONArray(field).getJSONObject(0));
            ArrayList<AgricultureAid> secondSelection = parseCommands(obj, parsedJson.getJSONArray(field).getJSONObject(1));
            return utils.union(firstSelection, secondSelection);
        } else if (field.equals("$and")) {
            ArrayListUtils<AgricultureAid> utils = new ArrayListUtils<>();
            ArrayList<AgricultureAid> firstSelection = parseCommands(obj, parsedJson.getJSONArray(field).getJSONObject(0));
            ArrayList<AgricultureAid> secondSelection = parseCommands(obj, parsedJson.getJSONArray(field).getJSONObject(1));
            return utils.intersection(firstSelection, secondSelection);
        } else {
            JSONObject innerObj = parsedJson.getJSONObject(field);
            String operator = innerObj.keys().next();
            switch (operator) {
                case "$bt":
                    float min = ((Double) innerObj.getJSONArray(operator).get(0)).floatValue();
                    float max = ((Double) innerObj.getJSONArray(operator).get(1)).floatValue();
                    return obj.filterField(field, operator, min, max);
                case "$in":
                case "$nin":
                    ArrayList<Object> values = new ArrayList<>();
                    for(Object el : innerObj.getJSONArray(operator)) {
                        values.add(el);
                    }
                    return obj.filterField(field, operator, values.toArray());
            }

            try {
                float val = innerObj.getFloat(operator);
                return obj.filterField(field, operator, val);
            } catch (Exception e) {
                String val = innerObj.getString(operator);
                return obj.filterField(field, operator, val);
            }
        }
    }

}
