package br.com.unicap.compiladores.analisadorlexico;

import java.util.Hashtable;

class Hash {

    private static Hash hash;

    private static Hashtable<String, Integer> pRHashtable;

    private Hash() {
        pRHashtable = new Hashtable<String, Integer>();
        run();
    }

    void run() {
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

    protected static Hash getCOntrutor() {
        if(hash == null) {
            hash = new Hash();
        }
        return hash;
    }

    Integer get(String termo) {
        return pRHashtable.get(termo);
    }
}
