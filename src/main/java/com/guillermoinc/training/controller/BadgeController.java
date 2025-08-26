package com.guillermoinc.training.controller;

import com.guillermoinc.training.dto.BadgeResponseDto;
import com.guillermoinc.training.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/badges")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BadgeController {
    
    private final BadgeService badgeService;
    
    @GetMapping
    public ResponseEntity<List<BadgeResponseDto>> getAllBadges() {
        List<BadgeResponseDto> badges = badgeService.getAllBadges();
        return ResponseEntity.ok(badges);
    }
    
    @GetMapping("/usuario")
    public ResponseEntity<List<BadgeResponseDto>> getUserBadges(Authentication authentication) {
        String email = authentication.getName();
        List<BadgeResponseDto> userBadges = badgeService.getUserBadges(email);
        return ResponseEntity.ok(userBadges);
    }
    
    @GetMapping("/disponibles")
    public ResponseEntity<List<BadgeResponseDto>> getAvailableBadges(Authentication authentication) {
        String email = authentication.getName();
        List<BadgeResponseDto> availableBadges = badgeService.getAvailableBadges(email);
        return ResponseEntity.ok(availableBadges);
    }
    
    @PostMapping("/otorgar/{badgeId}")
    public ResponseEntity<BadgeResponseDto> awardBadge(
            @PathVariable Long badgeId,
            Authentication authentication) {
        String email = authentication.getName();
        BadgeResponseDto awardedBadge = badgeService.awardBadge(badgeId, email);
        return ResponseEntity.ok(awardedBadge);
    }
}