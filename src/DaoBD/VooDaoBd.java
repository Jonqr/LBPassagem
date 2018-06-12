/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoBD;

import Dao.AviaoDao;
import Dao.VooDao;
import Dominio.Aviao;
import Dominio.Voo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonh_
 */
public class VooDaoBd implements VooDao{
    
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
    public void salvar(Voo voo) {
        
        int id =0;
        try {
            String sql = "INSERT INTO voo (cod_aviao,origem,destino, horario) "
                    + "VALUES (?,?,?,?)";
            //    public Voo(int id, Aviao aviao, String origem, String destino, LocalTime horario) {

            //- CRUD de vôos: anota-se para o vôo a origem (cidade), destino (cidade), o horário do vôo e o avião.
            //Foi criado um novo método conectar para obter o id
            conectarObtendoId(sql);
            comando.setString(1, voo.getAviao().getCodigo());
            comando.setString(2, voo.getOrigem());
            comando.setString(3, voo.getDestino());
            String horario = voo.getHorario()+"";
            comando.setString(4,horario );
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                voo.setId(id);
            }
            else{
                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
                throw new BDException("Nao gerou o id conforme esperado!");
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar Voo no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        } 
    }
    
    @Override
    public void deletar(Voo voo) {
        try {
            String sql = "DELETE FROM voo WHERE id = ?";
            conectar(sql);
            comando.setInt(1, voo.getId());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar Voo no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

    }
    
    @Override
    public void atualizar(Voo voo) { 
    try {                     //origem, destino, horario, aviao
            String sql = "UPDATE voo SET cod_aviao=?, origem=?, destino=?, horario=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, voo.getAviao().getCodigo());
            comando.setString(2, voo.getOrigem());
            comando.setString(3, voo.getDestino());
            String horario = voo.getHorario()+"";
            comando.setString(4, horario);
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar Voo no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    
    }
       
    @Override
    public List<Voo> listar() {
        
        AviaoDao aviaoDao;
        aviaoDao = new AviaoDaoBD();
        
        List<Voo> listaVoo = new ArrayList<>();
        String sql = "SELECT * FROM voo";
        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                
                int id = resultado.getInt("id");
                Aviao av = aviaoDao.procurarPorCodigo(resultado.getString("cod_aviao")); 
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                String horario = resultado.getString("horario");
                Voo voo = new Voo(id, av , origem, destino, LocalTime.parse(horario));
                listaVoo.add(voo);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os voo do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVoo);     
    }
    
    @Override
    public Voo procurarPorId(int id) {
          AviaoDao aviaoDao;
          aviaoDao = new AviaoDaoBD();
        String sql = "SELECT * FROM voo WHERE id = ?";
        
        try {
            conectar(sql);
            comando.setInt(1, id);
            
            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {   
                int ids = resultado.getInt("id");
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                String horario = resultado.getString("horario");
                String idav = resultado.getString("cod_aviao")  ;              
                Voo voo = new Voo(id, aviaoDao.procurarPorCodigo(idav), origem, destino, LocalTime.parse(horario));    
                return voo;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Voo pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

        return (null);

    }
                  
    @Override
    public List<Voo> listarPorHorario(String horario) {
        AviaoDao aviaoDao;
        aviaoDao = new AviaoDaoBD();
        List<Voo> listaVoo = new ArrayList<>();

        String sql = "SELECT * FROM voo WHERE horario like ?";
        
        try {
            conectar(sql);
            comando.setString(1, "%"+horario+"%");

            ResultSet resultado = comando.executeQuery();
            
            if (resultado.next()) {
                int id = resultado.getInt("id");
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                String hora = resultado.getString("horario");
                String codigo = resultado.getString("cod_aviao");                  
                Aviao aviao = aviaoDao.procurarPorCodigo(codigo);
                Voo voo = new Voo(id, aviao, origem, destino, LocalTime.parse(hora));
                listaVoo.add(voo);   
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Voo pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVoo);
    }
    
    @Override
    public List<Voo> listarPorDestino(String destinos) {
        AviaoDao aviaoDao;
        aviaoDao = new AviaoDaoBD();
        List<Voo> listaVoo = new ArrayList<>();
        String sql = "SELECT * FROM voo WHERE destino = ?";
        
        try {
            conectar(sql);
            comando.setString(1,"%"+destinos+"%");
            
            ResultSet resultado = comando.executeQuery();
            
            if (resultado.next()) {
                int id = resultado.getInt("id");
                Aviao aviao = aviaoDao.procurarPorCodigo(resultado.getString("cod_aviao"))  ;                  
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                String horario = resultado.getString("horario");
                Voo voo = new Voo(id, aviao, origem,destino,LocalTime.parse(horario));
                listaVoo.add(voo);            
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Voo pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVoo);
        
    }
    
    @Override
    public List<Voo> listarPorOrigem(String origems) {
    AviaoDao aviaoDao;
    aviaoDao = new AviaoDaoBD();
    List<Voo> listaVoo = new ArrayList<>();

    String sql = "SELECT * FROM voo WHERE origem LIKE ?";
        
    try {
            conectar(sql);
            comando.setString(1, "%"+origems+"%");
            
            ResultSet resultado = comando.executeQuery();
            
            if (resultado.next()) {
                int id = resultado.getInt("id");
                String origem = resultado.getString("origem");
                String destino = resultado.getString("destino");
                String horario = resultado.getString("horario");
                Aviao aviao = aviaoDao.procurarPorCodigo(resultado.getString("cod_aviao"));                  
                Voo voo = new Voo(id, aviao, origem,destino,LocalTime.parse(horario));
                listaVoo.add(voo);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Voo pelo id do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVoo);
       }
    
}
