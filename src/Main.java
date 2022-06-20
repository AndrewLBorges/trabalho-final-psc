import gerenciadoresEntidade.GerenciadorAluno;
import gerenciadoresEntidade.GerenciadorProfessor;
import gerenciadoresEntidade.GerenciadorSala;

public class Main {
    public static void main(String[] args) {
        var gerenciadorAlunos = new GerenciadorAluno();
        var gerenciadorProfessores = new GerenciadorProfessor();
        var gerenciadorSalas = new GerenciadorSala();

        var sistema = new SistemaCursos(gerenciadorAlunos, gerenciadorProfessores, gerenciadorSalas);

        sistema.iniciarSistema();
    }
}
