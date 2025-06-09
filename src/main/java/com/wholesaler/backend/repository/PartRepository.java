package com.wholesaler.backend.repository;

import com.wholesaler.backend.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    List<Part> findByOrderByPartNameAsc();
}
