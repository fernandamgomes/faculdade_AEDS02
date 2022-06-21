public class palindromoRecur {
    public static void main(String[] args) {
        // ler entrada linha por linha e checar se e palindromo ou fim do arquivo
        String str = MyIO.readLine();
        int palindromo;
        while (isFim(str) == false) {
            palindromo = isPalindromoRecur(str, 0);
            if (palindromo == 0){
                MyIO.println("SIM");
            }
            else if (palindromo == 1){
                MyIO.println("NAO");
            }
            str = MyIO.readLine();
        }

    }

    // checa se e palindromo ou nao e imprime o resultado
    public static int isPalindromoRecur(String str, int i) {
        int resp;
        if (i > str.length()/2 - 1) {
            resp = 0;
        }
        else {
            if (str.charAt(i) != str.charAt(str.length() - 1 - i)) {
                resp = 1;
            }
            else {
                resp = isPalindromoRecur(str, i+1);
            }
        }
        return resp;
    }


    // checa se o arquivo chegou ao fim
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }
}
