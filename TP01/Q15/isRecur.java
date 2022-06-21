public class isRecur {
    // transforma char minusculo em maiusculo
    public static char toUpper(char c) {
        return (c >= 'a' && c <= 'z') ? ((char) ((int) c - 32)) : c;
    }
    // checa se toda a string e composta por vogais
    public static String isVogal(String str, int i) {
        String result;
        if (i == str.length()) {
            result = "SIM";
        }
        else if  ((toUpper(str.charAt(i)) != 'A') && (toUpper(str.charAt(i)) != 'E') && (toUpper(str.charAt(i)) != 'I') && (toUpper(str.charAt(i)) != 'O') && (toUpper(str.charAt(i)) != 'U')) {
            result = "NAO";
        }
        else {
            result = isVogal(str, ++i);
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
    public static String isConsonant(String str, int i) {
        String result;
        if (i == str.length()) {
            result = "SIM";
        }
        else if ((toUpper(str.charAt(i)) < 'A' || toUpper(str.charAt(i)) > 'Z') || (isVogalChar(str.charAt(i)) == "SIM")) {
            result = "NAO";
        }
        else {
            result = isConsonant(str, ++i);
        }
        return result;
    }

    // checa se a string e um numero inteiro
    public static String isInt(String str, int i) {
        String result;
        if (i == str.length()) {
            result = "SIM";
        }
        else if ((str.charAt(i) < '0') || (str.charAt(i) > '9')) {
            result = "NAO";
        }
        else {
            result = isInt(str, ++i);
        }
        return result;
    }
    // checa se a string e um numero real
    public static String isReal(String str, int i, int contador) {
        String result;
        
        if (i == str.length()) {
            result = "SIM";
        }
        else if ((contador > 1) || ((str.charAt(i) != ',' && str.charAt(i) != '.') && (str.charAt(i) < '0') || (str.charAt(i) > '9'))) {
            result = "NAO";
        }
        else {
            if (str.charAt(i) == ',' || str.charAt(i) == '.') {
                contador++;
            }
            result = isReal(str, ++i, contador);
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
            MyIO.print(isVogal(str, 0) + " ");
            MyIO.print(isConsonant(str, 0) + " ");
            MyIO.print(isInt(str, 0) + " ");
            MyIO.print(isReal(str, 0, 0) + "\n");
            str = MyIO.readLine();
        }
    }
}