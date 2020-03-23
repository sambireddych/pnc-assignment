package com.user.userapplication.repository;

import com.user.userapplication.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    String findByuniqueIdentification(String uniqueIdentification);

}
