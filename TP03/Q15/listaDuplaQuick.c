#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define TAM 80
// TIPO FILME ===================================================================
typedef struct
{
    char nome[100];
    char titulo[100];
    char data[15];
    int duracao;
    char genero[100];
    char idioma[50];
    char situacao[50];
    float orcamento;
    char palavrasChave[50][100];
    int numPChave;
} Filme;
Filme filmes[TAM];
int contador = 0;
// TIPO CELULA ===================================================================
typedef struct CelulaDupla
{
    Filme filme;              // Elemento inserido na celula.
    struct CelulaDupla *prox; // Aponta a celula prox.
    struct CelulaDupla *ant;  // Aponta a celula anterior.
} CelulaDupla;

CelulaDupla *novaCelulaDupla(Filme filme)
{
    CelulaDupla *nova = (CelulaDupla *)malloc(sizeof(CelulaDupla));
    nova->filme = filme;
    nova->ant = nova->prox = NULL;
    return nova;
}
// LISTA PROPRIAMENTE DITA =======================================================
CelulaDupla *primeiro;
CelulaDupla *ultimo;

// CABECALHO FUNCOES =============================================================
Filme lerArquivo(char *arquivo, int contador);
char *removeTag(char *line, char linemod[300]);
int isFim(char str[]);
float calculoOrcamento(char line[]);
char *strremove(char *str, const char *sub);
void start();
void inserirInicio(Filme filme);
void inserirFim(Filme filme);
Filme removerInicio();
Filme removerFim();
int tamanho();
void inserir(Filme filme, int pos);
Filme remover(int pos);
void imprimir();
CelulaDupla* sort (CelulaDupla* esq, CelulaDupla* dir);
void quicksort (CelulaDupla* esq, CelulaDupla* dir);
void swap (CelulaDupla* i, CelulaDupla* j);

// INICIO PROGRAMA ================================================================
int main(void)
{
    // VARIAVEIS
    char str[100];
    int numOperacoes = 0;
    // ler nome do arquivo a partir do pubin
    fgets(str, 100, stdin);
    str[strlen(str) - 1] = '\0';
    start();
    // enquanto nao alcancar o fim do pubin ler arquivos
    while (isFim(str) == 0)
    {
        // preencher struct filme com dados do filme lido e salvar o tam do array de palavras chave
        Filme tmp = lerArquivo(str, contador);
        inserirFim(tmp);
        contador++;
        fflush(stdin);
        // ler nome do arquivo a partir do pubin
        fgets(str, 100, stdin);
        str[strlen(str) - 1] = '\0';
    }
    quicksort (primeiro->prox, ultimo);
    imprimir();
    return 0;
}

