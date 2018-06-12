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
public class VendaMenu {
    public static final int OP_GERAR_VENDA = 1;
    public static final int OP_ATUALIZAR_VENDA = 2;
    public static final int OP_DELETAR_VENDA = 3;
    public static final int OP_LISTAR_VENDAS= 4;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Gerar Venda\n"
                + "2- Atualizar Venda\n"
                + "3- Deletar Venda\n"
                + "4- Listar Vendas\n"
                + "0- Sair"
                + "\n--------------------------------------");
    }
    
    
    
    
}
