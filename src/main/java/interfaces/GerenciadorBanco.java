package interfaces;

import java.util.List;

public interface GerenciadorBanco<T>{
    void inserir(T entidade);
    T buscarUm(int id_entidade);
    T buscarUm(String id_entidade);
    List<T> buscarTodos();
}
