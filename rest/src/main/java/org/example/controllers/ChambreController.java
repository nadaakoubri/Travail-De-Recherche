package org.example.controllers;

import org.example.entities.Chambre;
import org.example.services.ChambreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("chambres")
public class ChambreController {

    @Autowired
    private ChambreService chambreService;

    @PostMapping
    public ResponseEntity<Chambre> saveChambre(@RequestBody Chambre chambre) {
        return new ResponseEntity<>(chambreService.saveRoom(chambre), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chambre> getChambreById(@PathVariable Long id) {
        return ResponseEntity.ok(chambreService.getRoomById(id));
    }

    @GetMapping
    public ResponseEntity<List<Chambre>> getAllChambres() {
        return ResponseEntity.ok(chambreService.getAllRooms());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chambre> updateChambre(@PathVariable Long id, @RequestBody Chambre chambre) {
        return ResponseEntity.ok(chambreService.updateRoom(id, chambre));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id) {
        chambreService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}
