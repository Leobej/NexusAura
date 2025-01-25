package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostDTO {
    private Integer postID;
    private String user;        // Username or user summary
    private String content;
    private String mediaType;
    private String mediaURL;
    private String timestamp;
}

