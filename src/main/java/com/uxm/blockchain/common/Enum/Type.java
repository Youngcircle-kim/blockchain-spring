package com.uxm.blockchain.common.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

@Getter
public enum Type {
    General, Producer;
    @JsonCreator
    public static Type of(String type){
        if (type == null){
            throw new IllegalArgumentException();
        }

        for (Type t : Type.values()){
            if (t.name().equals(type)){
                return t;
            }
        }

        throw new IllegalArgumentException();
    }
}
