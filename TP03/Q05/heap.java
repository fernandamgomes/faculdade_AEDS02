import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class heap {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        ListaFilmes lista = new ListaFilmes();
        // ler linhas stdin
        String filmeIn = sc.nextLine();
        while (isFim(filmeIn) == false) {
            Filme tmp = new Filme();
            tmp.lerArquivo(filmeIn);
            // ler stdin - novo input
            lista.inserirFim(tmp);
            filmeIn = sc.nextLine();
        }

        // imprimir lista de filmes ORDENADOS
        lista.ordHeap();
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
    private int contadorLista = 1; // HEAP COMECA DA POS 1

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
        // HEAP COMECA DAS POS 1
        for (int i = 1; i <= contadorLista; i++) {
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


    // ORDENAR POR GENERO --> ORDENAR POR NOME
    void ordHeap() {
        // construir heap
        contadorLista--;
        for (int tam = 2; tam <= contadorLista; tam++) {
            try {
                construir(tam);
            } catch (Exception e) {
                System.out.println(tam + "\n");
            }
        }

        // ordernacao array
        int tam = contadorLista;
        while (tam > 1) {
            swap(1, tam--);
            reconstruir(tam);
        }
    }

    void construir(int tam) {
        //(tmp.getData().compareTo(filmes[j].getData())) < 0
        for (int i = tam; i > 1; i = i/2) {
            if ((filmes[i].getGenero().compareTo(filmes[i/2].getGenero()) > 0) || ((filmes[i].getGenero().compareTo(filmes[i/2].getGenero()) == 0) && ((filmes[i].getNome().compareTo(filmes[i/2].getNome()) > 0)))) {
                swap(i, i/2);
            }
        }
    }
    void reconstruir(int tam) {
        int i = 1;
        while (temFilho(i, tam) == true) {
            int filho = getMaiorFilho(i, tam);
            if  ((filmes[i].getGenero().compareTo(filmes[filho].getGenero()) < 0) || ((filmes[i].getGenero().compareTo(filmes[filho].getGenero()) == 0) && (filmes[i].getNome().compareTo(filmes[filho].getNome()) < 0)))  {
                swap(i, filho);
                i = filho;
            }
            else {
                i = tam;
            }
        }
    }

    boolean temFilho(int i, int tam) {
        return(i <= tam/2);
    }

    int getMaiorFilho(int i, int tam) {
        int filho;
        if ((2 * i == tam) || (filmes[2*i].getGenero().compareTo(filmes[2*i+1].getGenero()) > 0) || ((filmes[2*i].getGenero().compareTo(filmes[2*i+1].getGenero()) == 0) && (filmes[2*i].getNome().compareTo(filmes[2*i+1].getNome()) > 0))) {
            filho = 2*i;
        } else {
            filho = 2*i + 1;
        }
        return filho;
    }

    void swap(int posA, int posB) {
        Filme tmp = new Filme();
        tmp = filmes[posA];
        filmes[posA] = filmes[posB];
        filmes[posB] = tmp;
    }
}