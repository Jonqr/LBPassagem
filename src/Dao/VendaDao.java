/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Dominio.Venda;
import java.util.List;

/**
 *
 * @author jonh_
 */
public interface VendaDao {
    public void gerarVenda(Venda venda);
    public void deletarVenda(Venda venda);
    public void atualizarVenda(Venda venda);
    public List<Venda> listar();
    public int validaVenda(Venda venda);
    public Venda procurarPorID(int venda);
    public List<Venda> vendaPorCliente(String rgCliente);
}
