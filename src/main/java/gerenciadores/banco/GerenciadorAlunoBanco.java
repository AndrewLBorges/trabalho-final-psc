package gerenciadores.banco;

import entidades.*;
import interfaces.GerenciadorBanco;
import utilsPersistencia.ConectorBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorAlunoBanco implements GerenciadorBanco<Aluno> {
    private ConectorBanco conectorBanco = new ConectorBanco();
    private Connection connection;

    public GerenciadorAlunoBanco(){
        this.connection = conectorBanco.getConnetion();
    }

    @Override
    public void inserir(Aluno entidade) {
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Aluno(nome_completo,cpf,endereco,email,celular,id_matricula) VALUES(?,?,?,?,?,?)");
            statement.setString(1, entidade.getNome_completo());
            statement.setString(2, entidade.getCpf());
            statement.setString(3, entidade.getEndereco());
            statement.setString(4, entidade.getEmail());
            statement.setString(5, entidade.getCelular());
            statement.setInt(6, (int)entidade.getMatricula());
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    @Override
    public Aluno buscarUm(int id_entidade) {
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Aluno WHERE id_matricula = ?");
            statement.setInt(1, id_entidade);

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

    @Override
    public Aluno buscarUm(String id_entidade) {
        return buscarUm(Integer.parseInt(id_entidade));
    }

    @Override
    public List<Aluno> buscarTodos() {
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
}
