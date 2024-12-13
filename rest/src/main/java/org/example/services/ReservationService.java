package org.example.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.entities.Reservation;
import org.example.entities.Chambre;
import org.example.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ChambreService chambreService;
    public Reservation saveReservation(Reservation reservation) {
        if (reservation.getChambre() == null) {
            throw new IllegalArgumentException("room cannot be null");
        }
        Chambre chambre = chambreService.getRoomById(reservation.getChambre().getId());
        if (!chambre.isAvailability()) {
            throw new IllegalStateException("room is not available");
        }
        chambre.setAvailability(false);
        chambreService.updateRoom(chambre.getId(), chambre);
        return reservationRepository.save(reservation);
    }
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found : " ));
    }
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Reservation reservation = getReservationById(id);
        if (!reservation.getChambre().getId().equals(updatedReservation.getChambre().getId())) {
            Chambre oldChambre = chambreService.getRoomById(reservation.getChambre().getId());
            oldChambre.setAvailability(true);
            chambreService.updateRoom(oldChambre.getId(), oldChambre);
            Chambre newChambre = chambreService.getRoomById(updatedReservation.getChambre().getId());
            if (!newChambre.isAvailability()) {
                throw new IllegalStateException(" not available");
            }
            newChambre.setAvailability(false);
            chambreService.updateRoom(newChambre.getId(), newChambre);
            reservation.setChambre(newChambre);
        }
        reservation.setClient(updatedReservation.getClient());
        reservation.setStartdate(updatedReservation.getStartdate());
        reservation.setEnddate(updatedReservation.getEnddate());
        reservation.setPreferences(updatedReservation.getPreferences());
        return reservationRepository.save(reservation);
    }
    public void deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        Chambre chambre = chambreService.getRoomById(reservation.getChambre().getId());
        chambre.setAvailability(true);
        chambreService.updateRoom(chambre.getId(), chambre);
        reservationRepository.deleteById(id);
    }
}
