package util;

import java.util.Scanner;

public class ScannerUtil {
    private static Scanner scanner = new Scanner(System.in);

    public static String input(String info) {
        System.out.println(info + ": ");
        String data = scanner.nextLine();
        return data;
    }

    public static Integer inputInteger (String info) {
        System.out.print(info + ": ");
        Integer data = scanner.nextInt();
        return data;
    }
}
