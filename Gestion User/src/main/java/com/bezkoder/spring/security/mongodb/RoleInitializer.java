package com.bezkoder.spring.security.mongodb;

import com.bezkoder.spring.security.mongodb.models.ERole;
import com.bezkoder.spring.security.mongodb.models.Role;
import com.bezkoder.spring.security.mongodb.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class RoleInitializer implements ApplicationRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeRoles();
    }

    private void initializeRoles() {
        if (!roleRepository.findByName(ERole.ROLE_USER).isPresent()) {
            roleRepository.save(new Role(ERole.ROLE_USER));
        }
        if (!roleRepository.findByName(ERole.ROLE_MODERATOR).isPresent()) {
            roleRepository.save(new Role(ERole.ROLE_MODERATOR));
        }
        if (!roleRepository.findByName(ERole.ROLE_ADMIN).isPresent()) {
            roleRepository.save(new Role(ERole.ROLE_ADMIN));
        }
    }
}
