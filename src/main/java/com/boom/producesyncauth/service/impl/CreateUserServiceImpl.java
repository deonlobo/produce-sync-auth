package com.boom.producesyncauth.service.impl;

import com.boom.producesyncauth.config.JwtService;
import com.boom.producesyncauth.data.*;
import com.boom.producesyncauth.repository.AddressRepository;
import com.boom.producesyncauth.repository.UserProfileRepository;
import com.boom.producesyncauth.service.AutoIncrementService;
import com.boom.producesyncauth.service.CreateUserService;
import com.boom.producesyncauth.service.Sender;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class CreateUserServiceImpl implements CreateUserService {
    @Autowired
    private UserProfileRepository repository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private AutoIncrementService autoIncrementService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private Sender sender;

    @Value("${opencage.key}")
    private String openCageKey;
    @Value("${opencage.url}")
    private String openCageUrl;

    @Override
    public ResponseEntity<String> createUser(UserProfile userProfile, Role role) {
        try {
            //Create User
            UserProfile existingUserProfile = repository.findByUsername(userProfile.getUsername());
            if(Objects.nonNull(existingUserProfile)){
                return ResponseEntity.status(409).body("User already exists, Please login");
            }
            //Get the geocode
            OpenCageResponseDTO responseDTO = getGeocode(userProfile.getAddress());
            if(Objects.isNull(responseDTO)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Could not find address, Enter a valid address");
            }
            userProfile.setId(autoIncrementService.getOrUpdateIdCount(role.name()));
            userProfile.setCreatedTs(Instant.now().toEpochMilli());
            userProfile.setRole(role);
            repository.insert(userProfile);

            //Insert Address
            Address address = userProfile.getAddress();
            address.setId(userProfile.getId());
            //Set location as specified in the mongodb geospecial document
            Location location = new Location();
            List<Double> lnglat = new ArrayList<>();
            lnglat.add(responseDTO.getResults().get(0).getGeometry().getLng());
            lnglat.add(responseDTO.getResults().get(0).getGeometry().getLat());
            location.setCoordinates(lnglat);
            address.setLocation(location);

            address.setRole(userProfile.getRole());
            addressRepository.save(address);

            var jwtToken = jwtService.generateToken(userProfile);
            sender.send(userProfile, jwtToken);
            return ResponseEntity.ok("Email sent successfully");
        } catch (Exception e) {
            // Handle the exception, log it, or take any appropriate action.
            e.printStackTrace();
            // Return false if an error occurs during insertion
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Exception occured please try again");
        }
    }



    public OpenCageResponseDTO getGeocode(Address address) throws JsonProcessingException {
        String encodedAddress = address.getAddressLine1()+", "+ address.getCity()
                                +", "+address.getProvince()+", "+ address.getCountry()
                                +", "+address.getPostalCode();
        encodedAddress = URLEncoder.encode(encodedAddress);
        System.out.println(encodedAddress);
        String apiUrl = String.format("%s?q=%s&key=%s", openCageUrl, encodedAddress, openCageKey);
        System.out.println(apiUrl);

        ResponseEntity<OpenCageResponseDTO> responseEntity = restTemplate.getForEntity(apiUrl, OpenCageResponseDTO.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            // Handle error cases
            return new OpenCageResponseDTO();
        }
    }

    @Override
    public ResponseEntity<String> sendAuthMail(UserProfile userProfile, Role role) {
        var user = repository.findByUsername(userProfile.getUsername());
        if(!user.getRole().equals(role)){
            return ResponseEntity.status(403).body("The User is not a "+role.name());
        }
        var jwtToken = jwtService.generateToken(user);
        sender.send(user, jwtToken);
        return ResponseEntity.ok("Email sent successfully");
    }

}
