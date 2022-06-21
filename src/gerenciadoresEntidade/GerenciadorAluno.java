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
        Aluno alunoRetorno;
        if(!alunos.isEmpty() && alunos.stream().anyMatch(aluno -> aluno.getMatricula() == chave))
            alunoRetorno = alunos.stream().filter(aluno -> aluno.getMatricula() == chave).findFirst().get();
        else
            alunoRetorno = conector.buscarAluno(chave);

        alunoRetorno.setCursos_registrados(conector.buscarCursosAluno(chave));
        return alunoRetorno;
    }

    @Override
    public Aluno retornarEndidade(String chave) {
        return retornarEntidade(Integer.parseInt(chave));
    }

    @Override
    public List<Aluno> retornarEntidades() {
        List<Aluno> listaBanco = conector.buscarTodosAlunos();

        for(Aluno aluno : listaBanco){
            aluno.setCursos_registrados(conector.buscarCursosAluno((int)aluno.getMatricula()));
        }

        this.alunos = listaBanco;
        return listaBanco;
    }
}
