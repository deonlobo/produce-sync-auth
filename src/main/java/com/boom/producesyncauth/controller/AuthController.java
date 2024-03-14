package com.boom.producesyncauth.controller;


import com.boom.producesyncauth.data.Role;
import com.boom.producesyncauth.data.UserProfile;
import com.boom.producesyncauth.service.CreateUserService;
import com.boom.producesyncauth.service.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private CreateUserService createUserService;

    @PostMapping("/seller/create")
    @CrossOrigin
    public ResponseEntity<String> createResourceSeller(@RequestBody UserProfile userProfile) {
        return createUserService.createUser(userProfile, Role.SELLER);
    }

    @PostMapping("/buyer/create")
    @CrossOrigin
    public ResponseEntity<String> createResourceBuyer(@RequestBody UserProfile userProfile) {
        return createUserService.createUser(userProfile, Role.BUYER);
    }

    @PostMapping("/seller/login")
    @CrossOrigin
    public ResponseEntity<String> loginSeller(@RequestBody UserProfile userProfile) {
        return createUserService.sendAuthMail(userProfile,  Role.SELLER);
    }

    @PostMapping("/buyer/login")
    @CrossOrigin
    public ResponseEntity<String> loginBuyer(@RequestBody UserProfile userProfile) {
        return createUserService.sendAuthMail(userProfile, Role.BUYER);
    }
}
