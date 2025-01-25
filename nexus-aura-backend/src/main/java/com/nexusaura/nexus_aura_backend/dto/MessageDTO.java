package com.nexusaura.nexus_aura_backend.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDTO {
    private Integer messageID;
    private String sender;      // Username of the sender
    private String receiver;    // Username of the receiver
    private String content;
    private String timestamp;
}

