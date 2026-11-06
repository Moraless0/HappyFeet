# Explicación Detallada del Último Commit

## Información del Commit
- **Commit**: `3b9d9d6` - "✨ feat: menus por consola, tablas formateadas y documentacion"
- **Fecha**: 2026-11-03 09:20:00
- **Archivos modificados**: 8 archivos
- **Líneas agregadas**: 1165 líneas

## Archivos del Commit

1. README.md (254 líneas) - Documentación del proyecto
2. Main.java (15 líneas) - Punto de entrada
3. MenuActividades.java (68 líneas) - Menú de actividades especiales
4. MenuCitas.java (190 líneas) - Menú de citas
5. MenuFacturacion.java (158 líneas) - Menú de facturación
6. MenuInventario.java (185 líneas) - Menú de inventario
7. MenuPacientes.java (230 líneas) - Menú de pacientes
8. MenuPrincipal.java (65 líneas) - Menú principal (Facade)

---

## Explicación Detallada de cada Archivo

### 1. Main.java - Punto de Entrada

```java
package com.happyfeet;

import com.happyfeet.view.MenuPrincipal;

public class Main {
    public static void main(String[] args) {
        MenuPrincipal menu = new MenuPrincipal();
        menu.iniciar();
    }
}
```

**Línea por línea:**
- `package com.happyfeet;` - Declara el paquete al que pertenece la clase (organización de código)
- `import com.happyfeet.view.MenuPrincipal;` - Importa la clase MenuPrincipal para poder usarla
- `public class Main {` - Declara la clase principal del programa
- `public static void main(String[] args) {` - Método principal que Java ejecuta al iniciar el programa. Es estático porque se puede llamar sin crear una instancia de la clase.
- `MenuPrincipal menu = new MenuPrincipal();` - Crea una instancia del Menú Principal. Esto inicializa todos los submenús.
- `menu.iniciar();` - Llama al método iniciar() del menú principal, que muestra el menú y procesa las opciones del usuario.
- `}` - Cierra la clase.

**Propósito**: Es el punto de entrada de toda aplicación Java. Cuando ejecutas el programa, Java busca este método main y lo ejecuta.

---

### 2. MenuPrincipal.java - Patrón Facade

```java
package com.happyfeet.view;

import com.happyfeet.util.ValidadorEntrada;

public class MenuPrincipal {
    private MenuPacientes menuPacientes;
    private MenuInventario menuInventario;
    private MenuCitas menuCitas;
    private MenuFacturacion menuFacturacion;
    private MenuActividades menuActividades;

    public MenuPrincipal() {
        this.menuPacientes = new MenuPacientes();
        this.menuInventario = new MenuInventario();
        this.menuCitas = new MenuCitas();
        this.menuFacturacion = new MenuFacturacion();
        this.menuActividades = new MenuActividades();
    }

    public void iniciar() {
        System.out.println("\n========================================");
        System.out.println("         VETERINARIA HAPPY FEET");
        System.out.println("      Sistema de Gestión Integral");
        System.out.println("========================================");

        int opcion;
        do {
            System.out.println("\n========================================");
            System.out.println("             MENÚ PRINCIPAL");
            System.out.println("========================================");
            System.out.println("  1. Pacientes (Dueños y Mascotas)");
            System.out.println("  2. Inventario");
            System.out.println("  3. Citas");
            System.out.println("  4. Facturación");
            System.out.println("  5. Actividades Especiales");
            System.out.println("  0. Salir");
            System.out.println("========================================");

            opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 5);

            switch (opcion) {
                case 1:
                    menuPacientes.mostrarMenu();
                    break;
                case 2:
                    menuInventario.mostrarMenu();
                    break;
                case 3:
                    menuCitas.mostrarMenu();
                    break;
                case 4:
                    menuFacturacion.mostrarMenu();
                    break;
                case 5:
                    menuActividades.mostrarMenu();
                    break;
                case 0:
                    System.out.println("\n========================================");
                    System.out.println(" ¡Gracias por usar el sistema Happy Feet!");
                    System.out.println("========================================\n");
                    break;
            }
        } while (opcion != 0);
    }
}
```

