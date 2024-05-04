package tn.elaainternational.reservation.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import tn.elaainternational.reservation.Models.User;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);
    public User findByEmail(String UserEmail);


    Boolean existsByEmail(String email);

    User findByVerificationToken(String token);

}
