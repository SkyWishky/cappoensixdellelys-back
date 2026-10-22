package fr.isen.tamagochi.service;

public class PetIsDeadException extends RuntimeException {

    public PetIsDeadException(long id) {
        super("Pet " + id + " is dead, it cannot do anything anymore");
    }
}
