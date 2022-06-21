package gerenciadores.entidade;

import entidades.Aluno;
import entidades.Curso;
import gerenciadores.banco.GerenciadorAlunoBanco;
import gerenciadores.banco.GerenciadorCursoBanco;
import interfaces.GerenciadorBanco;
import interfaces.GerenciadorEntidades;
import utilsPersistencia.ConectorBanco;
import utilsPersistencia.GerenciadorRelacionamentoBanco;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorCurso implements GerenciadorEntidades<Curso> {
    private final GerenciadorBanco<Curso> bancoCurso;
    private final GerenciadorRelacionamentoBanco relacionador;
    private static List<Curso> cursos;

    public GerenciadorCurso(){
        this.bancoCurso = new GerenciadorCursoBanco();
        this.relacionador = new GerenciadorRelacionamentoBanco();
        cursos = new ArrayList<>();
    }

    @Override
    public void cadastrar(Curso entidade) {
        bancoCurso.inserir(entidade);
    }

    @Override
    public Curso retornarEntidade(int chave) {
        Curso cursoRetorno;
        if(!cursos.isEmpty() && cursos.stream().anyMatch(curso -> curso.getCodigo() == chave))
            cursoRetorno = cursos.stream().filter(curso -> curso.getCodigo() == chave).findFirst().get();
        else
            cursoRetorno = bancoCurso.buscarUm(chave);

        cursoRetorno.setAlunos_matriculados(relacionador.buscarAlunosCurso(chave));
        return cursoRetorno;
    }

    @Override
    public Curso retornarEndidade(String chave) {
        return retornarEntidade(Integer.parseInt(chave));
    }

    @Override
    public List<Curso> retornarEntidades() {
        List<Curso> listaBanco = bancoCurso.buscarTodos();

        for(Curso curso : listaBanco){
            curso.setAlunos_matriculados(relacionador.buscarAlunosCurso((int)curso.getCodigo()));
        }

        this.cursos = listaBanco;
        return listaBanco;
    }
}
