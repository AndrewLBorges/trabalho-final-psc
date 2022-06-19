package entidades;

import excecoes.AlunoException;

import java.util.ArrayList;
import java.util.List;

public class Curso {
    private long codigo;
    private String nome;
    private int carga_horaria;
    private String descricao;
    private List<Aluno> alunos_matriculados;
    private Professor professor;
    private Sala sala;

    public Curso(long codigo, String nome, int carga_horaria, String descricao) {
        this.codigo = codigo;
        this.nome = nome;
        this.carga_horaria = carga_horaria;
        this.descricao = descricao;
        this.alunos_matriculados = new ArrayList<>();
    }

    public long getCodigo() {
        return codigo;
    }

    public void setCodigo(long codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCarga_horaria() {
        return carga_horaria;
    }

    public void setCarga_horaria(int carga_horaria) {
        this.carga_horaria = carga_horaria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void matricularAluno(Aluno aluno) throws AlunoException {
        if(!alunoMatriculado(aluno) && !ultrapassouCapacidadeDaSala()){
            this.alunos_matriculados.add(aluno);
            return;
        }
        if(alunoMatriculado(aluno))
            throw new AlunoException("Aluno já matriculado!");

        throw new AlunoException("Capacidade de alunos da sala foi excedida!");
    }

    private boolean alunoMatriculado(Aluno aluno){
        return this.alunos_matriculados
                .stream()
                .anyMatch(aluno_matriculado -> aluno_matriculado.getMatricula() == aluno.getMatricula());
    }

    private boolean ultrapassouCapacidadeDaSala(){
        return alunos_matriculados.size() + 1 > sala.getCapacidade();
    }

    public boolean cadastrarProfessor(Professor professor){
        if (professor != null && !temProfessorCadastrado()){
            this.professor = professor;
            return true;
        }
        return false;
    }

    public boolean cadastrarSala(Sala sala){
        if (sala != null && !temSalaCadastrada()){
            this.sala = sala;
            return true;
        }
        return false;
    }

    public boolean temProfessorCadastrado(){
        return this.professor != null;
    }

    public boolean temSalaCadastrada(){
        return this.sala != null;
    }

    public Professor getProfessor(){
        return this.professor;
    }

    public Sala getSala(){
        return this.sala;
    }

    public List<Aluno> getAlunos_matriculados(){
        return this.alunos_matriculados;
    }
}
