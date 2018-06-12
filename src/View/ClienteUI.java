/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dao.ClienteDao;
import DaoBD.ClienteDaoBD;
import Dominio.Cliente;
import Util.Console;
import Util.StringUtils;
import ViewMenu.ClienteMenu;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author jonh_
 */
public class ClienteUI {
    private ClienteDao clienteDao;
    
    public ClienteUI() {
        clienteDao = new ClienteDaoBD();
    }
    
    public void menu() {
        int opcao = -1;
        do {
            try {
                System.out.println(ClienteMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case ClienteMenu.OP_CADASTRAR:
                        cadastrarCliente();
                        break;
                    case ClienteMenu.OP_DELETAR:
                        deletarCliente();
                        break;
                    case ClienteMenu.OP_ATUALIZAR:
                        atualizarCliente();
                        break;
                    case ClienteMenu.OP_LISTAR:
                        mostrarClientes();
                        break;
                    case ClienteMenu.OP_CONSULTAR:
                        consultarClientesPorNome();
                        break;
                    case ClienteMenu.OP_CONSULTAR_RG:
                        consultarClientesPorRG();
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
    
    private void cadastrarCliente() {
        
        try{
        String rg = Console.scanString("RG: ");
        String nome = Console.scanString("Nome: ");
        String telefone = Console.scanString("Telefone: ");
        
            if(StringUtils.isNullOrBlank(rg)==false||StringUtils.isNullOrEmpty(rg)==false
            ||StringUtils.isNullOrBlank(nome)==false||StringUtils.isNullOrEmpty(nome)==false){               
                if(clienteDao.procurarPorRg(rg) == null){ 
                clienteDao.salvar(new Cliente( nome,rg, telefone));
                System.out.println("Cliente " + nome + " cadastrado com sucesso!");                
                }else{
                    System.err.println("ATENÇÃO - CLIENTE EXISTENTE !");
                }                
            }else{            
           System.err.println("ERRO: DATA[BLANK/NULL] - NAO FOI POSSIVEL CADASTRAR CLIENTE !");
           System.err.println("SISTEMA NÃO EXECUTOU POIS HAVIA DADOS NÃO COMPATIVEIS CONFORME INFORMADO:");
           System.err.println("RG: "+rg + "\n"
                             +"NOME"+nome+"\n"
                             +"TELEFONE:"+telefone+"\n"); 
           }

        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }
    
    public void mostrarClientes() {
        List<Cliente> listaClientes = clienteDao.listar();
        this.mostrarClientes(listaClientes);
    }
    
    private void deletarCliente() {
        try{
        String rg = Console.scanString("RG do cliente a ser deletado: ");
        Cliente cliente = clienteDao.procurarPorRg(rg);
        this.mostrarCliente(cliente);
        if (UIUtil.getConfirmacao("Realmente deseja excluir esse cliente?")) {
            clienteDao.deletar(cliente);
            System.out.println("Cliente deletado com sucesso!");
        } else {
            System.out.println("Operacao cancelada!");
        }
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }

    private void atualizarCliente() {   
        try{
        String rg = Console.scanString("RG do cliente a ser alterado: ");
        Cliente pac = clienteDao.procurarPorRg(rg);
        this.mostrarCliente(pac);  
        System.out.println("Digite os dados do cliente que quer alterar [Vazio caso nao queira]");
        String rg2 = Console.scanString("Rg: ");
        String nome = Console.scanString("Nome: ");
        String telefone = Console.scanString("telefone: ");
        
        if(StringUtils.isNullOrBlank(rg)==false||StringUtils.isNullOrEmpty(rg)==false
           ||StringUtils.isNullOrBlank(nome)==false||StringUtils.isNullOrEmpty(nome)==false){
            if(clienteDao.procurarPorRg(rg).getRg()== null){
            if(!rg2.isEmpty()){
            pac.setRg(rg2);
            }
            if (!nome.isEmpty()) {
            pac.setNome(nome);
            }
            pac.setTelefone(telefone);           
            clienteDao.atualizar(pac);
            System.out.println("Cliente " + nome + " atualizado com sucesso!");
            }else{           
                System.err.println("CLIENTE JÁ EXISTENTE !!!");
            }
            }else{
            
           System.err.println("ERRO: DATA[BLANK/NULL] - NAO FOI POSSIVEL ATUALIZAR CLIENTE!");
           System.err.println("SISTEMA NÃO EXECUTOU POIS HAVIA DADOS NÃO COMPATIVEIS CONFORME INFORMADO:");
           System.err.println("RG: "+rg + "\n"
                             +"NOME"+nome+"\n"
                             +"TELEFONE:"+telefone+"\n"); 
           }       
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }

    private void consultarClientesPorNome() {
       try{
        String nome = Console.scanString("Nome: ");
        List<Cliente> listaCliente = clienteDao.listarPorNome(nome);
        this.mostrarClientes(listaCliente);
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }

    private void consultarClientesPorRG(){
        try{
        String rg = Console.scanString("RG: ");
        Cliente cliente = clienteDao.procurarPorRg(rg);
        this.mostrarCliente(cliente);
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }
    
    private void mostrarCliente(Cliente p) {
        
        p = clienteDao.procurarPorRg(p.getRg());
        System.out.println("-----------------------------");
        System.out.println("Cliente");
        System.out.println("RG: " + p.getRg());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Telefone: "
                + p.getTelefone());
        System.out.println("-----------------------------");
    }

    private void mostrarClientes(List<Cliente> listaClientes) {
        if (listaClientes.isEmpty()) {
            System.out.println("Clientes nao encontrados!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "RG") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|TELEFONE"));
            for (Cliente cliente : listaClientes) {
                System.out.println(String.format("%-10s", cliente.getRg()) + "\t"
                        + String.format("%-20s", "|" + cliente.getNome()) + "\t"
                        + String.format("%-20s", "|" + cliente.getTelefone()));
            }
        }
    }
}
