package com.example.service.Repository;

import com.example.service.Models.ServiceContrat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceRepository extends MongoRepository<ServiceContrat, String> {
}
