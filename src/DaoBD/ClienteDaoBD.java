/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoBD;

import Dao.ClienteDao;
import Dominio.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonh_
 */
public class ClienteDaoBD implements ClienteDao{
    
    private Connection conexao;
    private PreparedStatement comando;
    
     public Connection conectar(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql);
        return conexao;
    }
    public void conectarObtendoId(String sql) throws SQLException {
        conexao = ConnectionFactory.getConnection();
        comando = conexao.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
    } 
    public void fecharConexao(){
    
     try {
            if (comando != null) {
                comando.close();
            }
            if (conexao != null) {
                conexao.close();
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Erro ao encerrar a conexao");
            throw new BDException(ex);
        }

    }


    @Override
    public void salvar(Cliente cliente){
        int id =0;
        try {
            String sql = "INSERT INTO cliente (rg, nome, telefone) "
                    + "VALUES (?,?,?)";

            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, cliente.getRg());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getTelefone());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar cliente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        } 

    }

    public void deletar(Cliente cliente){
    try {
            String sql = "DELETE FROM cliente WHERE rg = ?";
            conectar(sql);
            comando.setString(1, cliente.getRg());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar cliente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    
    }
    public void atualizar(Cliente cliente){
    try {
            String sql = "UPDATE cliente SET rg=?, nome=?, telefone=? "
                    + "WHERE rg=?";
            conectar(sql);
            comando.setString(1, cliente.getRg());
            comando.setString(2, cliente.getNome());
            comando.setString(3, cliente.getTelefone());
            comando.setString(4, cliente.getRg());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar cliente no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    
    
    }

    @Override
    public List<Cliente> listar(){
        
         List<Cliente> listaCliente = new ArrayList<>();
        String sql = "SELECT * FROM Cliente";
        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {               
                String rg = resultado.getString("rg");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                Cliente pac = new Cliente(rg, nome, telefone);
                listaCliente.add(pac);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os clientes do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaCliente);  
    }
    
    public Cliente procurarPorRg(String rg){
     String sql = "SELECT * FROM Cliente WHERE rg LIKE ?";
        try {
            conectar(sql);
            comando.setString(1, "%"+rg+"%");
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                String RG = resultado.getString("rg");
                String nome = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                Cliente cli = new Cliente(nome, RG, telefone);
                return cli;
            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Cliente pelo rg do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);
    
    
    }
    
    public List<Cliente> listarPorNome(String nome){
    List<Cliente> listaCliente = new ArrayList<>();
        String sql = "SELECT * FROM cliente WHERE nome LIKE ?";

        try {
            conectar(sql);
            comando.setString(1, "%" + nome + "%");
            ResultSet resultado = comando.executeQuery();

            while (resultado.next()) {
                
                String rg = resultado.getString("rg");
                String nomeX = resultado.getString("nome");
                String telefone = resultado.getString("telefone");
                
                Cliente cli = new Cliente(nomeX,rg, telefone);

                listaCliente.add(cli);

            }

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os Cliente pelo nome do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaCliente);
    
    }







}//fim class
