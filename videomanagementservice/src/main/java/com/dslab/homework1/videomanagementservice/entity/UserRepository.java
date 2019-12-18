package com.dslab.homework1.videomanagementservice.entity;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User,Integer> {
    User findByEmail(String email);

    //void deleteByEmail(String email);
}
