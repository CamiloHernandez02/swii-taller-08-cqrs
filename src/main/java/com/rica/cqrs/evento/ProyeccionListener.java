package com.rica.cqrs.evento;

import com.rica.cqrs.proyector.ProyectorFichas;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ProyeccionListener {
    private final ProyectorFichas proyector;
    
    public ProyeccionListener(ProyectorFichas proyector) {
        this.proyector = proyector;
    }
    
    @KafkaListener(topics = "publicaciones.registradas", groupId = "consultas-service")
    public void escucharPublicacionesRegistradas(PublicacionRegistradaEvent evento) {
        proyector.proyectarPublicacionRegistrada(evento);
    }
    
    @KafkaListener(topics = "investigadores.eliminados", groupId = "consultas-service")
    public void escucharInvestigadoresEliminados(InvestigadorEliminadoEvent evento) {
        proyector.proyectarInvestigadorEliminado(evento);
    }
}
