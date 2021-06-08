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
	    if(e.getTipo().getTexto() == this.getTipo().getTexto()) {
            if(e.getNivel() < this.getNivel()) {
                return true;
            }
        }
        return true;
    }
    //this é o novo. E é o elemento da lista
    public boolean equals(Elemento<T> e) {
        if(this.getTipo().getTexto().compareTo(e.getTipo().getTexto()) == 0){
            if(this.nivel < e.getNivel()){
                return false;
            } else if (this.getNivel() == e.getNivel()) {
                if(this.getEscopo() == e.getEscopo()){
                    return true;
                } else {
                    return false; 
                }
            } else {
                return true;
            }            
        }else{
            return false;
        }
    }
}


