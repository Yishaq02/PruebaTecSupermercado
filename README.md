# API Ventas - Cadena de Supermercados

Sistema de gestión de ventas para cadena de supermercados desarrollado con Spring Boot.

## Descripción

API REST para la gestión de ventas, productos, sucursales y estadísticas de una cadena de supermercados. Implementa arquitectura en capas con patrón DTO y cálculo de estadísticas usando Java Streams.

## Tecnologías

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **H2 Database** (desarrollo)
- **MySQL** (producción)
- **Lombok**
- **Gradle**

## Estructura del Proyecto

```
src/main/java/com/isaacCompany/PruebaTecSupermercado/
├── controller/          # Controladores REST
├── service/            # Lógica de negocio
├── repository/         # Acceso a datos
├── model/             # Entidades JPA
├── dto/               # Data Transfer Objects
├── mapper/            # Mapeo entre entidades y DTOs
└── exception/         # Manejo de excepciones
```

## Modelo de Datos

### Entidades Principales

- **Venta**: Registro de ventas con estado (REGISTRADA/ANULADA)
- **DetalleVenta**: Líneas de productos por venta
- **Producto**: Catálogo de productos
- **Sucursal**: Sucursales de la cadena

### Relaciones

- `Venta` → `Sucursal` (ManyToOne)
- `Venta` → `DetalleVenta` (OneToMany)
- `DetalleVenta` → `Producto` (ManyToOne)

## Instalación y Ejecución

### Prerrequisitos

- JDK 17 o superior
- Gradle (incluido con wrapper)

### Pasos

1. **Clonar el repositorio**
```bash
git clone <url-del-repositorio>
cd PruebaTecSupermercado
```

2. **Ejecutar la aplicación**
```bash
# Windows
.\gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

3. **La API estará disponible en:**
```
http://localhost:8080
```

## Endpoints

### Productos

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/productos` | Listar todos los productos |
| POST | `/api/productos` | Crear producto |
| PUT | `/api/productos/{id}` | Actualizar producto |
| DELETE | `/api/productos/{id}` | Eliminar producto |

### Sucursales

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/sucursales` | Listar todas las sucursales |
| POST | `/api/sucursales` | Crear sucursal |
| PUT | `/api/sucursales/{id}` | Actualizar sucursal |
| DELETE | `/api/sucursales/{id}` | Eliminar sucursal |

### Ventas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/ventas` | Listar todas las ventas |
| POST | `/api/ventas` | Crear venta |
| PUT | `/api/ventas/{id}` | Actualizar venta |
| DELETE | `/api/ventas/{id}` | Eliminar venta |

### Estadísticas

| Método | Endpoint | Descripción |
|--------|----------|-------------|
| GET | `/api/estadisticas/producto-mas-vendido` | Producto más vendido |

## Endpoint de Estadísticas - Producto Más Vendido

Este endpoint calcula el producto más vendido utilizando **Java Streams** y filtra solo las ventas con estado `REGISTRADA` (excluye ventas anuladas).

### Casos de Uso

#### Sin filtros (todos los productos de todas las sucursales y fechas)

```http
GET http://localhost:8080/api/estadisticas/producto-mas-vendido
```

**Respuesta:**
```json
{
    "productoId": 1,
    "nombreProducto": "Naranjas",
    "categoria": "Frutas",
    "cantidadTotal": 150
}
```

---

#### Filtrado por sucursal

```http
GET http://localhost:8080/api/estadisticas/producto-mas-vendido?sucursalId=1
```

**Respuesta:**
```json
{
    "productoId": 2,
    "nombreProducto": "Manzanas",
    "categoria": "Frutas",
    "cantidadTotal": 85
}
```

**Descripción:** Retorna el producto más vendido únicamente en la sucursal con ID 1.

---

#### Filtrado por fecha

```http
GET http://localhost:8080/api/estadisticas/producto-mas-vendido?fecha=2025-09-09
```

**Respuesta:**
```json
{
    "productoId": 3,
    "nombreProducto": "Leche",
    "categoria": "Lácteos",
    "cantidadTotal": 120
}
```

**Descripción:** Retorna el producto más vendido en la fecha específica (formato: `yyyy-MM-dd`).

---

#### Filtrado por fecha Y sucursal

```http
GET http://localhost:8080/api/estadisticas/producto-mas-vendido?fecha=2025-09-09&sucursalId=1
```

**Respuesta:**
```json
{
    "productoId": 1,
    "nombreProducto": "Naranjas",
    "categoria": "Frutas",
    "cantidadTotal": 45
}
```

**Descripción:** Retorna el producto más vendido en la fecha específica y sucursal específica.

---

### Respuesta cuando no hay datos

Si no existen ventas que cumplan los criterios de filtrado:

```http
HTTP/1.1 204 No Content
```

## Ejemplos de Uso

### Crear un Producto

```http
POST http://localhost:8080/api/productos
Content-Type: application/json

{
    "nombre": "Naranjas",
    "categoria": "Frutas",
    "precio": 1500.0,
    "cantidad": 100
}
```

### Crear una Sucursal

```http
POST http://localhost:8080/api/sucursales
Content-Type: application/json

{
    "nombre": "Sucursal Centro",
    "direccion": "Av. Principal 123"
}
```

### Crear una Venta

```http
POST http://localhost:8080/api/ventas
Content-Type: application/json

{
    "fecha": "2025-11-20",
    "estado": "REGISTRADA",
    "idSucursal": 1,
    "detalle": [
        {
            "nombreProd": "Naranjas",
            "cantProd": 10,
            "precio": 1500.0,
            "subtotal": 15000.0
        }
    ],
    "total": 15000.0
}
```



## Características Destacadas

-  **Enum EstadoVenta**: Type-safe para estados de venta (REGISTRADA/ANULADA)
-  **Cálculo con Streams**: Estadísticas calculadas usando programación funcional
-  **Arquitectura en Capas**: Separación clara de responsabilidades
-  **DTOs**: Desacoplamiento entre capa de presentación y modelo de datos
-  **Soft Delete**: Campo `borradoLogico` para eliminación lógica
-  **Filtros Opcionales**: Endpoints flexibles con query parameters

## Notas Técnicas

### Estados de Venta

Las ventas pueden tener dos estados:
- `REGISTRADA`: Venta válida que cuenta para estadísticas
- `ANULADA`: Venta cancelada que NO cuenta para estadísticas

### Cálculo de Estadísticas

El servicio de estadísticas utiliza Java Streams para:
1. Filtrar ventas por estado (solo REGISTRADA)
2. Aplicar filtros opcionales (fecha, sucursal)
3. Agrupar por producto
4. Sumar cantidades vendidas
5. Encontrar el máximo

## Autor

Isaac - Prueba Técnica Supermercado

## Licencia

Este proyecto es de uso educativo y de demostración.
