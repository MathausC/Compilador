package br.com.unicap.compiladores.excecoes;

public class SemanticException extends RuntimeException{
    public final static int ERRO_UNDEFINED_VARIABLE= 19;
    public final static int ERRO_DEFINED_VARIABLE = 20;
    public final static int ERRO_ATTRIBUTION_INCORRECT= 21;
    public final static int ERRO_3 = 22;
    public final static int ERRO_4 = 23;

    private Descricoes d = new Descricoes();
    private String ms;
    private int linha;
    private int coluna;
    
    public SemanticException(int erro, int linha, int coluna){
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
