package com.riwi.virtualClasses.domain.repositories;

import com.riwi.virtualClasses.domain.entities.Lesson;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByClassIdAndActiveTrue(Long classId);
    List<Lesson> findByActiveTrue();
}

