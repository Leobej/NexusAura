package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsersEntityDTO {
    private Integer id;
    private String username;
    private String role;
    private String createdAt;
}

