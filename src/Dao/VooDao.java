/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Dao;

import Dominio.Voo;
import java.util.List;

/**
 *
 * @author jonh_
 */
public interface VooDao {
    
    public void salvar(Voo voo);
    public void deletar(Voo voo);
    public void atualizar(Voo voo);
    public List<Voo> listar();
    public Voo procurarPorId(int id);
    //Adicionado
    public List<Voo> listarPorHorario(String horario);
    public List<Voo> listarPorDestino(String destino);
    public List<Voo> listarPorOrigem(String origem);
}    
/*
- CRUD de vôos: anota-se para o vôo a origem (cidade), destino
(cidade), o horário do vôo e o avião
*/