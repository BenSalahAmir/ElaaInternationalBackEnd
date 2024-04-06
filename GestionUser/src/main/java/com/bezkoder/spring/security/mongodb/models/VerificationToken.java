package com.bezkoder.spring.security.mongodb.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "verification_tokens") // Map this entity to a MongoDB collection
public class VerificationToken implements Serializable {
    @Id
    private String id; // Use String type for MongoDB ObjectId

    private String token;

    @DBRef // Use DBRef for referencing User entity
    private User user;

    private LocalDateTime expiryDate;
}
