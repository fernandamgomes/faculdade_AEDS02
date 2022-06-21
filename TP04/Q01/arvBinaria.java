import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class arvBinaria {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        String filmeIn = sc.nextLine();
        ArvoreBinaria arvore = new ArvoreBinaria();
        // PRIMEIRA PARTE: ler linhas e inserir filmes na arvore ate achar a palavra FIM
        while (isFim(filmeIn) == false) {
            Filme tmp = new Filme();
            tmp.lerArquivo(filmeIn);
            // ler stdin - novo input
            arvore.inserir(tmp);
            filmeIn = sc.nextLine();
        }
        // SEGUNDA PARTE inserir, remover
        int num = Integer.parseInt(sc.nextLine());
        while (num > 0) {
            filmeIn = sc.nextLine();
            num--;
            char operacao = filmeIn.charAt(0);
            filmeIn = filmeIn.substring(2);
            if (operacao == 'I') {
                Filme tmp = new Filme();
                tmp.lerArquivo(filmeIn);
                arvore.inserir(tmp);
            }
            else if (operacao == 'R'){
                arvore.remover(filmeIn);
            }
        }
        // TERCEIRA PARTE pesquisar
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
        if (file.isFile() == false) {
            throw new Exception("filme nao existe");
        }
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
            else if (line.contains("Or�amento")) {
                line = removeTag(line);
                line = line.replace("Or�amento", "").trim().replaceAll("[,$-]", "");
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
            else if (line.contains("strong><bdi>Situa��o</bdi></strong")) {
                line = removeTag(line);
                line = line.replace("Situa��o", "").trim();
                this.setSituacao(line);
            }

            // TITULO
            else if (line.contains("<strong>T�tulo original</strong>")) {
                temTitulo = true;
                line = removeTag(line);
                line = line.replace("T�tulo original", "").trim();
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
class No {
    public Filme filme;
    public No esq;
    public No dir;

    public No(Filme filme) {
        this.filme = filme;
        this.dir = null;
        this.esq = null;
    }

    public No(Filme filme, No esq, No dir) {
        this.filme = filme;
        this.esq = esq;
        this.dir = dir;
    }
}

// --------------------------- ARVORE -----------------------
class ArvoreBinaria {
    private No raiz;

    public ArvoreBinaria() {
        raiz = null;
    }

    // PESQUISA E IMPRIME CAMINHAMENTO
    public boolean pesquisar(String chave) {
        System.out.println(chave);
        System.out.print("=>raiz");
        return pesquisar(chave, raiz);
    }

    private boolean pesquisar(String chave, No i) {
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

    // INSERIR
    public void inserir(Filme filme) throws Exception {
        raiz = inserir(filme, raiz);
    }
    private No inserir(Filme filme, No i) throws Exception {
        if (i == null) {
            i = new No(filme);

        } else if (filme.getTitulo().compareTo(i.filme.getTitulo()) < 0) {
            i.esq = inserir(filme, i.esq);

        } else if (filme.getTitulo().compareTo(i.filme.getTitulo()) > 0) {
            i.dir = inserir(filme, i.dir);

        } else {
            throw new Exception("Erro ao inserir!");
        }
        return i;
    }

    // REMOVER
    public void remover(String filme) throws Exception {
        raiz = remover(filme, raiz);
    }
    private No remover(String chave, No i) throws Exception {
        if (i == null) {
            System.out.println("ERRO AO REMOVER" + chave);
        } else if (chave.compareTo(i.filme.getTitulo()) < 0) {
            i.esq = remover(chave, i.esq);
        } else if (chave.compareTo(i.filme.getTitulo()) > 0) {
            i.dir = remover(chave, i.dir);
            // Sem no a direita.
        } else if (i.dir == null) {
            i = i.esq;
            // Sem no a esquerda.
        } else if (i.esq == null) {
            i = i.dir;
            // No a esquerda e no a direita.
        } else {
            i.esq = maiorEsq(i, i.esq);
        }
        return i;
    }

    private No maiorEsq(No i, No j) {
        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.filme = j.filme; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.
            // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

}