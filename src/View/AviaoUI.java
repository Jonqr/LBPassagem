/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dao.AviaoDao;
import DaoBD.AviaoDaoBD;
import Dominio.Aviao;
import Util.Console;
import Util.StringUtils;
import ViewMenu.AviaoMenu;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author jonh_
 */
public class AviaoUI {
    private AviaoDao aviaoDao;
    
    public AviaoUI() {
        aviaoDao = new AviaoDaoBD();
    }

    public void menu() {
        int opcao = -1;
        do {
            try {
                System.out.println(AviaoMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case AviaoMenu.OP_CADASTRAR:
                        cadastrarAviao();
                        break;
                    case AviaoMenu.OP_DELETAR:
                        deletarAviao();
                        break;
                    case AviaoMenu.OP_ATUALIZAR:
                        atualizarAviao();
                        break;
                    case AviaoMenu.OP_LISTAR:
                        mostrarAvioes();
                        break;
                    case AviaoMenu.OP_CONSULTAR_CODIGO:
                        consultarAviaoPorCodigo();
                        break;
                    case AviaoMenu.OP_CONSULTAR_NOME:
                        consultarAviaoPorNome();
                        break;
                    case AviaoMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != AviaoMenu.OP_SAIR);
    }

    private void cadastrarAviao() {
        try{    
        String cod = Console.scanString("Codigo: ");
        String nome = Console.scanString("Nome: ");
        int qtdAssento = Console.scanInt("Quantidade Assentos: ");
        if(StringUtils.isNullOrBlank(cod)==false||StringUtils.isNullOrEmpty(cod)==false||
           StringUtils.isNullOrBlank(nome)==false||StringUtils.isNullOrEmpty(nome)==false||
           qtdAssento>0){
            
               if (aviaoDao.procurarPorNome(nome)==null){
                   if (aviaoDao.procurarPorCodigo(cod)==null) {
                       aviaoDao.salvar(new Aviao(cod, nome,qtdAssento ));
                       System.out.println("Aviao " + nome + " cadastrado com sucesso!");
                   }else{System.err.println("AVIAO JA EXISTE!");}     
                   
            }else{
                   System.err.println("AVIAO JA EXISTE!");
           }
        }else{
            System.err.println("ERRO [NULL/BLANK] - Nao foi possivel cadastrar aviao!");
            System.err.println("Codigo: "+cod+"\n"
                              +"Nome: "+nome+ "\n"
                              +"Assentos"+ qtdAssento+ "\n");       
        }  
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }

    public void mostrarAvioes() {
        List<Aviao> listaAviaos = aviaoDao.listar();
        this.mostrarAvioes(listaAviaos);
    }

    private void deletarAviao() {
        try{
        mostrarAvioes();
        String cod = Console.scanString("RG do aviao a ser deletado: ");
        if(StringUtils.isNullOrBlank(cod)||StringUtils.isNullOrEmpty(cod)){
        Aviao avi = aviaoDao.procurarPorCodigo(cod);
        this.mostrarAviao(avi);
        if (UIUtil.getConfirmacao("Realmente deseja excluir esse aviao?")) {
            aviaoDao.deletar(avi);
            System.out.println("Aviao deletado com sucesso!");
        } else {
            System.out.println("Operacao cancelada!");
        }
        }else{
            System.err.println("Atenção ! - Não foi possivel remover avião selecionado! ");
            System.err.println("Aviao - codigo: "+cod+" - não foi encontrato");
        }
        
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }

    private void atualizarAviao() {
        try{
        
        this.mostrarAvioes();
        String cod = Console.scanString("Codigo do aviao a ser alterado: ");
        Aviao pac = aviaoDao.procurarPorCodigo(cod);
        this.mostrarAviao(pac);
        System.out.println("Digite os dados do aviao que quer alterar [Vazio caso nao queira]");       
        String codigo = Console.scanString("codigo: ");
        String nome = Console.scanString("Nome: ");
        Integer qtdAssento = Console.scanInt("Quantidade Assentos");
        
        if(aviaoDao.procurarPorCodigo(cod)==null||aviaoDao.procurarPorNome(nome)==null){
        if (!nome.isEmpty()) {
            pac.setNome(nome);
        }
        if (!codigo.isEmpty()) {
            pac.setCodigo(codigo);
        }
        if(qtdAssento != 0){
            pac.setQtdAssento(qtdAssento);
        }
        aviaoDao.atualizar(pac);
        System.out.println("Aviao " + nome + " atualizado com sucesso!");  
        }else{System.err.println("Aviao já existe ! ");}
        
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
    }
    
    private void consultarAviaoPorNome(){
    
        String nome = Console.scanString("Nome: ");
        Aviao aviao = aviaoDao.procurarPorNome(nome);
        this.mostrarAviao(aviao);
    
    }

    private void consultarAviaoPorCodigo(){
    
        String nome = Console.scanString("Codigo: ");
        Aviao aviao = aviaoDao.procurarPorCodigo(nome);
        this.mostrarAviao(aviao);
    
    }
       
    private void mostrarAviao(Aviao p) {
        System.out.println("-----------------------------");
        System.out.println("Aviao");
        System.out.println("codigo:: " + p.getCodigo());
        System.out.println("Nome: " + p.getNome());
        System.out.println("Qtd Assentos : "
                + p.getQtdAssento());
        System.out.println("-----------------------------");
    }

    private void mostrarAvioes(List<Aviao> listaAviaos) {
        if (listaAviaos.isEmpty()) {
            System.out.println("Aviaos nao encontrados!");
        } else {
            System.out.println("-----------------------------\n");
            System.out.println(String.format("%-10s", "COD") + "\t"
                    + String.format("%-20s", "|NOME") + "\t"
                    + String.format("%-20s", "|QTD ASSENTO"));
            for (Aviao aviao : listaAviaos) {
                System.out.println(String.format("%-10s", aviao.getCodigo()) + "\t"
                        + String.format("%-20s", "|" + aviao.getNome()) + "\t"
                        + String.format("%-20s", "|" + aviao.getQtdAssento()));
            }
        }
    }
}
