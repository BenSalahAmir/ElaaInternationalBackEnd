package com.example.contratsassurance.Controlleurs;

import com.example.contratsassurance.Entities.ContratAssurance;
import com.example.contratsassurance.Service.ContratAssuranceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrat")
@CrossOrigin
public class ContrasAssuranceControlleur {

    @Autowired
    private ContratAssuranceService contratAssuranceService;

    @PostMapping("/add")
    public ContratAssurance addContrat(@RequestBody ContratAssurance contratAssurance) {
        return contratAssuranceService.saveContratAssurance(contratAssurance);
    }

    @PutMapping("/update/{id}")
    public ContratAssurance updateContrat(@PathVariable String id, @RequestBody ContratAssurance updatedContrat) {
        return contratAssuranceService.updateContratAssurance(id, updatedContrat);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteContrat(@PathVariable String id) {
        contratAssuranceService.deleteContratAssurance(id);
    }

    @GetMapping("/all")
    public List<ContratAssurance> getAllContrats() {
        return contratAssuranceService.getAllContrats();
    }

    @GetMapping("/getbyid/{id}")
    public ContratAssurance getContratbyid(@PathVariable String id) {
        return contratAssuranceService.getcontratbyId(id);
    }
}