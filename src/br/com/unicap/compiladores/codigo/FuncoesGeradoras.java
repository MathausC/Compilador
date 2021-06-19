package br.com.unicap.compiladores.codigo;


public class FuncoesGeradoras {
    private TabelaDeComandos tabela;
    private String registrador;
    private String funcao;
    private static int label;

    public FuncoesGeradoras() {
        tabela = TabelaDeComandos.getConstrutor();
    }
    public String geraFuncao(ClusterDeOperacoes co){
        String op = tabela.get(co.getOperacao());
        if(isAtribuicao(op)){
            return op + " " + co.getDestino().getRegistrador() + ", " + co.getDestino().getValor();
        }else if(isOperacaoR(op)){
            return op + " " + co.getDestino().getRegistrador() + ", " + co.getOperando1().getRegistrador() + ", " + co.getOperando2().getRegistrador();
        }else if(isComparacao(op)){            
            return   "CMP " + co.getDestino().getRegistrador() + ", " + co.getOperando1().getRegistrador() + "\n" + op + " ." + label();
        }else{
            return null;
        }
        
    }

    public String label() {
        String l = "L" + label;
        return l;
    }

    public void setRegistrador(String reg) {
        registrador = reg;
    }

    public void setFuncao(String fun) {
        funcao = fun;
    }

    public String getRegistrador() {
        return registrador;
    }

    public String getFuncao() {
        return funcao;
    }

    private boolean isOperacaoR(String op){
        return ("ADD".equals(op) || "SUB".equals(op) || "DIV".equals(op)  || "MULT".equals(op));
    }
    

    private boolean isAtribuicao(String op){
        return "MOV".equals(op);
    }

    private boolean isComparacao(String op){
        return ("JL".equals(op) || "JLE".equals(op) || "JG".equals(op)  || "JGE".equals(op)|| "JNE".equals(op)|| "JE".equals(op));
    }

    public String getLabel() {
        String s = "L" + label + ":\n";
        label++;
        return s;
    }
}
