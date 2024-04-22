package com.example.service.Repository;

import com.example.service.Models.AssuranceType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeAssuranceRepository extends MongoRepository<AssuranceType, String> {
}
