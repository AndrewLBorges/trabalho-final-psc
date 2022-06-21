package utilsPersistencia;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;

public class ConectorBanco implements AutoCloseable{
    private static  String URL_BANCO;
    private Connection connection;

    public ConectorBanco(){

        try{

            File file = new File("src/main/resources/settings.json");

            JsonObject jsonObject = new JsonParser().parse(new JsonReader(new FileReader(file))).getAsJsonObject();

            URL_BANCO = jsonObject.get("databaseConnectionString").getAsString();
        } catch (FileNotFoundException exception){
            System.out.println("Arquivo de configuração não encontrado!");
            URL_BANCO = "";
        }
    }

    public Connection getConnetion(){
        try{
            DriverManager.registerDriver(new com.microsoft.sqlserver.jdbc.SQLServerDriver());
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

            return DriverManager.getConnection(URL_BANCO);
        } catch (SQLException exception){
            System.out.println("Conexao com banco de dados falhou! stackTrace:");
            exception.printStackTrace();
            return null;
        } catch (ClassNotFoundException exception){
            System.out.println("Conexao com banco de dados falhou! stackTrace:");
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void close() throws Exception {
        if(connection != null && !connection.isClosed())
            connection.close();
    }
}
