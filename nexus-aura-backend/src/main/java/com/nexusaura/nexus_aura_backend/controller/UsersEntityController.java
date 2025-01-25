package com.nexusaura.nexus_aura_backend.controller;

import com.nexusaura.nexus_aura_backend.dto.UsersEntityDTO;
import com.nexusaura.nexus_aura_backend.service.UsersEntityService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usersEntity")
public class UsersEntityController {

    private final UsersEntityService usersEntityService;

    public UsersEntityController(UsersEntityService usersEntityService) {
        this.usersEntityService = usersEntityService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsersEntityDTO> getUserById(@PathVariable Integer id) {
        UsersEntityDTO user = usersEntityService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<UsersEntityDTO> createUser(@RequestBody @Valid UsersEntityDTO usersEntityDTO) {
        UsersEntityDTO createdUser = usersEntityService.createUser(usersEntityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @GetMapping
    public ResponseEntity<List<UsersEntityDTO>> getAllUsers() {
        List<UsersEntityDTO> users = usersEntityService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable Integer id) {
        usersEntityService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<UsersEntityDTO> updateUserRole(@PathVariable Integer id, @RequestBody String newRole) {
        UsersEntityDTO updatedUser = usersEntityService.updateUserRole(id, newRole);
        return ResponseEntity.ok(updatedUser);
    }
}

