package utilsPersistencia;

import entidades.Aluno;
import entidades.Curso;
import gerenciadores.banco.GerenciadorAlunoBanco;
import gerenciadores.banco.GerenciadorCursoBanco;
import interfaces.GerenciadorBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorRelacionamentoBanco {
    private ConectorBanco conectorBanco = new ConectorBanco();
    private Connection connection;
    private GerenciadorBanco<Curso> cursoBanco;
    private GerenciadorBanco<Aluno> alunoBanco;

    public GerenciadorRelacionamentoBanco(){
        this.connection = conectorBanco.getConnetion();
        this.alunoBanco = new GerenciadorAlunoBanco();
        this.cursoBanco = new GerenciadorCursoBanco();
    }

    public void alocarSalaAoCurso(int idCurso, String nomeSala){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("UPDATE Curso SET nome_sala = ? WHERE codigo = ?");
            statement.setString(1, nomeSala);
            statement.setInt(2, idCurso);
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao atualizar dado!");
        }
    }

    public void alocarProfessorAoCurso(int idCurso, int codigoFuncionario){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("UPDATE Curso SET id_professor = ? WHERE codigo = ?");
            statement.setInt(1, codigoFuncionario);
            statement.setInt(2, idCurso);
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao atualizar dado!");
        }
    }
    public void matricularAlunoAoCurso(int idCurso, int idAluno){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Curso_Aluno(id_curso,id_matricula_aluno) VALUES(?,?)");
            statement.setInt(1, idCurso);
            statement.setInt(2, idAluno);
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    public List<Curso> buscarCursosAluno(int idAluno){
        List<Curso> cursos = new ArrayList<>();
        PreparedStatement statement;

        try{
            Curso curso;
            statement = connection.prepareStatement("SELECT * FROM Curso_Aluno WHERE id_matricula_aluno = ?");
            statement.setInt(1, idAluno);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                var codigoCurso = result.getInt(1);

                curso =  cursoBanco.buscarUm(codigoCurso);
                cursos.add(curso);
            }
        }catch (SQLException exception){
            return null;
        }
        return cursos;
    }

    public List<Aluno> buscarAlunosCurso(int idCurso){
        List<Aluno> alunos = new ArrayList<>();
        PreparedStatement statement;

        try{
            Aluno aluno;
            statement = connection.prepareStatement("SELECT id_matricula_aluno FROM Curso_Aluno WHERE id_curso = ?");
            statement.setInt(1, idCurso);

            ResultSet result = statement.executeQuery();

            while(result.next()){
                var codigoCurso = result.getInt(1);

                aluno =  alunoBanco.buscarUm(codigoCurso);
                alunos.add(aluno);
            }
        }catch (SQLException exception){
            return null;
        }
        return alunos;
    }
}
