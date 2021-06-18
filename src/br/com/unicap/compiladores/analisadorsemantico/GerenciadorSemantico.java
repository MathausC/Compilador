package br.com.unicap.compiladores.analisadorsemantico;
import br.com.unicap.compiladores.excecoes.SemanticException;
import br.com.unicap.compiladores.parser.Parser;

import java.util.Stack;

public class GerenciadorSemantico {
    private Lista lista;
    private Stack<Integer> pilha;
    private Parser p;
    
    public GerenciadorSemantico(Parser p) {
        lista = new Lista();
        pilha = new Stack<Integer>();
        this.p = p;
    }
    public void fechaEscopo(){
       int n = pilha.pop();
       int i = lista.size() - 1;
       while(lista.get(i).getNivel() == n){
            lista.get(i).modFalse();
            i--;           
       }
    }
    // 0 1
    public void add(int n){
        if(pilha.search(n) == -1){
            pilha.push(n);
        }
    }
    public void addLista(Elemento<String> e){
        if(!lista.contains(e)) {
            lista.add(e);
        } else {
            throw new SemanticException(SemanticException.ERRO_DEFINED_VARIABLE, p.getLinha(), p.getColuna());
        }
    }
    
//elemento ta dentro da lista
    public Elemento<String> procurar(Elemento<String> e) {
       int i = lista.size() - 1;
       while(i >= 0){
            if(lista.get(i).equals(e)){
                if(lista.get(i).getMod()){
                    return lista.get(i);
                }
            }
            i--;           
       }
        return null;
    }

    public void alteraElemento(Elemento<String> e){
        if(lista.contains(e)) {
            int index = lista.indexOf(e);
            lista.set(index, e);
        }
    }    
    public Lista getLista(){
        return this.lista;
    }
}
