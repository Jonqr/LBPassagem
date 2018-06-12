/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Dominio.Cliente;
import java.util.List;

/**
 *
 * @author jonh_
 */
public interface ClienteDao {
    public void salvar(Cliente cliente);
    public void deletar(Cliente cliente);
    public void atualizar(Cliente cliente);
    public List<Cliente> listar();
    public Cliente procurarPorRg(String rg);
    public List<Cliente> listarPorNome(String nome);
}
