import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class merge {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        ListaFilmes lista = new ListaFilmes();
        int contador = 0;
        // ler linhas stdin
        String filmeIn = sc.nextLine();
        while (isFim(filmeIn) == false) {
            Filme tmp = new Filme();
            tmp.lerArquivo(filmeIn);
            // ler stdin - novo input
            lista.inserirFim(tmp);
            filmeIn = sc.nextLine();
            contador++;
        }

        // imprimir lista de filmes ORDENADOS
        lista.ordMerge(0, contador - 1);
        lista.imprimir();
        sc.close();
    }

    // ACHA O FIM DO ARQUIVO
    public static boolean isFim(String str) {
        return ((str.length() == 3) && (str.charAt(0) == 'F') && (str.charAt(1) == 'I')
                && (str.charAt(2) == 'M'));
    }
}

class Filme {
    // ATRIBUTOS
    private String nome;
    private String titulo;
    private Date data;
    private int duracao;
    private String genero;
    private String idioma;
    private String situacao;
    private float orcamento;
    private String[] palavrasChave;

    // CONSTRUTORES
    public Filme(String nome, String titulo, Date data, int duracao, String genero, String idioma,
            String situacao, float orcamento, String[] palavrasChave) {
        this.nome = nome;
        this.titulo = titulo;
        this.data = data;
        this.duracao = duracao;
        this.genero = genero;
        this.idioma = idioma;
        this.situacao = situacao;
        this.orcamento = orcamento;
        this.palavrasChave = palavrasChave;
    }

    public Filme() {
    }

    // GETTERS

    public String getNome() {
        return nome;
    }

    public String getTitulo() {
        return titulo;
    }

    public Date getData() {
        return data;
    }

    public int getDuracao() {
        return duracao;
    }

    public String getGenero() {
        return genero;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getSituacao() {
        return situacao;
    }

    public float getOrcamento() {
        return orcamento;
    }

    public String[] getPalavrasChave() {
        return palavrasChave;
    }

    // SETTERS
    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public void setSituacao(String situacao) {
        this.situacao = situacao;
    }

    public void setOrcamento(float orcamento) {
        this.orcamento = orcamento;
    }

    public void setPalavrasChave(String[] palavrasChave) {
        this.palavrasChave = palavrasChave;
    }

    // CLONE
    public Filme clone() {
        Filme clone = new Filme();
        clone.setNome(this.getNome());
        clone.setTitulo(this.getTitulo());
        clone.setData(this.getData());
        clone.setDuracao(this.getDuracao());
        clone.setGenero(this.getGenero());
        clone.setIdioma(this.getIdioma());
        clone.setSituacao(this.getSituacao());
        clone.setOrcamento(this.getOrcamento());
        clone.setPalavrasChave(this.getPalavrasChave());
        return clone;
    }

    // IMPRIMIR
    public void Imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        System.out.print(this.getNome() + " " + this.getTitulo() + " " + sdf.format(this.getData()) + " "
                + this.getDuracao() + " " + this.getGenero() + " " + this.getIdioma() + " " + this.getSituacao() + " "
                + this.getOrcamento() + " ");

        // IMPRIMIR ARRAY PALAVRAS CHAVE
        System.out.print("[");
        if (this.getPalavrasChave() != null) {
            String[] pChave = this.getPalavrasChave();

            for (int i = 0; i < pChave.length - 1; i++) {
                System.out.print(pChave[i] + ", ");
            }
            System.out.print(pChave[pChave.length - 1]);
        }
        System.out.print("]\n");
    }

