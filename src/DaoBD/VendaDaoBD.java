/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoBD;

import Dao.ClienteDao;
import Dao.VendaDao;
import Dao.VooDao;
import Dominio.Aviao;
import Dominio.Venda;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jonh_
 */
public class VendaDaoBD implements VendaDao{
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
    public void gerarVenda(Venda venda) {
            ClienteDao clienteDao;
            clienteDao = new ClienteDaoBD();
            VooDao vooDao;
            vooDao = new VooDaoBd();
        int id =0;
        try {
            String sql = "INSERT INTO venda (cliente,voo,horario) "
                    + "VALUES (?,?,?)";

            conectarObtendoId(sql);
            comando.setInt(2, venda.getVoo().getId());
            comando.setString(1,venda.getCliente().getRg());
            venda.setHora_de_venda(LocalTime.now());
            String horaVenda = venda.getHora_de_venda().format(DateTimeFormatter.ISO_TIME);
            comando.setString(3,horaVenda );
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
            ResultSet resultado = comando.getGeneratedKeys();
            if (resultado.next()) {
                //seta o id para o objeto
                id = resultado.getInt(1);
                venda.setId(id);
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
    public void deletarVenda(Venda venda) {
        try {
            String sql = "DELETE FROM venda WHERE id = ?";
            conectar(sql);
            comando.setInt(1, venda.getId());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar Venda no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

    
    }

    @Override
    public void atualizarVenda(Venda venda) {
        try {
            String sql = "UPDATE venda SET cliente=?, voo=?, horario=? "
                    + "WHERE id=?";

            conectar(sql);
            comando.setString(1, venda.getCliente().getRg());
            comando.setInt(2, venda.getVoo().getId());
            String horario =  venda.getHora_de_venda()+"";
            comando.setString(3,horario);
            comando.setInt(4, venda.getId());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar venda no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }

    @Override
    public List<Venda> listar() {
        
        List<Venda> listaVenda = new ArrayList<>();
        String sql = "SELECT * FROM venda";
        try {
            ClienteDao clienteDao;
            clienteDao = new ClienteDaoBD();
            VooDao vooDao;
            vooDao = new VooDaoBd();
            
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String rg = resultado.getString("cliente");
                int vooId = resultado.getInt("voo");
                String horario = resultado.getString("horario");
                Venda venda = new Venda(id, clienteDao.procurarPorRg(rg),vooDao.procurarPorId(vooId),LocalTime.parse(horario));
                listaVenda.add(venda);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os venda do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVenda);
        
    }

    
    @Override
    public int validaVenda(Venda venda){
        String sql = "SELECT count(voo) FROM venda WHERE voo = ?";
        try {
            conectar(sql);
            comando.setInt(1,venda.getVoo().getId());
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                Long soma = resultado.getLong("count");
                int resp = Integer.valueOf(soma.toString());
                return resp;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Aviao pelo cod do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return 0;
                
    }

    @Override
    public Venda procurarPorID(int vendaid) {
        ClienteDao clienteDao;
        clienteDao = new ClienteDaoBD();
        VooDao vooDao;
        vooDao = new VooDaoBd();
        try {
        String sql = "select * from venda where id = ?";
        
        conectar(sql);
        comando.setInt(1, vendaid);
        ResultSet resultado = comando.executeQuery();
        if(resultado.next()){  
        int id = resultado.getInt("id");
        String rg = resultado.getString("cliente");
        int voo = resultado.getInt("voo");
        String horario = resultado.getString("horario");
        Venda venda = new Venda(id, clienteDao.procurarPorRg(rg),vooDao.procurarPorId(voo),LocalTime.parse(horario));    
        return venda;   
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
    public List<Venda> vendaPorCliente(String rgCliente) {     
        List<Venda> listaVenda = new ArrayList<>();
        String sql = "SELECT * FROM venda where cliente = ?" ;       
        try {
            ClienteDao clienteDao;
            clienteDao = new ClienteDaoBD();
            VooDao vooDao;
            vooDao = new VooDaoBd();         
            conectar(sql);
            comando.setString(1, rgCliente);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String rg = resultado.getString("cliente");
                int vooId = resultado.getInt("voo");
                String horario = resultado.getString("horario");
                Venda venda = new Venda(id, clienteDao.procurarPorRg(rg),vooDao.procurarPorId(vooId),LocalTime.parse(horario));
                listaVenda.add(venda);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os venda do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaVenda);   
    }
    
  

}
