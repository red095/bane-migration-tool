package com.inpart.bane.model;

import java.util.Objects;

public class Client {

    private Long id;
    private String name;
    private String email;
    private String company;
    private boolean migrated;

    public Client() {}
    public Client(Long id, String name, String email, String company) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.company = company;
        this.migrated = false;
    }

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}
    public String getCompany() {return company;}
    public void setCompany(String company) {this.company = company;}
    public boolean isMigrated() { return migrated; }
    public void setMigrated(boolean migrated) { this.migrated = migrated; }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", company='" + company + '\'' +
                ", migrated=" + migrated +
                '}';
    }
}
