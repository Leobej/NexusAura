package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LikesDTO {
    private Integer likeID;
    private Integer postID;     // ID of the liked post
    private Integer commentID;  // ID of the liked comment (optional)
    private String user;        // Username of the user who liked
    private String timestamp;
}

