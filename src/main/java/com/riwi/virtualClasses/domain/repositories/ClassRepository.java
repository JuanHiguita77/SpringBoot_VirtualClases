package com.riwi.virtualClasses.domain.repositories;

import com.riwi.virtualClasses.domain.entities.Class;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    Page<Class> findByActiveTrue(Pageable pageable);
    Page<Class> findByNameContainingAndActiveTrueOrDescriptionContainingAndActiveTrue(String name, String description, Pageable pageable);
}
