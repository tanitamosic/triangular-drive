package com.NWT_KTS_project.repository;

import com.NWT_KTS_project.model.users.Role;
import com.NWT_KTS_project.model.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {

    User findUserByEmail(String email);

    @Query(nativeQuery = true, value="SELECT * FROM users WHERE users.type=?1")
    List<User> findUsersByRole(String role);

}
