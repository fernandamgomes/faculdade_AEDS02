#include <stdio.h>
#include <stdlib.h>
#include <string.h>
int isFim(char str[])
{
    return ((str[0] == 'F') && (str[1] == 'I') && (str[2] == 'M'));
}

// transforma char minusculo em maiusculo
char toUpper(char c)
{
    return (c >= 'a' && c <= 'z') ? ((char)((int)c - 32)) : c;
}

// checa se toda a string e composta por vogais
void isVogal(char str[])
{
    char *result = "SIM";
    char c = '\0';
    for (int i = 0; i < strlen(str) - 2; i++)
    {
        c = toUpper(str[i]);
        if ((c != 'A') && (c != 'E') && (c != 'I') && (c != 'O') && (c != 'U'))
        {
            result = "NAO";
            i = strlen(str);
        }
    }
    printf("%s ", result);
}
// checa se um caractere e vogal
int isVogalChar(char c)
{
    int result = 0;
    c = toUpper(c);
    if ((c != 'A') && (c != 'E') && (c != 'I') && (c != 'O') && (c != 'U'))
    {
        result = 1;
    }
    return result;
}

// checa se toda a string e composta por consoantes
void isConsonant(char str[])
{
    char *result = "SIM";
    char c = '\0';
    for (int i = 0; i < strlen(str) - 2; i++)
    {
        c = toUpper(str[i]);
        if ((c < 'A' || c > 'Z') || (isVogalChar(c) == 0))
        {
            result = "NAO";
            i = strlen(str);
        }
    }
    printf("%s ", result);
}

// checa se a string e um numero inteiro
void isInt(char str[])
{
    char *result = "SIM";
    char c = '\0';
    for (int i = 0; i < strlen(str) - 2; i++)
    {
        c = str[i];
        if ((c < '0') || (c > '9'))
        {
            result = "NAO";
        }
    }
    printf("%s ", result);
}

// checa se a string e um numero inteiro
void isReal(char str[])
{
    char *result = "SIM";
    char c = '\0';
    int contador = 0;
    for (int i = 0; i < strlen(str) - 2; i++)
    {
        c = str[i];
        if (c == ',' || c == '.')
        {
            contador++;
        }
        if ((contador > 1) || ((c != ',' && c != '.') && (c < '0') || (c > '9')))
        {
            result = "NAO";
            i = strlen(str);
        }
    }
    printf("%s ", result);
}

int main(void)
{
    // VARIAVEIS
    char str[200];
    fflush(stdin);
    fgets(str, 100, stdin);

    
    while (isFim(str) == 0)
    {
        isVogal(str);
        isConsonant(str);
        isInt(str);
        isReal(str);
        printf("\n");
        fflush(stdin);
        fgets(str, 200, stdin);
    }
    return 0;
}
