package com.example.poc.repository;

import com.example.poc.repository.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final UserJpaRepository jpaRepository;

    public UserEntity createUser(UserEntity userEntity) {
        return jpaRepository.save(userEntity);
    }

    public List<UserEntity> createUsers(List<UserEntity> userEntities) {
        return jpaRepository.saveAll(userEntities);
    }

    public List<UserEntity> readAll() {
        return jpaRepository.findAll();
    }

    public Optional<UserEntity> readById(Long id) {
        return jpaRepository.findById(id);
    }
}
