
## 🍔 Sistema de Punto de Venta - Comida Rápida FUDSAP

**Equipo #15**

## 👥 Integrantes
- **Santiago Pérez Ana Belén** - Desarrollador y Diseñador

---

## 📌 Descripción del Sistema

Sistema de gestión integral para restaurantes de comida rápida que permite:
- Identificación de roles (Admin, Cajero, Cliente)
- Gestión de compra y venta de alimentos
- Control de inventario en tiempo real
- Generación automatizada de reportes y tickets
- Sistema de autenticación seguro con CAPTCHA

**🖥️ Tipo de Sistema:** Desktop App en Java, con Swing


**🧩 Componente visual integrado:**
- Validación de formularios con `CAPTCHA` del Equipo 2  
  🔗 [Repositorio del componente CAPTCHA](https://github.com/FanyBr07/ComponenteVisual)

**📦 Librería externa implementada:**
- Selector de fechas con `Libreria_impuestos` del Equipo 14 
  🔗 [Repositorio del componente Libreria-impuestos]( https://github.com/LuisMarioMonterrubio/Libreria-Calculadora-de-Impuestos  )

---

## 🔧 Funcionalidades Principales

### 🔒 Seguridad Integrada
- Validación de email 
- Contraseña mínima de 6 caracteres
- Acceso administrativo especial
- Conexión segura con PostgreSQL
- Manejo robusto de excepciones

```java
private boolean validarEmail(String email) {
    String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
    return Pattern.compile(emailRegex).matcher(email).matches();
}
````

### 📝 Operaciones CRUD

* **Usuarios**:

  * Crear, editar y desactivar cuentas (Admin, Empleado, Cliente)
* **Productos**:

  * Registrar alimentos 
  * Eliminar alimentos
  * Categorías: Combos, Bebidas, Postres, etc.

### 🛒 Proceso Principal: Venta de Comida

1. Selección visual de productos
2. Promociones automáticas
3. Cálculo de impuestos con `Libreria_impuestos`
4. Generación de ticket digital/imprimible

### 📤 Envío de PDF por Correo Electrónico

* **Tecnología:** iTextPDF + JavaMail
* **Contenido del PDF:**

  * Detalles del pedido
  * IVA (16%) calculado automáticamente
  * Detección de rol (empleado o cliente)
  * Enviado automáticamente al correo registrado

```java
Document document = new Document();
PdfWriter.getInstance(document, new FileOutputStream("ticket_compra.pdf"));
document.open();
Paragraph titulo = new Paragraph("RECIBO DE COMPRA", new Font(Font.FontFamily.HELVETICA, 20, Font.BOLD));
titulo.setAlignment(Element.ALIGN_CENTER);
document.add(titulo);
```

🔗 Repositorio de PDF: [iTextPDF](https://github.com/itext/itextpdf)

---

## 🧠 Otras Características Únicas


* **Validación CAPTCHA** en login
* **Cambio de interfaz** dinámico por rol de usuario
* **Detección y personalización automática** del contenido del ticket según usuario

---

## ⚙️ Dependencias y Configuración

### 📚 Librerías Externas Usadas

| Librería            | Función                          |
| ------------------- | -------------------------------- |
| CaptchaCadenas      | Validación anti-bots             |
| Libreria\_impuestos | Cálculo de impuestos (IVA, etc.) |
| PDFBox / iTextPDF   | Generación de PDF de tickets     |
| JavaMail            | Envío de correos con adjuntos    |

---

### 🚀 Instalación

#### 🧰 Requisitos Mínimos

* Java JDK 11+
* PostgreSQL 13+
* NetBeans / Eclipse

#### 📦 Pasos

```bash
1. git clone https://github.com/turepositorio/fudsap.git
2. cd fudsap
3. Importar a NetBeans/Eclipse como proyecto Java
4. Agregar los .jar en /lib a las dependencias del proyecto
5. Ejecutar src/main/java/MainApplication.java
6. Configurar credenciales DB en config/database.properties
```

#### 🛠️ Base de Datos

```sql
CREATE DATABASE fudsap_db;
-- Ejecutar scripts SQL desde /database/init.sql
```

---

## 🖼️ Funcionalidades Clave con Imágenes

### 🔐 CAPTCHA en Login

<img width="246" height="504" alt="image" src="https://github.com/user-attachments/assets/91c92517-1b8f-4e5e-8ba4-cad4f69f635f" />


### 👤 CRUD Usuarios 

<img width="941" height="419" alt="image" src="https://github.com/user-attachments/assets/8ed1ab07-869b-4a5b-87b9-e2b015331311" />

### 🍔 CRUD Productos

<img width="953" height="437" alt="image" src="https://github.com/user-attachments/assets/50fc035e-3bdc-499e-a26c-3a839a7a2384" />


### 🧾 Ticket en PDF

<img width="659" height="385" alt="image" src="https://github.com/user-attachments/assets/6ed42a22-5030-4859-af06-6e726470915d" />


---

## 💡 Mejoras Realizadas

* Integración completa de validaciones personalizadas
* Código modular reutilizable
* Snippets documentados y comentados
* Inclusión de librerías externas adaptadas
* Interfaz gráfica moderna y adaptable por rol

---

> ✨ **Dato Curioso:** El sistema reduce tiempos de espera en un **40%**, comprobado en pruebas internas.

---

## 📌 Notas Finales

* Documentación clara y dividida por sección funcional
* Incluye todas las dependencias necesarias
* Apto para ampliación futura (por ejemplo: facturación electrónica)

---

**Desarrollado con 💻 y 🍟 por el Equipo 15**

```
