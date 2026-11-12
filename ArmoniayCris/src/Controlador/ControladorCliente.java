/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.swing.table.DefaultTableModel;

public class ControladorCliente {

    private final DefaultTableModel clientes;

    private final Path archivoClientes = Paths.get("datos", "clientes.txt");

    public ControladorCliente(DefaultTableModel clientes) {
        this.clientes = clientes;
    }

    public String guardarCliente(String nombre, String telefono) {
        nombre = nombre.trim();
        telefono = telefono.trim();
  
        if (nombre.isEmpty()) {
            return "El nombre del cliente no puede estar vacio.";
        }

        if (telefono.isEmpty()) {
            return "El telefono no puede estar vacio.";
        }

        if (!telefono.matches("\\d+")) {
            return "El telefono solo puede contener numeros.";
        }

        if (nombre.contains("\t") || nombre.contains("\n")
                || nombre.contains("\r")) {
            return "El nombre contiene caracteres no permitidos.";
        }

        Cliente cliente = new Cliente(nombre, telefono);

        try {
            Files.createDirectories(archivoClientes.getParent());

            try (BufferedWriter escritor = Files.newBufferedWriter(
                    archivoClientes,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {

                escritor.write(cliente.getNombre() + "\t" + cliente.getTelefono());
                escritor.newLine();
            }

            // Actualizar la tabla
            clientes.addRow(new Object[]{cliente.getNombre(),cliente.getTelefono()});

            return null;

        } catch (IOException e) {
            return "Error al guardar el cliente: " + e.getMessage();
        }
    }

    // Cargar los clientes guardados en el archivo TXT
    public String cargarClientes() {

        try {
            if (!Files.exists(archivoClientes)) {
                return null;
            }

            try (BufferedReader lector = Files.newBufferedReader(
                    archivoClientes, StandardCharsets.UTF_8)) {

                String linea;

                while ((linea = lector.readLine()) != null) {

                    String[] datos = linea.split("\t", -1);

                    if (datos.length == 2) {

                        Cliente cliente = new Cliente(datos[0],datos[1]);

                        clientes.addRow(new Object[]{cliente.getNombre(),
                            cliente.getTelefono()
                        });
                    }
                }
            }
            return null;
            
        } catch (IOException e) {
            return "Error al cargar los clientes: " + e.getMessage();
        }
    }
}

