package com.example.service.Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "AssuranceType")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssuranceType {

    @Id
    private String typeId;
    private String typeName;

}
