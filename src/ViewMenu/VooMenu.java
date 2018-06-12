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
public class VooMenu {
    
    
    public static final int OP_CADASTRAR = 1;
    public static final int OP_DELETAR = 2;
    public static final int OP_ATUALIZAR = 3;
    public static final int OP_LISTAR = 4;
    public static final int OP_CONSULTAR_HORA = 5;
    public static final int OP_CONSULTAR_DESTINO = 6;
    public static final int OP_CONSULTAR_ORIGEM= 7;
    public static final int OP_CONSULTAR = 0;

        
    public static final int OP_SAIR = 0;

    public static String getOpcoes() {
        return ("\n--------------------------------------\n"
                + "1- Cadastrar Voo \n"
                + "2- Deletar Voo\n"
                + "3- Atualizar dados do Voo\n"
                + "4- Listar Voos\n"
                + "5- Consultar Voo por Horario\n"
                + "6- Consultar Voo por Destino\n"
                + "7- Consultar Voo por Origem\n"
                + "0- Sair"
                + "\n--------------------------------------");
    }

    
}
