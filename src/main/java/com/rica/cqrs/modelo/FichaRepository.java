package com.rica.cqrs.modelo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FichaRepository extends MongoRepository<FichaInvestigador, String> {
    FichaInvestigador findByCorreo(String correo);
    List<FichaInvestigador> findByEstado(String estado);
    List<FichaInvestigador> findAllByOrderByTotalPublicacionesDesc();
}
