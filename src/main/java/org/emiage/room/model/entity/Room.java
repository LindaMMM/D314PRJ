/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author linda
 */
@Entity
@Table(name = "ROOM")
@NamedQueries({
        @NamedQuery(name = "Room.findAll", query = "SELECT p FROM Room p")
})
public class Room extends Audit {
    private static final long serialVersionUID = -825634229676522580L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    protected String name;
    
    @NotNull
    protected int capacity;
    
    protected String comment;
    
    @OneToMany( 
            targetEntity = Resa.class, 
            mappedBy = "room", 
            cascade = { CascadeType.PERSIST, CascadeType.REMOVE }
    )
    private Set<Resa> resas;
    
    /*-----------Get/Set---------*/
     public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
   /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the value of name.
     *
     * @param Name new value of room
     */
    public void setName(String name) {
        this.name = name;
    }
    
    public void setResas(Set<Resa> resas)
    {
        this.resas = resas;
    }
    
    public Set<Resa> getResas()
    {
        return resas;
    }
    
   /**
     * Get the value of Name
     *
     * @return the value of Name
     */
    public String getComment() {
        return comment;
    }

    /**
     * Set the value of comment.
     *
     * @param Name new value of comment
     */
    public void setComment(String name) {
        this.comment = name;
    }
    
    public int getCapacity()
    {
        return capacity;
    }
    
    public void setCapacity(int valCapacity)
    {
        this.capacity= valCapacity;
    }
    /**
     * Construteur de l'entité Room.
     */
    public Room(){
        this.resas = new HashSet<>();
    }
    
    /**
     * Construteur de l'entité Room.
     * @param name nom de la salle
     */
    public Room(String name)
    {
        this.resas = new HashSet<>();
        this.name = name;
    }
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Room)) {
            return false;
        }
        Room other = (Room) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        return "Room [id=" + id + "] [name=" + name + "]  [capacity=" + capacity + "]  [comment=" + comment + "]" + super.toString();
    }
}
