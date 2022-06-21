import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Scanner;
public class arvArv {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        ArvoreArvore arvore = new ArvoreArvore();
        String filmeIn = sc.nextLine();
        // 1 PARTE -> ler input filme e inserir na arvore
        while (isFim(filmeIn) == false) {
            Filme tmp = new Filme();
            tmp.lerArquivo(filmeIn);
            arvore.inserir(tmp.getTitulo());
            filmeIn = sc.nextLine();
        }
        // 2 PARTE -> INSERCOES
        filmeIn = sc.nextLine();
        int num = Integer.parseInt(filmeIn);
        while (num > 0) {
            num--;
            filmeIn = sc.nextLine();
            Filme tmp = new Filme();
            filmeIn = filmeIn.substring(2);
            tmp.lerArquivo(filmeIn);
            arvore.inserir(tmp.getTitulo());
        }
        // 3 PARTE -> PESQUISA
        filmeIn = sc.nextLine();
        while (!(isFim(filmeIn))) {
            arvore.caminharPre(filmeIn.charAt(0), filmeIn);
            filmeIn = sc.nextLine();
        }
        sc.close();
    }

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

// ---------------- NOS --------------------------
class No2 { // --> string
    public String elemento; // Conteudo do no.
    public No2 esq; // No da esquerda.
    public No2 dir; // No da direita.

    No2(String elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
    }

    No2(String elemento, No2 esq, No2 dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class No { // --> char
    public char elemento; // Conteudo do no.
    public No esq; // No da esquerda.
    public No dir; // No da direita.
    public No2 outro;

    No(char elemento) {
        this.elemento = elemento;
        this.esq = this.dir = null;
        this.outro = null;
    }

    No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
        this.outro = null;
    }
}

// ------------------ ARVORE DE ARVORE --------------------------
class ArvoreArvore {
    private No raiz; // Raiz da arvore.
    ArrayList<String> percuso = new ArrayList<String>();
    boolean temporaria_resp = false;

    public ArvoreArvore() throws Exception {
        raiz = null;
        inserir('D');
        inserir('R');
        inserir('Z');
        inserir('X');
        inserir('V');
        inserir('B');
        inserir('F');
        inserir('P');
        inserir('U');
        inserir('I');
        inserir('G');
        inserir('E');
        inserir('J');
        inserir('L');
        inserir('H');
        inserir('T');
        inserir('A');
        inserir('W');
        inserir('S');
        inserir('O');
        inserir('M');
        inserir('N');
        inserir('K');
        inserir('C');
        inserir('Y');
        inserir('Q');
        // os outros 23 caracteres.
    }

    public void caminharPre(char x, String filme) {
        System.out.println("=> " + filme);
        percuso.add("raiz");
        caminharPre(raiz, x, filme);
        percuso.add("fim");
        for (int i = 0; i < percuso.size() - 1; i++) {
            System.out.print(percuso.get(i) + " ");
            if (percuso.get(i + 1).equals("encontrou")) {
                i = percuso.size();
            }
        }
        if (temporaria_resp) {
            System.out.print(" SIM");
        } else {
            System.out.print(" NAO");
        }
        percuso.clear();
        System.out.println();
        percuso.clear();
        temporaria_resp = false;
    }

    private void caminharPre(No i, char x, String filme) {
        boolean t = false;
        if (i != null && t != true) {
            if (pesquisarSegundaArvore(i.outro, filme)) {
                t = true;
                percuso.add("encontrou");
                temporaria_resp = true;
            }
            percuso.add(" ESQ");
            caminharPre(i.esq, x, filme);
            percuso.add(" DIR");
            caminharPre(i.dir, x, filme); // Elementos da direita.
        }
    }

    public void inserir(char x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(char x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("Erro ao inserir!");
        }
        return i;
    }
    public void inserir(String s) throws Exception {
        inserir(s, raiz);
    }

    public void inserir(String s, No i) throws Exception {
        if (i == null) {
            throw new Exception("Erro ao inserir: caractere invalido!");
        } else if (s.charAt(0) < i.elemento) {
            inserir(s, i.esq);
        } else if (s.charAt(0) > i.elemento) {
            inserir(s, i.dir);
        } else {
            i.outro = inserir(s, i.outro);
        }
    }

    private No2 inserir(String s, No2 i) throws Exception {
        if (i == null) {
            i = new No2(s);
        } else if (s.compareTo(i.elemento) < 0) {
            i.esq = inserir(s, i.esq);
        } else if (s.compareTo(i.elemento) > 0) {
            i.dir = inserir(s, i.dir);
        } else {
            throw new Exception("Erro ao inserir: elemento existente!");
        }
        return i;
    }

    private boolean pesquisarSegundaArvore(No2 no, String x) {
        boolean resp;
        if (no == null) {
            resp = false;
        } else if (x.compareTo(no.elemento) < 0) {
            percuso.add("esq");
            resp = pesquisarSegundaArvore(no.esq, x);
        } else if (x.compareTo(no.elemento) > 0) {
            percuso.add("dir");
            resp = pesquisarSegundaArvore(no.dir, x);
        } else {
            resp = true;
        }
        return resp;
    }
}
