package com.nexusaura.nexus_aura_backend.controller;

import com.nexusaura.nexus_aura_backend.dto.UserEntityDTO;
import com.nexusaura.nexus_aura_backend.service.UserEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserEntityController {

    private final UserEntityService userEntityService;

    public UserEntityController(UserEntityService userEntityService) {
        this.userEntityService = userEntityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserEntityDTO> getUserById(@PathVariable Integer id) {
        UserEntityDTO user = userEntityService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UserEntityDTO> createUser(@RequestBody @Valid UserEntityDTO userEntityDTO) {
        UserEntityDTO createdUser = userEntityService.createUser(userEntityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UserEntityDTO>> getAllUsers() {
        List<UserEntityDTO> users = userEntityService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        userEntityService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}

