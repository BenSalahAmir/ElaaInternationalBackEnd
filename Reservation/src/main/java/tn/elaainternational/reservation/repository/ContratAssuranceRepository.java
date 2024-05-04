package tn.elaainternational.reservation.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import tn.elaainternational.reservation.Models.ContratAssurance;

import java.util.Optional;

@Repository
public interface ContratAssuranceRepository extends MongoRepository<ContratAssurance, String> {
    Optional<ContratAssurance> findByAdressemail(String adresseMail);

}