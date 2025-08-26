package com.guillermoinc.training.service.impl;

import com.guillermoinc.training.dto.BadgeResponseDto;
import com.guillermoinc.training.entity.*;
import com.guillermoinc.training.repository.BadgeRepository;
import com.guillermoinc.training.repository.UsuarioBadgeRepository;
import com.guillermoinc.training.repository.UsuarioRepository;
import com.guillermoinc.training.service.BadgeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BadgeServiceImpl implements BadgeService {
    
    private final BadgeRepository badgeRepository;
    private final UsuarioBadgeRepository usuarioBadgeRepository;
    private final UsuarioRepository usuarioRepository;
    
    @Override
    public List<BadgeResponseDto> getAllBadges() {
        return badgeRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BadgeResponseDto> getUserBadges(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        return usuarioBadgeRepository.findByIdUsuario(usuario.getIdUsuario()).stream()
                .map(usuarioBadge -> {
                    BadgeResponseDto dto = convertToDto(usuarioBadge.getBadge());
                    dto.setEarnedDate(usuarioBadge.getFechaObtencion());
                    return dto;
                })
                .collect(Collectors.toList());
    }
    
    @Override
    public List<BadgeResponseDto> getAvailableBadges(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        List<Long> earnedBadgeIds = usuarioBadgeRepository.findByIdUsuario(usuario.getIdUsuario())
                .stream()
                .map(UsuarioBadge::getIdBadge)
                .collect(Collectors.toList());
        
        return badgeRepository.findAll().stream()
                .filter(badge -> !earnedBadgeIds.contains(badge.getIdBadge()))
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }
    
    @Override
    public BadgeResponseDto awardBadge(Long badgeId, String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        Badge badge = badgeRepository.findById(badgeId)
                .orElseThrow(() -> new RuntimeException("Badge no encontrado"));
        
        // Verificar si ya tiene el badge
        if (usuarioBadgeRepository.existsByIdUsuarioAndIdBadge(usuario.getIdUsuario(), badgeId)) {
            throw new RuntimeException("El usuario ya tiene este badge");
        }
        
        // Crear relación usuario-badge
        UsuarioBadge usuarioBadge = UsuarioBadge.builder()
                .idUsuario(usuario.getIdUsuario())
                .idBadge(badgeId)
                .fechaObtencion(LocalDateTime.now())
                .build();
        
        usuarioBadgeRepository.save(usuarioBadge);
        
        BadgeResponseDto dto = convertToDto(badge);
        dto.setEarnedDate(usuarioBadge.getFechaObtencion());
        return dto;
    }
    
    @Override
    public void checkAndAwardBadges(String email, Long courseId) {
        // Lógica para verificar si el usuario merece badges automáticamente
        // Por ejemplo, al completar un curso
        // Esto se puede expandir con diferentes criterios
    }
    
    private BadgeResponseDto convertToDto(Badge badge) {
        return BadgeResponseDto.builder()
                .id(badge.getIdBadge())
                .name(badge.getName())
                .description(badge.getDescription())
                .icon(badge.getIcon())
                .color(badge.getColor())
                .category(badge.getCategory())
                .rarity(badge.getRarity())
                .points(badge.getPoints())
                .courseId(badge.getCourseId())
                .requirements(badge.getRequirements())
                .build();
    }
}