public class cesarRecur {
    // checa se o fim do arquivo foi alcancado
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }

    // realiza o ciframento (+3) e imprime o resultado
    public static String ciframentoCesar(String str, int i) {
        String cipher = "";

        if (i != str.length()) {
            cipher = cipher + (char)(str.charAt(i) + 3) + ciframentoCesar(str, ++i);
        }
        return cipher;
    }
    public static void main(String[] args) {
        String str = MyIO.readLine();
        while (isFim(str) == false) {
            MyIO.println(ciframentoCesar(str, 0));
            str = MyIO.readLine();
        }
    }
}
