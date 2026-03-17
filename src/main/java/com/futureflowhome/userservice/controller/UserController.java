package com.futureflowhome.userservice.controller;

import com.futureflowhome.userservice.dto.UserCreateRequest;
import com.futureflowhome.userservice.dto.UserDto;
import com.futureflowhome.userservice.security.AuthenticatedUser;
import com.futureflowhome.userservice.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Get the currently authenticated user's profile.
     * GET /api/v1/users/me
     */
    @GetMapping(path = "/me", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> me(@AuthenticationPrincipal AuthenticatedUser principal) {
        UserDto user = userService.getById(principal.getUserId());
        return ResponseEntity.ok(user);
    }

    /**
     * Register a new user.
     * POST /api/v1/users
     * Returns 201 Created with Location header and user body; 400 on validation error; 409 if username or email already exists.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> register(@Valid @RequestBody UserCreateRequest request) {
        UserDto created = userService.register(request);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(created.getId())
                .toUri();
        return ResponseEntity.created(location).body(created);
    }
}
