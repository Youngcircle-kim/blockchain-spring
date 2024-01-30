package com.uxm.blockchain.common.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Genre {
    RAndB, HipHop, Ballad, Pop, Jazz, Rock;
    @JsonCreator
    public static Genre of(String genre){
        if (genre == null){
            throw new IllegalArgumentException();
        }

        for (Genre g:Genre.values()){
            if (g.name().equals(genre)){
                return g;
            }
        }

        throw new IllegalArgumentException();
    }
}
