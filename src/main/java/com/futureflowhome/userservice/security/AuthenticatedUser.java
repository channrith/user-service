package com.futureflowhome.userservice.security;

import java.util.Objects;

/**
 * Principal held in the security context after JWT validation.
 * Uses user UUID (not internal id) for cross-service identity (e.g. todo-service task ownership).
 */
public class AuthenticatedUser {

    private final String userUuid;
    private final String username;

    public AuthenticatedUser(String userUuid, String username) {
        this.userUuid = userUuid;
        this.username = username;
    }

    public String getUserId() {
        return userUuid;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthenticatedUser that = (AuthenticatedUser) o;
        return Objects.equals(userUuid, that.userUuid) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userUuid, username);
    }
}
