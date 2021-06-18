package br.com.unicap.compiladores.main;


import br.com.unicap.compiladores.analisadorlexico.ScannerNosso;
import br.com.unicap.compiladores.excecoes.LexicalException;
import br.com.unicap.compiladores.excecoes.SyntacticException;
import br.com.unicap.compiladores.parser.Parser;

public class Main {
    public static void main(String[] args) {
        try {
            ScannerNosso s = new ScannerNosso("teste.txt");
            Parser p = Parser.getContrutor(s);
            p.getParser();
        }catch (LexicalException a){
            System.out.println(a.getMessage());
        }catch (SyntacticException a) {
            System.out.println(a.getMessage());
        }
    }
}
