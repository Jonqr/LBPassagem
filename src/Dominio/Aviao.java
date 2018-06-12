/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dominio;

/**
 *
 * @author jonh_
 */
public class Aviao {
    private String codigo, nome;
    private int qtdAssento;

    public Aviao(String codigo, String nome, int qtdAssento) {
        this.codigo = codigo;
        this.nome = nome;
        this.qtdAssento = qtdAssento;
    }

    
    
    
    @Override
    public String toString() {
        return "Aviao{" + "codigo=" + codigo + ", nome=" + nome + ", qtdAssento=" + qtdAssento + '}';
    }

    
    
    
    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQtdAssento() {
        return qtdAssento;
    }

    public void setQtdAssento(int qtdAssento) {
        this.qtdAssento = qtdAssento;
    }
    
    
    
    
}
