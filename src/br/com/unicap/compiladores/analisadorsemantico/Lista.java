package br.com.unicap.compiladores.analisadorsemantico;

import br.com.unicap.compiladores.analisadorlexico.Token;

public class Lista  {

    private No inicio;
    private No fim;

    public void inserir(Elemento<Token> e) {
        No no = new No(e);
        if(inicio == null) {
            inicio = no;
            fim = no;
        } else {
           fim.setProx(no);
           no.setAnt(fim);
           fim = no;
        }
    }

    public boolean procura(Elemento<Token> e) {
        return true;
    }
 
    private class No {
        private Elemento<Token> valor;
        private No anr;
        private No prox;

        No(Elemento<Token> valor) {
            this.valor = valor;
        }

        Elemento<Token> getValor() {
            return valor;
        }
        void setValor(Elemento<Token> valor) {
            this.valor = valor;
        }

        No getAnt() {
            return anr;
        }
        void setAnt(No no) {
            this.anr = no;
        }

        No getProx() {
            return prox;
        }
        void setProx(No no) {
            prox = no;
        }

    }
}