**Línea por línea:**

**Campos de la clase:**
- `private MenuPacientes menuPacientes;` - Variable que almacena una instancia del menú de pacientes
- `private MenuInventario menuInventario;` - Variable que almacena una instancia del menú de inventario
- `private MenuCitas menuCitas;` - Variable que almacena una instancia del menú de citas
- `private MenuFacturacion menuFacturacion;` - Variable que almacena una instancia del menú de facturación
- `private MenuActividades menuActividades;` - Variable que almacena una instancia del menú de actividades

**Constructor:**
- `public MenuPrincipal() {` - Constructor que se ejecuta cuando se crea una instancia
- `this.menuPacientes = new MenuPacientes();` - Crea una instancia del menú de pacientes. Esto inicializa todos los servicios que ese menú necesita.
- `this.menuInventario = new MenuInventario();` - Crea una instancia del menú de inventario
- `this.menuCitas = new MenuCitas();` - Crea una instancia del menú de citas
- `this.menuFacturacion = new MenuFacturacion();` - Crea una instancia del menú de facturación
- `this.menuActividades = new MenuActividades();` - Crea una instancia del menú de actividades
- `}` - Cierra el constructor

**Método iniciar():**
- `public void iniciar() {` - Método principal que inicia la aplicación
- `System.out.println("\n========================================");` - Imprime una línea de separador (el \n es un salto de línea)
- `System.out.println("         VETERINARIA HAPPY FEET");` - Imprime el nombre de la veterinaria
- `System.out.println("      Sistema de Gestión Integral");` - Imprime el subtítulo del sistema
- `System.out.println("========================================");` - Imprime otra línea de separador

- `int opcion;` - Declara una variable para almacenar la opción seleccionada por el usuario

- `do {` - Inicia un bucle do-while que se ejecuta al menos una vez y continúa hasta que la condición sea falsa

**Dentro del bucle:**
- `System.out.println("\n========================================");` - Imprime separador del menú
- `System.out.println("             MENÚ PRINCIPAL");` - Imprime el título del menú
- `System.out.println("========================================");` - Imprime separador
- `System.out.println("  1. Pacientes (Dueños y Mascotas)");` - Imprime la opción 1
- `System.out.println("  2. Inventario");` - Imprime la opción 2
- `System.out.println("  3. Citas");` - Imprime la opción 3
- `System.out.println("  4. Facturación");` - Imprime la opción 4
- `System.out.println("  5. Actividades Especiales");` - Imprime la opción 5
- `System.out.println("  0. Salir");` - Imprime la opción 0 para salir
- `System.out.println("========================================");` - Imprime separador

- `opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 5);` - Lee un número entero del usuario. El método leerInt valida que sea un número y que esté entre 0 y 5.

**Switch statement:**
- `switch (opcion) {` - Evalúa la opción seleccionada
- `case 1: menuPacientes.mostrarMenu(); break;` - Si selecciona 1, muestra el menú de pacientes y luego sale del switch (break)
- `case 2: menuInventario.mostrarMenu(); break;` - Si selecciona 2, muestra el menú de inventario
- `case 3: menuCitas.mostrarMenu(); break;` - Si selecciona 3, muestra el menú de citas
- `case 4: menuFacturacion.mostrarMenu(); break;` - Si selecciona 4, muestra el menú de facturación
- `case 5: menuActividades.mostrarMenu(); break;` - Si selecciona 5, muestra el menú de actividades
- `case 0: System.out.println(...); break;` - Si selecciona 0, imprime mensaje de despedida y sale del switch

- `} while (opcion != 0);` - La condición del bucle: continúa mientras la opción no sea 0. Cuando el usuario selecciona 0, el bucle termina.

**Propósito**: Este es el patrón Facade. Proporciona una interfaz simplificada a un subsistema complejo de menús. El usuario solo interactúa con este menú principal, que a su vez coordina todos los otros menús.

