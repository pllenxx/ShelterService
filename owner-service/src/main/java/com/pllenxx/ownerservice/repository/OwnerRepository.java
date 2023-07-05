package com.pllenxx.ownerservice.repository;

import com.pllenxx.entities.jpaEntities.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
    void deleteById(Long id);
}
