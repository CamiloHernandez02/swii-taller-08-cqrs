package com.rica.cqrs.proyector;

import com.rica.cqrs.evento.PublicacionRegistradaEvent;
import com.rica.cqrs.evento.InvestigadorEliminadoEvent;
import com.rica.cqrs.modelo.FichaInvestigador;
import com.rica.cqrs.modelo.FichaRepository;
import com.rica.cqrs.modelo.ResumenPublicacion;
import org.springframework.stereotype.Service;

@Service
public class ProyectorFichas {
    private final FichaRepository fichaRepository;
    
    public ProyectorFichas(FichaRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }
    
    public synchronized void proyectarPublicacionRegistrada(PublicacionRegistradaEvent evento) {
        String correo = evento.getInvestigadorCorreo();
        FichaInvestigador ficha = fichaRepository.findByCorreo(correo);
        
        // Si la ficha no existe, crearla (evento fuera de orden)
        if (ficha == null) {
            ficha = new FichaInvestigador(correo, correo.split("@")[0]);
        }
        
        // Registrar la publicación (idempotencia)
        ResumenPublicacion resumen = new ResumenPublicacion(
            evento.getPublicacionId(),
            evento.getTitulo(),
            evento.getAnio()
        );
        ficha.registrarPublicacion(resumen);
        
        fichaRepository.save(ficha);
    }
    
    public synchronized void proyectarInvestigadorEliminado(InvestigadorEliminadoEvent evento) {
        String correo = evento.getInvestigadorCorreo();
        FichaInvestigador ficha = fichaRepository.findByCorreo(correo);
        
        if (ficha != null) {
            ficha.setEstado("eliminado");
            ficha.eliminarPublicaciones();
            fichaRepository.save(ficha);
        }
    }
    
    public void reconstruirDesdeInicio() {
        fichaRepository.deleteAll();
    }
}