---

### 3. MenuPacientes.java - Menú de Gestión de Pacientes

Este archivo es el más grande (230 líneas) y maneja la gestión de dueños y mascotas. Voy a explicarlo por secciones:

#### Campos y Constructor:
```java
public class MenuPacientes {
    private DuenoService duenoService;
    private MascotaService mascotaService;

    public MenuPacientes() {
        this.duenoService = new DuenoService();
        this.mascotaService = new MascotaService();
    }
```

- `private DuenoService duenoService;` - Servicio para operaciones de dueños
- `private MascotaService mascotaService;` - Servicio para operaciones de mascotas
- `public MenuPacientes() {` - Constructor que crea las instancias de los servicios
- `this.duenoService = new DuenoService();` - Crea el servicio de dueños (que internamente crea un DuenoDAO)
- `this.mascotaService = new MascotaService();` - Crea el servicio de mascotas (que internamente crea un MascotaDAO)

**Propósito**: Los servicios encapsulan la lógica de negocio. El menú solo se encarga de la interfaz de usuario.

#### Método mostrarMenu():
```java
public void mostrarMenu() {
    int opcion;
    do {
        System.out.println("\n========================================");
        System.out.println("          MÓDULO DE PACIENTES");
        System.out.println("========================================");
        System.out.println("  1. Registrar Dueño");
        System.out.println("  2. Listar Dueños");
        System.out.println("  3. Buscar Dueño por Documento");
        System.out.println("  4. Registrar Mascota");
        System.out.println("  5. Listar Mascotas de un Dueño");
        System.out.println("  6. Transferir Mascota");
        System.out.println("  0. Volver al Menú Principal");
        System.out.println("========================================");

        opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 6);

        switch (opcion) {
            case 1: registrarDueno(); break;
            case 2: listarDuenos(); break;
            case 3: buscarDuenoPorDocumento(); break;
            case 4: registrarMascota(); break;
            case 5: listarMascotasPorDueno(); break;
            case 6: transferirMascota(); break;
            case 0: System.out.println("\nVolviendo al menú principal..."); break;
        }

        if (opcion != 0) {
            ValidadorEntrada.pausar();
        }
    } while (opcion != 0);
}
```

- `public void mostrarMenu() {` - Método que muestra el menú de pacientes
- `int opcion;` - Variable para la opción seleccionada
- `do {` - Bucle que se repite hasta que el usuario seleccione 0
- Imprime el menú con las opciones disponibles
- `opcion = ValidadorEntrada.leerInt("Seleccione una opción: ", 0, 6);` - Lee la opción (de 0 a 6)
- `switch (opcion) {` - Evalúa la opción
- Cada `case` llama al método correspondiente:
  - `case 1: registrarDueno();` - Llama al método para registrar un nuevo dueño
  - `case 2: listarDuenos();` - Llama al método para listar todos los dueños
  - `case 3: buscarDuenoPorDocumento();` - Llama al método para buscar un dueño por su documento
  - `case 4: registrarMascota();` - Llama al método para registrar una nueva mascota
  - `case 5: listarMascotasPorDueno();` - Llama al método para listar mascotas de un dueño
  - `case 6: transferirMascota();` - Llama al método para transferir propiedad de una mascota
  - `case 0:` - Imprime mensaje de volver al menú principal
- `if (opcion != 0) { ValidadorEntrada.pausar(); }` - Si no es 0, pausa para que el usuario pueda leer el resultado antes de que se limpie la pantalla
- `} while (opcion != 0);` - Continúa el bucle hasta que el usuario seleccione 0

