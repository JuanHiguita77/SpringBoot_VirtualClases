package com.riwi.virtualClasses.domain.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.riwi.virtualClasses.domain.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    Page<Student> findByNameContainingAndActiveTrueOrDescriptionContainingAndActiveTrue(String name, String description, Pageable pageable);
    
    Page<Student> findByActiveTrue(Pageable pagination);
}
