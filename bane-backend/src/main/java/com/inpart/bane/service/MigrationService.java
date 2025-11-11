package com.inpart.bane.service;

import com.inpart.bane.model.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MigrationService {

    private static final Logger logger = LoggerFactory.getLogger(MigrationService.class);

    private final ClientService clientService;

    public MigrationService(ClientService clientService) {
        this.clientService = clientService;
    }

    /**
     * Migrates a client from legacy system to new system
     * @param clientId the ID of the client to migrate
     * @return the migrated client if successful, empty if migration failed
     */
    public Optional<Client> migrateClient(Long clientId) {
        logger.info("Attempting to migrate client with ID: {}", clientId);

        // Check if client exists in legacy system
        Optional<Client> legacyClientOpt = clientService.getLegacyClientById(clientId);
        if (legacyClientOpt.isEmpty()) {
            logger.warn("Migration failed: Client with ID {} not found in legacy system", clientId);
            return Optional.empty();
        }

        Client legacyClient = legacyClientOpt.get();

        // Check if client is already migrated
        if (clientService.isClientMigrated(clientId)) {
            logger.warn("Migration failed: Client with ID {} is already migrated", clientId);
            return Optional.empty();
        }

        // Perform migration
        try {
            // Create a copy for the new system (in real scenario, this would involve data transformation)
            Client migratedClient = createMigratedClient(legacyClient);

            // Add to new system
            clientService.saveNewClient(migratedClient);

            // Mark as migrated in legacy system (but keep in legacy list as per requirements)
            legacyClient.setMigrated(true);
            clientService.saveLegacyClient(legacyClient);

            logger.info("Successfully migrated client: {} (ID: {})", migratedClient.getName(), migratedClient.getId());

            return Optional.of(migratedClient);

        } catch (Exception e) {
            logger.error("Error during migration of client ID {}: {}", clientId, e.getMessage());
            return Optional.empty();
        }
    }

    private Client createMigratedClient(Client legacyClient) {
        Client migratedClient = new Client();
        migratedClient.setId(legacyClient.getId());
        migratedClient.setName(legacyClient.getName());
        migratedClient.setEmail(legacyClient.getEmail());
        migratedClient.setCompany(legacyClient.getCompany());
        migratedClient.setMigrated(true);
        return migratedClient;
    }
}