#### Método registrarDueno():
```java
private void registrarDueno() {
    System.out.println("\n----------------------------------------");
    System.out.println("         REGISTRAR NUEVO DUEÑO");
    System.out.println("----------------------------------------");
    String nombre = ValidadorEntrada.leerStringNoVacio("Nombre completo: ");
    String documento = ValidadorEntrada.leerStringNoVacio("Documento de identidad: ");
    String direccion = ValidadorEntrada.leerString("Dirección: ");
    String telefono = ValidadorEntrada.leerString("Teléfono: ");
    String email = ValidadorEntrada.leerStringNoVacio("Email: ");
    String contactoEmergencia = ValidadorEntrada.leerString("Contacto de emergencia: ");

    Dueno dueno = new Dueno(nombre, documento, email);
    dueno.setDireccion(direccion);
    dueno.setTelefono(telefono);
    dueno.setContactoEmergencia(contactoEmergencia);

    if (duenoService.registrarDueno(dueno)) {
        System.out.println("\n✅ Dueño registrado exitosamente.");
    } else {
        System.out.println("\n❌ No se pudo registrar el dueño.");
    }
}
```

- `private void registrarDueno() {` - Método privado (solo accesible dentro de la clase) para registrar un dueño
- Imprime separadores y título
- `String nombre = ValidadorEntrada.leerStringNoVacio("Nombre completo: ");` - Lee el nombre del dueño. El método leerStringNoVacio valida que no esté vacío.
- `String documento = ValidadorEntrada.leerStringNoVacio("Documento de identidad: ");` - Lee el documento de identidad
- `String direccion = ValidadorEntrada.leerString("Dirección: ");` - Lee la dirección (puede estar vacío)
- `String telefono = ValidadorEntrada.leerString("Teléfono: ");` - Lee el teléfono (puede estar vacío)
- `String email = ValidadorEntrada.leerStringNoVacio("Email: ");` - Lee el email (no puede estar vacío)
- `String contactoEmergencia = ValidadorEntrada.leerString("Contacto de emergencia: ");` - Lee el contacto de emergencia (puede estar vacío)
- `Dueno dueno = new Dueno(nombre, documento, email);` - Crea un nuevo objeto Dueno con los datos básicos (nombre, documento, email)
- `dueno.setDireccion(direccion);` - Establece la dirección en el objeto
- `dueno.setTelefono(telefono);` - Establece el teléfono
- `dueno.setContactoEmergencia(contactoEmergencia);` - Establece el contacto de emergencia
- `if (duenoService.registrarDueno(dueno)) {` - Llama al servicio para registrar el dueño en la base de datos. Si retorna true, el registro fue exitoso.
- `System.out.println("\n✅ Dueño registrado exitosamente.");` - Imprime mensaje de éxito con emoji ✅
- `} else { System.out.println("\n❌ No se pudo registrar el dueño."); }` - Si falla, imprime mensaje de error con emoji ❌

#### Método listarDuenos():
```java
private void listarDuenos() {
    System.out.println("\n---------------------------------------------------------------------------------------------------------");
    System.out.println("                                         LISTADO DE DUEÑOS");
    List<Dueno> duenos = duenoService.listarDuenos();
    if (duenos.isEmpty()) {
        System.out.println("---------------------------------------------------------------------------------------------------------");
        System.out.println("No hay dueños registrados actualmente.");
        System.out.println("---------------------------------------------------------------------------------------------------------");
        return;
    }

    String separador = "+------+------------------------+--------------+--------------+--------------------------+-----------------+";
    System.out.println(separador);
    System.out.printf("| %-4s | %-22s | %-12s | %-12s | %-24s | %-15s |%n",
            "ID", "Nombre", "Documento", "Teléfono", "Email", "Emergencia");
    System.out.println(separador);
    for (Dueno d : duenos) {
        System.out.printf("| %-4d | %-22s | %-12s | %-12s | %-24s | %-15s |%n",
                d.getId(),
                ValidadorEntrada.truncar(d.getNombreCompleto(), 22),
                ValidadorEntrada.truncar(d.getDocumentoIdentidad(), 12),
                ValidadorEntrada.truncar(d.getTelefono(), 12),
                ValidadorEntrada.truncar(d.getEmail(), 24),
                ValidadorEntrada.truncar(d.getContactoEmergencia(), 15));
    }
    System.out.println(separador);
    System.out.println("Total: " + duenos.size() + " dueño(s) registrado(s).");
}
```

