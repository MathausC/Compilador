package br.com.unicap.compiladores.codigo;

public class FuncoesGeradoras {
    private String registrador;
    private String funcao;
    private static int contador;

    public void setRegistrador(String reg) {
        registrador = reg;
    }

    public void setFuncao(String fun) {
        funcao = fun;
    }

    public String getRegistrado() {
        return registrador;
    }

    public String getFuncao() {
        return funcao;
    }
}
