package com.bezkoder.spring.security.mongodb.controllers;

import com.bezkoder.spring.security.mongodb.models.User;
import com.bezkoder.spring.security.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.security.mongodb.repository.RoleRepository;
import com.bezkoder.spring.security.mongodb.repository.UserRepository;
import com.bezkoder.spring.security.mongodb.security.jwt.JwtUtils;
import com.bezkoder.spring.security.mongodb.security.services.EmailServiceImpl;
import com.bezkoder.spring.security.mongodb.security.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//for Angular Client (withCredentials)
//@CrossOrigin(origins = "http://localhost:8081", maxAge = 3600, allowCredentials="true")
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/test")
public class TestController {

  @Autowired
  UserRepository userRepository;
  @Autowired
  private EmailServiceImpl emailServ;

  @Autowired
  RoleRepository roleRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  PasswordEncoder encoder;

  @Autowired
  JwtUtils jwtUtils;


  @Autowired
  UserService userService;
  @GetMapping("/all")
  public String allAccess() {
    return "Public Content.";
  }

  @GetMapping("/user")
  @PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
  public String userAccess() {
    return "User Content.";
  }

  @GetMapping("/mod")
  @PreAuthorize("hasRole('MODERATOR')")
  public String moderatorAccess() {
    return "Moderator Board.";
  }

  @GetMapping("/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return "Admin Board.";
  }



  @PutMapping("/update/{id}")
  public ResponseEntity<?> updateUser(@PathVariable String id, @Valid @RequestBody User updatedUser) {
    Optional<User> userData = userRepository.findById(id);
    if (userData.isPresent()) {
      User existingUser = userData.get();

      if (userRepository.existsByUsername(existingUser.getUsername())) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Erreur : Le nom d'utilisateur est déjà pris !"));
      }

      // Update user data
      if (updatedUser.getUsername() != null) {
        existingUser.setUsername(updatedUser.getUsername());
      }
      if (updatedUser.getNumeroTelephone() != 0) {
        existingUser.setNumeroTelephone(updatedUser.getNumeroTelephone());
      }
      if (updatedUser.getRegion() != null) {
        existingUser.setRegion(updatedUser.getRegion());
      }
      if (!isValidPassword(updatedUser.getPassword())) {
        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Erreur : Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un symbole et avoir une longueur entre 6 et 40 caractères."));
      }
      if (updatedUser.getPassword() != null) {
        // Encrypt the new password
        existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
      }


      // Save updated user
      userRepository.save(existingUser);
      return ResponseEntity.ok(new MessageResponse("User updated successfully"));
    } else {
      return ResponseEntity.notFound().build();
    }
  }
  private boolean isValidPassword(String password) {
    String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,40}$";
    Pattern pattern = Pattern.compile(passwordRegex);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }
}
