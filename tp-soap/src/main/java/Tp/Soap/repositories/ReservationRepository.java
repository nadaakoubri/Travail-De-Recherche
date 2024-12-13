package Tp.Soap.repositories;

import Tp.Soap.entites.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface ReservationRepository extends CrudRepository<Reservation, Integer> {
}
