package com.bezkoder.spring.security.mongodb.repository;

import com.bezkoder.spring.security.mongodb.models.UserMail;

public interface IUserEmailRepository {

    public void sendCodeByMail(UserMail mail);

}
