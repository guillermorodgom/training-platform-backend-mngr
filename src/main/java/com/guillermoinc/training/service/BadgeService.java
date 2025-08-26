package com.guillermoinc.training.service;

import com.guillermoinc.training.dto.BadgeResponseDto;

import java.util.List;

public interface BadgeService {
    
    List<BadgeResponseDto> getAllBadges();
    
    List<BadgeResponseDto> getUserBadges(String email);
    
    List<BadgeResponseDto> getAvailableBadges(String email);
    
    BadgeResponseDto awardBadge(Long badgeId, String email);
    
    void checkAndAwardBadges(String email, Long courseId);
}