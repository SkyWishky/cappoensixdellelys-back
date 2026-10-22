package fr.isen.tamagochi.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;

import org.junit.jupiter.api.Test;

class PetTest {

    private final Instant now = Instant.parse("2026-01-01T12:00:00Z");

    @Test
    void newPetStartsAliveWithInitialStats() {
        Pet pet = new Pet("Segfault", Species.CAT, now);

        assertThat(pet.isAlive()).isTrue();
        assertThat(pet.getFullness()).isEqualTo(Pet.INITIAL_STAT);
        assertThat(pet.getHappiness()).isEqualTo(Pet.INITIAL_STAT);
        assertThat(pet.getEnergy()).isEqualTo(Pet.INITIAL_STAT);
    }

    @Test
    void decayLowersEveryStatAndMovesCheckpoint() {
        Pet pet = new Pet("Segfault", Species.CAT, now);
        Instant later = now.plusSeconds(600);

        pet.decay(10, later);

        assertThat(pet.getFullness()).isEqualTo(70);
        assertThat(pet.getHappiness()).isEqualTo(70);
        assertThat(pet.getEnergy()).isEqualTo(70);
        assertThat(pet.getLastDecayAt()).isEqualTo(later);
    }

    @Test
    void statsNeverExceedMaximum() {
        Pet pet = new Pet("Segfault", Species.CAT, now);

        pet.feed(now);

        assertThat(pet.getFullness()).isEqualTo(Pet.MAX_STAT);
    }

    @Test
    void petDiesWhenAStatReachesZero() {
        Pet pet = new Pet("Friday Deploy", Species.DUCK, now);
        Instant death = now.plusSeconds(3600);

        pet.decay(500, death);

        assertThat(pet.isAlive()).isFalse();
        assertThat(pet.getDiedAt()).isEqualTo(death);
        assertThat(pet.getFullness()).isZero();
    }

    @Test
    void playingTooMuchCanBeFatal() {
        Pet pet = new Pet("Kubernetto", Species.DOG, now);

        for (int i = 0; i < 6; i++) {
            pet.play(now);
        }

        assertThat(pet.getEnergy()).isZero();
        assertThat(pet.isAlive()).isFalse();
    }
}
