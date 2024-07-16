package com.rodrigoc.ScreenMatch.Principal;

import java.util.Arrays;
import java.util.List;

public class Streams {
    public void muestraEjemplo(){
        List<String> nombres = Arrays.asList("Pedro", "Pablo", "Juan");
        nombres.stream()
                .sorted()
                .limit(1)
                .filter(n-> n.startsWith("J"))
                .map(n -> n.toUpperCase())
                .forEach(System.out::println);
    }
}
