package br.com.unicap.compiladores.excecoes;

public class LexicalException extends RuntimeException{
    public LexicalException(String ms){
        super(ms);
    }
}
