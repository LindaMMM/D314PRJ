/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.security.entity;

import jakarta.persistence.CascadeType;
import java.util.List;

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
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLE_APP")
@NamedQueries({
        @NamedQuery(name = "Role.findbyName", query = "SELECT r FROM Role r where r.name = :value")
})
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id_role;
    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "USER_ROLE", joinColumns
            = @JoinColumn(name = "id_user"), inverseJoinColumns
            = @JoinColumn(name = "id_role"))
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    public Role() {
    }

    public Role(long role_id, String name) {
        this.id_role = role_id;
        this.name = name;

    }

    public long getRole_id() {
        return id_role;
    }

    public void setRole_id(long role_id) {
        this.id_role = role_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
