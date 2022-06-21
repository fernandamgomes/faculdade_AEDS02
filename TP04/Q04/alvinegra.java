import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class alvinegra {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        String filmeIn = sc.nextLine();
        ArvoreAlvinegra arvore = new ArvoreAlvinegra();
        // PRIMEIRA PARTE: ler linhas e inserir filmes na arvore ate achar a palavra FIM
        while (isFim(filmeIn) == false) {
            Filme tmp = new Filme();
            tmp.lerArquivo(filmeIn);
            // ler stdin - novo input
            arvore.inserir(tmp);
            filmeIn = sc.nextLine();
        }
        // SEGUNDA PARTE pesquisar
        filmeIn = sc.nextLine();
        while (isFim(filmeIn) == false) {
            arvore.pesquisar(filmeIn);
            System.out.println();
            filmeIn = sc.nextLine();
        }
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
// ---------------------------- NO ARVORE ---------------------
class NoAN {
    public boolean cor;
    public Filme filme;
    public NoAN esq, dir;

    public NoAN() {
        this(null);
    }

    public NoAN(Filme filme) {
        this(filme, false, null, null);
    }

    public NoAN(Filme filme, boolean cor) {
        this(filme, cor, null, null);
    }

    public NoAN(Filme filme, boolean cor, NoAN esq, NoAN dir) {
        this.cor = cor;
        this.filme = filme;
        this.esq = esq;
        this.dir = dir;
    }
}

// --------------------------- ARVORE -----------------------
class ArvoreAlvinegra {
    private NoAN raiz; // Raiz da arvore.

    public ArvoreAlvinegra() {
        raiz = null;
    }

    // PESQUISA E IMPRIME CAMINHAMENTO
    public boolean pesquisar(String chave) {
        System.out.println(chave);
        System.out.print("raiz");
        return pesquisar(chave, raiz);
    }

    private boolean pesquisar(String chave, NoAN i) {
        boolean resp;
        if (i == null) {
            resp = false;
            System.out.print(" NAO");
        } else if (chave.equals(i.filme.getTitulo())) {
            resp = true;
            System.out.print(" SIM");
        } else if (chave.compareTo(i.filme.getTitulo()) < 0) {
            System.out.print(" esq");
            resp = pesquisar(chave, i.esq);
        } else {
            System.out.print(" dir");
            resp = pesquisar(chave, i.dir);
        }
        return resp;
    }

    public void inserir(Filme filme) throws Exception {
        String tituloFilme = filme.getTitulo();
        // Se a arvore estiver vazia
        if (raiz == null) {
            raiz = new NoAN(filme);

            // Senao, se a arvore tiver um filme
        } else if (raiz.esq == null && raiz.dir == null) {
            if (tituloFilme.compareTo(raiz.filme.getTitulo()) < 0) {
                raiz.esq = new NoAN(filme);
            } else {
                raiz.dir = new NoAN(filme);
            }

            // Senao, se a arvore tiver dois elementos (raiz e dir)
        } else if (raiz.esq == null) {
            if (tituloFilme.compareTo(raiz.filme.getTitulo()) < 0) {
                raiz.esq = new NoAN(filme);
            } else if (tituloFilme.compareTo(raiz.dir.filme.getTitulo()) < 0) {
                raiz.esq = new NoAN(raiz.filme);
                raiz.filme = filme;
            } else {
                raiz.esq = new NoAN(raiz.filme);
                raiz.filme = raiz.dir.filme;
                raiz.dir.filme = filme;
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, se a arvore tiver dois elementos (raiz e esq)
        } else if (raiz.dir == null) {
            if (tituloFilme.compareTo(raiz.filme.getTitulo()) > 0) {
                raiz.dir = new NoAN(filme);
            } else if (tituloFilme.compareTo(raiz.esq.filme.getTitulo()) > 0) {
                raiz.dir = new NoAN(raiz.filme);
                raiz.filme = filme;
            } else {
                raiz.dir = new NoAN(raiz.filme);
                raiz.filme = raiz.esq.filme;
                raiz.esq.filme = filme;
            }
            raiz.esq.cor = raiz.dir.cor = false;

            // Senao, a arvore tem tres ou mais elementos
        } else {
            inserir(filme, null, null, null, raiz);
        }
        raiz.cor = false;
    }

    private void balancear(NoAN bisavo, NoAN avo, NoAN pai, NoAN i) {
        // Se o pai tambem e preto, reequilibrar a arvore, rotacionando o avo
        if (pai.cor == true) {
            // 4 tipos de reequilibrios e acoplamento
            if (pai.filme.getTitulo().compareTo(avo.filme.getTitulo()) > 0) { // rotacao a esquerda ou direita-esquerda
                if (i.filme.getTitulo().compareTo(pai.filme.getTitulo()) > 0) {
                    avo = rotacaoEsq(avo);
                } else {
                    avo = rotacaoDirEsq(avo);
                }
            } else { // rotacao a direita ou esquerda-direita
                if (i.filme.getTitulo().compareTo(pai.filme.getTitulo()) < 0) {
                    avo = rotacaoDir(avo);
                } else {
                    avo = rotacaoEsqDir(avo);
                }
            }
            if (bisavo == null) {
                raiz = avo;
            } else if (avo.filme.getTitulo().compareTo(bisavo.filme.getTitulo()) < 0) {
                bisavo.esq = avo;
            } else {
                bisavo.dir = avo;
            }
            // reestabelecer as cores apos a rotacao
            avo.cor = false;
            avo.esq.cor = avo.dir.cor = true;
        } // if(pai.cor == true)
    }

    private void inserir(Filme filme, NoAN bisavo, NoAN avo, NoAN pai, NoAN i) throws Exception {
        String tituloFilme = filme.getTitulo();
        if (i == null) {
            if (tituloFilme.compareTo(pai.filme.getTitulo()) < 0) {
                i = pai.esq = new NoAN(filme, true);
            } else {
                i = pai.dir = new NoAN(filme, true);
            }
            if (pai.cor == true) {
                balancear(bisavo, avo, pai, i);
            }
        } else {
            // Achou um 4-no: eh preciso fragmenta-lo e reequilibrar a arvore
            if (i.esq != null && i.dir != null && i.esq.cor == true && i.dir.cor == true) {
                i.cor = true;
                i.esq.cor = i.dir.cor = false;
                if (i == raiz) {
                    i.cor = false;
                } else if (pai.cor == true) {
                    balancear(bisavo, avo, pai, i);
                }
            }
            if (tituloFilme.compareTo(i.filme.getTitulo()) < 0) {
                inserir(filme, avo, pai, i, i.esq);
            } else if (tituloFilme.compareTo(i.filme.getTitulo()) > 0) {
                inserir(filme, avo, pai, i, i.dir);
            } else {
                throw new Exception("Erro inserir (filme repetido)!");
            }
        }
    }

    private NoAN rotacaoDir(NoAN no) {
        NoAN noEsq = no.esq;
        NoAN noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;

        return noEsq;
    }

    private NoAN rotacaoEsq(NoAN no) {
        NoAN noDir = no.dir;
        NoAN noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;
        return noDir;
    }

    private NoAN rotacaoDirEsq(NoAN no) {
        no.dir = rotacaoDir(no.dir);
        return rotacaoEsq(no);
    }

    private NoAN rotacaoEsqDir(NoAN no) {
        no.esq = rotacaoEsq(no.esq);
        return rotacaoDir(no);
    }
}