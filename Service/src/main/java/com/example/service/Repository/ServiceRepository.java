package com.example.service.Repository;

import com.example.service.Models.ServiceContrat;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceRepository extends MongoRepository<ServiceContrat, String> {

    List<ServiceContrat> findByServiceNameIn(List<String> serviceNames);

}
