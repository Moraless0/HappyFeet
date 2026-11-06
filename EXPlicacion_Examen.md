# Explicación Detallada del Commit "Se sube el examen"

## Información del Commit
- **Commit**: `ecf005b`
- **Mensaje**: "feat: :sparkles: Se sube el examen"
- **Fecha**: 2026-11-05 15:50:04
- **Archivos modificados**: 5 archivos
- **Líneas agregadas**: 108 líneas

## Archivos del Commit

1. README.md (4 líneas) - Documentación de la nueva funcionalidad
2. CitaDAO.java (15 líneas) - Nuevo método para consultar citas por fecha
3. CitaService.java (35 líneas) - Lógica de negocio para buscar primer horario disponible
4. MenuHorarios.java (47 líneas) - Nuevo menú para buscar horarios disponibles
5. MenuPrincipal.java (8 líneas) - Integración del nuevo menú en el menú principal

---

## Explicación Detallada de cada Archivo

### 1. README.md - Documentación

```markdown
6. **Buscar Primer Horario Disponible**:
   - Ingrese una fecha para consultar el primer espacio libre de la agenda
   - El sistema valida automáticamente el horario laboral (9:00 a 17:00) y la duración de las citas (30 minutos)
```

**Propósito**: Documenta la nueva funcionalidad agregada al sistema. Explica que el usuario puede ingresar una fecha para consultar el primer horario disponible, y que el sistema valida automáticamente el horario laboral y la duración de las citas.

---

### 2. CitaDAO.java - Acceso a Datos

**Cambios agregados:**

```java
import java.time.LocalDate;  // Agregado import
```

**Nuevo método:**

```java
public List<Cita> listarPorFecha(LocalDate fecha) throws SQLException {
    List<Cita> lista = new ArrayList<>();
    String sql = "SELECT * FROM citas WHERE DATE(fecha_hora) = ? AND estado_id <> 5 ORDER BY fecha_hora ASC";
    try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
        ps.setDate(1, Date.valueOf(fecha));
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(mapear(rs));
            }
        }
    }
    return lista;
}
```

**Línea por línea:**

- `import java.time.LocalDate;` - Importa la clase LocalDate para trabajar con fechas

- `public List<Cita> listarPorFecha(LocalDate fecha) throws SQLException {`
  - Método público que retorna una lista de citas para una fecha específica
  - `throws SQLException` indica que puede lanzar una excepción de SQL

- `List<Cita> lista = new ArrayList<>();` - Crea una lista vacía para almacenar las citas encontradas

- `String sql = "SELECT * FROM citas WHERE DATE(fecha_hora) = ? AND estado_id <> 5 ORDER BY fecha_hora ASC";`
  - Consulta SQL que:
    - `SELECT *` - Selecciona todos los campos de la tabla citas
    - `WHERE DATE(fecha_hora) = ?` - Filtra por la fecha (ignora la hora)
    - `AND estado_id <> 5` - Excluye las citas canceladas (estado 5)
    - `ORDER BY fecha_hora ASC` - Ordena por fecha/hora de forma ascendente

- `try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {`
  - Try-with-resources que:
    - Obtiene una conexión a la base de datos
    - Prepara el statement SQL con el parámetro `?`
    - Cierra automáticamente la conexión y el statement al terminar

- `ps.setDate(1, Date.valueOf(fecha));`
  - Establece el parámetro `?` con la fecha convertida a java.sql.Date
  - `Date.valueOf(fecha)` convierte LocalDate a Date

- `try (ResultSet rs = ps.executeQuery()) {`
  - Ejecuta la consulta y obtiene el resultado
  - Try-with-resources que cierra el ResultSet automáticamente

- `while (rs.next()) {`
  - Itera sobre cada fila del resultado

- `lista.add(mapear(rs));`
  - Convierte cada fila del ResultSet a un objeto Cita usando el método `mapear(rs)`
  - Agrega el objeto a la lista

- `return lista;` - Retorna la lista de citas encontradas

**Propósito**: Este método permite consultar todas las citas de una fecha específica, excluyendo las canceladas. Es esencial para el algoritmo de búsqueda de horarios disponibles.

---

### 3. CitaService.java - Lógica de Negocio

**Cambios agregados:**

```java
import java.time.LocalDateTime;  // Agregado import
import java.time.LocalTime;      // Agregado import
```

