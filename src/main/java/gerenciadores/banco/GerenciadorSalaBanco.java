package gerenciadores.banco;

import entidades.Sala;
import interfaces.GerenciadorBanco;
import utilsPersistencia.ConectorBanco;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorSalaBanco implements GerenciadorBanco<Sala> {
    private ConectorBanco conectorBanco = new ConectorBanco();
    private Connection connection;

    public GerenciadorSalaBanco(){
        this.connection = conectorBanco.getConnetion();
    }

    @Override
    public void inserir(Sala entidade) {
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("INSERT INTO Sala(nome,local,capacidade) VALUES(?,?,?)");
            statement.setString(1, entidade.getNome());
            statement.setString(2, entidade.getLocal());
            statement.setInt(3, entidade.getCapacidade());
            statement.executeUpdate();
        }catch (SQLException exception){
            System.out.println("Falha ao registar dado!");
        }
    }

    @Override
    public Sala buscarUm(int id_entidade) {
        return null;
    }

    @Override
    public Sala buscarUm(String id_entidade) {
        PreparedStatement statement;

        try{
            statement = connection.prepareStatement("SELECT * FROM Sala WHERE nome = ?");
            statement.setString(1, id_entidade);

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

    @Override
    public List<Sala> buscarTodos() {
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
}
