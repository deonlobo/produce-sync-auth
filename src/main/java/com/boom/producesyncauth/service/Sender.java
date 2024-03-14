package com.boom.producesyncauth.service;

import com.boom.producesyncauth.data.Role;
import com.boom.producesyncauth.data.UserProfile;

/**
 * An interface for sending temporary authentication tokens to users.
 * 
 * @author Arik Cohen
 * @since Jan 30, 2018
 */
public interface Sender {

  void send (UserProfile userProfile, String aToken);
  
}
