import java.util.Scanner;

public class matrizDinam {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int numCasos = sc.nextInt();
        int linhas = 0;
        int colunas = 0;

        for (int i = 0; i < numCasos; i++) {
            Matriz matrizes[] = new Matriz[2];
            for (int j = 0; j < 2; j++) {
                linhas = sc.nextInt();
                colunas = sc.nextInt();
                Matriz tmp = new Matriz(linhas, colunas);
                tmp.ler(sc);
                matrizes[j] = tmp;
            }
            matrizes[0].mostrarDiagonalPrincipal();
            matrizes[0].mostrarDiagonalSecundaria();
            matrizes[0].soma(matrizes[1]).imprimir();
            matrizes[0].multiplicacao(matrizes[1]).imprimir();
        }
        sc.close();
    }
}

class Celula {
    public int elemento;
    public Celula inf, sup, ant, prox;

    public Celula() {
        this(0);
    }

    public Celula(int elemento) {
        this(elemento, null, null, null, null);
    }

    public Celula(int elemento, Celula inf, Celula sup, Celula ant, Celula prox) {
        this.elemento = elemento;
        this.inf = inf;
        this.sup = sup;
        this.ant = ant;
        this.prox = prox;
    }
}

class Matriz {
    private Celula inicio;
    private int linhas;
    private int colunas;

    public Matriz() {
        this(3, 3);
    }

    public Matriz(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;

        inicio = new Celula();
        Celula tmp = inicio;
        // preencher linhas 1
        for (int i = 1; i < colunas; i++) {
            tmp.prox = new Celula();
            tmp.prox.ant = tmp;
            tmp = tmp.prox;
        }

        // preencher demais linhas
        tmp = inicio;
        for (int l = 1; l < linhas; l++, tmp = tmp.inf) {
            Celula i = tmp;
            i.inf = new Celula();
            i.inf.sup = i;
            Celula j = i.inf;
            for (int c = 1; c < colunas; c++, j = j.prox) {
                i = i.prox;
                i.inf = new Celula();
                i.inf.sup = i;
                i.inf.ant = j;
                j.prox = i.inf;
            }
        }
    }

    // ler input do usuario e preencher matriz
    public void ler(Scanner sc) {
        Celula tmp = inicio;
        Celula guardaC0 = inicio;
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                tmp.elemento = sc.nextInt();
                tmp = tmp.prox;
            }
            guardaC0 = guardaC0.inf;
            tmp = guardaC0;
        }
    }

    void imprimir() {
        Celula tmp = inicio;
        Celula guardaC0 = inicio;
        Scanner sc = new Scanner(System.in);
        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                System.out.print(tmp.elemento + " ");
                tmp = tmp.prox;
            }
            System.out.println("");
            guardaC0 = guardaC0.inf;
            tmp = guardaC0;
        }
        sc.close();
    }

    void mostrarDiagonalPrincipal() {
        Celula tmp = inicio;
        System.out.print(tmp.elemento + " ");
        for (int c = 0; c < colunas - 1; c++, tmp = tmp.prox.inf) {
            System.out.print(tmp.prox.inf.elemento + " ");
        }
        System.out.println();
    }

    void mostrarDiagonalSecundaria() {
        Celula tmp = inicio;
        for (int c = 0; c < colunas - 1; c++) {
            tmp = tmp.prox;
        }
        System.out.print(tmp.elemento + " ");
        for (int c = 0; c < colunas - 1; c++, tmp = tmp.ant.inf) {
            System.out.print(tmp.ant.inf.elemento + " ");
        }
        System.out.println();
    }

    Matriz soma(Matriz matrizDois) {
        // VARIAVEIS
        Matriz resultado = new Matriz(linhas, colunas);
        Celula tmpR = resultado.inicio;
        Celula tmpM1 = this.inicio;
        Celula tmpM2 = matrizDois.inicio;
        Celula guardaC0M1 = this.inicio;
        Celula guardaC0M2 = matrizDois.inicio;
        Celula guardaC0MR = resultado.inicio;

        for (int l = 0; l < linhas; l++) {
            for (int c = 0; c < colunas; c++) {
                tmpR.elemento = tmpM1.elemento + tmpM2.elemento;
                tmpR = tmpR.prox;
                tmpM1 = tmpM1.prox;
                tmpM2 = tmpM2.prox;
            }
            guardaC0M1 = tmpM1 = guardaC0M1.inf;
            guardaC0M2 = tmpM2 = guardaC0M2.inf;
            guardaC0MR = tmpR = guardaC0MR.inf;

        }
        return resultado;
    }

    Matriz multiplicacao(Matriz matrizDois) {
        // VARIAVEIS
        Matriz resultado = new Matriz(linhas, colunas);
        Celula tmpR = resultado.inicio;
        Celula tmpM1 = this.inicio;
        Celula tmpM2 = matrizDois.inicio;
        Celula guardaC0M1 = this.inicio;
        Celula guardaC0M2 = matrizDois.inicio;
        Celula guardaC0MR = resultado.inicio;

        for (int lR = 0; lR < linhas; lR++) {
            for (int l1 = 0; l1 < linhas; l1++) {
                for (int c = 0; c < colunas; c++) {
                    // CALCULAR O VALOR DO ELEMENTO NA MATRIZ RESULTANTE
                    tmpR.elemento += tmpM1.elemento * tmpM2.elemento;
                    // PERCORRER LINHA M1
                    tmpM1 = tmpM1.prox;
                    // PERCORRER COLUNA NA M2
                    tmpM2 = tmpM2.inf;
                }
                // PREENCHEU O ELEMENTO NA MATRIZ RESULTANTE --> PROX ELEMENTO DA MESMA LINHA
                tmpR = tmpR.prox;
                // VOLTAR AO PRIMEIRO ELEMENTO DA LINHA DA MATRIZ UM
                tmpM1 = guardaC0M1;
                // IR PARA O PRIMEIRO ELEMENTO DA PROXIMA COLUNA NA MATRIZ 2
                tmpM2 = guardaC0M2.prox;

            }
            // FIM DA LINHA DA MATRIZ RESULTANTE
            // IR PARA A PROXIMA LINHA
            guardaC0MR = guardaC0MR.inf;
            tmpR = guardaC0MR;

            // NA MATRIZ 1, A LINHA TB PODE SER DESCARTADA, NAO VAI SER MAIS USADA NA
            // MULTIPLICACAO
            guardaC0M1 = guardaC0M1.inf;
            tmpM1 = guardaC0M1;

            // NA MATRIZ 2, AO FINAL DE PREENCHER QUALQUER LINHA DA MATRIZ RESULTANTE,
            // RETORNAR A 1 COLUNA
            tmpM2 = guardaC0M2;
        }
        return resultado;
    }
}
