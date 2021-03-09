package br.com.unicap.compiladores.analisadorlexico;

class Token {
    static final int TK_IDENTIFICADOR = 0;
    static final int TK_NUMERO_INT = 1;
    static final int TK_NUMERO_FLT = 2;
    static final int TK_OPER_REL = 3;
    static final int TK_OPER_MAT = 4;
    static final int TK_PONTUACAO = 5;
    static final int TK_ATRIBUICAO = 6;
    static final int TK_CHAR = 7;
    static final int TK_STRING = 8;

    private int tipo;
    private String texto;
    
    Token(int tipo, String texto) {
        setTipo(tipo);
        setTexto(texto);
    }

    Token(){}

    int getTipo() {
        return tipo;
    }

    private void setTipo(int tipo) {
        this.tipo = tipo;
    }

    String getTexto() {
        return texto;
    }

    private void setTexto(String texto) {
        this.texto = texto;
    }
}
