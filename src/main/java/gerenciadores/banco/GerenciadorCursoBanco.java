package gerenciadores.banco;

import entidades.Curso;
import entidades.Professor;
import entidades.Sala;
import interfaces.GerenciadorBanco;
import utilsPersistencia.ConectorBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorCursoBanco implements GerenciadorBanco<Curso> {
    private ConectorBanco conectorBanco = new ConectorBanco();
    private Connection connection;
    private GerenciadorBanco<Professor> bancoProfessor;
    private GerenciadorBanco<Sala> bancoSala;

    public GerenciadorCursoBanco(){
        this.connection = conectorBanco.getConnetion();
        this.bancoProfessor = new GerenciadorProfessorBanco();
        this.bancoSala = new GerenciadorSalaBanco();
    }

    @Override
    public void inserir(Curso entidade) {
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Curso(codigo,nome,carga_horaria, descricao) VALUES(?,?,?,?)");
            statement.setInt(1, (int)entidade.getCodigo());
            statement.setString(2, entidade.getNome());
            statement.setInt(3, entidade.getCarga_horaria());
            statement.setString(4, entidade.getDescricao());
            statement.executeUpdate();
        } catch(SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    @Override
    public Curso buscarUm(int id_entidade) {
        PreparedStatement statement;
        Curso curso;

        try{
            statement = connection.prepareStatement("SELECT * FROM Curso WHERE codigo = ?");
            statement.setInt(1, id_entidade);

            ResultSet result = statement.executeQuery();

            if(result.next()){
                var codigo = result.getInt(1);
                var nome = result.getString(2);
                var carga_horaria = result.getInt(3);
                var descricao = result.getString(4);
                var id_professor = result.getInt(5);
                var nome_sala = result.getString(6);
                var professor = bancoProfessor.buscarUm(id_professor);
                var sala = bancoSala.buscarUm(nome_sala);

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

    @Override
    public Curso buscarUm(String id_entidade) {
        return buscarUm(Integer.parseInt(id_entidade));
    }

    @Override
    public List<Curso> buscarTodos() {
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
                var professor = bancoProfessor.buscarUm(id_professor);
                var sala = bancoSala.buscarUm(nome_sala);

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
}
