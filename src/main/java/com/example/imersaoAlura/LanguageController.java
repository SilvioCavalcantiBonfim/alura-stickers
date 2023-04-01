package com.example.imersaoAlura;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@CrossOrigin
@RestController
public class LanguageController {

    @Autowired
    private LanguageRepository repository;

    @GetMapping("/language/all")
    public List<Language> getAllorderBy(@RequestParam(defaultValue = "") String orderBy, @RequestParam(defaultValue = "0") int min, @RequestParam(defaultValue = "10000") int max) {
        List<Language> listLanguage = new ArrayList<>();
        List<Language> rt = new ArrayList<>();
        switch (orderBy) {
            case "ASC":
                listLanguage = repository.findAllByOrderByRankingAsc();
                break;
            case "DESC":
                listLanguage = repository.findAllByOrderByRankingDesc();
                break;
            default:
                listLanguage = repository.findAll();
                break;
        }
        for (Language l : listLanguage) {
            if(l.getRanking() >= min && l.getRanking() <= max)
                rt.add(l);
        }
        return rt;
    }

    @PostMapping("/language")
    @ResponseStatus(code = HttpStatus.CREATED)
    public Language registerLanguage(@RequestBody Language language) {
        return repository.save(language);
    }

    @DeleteMapping("/language")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void deleteLanguage(@RequestBody Map<String, String> data) {
        try {
            String id = data.get("id");
            repository.deleteById(id);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/language")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void editLanguage(@RequestBody Language language) {
        try {
            String Id = language.getId();
            Optional<Language> old = repository.findById(Id);
            if (repository.findById(Id).isEmpty()) {
                throw new Exception();
            } else {
                Language update = new Language(old.get().getId(), language.getTitle(), language.getImage(),
                        old.get().getRanking());
                repository.save(update);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
