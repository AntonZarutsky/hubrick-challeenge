package de.hubrick.challenge.zarutsky.domain;

import java.util.Optional;

public enum Gender {

    MALE("m"),
    FEMALE("f");

    private String shortName;

    Gender(String shortName) {
        this.shortName = shortName;
    }

    public String getShortName() {
        return shortName;
    }

    public static Optional<Gender> byShortName(String name){
        for (Gender gender : Gender.values()){
            if (gender.getShortName().equals(name)){
                return Optional.of(gender);
            }
        }
        return Optional.empty();
    }
}
