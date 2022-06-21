import java.util.Scanner;

public class cesar {
    // checa se o fim do arquivo foi alcancado
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }
    // realiza o ciframento (+3) e imprime o resultado
    public static void ciframentoCesar(String str) {
        String cipher = "";
        for (int i = 0; i < str.length(); i++) {
            if(Character.isUpperCase(str.charAt(i))) {
                cipher = cipher + (char)((str.charAt(i) + 3 - 65) % 26 + 65);
            }
            else if(Character.isLowerCase(str.charAt(i))) {
                cipher = cipher + (char)((str.charAt(i) + 3 - 97) % 26 + 97);
            }
        }
        System.out.println(cipher);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String str = sc.nextLine();
        while (isFim(str) == false) {
            ciframentoCesar(str);
            str = sc.nextLine();
        }
        sc.close();
    }
}
