import gerenciadoresEntidade.GerenciadorAluno;
import gerenciadoresEntidade.GerenciadorCurso;
import gerenciadoresEntidade.GerenciadorProfessor;
import gerenciadoresEntidade.GerenciadorSala;

public class Main {
    public static void main(String[] args) {
        var gerenciadorAlunos = new GerenciadorAluno();
        var gerenciadorProfessores = new GerenciadorProfessor();
        var gerenciadorSalas = new GerenciadorSala();
        var gerenciadorCursos = new GerenciadorCurso();

        var sistema = new SistemaCursos(gerenciadorAlunos, gerenciadorProfessores, gerenciadorSalas, gerenciadorCursos);

        sistema.iniciarSistema();
    }
}
