package fr.isen.tamagochi.dto;

import java.time.Instant;

import fr.isen.tamagochi.model.Pet;
import fr.isen.tamagochi.model.Species;

public record PetResponse(
        Long id,
        String name,
        Species species,
        int fullness,
        int happiness,
        int energy,
        boolean alive,
        Instant createdAt,
        Instant diedAt) {

    public static PetResponse from(Pet pet) {
        return new PetResponse(pet.getId(), pet.getName(), pet.getSpecies(),
                pet.getFullness(), pet.getHappiness(), pet.getEnergy(),
                pet.isAlive(), pet.getCreatedAt(), pet.getDiedAt());
    }
}