Filme lerArquivo(char *arquivo, int contador)
{
    Filme tmp;
    // ABRIR ARQUIVO HTML
    char arquivoPath[] = "/tmp/filmes/";
    strcat(arquivoPath, arquivo);
    FILE *fp = fopen(arquivoPath, "r");
    int pChave = 0;

    // SE ARQUIVO FOR VALIDO LER line A line PROCURANDO POR ELEMENTOS
    if (fp != NULL)
    {
        char line[300];
        fgets(line, 300, fp);
        int temTitulo = 1;
        char linecopy[300];

        // ler linhas ate a tag de fechamento do html
        while (strstr(line, "</html>") == NULL)
        {
            // printf("%s %d\n", str, strlen(str));
            memset(&linecopy[0], 0, sizeof(linecopy));
            // NOME
            if (strstr(line, "h2 class"))
            {
                fgets(line, 300, fp);
                removeTag(line, linecopy);
                strcpy(tmp.nome, linecopy);
            }

            // TITULO
            else if (strstr(line, "<strong>Título original</strong>"))
            {
                // formatar string e atualizar que esse filme tem titulo setado
                temTitulo = 0;
                removeTag(line, linecopy);
                strremove(linecopy, "Título original ");
                strcpy(tmp.titulo, linecopy);
            }

            // DATA LANCAMENTO
            else if (strstr(line, "span class=\"release\""))
            {
                fgets(line, 300, fp);
                int pos = 0;
                for (int i = 0; i < strlen(line) - 1; i++)
                {
                    if ((line[i] == '/') || (isdigit(line[i])))
                    {
                        linecopy[pos] = line[i];
                        pos++;
                    }
                }
                strcpy(tmp.data, linecopy);
            }

            // DURACAO
            else if (strstr(line, "runtime"))
            {
                fgets(line, 300, fp);
                fgets(line, 300, fp);
                // VARIAVEIS
                int indexM = 0;
                int indexH = 0;
                int pos = 0;
                char horas[5];
                char minutos[5];
                // formatar string e salvar a posicao do caractere "H" e do caractere "M"
                for (int i = 0; i < strlen(line); i++)
                {
                    if ((isspace(line[i])) == 0)
                    {
                        if (line[i] == 'h')
                        {
                            indexH = pos;
                        }
                        else if (line[i] == 'm')
                        {
                            indexM = pos;
                        }
                        linecopy[pos] = line[i];
                        pos++;
                    }
                }
                // formatar string HORAS
                memset(&horas[0], 0, sizeof(horas));
                for (int i = 0; i < indexH; i++)
                {
                    horas[i] = linecopy[i];
                }

                // formatar string MINUTOS
                memset(&minutos[0], 0, sizeof(minutos));
                if (indexH != 0)
                {
                    indexH++;
                }
                pos = 0;
                for (indexH; indexH < indexM; indexH++)
                {
                    minutos[pos] = linecopy[indexH];
                    pos++;
                }
                // calculo duracao
                int duracao = atoi(horas) * 60 + atoi(minutos);
                tmp.duracao = duracao;
            }

            // GENERO
            else if (strstr(line, "genres"))
            {
                // pular linhas ate encontrar a linha certa
                while (strstr(line, "<a href") == NULL)
                {
                    fgets(line, 300, fp);
                }
                // formatar string
                removeTag(line, linecopy);
                strremove(linecopy, "&nbsp;");
                strcpy(tmp.genero, linecopy);
            }

            // SITUACAO
            else if (strstr(line, "<bdi>Situação</bdi>"))
            {
                // formatar string
                removeTag(line, linecopy);
                strremove(linecopy, "Situação ");
                strcpy(tmp.situacao, linecopy);
            }

            // IDIOMA
            else if (strstr(line, "Idioma"))
            {
                // formatar string
                removeTag(line, linecopy);
                strremove(linecopy, "Idioma original ");
                strcpy(tmp.idioma, linecopy);
            }

            // ORCAMENTO
            else if (strstr(line, "Orçamento"))
            {
                // formatar string
                removeTag(line, linecopy);
                float orcamento = calculoOrcamento(linecopy);
                tmp.orcamento = orcamento;
            }

            // PALAVRAS CHAVE
            else if (strstr(line, "Palavras-chave"))
            {
                while ((strstr(line, "<li><a") == NULL) && (strstr(line, "Nenhuma palavra") == NULL))
                {
                    fgets(line, 300, fp);
                }

                if (strstr(line, "Nenhuma palavra-chave"))
                {
                    strcpy(tmp.palavrasChave[0], "\0");
                }
                else
                {
                    while ((strstr(line, "</ul>") == NULL))
                    {
                        removeTag(line, linecopy);
                        if (linecopy[0] != '\0')
                        {
                            strcpy(tmp.palavrasChave[pChave], linecopy);
                            pChave++;
                        }
                        fgets(line, 300, fp);
                    }
                }
                pChave = pChave - 1;
                tmp.numPChave = pChave;
            }
            // ler proxima linha ate o fim do arquivo
            fgets(line, 500, fp);
        }
        // apos leitura do html fechar arquivo
        fclose(fp);
        // se nao tiver titulo, setar titulo como nome
        if (temTitulo == 1)
        {
            strcpy(tmp.titulo, tmp.nome);
        }
    }
    else
    {
        printf("Erro ao tentar ler arquivo\n");
    }
    return tmp;
}

