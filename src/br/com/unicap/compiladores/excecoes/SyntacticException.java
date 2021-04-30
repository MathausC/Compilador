package br.com.unicap.compiladores.excecoes;


public class SyntacticException extends RuntimeException{
    public final static int ERRO_TYPE_CLASS = 5;
    public final static int ERRO_NAME_CLASS = 6;
    public final static int ERRO_OPEN_BLOCK= 7;
    public final static int ERRO_CLOSE_BLOCK = 8;
    

    private Descricoes d = new Descricoes();
    private String ms;
    private int linha;
    private int coluna;

    public SyntacticException(int erro, int linha, int coluna){
        super();
        ms = d.descricaoHT.get(erro);
        this.linha = linha;
        this.coluna = coluna;
    }

    @Override
    public String getMessage(){
        return "ERRO: " + ms + "\nLINHA: " + (linha+1) + " COLUNA: "+ coluna;
    }

}