/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

import java.time.LocalTime;

/**
 *
 * @author jonh_
 */
public class Venda {
    private int id;
    private Cliente cliente;
    private Voo voo;
    private LocalTime hora_de_venda;

    
    
    public Venda(Cliente cliente, Voo voo) {
        this.cliente = cliente;
        this.voo = voo;
        hora_de_venda = LocalTime.now();
    }

    public Venda(int id, Cliente cliente, Voo voo ) {
        this.id = id;
        this.cliente = cliente;
        this.voo = voo;
        hora_de_venda = LocalTime.now();
    }

    public Venda(int id, Cliente cliente, Voo voo, LocalTime hora_de_venda) {
        this.id = id;
        this.cliente = cliente;
        this.voo = voo;
        this.hora_de_venda = hora_de_venda;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Voo getVoo() {
        return voo;
    }

    public void setVoo(Voo voo) {
        this.voo = voo;
    }

    public LocalTime getHora_de_venda() {
        return hora_de_venda;
    }

    public void setHora_de_venda(LocalTime hora_de_venda) {
        this.hora_de_venda = hora_de_venda;
    }

    @Override
    public String toString() {
        return "Venda{" + "id=" + id + ", cliente=" + cliente + ", voo=" + voo + ", hora_de_venda=" + hora_de_venda + '}';
    }
    
    
}
