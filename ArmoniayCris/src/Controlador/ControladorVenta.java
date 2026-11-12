/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.table.DefaultTableModel;

public class ControladorVenta {

    private final String archivo = "ventas.txt";

    // Guardar historial de ventas
    public void guardarVentas(DefaultTableModel ventas) throws IOException {

        try (BufferedWriter escritor =
                new BufferedWriter(new FileWriter(archivo))) {

            for (int i = 0; i < ventas.getRowCount(); i++) {

                String fecha = ventas.getValueAt(i, 0).toString();
                String cliente = ventas.getValueAt(i, 1).toString();
                String detalle = ventas.getValueAt(i, 2).toString();
                String total = ventas.getValueAt(i, 3).toString();

                escritor.write(fecha + "|" + cliente + "|"
                        + detalle + "|" + total);

                escritor.newLine();
            }
        }
    }

    // Cargar historial de ventas
    public void cargarVentas(DefaultTableModel ventas) throws IOException {

        File archivoVentas = new File(archivo);

        ventas.setRowCount(0);

        if (!archivoVentas.exists()) {
            return;
        }

        try (BufferedReader lector =
                new BufferedReader(new FileReader(archivoVentas))) {

            String linea;

            while ((linea = lector.readLine()) != null) {

                String[] datos = linea.split("\\|", -1);

                if (datos.length == 4) {

                    String fecha = datos[0];
                    String cliente = datos[1];
                    String detalle = datos[2];
                    double total = Double.parseDouble(datos[3]);

                    ventas.addRow(new Object[]{
                        fecha,
                        cliente,
                        detalle,
                        total
                    });
                }
            }
        }
    }
    
    
}