# Sistema de Gestión Integral para Veterinaria "Happy Feet"

## Descripción del Problema Administrativo

La clínica veterinaria "Happy Feet" ha sido un pilar en su comunidad durante años, conocida por su trato compasivo y su excelente atención médica. Sin embargo, su éxito ha traído consigo un desafío abrumador: la gestión de sus operaciones. El sistema actual, una mezcla de fichas de papel, agendas manuales y hojas de cálculo, está colapsando. Los problemas diarios incluyen:

- **Historiales Clínicos Incompletos**: Encontrar el historial de vacunas o las alergias de una mascota durante una emergencia es una carrera contra el tiempo revisando archivadores desorganizados.
- **Fugas de Inventario**: No hay un control en tiempo real del stock de medicamentos, resultando en desperdicios por vencimiento y escasez de insumos críticos.
- **Agendamiento Caótico**: Las citas se solapan o se registran incorrectamente, causando frustración tanto en el personal como en los clientes.
- **Facturación Lenta y Propensa a Errores**: Calcular manualmente los costos es un proceso lento que a menudo resulta en facturas incorrectas y pérdidas financieras.

"Happy Feet" necesita urgentemente un Sistema de Gestión Integral que centralice toda su operación, garantizando eficiencia, precisión y un mejor servicio al cliente.

## Tecnologías Utilizadas

- **Java 17**: Lenguaje de programación principal con características modernas
- **Maven 3.6+**: Gestión de dependencias y automatización de build
- **MySQL 8.0**: Sistema de gestión de base de datos relacional
- **JDBC**: Conexión nativa a la base de datos (sin ORM para mayor control)

## Modelo de Base de Datos y Diagrama E-R

El sistema utiliza una base de datos relacional MySQL con las siguientes tablas principales:

### Entidades Principales

- **duenos**: Información de los dueños de mascotas (id, nombre_completo, documento_identidad, direccion, telefono, email, contacto_emergencia, fecha_registro, activo)
- **mascotas**: Registro de pacientes con relación a dueños (id, dueno_id, nombre, especie, raza, fecha_nacimiento, peso, activo)
- **veterinarios**: Personal médico de la clínica (id, nombre, especialidad, licencia, activo)
- **citas**: Agendamiento de consultas (id, mascota_id, veterinario_id, fecha_hora, motivo, estado_id)
- **inventario**: Control de stock de productos (id, nombre, tipo_producto_id, cantidad_stock, fecha_vencimiento, precio)
- **facturas**: Registro de transacciones financieras (id, dueno_id, numero_factura, fecha_emision, subtotal, impuesto, descuento, total, metodo_pago, estado)
- **tipos_producto**: Categorización de productos (medicamentos, vacunas, insumos, accesorios)
- **estados_cita**: Estados del ciclo de vida de citas (programada, confirmada, en_proceso, finalizada, cancelada)

### Relaciones (Diagrama E-R Simplificado)

```
duenos (1) ----< (N) mascotas
mascotas (1) ----< (N) citas
veterinarios (1) ----< (N) citas
citas (1) ----< (N) facturas
tipos_producto (1) ----< (N) inventario
```

Para ver el esquema completo, consulte el archivo `database/schema.sql`.

## Instrucciones de Instalación y Ejecución

### Requisitos Previos

- JDK 17 o superior
- Maven 3.6 o superior
- MySQL Server 8.0 o superior

### Configuración de la Base de Datos

1. Crear el archivo `config.properties` en la raíz del proyecto basándose en `config.properties.example`:

```properties
db.host=localhost
db.port=3306
db.name=happy_feet_veterinaria
db.user=tu_usuario
db.password=tu_contraseña
```

2. Ejecutar el script de esquema para crear la base de datos:

```bash
mysql -u tu_usuario -p < database/schema.sql
```

3. Ejecutar el script de datos de prueba para poblar la base de datos:

```bash
mysql -u tu_usuario -p happy_feet_veterinaria < database/data.sql
```

### Ejecución del Proyecto

Compilar y ejecutar usando Maven:

```bash
mvn clean compile exec:java
```

O empaquetar y ejecutar el JAR:

```bash
mvn clean package
java -jar target/HappyFeet-1.0-SNAPSHOT.jar
```

## Patrones de Diseño Implementados

El proyecto implementa 5 patrones de diseño fundamentales para garantizar una arquitectura escalable y mantenible:

### 1. Singleton - `ConexionBD`

**Ubicación**: `com.happyfeet.repository.ConexionBD`

**Propósito**: Garantizar que exista una única instancia de conexión a la base de datos durante toda la ejecución de la aplicación, optimizando recursos y evitando conexiones duplicadas.

**Implementación**:
- Constructor privado para evitar instanciación externa
- Campo `instancia` declarado `volatile` para visibilidad entre hilos
- **Double-checked locking** — thread-safe sin sincronización en cada llamada

```java
// volatile garantiza visibilidad entre hilos
private static volatile ConexionBD instancia;

public static ConexionBD obtenerInstancia() {
    if (instancia == null) {
        synchronized (ConexionBD.class) {   // sincroniza solo la primera vez
            if (instancia == null) {
                instancia = new ConexionBD();
            }
        }
    }
    return instancia;
}
```

### 2. DAO (Data Access Object) - OperacionesDAO y DAOs Específicos

**Ubicación**: `com.happyfeet.repository.*`

