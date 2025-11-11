package com.inpart.bane.service;

import com.inpart.bane.model.Client;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ClientService {

    // Using ConcurrentHashMap for thread-safe operations
    private final ConcurrentHashMap<Long, Client> legacyClients = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Long, Client> newClients = new ConcurrentHashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);

    // Initialize with sample data
    @PostConstruct
    public void init() {
        createSampleClients();
    }

    private void createSampleClients() {
        saveLegacyClient(new Client(idCounter.getAndIncrement(), "John Doe", "john@example.com", "Acme Corp"));
        saveLegacyClient(new Client(idCounter.getAndIncrement(), "Jane Smith", "jane@example.com", "Globex Inc"));
        saveLegacyClient(new Client(idCounter.getAndIncrement(), "Bob Johnson", "bob@example.com", "Stark Industries"));
        saveLegacyClient(new Client(idCounter.getAndIncrement(), "Alice Brown", "alice@example.com", "Wayne Enterprises"));
        saveLegacyClient(new Client(idCounter.getAndIncrement(), "Charlie Wilson", "charlie@example.com", "Umbrella Corp"));
    }

    // Legacy Clients operations
    public List<Client> getAllLegacyClients() {
        return new ArrayList<>(legacyClients.values());
    }

    public Optional<Client> getLegacyClientById(Long id) {
        return Optional.ofNullable(legacyClients.get(id));
    }

    public void saveLegacyClient(Client client) {
        legacyClients.put(client.getId(), client);
    }

    public void removeLegacyClient(Long id) {
        legacyClients.remove(id);
    }

    // New Clients operations
    public List<Client> getAllNewClients() {
        return new ArrayList<>(newClients.values());
    }

    public Optional<Client> getNewClientById(Long id) {
        return Optional.ofNullable(newClients.get(id));
    }

    public void saveNewClient(Client client) {
        newClients.put(client.getId(), client);
    }

    public boolean isClientMigrated(Long id) {
        return newClients.containsKey(id);
    }

    // Utility methods
    public boolean legacyClientExists(Long id) {
        return legacyClients.containsKey(id);
    }
}