- `private void listarDuenos() {` - Método para listar todos los dueños
- `List<Dueno> duenos = duenoService.listarDuenos();` - Obtiene la lista de dueños del servicio (que consulta la base de datos)
- `if (duenos.isEmpty()) {` - Si la lista está vacía, muestra mensaje de "no hay dueños" y retorna
- `String separador = "+------+..."` - Crea una cadena que será el separador de la tabla (líneas horizontales)
- `System.out.println(separador);` - Imprime el separador superior
- `System.out.printf("| %-4s | %-22s | ...", "ID", "Nombre", ...);` - Imprime el encabezado de la tabla usando printf con formato. El `%n` es salto de línea, `%s` es string, `%-22s` significa string alineado a la izquierda con 22 caracteres.
- `System.out.println(separador);` - Imprime el separador después del encabezado
- `for (Dueno d : duenos) {` - Bucle for-each que itera sobre cada dueño en la lista
- `System.out.printf("| %-4d | %-22s | ...", d.getId(), ...);` - Imprime cada fila de la tabla con los datos del dueño. El `%d` es para números, `%s` es para strings.
- `ValidadorEntrada.truncar(d.getNombreCompleto(), 22)` - Trunca el nombre a 22 caracteres para que no rompa el formato de la tabla
- `}` - Cierra el bucle
- `System.out.println(separador);` - Imprime el separador inferior
- `System.out.println("Total: " + duenos.size() + " dueño(s) registrado(s).");` - Imprime el total de dueños

#### Método buscarDuenoPorDocumento():
```java
private void buscarDuenoPorDocumento() {
    System.out.println("\n----------------------------------------");
    System.out.println("          BUSCAR DUEÑO POR DOC");
    System.out.println("----------------------------------------");
    String documento = ValidadorEntrada.leerStringNoVacio("Documento de identidad: ");
    Dueno dueno = duenoService.buscarPorDocumento(documento);
    if (dueno != null) {
        System.out.println("\n========================================");
        System.out.println("            DATOS DEL DUEÑO");
        System.out.println("========================================");
        System.out.printf("  ID:                  %d%n", dueno.getId());
        System.out.printf("  Nombre completo:     %s%n", dueno.getNombreCompleto());
        System.out.printf("  Documento:           %s%n", dueno.getDocumentoIdentidad());
        System.out.printf("  Dirección:           %s%n", ValidadorEntrada.texto(dueno.getDireccion()));
        System.out.printf("  Teléfono:            %s%n", ValidadorEntrada.texto(dueno.getTelefono()));
        System.out.printf("  Email:               %s%n", dueno.getEmail());
        System.out.printf("  Contacto Emergencia: %s%n", ValidadorEntrada.texto(dueno.getContactoEmergencia()));
        System.out.printf("  Estado:              %s%n", dueno.isActivo() ? "Activo" : "Inactivo");
        System.out.println("========================================");
    } else {
        System.out.println("\n❌ No se encontró ningún dueño con el documento: " + documento);
    }
}
```

- `private void buscarDuenoPorDocumento() {` - Método para buscar un dueño por su documento
- `String documento = ValidadorEntrada.leerStringNoVacio("Documento de identidad: ");` - Lee el documento del usuario
- `Dueno dueno = duenoService.buscarPorDocumento(documento);` - Busca el dueño en la base de datos por documento
- `if (dueno != null) {` - Si se encontró el dueño
- Imprime los datos del dueño usando `System.out.printf()` con formato específico
- `ValidadorEntrada.texto(dueno.getDireccion())` - Convierte null a "-" para que se vea mejor en la tabla
- `dueno.isActivo() ? "Activo" : "Inactivo"` - Operador ternario: si es activo muestra "Activo", si no "Inactivo"
- `} else { System.out.println("\n❌ No se encontró..."); }` - Si no se encontró, imprime mensaje de error

