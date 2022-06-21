import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class listaQuick {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        ListaDupla lista = new ListaDupla();
        int contador = -1;
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
        lista.ordQuick(0, contador);
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


class CelulaDupla {
    public Filme filme;
    public CelulaDupla ant;
    public CelulaDupla prox;

    /**
     * Construtor da classe.
     */
    public CelulaDupla() {
    }

    /**
     * Construtor da classe.
     * 
     * @param elemento int inserido na celula.
     */
    public CelulaDupla(Filme filme) {
        this.filme = filme;
        this.ant = this.prox = null;
    }
}

class ListaDupla {
    private CelulaDupla primeiro;
    private CelulaDupla ultimo;

    public ListaDupla() {
        primeiro = new CelulaDupla();
        ultimo = primeiro;
    }

    public void inserirInicio(Filme filme) {
        CelulaDupla tmp = new CelulaDupla(filme);
        tmp.ant = primeiro;
        tmp.prox = primeiro.prox;
        primeiro.prox = tmp;

        if (primeiro == ultimo) {
            ultimo = tmp;
        } else {
            tmp.prox.ant = tmp;
        }
        tmp = null;
    }

    public void inserirFim(Filme filme) {
        CelulaDupla tmp = new CelulaDupla(filme);
        ultimo.prox = tmp;
        tmp.ant = ultimo;
        ultimo = tmp;
        tmp = null;
    }

    public void inserir(Filme filme, int pos) throws Exception {
        if (pos < 0 || pos > tamanho()) {
            throw new Exception("Erro ao inserir posicao invalida!");
        } else if (pos == 0) {
            inserirInicio(filme);
        } else if (pos == tamanho()) {
            inserirFim(filme);
        } else {
            // Caminhar ate a posicao anterior a insercao
            CelulaDupla i = primeiro;
            for (int j = 0; j < pos; j++, i = i.prox);

            CelulaDupla tmp = new CelulaDupla(filme);
            tmp.ant = i;
            tmp.prox = i.prox;
            tmp.ant.prox = tmp.prox.ant = tmp;
            tmp = i = null;
        }
    }

    public Filme removerInicio() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover lista vazia!");
        }

        CelulaDupla tmp = primeiro;
        primeiro = primeiro.prox;
        Filme resp = primeiro.filme;
        tmp.prox = primeiro.ant = null;
        tmp = null;
        return resp;
    }

    public Filme removerFim() throws Exception {
        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover lista!");
        }
        Filme resp = ultimo.filme;
        ultimo = ultimo.ant;
        ultimo.prox.ant = null;
        ultimo.prox = null;
        return resp;
    }

    public Filme remover(int pos) throws Exception {
        Filme resp;
        int tamanho = tamanho();

        if (primeiro == ultimo) {
            throw new Exception("Erro ao remover lista!");

        } else if (pos < 0 || pos >= tamanho) {
            throw new Exception("Erro ao remover posicao invalida!");
        } else if (pos == 0) {
            resp = removerInicio();
        } else if (pos == tamanho - 1) {
            resp = removerFim();
        } else {
            CelulaDupla i = primeiro.prox;
            for (int j = 0; j < pos; j++, i = i.prox);
            // i == pos
            i.ant.prox = i.prox;
            i.prox.ant = i.ant;
            resp = i.filme;
            i.prox = i.ant = null;
            i = null;
        }

        return resp;
    }

    public int tamanho() {
        int tamanho = 0;
        for (CelulaDupla i = primeiro; i != ultimo; i = i.prox, tamanho++);
        return tamanho;
    }

    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        CelulaDupla tmp = primeiro;
        for (tmp = tmp.prox; tmp != null; tmp = tmp.prox) {
            System.out.print(
                    tmp.filme.getNome() + " " +
                            tmp.filme.getTitulo() + " " + sdf.format(tmp.filme.getData())
                            + " "
                            + tmp.filme.getDuracao() + " " + tmp.filme.getGenero() + " " + tmp.filme.getIdioma() + " "
                            + tmp.filme.getSituacao()
                            + " "
                            + tmp.filme.getOrcamento() + " ");

            // IMPRIMIR ARRAY PALAVRAS CHAVE
            System.out.print("[");
            if (tmp.filme.getPalavrasChave() != null) {
                String[] pChave = tmp.filme.getPalavrasChave();

                for (int j = 0; j < pChave.length - 1; j++) {
                    System.out.print(pChave[j] + ", ");
                }
                System.out.print(pChave[pChave.length - 1]);
            }
            System.out.print("]\n");
        }
    }

    public CelulaDupla posCelula(int pos) {
        CelulaDupla resp = primeiro.prox;
        for (int j = 0; j < pos; j++, resp = resp.prox);
        return resp;
    }

    void ordQuick(int esq, int dir) {
        // VARIAVEIS
        int i = esq;
        int j = dir;
        int pivo = (esq + dir) / 2;
        while (i <= j) {
            while ((posCelula(i).filme.getSituacao().compareTo(posCelula(pivo).filme.getSituacao()) < 0)
                    || ((posCelula(i).filme.getSituacao().compareTo(posCelula(pivo).filme.getSituacao()) == 0)
                            && (posCelula(i).filme.getNome().compareTo(posCelula(pivo).filme.getNome()) < 0))) {
                i++;
            }
            while ((posCelula(j).filme.getSituacao().compareTo(posCelula(pivo).filme.getSituacao()) > 0)
                    || ((posCelula(j).filme.getSituacao().compareTo(posCelula(pivo).filme.getSituacao()) == 0)
                            && (posCelula(j).filme.getNome().compareTo(posCelula(pivo).filme.getNome()) > 0))) {
                j--;
            }
            if (i <= j) {
                swap(i, j);
                i++;
                j--;
            }
        }
        if (esq < j) {
            ordQuick(esq, j);
        }
        if (i < dir) {
            ordQuick(i, dir);
        }
    }

    void swap(int posA, int posB) {
        Filme tmp = posCelula(posA).filme;
        posCelula(posA).filme = posCelula(posB).filme;
        posCelula(posB).filme = tmp;
    }
}
