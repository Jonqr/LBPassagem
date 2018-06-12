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
public class ClienteMenu {
    
    public static final int OP_CADASTRAR = 1;
    public static final int OP_DELETAR = 2;
    public static final int OP_ATUALIZAR = 3;
    public static final int OP_LISTAR = 4;
    public static final int OP_CONSULTAR = 5;
    public static final int OP_CONSULTAR_RG = 6;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar Cliente\n"
                + "2- Deletar Cliente\n"
                + "3- Atualizar dados do Cliente\n"
                + "4- Listar Clientes\n"
                + "5- Listar Clientes por Nome\n"
                + "6- Consultar Clientes por RG \n"
                + "0- Sair"
                + "\n--------------------------------------");
    }
    
}
