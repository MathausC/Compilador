package br.com.unicap.compiladores.main;


import br.com.unicap.compiladores.analisadorlexico.*;
import br.com.unicap.compiladores.excecoes.LexicalException;
import br.com.unicap.compiladores.excecoes.SyntacticException;
import br.com.unicap.compiladores.parser.Parser;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Token> t = new ArrayList<Token>();
        System.out.println(TokensID.TK_ATRIBUICAO);
        try {
            ScannerNosso s = new ScannerNosso("teste.txt");
            Parser p = Parser.getContrutor(s);
            p.getParser();
            t = s.getTokens();
            System.out.println(t);
        }catch (LexicalException a){
            System.out.println(a.getMessage());
        }catch (SyntacticException a) {
            System.out.println(a.getMessage());
        }
    }
}
