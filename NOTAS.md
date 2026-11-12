# Notas de Implementación - Taller Lección 8: CQRS

## Arquitectura

```
investigadores-service (escribe) → Event Bus
publicaciones-service (escribe)  → Event Bus
                                 ↓
                          consultas-service (lee)
```

## Componentes

- **consultas-service**: Nuevo servicio que mantiene vistas denormalizadas
  - Modelo de lectura: tabla `investigador_con_publicaciones`
  - PostgreSQL para persistencia
  - RabbitMQ para escuchar eventos

## Decisiones de Diseño

1. **Separación de modelos**: Escritura en servicios originales, lectura centralizada
2. **Idempotencia**: Proyecciones toleran eventos duplicados
3. **Consistencia eventual**: Aceptamos delay entre escribir y leer
4. **Reconstrucción**: Endpoint para reconstruir modelo desde eventos

## Entrega

- Branch: `taller-cqrs`
- Estructura: `consultas-service/` como módulo Maven independiente
