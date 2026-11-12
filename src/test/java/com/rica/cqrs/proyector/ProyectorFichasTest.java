package com.rica.cqrs.proyector;

import com.rica.cqrs.evento.PublicacionRegistradaEvent;
import com.rica.cqrs.evento.InvestigadorEliminadoEvent;
import com.rica.cqrs.modelo.FichaInvestigador;
import com.rica.cqrs.modelo.FichaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProyectorFichasTest {
    @Mock
    private FichaRepository fichaRepository;
    
    @InjectMocks
    private ProyectorFichas proyector;
    
    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testProyectarPublicacionRegistrada() {
        String correo = "test@uptc.edu.co";
        FichaInvestigador fichaExistente = new FichaInvestigador(correo, "Juan");
        
        when(fichaRepository.findByCorreo(correo)).thenReturn(fichaExistente);
        
        PublicacionRegistradaEvent evento = new PublicacionRegistradaEvent(
            "pub-1", correo, "Artículo CQRS", 2025
        );
        
        proyector.proyectarPublicacionRegistrada(evento);
        
        ArgumentCaptor<FichaInvestigador> captor = ArgumentCaptor.forClass(FichaInvestigador.class);
        verify(fichaRepository).save(captor.capture());
        
        FichaInvestigador fichaGuardada = captor.getValue();
        assertEquals(1, fichaGuardada.getTotalPublicaciones());
        assertEquals("Artículo CQRS", fichaGuardada.getPublicaciones().get(0).getTitulo());
    }
    
    @Test
    public void testEventoFueraDeOrden() {
        String correo = "test@uptc.edu.co";
        
        when(fichaRepository.findByCorreo(correo)).thenReturn(null); // Ficha no existe
        
        PublicacionRegistradaEvent evento = new PublicacionRegistradaEvent(
            "pub-1", correo, "Artículo CQRS", 2025
        );
        
        proyector.proyectarPublicacionRegistrada(evento);
        
        ArgumentCaptor<FichaInvestigador> captor = ArgumentCaptor.forClass(FichaInvestigador.class);
        verify(fichaRepository).save(captor.capture());
        
        FichaInvestigador fichaCreada = captor.getValue();
        assertNotNull(fichaCreada);
        assertEquals(correo, fichaCreada.getCorreo());
        assertEquals(1, fichaCreada.getTotalPublicaciones());
    }
    
    @Test
    public void testEvitarDuplicados() {
        String correo = "test@uptc.edu.co";
        FichaInvestigador ficha = new FichaInvestigador(correo, "Juan");
        
        when(fichaRepository.findByCorreo(correo)).thenReturn(ficha);
        
        PublicacionRegistradaEvent evento = new PublicacionRegistradaEvent(
            "pub-1", correo, "Artículo CQRS", 2025
        );
        
        // Proyectar dos veces el mismo evento
        proyector.proyectarPublicacionRegistrada(evento);
        proyector.proyectarPublicacionRegistrada(evento);
        
        ArgumentCaptor<FichaInvestigador> captor = ArgumentCaptor.forClass(FichaInvestigador.class);
        verify(fichaRepository, times(2)).save(captor.capture());
        
        // La última captura debe tener solo 1 publicación (idempotencia)
        FichaInvestigador fichaFinal = captor.getValue();
        assertEquals(1, fichaFinal.getTotalPublicaciones());
    }
    
    @Test
    public void testProyectarInvestigadorEliminado() {
        String correo = "test@uptc.edu.co";
        FichaInvestigador ficha = new FichaInvestigador(correo, "Juan");
        ficha.registrarPublicacion(new com.rica.cqrs.modelo.ResumenPublicacion("pub-1", "Art1", 2025));
        
        when(fichaRepository.findByCorreo(correo)).thenReturn(ficha);
        
        InvestigadorEliminadoEvent evento = new InvestigadorEliminadoEvent(correo);
        proyector.proyectarInvestigadorEliminado(evento);
        
        ArgumentCaptor<FichaInvestigador> captor = ArgumentCaptor.forClass(FichaInvestigador.class);
        verify(fichaRepository).save(captor.capture());
        
        FichaInvestigador fichaActualizada = captor.getValue();
        assertEquals("eliminado", fichaActualizada.getEstado());
        assertEquals(0, fichaActualizada.getTotalPublicaciones());
        assertTrue(fichaActualizada.getPublicaciones().isEmpty());
    }
    
    @Test
    public void testReconstruir() {
        proyector.reconstruirDesdeInicio();
        verify(fichaRepository).deleteAll();
    }
}
