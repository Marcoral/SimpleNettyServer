package com.github.marcoral.simplenettyserver.api.util;

public class Validate {
    public static void parameterNotNull(Object what, String name) {
        if(what == null)
            throw new IllegalArgumentException(name + " must not be null!");
    }
}
