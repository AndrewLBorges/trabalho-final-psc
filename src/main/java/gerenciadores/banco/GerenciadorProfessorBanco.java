package gerenciadores.banco;

import entidades.Professor;
import interfaces.GerenciadorBanco;
import utilsPersistencia.ConectorBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorProfessorBanco implements GerenciadorBanco<Professor> {
    private ConectorBanco conectorBanco = new ConectorBanco();
    private Connection connection;

    public GerenciadorProfessorBanco(){
        this.connection = conectorBanco.getConnetion();
    }

    @Override
    public void inserir(Professor entidade) {
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Professor(nome_completo,cpf,endereco,email,celular,id_funcionario) VALUES(?,?,?,?,?,?)");
            statement.setString(1, entidade.getNome_completo());
            statement.setString(2, entidade.getCpf());
            statement.setString(3, entidade.getEndereco());
            statement.setString(4, entidade.getEmail());
            statement.setString(5, entidade.getCelular());
            statement.setInt(6, (int)entidade.getCodigo_funcionario());
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    @Override
    public Professor buscarUm(int id_entidade) {
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Professor WHERE id_funcionario = ?");
            statement.setInt(1, id_entidade);

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

    @Override
    public Professor buscarUm(String id_entidade) {
        return buscarUm(Integer.parseInt(id_entidade));
    }

    @Override
    public List<Professor> buscarTodos() {
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
}
