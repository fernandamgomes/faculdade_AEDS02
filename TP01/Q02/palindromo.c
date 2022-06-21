#include <stdio.h>
#include <stdlib.h>
#include <string.h>
void isPalindromo(int tam, char str[]);
int isFim(char str[]);
int main()
{
    // VARIAVEIS
    char str[250];
    int tam = 0;

    fgets(str, 250, stdin);
    tam = strlen(str);
    while (isFim(str) == 0)
    {
        isPalindromo(tam, str);
        fflush(stdin);
        fgets(str, 250, stdin);
        tam = strlen(str);
    }
    return 1;
}
// checa se e palindromo ou nao e imprime o resultado na tela
void isPalindromo(int tam, char str[])
{
    int teste = 0;
    for (int i = 0; i < tam - 2 / 2; i++)
    {
        if (str[i] != str[tam - 2 - i])
        {
            teste = 1;
            i = tam;
        }
    }
    if (teste == 0)
    {
        printf("SIM\n");
    }
    else
    {
        printf("NAO\n");
    }
}
// checa se chegou ao fim do input
int isFim(char str[])
{
    return ((str[0] == 'F') && (str[1] == 'I') && (str[2] == 'M'));
}