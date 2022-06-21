import java.util.Scanner;

public class palindromo {
    public static void main(String[] args) {
        // ler entrada linha por linha e checar se e palindromo ou fim do arquivo
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        while (isFim(str) == false) {
            isPalindromo(str);
            str = sc.nextLine();
        }
        sc.close();
    }

    // checa se e palindromo ou nao e imprime o resultado
    public static void isPalindromo(String str) {
        String palindromo = "SIM";
        for (int i = 0; i < str.length() / 2; i++) {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                palindromo = "NAO";
                i = str.length();
            }
        }
        System.out.println(palindromo);
    }

    // checa se o arquivo chegou ao fim
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }
}
