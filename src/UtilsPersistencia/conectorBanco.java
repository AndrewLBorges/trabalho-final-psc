package UtilsPersistencia;

import entidades.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class conectorBanco implements  AutoCloseable{
    private static final String URL_BANCO = "jdbc:sqlserver://treinamento.database.windows.net:1433;database=SistemaCursos;user=usrAndrew@treinamento;password=abCD1234;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
    private Connection connection;

    public conectorBanco(){
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

    public void inserirSala(Aluno aluno){
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
            System.out.println("Um erro ocorreu ao cadastrar o dado no banco de dados.");
        }
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
            System.out.println("Um erro ocorreu ao cadastrar o dado no banco de dados.");
        }
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
            System.out.println("Um erro ocorreu ao cadastrar o dado no banco de dados.");
        }
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
            System.out.println("Um erro ocorreu ao cadastrar o dado no banco de dados.");
        }
    }

    @Override
    public void close() throws Exception {
        if(connection != null && !connection.isClosed())
            connection.close();
    }
}
