# Sistema de Gestión de Restaurante – Sabor Gourmet

## 📌 Presentación del Proyecto

El proyecto **“Sabor Gourmet”** consiste en el desarrollo de un sistema integral para la gestión de un restaurante, diseñado para automatizar procesos de atención al cliente, control de pedidos, gestión de mesas, inventario, facturación y seguridad de usuarios.  

El sistema está desarrollado con **Spring Boot 3+**, aplicando **AOP (Programación Orientada a Aspectos)** para auditoría y logging, y **Spring Security** para control de autenticación y autorización.

---

## 🏗️ Arquitectura y Tecnologías

- **Framework:** Spring Boot 3+  
- **Patrón de diseño:** MVC (Model-View-Controller)  
- **Front-end:** Thymeleaf + Bootstrap 5  
- **Persistencia:** Spring Data JPA + MySQL (gestionado con SQLyog)  
- **Seguridad:** Spring Security  
  - Roles: `ADMIN`, `MOZO`, `COCINERO`, `CAJERO`  
  - Rutas restringidas según rol:
    - `/admin/**` → Solo ADMIN  
    - `/pedidos/**` → MOZO y COCINERO  
    - `/ventas/**` → CAJERO y ADMIN  
    - `/inventario/**` → ADMIN  
- **Aspectos AOP implementados:**  
  - Auditoría de acciones CRUD en base de datos  
  - Logging de operaciones  

---

## 🗂️ Módulos del Sistema

### 1️⃣ Clientes y Mesas
- **Objetivo:** Registrar clientes, asignar mesas y controlar ocupación.  
- **Flujo:**  
  1. Registro opcional del cliente.  
  2. Asignación de mesa disponible.  
  3. Actualización del estado de la mesa (ocupada/libre).  
- **Tablas principales:**  
  - `Cliente` → idCliente, dni, nombres, apellidos, teléfono, correo, estado  
  - `Mesa` → idMesa, numero, capacidad, estado  

### 2️⃣ Menú y Platos
- **Objetivo:** Registrar platos y bebidas, asociar insumos y controlar stock.  
- **Tablas principales:**  
  - `Plato` → idPlato, nombre, tipo, precio, descripcion, estado  
  - `Insumo` → idInsumo, nombre, unidadMedida, stock, stockMinimo, precioCompra, estado  
  - `PlatoInsumo` → idPlatoInsumo, idPlato, idInsumo, cantidadUsada  

### 3️⃣ Pedidos y Detalles
- **Objetivo:** Registrar y gestionar pedidos asociados a mesas o clientes.  
- **Flujo:** Pedido → Cocina → Servido → Facturación.  
- **Tablas principales:**  
  - `Pedido` → idPedido, idCliente, idMesa, fechaHora, estado  
  - `DetallePedido` → idDetallePedido, idPedido, idPlato, cantidad, subtotal  

### 4️⃣ Ventas y Facturación
- **Objetivo:** Generar facturas automáticas y controlar pagos.  
- **Tablas principales:**  
  - `Factura` → idFactura, idPedido, fechaEmision, total, metodoPago, estado  
  - `DetalleFactura` → idDetalleFactura, idFactura, concepto, monto  

### 5️⃣ Inventario y Compras
- **Objetivo:** Controlar stock de insumos y registrar compras.  
- **Tablas principales:**  
  - `Proveedor` → idProveedor, ruc, nombre, telefono, correo, direccion  
  - `Compra` → idCompra, idProveedor, fechaCompra, total  
  - `DetalleCompra` → idDetalleCompra, idCompra, idInsumo, cantidad, precioUnitario, subtotal  

### 6️⃣ Administración y Seguridad
- **Objetivo:** Gestión de usuarios, roles y auditoría de acciones.  
- **Tablas principales:**  
  - `Usuario` → idUsuario, nombreUsuario, contraseña, rol, estado  
  - `Bitacora` → idBitacora, idUsuario, accion, fechaHora  

---

## 🔑 Relaciones Clave (Resumidas)

- Cliente → Pedido → Factura → DetalleFactura (1 a muchos)  
- Pedido → DetallePedido → Plato → PlatoInsumo → Insumo (1 a muchos)  
- Insumo → DetalleCompra → Compra → Proveedor (1 a muchos)  
- Usuario → Bitacora (1 a muchos)  
- Mesa → Pedido (1 a muchos)  

---

## ✅ Requerimientos del Sistema

### Funcionales (RF)
1. Registrar y consultar clientes, asignar y liberar mesas.  
2. Registrar platos y bebidas, asociar insumos y actualizar stock.  
3. Registrar pedidos, cambiar estado y mostrar pendientes en cocina.  
4. Generar facturas automáticas, registrar método de pago, reportes de ventas.  
5. Registrar proveedores, compras de insumos y alertas de stock bajo.  
6. Crear usuarios, roles, registrar acciones y restringir accesos.  

### No Funcionales (RNF)
- RNF1: Contraseñas cifradas (BCrypt).  
- RNF2: Acceso solo a usuarios autenticados.  
- RNF3: Auditoría completa de acciones.  
- RNF4: Disponibilidad ≥ 98%.  
- RNF5: Respaldo automático de base de datos.  
- RNF6: Respuesta rápida (< 2 segundos al crear pedido).  
- RNF7: Soporta 50 usuarios simultáneos.  
- RNF8: Interfaz simple, intuitiva y agradable.  
- RNF9: Idioma español, compatible con pantallas táctiles.  
- RNF10: Arquitectura modular (MVC/REST).  
- RNF11: Escalable para módulos futuros (delivery, reservas).  

---

## 🛠️ Herramientas Usadas

- **IDE:** IntelliJ IDEA  
- **Base de datos:** MySQL (gestionado con SQLyog)  
- **Servidor local:** XAMPP (Apache + MySQL)  
- **Frontend:** Thymeleaf + Bootstrap 5  
- **Backend:** Spring Boot + Spring Data JPA + Spring Security + AOP  

---

## 📂 Estructura del Proyecto

src/
├─ main/
│ ├─ java/
│ │ └─ com/
│ │ └─ saborgourmet/
│ │ ├─ controller/
│ │ ├─ service/
│ │ ├─ repository/
│ │ ├─ model/
│ │ └─ security/
│ └─ resources/
│ ├─ templates/
│ ├─ static/
│ └─ application.properties
└─ test/

---

## ⚡ Funcionalidades Destacadas

- Gestión completa de mesas, clientes y pedidos.  
- Control de inventario en tiempo real.  
- Auditoría de acciones mediante AOP.  
- Seguridad basada en roles y autenticación con Spring Security.  
- Generación automática de facturas y reportes de ventas.  

---

## 📌 Notas Finales

Este proyecto representa un **sistema integral y escalable** para la gestión de restaurantes, aplicando buenas prácticas de desarrollo con Spring Boot, AOP, seguridad, y control de versiones con Git/GitHub.
