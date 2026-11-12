package com.rica.cqrs.configuracion;

import com.fasterxml.jackson.databind.JsonDeserializer;
import com.rica.cqrs.evento.PublicacionRegistradaEvent;
import com.rica.cqrs.evento.InvestigadorEliminadoEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

@Configuration
public class KafkaConfig {
    
    @Bean
    public JsonSerializer<PublicacionRegistradaEvent> publicacionRegistradaSerializer() {
        return new JsonSerializer<>();
    }
    
    @Bean
    public JsonSerializer<InvestigadorEliminadoEvent> investigadorEliminadoSerializer() {
        return new JsonSerializer<>();
    }
}
