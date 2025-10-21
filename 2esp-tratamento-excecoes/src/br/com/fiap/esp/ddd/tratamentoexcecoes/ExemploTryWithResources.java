package br.com.fiap.esp.ddd.tratamentoexcecoes;

import java.io.BufferedReader;
import java.io.FileReader;

public class ExemploTryWithResources {
    public static void main(String[] args) {
        try (BufferedReader br = new BufferedReader(new FileReader("arquivo.txt"))) {
            System.out.println(br.readLine());
        } catch (Exception e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }
}
