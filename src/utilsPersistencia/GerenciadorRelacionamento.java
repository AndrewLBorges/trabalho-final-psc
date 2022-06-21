package utilsPersistencia;

public class GerenciadorRelacionamento {
    private final ConectorBanco conector;

    public GerenciadorRelacionamento(){
        this.conector = new ConectorBanco();
    }

    public void relacionarSalaCurso(int idCurso, String nomeSala){
        conector.alocarSalaAoCurso(idCurso, nomeSala);
    }

    public void relacionarProfessorCurso(int idCurso, int idFuncionario){
        conector.alocarProfessorAoCurso(idCurso, idFuncionario);
    }

    public void relacionarAlunoCurso(int idCurso, int idAluno){
        conector.matricularAlunoAoCurso(idCurso, idAluno);
    }
}
