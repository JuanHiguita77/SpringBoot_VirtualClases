package com.riwi.virtualClasses.domain.repositories;

import com.riwi.virtualClasses.domain.entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    List<Class> findByActiveTrue();
    List<Class> findByNameContainingOrDescriptionContainingAndActiveTrue(String name, String description);

}
