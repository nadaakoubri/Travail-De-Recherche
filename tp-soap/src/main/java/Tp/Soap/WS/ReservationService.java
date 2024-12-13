package Tp.Soap.WS;


import Tp.Soap.entites.Chambre;
import Tp.Soap.entites.Reservation;
import Tp.Soap.repositories.ReservationRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.List;
@WebService
@Component
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private ChambreService chambreService;
    @WebMethod
    public Reservation createReservation(Reservation reservation) {
        if (reservation.getChambre() == null) {
            throw new IllegalArgumentException("Room cannot be null");
        }
        Chambre chambre = chambreService.getChambreById(reservation.getChambre().getId());
        if (!chambre.isAvailability()) {
            throw new IllegalStateException("Room is not available");
        }
        chambre.setAvailability(false);
        chambreService.updateChambre(chambre.getId(), chambre);
        return reservationRepository.save(reservation);
    }
    @WebMethod
    public Reservation getReservationById(Long id) {
        return reservationRepository.findById(Math.toIntExact(id))
                .orElseThrow(() -> new EntityNotFoundException("Reservation not found  " ));
    }
    @WebMethod
    public List<Reservation> getAllReservations() {
        return (List<Reservation>) reservationRepository.findAll();
    }
    @WebMethod
    public Reservation updateReservation(Long id, Reservation updatedReservation) {
        Reservation reservation = getReservationById(id);
        if (!reservation.getChambre().getId().equals(updatedReservation.getChambre().getId())) {
            Chambre oldChambre = chambreService.getChambreById(reservation.getChambre().getId());
            oldChambre.setAvailability(true);
            chambreService.updateChambre(oldChambre.getId(), oldChambre);
            Chambre newChambre = chambreService.getChambreById(updatedReservation.getChambre().getId());
            if (!newChambre.isAvailability()) {
                throw new IllegalStateException("New Room is not available");
            }
            newChambre.setAvailability(false);
            chambreService.updateChambre(newChambre.getId(), newChambre);
            reservation.setChambre(newChambre);
        }
        reservation.setClient(updatedReservation.getClient());
        reservation.setDateDebut(updatedReservation.getDateDebut());
        reservation.setDateFin(updatedReservation.getDateFin());
        reservation.setPreferences(updatedReservation.getPreferences());
        return reservationRepository.save(reservation);
    }
    @WebMethod
    public void deleteReservation(Long id) {
        Reservation reservation = getReservationById(id);
        Chambre chambre = chambreService.getChambreById(reservation.getChambre().getId());
        chambre.setAvailability(true);
        chambreService.updateChambre(chambre.getId(), chambre);
        reservationRepository.deleteById(Math.toIntExact(id));
    }
}
