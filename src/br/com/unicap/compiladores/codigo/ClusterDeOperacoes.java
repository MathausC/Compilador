package br.com.unicap.compiladores.codigo;

import br.com.unicap.compiladores.analisadorsemantico.Elemento;

public class ClusterDeOperacoes {
    private Elemento<String> destino;
    private Elemento<String> operando1;
    private Elemento<String> operando2;
    private String operacao;

    public Elemento<String> getDestino() {
        return destino;
    }

    public void setDestino(Elemento<String> destino) {
        this.destino = destino;
    }

    public Elemento<String> getOperando1() {
        return operando1;
    }

    public void setOperando1(Elemento<String> operando1) {
        this.operando1 = operando1;
    }

    public Elemento<String> getOperando2() {
        return operando2;
    }
    public void setOperando2(Elemento<String> operando2) {
        this.operando2 = operando2;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }
}
