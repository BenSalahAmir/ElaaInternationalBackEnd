package com.example.service.Services;

import com.example.service.Models.AssuranceType;
import com.example.service.Repository.TypeAssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssuranceTypeService {

    @Autowired
    private TypeAssuranceRepository typeAssuranceRepository;

    // Create
    public AssuranceType createAssuranceType(AssuranceType assuranceType) {
        return typeAssuranceRepository.save(assuranceType);
    }

    // Read
    public List<AssuranceType> getAllAssuranceTypes() {
        return typeAssuranceRepository.findAll();
    }

    public Optional<AssuranceType> getAssuranceTypeById(String id) {
        return typeAssuranceRepository.findById(id);
    }

    // Update
    public AssuranceType updateAssuranceType(String id, AssuranceType assuranceType) {
        assuranceType.setTypeId(id);
        return typeAssuranceRepository.save(assuranceType);
    }

    // Delete
    public void deleteAssuranceType(String id) {
        typeAssuranceRepository.deleteById(id);
    }
}
