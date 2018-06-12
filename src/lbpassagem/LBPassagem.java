/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lbpassagem;

/**
 *
 * @author jonh_
 */
public class LBPassagem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        new View.MainUI().menu();
        // TODO code application logic here
    }
    
}
/*
- Todas as funcionalidades, exceto os relatórios (CRUDs, funcionalidades), funcionando corretamente com persistência;

- Interface com usuário (via console) adequada para toda a aplicação;

- Validações para não cadastrar dados repetidos e não aceitar entradas erradas (tipo diferente de dados, por exemplo);

- Utilização dos conceitos de orientação a objetos de maneira apropriada: cuidados com encapsulamento, coesão e acoplamento, e principalmente não confundir modelo OO com modelo relacional;

- Legibilidade de código (estruturação correta, pacotes, etc)


*/