# Taller Lección 8: CQRS

Ingeniería de Software II (UPTC). Implementación del patrón CQRS (Command Query Responsibility Segregation) sobre rica-microservicios.

## Objetivo

Crear un servicio de lectura (`consultas-service`) que reciba eventos de los servicios de `investigadores` y `publicaciones`, y proyecte una vista denormalizada de la ficha completa del investigador con sus publicaciones.

## Componentes

| Componente | Puerto | Descripción |
| :--- | :--- | :--- |
| `consultas-service` | 8082 | Servicio de lectura con modelo desnormalizado |
| Event Bus | | Comunicación asincrónica entre servicios |

## Requisitos

- Separar modelo de escritura (investigadores-service, publicaciones-service) del modelo de lectura (consultas-service)
- Implementar proyecciones idempotentes
- Escuchar eventos de dominio desde ambos servicios
- Almacenar vista denormalizada (investigador + publicaciones)
- Endpoint HTTP para consultar la ficha completa
- Reconstruir modelo de lectura a partir de eventos

## Ejecutar

```bash
# Iniciar base de datos y servicios (taller anterior)
cd ../swii-taller-06-comunicacion
# Ver instrucciones en ese directorio

# En otra terminal, iniciar consultas-service
cd consultas-service && docker compose up -d && ./mvnw spring-boot:run
```

## Entregables

- Implementación del `consultas-service` con proyecciones
- Endpoint de lectura funcional
- Pruebas de punta a punta
- Rama: `taller-cqrs`
