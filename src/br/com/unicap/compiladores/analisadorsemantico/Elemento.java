package br.com.unicap.compiladores.analisadorsemantico;
import br.com.unicap.compiladores.analisadorlexico.*;

public class Elemento <T>{
    private T valor;
    private Token tipo;

    public Elemento(T valor, Token tipo){
        this.valor = valor;
        this.tipo = tipo;
    }

    public T getValor(){
        return valor;

    }
    public Token getTipo(){
        return tipo;
    }
    

    
}
