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
public class Voo {
    private int id;
    private Aviao aviao;
    private String origem,destino;
    private LocalTime horario;

    public Voo(int id, Aviao aviao, String origem, String destino, LocalTime horario) {
        this.id = id;
        this.aviao = aviao;
        this.origem = origem;
        this.destino = destino;
        this.horario = horario;
    }

    public Voo(Aviao aviao, String origem, String destino, LocalTime horario) {
        this.aviao = aviao;
        this.origem = origem;
        this.destino = destino;
        this.horario = horario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Aviao getAviao() {
        return aviao;
    }

    public void setAviao(Aviao aviao) {
        this.aviao = aviao;
    }

    public String getOrigem() {
        return origem;
    }

    public void setOrigem(String origem) {
        this.origem = origem;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public LocalTime getHorario() {
        return horario;
    }

    public void setHorario(LocalTime horario) {
        this.horario = horario;
    }

    @Override
    public String toString() {
        return "Voo{" + "id=" + id + ", aviao=" + aviao + ", origem=" + origem + ", destino=" + destino + ", horario=" + horario + '}';
    }
    
    

    

    

    
    
    
}
