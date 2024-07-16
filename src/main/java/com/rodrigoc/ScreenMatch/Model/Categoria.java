package com.rodrigoc.ScreenMatch.Model;

public enum Categoria {
    ACCION("Action", "Acción"), ROMANCE("Romance", "Romance"), COMEDIA("Comedy", "Comedia"), DRAMA("Drama", "Drama"), CRIMEN("Crime", "Crimen");

    private String categoriaOmbd;
    private String espanol;
    Categoria (String categoriaOmbd, String espanol){
        this.categoriaOmbd = categoriaOmbd;
        this.espanol = espanol;
    }

    public static Categoria fromString(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.categoriaOmbd.equalsIgnoreCase(text)){
                return categoria;
            }
        }

        throw new IllegalArgumentException("Ninguna Categoria Encontrada: "+text);
    }

    public static Categoria fromEspanol(String text){
        for(Categoria categoria : Categoria.values()){
            if(categoria.espanol.equalsIgnoreCase(text)){
                return categoria;
            }
        }

        throw new IllegalArgumentException("Ninguna Categoria Encontrada: "+text);
    }
}
