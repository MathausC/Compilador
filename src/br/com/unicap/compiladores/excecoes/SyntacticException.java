package br.com.unicap.compiladores.excecoes;


public class SyntacticException extends RuntimeException{
    public final static int ERRO_TYPE_CLASS = 5;
    public final static int ERRO_NAME_CLASS = 6;
    public final static int ERRO_OPEN_BLOCK= 7;
    public final static int ERRO_CLOSE_BLOCK = 8;
    public final static int ERRO_DECLARATION = 9;
    public final static int ERRO_CLOSE_DECLARATION = 10;
    public final static int ERRO_ATTRIBUTION = 11;
    public final static int ERRO_CLOSE_COMPARING = 12;
    public final static int ERRO_TYPE_COMPARING = 13;
    public final static int ERRO_CONDICIONAL_CLOSE = 14;
    public final static int ERRO_CONDICIONAL_OPEN = 15;
    public final static int ERRO_EXPRESSION_FORMATION = 16;
    public final static int STATIMANT_MISSING = 17;
    public final static int CONDICIONAL_MISSING = 18;
    
    
    

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