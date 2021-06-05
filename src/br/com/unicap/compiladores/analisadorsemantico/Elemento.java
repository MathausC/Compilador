package br.com.unicap.compiladores.analisadorsemantico;
import br.com.unicap.compiladores.analisadorlexico.*;

public class Elemento <T> {
    private T valor;
    private Token tipo;
    private int escopo;
    private int nivel;

    public Elemento(T valor, Token tipo){
        this.valor = valor;
        this.tipo = tipo;
    }

    public T getValor(){
        return valor;

    }

    void setValor(T valor) {
	this.valor = valor;
    }

    public Token getTipo(){
        return tipo;
    }

    void setTipo(Token token){
	tipo = token;
    }

    public int getNivel() {
        return nivel;
    }
    void setNivel(int valor) {
        nivel = valor;
    }

    public int getEscopo() {
        return escopo;
    }

    void setEscopo(int valor) {
        escopo = valor;
    }

    public boolean temMesmoNome(Elemento<T> e) {
	    if(e.tipo.getTexto() == this.tipo.getTexto()) {
            if(e.getNivel() < this.getNivel()) {
                return true;
            }
        }
        return true;
    }


}
