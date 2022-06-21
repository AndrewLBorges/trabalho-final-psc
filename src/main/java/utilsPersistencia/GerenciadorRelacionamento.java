package utilsPersistencia;

public class GerenciadorRelacionamento {
    private final GerenciadorRelacionamentoBanco gerenciadorBanco;

    public GerenciadorRelacionamento(){
        this.gerenciadorBanco = new GerenciadorRelacionamentoBanco();
    }

    public void relacionarSalaCurso(int idCurso, String nomeSala){
        gerenciadorBanco.alocarSalaAoCurso(idCurso, nomeSala);
    }

    public void relacionarProfessorCurso(int idCurso, int idFuncionario){
        gerenciadorBanco.alocarProfessorAoCurso(idCurso, idFuncionario);
    }

    public void relacionarAlunoCurso(int idCurso, int idAluno){
        gerenciadorBanco.matricularAlunoAoCurso(idCurso, idAluno);
    }
}
