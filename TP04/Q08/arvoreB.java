import java.util.Scanner;

public class arvoreB {
    public static void main(String[] args) throws Exception {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        int numRep = 0;
        int tmp = 0;
        int nums = 0;

        numRep = sc.nextInt();

        for (int contador = 0; contador < numRep; contador++) {
            ArvoreBinaria arvore = new ArvoreBinaria();
            nums = sc.nextInt();
            for (int i = 0; i < nums; i++) {
                tmp = sc.nextInt();
                arvore.inserir(tmp);
            }
            System.out.println("Case " + (contador + 1) + ":");
            arvore.caminharPre();
            arvore.caminharCentral();
            arvore.caminharPos();
            System.out.println();
        }
        sc.close();
    }
}

class No {
    public int elemento;
    public No esq, dir;

    public No(int elemento) {
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }

    public No(int elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private No raiz;
    String tmp = "";

    ArvoreBinaria() {
        raiz = null;
    }

    public void inserir(int x) throws Exception {
        raiz = inserir(x, raiz);
    }

    private No inserir(int x, No i) throws Exception {
        if (i == null) {
            i = new No(x);
        } else if (x < i.elemento) {
            i.esq = inserir(x, i.esq);
        } else if (x > i.elemento) {
            i.dir = inserir(x, i.dir);
        } else {
            throw new Exception("erro");
        }
        return i;
    }

    public void caminharCentral() {
        tmp = "";
        tmp = caminharCentral(tmp, raiz);
        System.out.print("In..: ");
        System.out.println(tmp.substring(0, tmp.length() - 1));
    }

    private String caminharCentral(String tmp, No i) {
        if (i != null) {
            tmp = caminharCentral(tmp, i.esq);
            tmp = tmp + i.elemento + " ";
            tmp = caminharCentral(tmp, i.dir);
        }
        return tmp;
    }

    public void caminharPre() {
        tmp = "";
        tmp = caminharPre(tmp, raiz);
        System.out.print("Pre.: ");
        System.out.println(tmp.substring(0, tmp.length() - 1));
    }

    private String caminharPre(String tmp, No i) {
        if (i != null) {
            tmp = tmp + i.elemento + " ";
            tmp = caminharPre(tmp, i.esq);
            tmp = caminharPre(tmp, i.dir);
        }
        return tmp;
    }

    public void caminharPos() {
        tmp = "";
        tmp = caminharPos(tmp, raiz);
        System.out.print("Post: ");
        System.out.println(tmp.substring(0, tmp.length() - 1));
    }

    private String caminharPos(String tmp, No i) {
        if (i != null) {
            tmp = caminharPos(tmp, i.esq);
            tmp = caminharPos(tmp, i.dir);
            tmp = tmp + i.elemento + " ";
        }
        return tmp;
    }

}