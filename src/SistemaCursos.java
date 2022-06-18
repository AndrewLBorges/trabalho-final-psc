import entidades.*;
import interfaces.Validador;
import utils.OpcaoMenu;
import utils.UtilEntrada;
import validadores.ValidadorEmail;
import javax.swing.*;

public class SistemaCursos {
    private Validador validador_email;
    private Curso curso;

    public SistemaCursos(){
        this.validador_email = new ValidadorEmail();
    }

    public void iniciarSistema(){
        OpcaoMenu opcao = OpcaoMenu.SEM_OPCAO;

        mostrarBoasVindas();

        while(opcao != OpcaoMenu.SAIR){
            mostrarMenu();
            opcao = obterResultadoMenu();
            realizarAcaoMenu(opcao);
            opcao = OpcaoMenu.SAIR;
        }
        mostrarMensagemSaida();
    }

    private void mostrarBoasVindas(){
        JOptionPane.showMessageDialog(null, "----------------- Bem-vindo(a) ao sistema de cadastro de Cursos -----------------");
    }

    private void mostrarMenu(){
        System.out.println("Escolha uma das opções abaixo para continuar: \n");
        System.out.println("Cadastrar curso (1)");
        System.out.println("Cadastrar aluno (2)");
        System.out.println("Matricular aluno em um curso (3)");
        System.out.println("Cadastrar uma sala (4)");
        System.out.println("Cadastrar um professor (5)");
        System.out.println("Alocar um profesor a um curso (6)");
        System.out.println("Alocar uma sala a um curso (7)");
        System.out.println("Listar com detalhes todos os cursos cadastrados (8)");
        System.out.println("Sair (0)");
    }

    private void mostrarMensagemSaida(){
        System.out.println("\n\nObrigado por usar nosso sistema!!");
    }

    private OpcaoMenu obterResultadoMenu(){
        System.out.print("Digite sua opção: ");
        int opcao = -1;

        while (opcao <0 || opcao > 8){
            opcao = (int)UtilEntrada.entradaInteira();

            if(opcao < 0 || opcao > 8)
                System.out.print("\nEntrada Inválida. Tente novamente: ");
        }

        return OpcaoMenu.valueOf(opcao);
    }

    private void realizarAcaoMenu(OpcaoMenu opcao){

    }
}
