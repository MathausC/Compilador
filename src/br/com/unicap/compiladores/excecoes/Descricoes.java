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
    }

    Descricoes() {
        run();
    }
}
