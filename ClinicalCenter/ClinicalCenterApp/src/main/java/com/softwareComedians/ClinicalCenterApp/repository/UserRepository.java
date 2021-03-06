package com.softwareComedians.ClinicalCenterApp.repository;

import com.softwareComedians.ClinicalCenterApp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository

public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    //User findByUsername(String username);
}
