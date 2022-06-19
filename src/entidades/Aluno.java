package entidades;

import java.util.ArrayList;
import java.util.List;

public class Aluno extends Usuario {
    private long matricula;
    private List<Curso> cursos_registrados;

    public Aluno(String nome_completo, long cpf, String endereco, String email, String celular, long matricula){
        this.setNome_completo(nome_completo);
        this.setEndereco(endereco);
        this.setEndereco(email);
        this.setCelular(celular);
        this.matricula = matricula;
        this.cursos_registrados = new ArrayList<>();
    }

    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public boolean registrarCursoMatriculado(Curso curso){
        if(!cursoRegistrado(curso)) {
            this.cursos_registrados.add(curso);
            return true;
        }
        return false;
    }

    private boolean cursoRegistrado(Curso curso){
        return this.cursos_registrados
                .stream()
                .anyMatch(cursos_registrado -> cursos_registrado.getCodigo() == curso.getCodigo());
    }
}
