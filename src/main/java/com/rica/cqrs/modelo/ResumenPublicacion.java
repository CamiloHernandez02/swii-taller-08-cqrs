package com.rica.cqrs.modelo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResumenPublicacion {
    @JsonProperty("publicacion_id")
    private String publicacionId;
    
    private String titulo;
    private Integer anio;
    
    public ResumenPublicacion() {}
    
    public ResumenPublicacion(String publicacionId, String titulo, Integer anio) {
        this.publicacionId = publicacionId;
        this.titulo = titulo;
        this.anio = anio;
    }
    
    public String getPublicacionId() { return publicacionId; }
    public void setPublicacionId(String publicacionId) { this.publicacionId = publicacionId; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
}
