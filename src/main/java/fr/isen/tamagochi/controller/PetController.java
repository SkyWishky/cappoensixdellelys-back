package fr.isen.tamagochi.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import fr.isen.tamagochi.dto.CreatePetRequest;
import fr.isen.tamagochi.dto.PetResponse;
import fr.isen.tamagochi.service.PetService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService service;

    public PetController(PetService service) {
        this.service = service;
    }

    @GetMapping
    public List<PetResponse> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public PetResponse findById(@PathVariable long id) {
        return service.findById(id);
    }

    @PostMapping
    public ResponseEntity<PetResponse> adopt(@Valid @RequestBody CreatePetRequest request) {
        PetResponse pet = service.adopt(request);
        return ResponseEntity.created(URI.create("/api/pets/" + pet.id())).body(pet);
    }

    @PostMapping("/{id}/feed")
    public PetResponse feed(@PathVariable long id) {
        return service.feed(id);
    }

    @PostMapping("/{id}/play")
    public PetResponse play(@PathVariable long id) {
        return service.play(id);
    }

    @PostMapping("/{id}/sleep")
    public PetResponse sleep(@PathVariable long id) {
        return service.sleep(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
