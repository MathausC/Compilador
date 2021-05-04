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
       
        descricaoHT.put(SyntacticException.ERRO_TYPE_CLASS, "Tipo de retorno da classe deve ser declarado.");
        descricaoHT.put(SyntacticException.ERRO_NAME_CLASS, "Nome de classe não permitido.");
        descricaoHT.put(SyntacticException.ERRO_OPEN_BLOCK, "Token inesperado antes do bloco.");
        descricaoHT.put(SyntacticException.ERRO_CLOSE_BLOCK, "Bloco não foi fechado.");
        descricaoHT.put(SyntacticException.ERRO_CLOSE_DECLARATION,"Declaração não finalizada.");
        descricaoHT.put(SyntacticException.ERRO_ATTRIBUTION,"Atribuição não compativel.");
        descricaoHT.put(SyntacticException.ERRO_CLOSE_COMPARING,"Comparação não finalizada.");
        descricaoHT.put(SyntacticException.ERRO_TYPE_COMPARING,"Atribuição invalida para comparação.");
        descricaoHT.put(SyntacticException.ERRO_CONDICIONAL_CLOSE,"Parâmetro da condicional não fechado.");
        descricaoHT.put(SyntacticException.ERRO_CONDICIONAL_OPEN,"Abertura necessária para o parâmetro da condicional");
        descricaoHT.put(SyntacticException.ERRO_EXPRESSION_FORMATION,"Expressão invalida");
        descricaoHT.put(SyntacticException.ERRO_DECLARATION,"Declaração inválida.");
        descricaoHT.put(SyntacticException.STATIMANT_MISSING,"Estabelcer condicional do faltando.");
        descricaoHT.put(SyntacticException.CONDICIONAL_MISSING,"Condicional não declarada.");
    }

    Descricoes() {
        run();
    }
}
