/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.security.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.emiage.room.model.entity.Resa;

/**
 *
 * @author linda
 */
@Entity
@Table(name = "USER_APP")
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
})
public class User {

    /**
     * Numéro de sérialisation
     */
    private static final long serialVersionUID = 1L;

    /**
     * Identifiant du user
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;

    /**
     * login
     */
    @Column(unique = true, length = 60, name="LOGIN")
    private String username;

    /**
     * Mot de passe
     */
    @Column
    private String password;

    @OneToMany(
            targetEntity = org.emiage.room.model.entity.Resa.class,
            mappedBy = "user",
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private Set<Resa> resas;

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;

    // Constructeur
    public User() {
        this.roles = new HashSet<>();
        this.resas = new HashSet<>();
    }

    public User(String name, String password) {
        this.resas = new HashSet<>();
        this.roles = new HashSet<>();
        this.username = name;
        this.password = password;

    }

    /**
     * Getter de l'identifiant de l'user
     *
     * @return r
     */
    public Long getId() {
        return id_user;
    }

    public void setId(Long Id) {
        this.id_user = Id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Role> getRoles() {
        return roles;
    }
    
    public void setResas(Set<Resa> resas) {
        this.resas = resas;
    }

    public Set<Resa> getResas() {
        return resas;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_user != null ? id_user.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        return !((this.id_user == null && other.id_user != null) || (this.id_user != null && !this.id_user.equals(other.id_user)));
    }

    @Override
    public String toString() {
        return "User[id=" + id_user + "] "
                + "[first=" + username + "] "
                + "[password=" + password + "]";
    }

}
