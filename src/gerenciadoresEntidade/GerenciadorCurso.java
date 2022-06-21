package gerenciadoresEntidade;

import entidades.Curso;
import interfaces.GerenciadorEntidades;
import utilsPersistencia.ConectorBanco;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorCurso implements GerenciadorEntidades<Curso> {
    private final ConectorBanco conector;
    private static List<Curso> cursos;

    public GerenciadorCurso(){
        this.conector = new ConectorBanco();
        cursos = new ArrayList<>();
    }

    @Override
    public void cadastrar(Curso entidade) {
        conector.inserirCurso(entidade);
    }

    @Override
    public Curso retornarEntidade(int chave) {
        Curso cursoRetorno;
        if(!cursos.isEmpty() && cursos.stream().anyMatch(curso -> curso.getCodigo() == chave))
            cursoRetorno = cursos.stream().filter(curso -> curso.getCodigo() == chave).findFirst().get();
        else
            cursoRetorno = conector.buscarCurso(chave);

        cursoRetorno.setAlunos_matriculados(conector.buscarAlunosCurso(chave));
        return cursoRetorno;
    }

    @Override
    public Curso retornarEndidade(String chave) {
        return retornarEntidade(Integer.parseInt(chave));
    }

    @Override
    public List<Curso> retornarEntidades() {
        List<Curso> listaBanco = conector.buscarTodosCursos();

        for(Curso curso : listaBanco){
            curso.setAlunos_matriculados(conector.buscarAlunosCurso((int)curso.getCodigo()));
        }

        this.cursos = listaBanco;
        return listaBanco;
    }
}
