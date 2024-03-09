package com.example.contratsassurance.Service;

import com.example.contratsassurance.Entities.ContratAssurance;
import com.example.contratsassurance.Repository.ContratAssuranceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContratAssuranceService {

    @Autowired
    private ContratAssuranceRepository contratAssuranceRepository;

    public ContratAssurance saveContratAssurance(ContratAssurance contratAssurance) {
        return contratAssuranceRepository.save(contratAssurance);
    }

    public ContratAssurance updateContratAssurance(String id, ContratAssurance updatedContratAssurance) {
        Optional<ContratAssurance> existingContrat = contratAssuranceRepository.findById(id);
        if (existingContrat.isPresent()) {
            ContratAssurance contratToUpdate = existingContrat.get();
            contratToUpdate.setCompagnieAssurance(updatedContratAssurance.getCompagnieAssurance());
            contratToUpdate.setTypeAssurance(updatedContratAssurance.getTypeAssurance());
            contratToUpdate.setNomAssure(updatedContratAssurance.getNomAssure());
            contratToUpdate.setNumeroSouscription(updatedContratAssurance.getNumeroSouscription());
            contratToUpdate.setBeneficiaire1(updatedContratAssurance.getBeneficiaire1());
            contratToUpdate.setRegion(updatedContratAssurance.getRegion());
            contratToUpdate.setServices(updatedContratAssurance.getServices());
            contratToUpdate.setPlafond(updatedContratAssurance.getPlafond());
            contratToUpdate.setNombreDeclarations(updatedContratAssurance.getNombreDeclarations());

            return contratAssuranceRepository.save(contratToUpdate);
        } else {
            return null;
        }
    }

    public void deleteContratAssurance(String id) {
        contratAssuranceRepository.deleteById(id);
    }

    public List<ContratAssurance> getAllContrats() {
        return contratAssuranceRepository.findAll();
    }

public ContratAssurance getcontratbyId(String id){
        return contratAssuranceRepository.findById(id).orElse(null);
}
}