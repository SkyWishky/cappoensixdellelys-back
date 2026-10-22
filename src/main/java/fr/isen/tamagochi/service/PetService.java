package fr.isen.tamagochi.service;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.function.BiConsumer;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fr.isen.tamagochi.config.PetProperties;
import fr.isen.tamagochi.dto.CreatePetRequest;
import fr.isen.tamagochi.dto.PetResponse;
import fr.isen.tamagochi.model.Pet;
import fr.isen.tamagochi.repository.PetRepository;

@Service
@Transactional
public class PetService {

    private final PetRepository repository;
    private final PetProperties properties;
    private final Clock clock;

    public PetService(PetRepository repository, PetProperties properties, Clock clock) {
        this.repository = repository;
        this.properties = properties;
        this.clock = clock;
    }

    public List<PetResponse> findAll() {
        return repository.findAllByOrderByAliveDescIdAsc().stream()
                .map(this::applyDecay)
                .map(PetResponse::from)
                .toList();
    }

    public PetResponse findById(long id) {
        return PetResponse.from(load(id));
    }

    public PetResponse adopt(CreatePetRequest request) {
        Pet pet = new Pet(request.name().trim(), request.species(), clock.instant());
        return PetResponse.from(repository.save(pet));
    }

    public PetResponse feed(long id) {
        return act(id, Pet::feed);
    }

    public PetResponse play(long id) {
        return act(id, Pet::play);
    }

    public PetResponse sleep(long id) {
        return act(id, Pet::sleep);
    }

    public void delete(long id) {
        repository.delete(load(id));
    }

    private PetResponse act(long id, BiConsumer<Pet, Instant> action) {
        Pet pet = load(id);
        if (!pet.isAlive()) {
            throw new PetIsDeadException(id);
        }
        action.accept(pet, clock.instant());
        return PetResponse.from(pet);
    }

    private Pet load(long id) {
        return applyDecay(repository.findById(id).orElseThrow(() -> new PetNotFoundException(id)));
    }

    private Pet applyDecay(Pet pet) {
        Duration interval = properties.decayInterval();
        Duration elapsed = Duration.between(pet.getLastDecayAt(), clock.instant());
        long points = elapsed.dividedBy(interval);
        if (points > 0) {
            int lost = (int) Math.min(points, Integer.MAX_VALUE);
            pet.decay(lost, pet.getLastDecayAt().plus(interval.multipliedBy(points)));
        }
        return pet;
    }
}
