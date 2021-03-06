package br.com.unicap.compiladores.analisadorsemantico;
import br.com.unicap.compiladores.analisadorlexico.*;
import br.com.unicap.compiladores.parser.*;
import br.com.unicap.compiladores.parser.Terminal;

public class Elemento <T> extends Terminal{
    private T valor;
    private Token tipo;
    private int escopo;
    private int nivel;
    private boolean mod;
    private String registrador;

    public Elemento(T valor, Token tipo, int nivel){
        this.valor = valor;
        this.tipo = tipo;
        mod = true;
        setNivel(nivel);
    }

    public void modFalse(){
        mod = false;
    }

    public void modTrue() {
        mod = true;
    }
    public boolean getMod(){
        return this.mod;
    }

    public T getValor(){
        return valor;

    }

    public void setValor(T valor) {
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
    
    public boolean equals(Elemento<T> e) {
        if(!mod) return mod;
        if(isRepetitive(e.getTipo())) {
            return false;
        }
        return (this.getTipo().getTexto().compareTo(e.getTipo().getTexto()) == 0);
    }

    public void setRegistrador(String registrador) {
        this.registrador = registrador;
    }

    public String getRegistrador() {
        return registrador;
    }
}


