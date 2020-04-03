package com.user.userapplication.service;


import com.user.userapplication.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> getAll();
    Optional<User> getById(long id);
    void delete(User user);
    User save(User user);
    String findUniqueIdentification(String uniqueIdentification);

}
