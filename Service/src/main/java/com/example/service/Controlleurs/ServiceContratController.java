package com.example.service.Controlleurs;

import com.example.service.Models.ContratAssurance;
import com.example.service.Models.ServiceContrat;
import com.example.service.Services.ServiceContratServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/services")
public class ServiceContratController {

    @Autowired
    private ServiceContratServiceImpl serviceContratService;

    @GetMapping
    public List<ServiceContrat> getAllServices() {
        return serviceContratService.getAllServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceContrat> getServiceById(@PathVariable String id) {
        Optional<ServiceContrat> service = serviceContratService.getServiceById(id);
        return service.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ServiceContrat createService(@RequestBody ServiceContrat service) {
        return serviceContratService.createService(service);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ServiceContrat> updateService(@PathVariable String id, @RequestBody ServiceContrat service) {
        return ResponseEntity.ok(serviceContratService.updateService(id, service));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable String id) {
        serviceContratService.deleteService(id);
        return ResponseEntity.ok().build();
    }


    /*@GetMapping("/getservices/{email}")
    public ResponseEntity<String> getServicesByEmail(@PathVariable String email) {
        Optional<String> servicesOptional = serviceContratService.(email);
        if (servicesOptional.isPresent()) {
            return ResponseEntity.ok(servicesOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }*/


    @GetMapping("/test/{email}")
    public ResponseEntity<List<ServiceContrat>> getServicesByEmailAsObjects(@PathVariable String email) {
        List<ServiceContrat> services = serviceContratService.getServicesByEmailAsObjects(email);
        if (!services.isEmpty()) {
            return ResponseEntity.ok(services);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



}
