package com.project.OOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.OOP.utils.ArrayListUtils;
import com.project.OOP.utils.CSVParser;
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
            ArrayList<AgricultureAid> test = null;
            if(!filter.isEmpty()){
                json = new JSONObject(filter);
                test = parseCommands(objects, json);
            }

            ObjectMapper mapper = new ObjectMapper();
            if(test != null) {
                return mapper.writeValueAsString(test);
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

    private ArrayList<AgricultureAid> parseCommands(AgricultureAidCollection obj, JSONObject parsedJson){
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
                    return obj.filterField(field, operator, innerObj.getJSONArray(operator).get(0), innerObj.getJSONArray(operator).get(1));
                case "$in":
                case "$nin":
                    ArrayList<Object> values = new ArrayList<>();
                    for(Object el : innerObj.getJSONArray(operator)) {
                        values.add(el);
                    }
                    return obj.filterField(field, operator, values.toArray());
            }
            String val = innerObj.getString(operator);
            return obj.filterField(field, operator, val);
        }
    }

}