char *removeTag(char *line, char linemod[]) // trim e remove tag
{
    int pos = 0;
    for (int i = 0; i < strlen(line) - 1; i++)
    {
        if (line[i] == '<')
        {
            i++;
            while (line[i] != '>')
            {
                i++;
            }
        }
        else
        {
            if ((isspace(line[i])) && (isspace(line[i + 1])))
            {
                i += 1;
            }
            else
            {
                strncpy(&linemod[pos], &line[i], 1);
                pos++;
            }
        }
    }
    linemod[pos] = '\0';
    return linemod;
}

float calculoOrcamento(char line[])
{
    int pos = 0;
    for (int i = 0; i < strlen(line); i++)
    {
        if ((isdigit(line[i])) || (line[i] == '.'))
        {
            line[pos] = line[i];
            pos++;
        }
    }
    line[pos] = '\0';
    memset(&line[pos + 1], 0, strlen(line));
    return (atof(line));
}

int isFim(char str[])
{
    return ((str[0] == 'F') && (str[1] == 'I') && (str[2] == 'M'));
}
// SOURCE: https://stackoverflow.com/questions/47116974/remove-a-substring-from-a-string-in-c
char *strremove(char *str, const char *sub)
{
    size_t len = strlen(sub);
    if (len > 0)
    {
        char *p = str;
        while ((p = strstr(p, sub)) != NULL)
        {
            memmove(p, p + len, strlen(p + len) + 1);
        }
    }
    return str;
}

// FUNCOES DA LISTA ==============================================================

// Cria uma lista dupla sem elementos (somente no cabeca).
void start()
{
    Filme aux;
    primeiro = novaCelulaDupla(aux);
    ultimo = primeiro;
}

// Insere um elemento na primeira posicao da lista.
void inserirInicio(Filme filme)
{
    CelulaDupla *tmp = novaCelulaDupla(filme);

    tmp->ant = primeiro;
    tmp->prox = primeiro->prox;
    primeiro->prox = tmp;
    if (primeiro == ultimo)
    {
        ultimo = tmp;
    }
    else
    {
        tmp->prox->ant = tmp;
    }
    tmp = NULL;
}

// Insere um elemento na ultima posicao da lista.
void inserirFim(Filme filme)
{
    ultimo->prox = novaCelulaDupla(filme);
    ultimo->prox->ant = ultimo;
    ultimo = ultimo->prox;
}

// Remove um elemento da primeira posicao da lista.
Filme removerInicio()
{
    if (primeiro == ultimo)
    {
        printf("erro: lista vazia!");
        exit(EXIT_FAILURE);
    }

    CelulaDupla *tmp = primeiro;
    primeiro = primeiro->prox;
    Filme resp = primeiro->filme;
    tmp->prox = primeiro->ant = NULL;
    free(tmp);
    tmp = NULL;
    return resp;
}

// Remove um elemento da ultima posicao da lista.
Filme removerFim()
{
    if (primeiro == ultimo)
    {
        printf("erro: lista vazia!");
        exit(EXIT_FAILURE);
    }
    Filme resp = ultimo->filme;
    ultimo = ultimo->ant;
    ultimo->prox->ant = NULL;
    free(ultimo->prox);
    ultimo->prox = NULL;
    return resp;
}

// Calcula e retorna o tamanho, em numero de elementos, da lista.
int tamanho()
{
    int tamanho = 0;
    CelulaDupla *i;
    for (i = primeiro; i != ultimo; i = i->prox, tamanho++)
        ;
    return tamanho;
}

