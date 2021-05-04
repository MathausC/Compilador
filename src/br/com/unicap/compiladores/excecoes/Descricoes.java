package br.com.unicap.compiladores.excecoes;

import java.util.Hashtable;

class Descricoes {
    Hashtable<Integer, String> descricaoHT = new Hashtable<Integer, String>();

    void run() {
        descricaoHT.put(LexicalException.ERRO_ID, "Identificador fora do padrão (letras, underline e digitos).");
        descricaoHT.put(LexicalException.ERRO_INT, "Inteiro inválido (somente numerais permitidos).");
        descricaoHT.put(LexicalException.ERRO_FLOAT, "Float inválido (somente numerais antes e após o ponto).");
        descricaoHT.put(LexicalException.ERRO_CHAR, "Caracter mal formado (somente um caracter entre aspas simples).");
        descricaoHT.put(LexicalException.ERRO_ESTADO, "Erro de estado (caracter deve se removido)");
       
        descricaoHT.put(SyntacticException.ERRO_TYPE_CLASS, "Identificador fora do padrão (letras, underline e digitos).");
        descricaoHT.put(SyntacticException.ERRO_NAME_CLASS, "Inteiro inválido (somente numerais permitidos).");
        descricaoHT.put(SyntacticException.ERRO_OPEN_BLOCK, "Float inválido (somente numerais antes e após o ponto).");
        descricaoHT.put(SyntacticException.ERRO_CLOSE_BLOCK, "Caracter mal formado (somente um caracter entre aspas simples).");
        descricaoHT.put(SyntacticException.ERRO_DECLARATION,"");
        descricaoHT.put(SyntacticException.ERRO_CLOSE_DECLARATION,"");
        descricaoHT.put(SyntacticException.ERRO_ATTRIBUTION,"");
        descricaoHT.put(SyntacticException.ERRO_CLOSE_COMPARING,"");
        descricaoHT.put(SyntacticException.ERRO_TYPE_COMPARING,"");
        descricaoHT.put(SyntacticException.ERRO_CONDICIONAL_CLOSE,"");
        descricaoHT.put(SyntacticException.ERRO_CONDICIONAL_OPEN,"");
        descricaoHT.put(SyntacticException.ERRO_COMPARATION_TYPE,"");
    }

    Descricoes() {
        run();
    }
}
