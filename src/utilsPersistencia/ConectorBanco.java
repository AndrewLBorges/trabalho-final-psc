package utilsPersistencia;

import entidades.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConectorBanco implements AutoCloseable{
    private static final String URL_BANCO = "jdbc:sqlserver://treinamento.database.windows.net:1433;database=SistemaCursos;user=usrAndrew@treinamento;password=abCD1234;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private Connection connection;

    public ConectorBanco(){
        this.connect();
    }

    private void connect(){
        try{
            connection = DriverManager.getConnection(URL_BANCO);
        } catch (SQLException exception){
            System.out.println("Conexao com banco de dados falhou! stackTrace:");
            exception.printStackTrace();
        }
    }

    public void inserirAluno(Aluno aluno){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Aluno(nome_completo,cpf,endereco,email,celular,id_matricula) VALUES(?,?,?,?,?,?)");
            statement.setString(1, aluno.getNome_completo());
            statement.setString(2, aluno.getCpf());
            statement.setString(3, aluno.getEndereco());
            statement.setString(4, aluno.getEmail());
            statement.setString(5, aluno.getCelular());
            statement.setInt(6, (int)aluno.getMatricula());
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    public Aluno buscarAluno(int idAluno){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Aluno WHERE id_matricula = ?");
            statement.setInt(1, idAluno);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                var nome = result.getString(1);
                var cpf = result.getString(2);
                var endereco = result.getString(3);
                var email = result.getString(4);
                var celular = result.getString(5);
                var id = result.getInt(6);
                return new Aluno(nome, cpf, endereco, email, celular, id);
            } else
                return null;
        }catch (SQLException exception){
            return null;
        }
    }

    public List<Aluno> buscarTodosAlunos(){
        PreparedStatement statement;
        List<Aluno> alunos = new ArrayList<>();

        try{
            statement = connection.prepareStatement("SELECT * FROM Aluno");
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                var nome = result.getString(1);
                var cpf = result.getString(2);
                var endereco = result.getString(3);
                var email = result.getString(4);
                var celular = result.getString(5);
                var id = result.getInt(6);
                alunos.add(new Aluno(nome, cpf, endereco, email, celular, id));
            }
        }catch (SQLException exception){
            return null;
        }
        return alunos;
    }

    public void inserirProfessor(Professor professor){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Professor(nome_completo,cpf,endereco,email,celular,id_funcionario) VALUES(?,?,?,?,?,?)");
            statement.setString(1, professor.getNome_completo());
            statement.setString(2, professor.getCpf());
            statement.setString(3, professor.getEndereco());
            statement.setString(4, professor.getEmail());
            statement.setString(5, professor.getCelular());
            statement.setInt(6, (int)professor.getCodigo_funcionario());
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    public Professor buscarProfessor(int idFuncionario){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Professor WHERE id_funcionario = ?");
            statement.setInt(1, idFuncionario);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                var nome = result.getString(1);
                var cpf = result.getString(2);
                var endereco = result.getString(3);
                var email = result.getString(4);
                var celular = result.getString(5);
                var id = result.getInt(6);
                return new Professor(nome, cpf, endereco, email, celular, id);
            } else
                return null;
        }catch (SQLException exception){
            return null;
        }
    }

    public List<Professor> buscarTodosProfessores(){
        PreparedStatement statement;
        List<Professor> professores = new ArrayList<>();

        try{
            statement = connection.prepareStatement("SELECT * FROM Professor");
            ResultSet result = statement.executeQuery();

            while(result.next()) {
                var nome = result.getString(1);
                var cpf = result.getString(2);
                var endereco = result.getString(3);
                var email = result.getString(4);
                var celular = result.getString(5);
                var id = result.getInt(6);
                professores.add(new Professor(nome, cpf, endereco, email, celular, id));
            }
        }catch (SQLException exception){
            return null;
        }
        return professores;
    }

    public void inserirSala(Sala sala){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Sala(nome,local,capacidade) VALUES(?,?,?)");
            statement.setString(1, sala.getNome());
            statement.setString(2, sala.getLocal());
            statement.setInt(3, sala.getCapacidade());
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    public Sala buscarSala(String nome){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Sala WHERE nome = ?");
            statement.setString(1, nome);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                var nomeSala = result.getString(1);
                var local = result.getString(2);
                var capacidade = result.getInt(3);
                return new Sala(nomeSala, local, capacidade);
            } else
                return null;
        }catch (SQLException exception){
            return null;
        }
    }

    public List<Sala> buscarTodasSalas(){
        PreparedStatement statement;
        List<Sala> salas = new ArrayList<>();

        try{
            statement = connection.prepareStatement("SELECT * FROM Sala");

            ResultSet result = statement.executeQuery();

            while(result.next()){
                var nomeSala = result.getString(1);
                var local = result.getString(2);
                var capacidade = result.getInt(3);
                salas.add(new Sala(nomeSala, local, capacidade));
            }
        }catch (SQLException exception){
            return null;
        }

        return salas;
    }

    public void inserirCurso(Curso curso){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Curso(codigo,nome,carga_horaria, descricao) VALUES(?,?,?,?)");
            statement.setInt(1, (int)curso.getCodigo());
            statement.setString(2, curso.getNome());
            statement.setInt(3, curso.getCarga_horaria());
            statement.setString(4, curso.getDescricao());
            statement.executeUpdate();
        } catch(SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    public Curso buscarCurso(int idCurso){
        PreparedStatement statement;
        Curso curso;

        try{
            statement = connection.prepareStatement("SELECT * FROM Curso WHERE codigo = ?");
            statement.setInt(1, idCurso);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                var codigo = result.getInt(1);
                var nome = result.getString(2);
                var carga_horaria = result.getInt(3);
                var descricao = result.getString(4);
                var id_professor = result.getInt(5);
                var nome_sala = result.getString(6);
                var professor = buscarProfessor(id_professor);
                var sala = buscarSala(nome_sala);

                curso =  new Curso(codigo, nome, carga_horaria, descricao);

                if(professor != null)
                    curso.cadastrarProfessor(professor);
                if(sala != null)
                    curso.cadastrarSala(sala);

                return curso;

            } else
                return null;
        }catch (SQLException exception){
            return null;
        }
    }

    public List<Curso> buscarTodosCursos(){
        List<Curso> cursos = new ArrayList<>();
        PreparedStatement statement;

        try{
            Curso curso;
            statement = connection.prepareStatement("SELECT * FROM Curso");

            ResultSet result = statement.executeQuery();

            while(result.next()){
                var codigo = result.getInt(1);
                var nome = result.getString(2);
                var carga_horaria = result.getInt(3);
                var descricao = result.getString(4);
                var id_professor = result.getInt(5);
                var nome_sala = result.getString(6);
                var professor = buscarProfessor(id_professor);
                var sala = buscarSala(nome_sala);

                curso =  new Curso(codigo, nome, carga_horaria, descricao);

                if(professor != null)
                    curso.cadastrarProfessor(professor);
                if(sala != null)
                    curso.cadastrarSala(sala);

                cursos.add(curso);
            }
        }catch (SQLException exception){
            return null;
        }

        return cursos;
    }

    public void alocarSalaAoCurso(int idCurso, String nomeSala){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("UPDATE Curso SET nome_sala = ? WHERE codigo = ?");
            statement.setString(1, nomeSala);
            statement.setInt(2, idCurso);
            statement.executeUpdate();
        }catch (SQLException exception){
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

                curso =  buscarCurso(codigoCurso);
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

                aluno =  buscarAluno(codigoCurso);
                alunos.add(aluno);
            }
        }catch (SQLException exception){
            return null;
        }
        return alunos;
    }

    @Override
    public void close() throws Exception {
        if(connection != null && !connection.isClosed())
            connection.close();
    }
}
