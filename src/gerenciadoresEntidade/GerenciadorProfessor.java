package gerenciadoresEntidade;

import entidades.Aluno;
import entidades.Professor;
import interfaces.GerenciadorEntidades;
import utilsPersistencia.ConectorBanco;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorProfessor implements GerenciadorEntidades<Professor> {
    private final ConectorBanco conector;
    private static List<Professor> professores;

    public GerenciadorProfessor(){
        this.conector = new ConectorBanco();
        professores = new ArrayList<>();
    }

    @Override
    public void cadastrar(Professor entidade) {
        conector.inserirProfessor(entidade);
    }

    @Override
    public Professor retornarEntidade(int chave) {
        if(!professores.isEmpty() && professores.stream().anyMatch(professor -> professor.getCodigo_funcionario() == chave))
            return professores.stream().filter(professor -> professor.getCodigo_funcionario() == chave).findFirst().get();
        return conector.buscarProfessor(chave);
    }

    @Override
    public Professor retornarEndidade(String chave) {
        return retornarEntidade(Integer.parseInt(chave));
    }

    @Override
    public List<Professor> retornarEntidades() {
        List<Professor> listaBanco = conector.buscarTodosProfessores();
        this.professores = listaBanco;
        return listaBanco;
    }
}
