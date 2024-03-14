package com.boom.producesyncauth.service.impl;


import com.boom.producesyncauth.data.IdCount;
import com.boom.producesyncauth.repository.IdCountRepository;
import com.boom.producesyncauth.service.AutoIncrementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutoIncrementServiceImpl implements AutoIncrementService {
    @Autowired
    private IdCountRepository repository;

    @Override
    public String getOrUpdateIdCount(String stringId) {
        Optional<IdCount> existingDocumentOptional = repository.findById(stringId);

        if (existingDocumentOptional.isPresent()) {
            IdCount exists = existingDocumentOptional.get();
            exists.setCount(exists.getCount()+1);
            return (stringId+"::"+repository.save(exists).getCount());
        } else{
            IdCount countDoc = new IdCount();
            countDoc.setId(stringId);
            countDoc.setCount(1001);
            return (stringId+"::"+repository.insert(countDoc).getCount());
        }
    }
}
