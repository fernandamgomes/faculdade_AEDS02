import java.util.Scanner;

public class operacoesABP {
    public static void main(String[] args) {
        // VARIAVEIS
        Scanner sc = new Scanner(System.in);
        ArvoreBinaria arvore = new ArvoreBinaria();

        while (sc.hasNextLine()) {
            String tmp = sc.nextLine();
            if(tmp.contains("INFIXA")) {
                arvore.caminharCentral();
            }
            else if(tmp.equals("PREFIXA")) {
                arvore.caminharPre();
            }
            else if(tmp.equals("POSFIXA")) {
                arvore.caminharPos();
            }
            else if(tmp.charAt(0) == 'I') {
                try {
                    arvore.inserir(tmp.charAt(2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else if(tmp.charAt(0) == 'P') {
                if (arvore.pesquisar(tmp.charAt(2)) == true) {
                    System.out.println(tmp.charAt(2) + " existe");
                }
                else {
                    System.out.println(tmp.charAt(2) + " nao existe");
                }
            }
        }
    }
}

class No {
    public char elemento;
    public No esq, dir;

    public No(char elemento) {
        this.elemento = elemento;
        this.esq = null;
        this.dir = null;
    }

    public No(char elemento, No esq, No dir) {
        this.elemento = elemento;
        this.esq = esq;
        this.dir = dir;
    }
}

class ArvoreBinaria {
    private No raiz;

    ArvoreBinaria() {
        raiz = null;
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
            throw new Exception("erro");
        }
        return i;
    }

    public void caminharCentral() {
        String tmp = "";
        tmp = caminharCentral(tmp, raiz);
        System.out.println(tmp.substring(0, tmp.length() -1));
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
        String tmp = "";
        tmp = caminharPre(tmp, raiz);
        System.out.println(tmp.substring(0, tmp.length() -1));
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
        String tmp = "";
        tmp = caminharPos(tmp, raiz);
        System.out.println(tmp.substring(0, tmp.length() -1));
    }
    private String caminharPos(String tmp, No i) {
        if (i != null) {
            tmp = caminharPos(tmp, i.esq);
            tmp = caminharPos(tmp, i.dir);
            tmp = tmp + i.elemento + " ";
        }
        return tmp;
    }

    public boolean pesquisar(char x) {
        return pesquisar(x, raiz);
    }

    private boolean pesquisar(char x, No i) {
        boolean resp;
        if (i == null) {
            resp = false;

        } else if (x == i.elemento) {
            resp = true;

        } else if (x < i.elemento) {
            resp = pesquisar(x, i.esq);

        } else {
            resp = pesquisar(x, i.dir);
        }
        return resp;
    }

}
