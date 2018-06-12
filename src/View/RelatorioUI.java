/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dao.VendaDao;
import DaoBD.VendaDaoBD;
import Dominio.Venda;
import Util.Console;
import ViewMenu.RelatorioMenu;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author jonh_
 */
public class RelatorioUI {

    public void menu() {
        int opcao = -1;
        do {
            try {
                System.out.println(RelatorioMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case RelatorioMenu.OP_VENDA_POR_CLIENTE:                      
                        VendaPorCliente();
                        break;
                    case RelatorioMenu.OP_CIDADE_ORIGEM:
                        new AviaoUI().menu();
                        break;
                    case RelatorioMenu.OP_CIDADE_DESTINO:
                        new VooUI().menu();
                        break;
                    case RelatorioMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != RelatorioMenu.OP_SAIR);
    }
    /*
    - Relatórios: vendas por cliente, cidades de origem e destino, períodos de vôos, vôos que mais usam determinado avião, clientes que mais compram passagens.

    */
    
    private void VendaPorCliente(){
        VendaDao vendaD;
        vendaD = new VendaDaoBD();
        
        try{
        new ClienteUI().mostrarClientes();
        String cliente = Console.scanString("RG Cliente:");
        List<Venda> lista = vendaD.vendaPorCliente(cliente);
        this.mostrarRelatorioVenda(lista);
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }

    }
    
    
    
    private void mostrarRelatorioVenda(List<Venda> listaRelatorioVenda) {
        if (listaRelatorioVenda.isEmpty()) {
            System.out.println("Clientes nao encontrados!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "ID") + "\t"
                    + String.format("%-20s", "|CLIENTE") + "\t"
                    + String.format("%-20s", "|VOO") + "\t"
                    + String.format("%-20s", "|HORARIO DE COMPRA"));
            for (Venda venda : listaRelatorioVenda) {
                System.out.println(String.format("%-10s", venda.getId()) + "\t"
                        + String.format("%-20s", "|" + venda.getCliente()) + "\t"
                        + String.format("%-20s", "|" + venda.getVoo()) + "\t"
                        + String.format("%-20s", "|" + venda.getHora_de_venda()));
            }
        }
    }
    
    
    
            
    
}