**Propósito**: Separar completamente la lógica de acceso a datos de la lógica de negocio. Cada DAO encapsula todas las operaciones CRUD para una entidad específica.

**Implementación**:
- `OperacionesDAO`: Clase base con acceso protegido al Singleton `ConexionBD`
- `DuenoDAO`, `MascotaDAO`, `ProductoDAO`, `CitaDAO`, `FacturaDAO`: DAOs específicos con CRUD completo
- Uso de `PreparedStatement` para prevenir inyección SQL
- **Todas las sentencias SQL residen exclusivamente en esta capa** (ninguna en service o view)

### 3. Factory Method - `GeneradorComprobante`

**Ubicación**: `com.happyfeet.service.GeneradorComprobante`

**Propósito**: Crear objetos de diferentes tipos de comprobantes (facturas, recetas, contratos de adopción) sin especificar la clase concreta en el código cliente.

**Implementación**:
- Método estático `crearComprobante(String tipo, Object datos)` que retorna el tipo de comprobante apropiado
- Clases concretas: `FacturaComprobante`, `RecetaComprobante`, `AdopcionComprobante`
- Permite agregar nuevos tipos de comprobantes sin modificar el código existente (Open/Closed Principle)

```java
public static Comprobante crearComprobante(String tipo, Object datos) {
    switch (tipo.toLowerCase()) {
        case "factura": return new FacturaComprobante((Factura) datos);
        case "receta": return new RecetaComprobante();
        case "adopcion": return new AdopcionComprobante();
        default: throw new IllegalArgumentException("Tipo no válido");
    }
}
```

### 4. Strategy - `EstrategiaPago`

**Ubicación**: `com.happyfeet.service.EstrategiaPago` y estrategias concretas

**Propósito**: Encapsular diferentes algoritmos de cálculo de pagos (efectivo, tarjeta, transferencia, descuentos del club) y permitir que se intercambien en tiempo de ejecución.

**Implementación**:
- Interfaz `EstrategiaPago` con método `procesarPago(BigDecimal monto)`
- Estrategias concretas: `PagoEfectivo`, `PagoTarjeta`, `PagoClub`
- `PagoClub` aplica descuentos basados en puntos acumulados
- El cliente (`FacturaService`) selecciona la estrategia apropiada según el método de pago elegido

```java
switch (metodoPago) {
    case EFECTIVO: estrategia = new PagoEfectivo(); break;
    case TARJETA: estrategia = new PagoTarjeta(); break;
}
BigDecimal montoFinal = estrategia.procesarPago(factura.getTotal());
```

### 5. Facade - `MenuPrincipal`

**Ubicación**: `com.happyfeet.view.MenuPrincipal`

**Propósito**: Proporcionar una interfaz simplificada y unificada a un subsistema complejo de menús, ocultando la complejidad de las interacciones entre diferentes módulos.

**Implementación**:
- `MenuPrincipal` actúa como punto de entrada único del sistema
- Coordina todos los sub-menus (`MenuPacientes`, `MenuInventario`, `MenuCitas`, `MenuFacturacion`, `MenuActividades`)
- Simplifica la navegación para el usuario final
- Desacopla el cliente (`Main.java`) de la complejidad interna del sistema

```java
public class MenuPrincipal {
    private MenuPacientes menuPacientes;
    private MenuInventario menuInventario;
    // ... otros menús

    public void iniciar() {
        // Lógica simplificada de navegación
        switch (opcion) {
            case 1: menuPacientes.mostrarMenu(); break;
            case 2: menuInventario.mostrarMenu(); break;
            // ...
        }
    }
}
```

## Guía de Uso

Al iniciar la aplicación, verá el menú principal con las siguientes opciones:

1. **Pacientes**: Gestión de dueños y mascotas
   - Registrar dueños y mascotas
   - Buscar dueños por documento
   - Listar mascotas por dueño
   - Transferir propiedad de mascotas

2. **Inventario**: Control de productos
   - Listar todos los productos
   - Registrar nuevos productos
   - Agregar stock
   - Ver reportes de stock bajo (usando Streams y Lambdas)
   - Ver reportes de productos próximos a vencer

3. **Citas**: Gestión de consultas
   - Programar nuevas citas
   - Listar citas por mascota
   - Ver todas las citas programadas

4. **Facturación**: Gestión financiera
   - Generar facturas
   - Seleccionar método de pago
   - Aplicar descuentos del club
   - Generar archivo de factura en texto plano

5. **Actividades Especiales**:
   - Generar contratos de adopción
   - Generar recetas médicas

## Manejo de Errores y Tolerancia a Fallos

El sistema implementa un manejo robusto de errores ("Cero Crashes"):

- **Protección de Inputs**: Todas las lecturas de consola están protegidas por `ValidadorEntrada` contra `InputMismatchException`
- **Logging de Errores Técnicos**: Los errores de SQL y excepciones técnicas se registran silenciosamente en `errores.log` usando `LoggerUtil`
- **Mensajes Amigables**: La consola muestra mensajes claros para el usuario final, mientras los detalles técnicos van al log
- **No Terminación Inesperada**: El programa nunca se detiene por errores de entrada o fallos de base de datos; el ciclo del menú continúa ejecutándose

## Autor

**Abdiel Morales**

Proyecto final de Java - Sistema de Gestión Integral para Veterinaria "Happy Feet"
