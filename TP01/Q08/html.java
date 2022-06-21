import java.io.*;
import java.net.*;


public class html {
    public static String getHtml(String endereco) {
        URL url;
        InputStream is = null;
        BufferedReader br;
        String resp = "", line;

        try {
            url = new URL(endereco);
            is = url.openStream(); // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                resp += line + "\n";
            }
        } catch (MalformedURLException mue) {
            mue.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        try {
            is.close();
        } catch (IOException ioe) {
            // nothing to see here

        }

        return resp;
    }


    // checa se o arquivo chegou ao fim
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }

    // conta vogais
    public static int [] contaVogais(String html) {
        int [] ocorrenciaLetras = new int [22]; 

        for (int i = 0; i < html.length(); i++) {
            char c = html.charAt(i);
            if (c == 'a') {
                ocorrenciaLetras[0] = ocorrenciaLetras[0] + 1;
            }
            else if (c == 'e') {
                ocorrenciaLetras[1] = ocorrenciaLetras[1] + 1;
            }
            else if (c == 'i') {
                ocorrenciaLetras[2] = ocorrenciaLetras[2] + 1;
            }
            else if (c == 'o') {
                ocorrenciaLetras[3] = ocorrenciaLetras[3] + 1;
            }
            else if (c == 'u') {
                ocorrenciaLetras[4] = ocorrenciaLetras[4] + 1;
            }
            else if (c == 'á') {
                ocorrenciaLetras[5] = ocorrenciaLetras[5] + 1;
            }
            else if (c == 'é') {
                ocorrenciaLetras[6] = ocorrenciaLetras[6] + 1;
            }
            else if (c == 'í') {
                ocorrenciaLetras[7] = ocorrenciaLetras[7] + 1;
            }
            else if (c == 'ó') {
                ocorrenciaLetras[8] = ocorrenciaLetras[8] + 1;
            }
            else if (c == 'ú') {
                ocorrenciaLetras[9] = ocorrenciaLetras[9] + 1;
            }
            else if (c == 'à') {
                ocorrenciaLetras[10] = ocorrenciaLetras[10] + 1;
            }
            else if (c == 'è') {
                ocorrenciaLetras[11] = ocorrenciaLetras[11] + 1;
            }
            else if (c == 'ì') {
                ocorrenciaLetras[12] = ocorrenciaLetras[12] + 1;
            }
            else if (c == 'ò') {
                ocorrenciaLetras[13] = ocorrenciaLetras[13] + 1;
            }
            else if (c == 'ù') {
                ocorrenciaLetras[14] = ocorrenciaLetras[14] + 1;
            }
            else if (c == 'ã') {
                ocorrenciaLetras[15] = ocorrenciaLetras[15] + 1;
            }
            else if (c == 'õ') {
                ocorrenciaLetras[16] = ocorrenciaLetras[16] + 1;
            }
            else if (c == 'â') {
                ocorrenciaLetras[17] = ocorrenciaLetras[17] + 1;
            }
            else if (c == 'ê') {
                ocorrenciaLetras[18] = ocorrenciaLetras[18] + 1;
            }
            else if (c == 'î') {
                ocorrenciaLetras[19] = ocorrenciaLetras[19] + 1;
            }
            else if (c == 'ô') {
                ocorrenciaLetras[20] = ocorrenciaLetras[20] + 1;
            }
            else if (c == 'û') {
                ocorrenciaLetras[21] = ocorrenciaLetras[21] + 1;
            }
        }
        return ocorrenciaLetras;
    }
    // conta consoantes minusculas
    public static int contaConsoantesMinusculas(String html){
        int consoantes = 0;
        for (int i = 0; i < html.length(); i++) {
            char c = html.charAt(i);
            if (c > 'a' && c <= 'z' && c != 'e' && c != 'i' && c != 'o' && c != 'u') {
                consoantes++;
            }
        }
        return consoantes;
    }
    // conta br
    public static int contaOcorrenciasBr(String html) {
        int br = 0;
        for (int i = 0; i < html.length() - 3; i++) {
            char c = html.charAt(i);
            if ((c == '<') && (html.charAt(i + 1) == 'b') && (html.charAt(i + 2) == 'r') && (html.charAt(i + 3) == '>')) {
                br++;
            }
        }
        return br;
    }
    // conta table
    public static int contaOcorrenciasTable(String html) {
        int table = 0;
        for (int i = 0; i < html.length() - 6; i++) {
            char c = html.charAt(i);
            if ((c == '<') && (html.charAt(i + 1) == 't') && (html.charAt(i + 2) == 'a') && (html.charAt(i + 3) == 'b') && (html.charAt(i + 4) == 'l') && (html.charAt(i + 5) == 'e') && (html.charAt(i + 6) == '>'))  {
                table++;
            }
        }
        return table;
    }

    public static void main(String[] args) {
        // encoding
        System.setProperty("file.encoding", "UTF-8");
        System.setProperty("encoding", "UTF-8");
        System.setProperty("sun.jnu.encoding", "UTF-8");
        System.setProperty("file.encoding", "ISO-8859-1");
        System.setProperty("encoding", "ISO-8859-1");
        System.setProperty("sun.jnu.encoding", "ISO-8859-1");
        MyIO.setCharset("UTF-8");
        // VARIAVEIS
        String endereco, html, nomePagina;
        int [] letras = new int [22];
        int br = 0;
        int table = 0;
        int consoantes = 0;

        nomePagina = MyIO.readLine();
        while (isFim(nomePagina) == false) {
            endereco = MyIO.readLine();
            // chamar metodos
            html = getHtml(endereco);
            letras = contaVogais(html);
            br = contaOcorrenciasBr(html);
            table = contaOcorrenciasTable(html);
            consoantes = contaConsoantesMinusculas(html);
            // atualizar consoantes de acordo com contagem de br e table
            consoantes = consoantes - (br * 2) - (table * 3);
            // atualizar letra a de acordo com contagem de table
            letras[0] = letras[0] - table;
            // atualizar letra e de acordo com contagem de table
            letras[1] = letras[1] - table;
            // imprimir resultados
            MyIO.print("a(" + letras[0] + ") " + "e(" + letras[1] + ") " +"i(" + letras[2] + ") " +"o(" + letras[3] + ") " +"u(" + letras[4] + ") ");
            MyIO.print("á(" + letras[5] + ") " + "é(" + letras[6] + ") " +"í(" + letras[7] + ") " +"ó(" + letras[8] + ") " +"ú(" + letras[9] + ") ");
            MyIO.print("à(" + letras[10] + ") " + "è(" + letras[11] + ") " +"ì(" + letras[12] + ") " +"ò(" + letras[13] + ") " +"ù(" + letras[14] + ") ");
            MyIO.print("ã(" + letras[15] + ") " + "õ(" + letras[16] + ") ");
            MyIO.print("â(" + letras[17] + ") " + "ê(" + letras[18] + ") " +"î(" + letras[19] + ") " +"ô(" + letras[20] + ") " +"û(" + letras[21] + ") ");
            MyIO.print("consoante(" + consoantes + ") ");
            MyIO.print("<br>(" + br + ") ");
            MyIO.print("<table>(" + table + ") ");
            MyIO.print(nomePagina + "\n");
            nomePagina = MyIO.readLine();
        }
    }
}
