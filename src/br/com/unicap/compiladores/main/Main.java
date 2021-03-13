package br.com.unicap.compiladores.main;

import java.util.ArrayList;

import br.com.unicap.compiladores.analisadorlexico.*;

public class Main {
    public static void main(String[] args) {
        ScannerNosso s = new ScannerNosso("teste.txt");
        ArrayList<Token> tokens = s.getTokens();
        System.out.println(tokens);
    }
}
