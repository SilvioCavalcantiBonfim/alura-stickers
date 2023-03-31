package com.example.imersaoAlura;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LanguageController {

    @Autowired
    private LanguageRepository repository;

    @GetMapping("/language/all")
    public List<Language> getAll(){
        return repository.findAll();  
    }

    @PostMapping("/language")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Language registerLanguage(@RequestBody Language language){
        return repository.save(language);
    }
}
