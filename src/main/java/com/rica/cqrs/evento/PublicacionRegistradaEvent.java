package com.rica.cqrs.evento;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PublicacionRegistradaEvent {
    @JsonProperty("publicacion_id")
    private String publicacionId;
    
    @JsonProperty("investigador_correo")
    private String investigadorCorreo;
    
    private String titulo;
    private Integer anio;
    
    public PublicacionRegistradaEvent() {}
    
    public PublicacionRegistradaEvent(String publicacionId, String investigadorCorreo, String titulo, Integer anio) {
        this.publicacionId = publicacionId;
        this.investigadorCorreo = investigadorCorreo;
        this.titulo = titulo;
        this.anio = anio;
    }
    
    public String getPublicacionId() { return publicacionId; }
    public void setPublicacionId(String publicacionId) { this.publicacionId = publicacionId; }
    
    public String getInvestigadorCorreo() { return investigadorCorreo; }
    public void setInvestigadorCorreo(String investigadorCorreo) { this.investigadorCorreo = investigadorCorreo; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public Integer getAnio() { return anio; }
    public void setAnio(Integer anio) { this.anio = anio; }
}