    // LER - efetuar a leitura dos atributos de um registro (arquivos html)
    public void lerArquivo(String arquivo) throws Exception {
        // File path - parametro
        File file = new File("/tmp/filmes/" + arquivo);
        Scanner scanner = new Scanner(file, "UTF-8");
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        // boolean que checa se o arq tem titulo definido, se nao tiver titulo = nome
        boolean temTitulo = false;
        while (scanner.hasNextLine()) {
            // ler arq linha a linha e procurar por atributos
            String line = scanner.nextLine();

            // NOME
            if (line.contains("h2 class")) {
                line = scanner.nextLine();
                this.setNome(removeTag(line).trim().replace("&amp;", ""));
            }

            // DATA DE LANCAMENTO
            else if (line.contains("span class=\"release\"")) {
                line = scanner.nextLine();
                line = line.trim();
                this.setData(sdf.parse(line));
            }

            // DURACAO
            else if (line.contains("runtime")) {
                scanner.nextLine();
                line = scanner.nextLine().trim();
                // VARIAVEIS
                int duracao = 0;
                int i = 0;
                int pos = 0;
                String tmp = "";
                // calculo da duracao em minutos, checar tamanho da string
                // HORAS
                if (line.contains("h")) {
                    i = line.indexOf("h");
                    for (int j = 0; j < i; j++) {
                        if ((line.charAt(j) >= 48) && (line.charAt(j) <= 57)) {
                            tmp = tmp + line.charAt(j);
                            pos++;
                        }
                    }
                    duracao = Integer.parseInt(tmp) * 60;
                    pos++;
                }
                // MINUTOS
                if (line.contains("m")) {
                    i = line.indexOf("m");
                    tmp = "";
                    for (int j = pos; j < i; j++) {
                        if ((line.charAt(j) >= 48) && (line.charAt(j) <= 57)) {
                            tmp = tmp + line.charAt(j);
                        }
                    }
                    duracao = duracao + Integer.parseInt(tmp);
                }
                this.setDuracao(duracao);
            }

            // GENERO
            else if (line.contains("genres")) {
                scanner.nextLine();
                line = scanner.nextLine();
                line = removeTag(line).replace("&nbsp;", "").trim();
                this.setGenero(line);
            }

            // IDIOMA
            else if (line.contains("Idioma")) {
                line = removeTag(line.trim());
                line = line.replace("Idioma original ", "");
                this.setIdioma(line);
            }

            // ORCAMENTO
            else if (line.contains("Orçamento")) {
                line = removeTag(line);
                line = line.replace("Orçamento", "").trim().replaceAll("[,$-]", "");
                if (line.isEmpty()) {
                    this.setOrcamento(0);
                } else {
                    this.setOrcamento(Float.parseFloat(line));
                }
            }

            // PALAVRAS CHAVE
            else if (line.contains("Palavras-chave")) {
                // VARIAVEIS
                String tmp = "";
                // testar se tem a lista de palavras chave
                while ((line.contains("<li><a") == false) && (line.contains("Nenhuma palavra") == false)) {
                    line = scanner.nextLine();
                }

                // se tiver umna lista montar array de palavras chave
                if (line.contains("<li><a")) {
                    while ((line.contains("</ul>") == false)) {
                        line = removeTag(line).trim();
                        if (((line.isEmpty()) == false)) {
                            tmp = tmp + line + ',';
                        }
                        line = scanner.nextLine();
                    }
                    this.setPalavrasChave(tmp.split(","));
                }
            }

            // SITUACAO
            else if (line.contains("strong><bdi>Situação</bdi></strong")) {
                line = removeTag(line);
                line = line.replace("Situação", "").trim();
                this.setSituacao(line);
            }

            // TITULO
            else if (line.contains("<strong>Título original</strong>")) {
                temTitulo = true;
                line = removeTag(line);
                line = line.replace("Título original", "").trim();
                this.setTitulo(line);
            }
        }
        // se apos ler todas as linhas do arquivo o titulo nao for encontrado, titulo =
        // nome
        if (temTitulo == false) {
            this.setTitulo(this.getNome());
        }
        scanner.close();
    }

    // REMOVE TAG
    public static String removeTag(String linha) {
        String linhaMod = "";
        for (int i = 0; i < linha.length(); i++) {
            if (linha.charAt(i) == '<') {
                i++;
                while (linha.charAt(i) != '>') {
                    i++;
                }
            } else {
                linhaMod = linhaMod + linha.charAt(i);
            }
        }
        return linhaMod;
    }
}

class ListaFilmes {
    // VARIAVEIS
    Filme filmes[] = new Filme[80];
    private int contadorLista = 0;

