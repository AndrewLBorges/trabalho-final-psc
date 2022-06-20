package gerenciadoresEntidade;

import entidades.Aluno;
import entidades.Sala;
import interfaces.GerenciadorEntidades;
import utilsPersistencia.ConectorBanco;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorSala implements GerenciadorEntidades<Sala> {
    private final ConectorBanco conector;
    private static List<Sala> salas;

    public GerenciadorSala() {
        this.conector = new ConectorBanco();
        salas = new ArrayList<>();
    }

    @Override
    public void cadastrar(Sala entidade) {
        conector.inserirSala(entidade);
    }

    @Override
    public Sala retornarEntidade(int chave) {
        return null;
    }

    @Override
    public Sala retornarEndidade(String chave) {
        if(!salas.isEmpty() && !salas.stream().anyMatch(sala -> sala.getNome() == chave))
            return salas.stream().filter(sala -> sala.getNome() == chave).findFirst().get();
        return conector.buscarSala(chave);
    }

    @Override
    public List<Sala> retornarEntidades() {
        List<Sala> listaBanco = conector.buscarTodasSalas();
        this.salas = listaBanco;
        return listaBanco;
    }
}
