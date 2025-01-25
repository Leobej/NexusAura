package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentDTO {
    private Integer commentID;
    private Integer postID;     // ID of the post being commented on
    private String user;        // Username or user summary
    private String content;
    private String timestamp;
}

