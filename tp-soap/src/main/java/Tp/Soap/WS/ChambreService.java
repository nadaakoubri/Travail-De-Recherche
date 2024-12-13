package Tp.Soap.WS;
import Tp.Soap.entites.Chambre;
import Tp.Soap.repositories.ChambreRepository;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@WebService
@Component
public class ChambreService {
    @Autowired
    private ChambreRepository chambreRepository;
    @WebMethod
    public Chambre createChambre(Chambre chambre) {
        return chambreRepository.save(chambre);
    }
    @WebMethod
    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with " ));
    }
    @WebMethod
    public List<Chambre> getAllChambres() {
        return chambreRepository.findAll();
    }
    @WebMethod
    public Chambre updateChambre(Long id, Chambre updatedChambre) {
        Chambre chambre = getChambreById(id);
        chambre.setType(updatedChambre.getType());
        chambre.setPrice(updatedChambre.getPrice());
        chambre.setAvailability(updatedChambre.isAvailability());
        return chambreRepository.save(chambre);
    }
    @WebMethod
    public void deleteChambre(Long id) {
        chambreRepository.deleteById(id);
    }
}