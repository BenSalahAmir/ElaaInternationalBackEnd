package com.bezkoder.spring.security.mongodb.security.services;

import com.bezkoder.spring.security.mongodb.models.User;
import com.bezkoder.spring.security.mongodb.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userDao;



    public User activateUser(String token) {
        User user = userDao.findByVerificationToken(token);
        if (user != null) {
            user.setIsverified(1);
            user.setVerificationToken(null);
            userDao.save(user);
        }
        return user;
    }
    public User findByUserEmail(String UserEmail)
    {
        return this.userDao.findByEmail(UserEmail);
    }


}
