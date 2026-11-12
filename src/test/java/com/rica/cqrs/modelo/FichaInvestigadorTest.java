package com.rica.cqrs.modelo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FichaInvestigadorTest {
    
    @Test
    public void testCrearFichaConValoresIniciales() {
        FichaInvestigador ficha = new FichaInvestigador("test@uptc.edu.co", "Juan");
        
        assertEquals("test@uptc.edu.co", ficha.getCorreo());
        assertEquals("Juan", ficha.getNombre());
        assertEquals(0, ficha.getTotalPublicaciones());
        assertEquals("activo", ficha.getEstado());
        assertTrue(ficha.getPublicaciones().isEmpty());
    }
    
    @Test
    public void testRegistrarPublicacion() {
        FichaInvestigador ficha = new FichaInvestigador("test@uptc.edu.co", "Juan");
        ResumenPublicacion pub = new ResumenPublicacion("pub-1", "Artículo CQRS", 2025);
        
        ficha.registrarPublicacion(pub);
        
        assertEquals(1, ficha.getTotalPublicaciones());
        assertEquals(1, ficha.getPublicaciones().size());
        assertEquals("Artículo CQRS", ficha.getPublicaciones().get(0).getTitulo());
    }
    
    @Test
    public void testIdempotencia() {
        FichaInvestigador ficha = new FichaInvestigador("test@uptc.edu.co", "Juan");
        ResumenPublicacion pub = new ResumenPublicacion("pub-1", "Artículo CQRS", 2025);
        
        ficha.registrarPublicacion(pub);
        ficha.registrarPublicacion(pub); // Intentar registrar duplicado
        
        assertEquals(1, ficha.getTotalPublicaciones()); // Debe seguir siendo 1
    }
    
    @Test
    public void testEliminarPublicaciones() {
        FichaInvestigador ficha = new FichaInvestigador("test@uptc.edu.co", "Juan");
        ficha.registrarPublicacion(new ResumenPublicacion("pub-1", "Artículo 1", 2025));
        ficha.registrarPublicacion(new ResumenPublicacion("pub-2", "Artículo 2", 2024));
        
        ficha.eliminarPublicaciones();
        
        assertEquals(0, ficha.getTotalPublicaciones());
        assertTrue(ficha.getPublicaciones().isEmpty());
    }
}
