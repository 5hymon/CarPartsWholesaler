package com.wholesaler.backend.repository;

import com.wholesaler.backend.model.PartCompatibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartCompatibilityRepository extends JpaRepository<PartCompatibility, Integer> {
}
