package entidades;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private long matricula;
    private List<Curso> cursos_matriculados;

    public Aluno(String nome_completo, long cpf, String endereco, String email, String celular, long matricula){
        this.setNome_completo(nome_completo);
        this.setEndereco(endereco);
        this.setEndereco(email);
        this.setCelular(celular);
        this.matricula = matricula;
        this.cursos_matriculados = new ArrayList<>();
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public void registrarCursoMatriculado(Curso curso){
        this.cursos_matriculados.add(curso);
    }
}
