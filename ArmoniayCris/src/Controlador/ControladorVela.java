/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

    package Controlador;

import Modelo.Vela;
import Modelo.VelaAromatica;
import Modelo.VelaDecorativa;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.swing.table.DefaultTableModel;
    

public class ControladorVela {

    private final DefaultTableModel inventario;

    private final Path archivoVelas = Paths.get("datos", "velas.txt");

    public ControladorVela(DefaultTableModel inventario) {
        this.inventario = inventario;
    }
    public String guardarVela(String nombre, boolean aromatica,
            String extra, String precioTexto, String cantidadTexto) {

        nombre = nombre.trim();
        extra = extra.trim();
        precioTexto = precioTexto.trim();
        cantidadTexto = cantidadTexto.trim();
        if (nombre.isEmpty()) {
            return "El nombre de la vela no puede estar vacio.";
        }

        if (extra.isEmpty()) {
            return "El aroma o diseno no puede estar vacio.";
        }

        if (precioTexto.isEmpty()) {
            return "El precio es obligatorio.";
        }
        
        if (cantidadTexto.isEmpty()) {
            return "La cantidad es obligatoria.";
        }
        
       
       

        // Validar caracteres del archivo
        if (nombre.contains("\t") || nombre.contains("\n")
                || nombre.contains("\r") || extra.contains("\t")
                || extra.contains("\n") || extra.contains("\r")) {
            return "El nombre o el detalle contiene caracteres no permitidos.";
        }

        // Evitar duplicados
        if (existe(nombre)) {
            return "Ya existe una vela con ese nombre.";
        }

        try {
            double precio = Double.parseDouble(precioTexto);
            int cantidad = Integer.parseInt(cantidadTexto);

            // Validar cantidad
            if (Double.isNaN(precio) || Double.isInfinite(precio)) {
                return "El precio debe ser un numero valido.";
            }

            if (precio < 0 || cantidad < 0) {
                return "El precio y la cantidad no pueden ser negativos.";
            }

            // Crear objeto segun tipo de vela
            Vela vela;

            if (aromatica) {
                vela = new VelaAromatica(nombre, precio, cantidad, extra);
            } else {
                vela = new VelaDecorativa(nombre, precio, cantidad, extra);
            }

            // Crear la carpeta si no existe
            Files.createDirectories(archivoVelas.getParent());

            // Guardar los datos en el archivo
            try (BufferedWriter escritor = Files.newBufferedWriter(
                    archivoVelas,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND)) {
                escritor.write(
                        (aromatica ? "A" : "D") + "\t"
                        + vela.getNombre() + "\t"
                        + extra + "\t"
                        + vela.getPrecio() + "\t"
                        + vela.getCantidad()
                );
                escritor.newLine();
            }

            // Actualizar la tabla
            inventario.addRow(new Object[]{vela.getNombre(),aromatica ? "Aromatica" : "Decorativa",
                extra,vela.getPrecio(),vela.getCantidad(),estadoDe(vela.getCantidad())});
            return null;
            
        } catch (NumberFormatException e) {
            return "El precio debe ser decimal y la cantidad debe ser un numero entero.";
            
        } catch (IOException e) {
            return "Error al guardar la vela: " + e.getMessage();
            
        }
    }

    // Consultar si ya existe una vela con ese nombre
    private boolean existe(String nombre) {

        for (int i = 0; i < inventario.getRowCount(); i++) {

            String nombreExistente =
                    (String) inventario.getValueAt(i, 0);

            if (nombre.equalsIgnoreCase(nombreExistente)) {
                return true;
            }
        }

        return false;
    }
    // Cargar las velas 
    public String cargarVelas() {
    if (!Files.exists(archivoVelas)) {
        return null;
    }

    try (BufferedReader br = Files.newBufferedReader(archivoVelas, StandardCharsets.UTF_8)) {
        String linea;

        while ((linea = br.readLine()) != null) {

            if (linea.trim().isEmpty()) {
                continue;
            }

            String[] datos = linea.split("\t", -1);

            if (datos.length != 5) {
                continue;
            }

            String tipo = datos[0].trim();
            String nombre = datos[1].trim();
            String extra = datos[2].trim();
            String precioTexto = datos[3].trim();
            String cantidadTexto = datos[4].trim();

            // No cargar registros con campos vacios
            if (tipo.isEmpty() || nombre.isEmpty() || extra.isEmpty()
                    || precioTexto.isEmpty() || cantidadTexto.isEmpty()) {
                continue;
            }

            try {
                double precio = Double.parseDouble(precioTexto);
                int cantidad = Integer.parseInt(cantidadTexto);

                if (!Double.isFinite(precio) || precio < 0 || cantidad < 0) {
                    continue;
                }

                Vela vela;

                if (tipo.equals("A")) {
                    vela = new VelaAromatica(nombre, precio, cantidad, extra);
                } else if (tipo.equals("D")) {
                    vela = new VelaDecorativa(nombre, precio, cantidad, extra);
                } else {
                    continue;
                }

                String tipoVela = tipo.equals("A") ? "Aromatica" : "Decorativa";

                inventario.addRow(new Object[]{
                    vela.getNombre(),
                    tipoVela,
                    extra,
                    vela.getPrecio(),
                    vela.getCantidad(),
                    estadoDe(vela.getCantidad())
                });

            } catch (NumberFormatException e) {
                continue;
            }
            
        }
        return null;
    } catch (IOException e) {
        throw new RuntimeException("Error al cargar las velas: " + e.getMessage());
    }
}

    // Determinar el estado del inventario
    private String estadoDe(int cantidad) {
        return cantidad == 0 ? "AGOTADA" : "Disponible";
    }
}
