package br.com.unicap.compiladores.analisadorsemantico;

import java.util.LinkedList;
import java.util.Stack;

public class GerenciadorSemantico {
    private LinkedList<Elemento<String>> lista;
    private Stack<Integer> pilha;
    
    public GerenciadorSemantico() {
        lista = new LinkedList<Elemento<String>>();
        pilha = new Stack<Integer>();
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
            //erro
        }
    }
    
//elemento ta dentro da lista
    public Elemento<String> procurar(Elemento<String> e) {
       int n = pilha.pop();
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
}
