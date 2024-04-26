package com.example.contratsassurance.Repository;

import com.example.contratsassurance.Entities.ContratAssurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContratAssuranceRepository extends MongoRepository<ContratAssurance, String> {
    Optional<ContratAssurance> findByAdressemail(String adresseMail);

}