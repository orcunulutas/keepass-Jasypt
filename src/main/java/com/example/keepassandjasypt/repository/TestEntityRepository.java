package com.example.keepassandjasypt.repository;

import com.example.keepassandjasypt.entity.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity,Long> {
}
