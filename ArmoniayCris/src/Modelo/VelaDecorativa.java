/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author andre
 */


public class VelaDecorativa extends Vela {
    private String diseno;

    public VelaDecorativa(String nombre, double precio, int cantidad, String diseno) {
        super(nombre, precio, cantidad);
        this.diseno = diseno;
    }

    public String getDiseno() { return diseno; }
    public void setDiseno(String diseno) { this.diseno = diseno; }

    @Override
    public double calcularPrecio() {
        return getPrecio() * getCantidad() * 1.15;
    }

    @Override
    public String toString() {
        return super.toString() + " | Diseño: " + diseno;
    }
}