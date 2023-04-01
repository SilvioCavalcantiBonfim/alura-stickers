package com.example.imersaoAlura;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LanguageRepository extends MongoRepository<Language, String>{
    List<Language> findByTitle(String title);
    List<Language> findAllByOrderByRankingAsc();
    List<Language> findAllByOrderByRankingDesc();
}
