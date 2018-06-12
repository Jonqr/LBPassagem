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
public class RelatorioMenu {
    public static final int OP_VENDA_POR_CLIENTE = 1;
    public static final int OP_CIDADE_ORIGEM = 2;
    public static final int OP_CIDADE_DESTINO = 3;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Venda por Cliente\n"
                + "2- Voo por Cidade de Origem\n"
                + "3- Voo pr Cidade de Destino\n"
                + "0- Sair"
                + "\n--------------------------------------");
    }
}