// Insere um elemento em uma posicao especifica considerando que o primeiro elemento valido esta na posicao 0.
void inserir(Filme filme, int pos)
{

    int tam = tamanho();

    if (pos < 0 || pos > tam)
    {
        printf("erro: posicao invalida!");
        exit(EXIT_FAILURE);
    }
    else if (pos == 0)
    {
        inserirInicio(filme);
    }
    else if (pos == tam)
    {
        inserirFim(filme);
    }
    else
    {
        // Caminhar ate a posicao anterior a insercao
        CelulaDupla *i = primeiro;
        int j;
        for (j = 0; j < pos; j++, i = i->prox);

        CelulaDupla *tmp = novaCelulaDupla(filme);
        tmp->ant = i;
        tmp->prox = i->prox;
        tmp->ant->prox = tmp->prox->ant = tmp;
        tmp = i = NULL;
    }
}

// Remove um elemento de uma posicao especifica da lista
// considerando que o primeiro elemento valido esta na posicao 0.
Filme remover(int pos)
{
    Filme resp;
    int tam = tamanho();

    if (primeiro == ultimo)
    {
        printf("erro: lista vazia!");
        exit(EXIT_FAILURE);
    }
    else if (pos < 0 || pos >= tam)
    {
        printf("erro: posicao invalida!");
        exit(EXIT_FAILURE);
    }
    else if (pos == 0)
    {
        resp = removerInicio();
    }
    else if (pos == tam - 1)
    {
        resp = removerFim();
    }
    else
    {
        // Caminhar ate a posicao anterior a insercao
        CelulaDupla *i = primeiro->prox;
        int j;
        for (j = 0; j < pos; j++, i = i->prox);
        i->ant->prox = i->prox;
        i->prox->ant = i->ant;
        resp = i->filme;
        i->prox = i->ant = NULL;
        free(i);
        i = NULL;
    }

    return resp;
}

// Imprimir lista de filmes
void imprimir()
{
    CelulaDupla *i;
    int j = 0;
    for (i = primeiro->prox; i != NULL; i = i->prox)
    {
        printf("[%d] %s %s %s %d %s %s %s %g [", j, i->filme.nome, i->filme.titulo, i->filme.data, i->filme.duracao, i->filme.genero, i->filme.idioma, i->filme.situacao, i->filme.orcamento);
        j++;
        if (i->filme.numPChave >= 0)
        {
            for (int j = 0; j < i->filme.numPChave; j++)
            {
                printf("%s, ", i->filme.palavrasChave[j]);
            }
            printf("%s]\n", i->filme.palavrasChave[i->filme.numPChave]);
        }
        else
        {
            printf("]\n");
        }
    }
}

// QUICKSORT ================================================================

CelulaDupla *sort(CelulaDupla *esq, CelulaDupla *dir)
{
    CelulaDupla *i = esq->ant;
    for (CelulaDupla *j = esq; j != dir; j = j->prox)
    {
        if (strcmp(j->filme.situacao, dir->filme.situacao) > 0 || (strcmp(j->filme.situacao, dir->filme.situacao) == 0 && strcmp(j->filme.nome, dir->filme.nome) < 0))
        {
            if (i == NULL)
            {
                i = esq;
            }
            else
            {
                i = i->prox;
            }
            swap(i, j);
        }
    }
    if (i == NULL)
    {
        i = esq;
    }
    else
    {
        i = i->prox;
    }
    swap(i, dir);
    return i;
}

void quicksort(CelulaDupla *esq, CelulaDupla *dir)
{
    if (dir != NULL && esq != dir && esq != dir->prox)
    {
        CelulaDupla *pivo = sort(esq, dir);
        quicksort(esq, pivo->ant);
        quicksort(pivo->prox, dir);
    }
}

void swap(CelulaDupla *i, CelulaDupla *j)
{
    Filme tmp = i->filme;
    i->filme = j->filme;
    j->filme = tmp;
}