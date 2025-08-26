package com.guillermoinc.training.dto;

import com.guillermoinc.training.entity.BadgeCategory;
import com.guillermoinc.training.entity.BadgeRarity;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BadgeResponseDto {
    
    private Long id;
    private String name;
    private String description;
    private String icon;
    private String color;
    private BadgeCategory category;
    private BadgeRarity rarity;
    private Integer points;
    private Integer courseId;
    private String requirements;
    private LocalDateTime earnedDate;
}