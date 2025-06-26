package com.example.bookstore.service;

import com.example.bookstore.model.CreditCard;
import com.example.bookstore.model.UserProfile;


public interface UserServices {
    UserProfile createUser(UserProfile user);
    UserProfile retrieveUser(String username);
    UserProfile updateUser(String username, String field, String value);
    CreditCard createUserCard(String username, CreditCard card);
}