#### Método registrarMascota():
```java
private void registrarMascota() {
    System.out.println("\n----------------------------------------");
    System.out.println("        REGISTRAR NUEVA MASCOTA");
    System.out.println("----------------------------------------");
    int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");
    Dueno dueno = duenoService.buscarDueno(duenoId);
    if (dueno == null) {
        System.out.println("❌ Dueño no encontrado.");
        return;
    }

    String nombre = ValidadorEntrada.leerStringNoVacio("Nombre de la mascota: ");
    int razaId = ValidadorEntrada.leerInt("ID de la raza: ");
    String sexo = ValidadorEntrada.leerStringNoVacio("Sexo (Macho/Hembra): ");
    System.out.print("Fecha de nacimiento (YYYY-MM-DD) o Enter para omitir: ");
    String fechaStr = ValidadorEntrada.leerString("");
    System.out.print("Peso actual (kg) o Enter para omitir: ");
    String pesoStr = ValidadorEntrada.leerString("");

    Mascota mascota = new Mascota(duenoId, nombre, razaId, sexo);
    if (!fechaStr.isEmpty()) {
        try {
            mascota.setFechaNacimiento(java.time.LocalDate.parse(fechaStr.trim()));
        } catch (Exception e) {
            System.out.println("⚠️ Fecha inválida, se guardará sin fecha de nacimiento.");
        }
    }
    if (!pesoStr.isEmpty()) {
        try {
            mascota.setPesoActual(new java.math.BigDecimal(pesoStr.trim()));
        } catch (Exception e) {
            System.out.println("⚠️ Peso inválido, se omitirá.");
        }
    }

    if (mascotaService.registrarMascota(mascota)) {
        System.out.println("\n✅ Mascota registrada exitosamente.");
    } else {
        System.out.println("\n❌ No se pudo registrar la mascota.");
    }
}
```

- `private void registrarMascota() {` - Método para registrar una nueva mascota
- `int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");` - Lee el ID del dueño
- `Dueno dueno = duenoService.buscarDueno(duenoId);` - Busca el dueño por ID para validar que existe
- `if (dueno == null) { System.out.println("❌ Dueño no encontrado."); return; }` - Si no existe el dueño, muestra error y retorna (no continúa)
- Lee los datos de la mascota: nombre, razaId, sexo
- `System.out.print("Fecha de nacimiento...");` - Usa print (sin salto de línea) para que el usuario escriba en la misma línea
- `String fechaStr = ValidadorEntrada.leerString("");` - Lee la fecha como string (puede estar vacío)
- `String pesoStr = ValidadorEntrada.leerString("");` - Lee el peso como string (puede estar vacío)
- `Mascota mascota = new Mascota(duenoId, nombre, razaId, sexo);` - Crea el objeto mascota con los datos básicos
- `if (!fechaStr.isEmpty()) {` - Si la fecha no está vacía, intenta parsearla
- `try { mascota.setFechaNacimiento(java.time.LocalDate.parse(fechaStr.trim())); }` - Intenta convertir el string a LocalDate. El método trim() elimina espacios.
- `catch (Exception e) { System.out.println("⚠️ Fecha inválida..."); }` - Si falla el parseo, muestra advertencia pero continúa
- `if (!pesoStr.isEmpty()) {` - Similar para el peso
- `try { mascota.setPesoActual(new java.math.BigDecimal(pesoStr.trim())); }` - Convierte el string a BigDecimal (para precisión decimal)
- `if (mascotaService.registrarMascota(mascota)) {` - Registra la mascota en la base de datos
- Imprime mensaje de éxito o error

