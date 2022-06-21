package interfaces;

import java.util.List;

public interface GerenciadorEntidades<T> {
    void cadastrar (T entidade);
    T retornarEntidade(int chave);
    T retornarEndidade(String chave);
    List<T> retornarEntidades();
}
