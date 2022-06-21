#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define TAM 80
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

void lerArquivo(char *arquivo, int contador);
char *removeTag(char *line, char linemod[300]);
int isFim(char str[]);
float calculoOrcamento(char line[]);
char *strremove(char *str, const char *sub);
void imprimir(Filme filmes[], int contador);
void inserir(int pos, Filme filmes[], char *filme);
Filme remover(int pos, Filme filmes[]);

int main(void)
{
    // VARIAVEIS
    char str[100];
    int numOperacoes = 0;
    // ler nome do arquivo a partir do pubin
    fgets(str, 100, stdin);
    str[strlen(str) - 1] = '\0';

    // enquanto nao alcancar o fim do pubin ler arquivos
    while (isFim(str) == 0)
    {
        // preencher struct filme com dados do filme lido e salvar o tam do array de palavras chave
        lerArquivo(str, contador);
        fflush(stdin);
        // ler nome do arquivo a partir do pubin
        fgets(str, 100, stdin);
        contador++;
        str[strlen(str) - 1] = '\0';
    }
    fflush(stdin);
    fgets(str, 100, stdin);
    numOperacoes = atoi(str);
    for (int i = numOperacoes; numOperacoes > 0; numOperacoes--)
    {
        // VARIAVEIS
        char operacao[5] = "\0";
        char filme[50] = "\0";
        int teste = 0;
        int pos = 0;
        char strPos[5] = "\0";
        Filme aux;
        // MANIPULAR STRINGS --> FILME E OPERACAO
        fgets(str, 100, stdin);
        operacao[0] = str[0];
        operacao[1] = str[1];
        operacao[2] = '\0';
        // SE OPERACAO FOR DO TIPO INSERIR --> PEGAR NOME FILME
        if ((strstr(operacao, "II")) || (strstr(operacao, "IF")))
        {
            strncpy(filme, str + 3, strlen(str) - 1);
            filme[strlen(filme) - 1] = '\0';
        }
        else if ((strstr(operacao, "I*")) || (strstr(operacao, "R*")))
        {
            strncpy(strPos, str + 3, 4);
            pos = atoi(strPos);
        }
        if (strstr(operacao, "I*"))
        {
            strncpy(filme, str + 6, strlen(str) - 1);
            filme[strlen(filme) - 1] = '\0';
            inserir(pos, filmes, filme);
        }
        else if (strstr(operacao, "II"))
        {
            inserir(0, filmes, filme);
        }
        else if (strstr(operacao, "IF"))
        {
            pos = contador;
            inserir(pos, filmes, filme);
        }
        else if (strstr(operacao, "R*"))
        {
            aux = remover(pos, filmes);
        }
        else if (strstr(operacao, "RI"))
        {
            aux = remover(0, filmes);
        }
        else if (strstr(operacao, "RF"))
        {
            pos = contador - 1;
            aux = remover(pos, filmes);
        }
        if (strstr(operacao, "RI") || strstr(operacao, "RF") || strstr(operacao, "R*"))
        {
            printf("(R) %s\n", aux.nome);
        }
        fflush(stdin);
    }
    imprimir(filmes, contador);
    return 1;
}

void inserir(int pos, Filme filmes[], char *filme)
{
    if ((contador >= TAM) || (pos > contador) || (pos < 0))
    {
        printf("erro ao inserir!");
        EXIT_FAILURE;
    }
    for (int i = contador; i > pos; i--)
    {
        filmes[i] = filmes[i - 1];
    }
    lerArquivo(filme, pos);
    contador++;
}

Filme remover(int pos, Filme filmes[])
{
    if ((pos < 0) || (contador == 0) || (pos >= contador))
    {
        printf("erro ao remover!");
        EXIT_FAILURE;
    }
    Filme aux = filmes[pos];
    contador--;
    for (int i = pos; i < contador; i++)
    {
        filmes[i] = filmes[i + 1];
    }
    return aux;
}

void imprimir(Filme filmes[], int contador)
{
    // nome - titulo - data - duracao - genero - idioma - siuacao -  orcamento - pchave
    for (int j = 0; j < contador; j++)
    {
        printf("[%d] %s %s %s %d %s %s %s %g [", j, filmes[j].nome, filmes[j].titulo, filmes[j].data, filmes[j].duracao, filmes[j].genero, filmes[j].idioma, filmes[j].situacao, filmes[j].orcamento);
        if (filmes[j].numPChave >= 0)
        {
            for (int i = 0; i < filmes[j].numPChave; i++)
            {
                printf("%s, ", filmes[j].palavrasChave[i]);
            }
            printf("%s]\n", filmes[j].palavrasChave[filmes[j].numPChave]);
        }
        else
        {
            printf("]\n");
        }
    }
}

void lerArquivo(char *arquivo, int contador)
{
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
                strcpy(filmes[contador].nome, linecopy);
            }

            // TITULO
            else if (strstr(line, "<strong>Título original</strong>"))
            {
                // formatar string e atualizar que esse filme tem titulo setado
                temTitulo = 0;
                removeTag(line, linecopy);
                strremove(linecopy, "Título original ");
                strcpy(filmes[contador].titulo, linecopy);
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
                strcpy(filmes[contador].data, linecopy);
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
                filmes[contador].duracao = duracao;
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
                strcpy(filmes[contador].genero, linecopy);
            }

            // SITUACAO
            else if (strstr(line, "<bdi>Situação</bdi>"))
            {
                // formatar string
                removeTag(line, linecopy);
                strremove(linecopy, "Situação ");
                strcpy(filmes[contador].situacao, linecopy);
            }

            // IDIOMA
            else if (strstr(line, "Idioma"))
            {
                // formatar string
                removeTag(line, linecopy);
                strremove(linecopy, "Idioma original ");
                strcpy(filmes[contador].idioma, linecopy);
            }

            // ORCAMENTO
            else if (strstr(line, "Orçamento"))
            {
                // formatar string
                removeTag(line, linecopy);
                float orcamento = calculoOrcamento(linecopy);
                filmes[contador].orcamento = orcamento;
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
                    strcpy(filmes[contador].palavrasChave[0], "\0");
                }
                else
                {
                    while ((strstr(line, "</ul>") == NULL))
                    {
                        removeTag(line, linecopy);
                        if (linecopy[0] != '\0')
                        {
                            strcpy(filmes[contador].palavrasChave[pChave], linecopy);
                            pChave++;
                        }
                        fgets(line, 300, fp);
                    }
                }
                pChave = pChave - 1;
                filmes[contador].numPChave = pChave;
            }
            // ler proxima linha ate o fim do arquivo
            fgets(line, 500, fp);
        }
        // apos leitura do html fechar arquivo
        fclose(fp);
        // se nao tiver titulo, setar titulo como nome
        if (temTitulo == 1)
        {
            strcpy(filmes[contador].titulo, filmes[contador].nome);
        }
    }
    else
    {
        printf("Erro ao tentar ler arquivo\n");
    }
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
