import java.io.*;

public class arquivo {
    public static void main(String[] args) {
        // VARIAVEIS
        int n = MyIO.readInt();
        double num = 0.0;
        long fp = 0;
        // abrir arquivo e escrever n numeros no arquivo
        try {
            RandomAccessFile raf = new RandomAccessFile("Q09.txt", "rw");
            for(int i = 0; i < n; i++){
                num = MyIO.readDouble();
                raf.writeDouble(num);
                fp = raf.getFilePointer();
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // abrir novamente o arquivo, ler numeros de tras pra frente e imprimir
        try {
            RandomAccessFile raf = new RandomAccessFile("Q09.txt", "r");
            fp = fp - 8;
            for(int i = 0; i < n; i++){
                raf.seek(fp - i * 8);
                num = raf.readDouble();
                if ((num % 1) == 0){
                    MyIO.println((int)num);
                }
                else {
                    MyIO.println(num);
                }
            }
            raf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
