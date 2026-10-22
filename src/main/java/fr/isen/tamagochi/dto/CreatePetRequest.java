package fr.isen.tamagochi.dto;

import fr.isen.tamagochi.model.Species;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreatePetRequest(
        @NotBlank @Size(max = 30) String name,
        @NotNull Species species) {
}
