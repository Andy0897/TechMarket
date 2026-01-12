package com.example.TehnicaBG.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends CrudRepository<User, Long> {
    public User findByUsername(@Param("username") String username);
    public User findByEmail(@Param("email") String email);
}