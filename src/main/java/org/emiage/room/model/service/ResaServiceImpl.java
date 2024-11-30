/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.emiage.room.model.service;

import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.emiage.room.model.dto.DTOAvailable;
import org.emiage.room.model.dto.DTOAvailableDate;
import org.emiage.room.model.dto.DTOReturn;
import org.emiage.room.model.entity.Resa;
import org.emiage.room.model.repository.ResaRepository;
import org.emiage.room.model.repository.RoomRepository;
import org.emiage.room.security.entity.User;
import org.emiage.room.security.repository.UserRepository;

/**
 *
 * @author linda
 */
public class ResaServiceImpl implements ResaService{

    private final Logger logger = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
    
    @Inject
    private ResaRepository resaRepository;
    
    @Inject
    private UserRepository userRepository;
    
    @Inject
    private RoomRepository roomRepository;
    
    @Override
    public Resa saveResa(Resa resa, String userName) throws RoomException, SystemException {
        
        // Recherche du user
        User user = userRepository.findByLoging(userName);
        
        // mise à jour des données de la réservation
        resa.setUserUpdate(userName);
        resa.setIdUser(user.getId());
        
        // test de l'existant de la salle de réunion
        var room = roomRepository.findById(resa.getIdRoom());
        if (!room.isPresent())
        {
            throw new RoomException("La salle de réunion n'existe pas");   
        }
        
        // Vérification des dates
        Date today;
        today = new Date();
        
        if (today.after(resa.getDateStart()))
            throw new RoomException("La date de démmarrage est antérieur à aujoud'hui");
        
        if (resa.getDateEnd().before(resa.getDateStart()))   
            throw new RoomException("Les dates ne sont pas conforme");
        
        // Vérification de la disponibilité
        if (!resaRepository.availableRoom(resa))
            throw new RoomException("la salle n'est pas disponible");
        
        if(resa.getId()== null){
            // nouvelle réservation
            resa.setUserCreate(userName);
            resa = resaRepository.create(resa);
        }
        else{
            // mise à jour de la réservation
            resa = resaRepository.update(resa);
        }        
        
        return resa;
        
    }

    @Override
    public boolean deleteResa(Long idresa) {
        return resaRepository.delete(idresa);        
    }
    
     @Override
    public boolean deletefromRoom(Long id){
        return resaRepository.deletebyroom(id);        
    }
    
    
    @Override
    public List<Resa> getAllMyResa(String nameUser){ 
        return resaRepository.findbyuser(nameUser);
    }
    
    @Override
    public Resa findResaById(Long id) {
        return resaRepository.findById(id).orElseThrow(() -> new WebApplicationException(Response.Status.NOT_FOUND));
   }
    
    @Override
    public List<Resa> findAll() {
        return resaRepository.findAll();
    }
    
    @Override
    public DTOAvailable searchAvailable(Long idroom, Set<DTOAvailableDate> lstdate ){
        logger.log(Level.SEVERE, "search nb {0}".formatted(lstdate.size()));
        DTOAvailable result = new DTOAvailable();
        result.setIdroom(idroom);
        for (DTOAvailableDate i : lstdate) {
            logger.log(Level.SEVERE, i.getDateStart().toString());
            i.setAvailable(resaRepository.availableRoom(idroom, i.getDateStart(), i.getDateEnd()));
            result.getLstDate().add(i);
        }
        
        return result;
    }
    
    @Override
    public DTOAvailable searchAvailable(DTOAvailable dto ){
        return this.searchAvailable(dto.getIdroom(), dto.getLstDate());
    }
    
    @Override
    public List<Resa> findbyRoomId(Long idroom){
        Date today;
        today = new Date();
        return resaRepository.findbyRoomId(idroom, today);
    }
    
}
