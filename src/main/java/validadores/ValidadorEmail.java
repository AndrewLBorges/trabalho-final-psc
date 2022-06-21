package validadores;

import interfaces.Validador;
import utils.UtilEntrada;

public class ValidadorEmail implements Validador<String> {
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";

    @Override
    public String validar(String entrada) {
        while(!entrada.matches(REGEX_EMAIL)){
            System.out.println("Email invalido! Digite novamente: ");
            entrada = UtilEntrada.entradaDeTexto();
        }

        return entrada;
    }
}
