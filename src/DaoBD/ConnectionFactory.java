package DaoBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lhries
 */
public class ConnectionFactory {
    private final static String HOST = "localhost";
    private final static String PORT = "5433";
    private final static String BD = "LaboratorioPassagem";
    private final static String URL = "jdbc:postgresql://"+HOST+":"+PORT+"/"+BD;
    private final static String USUARIO = "postgres";
    private final static String SENHA = "admin";
    
    public static Connection getConnection(){
        Connection conexao = null;
        try {
            Class.forName("org.postgresql.Driver");
            conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            
        } catch (ClassNotFoundException ex) {
            System.err.println("Erro de Sistema - Classe do Driver Nao Encontrada!");
            throw new BDException(ex);
        } catch (SQLException ex) {
            System.err.println("Erro de Sistema - Problema na conexão do banco de dados");
            throw new BDException(ex);
        }
        return(conexao);
    }
    
}