    // INSERIR NO INICIO
    void inserirInicio(Filme filme) throws Exception {
        if (contadorLista >= filmes.length) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = contadorLista; i > 0; i--) {
            filmes[i] = filmes[i - 1];
        }
        filmes[0] = filme;
        contadorLista++;
    }

    // INSERIR
    void inserir(Filme filme, int pos) throws Exception {
        if ((contadorLista >= filmes.length) || (pos > contadorLista) || (pos < 0)) {
            throw new Exception("Erro ao inserir!");
        }
        for (int i = contadorLista; i > pos; i--) {
            filmes[i] = filmes[i - 1];
        }
        filmes[pos] = filme;
        contadorLista++;
    }

    // INSERIR FIM
    void inserirFim(Filme filme) throws Exception {
        if (contadorLista >= filmes.length) {
            throw new Exception("Erro ao inserir!");
        }

        filmes[contadorLista] = filme;
        contadorLista++;
    }

    // REMOVER INICIO
    Filme removerInicio() throws Exception {
        if (contadorLista == 0) {
            throw new Exception("Erro ao remover!");
        }
        Filme filme = filmes[0];
        contadorLista--;
        for (int i = 0; i < contadorLista; i++) {
            filmes[i] = filmes[i + 1];
        }
        return filme;
    }

    // REMOVER
    Filme remover(int pos) throws Exception {
        if ((contadorLista == 0) || (pos < 0) || (pos >= contadorLista)) {
            throw new Exception("Erro ao remover!");
        }
        Filme filme = filmes[pos];
        contadorLista--;
        for (int i = pos; i < contadorLista; i++) {
            filmes[i] = filmes[i + 1];
        }
        return filme;
    }

    // REMOVER FIM
    Filme removerFim() throws Exception {
        if (contadorLista == 0) {
            throw new Exception("Erro ao remover!");
        }
        Filme filme = filmes[contadorLista - 1];
        contadorLista--;
        return filme;
    }

    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (int i = 0; i < contadorLista; i++) {
            System.out.print(
                    filmes[i].getNome() + " " +
                            filmes[i].getTitulo() + " " + sdf.format(filmes[i].getData())
                            + " "
                            + filmes[i].getDuracao() + " " + filmes[i].getGenero() + " " + filmes[i].getIdioma() + " "
                            + filmes[i].getSituacao()
                            + " "
                            + filmes[i].getOrcamento() + " ");

            // IMPRIMIR ARRAY PALAVRAS CHAVE
            System.out.print("[");
            if (filmes[i].getPalavrasChave() != null) {
                String[] pChave = filmes[i].getPalavrasChave();

                for (int j = 0; j < pChave.length - 1; j++) {
                    System.out.print(pChave[j] + ", ");
                }
                System.out.print(pChave[pChave.length - 1]);
            }
            System.out.print("]\n");
        }
    }
    // FONTE: https://www.geeksforgeeks.org/merge-sort/
    void merge(int l, int m, int r) {
        // Find sizes of two subarrays to be merged
        int n1 = m - l + 1;
        int n2 = r - m;

        /* Create temp arrays */
        Filme[] L = new Filme[n1];
        Filme[] R = new Filme[n2];

        /* Copy data to temp arrays */
        for (int i = 0; i < n1; ++i)
            L[i] = filmes[l + i];
        for (int j = 0; j < n2; ++j)
            R[j] = filmes[m + 1 + j];

        /* Merge the temp arrays */

        // Initial indexes of first and second subarrays
        int i = 0, j = 0;

        // Initial index of merged subarray array
        int k = l;
        while (i < n1 && j < n2) {
            if ((L[i].getOrcamento() < R[j].getOrcamento()) || ((L[i].getOrcamento() == R[j].getOrcamento()) && (L[i].getNome().compareTo(R[j].getNome()) < 0 ))) {
                filmes[k] = L[i];
                i++;
            } else {
                filmes[k] = R[j];
                j++;
            }
            k++;
        }

        /* Copy remaining elements of L[] if any */
        while (i < n1) {
            filmes[k] = L[i];
            i++;
            k++;
        }

        /* Copy remaining elements of R[] if any */
        while (j < n2) {
            filmes[k] = R[j];
            j++;
            k++;
        }
    }

    // Main function that sorts arr[l..r] using
    // merge()
    void ordMerge(int l, int r) {
        if (l < r) {
            // Find the middle point
            int m = l + (r - l) / 2;

            // Sort first and second halves
            ordMerge(l, m);
            ordMerge(m + 1, r);

            // Merge the sorted halves
            merge(l, m, r);
        }
    }

}