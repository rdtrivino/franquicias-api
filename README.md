# 📘 Franquicias API – README

## 🚀 Descripción del Proyecto
Este proyecto implementa un API para gestionar **franquicias**, sus **sucursales** y los **productos** disponibles en cada sucursal.  
El objetivo es cumplir con los criterios solicitados en la prueba técnica:

- Manejo de franquicias → sucursales → productos  
- Operaciones CRUD esenciales  
- Endpoint para consultar el/los productos con mayor stock  
- Persistencia real en **MySQL**  
- Arquitectura clara y organizada  
- Proyecto empacado en **Docker**  
- Programación **reactiva (WebFlux)**  
- Infraestructura lista para integración futura con Terraform

---

# 🛠️ Tecnologías Utilizadas
| Componente | Tecnología |
|-----------|------------|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.5.7 |
| Programación reactiva | Spring WebFlux |
| Persistencia | Spring Data JPA + MySQL |
| Construcción | Maven 3.9 |
| Contenedores | Docker + Docker Compose |
| Arquitectura | Clean Architecture |

---

# 📦 Estructura del Proyecto
```
src/
 └── main/java/com/rubentrivino/franquiciasapi
      ├── domain/
      ├── application/
      ├── infrastructure/
      │     ├── controller
      │     └── repository
      └── FranquiciasApiApplication.java
```

---

# ⚙️ Cómo Ejecutar el Proyecto

## 1. Clonar el repositorio
```bash
git clone https://github.com/rdtrivino/franquicias-api.git
cd franquicias-api
```

---

# 🐳 Ejecución con Docker
## Construir e iniciar los contenedores
```bash
docker compose up -d --build
```

## Detener contenedores
```bash
docker compose down -v
```

---

# 🗄️ Base de Datos
La base se crea automáticamente:

```
MYSQL_DATABASE: franquicias_db
MYSQL_USER: root
MYSQL_PASSWORD: root
```

La aplicación se conecta con:
```
jdbc:mysql://db:3306/franquicias_db
```

---

# 📚 Documentación de Endpoints

## 1. Crear franquicia
POST `/api/franchises`
```json
{ "name": "Franquicia A" }
```

## 2. Agregar sucursal
POST `/api/franchises/{franchiseId}/branches`
```json
{ "name": "Sucursal Norte" }
```

## 3. Listar sucursales
GET `/api/franchises/{franchiseId}/branches`

## 4. Agregar producto
POST `/api/branches/{branchId}/products`
```json
{ "name": "Producto X", "stock": 25 }
```

## 5. Eliminar producto
DELETE `/api/products/{productId}`

## 6. Modificar stock
PUT `/api/products/{productId}/stock`
```json
{ "stock": 50 }
```

## 7. Producto con mayor stock por franquicia
GET `/api/franchises/{franchiseId}/products/max-stock`

---

# ⭐ Endpoints Extra (PLUS)

## Actualizar nombre franquicia
PUT `/api/franchises/{id}`
```json
{ "name": "Nuevo Nombre" }
```

## Actualizar nombre sucursal
PUT `/api/branches/{id}`
```json
{ "name": "Sucursal Actualizada" }
```

## Actualizar nombre producto
PUT `/api/products/{id}`
```json
{ "name": "Producto Actualizado" }
```

---

# 🏗️ Docker Compose
```yaml
services:
  db:
    image: mysql:8
    container_name: franquicias-db
    environment:
      MYSQL_ROOT_PASSWORD: root
      MYSQL_DATABASE: franquicias_db
      MYSQL_USER: root
      MYSQL_PASSWORD: root
    ports:
      - "3307:3306"
    volumes:
      - db_data:/var/lib/mysql
    networks:
      - franquicias-net

  api:
    build: .
    container_name: franquicias-api
    ports:
      - "8081:8080"
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://db:3306/franquicias_db
      SPRING_DATASOURCE_USERNAME: root
      SPRING_DATASOURCE_PASSWORD: root
    depends_on:
      - db
    networks:
      - franquicias-net

networks:
  franquicias-net:

volumes:
  db_data:
```

---

# 🧪 Pruebas
- Pruebas unitarias con JUnit + Mockito (en progreso)
- Integración futura con Terraform (opcional)

---

# 📄 Licencia
Uso libre para pruebas.

---

# ✔️ Estado del Proyecto
| Requisito | Estado |
|-----------|--------|
| CRUD franquicias | ✔️ |
| CRUD sucursales | ✔️ |
| CRUD productos | ✔️ |
| Max Stock | ✔️ |
| Persistencia MySQL | ✔️ |
| Docker | ✔️ |
| WebFlux | ✔️ |
| Terraform  | ✔️ |
