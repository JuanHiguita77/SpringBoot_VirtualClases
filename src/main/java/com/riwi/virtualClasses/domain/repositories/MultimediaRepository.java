package com.riwi.virtualClasses.domain.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.riwi.virtualClasses.domain.entities.Multimedia;

public interface MultimediaRepository extends JpaRepository<Multimedia, Long>{
    
}
