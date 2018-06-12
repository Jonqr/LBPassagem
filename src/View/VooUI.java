/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Dao.AviaoDao;
import Dao.VooDao;
import DaoBD.AviaoDaoBD;
import DaoBD.VooDaoBd;
import Dominio.Aviao;
import Dominio.Voo;
import Dominio.Voo;
import Util.Console;
import Util.StringUtils;
import ViewMenu.VooMenu;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.List;
/**
 *
 * @author jonh_
 */

public class VooUI {

    private VooDao vooDao;
    
    public VooUI() {
        vooDao = new VooDaoBd();
    }
   public void menu() {
        int opcao = -1;
        do {
            try {
                System.out.println(VooMenu.getOpcoes());
                opcao = Console.scanInt("Digite sua opção:");
                switch (opcao) {
                    case VooMenu.OP_CADASTRAR:
                        cadastrarVoo();
                        break;
                    case VooMenu.OP_DELETAR:
                        deletarVoo();
                        break;
                    case VooMenu.OP_ATUALIZAR:
                        atualizarVoo();
                        break;
                    case VooMenu.OP_LISTAR:
                        mostrarVoos();
                        break;
                    case VooMenu.OP_CONSULTAR_HORA:
                        consultarVooPorHorario();
                        break;
                    case VooMenu.OP_CONSULTAR_DESTINO:
                        consultarVooPorDestino();
                        break;
                    case VooMenu.OP_CONSULTAR_ORIGEM:
                        consultarVooPorOrigem();
                        break;
                    case VooMenu.OP_SAIR:
                        System.out.println("Finalizando a aplicacao..");
                        break;
                    default:
                        System.out.println("Opção inválida..");
                }
            } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
            }

        } while (opcao != VooMenu.OP_SAIR);
    }
    
   private void cadastrarVoo(){
       AviaoDao avv; 
       avv = new AviaoDaoBD();
       try{
       String origem = Console.scanString("origem :");
       String destino = Console.scanString("destino :");
       String horario= Console.scanString("horario :");
       new AviaoUI().mostrarAvioes();
       String codAviao = Console.scanString("Cod Aviao: ");  
       if(StringUtils.isNullOrBlank(origem)==false||StringUtils.isNullOrEmpty(origem)==false
         ||StringUtils.isNullOrBlank(destino)==false||StringUtils.isNullOrEmpty(destino)==false
         ||StringUtils.isNullOrBlank(horario)==false||StringUtils.isNullOrEmpty(horario)==false
         ||StringUtils.isNullOrBlank(codAviao)==false||StringUtils.isNullOrEmpty(codAviao)==false){
           Aviao aviao = avv.procurarPorCodigo(codAviao);
           vooDao.salvar(new Voo(aviao,origem,destino,LocalTime.parse(horario)));
           System.out.println("Voo [ "+aviao +"] horario: "+horario + "Origem: " +origem+ "e Destino: "+destino+", cadastrado com sucesso!");
       }else{
           System.err.println("ERRO: DATA[BLANK/NULL] - NAO FOI POSSIVEL CADASTRAR SEU VÔO !");
           System.err.println("SISTEMA NÃO EXECUTOU POIS HAVIA DADOS NÃO COMPATIVEIS CONFORME INFORMADO:");
           System.err.println("Origem: "+origem + "\n"
                             +"Destino"+destino+"\n"
                             +"Horario:"+horario+"\n"
                             +"Index Aviao: "+codAviao+"\n"); 
       }
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
        }
   }
   public void mostrarVoos(){
       
       List<Voo> listaVoo = vooDao.listar();
        this.mostrarListaVoos(listaVoo);
   }
 
   private void deletarVoo(){
       mostrarVoos();
       try{
           
       vooDao.listar();
       int id = Console.scanInt("Informe o ID para remover o Voo:");
       Voo voo = vooDao.procurarPorId(id);
       if (UIUtil.getConfirmacao("Realmente deseja excluir esse Voo?")) {
            vooDao.deletar(voo);
            System.out.println("Voo deletado com sucesso!");
        } else {
            System.out.println("Operacao cancelada!");
        }
       } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
        }
   }

   private void atualizarVoo(){
       AviaoDao aviaoDao;
       aviaoDao = new AviaoDaoBD();
       mostrarVoos();
       try{
           
           vooDao.listar();
       int id = Console.scanInt("Informe o ID do Voo que deseja alterar:");
       Voo voo = vooDao.procurarPorId(id);
       System.out.println("Digite os dados do voo que deseja alterar[Vazio caso nao queira]");
       String origem = Console.scanString("Origem");
       String destino = Console.scanString("Destino");
       String horario= Console.scanString("Horario");
       String aviao = Console.scanString("Cod do Aviao:");
       
       if(StringUtils.isNullOrBlank(origem)==false||StringUtils.isNullOrEmpty(origem)==false
         ||StringUtils.isNullOrBlank(destino)==false||StringUtils.isNullOrEmpty(destino)==false
         ||StringUtils.isNullOrBlank(horario)==false||StringUtils.isNullOrEmpty(horario)==false
         ||StringUtils.isNullOrBlank(aviao)==false||StringUtils.isNullOrEmpty(aviao)==false){
           
       if (!origem.isEmpty()) {
            voo.setOrigem(origem);
        }
       if (!destino.isEmpty()) {
            voo.setDestino(destino);
        }
       if (!horario.isEmpty()) {
            voo.setHorario(LocalTime.parse(horario));
        }
       if (!aviao.isEmpty()) {
            voo.setAviao(aviaoDao.procurarPorCodigo(aviao));
        }
       vooDao.atualizar(voo);
       System.out.println("Voo atualizado com sucesso!");
       }else{
           System.err.println("ERRO: DATA[BLANK/NULL] - NAO FOI POSSIVEL ATUALIZAR SEU VÔO !");
           System.err.println("SISTEMA NÃO EXECUTOU POIS HAVIA DADOS NÃO COMPATIVEIS CONFORME INFORMADO:");
           System.err.println("Origem: "+origem + "\n"
                             +"Destino"+destino+"\n"
                             +"Horario:"+horario+"\n"
                             +"Index Aviao: "+aviao+"\n"); 
       }
       } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente numeros sao permitidos!");
       }
   }
     
   private void consultarVooPorHorario(){
       try{
       String horario = Console.scanString("Horario: ");
        List<Voo> listaVoo = vooDao.listarPorHorario(horario);
        this.mostrarListaVoos(listaVoo);
        } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
        }
   }
     
   private void consultarVooPorDestino(){ 
       try{
       String Destino = Console.scanString("Destino: ");
       List<Voo> listaVoo = vooDao.listarPorDestino(Destino);
       this.mostrarListaVoos(listaVoo);  
       } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
       }  
   }
   
   private void consultarVooPorOrigem(){
       try{
       String Origem = Console.scanString("Origem: ");
       List<Voo> listaVoo = vooDao.listarPorOrigem(Origem);
       this.mostrarListaVoos(listaVoo);
       } catch (InputMismatchException ex) {
                UIUtil.mostrarErro("Somente caracteres sao permitidos!");
       }
   
   }
   
   private void mostraraVoo(Voo voo) {
        System.out.println("-----------------------------");
        System.out.println("Voo:");
        System.out.println("Horario: " + voo.getHorario());
        System.out.println("Aviao: " + voo.getAviao());
        System.out.println("Origem: " + voo.getOrigem());
        System.out.println("Destino: " + voo.getDestino());    
        System.out.println("-----------------------------");
    }
    
   private void mostrarListaVoos(List<Voo> listaVoo) {
        
       if (listaVoo.isEmpty()) {
            System.out.println("Voos nao encontrados!");
        } else {
            System.out.println("-----------------------------\n");
            
            System.out.println(String.format("%-10s", "ID: ") + "\t"
          + String.format("%-20s", "|HORARIO:") + "\t"
          + String.format("%-20s", "|ORIGEM:") + "\t"
          + String.format("%-20s", "|DESTINO:") + "\t"
          + String.format("%-20s", "|AVIAO:"));
            for (Voo voo : listaVoo) {
                System.out.println(String.format("%-10s", voo.getId()) + "\t"
                                 + String.format("%-20s", "|" + voo.getHorario()) + "\t"
                                 + String.format("%-20s", "|" + voo.getOrigem()) + "\t"
                                 + String.format("%-20s", "|" + voo.getDestino()) + "\t"
                                 + String.format("%-20s", "|" + voo.getAviao()));
            }
        }
    }
   
 
}
