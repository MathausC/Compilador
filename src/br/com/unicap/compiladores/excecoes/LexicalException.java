package br.com.unicap.compiladores.excecoes;

public class LexicalException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private int linha;
    private int coluna;

    public LexicalException(String ms, int linha, int coluna){
        super(ms + "\nLINHA: " + (linha+1) + " COLUNA: "+ coluna);
    }

}
