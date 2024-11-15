package com.rainbow_sims.rainbow_SIMS.repository;

import com.rainbow_sims.rainbow_SIMS.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findUserByUsername(String username);  // Change "findUserByUserName" to "findUserByUsername"
}
