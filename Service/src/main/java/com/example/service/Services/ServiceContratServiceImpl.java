package com.example.service.Services;

import com.example.service.Models.ServiceContrat;
import com.example.service.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ServiceContratServiceImpl {


    private final ServiceRepository serviceRepository;

    @Autowired
    public ServiceContratServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }



    // Create
    public ServiceContrat createService(ServiceContrat service) {
        return serviceRepository.save(service);
    }

    // Read
    public List<ServiceContrat> getAllServices() {
        return serviceRepository.findAll();
    }

    public Optional<ServiceContrat> getServiceById(String id) {
        return serviceRepository.findById(id);
    }

    // Update
    public ServiceContrat updateService(String id, ServiceContrat service) {
        service.setServiceId(id);
        return serviceRepository.save(service);
    }

    // Delete
    public void deleteService(String id) {
        serviceRepository.deleteById(id);
    }












}