#### Método listarMascotasPorDueno():
```java
private void listarMascotasPorDueno() {
    System.out.println("\n----------------------------------------");
    System.out.println("         MASCOTAS POR DUEÑO");
    System.out.println("----------------------------------------");
    int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");
    List<Mascota> mascotas = mascotaService.listarMascotasPorDueno(duenoId);
    if (mascotas.isEmpty()) {
        System.out.println("\nEl dueño no tiene mascotas registradas.");
        return;
    }

    String separador = "+------+--------------------+---------+---------+-----------+--------------+----------------------+";
    System.out.println("\n" + separador);
    System.out.printf("| %-4s | %-18s | %-7s | %-7s | %-9s | %-12s | %-20s |%n",
            "ID", "Nombre", "Raza", "Sexo", "Peso", "F. Nacim.", "Alergias");
    System.out.println(separador);
    for (Mascota m : mascotas) {
        String peso = m.getPesoActual() != null ? m.getPesoActual() + " kg" : "-";
        String nac = m.getFechaNacimiento() != null ? m.getFechaNacimiento().toString() : "-";
        System.out.printf("| %-4d | %-18s | %-7d | %-7s | %-9s | %-12s | %-20s |%n",
                m.getId(),
                ValidadorEntrada.truncar(m.getNombre(), 18),
                m.getRazaId(),
                ValidadorEntrada.truncar(m.getSexo(), 7),
                ValidadorEntrada.truncar(peso, 9),
                nac,
                ValidadorEntrada.truncar(m.getAlergias(), 20));
    }
    System.out.println(separador);
    System.out.println("Total: " + mascotas.size() + " mascota(s).");
}
```

- `private void listarMascotasPorDueno() {` - Método para listar mascotas de un dueño específico
- `int duenoId = ValidadorEntrada.leerInt("ID del dueño: ");` - Lee el ID del dueño
- `List<Mascota> mascotas = mascotaService.listarMascotasPorDueno(duenoId);` - Obtiene la lista de mascotas del dueño
- `if (mascotas.isEmpty()) {` - Si no hay mascotas, muestra mensaje y retorna
- Similar al listado de dueños, imprime una tabla formateada
- `String peso = m.getPesoActual() != null ? m.getPesoActual() + " kg" : "-";` - Si el peso no es null, lo muestra con "kg", si es null muestra "-"
- `String nac = m.getFechaNacimiento() != null ? m.getFechaNacimiento().toString() : "-";` - Similar para la fecha de nacimiento
- Imprime cada mascota en formato de tabla

#### Método transferirMascota():
```java
private void transferirMascota() {
    System.out.println("\n----------------------------------------");
    System.out.println("         TRANSFERIR PROPIEDAD");
    System.out.println("----------------------------------------");
    int mascotaId = ValidadorEntrada.leerInt("ID de la mascota: ");
    int nuevoDuenoId = ValidadorEntrada.leerInt("ID del nuevo dueño: ");

    if (mascotaService.transferirPropiedad(mascotaId, nuevoDuenoId)) {
        System.out.println("\n✅ Propiedad transferida exitosamente.");
    } else {
        System.out.println("\n❌ No se pudo transferir la propiedad. Verifique los IDs ingresados.");
    }
}
```

- `private void transferirMascota() {` - Método para transferir la propiedad de una mascota a otro dueño
- Lee el ID de la mascota y el ID del nuevo dueño
- `if (mascotaService.transferirPropiedad(mascotaId, nuevoDuenoId)) {` - Llama al servicio para actualizar la base de datos
- Imprime mensaje de éxito o error

---

### 4. Otros Menús (MenuInventario, MenuCitas, MenuFacturacion, MenuActividades)

Estos archivos siguen el mismo patrón que MenuPacientes:

**Estructura común:**
1. **Campos**: Instancias de los servicios necesarios
2. **Constructor**: Inicializa los servicios
3. **mostrarMenu()**: Bucle principal que muestra opciones y llama a métodos privados
4. **Métodos privados**: Cada uno maneja una funcionalidad específica (registrar, listar, buscar, etc.)

**Diferencias específicas:**
- **MenuInventario**: Maneja productos, stock, vencimientos. Usa Streams y Lambdas para reportes.
- **MenuCitas**: Maneja citas médicas. Incluye validación de fechas.
- **MenuFacturacion**: Maneja facturas, pagos, generación de archivos .txt. Integra el patrón Strategy para pagos.
- **MenuActividades**: Maneja jornadas de adopción, vacunación, club de puntos.

