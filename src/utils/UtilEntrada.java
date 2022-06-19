package utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Scanner;

public class UtilEntrada implements AutoCloseable {
    private static Scanner scanner = new Scanner(System.in);

    public static long entradaInteira(){
        long entrada;

        while(!scanner.hasNextInt()){
            System.out.print("\nNumero invalido, digite novamente: ");
            scanner.next();
        }
        entrada = scanner.nextInt();

        return entrada;
    }
    public static double entradaDecimal(){
        double entrada;

        while(!scanner.hasNextDouble()){
            System.out.print("\nNumero invalido, digite novamente: ");
            scanner.next();
        }
        entrada = scanner.nextDouble();

        return entrada;
    }
    public static String entradaDeTexto(){
        String entrada;

        while(!scanner.hasNextLine()){
            System.out.print("\nTexto invalido, digite novamente: ");
            scanner.next();
        }
        entrada = scanner.nextLine();

        return entrada;
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