**Constantes agregadas:**

```java
private static final LocalTime HORA_APERTURA = LocalTime.of(9, 0);
private static final LocalTime HORA_CIERRE = LocalTime.of(17, 0);
private static final long DURACION_CITA_MINUTOS = 30;
```

**Nuevo método:**

```java
// busca el primer bloque de 30 minutos libre dentro del horario laboral (9:00 a 17:00)
public Optional<LocalDateTime> buscarPrimerHorarioDisponible(LocalDate fecha) {
    List<Cita> citas;
    try {
        citas = citaDAO.listarPorFecha(fecha);
    } catch (SQLException e) {
        LoggerUtil.registrarError("Error al buscar horarios disponibles. Fecha: " + fecha, e);
        return Optional.empty();
    }

    LocalDateTime cursor = LocalDateTime.of(fecha, HORA_APERTURA);
    LocalDateTime ultimoInicio = LocalDateTime.of(fecha, HORA_CIERRE).minusMinutes(DURACION_CITA_MINUTOS);

    while (!cursor.isAfter(ultimoInicio)) {
        LocalDateTime inicioSlot = cursor;
        LocalDateTime finSlot = cursor.plusMinutes(DURACION_CITA_MINUTOS);
        boolean ocupado = citas.stream().anyMatch(c -> {
            LocalDateTime inicioCita = c.getFechaHora();
            LocalDateTime finCita = inicioCita.plusMinutes(DURACION_CITA_MINUTOS);
            return inicioCita.isBefore(finSlot) && inicioSlot.isBefore(finCita);
        });
        if (!ocupado) {
            return Optional.of(cursor);
        }
        cursor = cursor.plusMinutes(DURACION_CITA_MINUTOS);
    }
    return Optional.empty();
}
```

**Línea por línea:**

**Imports:**
- `import java.time.LocalDateTime;` - Importa LocalDateTime para trabajar con fecha y hora
- `import java.time.LocalTime;` - Importa LocalTime para trabajar solo con hora

**Constantes:**
- `private static final LocalTime HORA_APERTURA = LocalTime.of(9, 0);`
  - Define la hora de apertura de la veterinaria (9:00 AM)
  - `LocalTime.of(9, 0)` crea una hora con 9 horas y 0 minutos
- `private static final LocalTime HORA_CIERRE = LocalTime.of(17, 0);`
  - Define la hora de cierre de la veterinaria (5:00 PM)
- `private static final long DURACION_CITA_MINUTOS = 30;`
  - Define la duración de cada cita en minutos (30 minutos)

**Método buscarPrimerHorarioDisponible():**

- `public Optional<LocalDateTime> buscarPrimerHorarioDisponible(LocalDate fecha) {`
  - Método público que retorna un Optional<LocalDateTime>
  - `Optional` es un contenedor que puede tener un valor o estar vacío (evita null)

- `List<Cita> citas;`
  - Declara una lista para almacenar las citas del día

- `try { citas = citaDAO.listarPorFecha(fecha); }`
  - Intenta obtener las citas del día usando el DAO
  - `citaDAO.listarPorFecha(fecha)` llama al método del DAO que consulta la base de datos

- `catch (SQLException e) {`
  - Si ocurre un error de SQL

- `LoggerUtil.registrarError("Error al buscar horarios disponibles. Fecha: " + fecha, e);`
  - Registra el error en errores.log

- `return Optional.empty();`
  - Retorna un Optional vacío (no hay horarios disponibles)

- `LocalDateTime cursor = LocalDateTime.of(fecha, HORA_APERTURA);`
  - Crea un cursor que empieza en la hora de apertura (9:00 AM) del día especificado
  - `LocalDateTime.of(fecha, HORA_APERTURA)` combina la fecha con la hora

- `LocalDateTime ultimoInicio = LocalDateTime.of(fecha, HORA_CIERRE).minusMinutes(DURACION_CITA_MINUTOS);`
  - Calcula la última hora posible para empezar una cita
  - `HORA_CIERRE` es 17:00, `minusMinutes(30)` resta 30 minutos, resultando en 16:30
  - Esto significa que la última cita puede empezar a las 16:30 para terminar a las 17:00

- `while (!cursor.isAfter(ultimoInicio)) {`
  - Bucle que continúa mientras el cursor no sea después de la última hora de inicio
  - `!cursor.isAfter(ultimoInicio)` significa "cursor <= ultimoInicio"

