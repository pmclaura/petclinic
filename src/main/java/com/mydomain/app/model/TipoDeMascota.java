package com.mydomain.app.model;

public enum TipoDeMascota {
    PERRO("perro"),
    GATO("gato"),
    HAMSTER("hamster"),
    PEZ("pez"),
    PERIQUITO("periquito");

    private String tipo;

    TipoDeMascota() {
    }

    TipoDeMascota(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
