/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author andre
 */


public class VelaAromatica extends Vela {
    private String aroma;

    public VelaAromatica(String nombre, double precio, int cantidad, String aroma) {
        super(nombre, precio, cantidad);
        this.aroma = aroma;
    }

    public String getAroma() { return aroma; }
    public void setAroma(String aroma) { this.aroma = aroma; }

    @Override
    public double calcularPrecio() {
        return getPrecio() * getCantidad() * 1.10;
    }

    @Override
    public String toString() {
        return super.toString() + " | Aroma: " + aroma;
    }
}