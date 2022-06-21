package gerenciadores.entidade;

import entidades.Aluno;
import gerenciadores.banco.GerenciadorAlunoBanco;
import interfaces.GerenciadorBanco;
import interfaces.GerenciadorEntidades;
import utilsPersistencia.ConectorBanco;
import utilsPersistencia.GerenciadorRelacionamentoBanco;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorAluno implements GerenciadorEntidades<Aluno> {
    private final GerenciadorBanco<Aluno> bancoAluno;
    private final GerenciadorRelacionamentoBanco relacionador;
    private static List<Aluno> alunos;

    public GerenciadorAluno(){
        this.bancoAluno = new GerenciadorAlunoBanco();
        this.relacionador = new GerenciadorRelacionamentoBanco();
        alunos = new ArrayList<>();
    }

    @Override
    public void cadastrar(Aluno entidade) {
        bancoAluno.inserir(entidade);
    }

    @Override
    public Aluno retornarEntidade(int chave) {
        Aluno alunoRetorno;
        if(!alunos.isEmpty() && alunos.stream().anyMatch(aluno -> aluno.getMatricula() == chave))
            alunoRetorno = alunos.stream().filter(aluno -> aluno.getMatricula() == chave).findFirst().get();
        else
            alunoRetorno = bancoAluno.buscarUm(chave);

        alunoRetorno.setCursos_registrados(relacionador.buscarCursosAluno(chave));
        return alunoRetorno;
    }

    @Override
    public Aluno retornarEndidade(String chave) {
        return retornarEntidade(Integer.parseInt(chave));
    }

    @Override
    public List<Aluno> retornarEntidades() {
        List<Aluno> listaBanco = bancoAluno.buscarTodos();

        for(Aluno aluno : listaBanco){
            aluno.setCursos_registrados(relacionador.buscarCursosAluno((int)aluno.getMatricula()));
        }

        this.alunos = listaBanco;
        return listaBanco;
    }
}
