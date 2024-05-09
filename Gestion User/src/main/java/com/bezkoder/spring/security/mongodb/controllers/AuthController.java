package com.bezkoder.spring.security.mongodb.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.bezkoder.spring.security.mongodb.Util.UserCode;
import com.bezkoder.spring.security.mongodb.models.*;
import com.bezkoder.spring.security.mongodb.repository.ContratAssuranceRepository;
import com.bezkoder.spring.security.mongodb.security.services.EmailServiceImpl;
import com.bezkoder.spring.security.mongodb.security.services.UserService;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bezkoder.spring.security.mongodb.payload.request.LoginRequest;
import com.bezkoder.spring.security.mongodb.payload.request.SignupRequest;
import com.bezkoder.spring.security.mongodb.payload.response.UserInfoResponse;
import com.bezkoder.spring.security.mongodb.payload.response.MessageResponse;
import com.bezkoder.spring.security.mongodb.repository.RoleRepository;
import com.bezkoder.spring.security.mongodb.repository.UserRepository;
import com.bezkoder.spring.security.mongodb.security.jwt.JwtUtils;
import com.bezkoder.spring.security.mongodb.security.services.UserDetailsImpl;




@CrossOrigin(origins = "*", maxAge = 3600)
//@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  @Autowired
  AuthenticationManager authenticationManager;


  @Autowired
  ContratAssuranceRepository contratAssuranceRepository;

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




  @GetMapping("/user/{email}")
  public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
    User user = userService.findByUserEmail(email);
    if (user != null) {
      return ResponseEntity.ok(user);
    } else {
      return ResponseEntity.notFound().build();
    }
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


  @PutMapping("/user/{id}")
  public ResponseEntity<?> updateUser2(@PathVariable String id, @Valid @RequestBody User updatedUser) {
    Optional<User> userData = userRepository.findById(id);
    if (userData.isPresent()) {
      User existingUser = userData.get();
      // Update user data
      existingUser.setUsername(updatedUser.getUsername());
      existingUser.setPassword(updatedUser.getPassword());
      existingUser.setNumeroTelephone(updatedUser.getNumeroTelephone());
      existingUser.setRegion(updatedUser.getRegion());
      // Save updated user
      userRepository.save(existingUser);
      return ResponseEntity.ok(new MessageResponse("User updated successfully"));
    } else {
      return ResponseEntity.notFound().build();
    }
  }


  @PutMapping("/updateuser")
  public ResponseEntity<?> update(@RequestBody User c) {

    Optional<User> userData = userRepository.findById(c.getId());
    if (userData.isPresent()) {
      User existingUser = userData.get();
      System.out.println("user find  aaaaaaaaaa"+ userData);



      if (c.getRegion() == null) {
        existingUser.setRegion(c.getRegion());
      }
      if (c.getNumeroTelephone() == 0) {
        existingUser.setNumeroTelephone(existingUser.getNumeroTelephone());
      }


      // Update user data
      if (c.getUsername() != null) {
        existingUser.setUsername(c.getUsername());
      }
      if (c.getNumeroTelephone() != 0) {
        existingUser.setNumeroTelephone(c.getNumeroTelephone());
      }
      if (c.getRegion() != null) {
        existingUser.setRegion(c.getRegion());
      }
      if (c.getPassword() != null) {
        // Encrypt the new password
        existingUser.setPassword(passwordEncoder.encode(c.getPassword()));
      }

      if (!isValidPassword(c.getPassword())) {

        return ResponseEntity
                .badRequest()
                .body(new MessageResponse("Erreur : Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un symbole et avoir une longueur entre 6 et 40 caractères."));
      }

      existingUser.setId(existingUser.getId());
      existingUser.setEmail(existingUser.getEmail());
      existingUser.setIsverified(1);

      Set<Role> roles = new HashSet<>();
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Erreur : Le rôle n'est pas trouvé."));
      roles.add(userRole);

      existingUser.setRoles(roles);
      System.out.println("roles"+ roles);


      // Save updated user
      userRepository.save(existingUser);
      System.out.println("user updated sucesss"+ existingUser);

      return ResponseEntity.ok(new MessageResponse("User updated successfully"));
    } else {
      System.out.println("errorrrrrrrrr update "+ userData);

      return ResponseEntity.notFound().build();
    }

  }



  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

    List<String> roles = userDetails.getAuthorities().stream()
        .map(item -> item.getAuthority())
        .collect(Collectors.toList());

    UserInfoResponse userInfoResponse = new UserInfoResponse(
            userDetails.getId(),
            userDetails.getUsername(),
            userDetails.getEmail(),
            roles,
            jwtCookie.getValue() // Include the token here
    );

    return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
            .body(userInfoResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

    // Check if username exists
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur : Le nom d'utilisateur est déjà pris !"));
    }

    // Check if email exists
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur : L'adresse email est déjà utilisée !"));
    }

    // Check if numeroSouscription exists
    if (!contratAssuranceRepository.existsByNumeroSouscription(signUpRequest.getRefContrat())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur : Référence de contrat invalide !"));
    }

    // Check if numeroSouscription exists
    if (!contratAssuranceRepository.existsByAdressemail(signUpRequest.getEmail())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur : Email ne pas trouver !"));
    }

    // Password validation
    if (!isValidPassword(signUpRequest.getPassword())) {
      return ResponseEntity
              .badRequest()
              .body(new MessageResponse("Erreur : Le mot de passe doit contenir au moins une lettre majuscule, une lettre minuscule, un symbole et avoir une longueur entre 6 et 40 caractères."));
    }

    ContratAssurance contratAssurance = contratAssuranceRepository.getContratAssurancesByAdressemail(signUpRequest.getEmail());

    // Create new user's account
    User user = new User(signUpRequest.getUsername(),
            signUpRequest.getEmail(),
            encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRoles();
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Erreur : Le rôle n'est pas trouvé."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                    .orElseThrow(() -> new RuntimeException("Erreur : Le rôle n'est pas trouvé."));
            roles.add(adminRole);
            break;
          case "mod":
            Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                    .orElseThrow(() -> new RuntimeException("Erreur : Le rôle n'est pas trouvé."));
            roles.add(modRole);
            break;
          default:
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Erreur : Le rôle n'est pas trouvé."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    user.setIsverified(0);
    user.setNumeroTelephone(contratAssurance.getTelephone());
    user.setRegion(contratAssurance.getRegion());
    //user.setRefContrat(contratAssurance.getNumeroSouscription());
    emailServ.sendVerificationEmail(user);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès !"));
  }

  private boolean isValidPassword(String password) {
    String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,40}$";
    Pattern pattern = Pattern.compile(passwordRegex);
    Matcher matcher = pattern.matcher(password);
    return matcher.matches();
  }


  @PostMapping("/resetPassword")
  public UserAccountResponse resetPassword(@RequestBody UserNewPassword newPassword) {
    UserAccountResponse accountResponse = new UserAccountResponse();

    // Validate the new password
    if (!isValidPassword(newPassword.getPassword())) {
      accountResponse.setResult(0);
      return accountResponse;
    }

    User user = this.userService.findByUserEmail(newPassword.getEmail());

    if (user != null && user.getUserCode().equals(newPassword.getCode())) {
      // If the user exists and the code matches, reset the password
      user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
      userRepository.save(user);
      accountResponse.setResult(1);
    } else {
      // If user not found or code doesn't match, set result to 0
      accountResponse.setResult(0);
    }

    return accountResponse;
  }











  @GetMapping("/activate")
  public ResponseEntity<String> activateAccount(@RequestParam String token) {
    User user = userService.activateUser(token);
    if (user != null) {
      return ResponseEntity.ok("Account activated successfully");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid activation token");
    }
  }




  @PostMapping("/checkEmail")
  public UserAccountResponse resetPasswordEmail(@RequestBody UserResetPassword resetPassword) {
    User user = this.userService.findByUserEmail(resetPassword.getEmail());
    UserAccountResponse accountResponse = new UserAccountResponse();
    if (user != null) {
      String code = UserCode.getCode();
      System.out.println("le code est" + code);
      UserMail mail = new UserMail(resetPassword.getEmail(), code);
      System.out.println("le mail est" + resetPassword.getEmail());
      System.out.println("la variable mail est" + mail);
      //emailServ.sendcodereset(mail);
      System.out.println("la variable User est" + user);
      user.setUserCode(code);
      userRepository.save(user);
      accountResponse.setResult(1);
    } else {
      accountResponse.setResult(0);
    }
    return accountResponse;
  }






}
