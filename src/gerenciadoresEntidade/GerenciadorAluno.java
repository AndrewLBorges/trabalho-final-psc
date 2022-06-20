package gerenciadoresEntidade;

import entidades.Aluno;
import interfaces.GerenciadorEntidades;
import utilsPersistencia.ConectorBanco;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorAluno implements GerenciadorEntidades<Aluno> {
    private final ConectorBanco conector;
    private static List<Aluno> alunos;

    public GerenciadorAluno(){
        this.conector = new ConectorBanco();
        alunos = new ArrayList<>();
    }

    @Override
    public void cadastrar(Aluno entidade) {
        conector.inserirAluno(entidade);
    }

    @Override
    public Aluno retornarEntidade(int chave) {
        if(!alunos.isEmpty() && alunos.stream().anyMatch(aluno -> aluno.getMatricula() == chave))
            return alunos.stream().filter(aluno -> aluno.getMatricula() == chave).findFirst().get();
        return conector.buscarAluno(chave);
    }

    @Override
    public Aluno retornarEndidade(String chave) {
        return retornarEntidade(Integer.parseInt(chave));
    }

    @Override
    public List<Aluno> retornarEntidades() {
        List<Aluno> listaBanco = conector.buscarTodosAlunos();
        this.alunos = listaBanco;
        return listaBanco;
    }
}
