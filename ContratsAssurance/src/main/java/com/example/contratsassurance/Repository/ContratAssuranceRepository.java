package com.example.contratsassurance.Repository;

import com.example.contratsassurance.Entities.ContratAssurance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContratAssuranceRepository extends MongoRepository<ContratAssurance, String> {
}