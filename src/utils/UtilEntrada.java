package utils;

import java.io.IOException;
import java.util.Scanner;

public class UtilEntrada implements AutoCloseable {
    private static Scanner scanner;

    public static long entradaInteira(){
        scanner = new Scanner(System.in);
        long entrada;

        while(!scanner.hasNextInt()){
            System.out.print("\nNumero invalido, digite novamente: ");
            scanner.next();
        }
        entrada = scanner.nextInt();

        return entrada;
    }
    public static double entradaDecimal(){
        scanner = new Scanner(System.in);
        double entrada;

        while(!scanner.hasNextDouble()){
            System.out.print("\nNumero invalido, digite novamente: ");
            scanner.next();
        }
        entrada = scanner.nextDouble();

        return entrada;
    }
    public static String entradaDeTexto(){
        scanner = new Scanner(System.in);
        String entrada;

        while(!scanner.hasNextLine()){
            System.out.print("\nTexto invalido, digite novamente: ");
            scanner.nextLine();
        }
        entrada = scanner.nextLine();

        return entrada;
    }

    @Override
    public void close() throws IOException {
        scanner.close();
    }
}
