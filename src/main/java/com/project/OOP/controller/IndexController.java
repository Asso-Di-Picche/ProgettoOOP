package com.project.OOP.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Classe che implementa il controller utilizzato per mostrare il frontend dell'applicazione all'utente
 */
@Controller
public class IndexController {

    /**
     * Rotta in cui verrà restituito il template contenente il frontend dell'applicazione
     * @return Stringa che verrà utilizzata per fare il render del template con il nome corrispondente
     */
    @GetMapping("/")
    public String index(){
        return "index";
    }


}
