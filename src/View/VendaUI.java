/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dao.ClienteDao;
import Dao.VendaDao;
import Dao.VooDao;
import DaoBD.ClienteDaoBD;
import DaoBD.VendaDaoBD;
import DaoBD.VooDaoBd;
import Dominio.Venda;
import Util.Console;
import Util.StringUtils;
import ViewMenu.VendaMenu;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author jonh_
 */
public class VendaUI {
    private VendaDao vendaDao;
    
    public VendaUI() {
        vendaDao = new VendaDaoBD();
    }
    
    public void menu() {
        int opcao = -1;
        do {
            try {
                System.out.println(VendaMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case VendaMenu.OP_GERAR_VENDA:
                        gerarVenda();
                        break;
                    case VendaMenu.OP_ATUALIZAR_VENDA:
                        
                        break;
                    case VendaMenu.OP_DELETAR_VENDA:
                        
                        break;
                    case VendaMenu.OP_LISTAR_VENDAS:
                        
                        break;
                    case VendaMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }
        } while (opcao != VendaMenu.OP_SAIR);
    }    
    
    private void gerarVenda(){
        try{
            
        VendaDao vendaDao;
        vendaDao = new VendaDaoBD();
        ClienteDao clienteDao;
        clienteDao = new ClienteDaoBD();
        VooDao vooDao;
        vooDao = new VooDaoBd();  
        
        System.out.println("Seleciona Cliente: ");
        new ClienteUI().mostrarClientes();
        String rg = Console.scanString("Rg: ");
        System.out.println("Informe Voo");
        new VooUI().mostrarVoos();
        int vooId = Console.scanInt("ID do Voo: ");
        
        if(StringUtils.isNullOrBlank(rg)==false||StringUtils.isNullOrEmpty(rg)==false
            ||StringUtils.isNullOrBlank(vooId)==false||StringUtils.isNullOrEmpty(vooId)==false){
            Venda venda = new Venda(clienteDao.procurarPorRg(rg), vooDao.procurarPorId(vooId));
            if (vendaDao.validaVenda(venda)<=venda.getVoo().getAviao().getQtdAssento()) {
                
                vendaDao.gerarVenda(venda);
                
            }else{System.err.println("Voo Lotado !");}

//                vendaDao.gerarVenda(new Venda(clienteDao.procurarPorRg(rg),vooDao.procurarPorId(vooId)));
               System.out.println("Venda para cliente: " + clienteDao.procurarPorRg(rg) + " cadastrado com sucesso!");       
       }else{            
           System.err.println("ERRO: DATA[BLANK/NULL] - NAO FOI POSSIVEL CADASTRAR VENDA !");
           System.err.println("SISTEMA NÃO EXECUTOU POIS HAVIA DADOS NÃO COMPATIVEIS CONFORME INFORMADO:");
           }
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }//gerarVenda
    
    private void deletaVenda(){
        listarVendas();
        try{
        int id = Console.scanInt("Informe o ID para remover a Venda:");
        Venda venda = vendaDao.procurarPorID(id);
       if (UIUtil.getConfirmacao("Realmente deseja excluir esse Voo?")) {
            vendaDao.deletarVenda(venda);
            System.out.println("Voo deletado com sucesso!");
        } else {
            System.out.println("Operacao cancelada!");
        }
       } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
        }
    }
    
    private void atualizaVenda(){
        VendaDao vendaDao;
        vendaDao = new VendaDaoBD();
        ClienteDao clienteDao;
        clienteDao = new ClienteDaoBD();
        VooDao vooDao;
        vooDao = new VooDaoBd();
     try{
       listarVendas();
       int id = Console.scanInt("Informe o ID da Venda que deseja alterar:");
       Venda venda = vendaDao.procurarPorID(id);
       System.out.println("Digite os dados do voo que deseja alterar[Vazio caso nao queira]");
       System.out.println("Selecione o cliente: ");
       new ClienteUI().mostrarClientes();
       String cliente = Console.scanString("RG: ");
       System.out.println("Selecione o Voo");
       new VooUI().mostrarVoos();
       int voo = Console.scanInt("ID do VOO");
       
       
       if(StringUtils.isNullOrBlank(cliente)==false||StringUtils.isNullOrEmpty(cliente)==false
         ||StringUtils.isNullOrBlank(voo)==false||StringUtils.isNullOrEmpty(voo)==false){
           
       if (!cliente.isEmpty()) {
            venda.setCliente(clienteDao.procurarPorRg(cliente));
        }
       if (voo!=0){
            venda.setVoo(vooDao.procurarPorId(id));
        }
        venda.setHora_de_venda(LocalTime.now());
       
       vendaDao.atualizarVenda(venda);
       System.out.println("Venda atualizado com sucesso!");
       }else{
           System.err.println("ERRO: DATA[BLANK/NULL] - NAO FOI POSSIVEL ATUALIZAR SEU VÔO !");
           System.err.println("SISTEMA NÃO EXECUTOU POIS HAVIA DADOS NÃO COMPATIVEIS CONFORME INFORMADO:");
       }
       } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
       }
    
    
    
    }
    
    private void listarVendas(){
       VendaDao vendaDao;
        vendaDao = new VendaDaoBD();
       
       List<Venda> listaVenda = vendaDao.listar();
        this.mostrarListaVenda(listaVenda);

    }

    private void mostrarListaVenda(List<Venda> listaVenda) {
        
        if (listaVenda.isEmpty()) {
            System.out.println("Voos nao encontrados!");
        } else {
            System.out.println("-----------------------------\n");
            
            System.out.println(String.format("%-10s", "ID: ") + "\t"
          + String.format("%-20s", "|HORARIO:") + "\t"
          + String.format("%-20s", "|CLIENTE:") + "\t"
          + String.format("%-20s", "|VOO:"));
            for (Venda venda : listaVenda) {
                System.out.println(String.format("%-10s", venda.getId()) + "\t"
                                 + String.format("%-20s", "|" + venda.getHora_de_venda()) + "\t"
                                 + String.format("%-20s", "|" + venda.getCliente()) + "\t"
                                 + String.format("%-20s", "|" + venda.getVoo()));
            }
        }
        
    }
    
    
    
}
/*
Tema 3: Venda de Passagem Aérea

Funcionalidades:

- Venda de passagens: 
registra uma venda, 
relacionando o cliente, 
vôo 
horário da compra, 
realizando o controle da quantidade de assentos.

- Relatórios: 
vendas por cliente
cidades de origem e destino
períodos de vôos
vôos que mais usam determinado avião,
clientes que mais compram passagens.


Para atingir o conceito B, é necessário realizar todas as funcionalidades solicitadas com persistência em banco de dados e ter uma preocupação com a parte visual da interface do usuário (ainda em console) e com o código. Por isso, é necessário:

- Todas as funcionalidades, exceto os relatórios (CRUDs, funcionalidades), funcionando corretamente com persistência;

- Interface com usuário (via console) adequada para toda a aplicação;

- Validações para não cadastrar dados repetidos e não aceitar entradas erradas (tipo diferente de dados, por exemplo);

- Utilização dos conceitos de orientação a objetos de maneira apropriada: cuidados com encapsulamento, coesão e acoplamento, e principalmente não confundir modelo OO com modelo relacional;

- Legibilidade de código (estruturação correta, pacotes, etc)
*/