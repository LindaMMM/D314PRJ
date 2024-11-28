/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import org.emiage.room.security.entity.User;

/**
 *
 * @author linda
 */
@Entity
@Table(name = "RESERVATION")
@NamedQueries({
        @NamedQuery(name = "Resa.findAll", query = "SELECT p FROM Resa p")
})
public class Resa extends Audit {
    private static final long serialVersionUID = -825634229676522580L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_resa;
    
    @NotNull
    @Column(name = "subject")
    protected String name;
       
    @Column(name = "STR_RESA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateStart;
    
    @Column(name = "END_RESA")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateEnd;
    
  /*  @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY ) 
    @JoinColumn( name = "user_id", nullable = false )
    private User user;
    
    @ManyToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    @JoinColumn( name = "room_id", nullable = false )
    private Room room;*/
    
    @Column(name = "user_id")
    private long id_user;
    public Long getIdUser() {
        return id_user;
    }

    public void setIdUser(Long id) {
        this.id_user = id;
    }
    
    @Column(name = "room_id")
    private Long id_room;
    public Long getIdRoom() {
        return id_room;
    }

    public void setIdRoom(Long id) {
        this.id_room = id;
    }
    
    /*Geter/setter*/
     public Long getId() {
        return id_resa;
    }

    public void setId(Long id) {
        this.id_resa = id;
    }
   
    public String getSubject() {
        return name;
    }

    public void setSubject(String name) {
        this.name = name;
    }
    
    /*public Room getRoom() {
        return room;
    }

    public void setRome(Room room) {
        this.room = room;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
   */
    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date date) {
        this.dateStart = date;
    }
    
    public Date getDateEnd()
    {
        return dateEnd;
    }
    
    public void setDateEnd(Date valend)
    {
        this.dateEnd = valend;
    }
    
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (id_resa != null ? id_resa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        
        if (object == this)
            return true;
        
        if (!(object instanceof Resa)) {
            return false;
        }
        
        Resa other = (Resa) object;
        if ((this.id_resa == null && other.id_resa != null) 
                || (this.id_resa != null && !this.id_resa.equals(other.id_resa))
                || (this.id_room != null && !this.id_room.equals(other.id_room))
                ) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        
        return "Resa [id=" + id_resa + "] [subject=" + name + "]  [start=" + dateStart + "]  [end=" + dateEnd + "]" + super.toString();
    }

    
    
}
