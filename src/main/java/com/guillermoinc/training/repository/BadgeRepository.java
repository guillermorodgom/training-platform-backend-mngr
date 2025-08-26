package com.guillermoinc.training.repository;

import com.guillermoinc.training.entity.Badge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BadgeRepository extends JpaRepository<Badge, Long> {
    
    List<Badge> findByCourseId(Integer courseId);
    
    List<Badge> findByCategory(String category);
    
    List<Badge> findByRarity(String rarity);
}