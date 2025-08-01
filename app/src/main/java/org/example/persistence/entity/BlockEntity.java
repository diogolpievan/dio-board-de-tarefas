package org.example.persistence.entity;

import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class BlockEntity {
    private long id;
    private OffsetDateTime blockedAt;
    private String blockReason;
    private OffsetDateTime unblockedAt;
    private String unblockReason;
}
