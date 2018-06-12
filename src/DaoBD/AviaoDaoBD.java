/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DaoBD;

import Dao.AviaoDao;
import Dominio.Aviao;
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
public class AviaoDaoBD implements AviaoDao{
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
    public void salvar(Aviao aviao){       
        int id =0;
        try {
            String sql = "INSERT INTO aviao (codigo, nome, assentos) "
                    + "VALUES (?,?,?)";
            conectarObtendoId(sql);
            comando.setString(1, aviao.getCodigo());
            comando.setString(2, aviao.getNome());
            comando.setInt(3, aviao.getQtdAssento());
            comando.executeUpdate();
            //Obtém o resultSet para pegar o id
//            ResultSet resultado = comando.getGeneratedKeys();
//            if (resultado.next()) {
//                //seta o id para o objeto
//                id = resultado.getInt(1);
//                aviao.setId(id);
//            }
//            else{
//                System.err.println("Erro de Sistema - Nao gerou o id conforme esperado!");
//                throw new BDException("Nao gerou o id conforme esperado!");
//            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao salvar aviao no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        } 
    }

    @Override
    public void deletar(Aviao aviao) {
        try {
            String sql = "DELETE FROM aviao WHERE codigo = ?";
            conectar(sql);
            comando.setString(1, aviao.getCodigo());
            comando.executeUpdate();
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao deletar aviao no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }

    }
    
     @Override
    public void atualizar(Aviao aviao) {
        try {
            String sql = "UPDATE Aviao SET codigo=?, nome=?, assentos=? "
                    + "WHERE codigo=?";

            conectar(sql);
            comando.setString(1, aviao.getCodigo());
            comando.setString(2, aviao.getNome());
            comando.setInt(3, aviao.getQtdAssento());
            comando.setString(4, aviao.getCodigo());
            comando.executeUpdate();

        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao atualizar Aviao no Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
    }
    
    @Override
    public List<Aviao> listar() {
        List<Aviao> listaAviao = new ArrayList<>();
        String sql = "SELECT * FROM aviao";
        try {
            conectar(sql);
            ResultSet resultado = comando.executeQuery();
            while (resultado.next()) {
                String cod = resultado.getString("codigo");
                String nome = resultado.getString("nome");
                int qtdAssento = resultado.getInt("assentos");
                Aviao pac = new Aviao(cod, nome, qtdAssento);
                listaAviao.add(pac);
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar os Avioes do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (listaAviao);
    }

    
    @Override
    public Aviao procurarPorNome(String nome){
    
            String sql = "SELECT * FROM aviao WHERE nome LIKE ?";
             try {
            conectar(sql);
            comando.setString(1,"%"+nome+"%");
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                String codigo = resultado.getString("codigo");
                String avnome = resultado.getString("nome");
                int qtdAssento = resultado.getInt("assentos");
                Aviao av = new Aviao( codigo, avnome,qtdAssento);
                return av;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Aviao pelo cod do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (null);
            
            
    }
    
    @Override
    public Aviao procurarPorCodigo(String cod) {
        String sql = "SELECT * FROM Aviao WHERE codigo = ?";
        try {
            conectar(sql);
            comando.setString(1, cod);
            ResultSet resultado = comando.executeQuery();
            if (resultado.next()) {
                String codigo = resultado.getString("codigo");
                String nome = resultado.getString("nome");
                int qtdAssento = resultado.getInt("assentos");
                Aviao cli = new Aviao( codigo,nome,qtdAssento);
                return cli;
            }
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema ao buscar o Aviao pelo cod do Banco de Dados!");
            throw new BDException(ex);
        } finally {
            fecharConexao();
        }
        return (null);
    
    }
}
