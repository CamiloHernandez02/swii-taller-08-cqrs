package com.rica.cqrs.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "fichas")
public class FichaInvestigador {
    @Id
    private String id;
    
    private String correo;
    private String nombre;
    private String estado;
    
    @JsonProperty("total_publicaciones")
    private int totalPublicaciones;
    
    private List<ResumenPublicacion> publicaciones;
    
    public FichaInvestigador() {
        this.publicaciones = new ArrayList<>();
        this.totalPublicaciones = 0;
        this.estado = "activo";
    }
    
    public FichaInvestigador(String correo, String nombre) {
        this();
        this.correo = correo;
        this.nombre = nombre;
        this.id = correo;
    }
    
    public synchronized void registrarPublicacion(ResumenPublicacion publicacion) {
        // Evitar duplicados
        boolean existe = publicaciones.stream()
            .anyMatch(p -> p.getPublicacionId().equals(publicacion.getPublicacionId()));
        if (!existe) {
            publicaciones.add(publicacion);
            totalPublicaciones++;
        }
    }
    
    public synchronized void eliminarPublicaciones() {
        publicaciones.clear();
        totalPublicaciones = 0;
    }
    
    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    
    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    
    public int getTotalPublicaciones() { return totalPublicaciones; }
    public void setTotalPublicaciones(int total) { this.totalPublicaciones = total; }
    
    public List<ResumenPublicacion> getPublicaciones() { return publicaciones; }
    public void setPublicaciones(List<ResumenPublicacion> publicaciones) { 
        this.publicaciones = publicaciones;
        this.totalPublicaciones = publicaciones.size();
    }
}
