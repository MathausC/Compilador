package br.com.unicap.compiladores.excecoes;

public class LexicalException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public final static int ERRO_ID = 0;
    public final static int ERRO_INT = 1;
    public final static int ERRO_FLOAT = 2;
    public final static int ERRO_CHAR = 3;
    public final static int ERRO_ESTADO = 4;

    private Descricoes d = new Descricoes();
    private String ms;
    private int linha;
    private int coluna;
    
    public LexicalException(int erro, int linha, int coluna){
        super();
        ms = d.descricaoHT.get(erro);
        this.linha = linha;
        this.coluna = coluna;
    }

    @Override
    public String getMessage() {
        return "ERRO: " + ms + "\nLINHA: " + (linha+1) + " COLUNA: "+ coluna;
    }
}
