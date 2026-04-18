package ui;

import java.util.Scanner;
import model.cartelera.Funcion;
import service.CarteleraService;
import service.ClienteService;
import service.ReservaService;

/**
 * Clase que implementa la interfaz de usuario por consola para el sistema de cine.
 * Gestiona la interaccion con el usuario, muestra los menus y procesa las opciones
 * seleccionadas. Coordina los servicios de cartelera, reservas y clientes.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see CarteleraService
 * @see ReservaService
 * @see ClienteService
 */
public class MenuConsola {

    // ==================== ATRIBUTOS ====================
    
    /** Scanner para leer la entrada del usuario. */
    private Scanner scanner;
    
    /** Servicio para operaciones de cartelera. */
    private CarteleraService carteleraService;
    
    /** Servicio para operaciones de reservas. */
    private ReservaService reservaService;
    
    /** Servicio para operaciones de clientes. */
    private ClienteService clienteService;
    
    /** Indica si hay una sesion de cliente activa. */
    private boolean sesionActiva;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del menu de consola.
     * Inicializa el scanner y crea las instancias de los servicios necesarios.
     * La sesion comienza como inactiva.
     */
    public MenuConsola() {
        this.scanner = new Scanner(System.in);
        this.carteleraService = new CarteleraService();
        this.reservaService = new ReservaService();
        this.clienteService = new ClienteService();
        this.sesionActiva = false;
    }

    // ==================== METODO PRINCIPAL ====================
    
    /**
     * Inicia el bucle principal del menu.
     * Muestra diferentes menus segun si hay una sesion activa o no.
     * Permite navegar entre todas las opciones del sistema hasta que el usuario decida salir.
     */
    public void iniciar() {
        boolean salir = false;
        
        while (!salir) {
            if (!sesionActiva) {
                mostrarMenuPrincipal();
                int opcion = leerOpcion();
                
                switch (opcion) {
                    case 1:
                        sesionActiva = clienteService.iniciarSesion(scanner);
                        if (sesionActiva) {
                            reservaService.setClienteActual(clienteService.getClienteActual());
                        }
                        pausa();
                        break;
                    case 2:
                        carteleraService.mostrarCartelera();
                        pausa();
                        break;
                    case 3:
                        System.out.println("Gracias por visitar Cine Colombia");
                        salir = true;
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            } else {
                mostrarMenuCliente();
                int opcion = leerOpcion();
                
                switch (opcion) {
                    case 1:
                        carteleraService.mostrarCartelera();
                        pausa();
                        break;
                    case 2:
                        carteleraService.mostrarFuncionesDisponibles();
                        System.out.print("\nSeleccione ID de funcion (0 para cancelar): ");
                        String idFuncion = scanner.nextLine();
                        if (!idFuncion.equals("0")) {
                            Funcion f = carteleraService.buscarFuncion(idFuncion);
                            if (f != null) {
                                carteleraService.mostrarDetalleFuncion(idFuncion);
                                System.out.print("Reservar? (S/N): ");
                                if (scanner.nextLine().equalsIgnoreCase("S")) {
                                    reservaService.iniciarReserva(scanner, f);
                                }
                            } else {
                                System.out.println("Funcion no encontrada");
                            }
                        }
                        pausa();
                        break;
                    case 3:
                        reservaService.mostrarMisReservas();
                        pausa();
                        break;
                    case 4:
                        System.out.print("Ingrese codigo de reserva: ");
                        String codigo = scanner.nextLine();
                        reservaService.consultarReserva(codigo);
                        pausa();
                        break;
                    case 5:
                        clienteService.consultarPerfil();
                        pausa();
                        break;
                    case 6:
                        clienteService.cerrarSesion();
                        sesionActiva = false;
                        System.out.println("Sesion cerrada");
                        pausa();
                        break;
                    default:
                        System.out.println("Opcion no valida");
                }
            }
        }
        
        scanner.close();
    }

    // ==================== METODOS DE MENU ====================
    
    /**
     * Muestra el menu principal cuando no hay una sesion activa.
     * Opciones: Iniciar sesion / Registrarse, Ver cartelera, Salir.
     */
    private void mostrarMenuPrincipal() {
        System.out.println("\n*** CINEMEX CONALEP REGION 057 ***");
        System.out.println("1. Iniciar sesion / Registrarse");
        System.out.println("2. Ver cartelera");
        System.out.println("3. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    /**
     * Muestra el menu de cliente cuando hay una sesion activa.
     * Opciones: Ver cartelera, Reservar funcion, Mis reservas,
     * Consultar reserva, Mi perfil, Cerrar sesion.
     */
    private void mostrarMenuCliente() {
        System.out.println("\n=== MENU PRINCIPAL ===");
        System.out.println("1. Ver cartelera");
        System.out.println("2. Reservar funcion");
        System.out.println("3. Mis reservas");
        System.out.println("4. Consultar reserva");
        System.out.println("5. Mi perfil");
        System.out.println("6. Cerrar sesion");
        System.out.print("Seleccione una opcion: ");
    }

    // ==================== METODOS AUXILIARES ====================
    
    /**
     * Lee y valida la opcion ingresada por el usuario.
     * 
     * @return Numero entero de la opcion seleccionada, o 0 si no es valida
     */
    private int leerOpcion() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    /**
     * Pausa la ejecucion hasta que el usuario presione Enter.
     * Util para que el usuario pueda leer mensajes antes de continuar.
     */
    private void pausa() {
        System.out.println("\nPresione Enter para continuar...");
        scanner.nextLine();
    }
    
}//fin de la clase