- `LocalDateTime inicioSlot = cursor;`
  - Guarda el inicio del slot actual

- `LocalDateTime finSlot = cursor.plusMinutes(DURACION_CITA_MINUTOS);`
  - Calcula el fin del slot actual (inicio + 30 minutos)

- `boolean ocupado = citas.stream().anyMatch(c -> {`
  - Usa Streams para verificar si hay alguna cita que se solape con el slot actual
  - `citas.stream()` convierte la lista en un Stream
  - `.anyMatch(...)` retorna true si alguna cita cumple la condición

- `LocalDateTime inicioCita = c.getFechaHora();`
  - Obtiene la hora de inicio de la cita actual

- `LocalDateTime finCita = inicioCita.plusMinutes(DURACION_CITA_MINUTOS);`
  - Calcula el fin de la cita actual (inicio + 30 minutos)

- `return inicioCita.isBefore(finSlot) && inicioSlot.isBefore(finCita);`
  - Verifica si hay solapamiento entre el slot actual y la cita
  - `inicioCita.isBefore(finSlot)` - La cita empieza antes de que termine el slot
  - `inicioSlot.isBefore(finCita)` - El slot empieza antes de que termine la cita
  - Si ambas condiciones son true, hay solapamiento

- `if (!ocupado) {`
  - Si el slot no está ocupado

- `return Optional.of(cursor);`
  - Retorna el cursor como el primer horario disponible
  - `Optional.of(cursor)` crea un Optional con el valor

- `cursor = cursor.plusMinutes(DURACION_CITA_MINUTOS);`
  - Avanza el cursor 30 minutos para probar el siguiente slot

- `return Optional.empty();`
  - Si no se encontró ningún horario disponible, retorna Optional vacío

**Propósito**: Este método implementa un algoritmo de búsqueda de primer horario disponible. Recorre los slots de 30 minutos desde las 9:00 AM hasta las 16:30 PM, verificando si cada slot está libre. Usa Streams y Lambdas para la verificación de solapamiento, demostrando programación funcional.

---

### 4. MenuHorarios.java - Nuevo Menú

```java
package com.happyfeet.view;

import com.happyfeet.service.CitaService;
import com.happyfeet.util.LoggerUtil;
import com.happyfeet.util.ValidadorEntrada;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class MenuHorarios {

    private final CitaService citaService;

    public MenuHorarios() {
        this.citaService = new CitaService();
    }

    public void mostrarMenu() {
        System.out.println("\n----------------------------------------");
        System.out.println("   BUSCAR PRIMER HORARIO DISPONIBLE");
        System.out.println("----------------------------------------");

        LocalDate fecha = pedirFecha();

        Optional<LocalDateTime> resultado = citaService.buscarPrimerHorarioDisponible(fecha);
        if (resultado.isPresent()) {
            System.out.println("\nEl primer horario disponible para el día " + fecha
                    + " es a las " + resultado.get().toLocalTime());
        } else {
            System.out.println("\nNo hay horarios disponibles para el día " + fecha
                    + ". La agenda está completa.");
        }
    }

    private LocalDate pedirFecha() {
        while (true) {
            String entrada = ValidadorEntrada.leerString("Ingrese la fecha (AAAA-MM-DD): ").trim();
            try {
                return LocalDate.parse(entrada);
            } catch (DateTimeParseException e) {
                LoggerUtil.mostrarError("Error: formato de fecha inválido. Use AAAA-MM-DD (ejemplo: 2026-11-05).");
            }
        }
    }
}
```

**Línea por línea:**

**Imports:**
- `import com.happyfeet.service.CitaService;` - Importa el servicio de citas
- `import com.happyfeet.util.LoggerUtil;` - Importa el utilitario de logging
- `import com.happyfeet.util.ValidadorEntrada;` - Importa el validador de entradas
- `import java.time.LocalDate;` - Importa LocalDate para fechas
- `import java.time.LocalDateTime;` - Importa LocalDateTime para fecha y hora
- `import java.time.format.DateTimeParseException;` - Importa la excepción para manejo de errores de parseo
- `import java.util.Optional;` - Importa Optional para manejar valores opcionales

**Campo:**
- `private final CitaService citaService;`
  - Variable que almacena el servicio de citas
  - `final` significa que no puede ser reasignado después de la inicialización

