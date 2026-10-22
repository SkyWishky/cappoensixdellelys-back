package fr.isen.tamagochi.model;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pet")
public class Pet {

    static final int MIN_STAT = 0;
    static final int MAX_STAT = 100;
    static final int INITIAL_STAT = 80;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    private int fullness;
    private int happiness;
    private int energy;
    private boolean alive;
    private Instant createdAt;
    private Instant lastDecayAt;
    private Instant diedAt;

    protected Pet() {
    }

    public Pet(String name, Species species, Instant now) {
        this.name = name;
        this.species = species;
        this.fullness = INITIAL_STAT;
        this.happiness = INITIAL_STAT;
        this.energy = INITIAL_STAT;
        this.alive = true;
        this.createdAt = now;
        this.lastDecayAt = now;
    }

    public void decay(int points, Instant at) {
        if (!alive || points <= 0) {
            return;
        }
        fullness = clamp(fullness - points);
        happiness = clamp(happiness - points);
        energy = clamp(energy - points);
        lastDecayAt = at;
        dieIfExhausted(at);
    }

    public void feed(Instant now) {
        fullness = clamp(fullness + 30);
        dieIfExhausted(now);
    }

    public void play(Instant now) {
        happiness = clamp(happiness + 25);
        energy = clamp(energy - 15);
        dieIfExhausted(now);
    }

    public void sleep(Instant now) {
        energy = clamp(energy + 40);
        fullness = clamp(fullness - 10);
        dieIfExhausted(now);
    }

    private void dieIfExhausted(Instant now) {
        if (alive && (fullness == MIN_STAT || happiness == MIN_STAT || energy == MIN_STAT)) {
            alive = false;
            diedAt = now;
        }
    }

    private static int clamp(int value) {
        return Math.clamp(value, MIN_STAT, MAX_STAT);
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public Species getSpecies() { return species; }
    public int getFullness() { return fullness; }
    public int getHappiness() { return happiness; }
    public int getEnergy() { return energy; }
    public boolean isAlive() { return alive; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getLastDecayAt() { return lastDecayAt; }
    public Instant getDiedAt() { return diedAt; }
}
