package utils;

import java.util.HashMap;
import java.util.Map;

public enum OpcaoMenu {
    SAIR(0),
    CADASTRO_CURSO(1),
    CADASTRO_ALUNO(2),
    MATRICULA_ALUNO(3),
    CADASTRO_SALA(4),
    CADASTRO_PROFESSOR(5),
    ALOCA_PROFESSOR(6),
    ALOCA_SALA(7),
    LISTAR_CURSOS(8),
    SEM_OPCAO(-1);

    private int value;
    private static Map map = new HashMap<>();

    private OpcaoMenu(int value) {
        this.value = value;
    }

    static {
        for (OpcaoMenu opcaoMenu : OpcaoMenu.values()) {
            map.put(opcaoMenu.value, opcaoMenu);
        }
    }

    public static OpcaoMenu valueOf(int opcaoMenu) {
        return (OpcaoMenu) map.get(opcaoMenu);
    }

    public int getValue() {
        return value;
    }
}
