/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewMenu;

/**
 *
 * @author jonh_
 */
public class AviaoMenu {
    
    public static final int OP_CADASTRAR = 1;
    public static final int OP_DELETAR = 2;
    public static final int OP_ATUALIZAR = 3;
    public static final int OP_LISTAR = 4;
    public static final int OP_CONSULTAR_CODIGO = 5;
    public static final int OP_CONSULTAR_NOME = 6;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar Aviaos\n"
                + "2- Deletar Aviao\n"
                + "3- Atualizar dados do Aviao\n"
                + "4- Listar Aviaos\n"
                + "5- Consultar Aviaos por Codigo\n"
                + "6- Consultar Aviaos por Nome\n"
                + "0- Sair"
                + "\n--------------------------------------");
    }
    
}
