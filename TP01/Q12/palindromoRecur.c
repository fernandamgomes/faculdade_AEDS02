#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int isPalindromoRecur(char str[], int i);
int isFim(int tam, char str[]);
int main()
{
    // VARIAVEIS
    char str[150];
    int palindromo = 0;

    fgets(str, 150, stdin);
    while (isFim(strlen(str), str) == 0)
    {
        palindromo = isPalindromoRecur(str, 0);
        if (palindromo == 0)
        {
            printf("SIM\n");
        }
        else if (palindromo == 1)
        {
            printf("NAO\n");
        }
        fflush(stdin);
        fgets(str, 150, stdin);
    }
    return 0;
}
// funcao recursiva que checa se e palindromo ou nao e imprime o resultado na tela
int isPalindromoRecur(char str[], int i)
{
    int resp;
    // tam = strlen - 1 - ultimo caractere \0
    int tam = strlen(str) - 1;
    if (i > tam / 2 - 1)
    {
        resp = 0;
    }
    else
    {
        if (str[i] != str[tam - 1 - i])
        {
            resp = 1;
        }
        else
        {
            resp = isPalindromoRecur(str, i + 1);
        }
    }
    return resp;
}
// checa se chegou ao fim do input
int isFim(int tam, char str[])
{
    return ((strlen(str) == 4) && (str[0] == 'F') && (str[1] == 'I') && (str[2] == 'M'));
}