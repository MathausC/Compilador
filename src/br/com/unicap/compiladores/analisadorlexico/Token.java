package br.com.unicap.compiladores.analisadorlexico;

import java.util.Hashtable;

public class Token{
    static final int TK_IDENTIFICADOR = 0;
    static final int TK_PR_MAIN = 1;
    static final int TK_PR_IF = 2;
    static final int TK_PR_ELSE = 3;
    static final int TK_PR_WHILE = 4;
    static final int TK_PR_DO = 5;
    static final int TK_PR_FOR = 6;
    static final int TK_PR_INT = 7;
    static final int TK_PR_FLOAT = 8;
    static final int TK_PR_CHAR = 9;    
    static final int TK_NUMERO_INT = 10;
    static final int TK_NUMERO_FLT = 11;
    static final int TK_OPER_REL_IGUAL = 12;
    static final int TK_OPER_REL_MENOR = 13;
    static final int TK_OPER_REL_MAIOR = 14;
    static final int TK_OPER_REL_MENOR_IGUAL = 15;
    static final int TK_OPER_REL_MAIOR_IGUAL = 16;
    static final int TK_OPER_REL_DIFERENTE = 17;
    static final int TK_OPER_MAT_SOMA = 18;
    static final int TK_OPER_MAT_SUB = 19;
    static final int TK_OPER_MAT_MULT = 20;
    static final int TK_OPER_MAT_DIV = 21;
    static final int TK_SEPARADOR_ABRE_CHAV = 22;
    static final int TK_SEPARADOR_FECHA_CHAV = 23;
    static final int TK_SEPARADOR_ABRE_COLCH = 24;
    static final int TK_SEPARADOR_FECHA_COLCH = 25;
    static final int TK_SEPARADOR_VIRGULA = 26;
    static final int TK_SEPARADOR_PONTO = 27;
    static final int TK_ATRIBUICAO = 28;
    static final int TK_CHAR = 29;

    private int tipo;
    private String texto;

    protected static Hashtable<String, Integer> pRHashtable;

    protected void run() {
        pRHashtable.put("main", 1);
        pRHashtable.put("if", 2);
        pRHashtable.put("else", 3);
        pRHashtable.put("while", 4);
        pRHashtable.put("do", 5);
        pRHashtable.put("for", 6);
        pRHashtable.put("int", 7);
        pRHashtable.put("float", 8);
        pRHashtable.put("char", 9);
    }
    
    Token(int tipo, String texto) {
        super();
        setTipo(tipo);
        setTexto(texto);
    }

    Token(){
        pRHashtable = new Hashtable<String, Integer>();
        run();
    }

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

    private String getTKString(int tipo) {
        switch (tipo) {
            case TK_IDENTIFICADOR: return "IDENTIFICADOR";
            case TK_PR_MAIN: return "MAIN";
            case TK_PR_IF: return "IF_ID";
            case TK_PR_ELSE: return "ELSE_ID";
            case TK_PR_WHILE: return "WHILE_ID";
            case TK_PR_DO: return "DO_ID";
            case TK_PR_FOR: return "FOR_ID";
            case TK_PR_INT: return "INT_ID";
            case TK_PR_FLOAT: return "FLOAT_ID";
            case TK_PR_CHAR: return "CHAR_ID";
            case TK_NUMERO_INT : return "INT_NUM";
            case TK_NUMERO_FLT : return "FLOAT_NUM";
            case TK_OPER_REL_IGUAL : return "IGUAL";
            case TK_OPER_REL_MENOR : return "MENOR";
            case TK_OPER_REL_MAIOR : return "MAIOR";
            case TK_OPER_REL_MENOR_IGUAL : return "MENOR_IGUAL";
            case TK_OPER_REL_MAIOR_IGUAL : return "MAIOR_IGUAL";
            case TK_OPER_REL_DIFERENTE : return "DIFERENTE";
            case TK_OPER_MAT_SOMA : return "SOMA";
            case TK_OPER_MAT_SUB : return "SUB";
            case TK_OPER_MAT_MULT : return "MULT";
            case TK_OPER_MAT_DIV : return "DIV";
            case TK_SEPARADOR_ABRE_CHAV : return "ABRE_CHAVE";
            case TK_SEPARADOR_FECHA_CHAV : return "FECHA_CHAVE";
            case TK_SEPARADOR_ABRE_COLCH : return "ABRE_COLCHETES";
            case TK_SEPARADOR_FECHA_COLCH : return "FECHA_COLCHETES";
            case TK_SEPARADOR_VIRGULA : return "VIRGULA";
            case TK_SEPARADOR_PONTO : return "PONTO_VIRGULA";
            case TK_ATRIBUICAO : return "ATRIBUICAO";
            case TK_CHAR : return "CHAR_ELEMENTO";
            default: return "CASO_INVÁLIDO";
        }
    }

    public String toString() {
        return "[tipo: " + getTKString(tipo) + " | texto: " + texto + "]\n";
    }
}
