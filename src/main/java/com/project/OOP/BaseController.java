package com.project.OOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jsonSchema.JsonSchema;
import com.fasterxml.jackson.module.jsonSchema.JsonSchemaGenerator;
import com.project.OOP.utils.CSVParser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class BaseController {
    @RequestMapping(value = "/data", method = RequestMethod.GET, produces="application/json")
    String getAllData(){
        try {
            ArrayList<AgricultureAid> objects = CSVParser.getDataFromCSV();
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(objects);
        } catch (IOException e){
            return e.toString();
        }
    }

    @RequestMapping(value = "/stats", method = RequestMethod.GET, produces="application/json")
    String getStats(@RequestParam String Geo){
        try {
            ArrayList<AgricultureAid> objects = CSVParser.getDataFromCSV();
            AgricultureAid foundObj = objects.stream().filter(el -> el.getGeo().equals(Geo)).findFirst().orElse(null);
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
}
