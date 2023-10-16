package com.passlocker.passlocker.repositores;

import com.passlocker.passlocker.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String>,
        PagingAndSortingRepository<UserEntity, String> {

    //  function to get the user by the given userid
    public UserEntity getUserEntityByUserId(String userId);
    //  function to get the user by the username
    public UserEntity getUserEntityByUsername(String username);
    //  function to get the user by the given email address
    public UserEntity getUserEntityByEmailAddress(String emailAddress);
    //  function to check if a user exists by username, userid or emailaddress
    public boolean existsByUserIdOrUsernameOrEmailAddress(String userId, String username, String emailAddress);
}
