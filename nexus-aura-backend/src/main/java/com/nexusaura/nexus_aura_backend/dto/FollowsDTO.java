package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FollowsDTO {
    private String followingUser; // Username or ID of the following user
    private String followedUser;  // Username or ID of the followed user
    private String createdAt;     // Timestamp of follow action
}
