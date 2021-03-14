package br.com.unicap.compiladores.main;

import java.util.ArrayList;

import br.com.unicap.compiladores.analisadorlexico.*;
import br.com.unicap.compiladores.excecoes.LexicalException;

public class Main {
    public static void main(String[] args) {
        try {
            ScannerNosso s = new ScannerNosso("teste.txt");
            ArrayList<Token> tokens = s.getTokens();
            System.out.println(tokens);
        }catch (LexicalException a){
            System.out.println("ERRO: " + a.getMessage());
        }catch (Exception b){
            System.out.println("ERRO GENERICO");
        }
    }
}
