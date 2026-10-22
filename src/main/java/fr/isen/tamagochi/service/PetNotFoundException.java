package fr.isen.tamagochi.service;

public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException(long id) {
        super("Pet " + id + " not found");
    }
}
