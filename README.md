# API Ventas - Cadena de Supermercados

Sistema de gestión de ventas para cadena de supermercados desarrollado con Spring Boot.

## Descripción

API REST para la gestión de ventas, productos, sucursales y estadísticas de una cadena de supermercados. Implementa arquitectura en capas con patrón DTO y cálculo de estadísticas usando Java Streams.

## Tecnologías

- **Java 17**
- **Spring Boot 3.5.7**
- **Spring Data JPA**
- **PostgreSQL** (base de datos principal)
- **Docker & Docker Compose** (containerización)
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

- **Docker** y **Docker Compose** instalados
- JDK 17 o superior (solo si ejecutas sin Docker)
- Gradle (incluido con wrapper)

### 🐳 Opción 1: Ejecutar con Docker Compose (Recomendado)

Esta es la forma más rápida y sencilla de ejecutar el proyecto con todas sus dependencias.

#### 1. Clonar el repositorio

```bash
git clone <url-del-repositorio>
cd PruebaTecSupermercado
```

#### 2. Configurar variables de entorno

Crea un archivo `.env` en la raíz del proyecto basándote en `.env.example`:

```bash
# Copiar el archivo de ejemplo
cp .env.example .env
```

Edita el archivo `.env` con tus credenciales:

```env
# Database Configuration
DB_URL=jdbc:postgresql://db:5432/supermercadoDB
DB_USERNAME=postgres
DB_PASSWORD=tu_contraseña_segura
DB_PORT=5432
DB_DIALECT=org.hibernate.dialect.PostgreSQLDialect

# Application Configuration
APP_PORT=8080
```

> ⚠️ **Importante**: El archivo `.env` contiene información sensible y **NO** debe subirse a Git. Ya está incluido en `.gitignore`.

#### 3. Construir y ejecutar los contenedores

```bash
# Construir y levantar los servicios en segundo plano
docker-compose up --build -d

# Ver los logs de la aplicación
docker-compose logs -f app

# Ver los logs de la base de datos
docker-compose logs -f db
```

#### 4. Verificar que todo esté funcionando

```bash
# Ver el estado de los contenedores
docker-compose ps

# Deberías ver dos contenedores corriendo:
# - supermercado-app (aplicación Spring Boot)
# - supermercado-db (PostgreSQL)
```

#### 5. La API estará disponible en:

```
http://localhost:8080

```


### 💻 Opción 2: Ejecutar localmente (sin Docker)

Si prefieres ejecutar la aplicación sin Docker, necesitarás tener PostgreSQL instalado localmente.

#### 1. Instalar PostgreSQL

Descarga e instala PostgreSQL desde [postgresql.org](https://www.postgresql.org/download/)

#### 2. Crear la base de datos

```sql
CREATE DATABASE supermercadoDB;
```

#### 3. Configurar variables de entorno

Actualiza el archivo `.env` con la URL de tu PostgreSQL local:

```env
DB_URL=jdbc:postgresql://localhost:5432/supermercadoDB
DB_USERNAME=postgres
DB_PASSWORD=tu_contraseña
```

#### 4. Ejecutar la aplicación

```bash
# Windows
.\gradlew.bat bootRun

# Linux/Mac
./gradlew bootRun
```

#### 5. La API estará disponible en:

```
http://localhost:8080
```

---

## 🐋 Arquitectura Docker

El proyecto utiliza Docker Compose para orquestar dos servicios:

### Servicios

1. **supermercado-app**: Aplicación Spring Boot
   - Puerto: `8080:8080`
   - Imagen: `springio/gs-spring-boot-docker`
   - Depende de: `supermercado-db`

2. **supermercado-db**: Base de datos PostgreSQL
   - Puerto: `5432:5432`
   - Imagen: `postgres:15-alpine`
   - Volumen persistente: `postgres-data`

### Red

Los contenedores se comunican a través de una red bridge personalizada llamada `supermercado-network`, lo que permite que la aplicación se conecte a la base de datos usando el nombre del servicio (`db`) en lugar de `localhost`.

### Persistencia de Datos

Los datos de PostgreSQL se almacenan en un volumen Docker llamado `postgres-data`, lo que garantiza que los datos persistan incluso si los contenedores se eliminan.

---

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
    "productoId": 2,
    "nombreProducto": "Coca Cola 1L.",
    "categoria": "Bebidas",
    "cantidadTotal": 100
}
```
<img width="1760" height="691" alt="Captura de pantalla 2025-11-26 080711" src="https://github.com/user-attachments/assets/136b65e9-fd91-45c7-91b2-38c99d0ee49e" />

---

#### Filtrado por sucursal

```http
GET http://localhost:8080/api/estadisticas/producto-mas-vendido?sucursalId=1
```

**Respuesta:**
```json
{
    "productoId": 2,
    "nombreProducto": "Coca Cola 1L.",
    "categoria": "Bebidas",
    "cantidadTotal": 100
}
```

**Descripción:** Retorna el producto más vendido únicamente en la sucursal con ID 1.

---

<img width="1758" height="722" alt="Captura de pantalla 2025-11-26 080740" src="https://github.com/user-attachments/assets/d18152e3-e2fb-4a68-b2c6-cfb842cfbe4a" />


#### Filtrado por fecha

```http
GET http://localhost:8080/api/estadisticas/producto-mas-vendido?fecha=2025-09-09
```

**Respuesta:**
```json
{
    "productoId": 2,
    "nombreProducto": "Coca Cola 1L.",
    "categoria": "Bebidas",
    "cantidadTotal": 100
}
```

**Descripción:** Retorna el producto más vendido en la fecha específica (formato: `yyyy-MM-dd`).

---

<img width="1766" height="724" alt="Captura de pantalla 2025-11-26 080758" src="https://github.com/user-attachments/assets/77932100-ee6c-4001-b545-64e3815bbb6a" />

#### Filtrado por fecha Y sucursal

```http
GET http://localhost:8080/api/estadisticas/producto-mas-vendido?fecha=2025-09-09&sucursalId=1
```

**Respuesta:**
```json
{
    "productoId": 1,
    "nombreProducto": "Naranjas",
    "categoria": "Frutas y Verduras",
    "cantidadTotal": 10
}
```

**Descripción:** Retorna el producto más vendido en la fecha específica y sucursal específica.

---

<img width="1760" height="702" alt="Captura de pantalla 2025-11-26 080817" src="https://github.com/user-attachments/assets/2d3f646c-ab29-4ed3-bd10-efa4cd222eff" />

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
