package com.project.OOP;

import com.project.OOP.utils.CSVDownloader;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class BaseController {
    @RequestMapping("/")
    void test(){
        try {
            JSONObject obj = CSVDownloader.getJSONFromURL("http://data.europa.eu/euodp/data/api/3/action/package_show?id=nywhJT0h8NIfj3nWwXgjPw");
            CSVDownloader.downloadCSVfromJSON(obj);
        } catch (IOException e){
            System.out.println(e.toString());
        }
    }
}
