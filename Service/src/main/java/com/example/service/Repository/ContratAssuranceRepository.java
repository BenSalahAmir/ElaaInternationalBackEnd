package com.example.service.Repository;

import com.example.service.Models.ContratAssurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContratAssuranceRepository extends MongoRepository<ContratAssurance, String> {



    Optional<ContratAssurance> findByAdressemail(String adressemail);

    Optional<String> findServicesByAdressemail(String adressemail);

    @Query(value = "{ 'adressemail' : ?0 }", fields = "{ 'services' : 1 }")
    Optional<String> findServicesByAdressemailProjection(String adressemail);

}
