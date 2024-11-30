/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.service;

import jakarta.transaction.SystemException;
import jakarta.ws.rs.PathParam;
import java.util.List;
import java.util.Set;
import org.emiage.room.model.dto.DTOAvailable;
import org.emiage.room.model.dto.DTOAvailableDate;
import org.emiage.room.model.entity.Resa;

/**
 *
 * @author linda
 */
public interface ResaService {
    /**
     * Sauvegarde de la réservation
     * @param resa reservation
     * @param userName
     * @return Retourne Vrai si la sauvegarde s'est effectué
     * @throws org.emiage.room.model.service.RoomException
     * @throws jakarta.transaction.SystemException
     */
    public Resa saveResa(Resa resa, String userName) throws RoomException,SystemException;
    
    /**
     * 
     * @param idresa identifiant de la réservation
     * @return 
     */
    public boolean deleteResa(Long idresa);
    
    /**
     * 
     * @param nameUser
     * @return 
     */
    public List<Resa> getAllMyResa(String nameUser);
    
    public Resa findResaById(Long id);
 
    public List<Resa> findAll();
    
    public DTOAvailable searchAvailable(Long idroom, Set<DTOAvailableDate> lstdate );
    
    public DTOAvailable searchAvailable(DTOAvailable dto );
    
    public List<Resa> findbyRoomId(Long idroom);
    
    public boolean deletefromRoom(Long idroom);
    
    
}
