import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class avl {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        String filmeIn = sc.nextLine();
        ArvoreAVL arvore = new ArvoreAVL();
        // PRIMEIRA PARTE: ler linhas e inserir filmes na arvore ate achar a palavra FIM
        while (isFim(filmeIn) == false) {
            Filme tmp = new Filme();
            tmp.lerArquivo(filmeIn);
            // ler stdin - novo input
            arvore.inserir(tmp.getTitulo());
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
                arvore.inserir(tmp.getTitulo());
            } else if (operacao == 'R') {
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
        //arvore.caminharCentral();
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
class No {
    public String elemento; // Conteudo do no.
    public No esq, dir; // Filhos da esq e dir.
    public int nivel; // Numero de niveis abaixo do no

    public No(String elemento) {
        this(elemento, null, null, 1);
    }

    public No(String elemento, No esq, No dir, int nivel) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.nivel = nivel;
    }

    public void setNivel() {
        this.nivel = 1 + Math.max(getNivel(esq), getNivel(dir));
    }

    public static int getNivel(No no) {
        return (no == null) ? 0 : no.nivel;
    }
}

// --------------------------- ARVORE -----------------------
class ArvoreAVL {
    private No raiz; // Raiz da arvore.

    public ArvoreAVL() {
        raiz = null;
    }

    public boolean pesquisar(String x) {
        System.out.println(x);
        System.out.print("raiz");
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(String x, No i) {
        boolean resp;
        if (i == null) {
            resp = false;
            System.out.print(" NAO");
        } else if (x.equals(i.elemento)) {
            resp = true;
            System.out.print(" SIM");
        } else if (x.compareTo(i.elemento) < 0) {
            System.out.print(" esq");
            resp = pesquisar(x, i.esq);
        } else {
            System.out.print(" dir");
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

    public void caminharCentral() {
        System.out.print("[ ");
        caminharCentral(raiz);
        System.out.println("]");
    }

    private void caminharCentral(No i) {
        if (i != null) {
            caminharCentral(i.esq); // Elementos da esquerda.
            System.out.print(i.elemento + " "); // Conteudo do no.
            caminharCentral(i.dir); // Elementos da direita.
        }
    }

    public void inserir(String x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(String x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x.compareTo(i.elemento) < 0) {
            i.esq = inserir(x, i.esq);
        } else if (x.compareTo(i.elemento) > 0) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("Erro ao inserir!");
        }
        return balancear(i);
    }

    public void remover(String x) throws Exception {
        raiz = remover(x, raiz);
    }

    private No remover(String x, No i) throws Exception {
        if (i == null) {
            System.out.println("Erro ao remover!");
        } else if (x.compareTo(i.elemento) < 0) {
            i.esq = remover(x, i.esq);
        } else if (x.compareTo(i.elemento) > 0) {
            i.dir = remover(x, i.dir);
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
        return balancear(i);
    }

    private No maiorEsq(No i, No j) {
        // Encontrou o maximo da subarvore esquerda.
        if (j.dir == null) {
            i.elemento = j.elemento; // Substitui i por j.
            j = j.esq; // Substitui j por j.ESQ.
            // Existe no a direita.
        } else {
            // Caminha para direita.
            j.dir = maiorEsq(i, j.dir);
        }
        return j;
    }

    private No balancear(No no) throws Exception {
        if (no != null) {
            int fator = No.getNivel(no.dir) - No.getNivel(no.esq);
            // Se balanceada
            if (Math.abs(fator) <= 1) {
                no.setNivel();
                // Se desbalanceada para a direita
            } else if (fator == 2) {
                int fatorFilhoDir = No.getNivel(no.dir.dir) - No.getNivel(no.dir.esq);
                // Se o filho a direita tambem estiver desbalanceado
                if (fatorFilhoDir == -1) {
                    no.dir = rotacionarDir(no.dir);
                }
                no = rotacionarEsq(no);
                // Se desbalanceada para a esquerda
            } else if (fator == -2) {
                int fatorFilhoEsq = No.getNivel(no.esq.dir) - No.getNivel(no.esq.esq);
                // Se o filho a esquerda tambem estiver desbalanceado
                if (fatorFilhoEsq == 1) {
                    no.esq = rotacionarEsq(no.esq);
                }
                no = rotacionarDir(no);
            } else {
                throw new Exception(
                        "Erro no No(" + no.elemento + ") com fator de balanceamento (" + fator + ") invalido!");
            }
        }
        return no;
    }

    private No rotacionarDir(No no) {
        No noEsq = no.esq;
        No noEsqDir = noEsq.dir;

        noEsq.dir = no;
        no.esq = noEsqDir;
        no.setNivel(); // Atualizar o nivel do no
        noEsq.setNivel(); // Atualizar o nivel do noEsq

        return noEsq;
    }

    private No rotacionarEsq(No no) {
        No noDir = no.dir;
        No noDirEsq = noDir.esq;

        noDir.esq = no;
        no.dir = noDirEsq;

        no.setNivel(); // Atualizar o nivel do no
        noDir.setNivel(); // Atualizar o nivel do noDir
        return noDir;
    }
}