package com.boom.producesyncauth.repository;

import com.boom.producesyncauth.data.IdCount;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IdCountRepository extends MongoRepository<IdCount,String> {
}
