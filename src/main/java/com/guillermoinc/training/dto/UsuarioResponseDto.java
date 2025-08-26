package com.guillermoinc.training.dto;

import com.guillermoinc.training.entity.Role;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDto {
    
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private String department;
    private String profilePicture;
    private LocalDateTime joinDate;
    private LocalDateTime lastActive;
    private List<String> enrolledCourses;
    private List<String> completedCourses;
    private List<String> badges;
}