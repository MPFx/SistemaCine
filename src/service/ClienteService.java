package service;

import java.util.Scanner;
import model.clientes.Cliente;
import repository.DataStore;

/**
 * Clase de servicio que gestiona la autenticacion y registro de clientes.
 * Proporciona funcionalidades para iniciar sesion, registrar nuevos clientes,
 * cerrar sesion y consultar el perfil del cliente actual.
 * 
 * @author ISC Israel de Jesus Mar Parada
 * @version 1.0
 * @see Cliente
 * @see DataStore
 */
public class ClienteService {

    // ==================== ATRIBUTOS ====================
    
    /** Almacenamiento de datos del sistema. */
    private DataStore dataStore;
    
    /** Cliente actualmente autenticado en sesion. */
    private Cliente clienteActual;

    // ==================== CONSTRUCTOR ====================
    
    /**
     * Constructor del servicio de clientes.
     * Obtiene la instancia unica del DataStore.
     */
    public ClienteService() {
        this.dataStore = DataStore.getInstance();
    }

    // ==================== METODOS DE AUTENTICACION ====================
    
    /**
     * Inicia sesion de un cliente existente.
     * Busca al cliente por su documento de identidad.
     * Si no existe, ofrece la opcion de registrarse.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     * @return true si la autenticacion es exitosa, false en caso contrario
     */
    public boolean iniciarSesion(Scanner scanner) {
        System.out.println("\n=== INICIO DE SESION ===");
        System.out.print("Documento de identidad: ");
        String documento = scanner.nextLine();
        
        Cliente cliente = dataStore.getClienteByDocumento(documento);
        if (cliente == null) {
            System.out.println("Cliente no encontrado. Desea registrarse? (S/N)");
            String opcion = scanner.nextLine();
            if (opcion.equalsIgnoreCase("S")) {
                return registrarCliente(scanner);
            }
            return false;
        }
        
        this.clienteActual = cliente;
        System.out.println("Bienvenido " + cliente.getNombreCliente());
        System.out.println("Nivel: " + cliente.getNivelMembresia() + 
                         " | Puntos: " + cliente.getPuntosAcumulados());
        return true;
    }

    /**
     * Registra un nuevo cliente en el sistema.
     * Solicita nombre, documento, email y telefono.
     * Valida que el documento no este ya registrado.
     * Genera un ID automatico para el nuevo cliente.
     * 
     * @param scanner Scanner para entrada de datos del usuario
     * @return true si el registro es exitoso, false en caso contrario
     */
    public boolean registrarCliente(Scanner scanner) {
        System.out.println("\n=== REGISTRO DE CLIENTE ===");
        
        System.out.print("Nombre completo: ");
        String nombre = scanner.nextLine();
        
        System.out.print("Documento de identidad: ");
        String documento = scanner.nextLine();
        
        if (dataStore.getClienteByDocumento(documento) != null) {
            System.out.println("Ya existe un cliente con ese documento");
            return false;
        }
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Telefono: ");
        String telefono = scanner.nextLine();
        
        String idCliente = "C" + (dataStore.getAllClientes().size() + 1000);
        Cliente nuevo = new Cliente(idCliente, nombre, documento);
        nuevo.setEmailCliente(email);
        nuevo.setTelefonoCliente(telefono);
        
        dataStore.addCliente(nuevo);
        this.clienteActual = nuevo;
        
        System.out.println("Registro exitoso. Bienvenido " + nombre);
        return true;
    }

    // ==================== METODOS DE SESION ====================
    
    /**
     * Cierra la sesion actual del cliente.
     * Muestra un mensaje de despedida y limpia el cliente actual.
     */
    public void cerrarSesion() {
        if (clienteActual != null) {
            System.out.println("Hasta luego " + clienteActual.getNombreCliente());
            this.clienteActual = null;
        }
    }

    // ==================== METODOS DE CONSULTA ====================
    
    /**
     * Muestra el perfil completo del cliente actual.
     * Incluye ID, nombre, documento, email, telefono, tipo, membresia,
     * puntos acumulados, fecha de registro, total de reservas y descuento actual.
     * Requiere una sesion activa.
     */
    public void consultarPerfil() {
        if (clienteActual == null) {
            System.out.println("No hay sesion activa");
            return;
        }

        System.out.println("\n=== PERFIL DEL CLIENTE ===");
        System.out.println("ID: " + clienteActual.getIdCliente());
        System.out.println("Nombre: " + clienteActual.getNombreCliente());
        System.out.println("Documento: " + clienteActual.getDocumentoIdentidad());
        System.out.println("Email: " + clienteActual.getEmailCliente());
        System.out.println("Telefono: " + clienteActual.getTelefonoCliente());
        System.out.println("Tipo: " + clienteActual.getTipoCliente());
        System.out.println("Membresia: " + clienteActual.getNivelMembresia());
        System.out.println("Puntos acumulados: " + clienteActual.getPuntosAcumulados());
        System.out.println("Fecha registro: " + clienteActual.getFechaRegistro());
        System.out.println("Total reservas: " + clienteActual.getHistorialReservas().size());
        System.out.println("Descuento actual: " + 
            clienteActual.CalcularDescuento().multiply(new java.math.BigDecimal("100")) + "%");
    }

    // ==================== GETTER ====================
    
    /**
     * Obtiene el cliente actualmente autenticado.
     * 
     * @return Cliente actual o null si no hay sesion activa
     */
    public Cliente getClienteActual() {
        return clienteActual;
    }
    
}//fin de la clase