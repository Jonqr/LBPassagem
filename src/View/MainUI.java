/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Util.Console;
import ViewMenu.ClienteMenu;
import ViewMenu.MainMenu;
import java.util.InputMismatchException;

/**
 *
 * @author jonh_
 */
public class MainUI {
    public void menu() {
        int opcao = -1;
        do {
            try {
                System.out.println(MainMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case MainMenu.OP_CLIENTE:                      
                        new ClienteUI().menu();
                        break;
                    case MainMenu.OP_AVIAO:
                        new AviaoUI().menu();
                        break;
                    case MainMenu.OP_VOO:
                        new VooUI().menu();
                        break;
                    case MainMenu.OP_VENDA:
                        new VendaUI().menu();
                        break;
                    case MainMenu.OP_RELATORIOS:
                        new RelatorioUI().menu();
                        break;
                    case ClienteMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != ClienteMenu.OP_SAIR);
    }
}
