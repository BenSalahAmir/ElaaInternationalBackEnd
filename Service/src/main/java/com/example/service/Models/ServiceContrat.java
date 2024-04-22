package com.example.service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "ServicesContrat")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceContrat {

    @Id
    private String serviceId;
    private String serviceName;



}
