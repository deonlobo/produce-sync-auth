package com.boom.producesyncauth.service;


import com.boom.producesyncauth.data.Role;
import com.boom.producesyncauth.data.UserProfile;
import org.springframework.http.ResponseEntity;

public interface CreateUserService {
    ResponseEntity<String> createUser(UserProfile userProfile, Role role);

    ResponseEntity<String> sendAuthMail(UserProfile userProfile, Role role);
}
