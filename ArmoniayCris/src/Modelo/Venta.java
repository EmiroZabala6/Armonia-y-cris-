/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author andre
 */
public class Venta {
   
    private int codigo;
    private Cliente cliente;
    private ArrayList<Vela> velas;
    private LocalDate fecha;

    public Venta(int codigo, Cliente cliente) {
        this.codigo = codigo;
        this.cliente = cliente;
        this.velas = new ArrayList<>();
        this.fecha = LocalDate.now();
    }

    public void agregarVela(Vela vela) {
        velas.add(vela);
    }

    public double calcularTotal() {
        double total = 0;
        for (Vela v : velas) {
            total += v.calcularPrecio();
        }
        return total;
    }

    public int getCodigo() { return codigo; }
    public Cliente getCliente() { return cliente; }
    public ArrayList<Vela> getVelas() { return velas; }
    public LocalDate getFecha() { return fecha; } 
}
