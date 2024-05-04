package com.example.service.Services;

import com.example.service.Models.ContratAssurance;
import com.example.service.Models.ServiceContrat;
import com.example.service.Repository.ContratAssuranceRepository;
import com.example.service.Repository.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ServiceContratServiceImpl {

    @Autowired
    private ContratAssuranceRepository contratAssuranceRepository;


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




    public Optional<ContratAssurance> getContratByEmail(String email) {
        return contratAssuranceRepository.findByAdressemail(email);
    }

    public List<ServiceContrat> getServicesByEmailAsObjects(String email) {
        Optional<ContratAssurance> contratOptional = getContratByEmail(email);
        if (contratOptional.isPresent()) {
            ContratAssurance contrat = contratOptional.get();
            String nombreDeclarations = contrat.getNombreDeclarations();

            // Check if nombreDeclarations is equal to "3/3"
            if ("3/3".equals(nombreDeclarations)) {
                return Collections.emptyList();
            }

            String services = contrat.getServices();
            List<String> servicesList = Arrays.asList(services.split(", "));
            List<ServiceContrat> servicesObjects = serviceRepository.findByServiceNameIn(servicesList);
            servicesObjects.forEach(service -> {
                System.out.println("Service: " + service.getServiceName());
            });
            return servicesObjects;
        } else {
            return Collections.emptyList();
        }
    }






}
