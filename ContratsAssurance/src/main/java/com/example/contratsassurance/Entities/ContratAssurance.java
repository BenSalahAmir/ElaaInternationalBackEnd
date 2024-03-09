package com.example.contratsassurance.Entities;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "contratsAssurance")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class ContratAssurance {

    @Id
    private String id;
    private String compagnieAssurance;
    private String typeAssurance;
    private String nomAssure;
    private int numeroSouscription;
    private String beneficiaire1;
    private String beneficiaire2;
    private String beneficiaire3;
    private String region;
    private String services;
    private String plafond;
    private String nombreDeclarations;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompagnieAssurance() {
        return compagnieAssurance;
    }

    public void setCompagnieAssurance(String compagnieAssurance) {
        this.compagnieAssurance = compagnieAssurance;
    }

    public String getTypeAssurance() {
        return typeAssurance;
    }

    public void setTypeAssurance(String typeAssurance) {
        this.typeAssurance = typeAssurance;
    }

    public String getNomAssure() {
        return nomAssure;
    }

    public void setNomAssure(String nomAssure) {
        this.nomAssure = nomAssure;
    }

    public int getNumeroSouscription() {
        return numeroSouscription;
    }

    public void setNumeroSouscription(int numeroSouscription) {
        this.numeroSouscription = numeroSouscription;
    }

    public String getBeneficiaire1() {
        return beneficiaire1;
    }

    public void setBeneficiaire1(String beneficiaire1) {
        this.beneficiaire1 = beneficiaire1;
    }

    public String getBeneficiaire2() {
        return beneficiaire2;
    }

    public void setBeneficiaire2(String beneficiaire2) {
        this.beneficiaire2 = beneficiaire2;
    }

    public String getBeneficiaire3() {
        return beneficiaire3;
    }

    public void setBeneficiaire3(String beneficiaire3) {
        this.beneficiaire3 = beneficiaire3;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPlafond() {
        return plafond;
    }

    public void setPlafond(String plafond) {
        this.plafond = plafond;
    }

    public String getNombreDeclarations() {
        return nombreDeclarations;
    }

    public void setNombreDeclarations(String nombreDeclarations) {
        this.nombreDeclarations = nombreDeclarations;
    }
}
