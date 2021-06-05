package br.com.unicap.compiladores.analisadorsemantico;

import java.util.LinkedList;
import br.com.unicap.compiladores.analisadorlexico.Token;

public class GerenciadorSemantico {
    private LinkedList<Elemento<Token>> lista;
    
    public GerenciadorSemantico() {
        lista = new LinkedList<Elemento<Token>>();
    }

    public int procurar(Elemento<Token> e) {
        if(lista.contains(e)) {
            int index = lista.indexOf(e);
            Elemento<Token> aux = lista.get(index);
            if(aux.temMesmoNome(e)) {
                return index;
            } else {
                
            }
        }
        
        return -1;
    }
}
