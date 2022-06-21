package gerenciadores.entidade;

import entidades.Sala;
import gerenciadores.banco.GerenciadorSalaBanco;
import interfaces.GerenciadorBanco;
import interfaces.GerenciadorEntidades;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorSala implements GerenciadorEntidades<Sala> {
    private final GerenciadorBanco<Sala> bancoSala;
    private static List<Sala> salas;

    public GerenciadorSala() {
        this.bancoSala = new GerenciadorSalaBanco();
        salas = new ArrayList<>();
    }

    @Override
    public void cadastrar(Sala entidade) {
        bancoSala.inserir(entidade);
    }

    @Override
    public Sala retornarEntidade(int chave) {
        return null;
    }

    @Override
    public Sala retornarEndidade(String chave) {
        if(!salas.isEmpty() && !salas.stream().anyMatch(sala -> sala.getNome() == chave))
            return salas.stream().filter(sala -> sala.getNome() == chave).findFirst().get();
        return bancoSala.buscarUm(chave);
    }

    @Override
    public List<Sala> retornarEntidades() {
        List<Sala> listaBanco = bancoSala.buscarTodos();
        this.salas = listaBanco;
        return listaBanco;
    }
}
