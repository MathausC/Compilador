package br.com.unicap.compiladores.analisadorlexico;

import java.util.Hashtable;

class Hash {

    private static Hash hash;

    private static Hashtable<String, TokensID> pRHashtable;

    private Hash() {
        pRHashtable = new Hashtable<String, TokensID>();
        run();
    }

    void run() {
        pRHashtable.put("main", TokensID.TK_PR_MAIN);
        pRHashtable.put("if", TokensID.TK_PR_IF);
        pRHashtable.put("else", TokensID.TK_PR_ELSE);
        pRHashtable.put("while", TokensID.TK_PR_WHILE);
        pRHashtable.put("do", TokensID.TK_PR_DO);
        pRHashtable.put("for", TokensID.TK_PR_FOR);
        pRHashtable.put("int", TokensID.TK_PR_INT);
        pRHashtable.put("float", TokensID.TK_PR_FLOAT);
        pRHashtable.put("char", TokensID.TK_PR_CHAR);
    }

    protected static Hash getCOntrutor() {
        if(hash == null) {
            hash = new Hash();
        }
        return hash;
    }

    TokensID get(String termo) {
        return pRHashtable.get(termo);
    }
}
