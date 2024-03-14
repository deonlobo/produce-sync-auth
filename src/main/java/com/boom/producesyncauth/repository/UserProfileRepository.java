package com.boom.producesyncauth.repository;

import com.boom.producesyncauth.data.UserProfile;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface UserProfileRepository extends MongoRepository<UserProfile,String> {
    UserProfile findByUsername(String username);
}
