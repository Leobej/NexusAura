package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntityDTO {
    private Integer userID;
    private String username;
    private String email;
    private String name;
    private String bio;
    private String password;
    private String profilePicture;
}

