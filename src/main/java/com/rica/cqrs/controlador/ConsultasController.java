package com.rica.cqrs.controlador;

import com.rica.cqrs.modelo.FichaInvestigador;
import com.rica.cqrs.modelo.FichaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/consultas")
public class ConsultasController {
    private final FichaRepository fichaRepository;
    
    public ConsultasController(FichaRepository fichaRepository) {
        this.fichaRepository = fichaRepository;
    }
    
    @GetMapping("/investigadores/{correo}")
    public ResponseEntity<FichaInvestigador> obtenerFicha(@PathVariable String correo) {
        FichaInvestigador ficha = fichaRepository.findByCorreo(correo);
        if (ficha == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ficha);
    }
    
    @GetMapping("/investigadores")
    public ResponseEntity<List<FichaInvestigador>> listarFichas(
            @RequestParam(required = false) String estado) {
        List<FichaInvestigador> fichas;
        if (estado != null) {
            fichas = fichaRepository.findByEstado(estado);
        } else {
            fichas = fichaRepository.findAll();
        }
        return ResponseEntity.ok(fichas);
    }
    
    @GetMapping("/investigadores/ranking/publicaciones")
    public ResponseEntity<List<FichaInvestigador>> rankingPublicaciones() {
        List<FichaInvestigador> ranking = fichaRepository.findAllByOrderByTotalPublicacionesDesc();
        return ResponseEntity.ok(ranking);
    }
}
