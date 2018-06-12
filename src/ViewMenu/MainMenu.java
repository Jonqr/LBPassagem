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
public class MainMenu {
    public static final int OP_CLIENTE = 1;
    public static final int OP_AVIAO = 2;
    public static final int OP_VOO = 3;
    public static final int OP_VENDA = 4;
    public static final int OP_RELATORIOS = 5;
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Menu Cliente\n"
                + "2- Menu Avião\n"
                + "3- Menu Voos\n"
                + "4- Menu Venda\n"
                + "5- Relatorios\n"
                + "0- Sair"
                + "\n--------------------------------------");
    }
}