**Constructor:**
- `public MenuHorarios() {`
  - Constructor que inicializa el servicio
- `this.citaService = new CitaService();`
  - Crea una instancia del servicio de citas

**Método mostrarMenu():**
- `public void mostrarMenu() {`
  - Método principal que muestra el menú de horarios

- `System.out.println("\n----------------------------------------");`
  - Imprime separador
- `System.out.println("   BUSCAR PRIMER HORARIO DISPONIBLE");`
  - Imprime el título del menú
- `System.out.println("----------------------------------------");`
  - Imprime separador

- `LocalDate fecha = pedirFecha();`
  - Llama al método pedirFecha() para obtener la fecha del usuario

- `Optional<LocalDateTime> resultado = citaService.buscarPrimerHorarioDisponible(fecha);`
  - Llama al servicio para buscar el primer horario disponible
  - Retorna un Optional que puede tener un valor o estar vacío

- `if (resultado.isPresent()) {`
  - Si el Optional tiene un valor (se encontró un horario)

- `System.out.println("\nEl primer horario disponible para el día " + fecha + " es a las " + resultado.get().toLocalTime());`
  - Imprime el resultado:
    - `resultado.get()` obtiene el valor del Optional
    - `.toLocalTime()` convierte LocalDateTime a LocalTime (solo la hora)
    - `fecha` se imprime usando su método toString()

- `} else {`
  - Si el Optional está vacío (no hay horarios disponibles)

- `System.out.println("\nNo hay horarios disponibles para el día " + fecha + ". La agenda está completa.");`
  - Imprime mensaje de que no hay horarios disponibles

**Método pedirFecha():**
- `private LocalDate pedirFecha() {`
  - Método privado que pide la fecha al usuario

- `while (true) {`
  - Bucle infinito que continúa hasta que se obtenga una fecha válida

- `String entrada = ValidadorEntrada.leerString("Ingrese la fecha (AAAA-MM-DD): ").trim();`
  - Lee una string del usuario
  - `.trim()` elimina espacios en blanco al inicio y final

- `try {`
  - Intenta parsear la fecha

- `return LocalDate.parse(entrada);`
  - Convierte el string a LocalDate
  - `LocalDate.parse()` espera el formato ISO (AAAA-MM-DD)
  - Si es válido, retorna la fecha y sale del método

- `} catch (DateTimeParseException e) {`
  - Si el formato de fecha es inválido, captura la excepción

- `LoggerUtil.mostrarError("Error: formato de fecha inválido. Use AAAA-MM-DD (ejemplo: 2026-11-05).");`
  - Muestra un mensaje de error al usuario

- `}` - Cierra el catch
- `}` - Cierra el while (continúa el bucle si hubo error)

**Propósito**: Este menú proporciona una interfaz simple para que el usuario consulte el primer horario disponible de una fecha específica. Maneja errores de formato de fecha con un bucle que continúa hasta que el usuario ingrese una fecha válida.

---

### 5. MenuPrincipal.java - Integración del Nuevo Menú

**Cambios agregados:**

```java
private MenuHorarios menuHorarios;  // Nuevo campo
```

```java
this.menuHorarios = new MenuHorarios();  // En el constructor
```

```java
System.out.println("  6. Buscar Primer Horario Disponible");  // Nueva opción en el menú
```

```java
opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 6);  // Rango actualizado de 0-5 a 0-6
```

```java
case 6:
    menuHorarios.mostrarMenu();
    break;  // Nuevo case para la opción 6
```

**Línea por línea:**

- `private MenuHorarios menuHorarios;`
  - Agrega un nuevo campo para almacenar la instancia del menú de horarios

- `this.menuHorarios = new MenuHorarios();`
  - En el constructor, crea una instancia del nuevo menú
  - Esto inicializa el servicio de citas que el menú necesita

- `System.out.println("  6. Buscar Primer Horario Disponible");`
  - Agrega la opción 6 al menú principal

- `opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 6);`
  - Actualiza el rango de opciones válidas de 0-5 a 0-6
  - Ahora el usuario puede seleccionar hasta la opción 6

- `case 6:`
  - Agrega un nuevo caso para la opción 6
- `menuHorarios.mostrarMenu();`
  - Llama al método mostrarMenu() del menú de horarios
- `break;`
  - Sale del switch

