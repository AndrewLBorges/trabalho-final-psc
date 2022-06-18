package utils;

import java.util.Scanner;

public class UtilEntrada {
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

        return "";
    }

    private void fecharStreamScanner(){
        scanner.close();
    }
}
