package br.com.fiap.esp.ddd.tratamentoexcecoes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ExemploTryCatchFinally {
    public static void main(String[] args) {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader("arquivo.txt"));
            System.out.println(br.readLine());
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.err.println("Erro ao fechar o recurso: " + e.getMessage());
                }
            }
        }
    }
}
