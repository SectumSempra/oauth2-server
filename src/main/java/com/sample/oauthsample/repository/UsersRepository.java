package com.sample.oauthsample.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sample.oauthsample.model.Users;

@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {

    Optional<Users> findByName(String name);

    @Transactional
    @Modifying
    @Query("delete from Users where name =:name")
    void deleteByName(@Param("name") String name);
}
