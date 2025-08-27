package com.guillermoinc.training.client;

import com.guillermoinc.training.dto.UsuarioResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "user-service", url = "${user.service.url:http://localhost:8080}")
public interface UserServiceClient {
    
    @GetMapping("/api/users/{userId}")
    UsuarioResponseDto getUserById(@PathVariable("userId") Integer userId, 
                                  @RequestHeader("Authorization") String authorization);
}