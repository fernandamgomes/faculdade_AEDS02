import java.util.Random;

public class aleatorio {
    public static void main(String[] args) {
        Random gerador = new Random();
        gerador.setSeed(4);
        // ler entrada linha por linha e realizar alteracoes ate o fim do arquivo
        String str = MyIO.readLine();
        while (isFim(str) == false) {
            alteracaoAleatoria(str, gerador);
            str = MyIO.readLine();
        }
    }

    // checa se o arquivo chegou ao fim
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }
    // sorteia dois caracteres e substitui o primeiro pelo segundo na string recebida por parametro
    public static void alteracaoAleatoria(String str, Random gerador) {
        String newStr = "";
        char original = ((char) ('a' + (Math.abs(gerador.nextInt()) % 26)));
        char alterado = ((char) ('a' + (Math.abs(gerador.nextInt()) % 26)));
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == original) {
                newStr = newStr + alterado;
            } else {
                newStr = newStr + str.charAt(i);
            }
        }
        MyIO.println(newStr);
    }
}
