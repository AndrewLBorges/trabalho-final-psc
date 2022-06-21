package gerenciadores.entidade;

import entidades.Professor;
import gerenciadores.banco.GerenciadorProfessorBanco;
import interfaces.GerenciadorBanco;
import interfaces.GerenciadorEntidades;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorProfessor implements GerenciadorEntidades<Professor> {
    private final GerenciadorBanco<Professor> bancoProfessor;
    private static List<Professor> professores;

    public GerenciadorProfessor(){
        this.bancoProfessor = new GerenciadorProfessorBanco();
        professores = new ArrayList<>();
    }

    @Override
    public void cadastrar(Professor entidade) {
        bancoProfessor.inserir(entidade);
    }

    @Override
    public Professor retornarEntidade(int chave) {
        if(!professores.isEmpty() && professores.stream().anyMatch(professor -> professor.getCodigo_funcionario() == chave))
            return professores.stream().filter(professor -> professor.getCodigo_funcionario() == chave).findFirst().get();
        return bancoProfessor.buscarUm(chave);
    }

    @Override
    public Professor retornarEndidade(String chave) {
        return retornarEntidade(Integer.parseInt(chave));
    }

    @Override
    public List<Professor> retornarEntidades() {
        List<Professor> listaBanco = bancoProfessor.buscarTodos();
        this.professores = listaBanco;
        return listaBanco;
    }
}
