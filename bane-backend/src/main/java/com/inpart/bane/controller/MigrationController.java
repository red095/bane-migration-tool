package com.inpart.bane.controller;

import com.inpart.bane.dto.ApiResponse;
import com.inpart.bane.model.Client;
import com.inpart.bane.service.ClientService;
import com.inpart.bane.service.MigrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Allow frontend to call the API
public class MigrationController {

    private final ClientService clientService;
    private final MigrationService migrationService;

    public MigrationController(ClientService clientService, MigrationService migrationService) {
        this.clientService = clientService;
        this.migrationService = migrationService;
    }

    @GetMapping("/legacy/clients")
    public ResponseEntity<ApiResponse<List<Client>>> getLegacyClients() {
        try {
            List<Client> legacyClients = clientService.getAllLegacyClients();
            return ResponseEntity.ok(ApiResponse.success("Legacy clients retrieved successfully", legacyClients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving legacy clients: " + e.getMessage()));
        }
    }

    @GetMapping("/new/clients")
    public ResponseEntity<ApiResponse<List<Client>>> getNewClients() {
        try {
            List<Client> newClients = clientService.getAllNewClients();
            return ResponseEntity.ok(ApiResponse.success("Migrated clients retrieved successfully", newClients));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error retrieving migrated clients: " + e.getMessage()));
        }
    }

    @PostMapping("/migrate/{id}")
    public ResponseEntity<ApiResponse<Client>> migrateClient(@PathVariable Long id) {
        try {
            Optional<Client> migratedClient = migrationService.migrateClient(id);

            if (migratedClient.isPresent()) {
                return ResponseEntity.ok(ApiResponse.success(
                        "Client migrated successfully",
                        migratedClient.get()
                ));
            } else {
                // Determine the specific error
                if (!clientService.legacyClientExists(id)) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ApiResponse.error("Client not found in legacy system"));
                } else if (clientService.isClientMigrated(id)) {
                    return ResponseEntity.status(HttpStatus.CONFLICT)
                            .body(ApiResponse.error("Client is already migrated"));
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(ApiResponse.error("Migration failed for unknown reason"));
                }
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Error during migration: " + e.getMessage()));
        }
    }
}