package com.pllenxx.kittiesservice.repository;

import com.pllenxx.entities.jpaEntities.Kitty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KittyRepository extends JpaRepository<Kitty, Long> {
    void deleteById(Long id);
    List<Kitty> getKittiesByOwnerId(Long ownerId);
}
