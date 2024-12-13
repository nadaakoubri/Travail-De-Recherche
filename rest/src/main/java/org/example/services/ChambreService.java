package org.example.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.entities.Chambre;
import org.example.repositories.ChambreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChambreService {
    @Autowired
    private ChambreRepository chambreRepository;

    public Chambre saveRoom(Chambre chambre) {
        return chambreRepository.save(chambre);
    }

    public Chambre getRoomById(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("room not found : " ));
    }

    public List<Chambre> getAllRooms() {
        return chambreRepository.findAll();
    }

    public Chambre updateRoom(Long id, Chambre updatedChambre) {
        Chambre chambre = getRoomById(id);
        chambre.setType(updatedChambre.getType());
        chambre.setPrice(updatedChambre.getPrice());
        chambre.setAvailability(updatedChambre.isAvailability());
        return chambreRepository.save(chambre);
    }

    public void deleteRoom(Long id) {
        chambreRepository.deleteById(id);
    }
}
