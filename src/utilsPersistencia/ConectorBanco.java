package utilsPersistencia;

import entidades.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConectorBanco implements  AutoCloseable{
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
        }
    }

    public Aluno buscarAluno(int id_aluno){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Aluno WHERE id_matricula = ?");
            statement.setInt(1, id_aluno);

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
        }
    }

    public Professor buscarProfessor(int id_funcionario){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Professor WHERE id_funcionario = ?");
            statement.setInt(1, id_funcionario);

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
        }
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

    public void alocarProfessorAoCurso(int idCurso, int codigo_funcionario){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("UPDATE Curso SET id_professor = ? WHERE codigo = ?");
            statement.setInt(1, codigo_funcionario);
            statement.setInt(2, idCurso);
            statement.executeUpdate();
        }catch (SQLException exception){
        }
    }

    public void matricularAlunoAoCurso(int id_curso, int id_aluno){
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Curso_Aluno(id_curso,id_matricula_aluno) VALUES(?,?)");
            statement.setInt(1, id_curso);
            statement.setInt(2, id_aluno);
            statement.executeUpdate();
        }catch (SQLException exception){
        }
    }

    @Override
    public void close() throws Exception {
        if(connection != null && !connection.isClosed())
            connection.close();
    }
}