**Propósito**: Integra el nuevo menú de horarios en el menú principal, permitiendo al usuario acceder a la funcionalidad de búsqueda de horarios desde la interfaz principal.

---

## Conceptos Clave para Sustentación

### 1. Algoritmo de Búsqueda de Primer Horario Disponible

El método `buscarPrimerHorarioDisponible()` implementa un algoritmo eficiente:

1. **Obtiene las citas del día**: Usa el DAO para consultar la base de datos
2. **Define el rango de búsqueda**: De 9:00 AM a 16:30 PM (última hora de inicio)
3. **Itera por slots de 30 minutos**: Recorre cada posible horario de inicio
4. **Verifica solapamiento**: Usa Streams para verificar si cada slot está libre
5. **Retorna el primer slot libre**: Si encuentra uno, lo retorna inmediatamente

**Complejidad temporal**: O(n*m) donde n es el número de slots (16 slots) y m es el número de citas del día

### 2. Uso de Streams y Lambdas

El método usa programación funcional:

```java
boolean ocupado = citas.stream().anyMatch(c -> {
    LocalDateTime inicioCita = c.getFechaHora();
    LocalDateTime finCita = inicioCita.plusMinutes(DURACION_CITA_MINUTOS);
    return inicioCita.isBefore(finSlot) && inicioSlot.isBefore(finCita);
});
```

- `citas.stream()` - Convierte la lista en un Stream
- `.anyMatch(...)` - Retorna true si alguna cita cumple la condición
- `c -> { ... }` - Lambda que define la condición de verificación

### 3. Uso de Optional

El método retorna `Optional<LocalDateTime>` en lugar de `LocalDateTime` o `null`:

- **Ventajas**:
  - Evita NullPointerException
  - Hace explícito que el resultado puede estar vacío
  - Permite encadenar operaciones funcionales

- **Uso**:
  - `Optional.of(cursor)` - Crea un Optional con valor
  - `Optional.empty()` - Crea un Optional vacío
  - `resultado.isPresent()` - Verifica si tiene valor
  - `resultado.get()` - Obtiene el valor (solo si isPresent() es true)

### 4. Separación de Responsabilidades (MVC)

- **View (MenuHorarios)**: Interfaz de usuario, manejo de entrada/salida
- **Service (CitaService)**: Lógica de negocio, algoritmo de búsqueda
- **Repository (CitaDAO)**: Acceso a datos, consultas SQL
- **Model (Cita)**: Entidad que representa una cita

### 5. Manejo de Errores

- **DAO**: Lanza SQLException si hay error de base de datos
- **Service**: Captura SQLException y registra en errores.log usando LoggerUtil
- **View**: Maneja DateTimeParseException con bucle de reintento

---

## Flujo de Ejecución

1. Usuario ejecuta `Main.main()`
2. Se crea instancia de `MenuPrincipal`
3. Se muestra el menú principal con 6 opciones
4. Usuario selecciona opción 6 (Buscar Primer Horario Disponible)
5. Se llama a `menuHorarios.mostrarMenu()`
6. Se pide la fecha al usuario con `pedirFecha()`
   - Si el formato es inválido, muestra error y vuelve a pedir
7. Se llama a `citaService.buscarPrimerHorarioDisponible(fecha)`
8. El servicio:
   - Obtiene las citas del día usando `citaDAO.listarPorFecha(fecha)`
   - Recorre los slots de 30 minutos desde 9:00 AM hasta 16:30 PM
   - Para cada slot, verifica si está libre usando Streams
   - Retorna el primer slot libre como Optional
9. El menú:
   - Si el Optional tiene valor, muestra el horario disponible
   - Si está vacío, muestra mensaje de agenda completa
10. El usuario puede volver al menú principal o salir

---

## Consejos para la Sustentación

1. **Explica el algoritmo**: Demuestra cómo el método recorre los slots y verifica solapamiento
2. **Muestra el uso de Streams**: Señala cómo se usa `stream().anyMatch()` para la verificación
3. **Explica Optional**: Muestra por qué se usa Optional en lugar de null
4. **Demuestra MVC**: Explica cómo se separan las responsabilidades entre las capas
5. **Muestra el manejo de errores**: Explica cómo se manejan los errores de fecha y SQL
6. **Demuestra la integración**: Muestra cómo el nuevo menú se integra en el menú principal

---

*Documento generado para ayudar en la sustentación del proyecto final de Java - Happy Feet*