---

### 5. README.md - Documentación

El README.md contiene:
- **Título y descripción**: Explica qué es el sistema y el problema que resuelve
- **Tecnologías**: Java 17, Maven, MySQL, JDBC
- **Modelo de base de datos**: Describe las tablas y relaciones
- **Instrucciones de instalación**: Pasos para configurar y ejecutar
- **Guía de uso**: Explica cada módulo del sistema
- **Patrones de diseño**: Explica dónde y cómo se aplican los 5 patrones
- **Manejo de errores**: Explica la tolerancia a fallos implementada

---

## Conceptos Clave para Sustentación

### 1. Arquitectura MVC
- **Model**: Entidades (Dueno, Mascota, etc.) y enums - Representan los datos
- **View**: Menús (MenuPacientes, MenuInventario, etc.) - Interfaz de usuario
- **Controller**: Servicios (DuenoService, MascotaService, etc.) - Lógica de negocio
- **Repository/DAO**: Acceso a datos (DuenoDAO, MascotaDAO, etc.) - Consultas SQL

### 2. Patrones de Diseño
- **Singleton**: `ConexionBD` - Una sola instancia de conexión a BD
- **DAO**: `DuenoDAO`, `MascotaDAO`, etc. - Separación de acceso a datos
- **Factory Method**: `GeneradorComprobante` - Crea diferentes tipos de comprobantes
- **Strategy**: `EstrategiaPago`, `PagoEfectivo`, `PagoTarjeta` - Diferentes algoritmos de pago
- **Facade**: `MenuPrincipal` - Simplifica la interfaz de múltiples menús

### 3. Manejo de Errores
- `ValidadorEntrada` - Valida todas las entradas del usuario
- `LoggerUtil` - Registra errores en `errores.log`
- Try-catch en todos los métodos que interactúan con la base de datos

### 4. Programación Funcional
- `InventarioService.filtrarPorTipoConStream()` usa Streams y Lambdas:
```java
return todos.stream()
        .filter(p -> p.getProductoTipoId() == tipoId)
        .collect(Collectors.toList());
```

---

## Flujo de Ejecución Típico

1. Usuario ejecuta `Main.main()`
2. Se crea instancia de `MenuPrincipal`
3. Se llama a `menuPrincipal.iniciar()`
4. Se muestra el menú principal
5. Usuario selecciona una opción (ej: 1 para Pacientes)
6. Se llama a `menuPacientes.mostrarMenu()`
7. Se muestra el menú de pacientes
8. Usuario selecciona una acción (ej: 1 para Registrar Dueño)
9. Se llama a `registrarDueno()`
10. Se leen los datos del usuario con `ValidadorEntrada`
11. Se crea un objeto `Dueno`
12. Se llama a `duenoService.registrarDueno(dueno)`
13. El servicio llama a `duenoDAO.crear(dueno)`
14. El DAO ejecuta la consulta SQL INSERT
15. Se retorna el resultado al servicio
16. Se retorna el resultado al menú
17. Se muestra mensaje de éxito o error
18. Se pausa para que el usuario pueda leer
19. Se vuelve al menú de pacientes
20. El usuario puede continuar o volver al menú principal

---

## Consejos para la Sustentación

1. **Explica la arquitectura MVC**: Muestra cómo se separan las responsabilidades
2. **Demuestra los patrones**: Señala dónde está cada patrón y por qué se usa
3. **Muestra el manejo de errores**: Explica cómo se previenen los crashes
4. **Demuestra la programación funcional**: Muestra el uso de Streams en InventarioService
5. **Explica el flujo de datos**: Desde la entrada del usuario hasta la base de datos y viceversa
6. **Muestra la documentación**: El README.md está bien estructurado para un proyecto universitario

---

*Documento generado para ayudar en la sustentación del proyecto final de Java - Happy Feet*
