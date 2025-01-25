package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipDTO {
    private Integer friendshipID;
    private String user1;        // Username or ID of user 1
    private String user2;        // Username or ID of user 2
    private String timestamp;
}

