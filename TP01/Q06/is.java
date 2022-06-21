public class is {
    // transforma char minusculo em maiusculo
    public static char toUpper(char c) {
        return (c >= 'a' && c <= 'z') ? ((char) ((int) c - 32)) : c;
    }
    // checa se toda a string e composta por vogais
    public static String isVogal(String str) {
        String result = "SIM";
        char c = '\0';
        for (int i = 0; i < str.length(); i++) {
            c = toUpper(str.charAt(i));
            if ((c != 'A') && (c != 'E') && (c != 'I') && (c != 'O') && (c != 'U')) {
                result = "NAO";
                i = str.length();
            }
        }
        return result;
    }
    // checa se um char e vogal
    public static String isVogalChar(char c) {
        String result = "SIM";
        c = toUpper(c);
        if ((c != 'A') && (c != 'E') && (c != 'I') && (c != 'O') && (c != 'U')) {
            result = "NAO";
        }
        return result;
    }
    // checa se toda a string e composta por consoantes
    public static String isConsonant(String str) {
        String result = "SIM";
        char c = '\0';
        for (int i = 0; i < str.length(); i++) {
            c = toUpper(str.charAt(i));
            if ((c < 'A' || c > 'Z') || (isVogalChar(c) == "SIM")) {
                result = "NAO";
                i = str.length();
            }
        }
        return result;
    }
    // checa se a string e um numero inteiro
    public static String isInt(String str) {
        String result = "SIM";
        char c = '\0';
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if ((c < '0') || (c > '9')) {
                result = "NAO";
                i = str.length();
            }
        }
        return result;
    }
    // checa se a string e um numero real
    public static String isReal(String str) {
        String result = "SIM";
        char c = '\0';
        int contador = 0;
        for (int i = 0; i < str.length(); i++) {
            c = str.charAt(i);
            if (c == ',' || c == '.') {
                contador++;
            }
            if ((contador > 1) || ((c != ',' && c != '.') && (c < '0') || (c > '9'))) {
                result = "NAO";
                i = str.length();
            }
        }
        return result;
    }

    // checa se o fim do arquivo foi alcancado
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }

    public static void main(String[] args) {
        String str = MyIO.readLine();
        while (isFim(str) == false) {
            MyIO.print(isVogal(str) + " ");
            MyIO.print(isConsonant(str) + " ");
            MyIO.print(isInt(str) + " ");
            MyIO.print(isReal(str) + "\n");
            str = MyIO.readLine();
        }
    }
}
