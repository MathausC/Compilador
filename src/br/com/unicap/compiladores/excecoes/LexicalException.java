package br.com.unicap.compiladores.excecoes;

public class LexicalException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public LexicalException(String ms){
        super(ms);
    }
}
