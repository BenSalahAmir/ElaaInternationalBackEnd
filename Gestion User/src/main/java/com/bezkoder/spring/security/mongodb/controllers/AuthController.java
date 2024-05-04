package com.bezkoder.spring.security.mongodb.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
    emailServ.sendVerificationEmail(user);
    userRepository.save(user);

    return ResponseEntity.ok(new MessageResponse("Utilisateur enregistré avec succès !"));
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
      emailServ.sendcodereset(mail);
      System.out.println("la variable User est" + user);
      user.setUserCode(code);
      userRepository.save(user);
      accountResponse.setResult(1);
    } else {
      accountResponse.setResult(0);
    }
    return accountResponse;
  }



  @PostMapping("/resetPassword")
  public UserAccountResponse resetPassword(@RequestBody UserNewPassword newPassword) {
    User user = this.userService.findByUserEmail(newPassword.getEmail());
    UserAccountResponse accountResponse = new UserAccountResponse();
    if (user != null) {
      if (user.getUserCode().equals(newPassword.getCode())) {
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        userRepository.save(user);
        accountResponse.setResult(1);
      } else {
        accountResponse.setResult(0);
      }
    } else {
      accountResponse.setResult(0);
    }
    return accountResponse;
  }


}
