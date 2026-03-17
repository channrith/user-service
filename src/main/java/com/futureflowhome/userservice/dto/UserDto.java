package com.futureflowhome.userservice.dto;

import java.time.Instant;

public class UserDto {

    private Long id;
    private String username;
    private String email;
    private boolean emailVerified;
    private boolean accountNonLocked;
    private boolean enabled;
    private Instant lastLoginAt;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    public UserDto() {
    }

    public UserDto(
            Long id,
            String username,
            String email,
            boolean emailVerified,
            boolean accountNonLocked,
            boolean enabled,
            Instant lastLoginAt,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.emailVerified = emailVerified;
        this.accountNonLocked = accountNonLocked;
        this.enabled = enabled;
        this.lastLoginAt = lastLoginAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public boolean isEmailVerified() { return emailVerified; }
    public boolean isAccountNonLocked() { return accountNonLocked; }
    public boolean isEnabled() { return enabled; }
    public Instant getLastLoginAt() { return lastLoginAt; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public Instant getDeletedAt() { return deletedAt; }
}
